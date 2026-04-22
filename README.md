# Event Planner 📅
O aplicație Java modernă și robustă concepută pentru gestionarea completă a evenimentelor (nunți, conferințe, petreceri private), punând accent pe conceptele avansate de Programare Orientată pe Obiecte (POO) și logica de business realistă.

## 📋 Despre Proiect
Acest sistem simulează activitatea unei agenții de planificare de evenimente. Obiectivul principal este corelarea nevoilor clienților cu resursele disponibile (locații, furnizori), asigurând în același timp validări de capacitate și calcule financiare precise. Proiectul este dezvoltat pentru disciplina Programare Avansată pe Obiecte (PAO).

## ✨ Funcționalități Principale
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
Proiectul demonstrează stăpânirea pilonilor fundamentali ai programării pe obiecte în Java:

* **Moștenire:** Utilizarea ierarhiilor complexe (ex: Persoana ➔ Client, Furnizor ➔ Fotograf).
* **Abstractizare:** Utilizarea clasei abstracte Furnizor pentru a asigura o structură comună, forțând în același timp specificitatea prin clasele derivate.
* **Compoziție:** Un eveniment "are" un client, o locație și o listă dinamică de furnizori (relația HAS-A).
* **Polimorfism:** Suprascrierea metodei toString() pentru afișarea detaliilor specifice fiecărui tip de furnizor sau eveniment.
* **Incapsulare:** Protejarea datelor prin modificatori de acces (private, protected) și expunerea lor securizată prin metode de tip Getter/Setter.

## 🧱 Structura Claselor (Ierarhie)

* **Model:** Persoana -> Client; Furnizor -> TrupaMuzica, Florist, FirmaCatering, Fotograf, CandyBar; Locatie; Eveniment -> PetrecerePrivata, Conferinta.
* **Service:** EventPlannerService.
* **Utils:** Integrare cu Java Time API (LocalDate) pentru gestionarea calendaristică.

## 👩‍💻 Autor
Panaet Maria Alexandra
