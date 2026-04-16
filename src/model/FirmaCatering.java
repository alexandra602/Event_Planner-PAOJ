package model;

public class FirmaCatering extends Furnizor {
    // pret (mostenit din Furnizor) reprezinta taxa de logistica (transport, inchiriere vesela, staff)
    // pretul meniurilor se calculeaza separat, prin pretMeniu
    private double pretMeniu;
    private String specificCulinar; // ex: traditional, asiatic etc.
    private boolean meniuriSpeciale; // daca ofera menirui de copii, vegane etc.

    public FirmaCatering(int id, String nume, String telefon, String email, double pret, double rating, double meniu, String specificCulinar, boolean meniuriSpeciale) {
        super(id, nume, telefon, email, pret, rating);
        this.pretMeniu = pret;
        this.specificCulinar = specificCulinar;
        this.meniuriSpeciale = meniuriSpeciale;
    }

    // setteri & getteri
    public double getPretMeniu() { return pretMeniu;}
    public void setPretMeniu(double pretMeniu) { this.pretMeniu = pretMeniu;}

    public String getSpecificCulinar() { return specificCulinar;}
    public void setSpecificCulinar(String specificCulinar) { this.specificCulinar = specificCulinar;}

    public boolean isMeniuriSpeciale() {  return meniuriSpeciale;}
    public void setMeniuriSpeciale(boolean meniuriSpeciale) { this.meniuriSpeciale = meniuriSpeciale;}

    // afisare
    @Override
    public String toString(){
        return "Firma Catering: " + super.toString() + " | Pret per Meniu: " + pretMeniu + " | Specific Culinar: " + specificCulinar + " | Meniuri Speciale: " + (meniuriSpeciale ? "Da" : "Nu");
    }
}
