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
        System.out.println(" --- Bun venit la Event Planner Pro! --- ");

        // meniul interactiv
        while (continua) {
            System.out.println("\n   *** MENIU PRINCIPAL ***");
            System.out.println("      1. Adauga client nou");
            System.out.println("      2. Afiseaza toti clientii");
            System.out.println("      3. Afiseaza cei mai ieftini furnizori");
            System.out.println("      4. Creeaza o Petrecere Privata");
            System.out.println("      5. Calculeaza si valideaza bugetul unui eveniment");
            System.out.println("      6. Asigneaza o locatie la un eveniment");
            System.out.println("      7. Asociaza un furnizor la un eveniment");
            System.out.println("      0. Iesire");
            System.out.print("      Alege o optiune: ");

            int opt =  scanner.nextInt();
            scanner.nextLine();

            switch (opt) {
                case 1:
                    int id = service.getClienti().size() + 100;

                    System.out.print("Nume Client: ");
                    String nume =  scanner.nextLine();
                    System.out.print("Buget (RON): ");
                    double buget =  scanner.nextDouble();
                    scanner.nextLine();

                    System.out.print("Telefon: ");
                    String telefon =  scanner.nextLine();
                    System.out.print("Email: ");
                    String email =  scanner.nextLine();

                    Client c = new Client(id, nume, telefon, email, buget);
                    service.adaugaClient(c);

                    System.out.println("   Client adaugat cu succes! ID alocat: " + id);
                    break;
                case 2:
                    service.afiseazaClienti();
                    break;
                case 3:
                    System.out.print("Pretul maxim cautat: ");
                    double pret = scanner.nextDouble();
                    service.filtreazaFurnizori(pret);
                    break;
                case 4:
                    int idEveniment = service.getEvenimente().size() + 1000;

                    System.out.print("ID Client existent (vezi lista la opt. 2): ");
                    int idClient = scanner.nextInt();
                    scanner.nextLine();

                    Client clientGasit = null;
                    for (Client client : service.getClienti()) {
                        if (client.getId() == idClient) {
                            clientGasit = client;
                            break;
                        }
                    }

                    if (clientGasit == null) {
                        System.out.println("   Eroare: Clientul cu ID " + idClient + " nu exista!");
                        break;
                    }

                    System.out.print("Nume Eveniment: ");
                    String numeEveniment = scanner.nextLine();
                    System.out.print("Nr Invitati: ");
                    int nrInvitati = scanner.nextInt();
                    System.out.print("Nr Copii: ");
                    int nrCopii = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("Tematica: ");
                    String tematica = scanner.nextLine();
                    System.out.print("Doriti open bar? [da/nu] ");
                    String doriti = scanner.nextLine();

                    boolean openBar = doriti.equalsIgnoreCase("da");

                    PetrecerePrivata pp = new PetrecerePrivata(idEveniment, numeEveniment, LocalDate.now(), nrInvitati, clientGasit, tematica, nrCopii, openBar);
                    service.creeazaEveniment(pp);

                    System.out.println("   Eveniment creat cu succes! ID alocat: " + idEveniment);
                    break;
                case 5:
                    System.out.print("ID Eveniment: ");
                    int idEv = scanner.nextInt();
                    service.valideazaBuget(idEv);
                    break;
                case 6:
                    System.out.print("ID Eveniment pentru care cautati locatie: ");
                    int idEvLoc = scanner.nextInt();

                    System.out.println("\nIată catalogul agenției:");
                    service.afiseazaLocatii(); // afisez optiunile de locatii

                    System.out.print("ID-ul Locatiei alese: ");
                    int idLocatieAleasa = scanner.nextInt();

                    service.asigneazaLocatie(idEvLoc, idLocatieAleasa);
                    break;

                case 7:
                    System.out.print("ID Eveniment: ");
                    int idEvFur = scanner.nextInt();

                    System.out.println("\nIată catalogul partenerilor noștri:");
                    service.afiseazaTotiFurnizorii(); // afisez optiunile de furnizori

                    System.out.print("ID-ul Furnizorului dorit: ");
                    int idFur = scanner.nextInt();

                    service.asociazaFurnizor(idEvFur, idFur);
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
        // locatii
        service.adaugaLocatie(new Locatie(1, "Ballroom Magic", "Bucuresti", 300, 5000, "Ionut", "07xx"));
        service.adaugaLocatie(new Locatie(2, "Hotel Marriott", "Bucuresti", 500, 12000, "Maria", "07xx"));
        service.adaugaLocatie(new Locatie(3, "Restaurantul Din Padure", "Brasov", 100, 2000, "Vasile", "07xx"));

        // furnizori
        service.adaugaFurnizor(new TrupaMuzica(1, "Trupa Feelings", "07xx", "contact@feelings.ro", 4500, 4, "Pop-Rock", 3));
        service.adaugaFurnizor(new TrupaMuzica(2, "DJ Alex", "07xx", "dj@alex.ro", 1500, 1, "House", 1));
        service.adaugaFurnizor(new FirmaCatering(3, "Delicii SRL", "07xx", "office@delicii.com", 1000, 3.5, 150, "International", true));
        service.adaugaFurnizor(new FirmaCatering(4, "Cantina Studenteasca", "07xx", "cantina@stud.com", 300, 0, 70, "Traditional", false));
        service.adaugaFurnizor(new Fotograf(5, "Alex Foto", "07xx", "alex@foto.ro", 2500, 4.5, true, 250, 14));
        service.adaugaFurnizor(new CandyBar(6, "Sweet Tooth", "07xx", "dulce@bar.ro", 1200, 4, "Disney", true));

        // clienti
        Client c1 = new Client(101, "Zaharia Vasile", "0722111222", "vasile@email.com", 60000);
        Client c2 = new Client(102, "Andreea Popescu", "0733444555", "andreea@email.com", 15000);
        Client c3 = new Client(103, "Studentul Zgarcit", "0744999888", "zgarcit@email.com", 3000);
        service.adaugaClient(c1);
        service.adaugaClient(c2);
        service.adaugaClient(c3);

        // evenimente
        PetrecerePrivata nunta = new PetrecerePrivata(1000, "Nunta Vasile & Maria", LocalDate.now().plusMonths(2), 200, c1, "Eleganta", 15, true);
        service.creeazaEveniment(nunta);
        service.asigneazaLocatie(1000, 2);
        service.asociazaFurnizor(1000, 1);
        service.asociazaFurnizor(1000, 3);
        service.asociazaFurnizor(1000, 5);

        PetrecerePrivata majorat = new PetrecerePrivata(1001, "Majorat Andreea", LocalDate.now().plusWeeks(3), 50, c2, "Neon Party", 0, false);
        service.creeazaEveniment(majorat);
        service.asigneazaLocatie(1001, 1);
        service.asociazaFurnizor(1001, 2);
        service.asociazaFurnizor(1001, 6);
    }
}