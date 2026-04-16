package model;
import java.time.LocalDate;

public class PetrecerePrivata extends Eveniment {
    private String tematica;
    private int nrCopii; // pentru a calcula costul total la mancare, pretul meniului de copii este jumatate din pretul meniului normal
    private boolean openBar; // daca petrecerea va avea Open Bar. Daca da, se adauga o taxa fixa de bauturi (150 lei/invitat) doar pentru adulti

    public PetrecerePrivata(int id, String nume, LocalDate data, int nrInvitati, Client client, String tematica, int nrCopii, boolean openBar) {
        super(id, nume, data, nrInvitati, client);
        this.tematica = tematica;
        this.nrCopii = nrCopii;
        this.openBar = openBar;
    }

    // setteri & getteri
    public String getTematica() { return tematica;}
    public void  setTematica(String tematica) { this.tematica = tematica;}

    public int getNrCopii() { return nrCopii;}
    public void setNrCopii(int nrCopii) { this.nrCopii = nrCopii;}

    public boolean isOpenBar() { return openBar;}
    public void setOpenBar(boolean openBar) { this.openBar = openBar;}

    // afisare
    @Override
    public String toString() {
        return super.toString() + "\n  -> Tip: Petrecere Privata | Tematica: " + tematica +
                " | Open Bar: " + (openBar ? "Da" : "Nu");
    }
}
