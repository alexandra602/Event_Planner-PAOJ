package model;

public class TrupaMuzica extends Furnizor {
    // pret (mostenit din Furnizor) reprezinta onorariul fix al trupei
    private String genMuzical; // ce gen de muzica performeaza in mod normal
    private int nrMembri; // pentru calculul meniurilor

    public TrupaMuzica(int id, String nume, String telefon, String email, double pret, double rating, String genMuzical, int nrMembri) {
        super(id, nume, telefon, email, pret, rating);
        this.genMuzical = genMuzical;
        this.nrMembri = nrMembri;
    }

    // setteri & getteri
    public String getGenMuzical() { return genMuzical;}
    public void setGenMuzical(String genMuzical) { this.genMuzical = genMuzical;}

    public int getNrMembri() { return nrMembri;}
    public void setNrMembri(int nrMembri) { this.nrMembri = nrMembri; }

    // afisare
    @Override
    public String toString(){
        return "Trupa Muzica: " + super.toString() + " | Gen: " + genMuzical + " | Nr Membri: " + nrMembri;
    }
}
