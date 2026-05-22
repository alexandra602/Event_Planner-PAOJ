package service;

import config.DatabaseManager;
import model.FirmaCatering;
import model.Fotograf;
import model.Furnizor;
import model.TrupaMuzica;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FurnizorDbService implements GenericService<Furnizor> {

    private static FurnizorDbService inst;

    private FurnizorDbService() {}

    public static FurnizorDbService getInstance() {
        if (inst == null) {
            inst = new FurnizorDbService();
        }
        return inst;
    }

    @Override
    public void adauga(Furnizor furnizor) {
        String sql = "INSERT INTO FURNIZORI (id, tip_furnizor, nume, telefon, email, pret_baza) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = DatabaseManager.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, furnizor.getId());
            ps.setString(2, furnizor.getClass().getSimpleName()); // salvez tipul pentru a sti daca e trupa, firmaCatering etc.
            ps.setString(3, furnizor.getNume());
            ps.setString(4, furnizor.getTelefon());
            ps.setString(5, furnizor.getEmail());
            ps.setDouble(6, furnizor.getPret());
            ps.setDouble(7, furnizor.getPret());

            ps.executeUpdate();
            System.out.println("   [DB] Furnizorul " + furnizor.getNume() + " a fost salvat cu succes!");
        } catch (SQLException ex) {
            System.out.println("    [!] Eroare la adaugarea furnizorului: " + ex.getMessage());
        }
    }

    @Override
    public List<Furnizor> citeste() {
        List<Furnizor> furnizori = new ArrayList<>();
        String sql = "SELECT * FROM FURNIZORI";

        try (Connection con = DatabaseManager.getConnection(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String tip = rs.getString("tip_furnizor");
                String nume = rs.getString("nume");
                String telefon = rs.getString("telefon");
                String email = rs.getString("email");
                double pret = rs.getDouble("pret_baza");
                double rating = rs.getDouble("rating");

                Furnizor f = null;
                if (tip != null) {
                    switch (tip) {
                        case "CandyBar":
                            f = new model.CandyBar(id, nume, telefon, email, pret, rating, "Standard", false);
                            break;
                        case "FirmaCatering":
                            f = new model.FirmaCatering(id, nume, telefon, email, pret, rating, 150.0, "International", false);
                            break;
                        case "Florist":
                            f = new model.Florist(id, nume, telefon, email, pret, rating, 100.0, new ArrayList<>());
                            break;
                        case "Fotograf":
                            f = new model.Fotograf(id, nume, telefon, email, pret, rating, true, 250.0, 14);
                            break;
                        case "TrupaMuzica":
                            f = new model.TrupaMuzica(id, nume, telefon, email, pret, rating, "Cover", 4);
                            break;
                        default:
                            System.out.println("   [!] Tip de furnizor necunoscut: " + tip);
                            break;
                    }
                }

                if (f != null) {
                    furnizori.add(f);
                }
            }
        } catch (SQLException e) {
            System.out.println("   [!] Eroare la citirea furnizorilor: " + e.getMessage());
        }
        return furnizori;
    }

    @Override
    public void actualizeaza(Furnizor furnizor) {
        String sql = "UPDATE FURNIZORI SET pret_baza = ? WHERE id = ?";
        try (Connection con = DatabaseManager.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDouble(1, furnizor.getPret());
            ps.setInt(2, furnizor.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("   [!] Eroare la actualizarea furnizorului: " + e.getMessage());
        }
    }

    @Override
    public void sterge(int id) {
        String sql = "DELETE FROM FURNIZORI WHERE id = ?";
        try (Connection con = DatabaseManager.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("   [DB] Furnizorul cu ID " + id + " a fost sters.");
        } catch (SQLException e) {
            System.out.println("   [!] Eroare la stergerea furnizorului: " +  e.getMessage());
        }
    }
}