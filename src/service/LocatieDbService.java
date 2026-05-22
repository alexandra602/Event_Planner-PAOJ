package service;

import config.DatabaseManager;
import model.Locatie;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LocatieDbService implements GenericService<Locatie> {

    private static LocatieDbService inst;

    private LocatieDbService() {} // constructor privat

    public static LocatieDbService getInstance() {
        if (inst == null) {
            inst = new LocatieDbService();
        }
        return inst;
    }

    @Override
    public void adauga(Locatie locatie) {
        String sql = "INSERT INTO LOCATII (id, nume, oras, capacitate, pret, persoana_contact, telefon) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = DatabaseManager.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, locatie.getId());
            ps.setString(2, locatie.getNume());
            ps.setString(3, locatie.getAdresa());
            ps.setInt(4, locatie.getCapacitate());
            ps.setDouble(5, locatie.getPret());
            ps.setString(6, locatie.getNumeContact());
            ps.setString(7, locatie.getTelefonContact());

            ps.executeUpdate();
            System.out.println("   [DB] Locatia " + locatie.getNume() + " a fost salvata cu succes!");
        } catch (SQLException ex) {
            System.out.println("    [!] Eroare la adaugarea locatiei: " + ex.getMessage());
        }
    }

    @Override
    public List<Locatie> citeste() {
        List<Locatie> locatii = new ArrayList<>();
        String sql = "SELECT * FROM LOCATII";

        try (Connection con = DatabaseManager.getConnection(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String nume = rs.getString("nume");
                String adresa = rs.getString("oras");
                int capacitate = rs.getInt("capacitate");
                double pret = rs.getDouble("pret");
                String numeContact = rs.getString("persoana_contact");
                String telefonContact = rs.getString("telefon");

                Locatie l = new Locatie(id, nume, adresa, capacitate, pret, numeContact, telefonContact);
                locatii.add(l);
            }
        } catch (SQLException e) {
            System.out.println("   [!] Eroare la citirea locatiilor: " + e.getMessage());
        }
        return locatii;
    }

    @Override
    public void actualizeaza(Locatie locatie) {
        String sql = "UPDATE LOCATII SET pret = ? WHERE id = ?";
        try (Connection con = DatabaseManager.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setDouble(1, locatie.getPret());
            ps.setInt(2, locatie.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("   [!] Eroare la actualizarea locatiei: " + e.getMessage());
        }
    }

    @Override
    public void sterge(int id) {
        String sql = "DELETE FROM LOCATII WHERE id = ?";
        try (Connection con = DatabaseManager.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("   [DB] Locatia cu ID " + id + " a fost stearsa.");

        } catch (SQLException e) {
            System.out.println("   [!] Eroare la stergerea locatiei: " +  e.getMessage());
        }
    }
}