package model;

public class Locatie {
    private int id;
    private String nume; // denumirea locatiie
    private String adresa;
    private int capacitate; // nuamrul maxim de persoane pe care il poate gazdui locatia (trebuie verificat daca nrInvitati <= capacitate)
    private double pret; // cat costa sa inchiriezi locatia/zi
    private String numeContact; // responsabilul de locatie, persoana de contact
    private String telefonContact;

    public Locatie(int id,  String nume, String adresa, int capacitate, double pret,  String numeContact, String telefonContact) {
        this.id = id;
        this.nume = nume;
        this.adresa = adresa;
        this.capacitate = capacitate;
        this.pret = pret;
        this.numeContact = numeContact;
        this.telefonContact = telefonContact;
    }

    // setteri & getteri
    public int getId() { return id;}
    public void setId(int id) { this.id = id;}

    public String getNume() { return nume;}
    public void setNume(String nume) { this.nume = nume;}

    public String getAdresa() { return adresa;}
    public void setAdresa(String adresa) { this.adresa = adresa;}

    public int getCapacitate() { return capacitate;}
    public void setCapacitate(int capacitate) { this.capacitate = capacitate;}

    public double getPret() { return pret;}
    public void setPret(double pret) { this.pret = pret;}

    public String getNumeContact() { return numeContact;}
    public void setNumeContact(String numeContact) { this.numeContact = numeContact;}

    public String getTelefonContact() { return telefonContact;}
    public void setTelefonContact(String telefonContact) { this.telefonContact = telefonContact;}

    // afisare
    @Override
    public String toString() {
        return nume + " (" + adresa + "): Capacitate: " + capacitate +
                " persoane | Pret: " + pret + " | Contact: " +  numeContact +
                " (" + telefonContact + ")";
    }
}
