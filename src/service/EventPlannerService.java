package service;

import exception.BugetDepasitException;
import model.*;

public class EventPlannerService {

    private static EventPlannerService instance;

    private EventPlannerService() {}

    public static EventPlannerService getInstance() {
        if (instance == null) {
            instance = new EventPlannerService();
        }
        return instance;
    }

    // logica de salvare
    public void inregistreazaClient(Client client) {
        ClientDbService.getInstance().adauga(client);
        AuditService.getInstance().logAction("Inregistrare Client: " + client.getNume());
    }

    public void inregistreazaLocatie(Locatie locatie) {
        LocatieDbService.getInstance().adauga(locatie);
        AuditService.getInstance().logAction("Inregistrare Locatie: " + locatie.getNume());
    }

    public void inregistreazaFurnizor(Furnizor furnizor) {
        FurnizorDbService.getInstance().adauga(furnizor);
        AuditService.getInstance().logAction("Inregistrare Furnizor: " + furnizor.getNume());
    }

    public void inregistreazaEveniment(Eveniment eveniment) {
        EvenimentDbService.getInstance().adauga(eveniment);
        AuditService.getInstance().logAction("Creare Eveniment: " + eveniment.getNume());
    }

    // logica de calcul
    public double calculeazaCostTotal(Eveniment eveniment) {
        double total = 0;

        // costul locatiei calculat pe baza numarului de zile (daca e conferinta)
        if (eveniment.getLocatie() != null) {
            double pretZi = eveniment.getLocatie().getPret();
            if (eveniment instanceof Conferinta) {
                total += pretZi * ((Conferinta) eveniment).getNrZile();
            } else {
                total += pretZi; // petrecere privata dureaza o singura zi
            }
        }

        // costurile specifice ale furnizorilor
        if (eveniment.getFurnizori() != null) {
            for (Furnizor f : eveniment.getFurnizori()) {
                total += f.getPret(); // taxa de baza din clasa furnizor

                if (f instanceof FirmaCatering) {
                    FirmaCatering catering = (FirmaCatering) f;
                    double pretMeniu = catering.getPretMeniu();

                    if (eveniment instanceof PetrecerePrivata) {
                        PetrecerePrivata pp = (PetrecerePrivata) eveniment;
                        int nrCopii = pp.getNrCopii();
                        int adulti = pp.getNrInvitati() - nrCopii;
                        if (adulti < 0) adulti = 0;

                        // pretul meniului de copil e jumatate
                        total += (adulti * pretMeniu) + (nrCopii * (pretMeniu / 2.0));
                    } else if (eveniment instanceof Conferinta) {
                        Conferinta conf = (Conferinta) eveniment;
                        total += conf.getNrInvitati() * pretMeniu * conf.getNrZile();
                    }
                }
                else if (f instanceof Fotograf) {
                    Fotograf foto = (Fotograf) f;
                    if (foto.isOferaVideo()) total += foto.getPretVideo();
                    if (foto.isOferaAlbum()) total += foto.getPretAlbum();
                }
                else if (f instanceof Florist) {
                    total += ((Florist) f).getCostAranjament();
                }
            }
        }

        // taxe specifice tipului de eveniment
        if (eveniment instanceof PetrecerePrivata) {
            PetrecerePrivata pp = (PetrecerePrivata) eveniment;
            if (pp.isOpenBar()) {
                int adulti = pp.getNrInvitati() - pp.getNrCopii();
                if (adulti < 0) adulti = 0;
                total += adulti * 150.0;
            }
        } else if (eveniment instanceof Conferinta) {
            total -= ((Conferinta) eveniment).getBugetSponsori(); // scad bugetul din sponzorizair
        }

        return total;
    }

    public void verificaBuget(Eveniment eveniment) throws BugetDepasitException {
        double costTotal = calculeazaCostTotal(eveniment);
        double bugetClient = eveniment.getClient().getBuget();

        if (costTotal > bugetClient) {
            throw new BugetDepasitException("Buget depasit pentru '" + eveniment.getNume() +
                    "'! Cost Total Real: " + costTotal + " RON, Buget maxim alocat de Client: " + bugetClient + " RON.");
        } else {
            System.out.println("   [OK] Evenimentul se incadreaza in buget! Cost total: " + costTotal + " RON.");
        }
        AuditService.getInstance().logAction("Validare Buget Eveniment ID: " + eveniment.getId());
    }

    public void asociazaFurnizorLaEveniment(Eveniment eveniment, Furnizor furnizor) {
        EvenimentDbService.getInstance().asociazaFurnizor(eveniment.getId(), furnizor.getId());
        AuditService.getInstance().logAction("Asociere furnizor " + furnizor.getNume() + " la " + eveniment.getNume());
    }
}