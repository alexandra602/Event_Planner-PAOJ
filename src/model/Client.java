package model;

public class Client extends Persoana {
    private double buget; // bugetul pe care il are clientul la dispozitie (costul total va trebui sa fie <= buget)

    public Client (int id, String nume, String telefon, String email, double buget) {
        super(id, nume, telefon, email); // constructorul superclasei
        this.buget = buget;
    }

    // setteri & getteri specifici
    public double getBuget() { return buget;}
    public void setBuget(double buget) { this.buget = buget;}

    // afisare
    @Override
    public String toString() {
        return "Client: " + super.toString() + " | Buget: " + buget;
    }
}
