package model;

// persoana fizica (pentru Petreceri Private) sau persoana juridica (pentru Conferinte)
public abstract class Persoana {
    protected int id; // CNP pentru persoana fizica, CUI pentru persoana jurdiica
    protected String nume;
    protected String telefon;
    protected String email;

    public Persoana(int id, String nume, String telefon, String email) {
        this.id = id;
        this.nume = nume;
        this.telefon = telefon;
        this.email = email;
    }

    // setteri & getteri
    public int getId() { return id;}
    public void setId(int id) { this.id = id;}

    public String getNume() { return nume;}
    public void setNume(String nume) { this.nume = nume;}

    public String getTelefon() { return telefon;}
    public void setTelefon(String telefon) { this.telefon = telefon;}

    public String getEmail() { return email;}
    public void setEmail(String email) { this.email = email;}

    // afisare
    @Override
    public String toString() {
        return "Nume: " + nume + " | Telefon: " + telefon + " | Email: " + email;
    }
}