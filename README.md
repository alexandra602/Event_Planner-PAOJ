# Event Planner 📅
O aplicație Java modernă și robustă concepută pentru gestionarea completă a evenimentelor (nunți, conferințe, petreceri private), punând accent pe conceptele avansate de Programare Orientată pe Obiecte (POO) și logica de business realistă. Proiectul integrează persistența datelor într-o bază de date Oracle (JDBC), lucrul cu fișiere (I/O), tratarea excepțiilor și un flux logic de business.

## 📋 Despre Proiect
Acest sistem simulează activitatea unei agenții de planificare de evenimente. Obiectivul principal este corelarea nevoilor clienților cu resursele disponibile (locații, furnizori), asigurând în același timp validări de capacitate, calcule financiare precise persistență securizată a datelor și generarea automată a documentelor contabile. Proiectul este dezvoltat pentru disciplina Programare Avansată pe Obiecte (PAO).

## ✨ Funcționalități Principale
### 🗄️ Persistență și Arhitectură (Nivel PAO) 

* **Baze de Date Oracle (JDBC):** Sistem CRUD complet funcțional pentru clienți, locații, furnizori și evenimente.

* **Design Patterns:** Utilizarea pattern-ului Singleton pentru gestionarea conexiunii la baza de date (DatabaseManager) și a serviciilor, plus Generics (GenericService<T>) pentru un cod decuplat și reutilizabil.

* **Audit (CSV):** Sistem automat de logare a acțiunilor critice (adăugări, actualizări buget, facturare) într-un fișier audit.csv.

* **Smart ID Prefixing:** Generarea ID-urilor în intervale predefinite (ex: 1000+ pentru Clienți, 2000+ pentru Furnizori) pentru trasabilitate și debugging vizual instantaneu.

### 💼 Logica de Business Avansată (State Machine) 

* **Validări Financiare Stricte:** Calculul polimorfic al costului total (chirie locație + tarife dinamice furnizori) în raport cu bugetul alocat de client.

* **Actualizări Automate:** Trecerea automată a evenimentelor în stadiul de **IN_ASTEPTARE** sau **CONFIRMAT** în funcție de validarea bugetului financiar.

* **Soft Delete:** Protejarea integrității bazei de date și a facturilor istorice prin evitarea ștergerii definitive; evenimentele anulate trec într-un status logic **ANULAT**.

* **File I/O (Facturare):** Generarea de bonuri/facturi detaliate în format .txt, cu timestamp-uri generate prin java.time (Java 8 Time API).
  
### 👥 Gestiune Clienți și Locații

* **Sistem de Profile:** Gestionarea clienților cu bugete specifice.
* **Management Locații:** Validarea automată a capacității sălii în funcție de numărul de invitați și calculul chiriei.

### 🎭 Management Furnizori (Catalog Diversificat)

* **Servicii Multiple:** Integrarea de artiști (Trupe Muzică), catering, floriști, fotografi și Candy Bar-uri.
* **Logică de Preț Complexă:** Distincție clară între taxe fixe (logistică/montaj) și costuri variabile (per invitat/pachet extra).

### 📈 Logica de Evenimente

* **Petreceri Private:** Personalizare cu tematici, calcul special pentru meniuri de copii și gestionare Open Bar.
* **Conferințe Corporate:** Suport pentru evenimente multi-zi, gestiune speakeri și integrarea bugetelor de sponsorizare.
* **Workflow:** Urmărirea statusului (In așteptare, Confirmat, Anulat).

## 🛠️ Concepte POO Aplicate

* **Moștenire:** Utilizarea ierarhiilor complexe (ex: Persoana ➔ Client, Furnizor ➔ Fotograf).
* **Abstractizare:** Utilizarea clasei abstracte Furnizor pentru a asigura o structură comună, forțând în același timp specificitatea prin clasele derivate.
* **Compoziție:** Un eveniment "are" un client, o locație și o listă dinamică de furnizori (relația HAS-A).
* **Polimorfism:** Suprascrierea metodei toString() pentru afișarea detaliilor specifice fiecărui tip de furnizor sau eveniment.
* **Incapsulare:** Protejarea datelor prin modificatori de acces (private, protected) și expunerea lor securizată prin metode de tip Getter/Setter.
* **Java Streams & Lambdas:** Folosite extensiv pentru filtrarea datelor în memorie (ex: extragerea evenimentelor active).
* **Tratarea Excepțiilor:** Crearea și gestionarea de excepții custom (**BugetDepasitException**, **CapacitateDepasitaException**) pentru a bloca stările invalide fără a opri execuția programului.

## 🧱 Structura Proiectului

* **Model:** Pachetul conține entitățile sistemului (Persoana -> Client; Furnizor -> TrupaMuzica, Florist, FirmaCatering, Fotograf, CandyBar; Locatie; Eveniment -> PetrecerePrivata, Conferinta).
* **Service:** EventPlannerService, clasele de DB tip *DbService, FacturareService, AuditService.
* **Exception:** Definirea erorilor custom de business.
* **Config:** Gestionarea stabilă a conexiunii la baza de date.

## 👩‍💻 Autor
Panaet Maria Alexandra
