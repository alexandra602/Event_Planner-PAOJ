package model;

public abstract class Furnizor {
    protected int id;
    protected String nume; // denumirea trupei, firmei etc.
    protected String telefon;
    protected String email;
    protected double pret; // pretul de baza (explicat la inceputul fiecarei clase derivate)
    protected double rating; // cu cate stele e cotat (ex: 4.5/10)

    public Furnizor(int id, String nume, String telefon, String email, double pret, double rating) {
        this.id = id;
        this.nume = nume;
        this.telefon = telefon;
        this.email = email;
        this.pret = pret;
        this.rating = rating;
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

    public double getPret() { return pret;}
    public void setPret(double pret) { this.pret = pret;}

    public double getRating() { return rating;}
    public void setRating(double rating) { this.rating = rating;}

    // afisare
    @Override
    public String toString(){
        return nume + " [Contact: " + telefon + " | " + email + "] - Rating: " + rating + " | Pret baza: " + pret + " RON";
    }
}
