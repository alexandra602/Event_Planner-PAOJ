package model;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public abstract class Eveniment {
    protected int id;
    protected String nume; // denumirea evenimentului
    protected LocalDate data;
    protected int nrInvitati; // cate persoane participa; pentru meniuri, capacitate etc.
    protected String status; // IN_ASTEPTARE, CONFIRMAT, ANULAT

    // compozitie
    protected Client client;
    protected Locatie locatie;
    protected List<Furnizor> furnizori;

    public Eveniment(int id, String nume, LocalDate data, int nrInvitati, Client client) {
        this.id = id;
        this.nume = nume;
        this.data = data;
        this.nrInvitati = nrInvitati;
        this.client = client;

        this.status = "IN_ASTEPTARE";
        this.locatie = null;
        this.furnizori = new ArrayList<>();
    }

    // setteri & getteri
    public int getId() { return id;}
    public void setId(int id) { this.id = id;}

    public String getNume() { return nume;}
    public void setNume(String nume) { this.nume = nume;}

    public LocalDate getData() {return data;}
    public void setData(LocalDate data) { this.data = data;}

    public int getNrInvitati() { return nrInvitati;}
    public void setNrInvitati(int nrInvitati) { this.nrInvitati = nrInvitati;}

    public String getStatus() { return status;}
    public void setStatus(String status) { this.status = status;}

    public Client getClient() { return client;}
    public void setClient(Client client) { this.client = client;}

    public Locatie getLocatie() { return locatie;}
    public void setLocatie(Locatie locatie) { this.locatie = locatie;}

    public List<Furnizor> getFurnizori() { return furnizori;}
    public void setFurnizori(List<Furnizor> furnizori) { this.furnizori = furnizori;}

    // functie pentru a adauga un furnizor
    public void adaugaFurnizor(Furnizor furnizor) {
        this.furnizori.add(furnizor);
    }

    // afisare
    @Override
    public String toString() {
        String numeLocatie = (locatie == null) ? "N/A" : locatie.getNume();
        return "Eveniment: " + nume + " | Data: " + data +
                " | Invitati: " + nrInvitati + " | Locatie: " + numeLocatie +
                " | Status: " + status + "\n  -> Client: " + client.getNume() +
                "\n  -> Furnizori alocati: " + furnizori.size();
    }
}
