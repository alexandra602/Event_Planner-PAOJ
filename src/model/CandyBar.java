package model;

public class CandyBar extends Furnizor {
    // pret (mostenit din Furnizor) reprezinta costul fix al pachetului de dulciuri (+transport, vase decorative, montaj)
    private String tematica;
    private boolean optiuniVegane; // daca furnizorul ofera optiuni vegane

    public CandyBar(int id, String nume, String telefon, String email, double pret, double rating, String tematica, boolean optiuniVegane) {
        super(id, nume, telefon, email, pret, rating);
        this.tematica = tematica;
        this.optiuniVegane = optiuniVegane;
    }

    // setteri & getteri
    public String getTematica() { return tematica;}
    public void setTematica(String tematica) { this.tematica = tematica;}

    public boolean isOptiuniVegane() { return optiuniVegane;}
    public void setOptiuniVegane(boolean optiuniVegane) { this.optiuniVegane = optiuniVegane;}

    // afisare
    @Override
    public String toString(){
        return "CandyBar: " + super.toString() + " | Tematica: " + tematica + " | Optiuni Vegane: " + (optiuniVegane ? "Da" : "Nu");
    }
}
