package model;
import java.util.List;

public class Florist extends Furnizor {
    // pret (mostenit din Furnizor) reprezinta taxa de transport si montaj
    // costul prorpiu-zis al florilor se calculeaza separat, prin costAranjament
    private double costAranjament;
    private List<String> floriDisponibile; // ce flori au in stoc si pot livra

    public Florist(int id, String nume, String telefon, String email, double pret, double rating, double costAranjament, List<String> floriDisponibile) {
        super(id, nume, telefon, email, pret, rating);
        this.costAranjament = costAranjament;
        this.floriDisponibile = floriDisponibile;
    }

    // setteri & getteri
    public double getCostAranjament() {return costAranjament;}
    public void setCostAranjament(double costAranjament) { this.costAranjament = costAranjament; }

    public List<String> getFloriDisponibile() { return floriDisponibile;}
    public void setFloriDisponibile(List<String> floriDisponibile) { this.floriDisponibile = floriDisponibile;}

    // functie pentru a verifica daca o anumita floare este disponibila
    public boolean oferaFloare (String floare) {
        return floriDisponibile.contains(floare);
    }

    // afisare
    @Override
    public String toString(){
        return "Florist: " + super.toString() + " | Cost per Aranjament: " + costAranjament + " RON | Flori: " + floriDisponibile;
    }
}
