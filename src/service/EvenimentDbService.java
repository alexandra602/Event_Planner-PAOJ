package service;

import config.DatabaseManager;
import model.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EvenimentDbService implements GenericService<Eveniment> {

    private static EvenimentDbService inst;

    private EvenimentDbService() {}

    public static EvenimentDbService getInstance() {
        if (inst == null) {
            inst = new EvenimentDbService();
        }
        return inst;
    }

    @Override
    public void adauga(Eveniment eveniment) {
        String sql = "INSERT INTO EVENIMENTE (id, nume, nr_invitati, status, client_id, locatie_id, tip_eveniment, " +
                "tematica, nr_copii, open_bar, domeniu, nr_speakeri, nr_zile, buget_sponsori) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DatabaseManager.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, eveniment.getId());
            ps.setString(2, eveniment.getNume());
            ps.setInt(3, eveniment.getNrInvitati());
            ps.setString(4, eveniment.getStatus() != null ? eveniment.getStatus().name() : "IN_ASTEPTARE");

            if (eveniment.getClient() != null) {
                ps.setInt(5, eveniment.getClient().getId());
            } else {
                ps.setNull(5, Types.INTEGER);
            }

            if (eveniment.getLocatie() != null) {
                ps.setInt(6, eveniment.getLocatie().getId());
            } else {
                ps.setNull(6, Types.INTEGER);
            }

            ps.setString(7, eveniment.getClass().getSimpleName());

            // mapez atributele specifice
            if (eveniment instanceof PetrecerePrivata) {
                PetrecerePrivata pp = (PetrecerePrivata) eveniment;
                ps.setString(8, pp.getTematica());
                ps.setInt(9, pp.getNrCopii());
                ps.setInt(10, pp.isOpenBar() ? 1 : 0);

                // coloanele specifice conferintei raman nule
                ps.setNull(11, Types.VARCHAR);
                ps.setNull(12, Types.INTEGER);
                ps.setNull(13, Types.INTEGER);
                ps.setNull(14, Types.DOUBLE);
            } else if (eveniment instanceof Conferinta) {
                Conferinta conf = (Conferinta) eveniment;

                ps.setNull(8, Types.VARCHAR);
                ps.setNull(9, Types.INTEGER);
                ps.setNull(10, Types.INTEGER);

                ps.setString(11, conf.getDomeniu());
                ps.setInt(12, conf.getNrSpeakeri());
                ps.setInt(13, conf.getNrZile());
                ps.setDouble(14, conf.getBugetSponsori());
            }

            ps.executeUpdate();
            System.out.println("[DB] Evenimentul " + eveniment.getNume() + " a fost salvat!");
        } catch (SQLException ex) {
            System.out.println("[!] Eroare la adaugarea evenimentului: " + ex.getMessage());
        }
    }

    // iau evenimentele disponibile din bd
    @Override
    public List<Eveniment> citeste() {
        List<Eveniment> evenimente = new ArrayList<>();
        String sql = "SELECT * FROM EVENIMENTE";

        // iau clientii, locatiile si furnziorii din bd
        List<Client> totiClienti = ClientDbService.getInstance().citeste();
        List<Locatie> toateLocatiile = LocatieDbService.getInstance().citeste();
        List<Furnizor> totiFurnizorii = FurnizorDbService.getInstance().citeste();

        try (Connection con = DatabaseManager.getConnection(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String nume = rs.getString("nume");
                int nrInvitati = rs.getInt("nr_invitati");
                String statusStr = rs.getString("status");
                int clientId = rs.getInt("client_id");
                int locatieId = rs.getInt("locatie_id");
                String tipEveniment = rs.getString("tip_eveniment");

                // caut cientul si locatia asociata
                Client clientAsociat = totiClienti.stream().filter(c -> c.getId() == clientId).findFirst().orElse(null);
                Locatie locatieAsociata = toateLocatiile.stream().filter(l -> l.getId() == locatieId).findFirst().orElse(null);

                Eveniment ev = null;

                if ("Conferinta".equalsIgnoreCase(tipEveniment)) {
                    String domeniu = rs.getString("domeniu");
                    int nrSpeakeri = rs.getInt("nr_speakeri");
                    int nrZile = rs.getInt("nr_zile");
                    double bugetSponsori = rs.getDouble("buget_sponsori");
                    ev = new Conferinta(id, nume, java.time.LocalDate.now(), nrInvitati, clientAsociat, domeniu, nrSpeakeri, nrZile, bugetSponsori);
                } else {
                    String tematica = rs.getString("tematica");
                    int nrCopii = rs.getInt("nr_copii");
                    boolean openBar = rs.getInt("open_bar") == 1;
                    ev = new PetrecerePrivata(id, nume, java.time.LocalDate.now(), nrInvitati, clientAsociat, tematica, nrCopii, openBar);
                }

                if (locatieAsociata != null) ev.setLocatie(locatieAsociata);
                if (statusStr != null) ev.setStatus(StatusEveniment.valueOf(statusStr));

                // populez lista de furnizori pentru eveniment
                String sqlFur = "SELECT furnizor_id FROM EVENIMENT_FURNIZOR WHERE eveniment_id = ?";
                try (PreparedStatement psFur = con.prepareStatement(sqlFur)) {
                    psFur.setInt(1, id);
                    try (ResultSet rsFur = psFur.executeQuery()) {
                        while(rsFur.next()) {
                            int fId = rsFur.getInt("furnizor_id");
                            Furnizor fGasit = totiFurnizorii.stream().filter(f -> f.getId() == fId).findFirst().orElse(null);
                            if (fGasit != null) {
                                ev.adaugaFurnizor(fGasit);
                            }
                        }
                    }
                }

                evenimente.add(ev);
            }
        } catch (SQLException e) {
            System.out.println("[!] Eroare la citirea evenimentelor: " + e.getMessage());
        }
        return evenimente;
    }

    // actualizez locatia si statusul
    @Override
    public void actualizeaza(Eveniment eveniment) {
        String sql = "UPDATE EVENIMENTE SET status = ?, locatie_id = ? WHERE id = ?";
        try (Connection con = DatabaseManager.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, eveniment.getStatus() != null ? eveniment.getStatus().name() : "IN_ASTEPTARE");
            if (eveniment.getLocatie() != null) {
                ps.setInt(2, eveniment.getLocatie().getId());
            } else {
                ps.setNull(2, Types.INTEGER);
            }
            ps.setInt(3, eveniment.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("[!] Eroare la actualizare: " + e.getMessage());
        }
    }

    @Override
    public void sterge(int id) {
        String sql = "DELETE FROM EVENIMENTE WHERE id = ?";
        try (Connection con = DatabaseManager.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("[!] Eroare la stergere: " +  e.getMessage());
        }
    }

    // metoda pentru a salva legatura in tabelul asociativ
    public void asociazaFurnizor(int idEveniment, int idFurnizor) {
        String sql = "INSERT INTO EVENIMENT_FURNIZOR (eveniment_id, furnizor_id) VALUES (?, ?)";
        try (Connection con = DatabaseManager.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idEveniment);
            ps.setInt(2, idFurnizor);
            ps.executeUpdate();
            System.out.println("[DB] Furnizorul a fost asociat cu succes la eveniment!");
        } catch (SQLException e) {
            System.out.println("[!] Eroare la asociere: " + e.getMessage());
        }
    }
}