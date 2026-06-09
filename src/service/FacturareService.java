package service;

import model.*;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FacturareService {
    private static FacturareService instance;

    private FacturareService() {}

    public static FacturareService getInstance() {
        if (instance == null) {
            instance = new FacturareService();
        }
        return instance;
    }

    public void genereazaFactura(Eveniment ev, double costTotal) {
        // generam un nume de fisier, ex: factura_NumeEveniment_1234.txt
        String fileName = "factura_" + ev.getNume().replaceAll("\\s+", "_") + "_" + ev.getId() + ".txt";

        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write("==================================================\n");
            writer.write("                     FACTURA\n");
            writer.write("==================================================\n");
            writer.write("\n");

            // detalii generice eveninment
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            writer.write("Data emiterii: " + LocalDateTime.now().format(formatter) + "\n");
            writer.write("ID Eveniment: " + ev.getId() + "\n");
            writer.write("Nume Eveniment: " + ev.getNume() + "\n");
            writer.write("Tip Eveniment: " + ev.getClass().getSimpleName() + "\n");
            writer.write("Numar Invitati: " + ev.getNrInvitati() + "\n");

            // datele clientului asociat
            if (ev.getClient() != null) {
                writer.write("\n--- DATE CLIENT ---\n");
                writer.write("Nume: " + ev.getClient().getNume() + "\n");
                writer.write("Telefon: " + ev.getClient().getTelefon() + "\n");
                writer.write("Email: " + ev.getClient().getEmail() + "\n");
            }

            // costurile asociate
            writer.write("\n--- DETALII COSTURI ---\n");

            if (ev.getLocatie() != null) {
                double pretLoc = ev.getLocatie().getPret();
                if (ev instanceof Conferinta) {
                    pretLoc = pretLoc * ((Conferinta) ev).getNrZile();
                    writer.write("Chirie Locatie (" + ev.getLocatie().getNume() + " x " + ((Conferinta) ev).getNrZile() + " zile): " + pretLoc + " RON\n");
                } else {
                    writer.write("Chirie Locatie (" + ev.getLocatie().getNume() + "): " + pretLoc + " RON\n");
                }
            } else {
                writer.write("Chirie Locatie: 0.0 RON (Locatie neasignata)\n");
            }

            if (ev.getFurnizori() != null && !ev.getFurnizori().isEmpty()) {
                writer.write("\nFurnizori asociati:\n");
                for (Furnizor f : ev.getFurnizori()) {
                    writer.write(" - " + f.getNume() + " [" + f.getClass().getSimpleName() + "]: " + f.getPret() + " RON (Taxa de baza)\n");
                }
            }

            if (ev instanceof PetrecerePrivata && ((PetrecerePrivata) ev).isOpenBar()) {
                writer.write("\nTaxa Open Bar: \n");
            }

            // daca e conferinta scad bugetul din sponzorizari
            if (ev instanceof Conferinta) {
                writer.write("\nReducere Buget Sponsori: -" + ((Conferinta) ev).getBugetSponsori() + " RON\n");
            }

            writer.write("\n");
            writer.write("--------------------------------------------------\n");
            writer.write("TOTAL DE PLATA: " + costTotal + " RON\n");
            writer.write("--------------------------------------------------\n");

            System.out.println("[OK] Factura a fost generata cu succes si salvata ca: " + fileName);
            AuditService.getInstance().logAction("Generare factura pentru eveniment ID: " + ev.getId());

        } catch (IOException e) {
            System.out.println("[!] Eroare la generarea facturii: " + e.getMessage());
        }
    }
}