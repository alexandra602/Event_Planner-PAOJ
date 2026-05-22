1\. Arhitectura și Modelele OOP (Stadiu: ✅ Finalizat)

* Definirea entităților: Ai creat clasele de bază (Eveniment, Client, Locatie, Furnizor, Persoana).
* Încapsulare: Toate atributele sunt private, accesate prin getteri și setteri.
* Moștenire și Clase Abstracte: Clasa abstractă Furnizor este extinsă de Fotograf, CandyBar, FirmaCatering, etc.
* Polimorfism: Ai suprascris corect metoda toString() în clasele copil pentru afișări personalizate.



2\. Baze de Date - JDBC (Stadiu: ✅ Finalizat)

* Conexiunea: Clasa Singleton DatabaseManager care comunică stabil cu Oracle.
* Interfețe și Generice: Interfața GenericService<T> care impune operațiile standard (CRUD).
* Serviciile de DB: Implementarea CRUD-ului pentru cele 4 clase obligatorii (ClientDbService, LocatieDbService, FurnizorDbService, EvenimentDbService).



3\. Fişiere și Audit (Stadiu: ✅ Finalizat)

* Serviciu de Audit: Clasa AuditService (Singleton) care înregistrează acțiunile.
* Persistența în CSV: Scrierea corectă pe rânduri a numelui acțiunii și a timestamp-ului în audit.csv.



4\. Logica de Business (Stadiu: 🟡 Urmează să fie asamblată)

* Unificarea: Clasa EventPlannerService trebuie să devină „creierul” care apelează atât baza de date, cât și serviciul de audit dintr-un singur loc.
* Calcule complexe: Funcție pentru calcularea costului total al unui eveniment (adunând prețul locației cu prețul fiecărui furnizor implicat, ținând cont de specificul lor polimorfic).
* Validări de Business: Funcție care verifică dacă costul total al evenimentului se încadrează în bugetul clientului.
* Asocieri: Logica prin care adăugăm efectiv un furnizor în lista de furnizori a unui eveniment.



5\. Tratarea Excepțiilor (Stadiu: ❌ Urmează)

* Excepții Custom: Crearea a 1-2 excepții specifice proiectului tău (de exemplu, BugetDepasitException sau LocatieIndisponibilaException).
* Securizarea aplicației: Folosirea blocurilor try-catch pentru a prinde erorile (ex: când utilizatorul tastează greșit în meniu).



6\. Interfața cu Utilizatorul - Meniul (Stadiu: ❌ Urmează)

* Meniul Interactiv: O buclă while(true) în clasa Main cu un bloc switch.
* Opțiunile din Consolă: Permiterea utilizatorului să adauge clienți, să vadă furnizori, să creeze evenimente și să le afișeze direct de la tastatură (folosind Scanner).

