package main;

import config.DatabaseManager;
import exception.BugetDepasitException;
import model.*;
import service.*;

import java.sql.SQLOutput;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        EventPlannerService plannerService = EventPlannerService.getInstance();

        // ma conectez la bd
        DatabaseManager.getConnection();

        while (true) {
            System.out.println("\n*** SISTEM MANAGEMENT EVENIMENTE ***");
            System.out.println("1. Adauga Client nou");
            System.out.println("2. Adauga Locatie noua");
            System.out.println("3. Adauga Furnizor");
            System.out.println("4. Planifica Eveniment nou");
            System.out.println("5. Valideaza Buget Eveniment");
            System.out.println("6. Asociaza Furnzior la Eveniment");
            System.out.println("7. Schimba / Asociaza Locatie la Eveniment");
            System.out.println("8. Genereaza Factura Eveniment");
            System.out.println("9. Afiseaza toate Evenimentele");
            System.out.println("10. Anulare Eveniment");
            System.out.println("11. Actualizare Buget Client");
            System.out.println("0. Iesire");
            System.out.print("Selecteaza optiunea: ");

            String optStr = scanner.nextLine();
            int opt;
            try {
                opt = Integer.parseInt(optStr);
            } catch (NumberFormatException e) {
                System.out.println("[!] Introdu un numar valid.");
                continue;
            }

            if (opt == 0) {
                DatabaseManager.closeConnection();
                System.out.println("[Sistem] Aplicatie inchisa cu succes. O zi buna!");
                break;
            }

            switch (opt) {
                case 1:
                    System.out.println("\n--- ADAUGARE CLIENT ---");
                    try {
                        int idClient = ThreadLocalRandom.current().nextInt(1000, 2000); // generare automata ID - interval rezervat pentru clienti
                        System.out.print("Nume complet: "); String nume = scanner.nextLine();
                        System.out.print("Numar telefon: "); String tel = scanner.nextLine();
                        System.out.print("Adresa email: "); String email = scanner.nextLine();
                        System.out.print("Buget maxim disponibil (RON): "); double buget = Double.parseDouble(scanner.nextLine());

                        plannerService.inregistreazaClient(new Client(idClient, nume, tel, email, buget));
                    } catch (Exception e) {
                        System.out.println("[!] Eroare: Ai introdus date invalide.");
                    }
                    break;

                case 2:
                    System.out.println("\n--- ADAUGARE LOCAȚIE ---");
                    try {
                        int idLocatie = ThreadLocalRandom.current().nextInt(2000, 3000); // generare automata ID - interval rezervat pentru locatii
                        System.out.print("Denumire locatie: "); String nume = scanner.nextLine();
                        System.out.print("Oras / Adresa: "); String adr = scanner.nextLine();
                        System.out.print("Capacitate maxima (numar persoane): "); int cap = Integer.parseInt(scanner.nextLine());
                        System.out.print("Pret inchiriere/zi (RON): "); double pret = Double.parseDouble(scanner.nextLine());
                        System.out.print("Nume persoana de contact: "); String mng = scanner.nextLine();
                        System.out.print("Telefon persoana de contact: "); String telC = scanner.nextLine();

                        plannerService.inregistreazaLocatie(new Locatie(idLocatie, nume, adr, cap, pret, mng, telC));
                    } catch (Exception e) {
                        System.out.println("[!] Eroare: Date numerice incorecte.");
                    }
                    break;
                case 3:
                    System.out.println("\n--- ADAUGARE FURNIZOR NOU ---");
                    try {
                        int idFur = java.util.concurrent.ThreadLocalRandom.current().nextInt(3000, 4000); // generare automata ID - interval rezervat pentru furnizori
                        System.out.print("Nume Furnizor / Companie: "); String numeF = scanner.nextLine();
                        System.out.print("Numar telefon: "); String telF = scanner.nextLine();
                        System.out.print("Adresa email: "); String emailF = scanner.nextLine();
                        System.out.print("Pret de baza / Onorariu (RON): "); double pretF = Double.parseDouble(scanner.nextLine());
                        System.out.print("Rating initial (1.0 - 5.0): "); double ratingF = Double.parseDouble(scanner.nextLine());

                        // aflu tipul furnizorului
                        System.out.println("\nSelecteaza tipul de furnizor:");
                        System.out.println("  1. Trupa Muzica");
                        System.out.println("  2. Fotograf");
                        System.out.println("  3. Firma Catering");
                        System.out.println("  4. Candy Bar");
                        System.out.println("  5. Florist");
                        System.out.print("Alege tip (1-5): ");
                        int tipF = Integer.parseInt(scanner.nextLine());

                        Furnizor nouFurnizor = null;

                        // construiesc obiectul dupa tipul real
                        if (tipF == 1) { // trupa muzica
                            System.out.print("Gen muzical abordat: "); String gen = scanner.nextLine();
                            System.out.print("Numar membri trupa: "); int membri = Integer.parseInt(scanner.nextLine());
                            nouFurnizor = new TrupaMuzica(idFur, numeF, telF, emailF, pretF, ratingF, gen, membri);
                        }
                        else if (tipF == 2) { // fotograf
                            System.out.print("Ofera si servicii video? (true/false): "); boolean vid = Boolean.parseBoolean(scanner.nextLine());
                            System.out.print("Preț album fizic extra (RON): "); double alb = Double.parseDouble(scanner.nextLine());
                            System.out.print("Timp livrare materiale (zile): "); int timp = Integer.parseInt(scanner.nextLine());
                            nouFurnizor = new Fotograf(idFur, numeF, telF, emailF, pretF, ratingF, vid, alb, timp);
                        }
                        else if (tipF == 3) { // firma catering
                            System.out.print("Pret standard per meniu (RON): "); double pMeniu = Double.parseDouble(scanner.nextLine());
                            System.out.print("Specific culinar (ex: Italian, Traditional): "); String spec = scanner.nextLine();
                            System.out.print("Ofera meniuri speciale/vegane? (true/false): "); boolean specM = Boolean.parseBoolean(scanner.nextLine());
                            nouFurnizor = new FirmaCatering(idFur, numeF, telF, emailF, pretF, ratingF, pMeniu, spec, specM);
                        }
                        else if (tipF == 4) { // candy bar
                            System.out.print("Tematica Candy Bar (ex: Disney, Vintage): "); String tematicaCandy = scanner.nextLine();
                            System.out.print("Ofera optiuni vegane? (true/false): "); boolean vegan = Boolean.parseBoolean(scanner.nextLine());
                            nouFurnizor = new CandyBar(idFur, numeF, telF, emailF, pretF, ratingF, tematicaCandy, vegan);
                        }
                        else if (tipF == 5) { // florist
                            System.out.print("Cost per aranjament (RON): "); double costAranj = Double.parseDouble(scanner.nextLine());
                            System.out.print("Flori disponibile (separate prin virgula, ex: Trandafiri, Lalele): ");
                            String floriInput = scanner.nextLine();
                            // transofrm textul introdus intr-o lista de string-uri
                            java.util.List<String> listaFlori = java.util.Arrays.asList(floriInput.split("\\s*,\\s*"));
                            nouFurnizor = new Florist(idFur, numeF, telF, emailF, pretF, ratingF, costAranj, listaFlori);
                        }
                        else {
                            System.out.println("[!] Tip de furnizor invalid.");
                            break;
                        }

                        plannerService.inregistreazaFurnizor(nouFurnizor);

                    } catch (Exception e) {
                        System.out.println("[!] Eroare: Date introduse incorect.");
                    }
                    break;
                case 4:
                    System.out.println("\n--- PLANIFICARE EVENIMENT ---");
                    try {
                        // clientul pe care il asociez evenimentului - obligatoriu
                        List<Client> clientiDB = ClientDbService.getInstance().citeste();
                        if (clientiDB.isEmpty()) {
                            System.out.println("[!] Nu exista clienti in baza de date. Adauga un client mai intai (Optiunea 1).");
                            break;
                        }

                        System.out.println("\nClienti disponibili:");
                        clientiDB.forEach(c -> System.out.println("  ID: " + c.getId() + " | Nume: " + c.getNume() + " | Buget: " + c.getBuget() + " RON"));
                        System.out.print("Introdu ID-ul clientului dorit: ");
                        int idC = Integer.parseInt(scanner.nextLine());
                        Client clientAles = clientiDB.stream().filter(c -> c.getId() == idC).findFirst().orElse(null);
                        if (clientAles == null) { System.out.println("[!] ID client invalid."); break; }

                        // locatia asociata - poate lipsi
                        List<Locatie> locatiiDB = LocatieDbService.getInstance().citeste();
                        Locatie locatieAleasa = null;
                        if (locatiiDB.isEmpty()) {
                            System.out.println("[!] Nu exista locatii in baza de date. Adauga una mai intai (Optiunea 2).");
                            break;
                        }

                        System.out.println("\nLocatii disponibile:");
                        locatiiDB.forEach(l -> System.out.println("  ID: " + l.getId() + " | Nume: " + l.getNume() + " | Capacitate: " + l.getCapacitate() + " locuri"));
                        System.out.print("Introdu ID-ul locatiei dorite (sau 0 pentru a o asigna mai tarziu): ");
                        int idL = Integer.parseInt(scanner.nextLine());
                        if (idL != 0) {
                            locatieAleasa = locatiiDB.stream().filter(l -> l.getId() == idL).findFirst().orElse(null);
                            if (locatieAleasa == null) {
                                System.out.println("[!] ID locatie invalid.");
                                break;
                            }
                        }

                        System.out.println("\nDetalii Eveniment:");
                        int idEv = ThreadLocalRandom.current().nextInt(4000, 5000); // generare automata ID - interval rezervat pentru evenimente
                        System.out.print("Numele evenimentului: "); String numeEv = scanner.nextLine();
                        System.out.print("Numar total de invitati: "); int invitati = Integer.parseInt(scanner.nextLine());

                        if (locatieAleasa != null && invitati > locatieAleasa.getCapacitate()) {
                            throw new exception.CapacitateDepasitaException("Capacitate depasita! Sala " + locatieAleasa.getNume() + " suportă maxim " + locatieAleasa.getCapacitate() + " persoane.");
                        }

                        System.out.print("Tip eveniment (1 = Petrecere Privata, 2 = Conferinta): ");
                        int tip = Integer.parseInt(scanner.nextLine());
                        Eveniment ev = null;

                        // construiesc obiectul cu tipul real
                        if (tip == 1) { // petrecere privata
                            System.out.print("Tematica: "); String tematica = scanner.nextLine();
                            System.out.print("Numar copii participanti: "); int nrCopii = Integer.parseInt(scanner.nextLine());
                            System.out.print("Open Bar (true/false): "); boolean ob = Boolean.parseBoolean(scanner.nextLine());
                            ev = new PetrecerePrivata(idEv, numeEv, LocalDate.now(), invitati, clientAles, tematica, nrCopii, ob);
                        } else if (tip == 2) { // conferinta
                            System.out.print("Domeniu (ex: IT, Medical): "); String domeniu = scanner.nextLine();
                            System.out.print("Numar Speakeri: "); int sp = Integer.parseInt(scanner.nextLine());
                            System.out.print("Durata (numar zile): "); int zile = Integer.parseInt(scanner.nextLine());
                            System.out.print("Buget primit de la sponsori (RON): "); double spns = Double.parseDouble(scanner.nextLine());
                            ev = new Conferinta(idEv, numeEv, LocalDate.now(), invitati, clientAles, domeniu, sp, zile, spns);
                        } else {
                            System.out.println("[!] Tip invalid.");
                            break;
                        }

                        ev.setLocatie(locatieAleasa);
                        ev.setStatus(StatusEveniment.CONFIRMAT);
                        plannerService.inregistreazaEveniment(ev);

                    } catch (exception.CapacitateDepasitaException e) {
                        System.out.println("[!] Eroare: Capacitate depasita! " +  e.getMessage());
                    }
                    catch (Exception e) {
                        System.out.println("[!] Eroare: Date introduse incorect.");
                    }
                    break;
                case 5:
                    System.out.println("\n--- VALIDARE BUGET EVENIMENT ---");
                    try {
                        // afisez doar evenimentele care nu sunt anulate
                        List<Eveniment> evenimente = EvenimentDbService.getInstance().citeste().stream()
                                .filter(eveniment -> eveniment.getStatus() != StatusEveniment.ANULAT)
                                .toList();
                        if (evenimente.isEmpty()) {
                            System.out.println("[!] Nu exista evenimente. Creeaza unul intai (Optiunea 3).");
                            break;
                        }

                        System.out.println("Evenimente active:");
                        evenimente.forEach(e -> System.out.println("  ID: " + e.getId() + " | Nume: " + e.getNume() + " | Client: " + (e.getClient() != null ? e.getClient().getNume() : "Lipsa")));

                        System.out.print("\nIntrodu ID-ul evenimentului pe care vrei sa-l validezi: ");
                        int idC = Integer.parseInt(scanner.nextLine());
                        Eveniment evGasit = evenimente.stream().filter(e -> e.getId() == idC).findFirst().orElse(null);

                        if (evGasit != null) {
                            plannerService.verificaBuget(evGasit);
                        } else {
                            System.out.println("[!] Evenimentul nu a fost gasit.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("[!] Eroare: ID numeric incorect.");
                    } catch (BugetDepasitException e) {
                        System.out.println("[!] Eroare: Buget depasit.");
                        System.out.println(e.getMessage());
                    }
                    break;
                case 6:
                    System.out.println("\n--- ASOCIERE FURNIZOR LA EVENIMENT ---");
                    try {
                        // aleg evenimentul
                        List<Eveniment> evenimente = EvenimentDbService.getInstance().citeste().stream()
                                .filter(eveniment -> eveniment.getStatus() != StatusEveniment.ANULAT)
                                .toList();
                        if (evenimente.isEmpty()) {
                            System.out.println("[!] Nu exista evenimente. Creeaza unul intai (Optiunea 3).");
                            break;
                        }
                        System.out.println("Evenimente active:");
                        evenimente.forEach(e -> System.out.println("  ID: " + e.getId() + " | Nume: " + e.getNume()));
                        System.out.print("Introdu ID Eveniment: ");
                        int idEvAles = Integer.parseInt(scanner.nextLine());
                        Eveniment evAles = evenimente.stream().filter(e -> e.getId() == idEvAles).findFirst().orElse(null);
                        if (evAles == null) { System.out.println("[!] ID Eveniment invalid."); break; }

                        // aleg furnizorul
                        List<Furnizor> furnizoriDisp = FurnizorDbService.getInstance().citeste();
                        if (furnizoriDisp.isEmpty()) {
                            System.out.println("[!] Nu exista furnizori in baza de date.");
                            break;
                        }
                        System.out.println("\nFurnizori disponibili:");
                        furnizoriDisp.forEach(f -> System.out.println("  ID: " + f.getId() + " | Nume: " + f.getNume() + " | Tip: " + f.getClass().getSimpleName() + " | Pret: " + f.getPret()));
                        System.out.print("Introdu ID Furnizor: ");
                        int idFurAles = Integer.parseInt(scanner.nextLine());
                        Furnizor furAles = furnizoriDisp.stream().filter(f -> f.getId() == idFurAles).findFirst().orElse(null);
                        if (furAles == null) { System.out.println("[!] ID Furnizor invalid."); break; }

                        // salvez asocierea
                        plannerService.asociazaFurnizorLaEveniment(evAles, furAles);

                    } catch (NumberFormatException e) {
                        System.out.println("[!] Te rog introdu un numar valid.");
                    }
                    break;
                case 7:
                    System.out.println("\n--- SCHIMBARE / ASIGNARE LOCAȚIE EVENIMENT ---");
                    try {
                        // citesc si afisez detaliile evenimentelor din bd
                        List<Eveniment> evenimente = EvenimentDbService.getInstance().citeste().stream()
                                .filter(eveniment -> eveniment.getStatus() != StatusEveniment.ANULAT)
                                .toList();
                        if (evenimente.isEmpty()) {
                            System.out.println("[!] Nu exista evenimente. Creeaza unul intai (Optiunea 3).");
                            break;
                        }
                        System.out.println("Evenimente active:");
                        evenimente.forEach(e -> System.out.println("  ID: " + e.getId() + " | Nume: " + e.getNume() +
                                " | Locatie curenta: " + (e.getLocatie() != null ? e.getLocatie().getNume() : "Niciuna")));

                        System.out.print("\nIntrodu ID-ul evenimentului pe care vrei sa-l modifici: ");
                        int idEv = Integer.parseInt(scanner.nextLine());
                        Eveniment evGasit = evenimente.stream().filter(e -> e.getId() == idEv).findFirst().orElse(null);
                        if (evGasit == null) { System.out.println("[!] Evenimentul nu a fost gasit."); break; }

                        // afisez locatiile disponibile pentru alegere
                        List<Locatie> locatii = LocatieDbService.getInstance().citeste();
                        System.out.println("\nLocatii disponibile in baza de date:");
                        locatii.forEach(l -> System.out.println("  ID: " + l.getId() + " | Nume: " + l.getNume() + " (Capacitate: " + l.getCapacitate() + ")"));

                        System.out.print("\nIntrodu ID-ul noii locatii alese: ");
                        int idLoc = Integer.parseInt(scanner.nextLine());
                        Locatie locAleasa = locatii.stream().filter(l -> l.getId() == idLoc).findFirst().orElse(null);
                        if (locAleasa == null) { System.out.println("[!] Locatia selectata nu exista."); break; }

                        // validarea capacitatii
                        if (evGasit.getNrInvitati() > locAleasa.getCapacitate()) {
                            throw new exception.CapacitateDepasitaException("Capacitate depasita! Sala " + locAleasa.getNume() + " suporta maxim " + locAleasa.getCapacitate() + " persoane.");
                        }

                        // actualizez obiectul
                        evGasit.setLocatie(locAleasa);
                        EvenimentDbService.getInstance().actualizeaza(evGasit);
                        System.out.println("[DB] Locatia a fost actualizata cu succes pentru evenimentul '" + evGasit.getNume() + "'!");

                    } catch (exception.CapacitateDepasitaException e) {
                        System.out.println("[!] Eroare: Capacitate depasita! " + e.getMessage());
                    }
                    catch (NumberFormatException e) {
                        System.out.println("[!] Te rog sa introduci un ID numeric valid.");
                    } catch (Exception e) {
                        System.out.println("[!] Eroare la procesarea solicitarii.");
                    }
                    break;
                case 8:
                    System.out.println("\n--- GENERARE FACTURA EVENIMENT ---");
                    try {
                        // afisez evenimentele active
                        List<Eveniment> evenimente = EvenimentDbService.getInstance().citeste().stream()
                                .filter(eveniment -> eveniment.getStatus() != StatusEveniment.ANULAT)
                                .toList();
                        if (evenimente.isEmpty()) {
                            System.out.println("[!] Nu exista evenimente in baza de date.");
                            break;
                        }

                        System.out.println("Evenimente active:");
                        evenimente.forEach(e -> System.out.println("  ID: " + e.getId() + " | Nume: " + e.getNume() +
                                " | Locatie curenta: " + (e.getLocatie() != null ? e.getLocatie().getNume() : "Niciuna")));

                        System.out.print("\nIntrodu ID-ul evenimentului pentru care vrei sa emiti factura: ");
                        int idFactura = Integer.parseInt(scanner.nextLine());
                        Eveniment evFactura = evenimente.stream().filter(e -> e.getId() == idFactura).findFirst().orElse(null);

                        if (evFactura != null) {
                            // calculam pretul real
                            double costFinal = plannerService.calculeazaCostTotal(evFactura);
                            // trimitem datele
                            FacturareService.getInstance().genereazaFactura(evFactura, costFinal);
                        } else {
                            System.out.println("[!] Evenimentul nu a fost gssit.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("[!] Eroare: ID numeric incorect.");
                    }
                    break;
                case 9:
                    System.out.println("\n--- LISTA EVENIMENTE ---");
                    java.util.List<Eveniment> evenimente = EvenimentDbService.getInstance().citeste();

                    // afisez toate evenimebtele (indiferent de status) din bd
                    if (evenimente.isEmpty()) {
                        System.out.println("Nu exista evenimente. Creeaza unul intai (Optiunea 3).");
                    } else {
                        System.out.println("Am gasit " + evenimente.size() + " evenimente:");
                        for (Eveniment e : evenimente) {
                            String numeLoc = (e.getLocatie() != null) ? e.getLocatie().getNume() : "Fara locație";
                            String numeCl = e.getClient().getNume();
                            System.out.println(" -> ID: " + e.getId() + " | " + e.getNume() + " [" + e.getClass().getSimpleName() +
                                    "] | Client: " + numeCl + " | Locatie: " + numeLoc + " | Status: " + e.getStatus());
                        }
                    }
                    break;
                case 10:
                    System.out.println("\n--- ANULARE EVENIMENT ---");
                    List<Eveniment> evenimenteDeAnulat = EvenimentDbService.getInstance().citeste().stream()
                            .filter(e -> e.getStatus() != StatusEveniment.ANULAT)
                            .toList(); // afisam doar ce nu e deja anulat

                    if (evenimenteDeAnulat.isEmpty()) {
                        System.out.println("[!] Nu exista evenimente active care pot fi anulate.");
                        break;
                    }

                    evenimenteDeAnulat.forEach(e -> System.out.println("  ID: " + e.getId() + " | " + e.getNume() + " | Status: " + e.getStatus()));
                    System.out.print("\nIntrodu ID-ul evenimentului pe care vrei sa-l anulezi: ");

                    try {
                        int idAnulare = Integer.parseInt(scanner.nextLine());
                        Eveniment evDeAnulat = evenimenteDeAnulat.stream().filter(e -> e.getId() == idAnulare).findFirst().orElse(null);

                        // setez statusul la anulat si actualizez obiectul
                        if (evDeAnulat != null) {
                            evDeAnulat.setStatus(StatusEveniment.ANULAT);
                            EvenimentDbService.getInstance().actualizeaza(evDeAnulat); // update la bd
                            AuditService.getInstance().logAction("Eveniment anulat: " + evDeAnulat.getId());
                            System.out.println("[OK] Evenimentul a fost anulat cu succes!");
                        } else {
                            System.out.println("[!] ID invalid.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("[!] Eroare: Introdu un numar valid.");
                    }
                    break;
                case 11:
                    System.out.println("\n--- ACTUALIZARE BUGET CLIENT ---");
                    try {
                        List<Client> clientiDB = ClientDbService.getInstance().citeste();
                        if (clientiDB.isEmpty()) {
                            System.out.println("[!] Nu exista clienti. Creeaza unul intai (Optiunea 1).");
                            break;
                        }

                        System.out.println("Clienti disponibili:");
                        clientiDB.forEach(c -> System.out.println("  ID: " + c.getId() + " | Nume: " + c.getNume() + " | Buget curent: " + c.getBuget() + " RON"));

                        System.out.print("\nIntrodu ID-ul clientului pe care vrei sa-l actualizezi: ");
                        int idCUpdate = Integer.parseInt(scanner.nextLine());
                        Client clientDeUpdatat = clientiDB.stream().filter(c -> c.getId() == idCUpdate).findFirst().orElse(null);

                        if (clientDeUpdatat != null) {
                            System.out.print("Introdu noul buget maxim disponibil (RON): ");
                            double bugetNou = Double.parseDouble(scanner.nextLine());

                            // modific obiectul si trimit update ul in bd
                            clientDeUpdatat.setBuget(bugetNou);
                            ClientDbService.getInstance().actualizeaza(clientDeUpdatat);

                            AuditService.getInstance().logAction("Update buget Client ID: " + clientDeUpdatat.getId() + " la " + bugetNou + " RON");
                            System.out.println("[OK] Bugetul a fost actualizat cu succes pentru '" + clientDeUpdatat.getNume() + "'!");
                        } else {
                            System.out.println("[!] Clientul nu a fost gasit.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("[!] Eroare: Te rog sa introduci o valoare numerica valida.");
                    } catch (Exception e) {
                        System.out.println("[!] Eroare la procesarea bazei de date.");
                    }
                    break;
                default:
                    System.out.println("[!] Optiune invalida. Te rog alege un numar din meniu.");
            }
        }
    }
}