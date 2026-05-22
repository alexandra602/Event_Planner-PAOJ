package service;
import config.DatabaseManager;
import model.Client;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// serviciu singleton
public class ClientDbService implements GenericService<Client> {
    private static ClientDbService inst;

    private ClientDbService() {} // constructor

    public static ClientDbService getInstance() {
        if (inst == null) {
            inst = new ClientDbService();
        }
        return inst;
    }

    @Override
    public void adauga(Client client) {
        String sql = "INSERT INTO CLIENTI (id, nume, telefon, email, buget) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = DatabaseManager.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, client.getId());
            ps.setString(2, client.getNume());
            ps.setString(3, client.getTelefon());
            ps.setString(4, client.getEmail());
            ps.setDouble(5, client.getBuget());

            ps.executeUpdate();
            System.out.println("   [DB] Clientul " + client.getNume() + " a fost salvat cu succes!");
        } catch (SQLException ex) {
            System.out.println("    [!] Eroare la adaugarea clientului: " + ex.getMessage());
        }
    }

    @Override
    public List<Client> citeste() {
        List<Client> clienti = new ArrayList<>();
        String sql = "SELECT * FROM CLIENTI";

        try (Connection con = DatabaseManager.getConnection(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String nume = rs.getString("nume");
                String telefon = rs.getString("telefon");
                String email = rs.getString("email");
                double buget = rs.getDouble("buget");

                Client c = new Client(id, nume, telefon, email, buget);
                clienti.add(c);
            }
        } catch (SQLException e) {
            System.out.println("   [!] Eroare la citirea clientilor: " + e.getMessage());
        }
        return clienti;
    }

    @Override
    public void actualizeaza(Client client) {
        String sql = "UPDATE CLIENTI SET buget = ? WHERE id = ?";
        try (Connection con = DatabaseManager.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setDouble(1, client.getBuget());
            ps.setInt(2, client.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("   [!] Eroare la actualizarea clientului: " + e.getMessage());
        }
    }

    @Override
    public void sterge(int id) {
        String sql = "DELETE FROM CLIENTI WHERE id = ?";
        try (Connection con = DatabaseManager.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("   [DB] Clientul cu ID " + id + " a fost sters.");

        } catch (SQLException e) {
            System.out.println("   [!] Eroare la stergerea clientului: " +  e.getMessage());
        }
    }
}
