package service;
import model.*;
import java.util.*;

public class EventPlannerService {
    // colectiile nesortate
    private List<Eveniment> evenimente = new ArrayList<>();
    private List<Furnizor> furnizori =  new ArrayList<>();
    private List<Locatie> locatii = new ArrayList<>();

    // colectie sortata
    private SortedSet<Client> clienti = new  TreeSet<>();

    // 1. adaugare client
    public void adaugaClient (Client client) {
        this.clienti.add(client);
        System.out.println("Clientul " + client.getNume() + " a fost adaugat.");
    }

    // 2. adaugare furnizor in catalogul general
    public void adaugaFurnizor(Furnizor furnizor) {
        this.furnizori.add(furnizor);
        System.out.println("Furnizorul " + furnizor.getNume() + " a fost adaugat.");
    }

    // 3. creare eveniment
    public void creeazaEveniment (Eveniment eveniment) {
        this.evenimente.add(eveniment);
        System.out.println("Evenimentul " + eveniment.getNume() + " a fost creat.");
    }

    // 4. asigneaza locatie (+validarea capacitatii)
    public void asigneazaLocatie(int idEveniment, int idLocatie) {
        Eveniment e = gasesteEveniment(idEveniment);

        // caut locatia in lista
        Locatie locatieGasita = null;
        for (Locatie l : locatii) {
            if (l.getId() == idLocatie) {
                locatieGasita = l;
                break;
            }
        }

        if (e != null && locatieGasita != null) {
            if (e.getNrInvitati() <= locatieGasita.getCapacitate()) {
                e.setLocatie(locatieGasita);
                System.out.println("   Locatia " + locatieGasita.getNume() + " a fost asignata cu succes!");
            } else {
                System.out.println("   Eroare: Locatia e prea mica pentru " + e.getNrInvitati() + " invitati.");
            }
        } else {
            System.out.println("   Eroare: Evenimentul sau Locatia nu au fost gasite!");
        }
    }

    // 5. asociaza furnizor la eveniment
    public void asociazaFurnizor (int idEveniment, int idFurnizor) {
        Eveniment e = gasesteEveniment(idEveniment);
        Furnizor f = gasesteFurnizor(idFurnizor);

        if (e != null && f != null) {
            e.adaugaFurnizor(f);
            System.out.println("Furnizorul " + f.getNume() + " a fost asociat evenimentului " + e.getNume());
        }
    }

    // 6. calculul costului total
    public double calculeazaCostTotal (int idEveniment) {
        Eveniment e = gasesteEveniment(idEveniment);

        if (e == null) return 0;

        double total = 0;
        // adaug pretul locatiei
        if (e.getLocatie() != null) {
            double l = e.getLocatie().getPret();
            // daca e conferinta, pretul se plateste pe zi
            if (e instanceof Conferinta) {
                l *= ((Conferinta) e).getNrZile();
            }
            total += l;
        }

        // adaug pretul pentru furnizori
        for (Furnizor f : e.getFurnizori()) {
            // adaug taxa de baza
            total += f.getPret();

            // logica pentru catering
            if (f instanceof FirmaCatering) {
                FirmaCatering fc = (FirmaCatering) f;

                if (e instanceof PetrecerePrivata) {
                    PetrecerePrivata pp = (PetrecerePrivata) e;
                    int adulti = pp.getNrInvitati() - pp.getNrCopii();

                    // mancare adulti + mancare copii (50% din pret)
                    total += adulti * fc.getPret();
                    total += pp.getNrCopii() * (fc.getPretMeniu() / 2);

                    // daca ofera open bar
                    if(pp.isOpenBar()) {
                        total += adulti * 150;
                    }
                }
                else if (e instanceof Conferinta) {
                    Conferinta c = (Conferinta) e;

                    // pretul mancarii se calculeaza per zi de conferinta
                    total += c.getNrInvitati() * fc.getPret() * c.getNrZile();
                }
            }

            // logica pentru fotograf
            if (f instanceof Fotograf) {
                Fotograf fo = (Fotograf) f;
                if (fo.isOferaVideo()) total += fo.getPretVideo();
                if (fo.isOferaAlbum()) total += fo.getPretAlbum();
            }
        }
       if (e instanceof Conferinta) {
           total -= ((Conferinta) e).getBugetSponsori();
       }

       return total;
    }

    // 7. validare buget (verific daca se incadreaza in bugetul clientului)
    public void valideazaBuget (int idEveniment) {
        Eveniment e = gasesteEveniment(idEveniment);
        if (e == null) {
            System.out.println("   Eroare: Evenimentul cu ID " + idEveniment + " nu a fost gasit!");
            return;
        }

        double cost = calculeazaCostTotal(idEveniment);
        double buget = e.getClient().getBuget();

        if (cost <= buget) {
            e.setStatus("CONFIRMAT");
            System.out.println("Status: Eveniment confirmat! Cost: " + cost + " / Buget: " + buget);
        } else {
            e.setStatus("ANULAT");
            System.out.println("Status: Eveniment anulat! Depaseste bugetul cu " + (cost - buget) + " RON");
        }
    }

    // 8. afisare clienti (sortati alfabetic)
    public void afiseazaClienti () {
        System.out.println("\n Lista Clienti:");
        for (Client c : this.clienti) {
            System.out.println(c); // se apeleaza automat metoda toString din Client
        }
    }

    // 9. filtrare furnizori (ii caut pe cei mai ieftini)
    public void filtreazaFurnizori (double pretMax) {
        System.out.println("\n Furnizori cu taxa sub " + pretMax + "RON:");
        furnizori.stream().filter(f -> f.getPret() <= pretMax).forEach(f -> System.out.println(f.getNume() + " - " + f.getPret() + " RON"));
    }

    // 10. afiseaza raportul final al unui eveniment
    public void afiseazaRaportEveniment (int idEveniment) {
        Eveniment e = gasesteEveniment(idEveniment);
        if (e != null) {
            System.out.println("\n Dosar eveniment:");
            System.out.println(e);
            System.out.println("Cost total estimat: " + calculeazaCostTotal(idEveniment) + "RON");
        } else {
            System.out.println("Evenimentul nu a fost gasit.");
        }
    }
    // 11. adauga locatie
    public void adaugaLocatie(Locatie locatie) {
        this.locatii.add(locatie);
    }

    // 12. afiseaza toate locatiile
    public void afiseazaLocatii() {
        System.out.println("--- Catalog Locatii ---");
        for (Locatie l : locatii) {
            System.out.println(l.getId() + ". " + l.getNume() + " (Capacitate: " + l.getCapacitate() + ", Pret: " + l.getPret() + " RON)");
        }
    }

    // 13. afiseaza toti furnizorii
    public void afiseazaTotiFurnizorii() {
        System.out.println("--- Catalog Furnizori ---");
        for (Furnizor f : furnizori) {
            System.out.println(f.getId() + ". " + f.getNume() + " - Pret Baza: " + f.getPret() + " RON");
        }
    }

    // metode helper pentru cautare
    private Eveniment gasesteEveniment(int idEveniment) {
        return evenimente.stream().filter(e -> e.getId() == idEveniment).findFirst().orElse(null);
    }
    private Furnizor gasesteFurnizor(int idFurnizor) {
        return furnizori.stream().filter(f -> f.getId() == idFurnizor).findFirst().orElse(null);
    }

    // getteri
    public List<Eveniment> getEvenimente() { return this.evenimente;}
    public SortedSet<Client> getClienti() { return this.clienti;}
}
