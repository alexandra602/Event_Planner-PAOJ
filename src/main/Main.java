package main;
import java.util.Scanner;
import java.time.LocalDate;
import model.*;
import service.EventPlannerService;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        EventPlannerService service = new EventPlannerService();

        populeazaCatalog(service);

        boolean continua = true;
        System.out.print(" --- Bun venit la Event Planner Pro! --- ");

        // meniul interactiv
        while (continua) {
            System.out.println("\n   *** MENIU PRINCIPAL ***");
            System.out.println("      1. Adauga client nou");
            System.out.println("      2. Afiseaza toti clientii");
            System.out.println("      3. Afiseaza cei mai ieftini furnizori");
            System.out.println("      4. Creeaza o Petrecere Privata");
            System.out.println("      5. Calculeaza si valideaza bugetul unui eveniment");
            System.out.println("      0. Iesire");
            System.out.println("      Alege o optiune: ");

            int opt =  scanner.nextInt();
            scanner.nextLine();

            switch (opt) {
                case 1:
                    int id = service.getClienti().size() + 100;

                    System.out.println("Nume Client: ");
                    String nume =  scanner.nextLine();
                    System.out.println("Buget (RON): ");
                    double buget =  scanner.nextDouble();

                    Client c = new Client(id, nume, "07xx", "email@test.ro", buget);
                    service.adaugaClient(c);

                    System.out.println("Client adaugat cu succes! ID alocat: " + id);
                    break;
                case 2:
                    service.afiseazaClienti();
                    break;
                case 3:
                    service.filtreazaFurnizori(2000);
                    break;
                case 4:
                    int idEveniment = service.getEvenimente().size() + 1000;

                    System.out.println("Nume Eveniment: ");
                    String numeEveniment = scanner.nextLine();
                    System.out.println("Nr Invitati: ");
                    int nrInvitati = scanner.nextInt();
                    System.out.println("Nr Copii: ");
                    int nrCopii = scanner.nextInt();

                    Client cTest = new Client(99, "Client Test", "-", "-", 10000);
                    PetrecerePrivata pp = new PetrecerePrivata(idEveniment, numeEveniment, LocalDate.now(), nrInvitati, cTest, "Casual", nrCopii, true);
                    service.creeazaEveniment(pp);

                    service.asigneazaLocatie(idEveniment, new Locatie(20, "Ballroom Magic", "Bucuresti", 300, 5000, "Contact", "07xx"));
                    service.asociazaFurnizor(idEveniment, 1);
                    service.asociazaFurnizor(idEveniment, 2);
                    System.out.println("Locatie si 2 furnizori au fost alocati pentru testare");

                    System.out.println("Eveniment creat cu succes! ID alocat: " + idEveniment);
                    break;
                case 5:
                    System.out.println("ID Eveniment: ");
                    int idEv = scanner.nextInt();
                    service.valideazaBuget(idEv);
                    break;
                case 0:
                    System.out.println("   Se inchide aplicatia...");
                    continua = false;
                    break;
                default:
                    System.out.println("Optiune invalida!");
            }
        }
        scanner.close();
    }

    // adaug date in catalog
    private static void populeazaCatalog (EventPlannerService service) {
        Furnizor trupa = new TrupaMuzica(1, "Trupa Feelings", "07xx", "contact@feelings.ro", 4500, 4, "Pop", 3);
        Furnizor catering = new FirmaCatering(2, "Delicii SRL", "07xx", "office@delicii.com", 1000, 3.5, 150, "International", true);

        service.adaugaFurnizor(trupa);
        service.adaugaFurnizor(catering);

        Client client1 = new Client(10, "Zaharia Vasile", "07xx", "vasile@email.com", 20000);
        Client client2 = new Client(11, "Andreea Popescu", "07xx", "andreea@email.com", 15000);

        service.adaugaClient(client1);
        service.adaugaClient(client2);
    }
}
