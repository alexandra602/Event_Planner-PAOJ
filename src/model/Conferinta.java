package model;
import java.time.LocalDate;

public class Conferinta extends Eveniment {
    private String domeniu; // ex: IT, medicina
    private int nrSpeakeri;
    private int nrZile; // pe cate zile se intinde conferinta; ajuta la pret total chirie/ catering
    private double bugetSponsori; // se scade din costul total la final

    public Conferinta(int id, String nume, LocalDate data, int nrInvitati, Client client, String domeniu, int nrSpeakeri, int nrZile, double bugetSponsori) {
        super(id, nume, data, nrInvitati, client);
        this.domeniu = domeniu;
        this.nrSpeakeri = nrSpeakeri;
        this.nrZile = nrZile;
        this.bugetSponsori = bugetSponsori;
    }

    // setteri & getteri
    public String getDomeniu() { return domeniu;}
    public void setDomeniu(String domeniu) { this.domeniu = domeniu;}

    public int getNrSpeakeri() { return nrSpeakeri;}
    public void setNrSpeakeri(int nrSpeakeri) { this.nrSpeakeri = nrSpeakeri;}

    public int getNrZile() { return nrZile;}
    public void setNrZile(int nrZile) { this.nrZile = nrZile;}

    public double getBugetSponsori() { return bugetSponsori;}
    public void setBugetSponsori(double bugetSponsori) { this.bugetSponsori = bugetSponsori;}

    // afisare
    @Override
    public String toString(){
        return super.toString() + "\n  -> Tip: Conferinta " + domeniu +
                " | Durata: " + nrZile + " zile | Speakeri: " + nrSpeakeri +
                " | Sponsorizari: " + bugetSponsori + " RON";
    }
}
