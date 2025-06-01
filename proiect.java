import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Objects;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.InputMismatchException;
import java.time.format.DateTimeParseException;

class Carte {
    protected String nume;
    protected Autor autor;
    protected Sectiune sectiune;
    protected int anPublicatie;
    protected boolean esteDisponibil;

    public Carte(String nume, Autor autor, Sectiune sectiune, int anPublicatie) {
        setNume(nume);
        setAutor(autor);
        setSectiune(sectiune);
        setAnPublicatie(anPublicatie);
        this.esteDisponibil = true; // cartea este disponibila la crearea obiectului
    }

    public void setNume(String nume) {
        if (nume == null || nume.isEmpty()) {
            throw new IllegalArgumentException("Numele nu poate fi null sau gol.");
        }
        this.nume = nume;
    }

    public String getNume() {
        return nume;
    }

    public void setAutor(Autor autor) {
        this.autor = Objects.requireNonNull(autor, "Autorul nu poate fi null.");
    }

    public Autor getAutor() {
        return autor;
    }

    public void setSectiune(Sectiune sectiune) {
        this.sectiune = Objects.requireNonNull(sectiune, "Sectiunea nu poate fi null.");
    }

    public Sectiune getSectiune() {
        return sectiune;
    }

    public void setAnPublicatie(int anPublicatie) {
        if (anPublicatie <= 0) {
            throw new IllegalArgumentException("Anul publicatiei trebuie sa fie un numar pozitiv.");
        }
        this.anPublicatie = anPublicatie;
    }

    public int getAnPublicatie() {
        return anPublicatie;
    }

    public void setEsteDisponibil(boolean esteDisponibil) {
        this.esteDisponibil = esteDisponibil;
    }

    public boolean esteDisponibil() {
        return esteDisponibil;
    }

    // builder
    protected Carte(Builder<?> builder) {
        this.nume = builder.nume;
        this.autor = builder.autor;
        this.sectiune = builder.sectiune;
        this.anPublicatie = builder.anPublicatie;
        this.esteDisponibil = builder.esteDisponibil;
    }

    public static class Builder<T extends Builder<T>> {
        protected String nume;
        protected Autor autor;
        protected Sectiune sectiune;
        protected int anPublicatie;
        protected boolean esteDisponibil = true;

        public T setNume(String nume) {
            this.nume = nume;
            return self();
        }

        public T setAutor(Autor autor) {
            this.autor = autor;
            return self();
        }

        public T setSectiune(Sectiune sectiune) {
            this.sectiune = sectiune;
            return self();
        }

        public T setAnPublicatie(int anPublicatie) {
            this.anPublicatie = anPublicatie;
            return self();
        }

        public T setEsteDisponibil(boolean esteDisponibil) {
            this.esteDisponibil = esteDisponibil;
            return self();
        }

        @SuppressWarnings("unchecked")
        protected T self() {
            return (T) this;
        }

        public Carte build() {
            return new Carte(this);
        }
    }

}


class Autor {
    private String nume;
    private String prenume;
    private String nationalitate;
    private List<Carte> cartiPublicate = new ArrayList<>();

    public Autor(String prenume, String nume, String nationalitate, List<Carte> cartiExistente) {
        setPrenume(prenume);
        setNume(nume);
        setNationalitate(nationalitate);

        if (cartiExistente != null) {
            this.cartiPublicate = new ArrayList<>(cartiExistente);
            this.cartiPublicate.forEach(carte -> carte.setAutor(this));
        } else {
            this.cartiPublicate = new ArrayList<>();
        }
    }

    public Autor(String prenume, String nume, String nationalitate) {
        this(prenume, nume, nationalitate, null);
    }

    public void setNume(String nume) {
        if (nume == null || nume.isEmpty()) {
            throw new IllegalArgumentException("Numele nu poate fi null sau gol.");
        }
        this.nume = nume;
    }

    public String getNume() {
        return nume;
    }

    public void setPrenume(String prenume) {
        if (prenume == null || prenume.isEmpty()) {
            throw new IllegalArgumentException("Prenumele nu poate fi null sau gol.");
        }
        this.prenume = prenume;
    }

    public String getPrenume() {
        return prenume;
    }

    public void setNationalitate(String nationalitate) {
        if (nationalitate == null || nationalitate.isEmpty()) {
            throw new IllegalArgumentException("Nationalitatea nu poate fi null sau gol.");
        }
        this.nationalitate = nationalitate;
    }

    public String getNationalitate() {
        return nationalitate;
    }

    public void adaugaCarte(Carte carte) {
        Objects.requireNonNull(carte, "Cartea nu poate fi null.");
        if (!this.cartiPublicate.contains(carte)) {
            this.cartiPublicate.add(carte);
            carte.setAutor(this);
        }
    }

    public List<Carte> getCartiPublicate() {
        return new ArrayList<>(cartiPublicate);
    }

    public void removeCarte(Carte carte) {
        if (this.cartiPublicate.contains(carte)) {
            this.cartiPublicate.remove(carte);
            carte.setAutor(null);
        }
    }

}


class Cititor {
    private String nume;
    private String prenume;
    private int idCititor;
    private String parola;
    private List<Carte> cartiImprumutate;

    public Cititor(String nume, String prenume, int idCititor, String parola, List<Carte> cartiImprumutate) {
        setNume(nume);
        setPrenume(prenume);
        setIdCititor(idCititor);
        if (cartiImprumutate != null) {
            this.cartiImprumutate = new ArrayList<>(cartiImprumutate);
            this.cartiImprumutate.forEach(carte -> carte.setEsteDisponibil(false));
        } else {
            this.cartiImprumutate = new ArrayList<>();
        }
        setParola(parola);
    }

    public Cititor(String nume, String prenume, int idCititor, String parola) {
        this(nume, prenume, idCititor, parola, null);
    }

    public void setNume(String nume) {
        if (nume == null || nume.isEmpty()) {
            throw new IllegalArgumentException("Numele nu poate fi null sau gol.");
        }
        this.nume = nume;
    }

    public String getNume() {
        return nume;
    }

    public void setPrenume(String prenume) {
        if (prenume == null || prenume.isEmpty()) {
            throw new IllegalArgumentException("Prenumele nu poate fi null sau gol.");
        }
        this.prenume = prenume;
    }

    public String getPrenume() {
        return prenume;
    }

    public void setIdCititor(int idCititor) {
        if (idCititor <= 0) {
            throw new IllegalArgumentException("ID-ul cititorului trebuie sa fie un numar pozitiv.");
        }
        this.idCititor = idCititor;
    }

    public int getIdCititor() {
        return idCititor;
    }

    public void imprumutaCarte(Carte carte) {
        Objects.requireNonNull(carte);
        if (!carte.esteDisponibil()) {
            throw new IllegalStateException("Cartea nu este  disponibila!");
        }
        cartiImprumutate.add(carte);
        carte.setEsteDisponibil(false);
    }

    public void adaugaCarteImprumutata(Carte carte) {
        cartiImprumutate.add(carte);
    }
    
    public void returneazaCarte(Carte carte) {
        if (this.cartiImprumutate.contains(carte)) {
            this.cartiImprumutate.remove(carte);
            carte.setEsteDisponibil(true);
        } else {
            throw new IllegalStateException("Cartea nu a fost imprumutata de acest cititor.");
        }
    }

    public List<Carte> getCartiImprumutate() {
        return new ArrayList<>(cartiImprumutate);
    }

    public void setParola(String parola) {
        this.parola = parola;
    }

    public String getParola() {
        return parola;
    }

}


class Sectiune {
    private String numeSectiune;
    private String locatie;

    public Sectiune(String numeSectiune, String locatie) {
        setNumeSectiune(numeSectiune);
        setLocatie(locatie);
    }

    public void setNumeSectiune(String numeSectiune) {
        if (numeSectiune == null || numeSectiune.isEmpty()) {
            throw new IllegalArgumentException("Numele sectiunii nu poate fi null sau gol.");
        }
        this.numeSectiune = numeSectiune;
    }

    public String getNumeSectiune() {
        return numeSectiune;
    }

    public void setLocatie(String locatie) {
        if (locatie == null || locatie.isEmpty()) {
            throw new IllegalArgumentException("Locatia nu poate fi null sau gol.");
        }
        this.locatie = locatie;
    }

    public String getLocatie() {
        return locatie;
    }

}


class Imprumut {
    private Cititor cititor;
    private Carte carteImprumutata;
    private LocalDate dataImprumut;
    private LocalDate dataReturnare;
    private boolean activ;

    public Imprumut(Cititor cititor, Carte carteImprumutata, LocalDate dataImprumut, LocalDate dataReturnare) {
        setCititor(cititor);
        setCarteImprumutata(carteImprumutata);
        setDataImprumut(dataImprumut);
        setDataReturnare(dataReturnare);
        this.activ = true; 
    }

    public void setCititor(Cititor cititor) {
        this.cititor = Objects.requireNonNull(cititor, "Cititorul nu poate fi null.");
    }

    public Cititor getCititor() {
        return cititor;
    }

    public void setCarteImprumutata(Carte carteImprumutata) {
        this.carteImprumutata = Objects.requireNonNull(carteImprumutata, "Cartea imprumutata nu poate fi null.");
    }

    public Carte getCarteImprumutata() {
        return carteImprumutata;
    }

    public void setDataImprumut(LocalDate dataImprumut) {
        this.dataImprumut = Objects.requireNonNull(dataImprumut, "Data imprumutului nu poate fi null.");
    }

    public LocalDate getDataImprumut() {
        return dataImprumut;
    }

    public void setDataReturnare(LocalDate dataReturnare) {
        this.dataReturnare = Objects.requireNonNull(dataReturnare, "Data returnarii nu poate fi null.");
    }

    public LocalDate getDataReturnare() {
        return dataReturnare;
    }

    public void finalizeazaImprumut() {
        this.activ = false;
    }

    public boolean esteActiv() {
        return activ;
    }

}


class Roman extends Carte {
    private String genLiterar;
    private int numarPagini;
    
    public Roman(String nume, Autor autor, Sectiune sectiune, int anPublicatie, String genLiterar, int numarPagini) {
        super(nume, autor, sectiune, anPublicatie);
        setGenLiterar(genLiterar);
        setNumarPagini(numarPagini);
    }

    public void setGenLiterar(String genLiterar) {
        if (genLiterar == null || !List.of("Drama", "Comedie", "SF").contains(genLiterar)) {
            throw new IllegalArgumentException("Gen literar invalid.");
        }
        this.genLiterar = genLiterar;
    }

    public String getGenLiterar() {
        return genLiterar;
    }

    public void setNumarPagini(int numarPagini) {
        if (numarPagini <= 0) {
            throw new IllegalArgumentException("Numarul de pagini trebuie sa fie un numar pozitiv.");
        }
        this.numarPagini = numarPagini;
    }

    public int getNumarPagini() {
        return numarPagini;
    }

    // builder
    private Roman(RomanBuilder builder) {
        super(builder);
        this.genLiterar = builder.genLiterar;
        this.numarPagini = builder.numarPagini;
    }

    public static class RomanBuilder extends Carte.Builder<RomanBuilder> {
        private String genLiterar;
        private int numarPagini;

        public RomanBuilder setGenLiterar(String genLiterar) {
            this.genLiterar = genLiterar;
            return this;
        }

        public RomanBuilder setNumarPagini(int numarPagini) {
            this.numarPagini = numarPagini;
            return this;
        }

        @Override
        public Roman build() {
            return new Roman(this);
        }
    }
}


class EditieSpeciala extends Carte {
    private String tipEditie;
    private int numarExemplare;

    public EditieSpeciala(String nume, Autor autor, Sectiune sectiune, int anPublicatie, String tipEditie, int numarExemplare) {
        super(nume, autor, sectiune, anPublicatie);
        setTipEditie(tipEditie);
        setNumarExemplare(numarExemplare);
    }

    public void setTipEditie(String tipEditie) {
        if (tipEditie == null || !List.of("Hardcover", "Paperback", "Aniversara").contains(tipEditie)) {
            throw new IllegalArgumentException("Tip editie invalid.");
        }
        this.tipEditie = tipEditie;
    }

    public String getTipEditie() {
        return tipEditie;
    }

    public void setNumarExemplare(int numarExemplare) {
        if (numarExemplare <= 0) {
            throw new IllegalArgumentException("Numarul de exemplare trebuie sa fie un numar pozitiv.");
        }
        this.numarExemplare = numarExemplare;
    }

    public int getNumarExemplare() {
        return numarExemplare;
    }

    // builder
    private EditieSpeciala(EditieSpecialaBuilder builder) {
        super(builder);
        this.tipEditie = builder.tipEditie;
        this.numarExemplare = builder.numarExemplare;
    }

    public static class EditieSpecialaBuilder extends Carte.Builder<EditieSpecialaBuilder> {
        private String tipEditie;
        private int numarExemplare;

        public EditieSpecialaBuilder setTipEditie(String tipEditie) {
            this.tipEditie = tipEditie;
            return this;
        }

        public EditieSpecialaBuilder setNumarExemplare(int numarExemplare) {
            this.numarExemplare = numarExemplare;
            return this;
        }

        @Override
        public EditieSpeciala build() {
            return new EditieSpeciala(this);
        }
    }
}


class Biblioteca {
    private List<Carte> listaCarti = new ArrayList<>();
    private Map<Autor, List<Carte>> cartiAutor = new HashMap<>();
    private Set<Cititor> cititoriInregistrati = new HashSet<>();
    private List<Imprumut> istoricImprumuturi = new ArrayList<>();
    private final List<Imprumut> imprumuturiActive = new ArrayList<>();
    private List<Sectiune> listaSectiuni = new ArrayList<>();

    public void adaugaAutor(Autor autor) {
        Objects.requireNonNull(autor);
        cartiAutor.putIfAbsent(autor, new ArrayList<>());
    }

    public void adaugaCarte(Carte carte) {
        Objects.requireNonNull(carte);
        listaCarti.add(carte);
        sorteazaListaCarti(); 
        
        Autor autor = carte.getAutor();
        cartiAutor.computeIfAbsent(autor, k -> new ArrayList<>()).add(carte);
    }

    public void stergeCarte(Carte carte) {
        Objects.requireNonNull(carte);
        listaCarti.remove(carte);
        cartiAutor.getOrDefault(carte.getAutor(), new ArrayList<>()).remove(carte);
    }

    public void stergeAutor(Autor autor) {
        Objects.requireNonNull(autor);
        cartiAutor.remove(autor);
        listaCarti.removeIf(carte -> carte.getAutor().equals(autor));
    }

    public void stergeCititor(Cititor cititor) {
        Objects.requireNonNull(cititor);
        cititoriInregistrati.remove(cititor);
    }

    public void inregistreazaCititor(Cititor cititor) {
        Objects.requireNonNull(cititor);
        cititoriInregistrati.add(cititor);
    }

    public void imprumutaCarte(Cititor cititor, Carte carte, LocalDate dataReturnare) {
        Objects.requireNonNull(cititor);
        Objects.requireNonNull(carte);

        if (!carte.esteDisponibil()) {
            throw new IllegalStateException("Cartea nu este disponibila!");
        }

        cititor.adaugaCarteImprumutata(carte);
        carte.setEsteDisponibil(false);

        Imprumut imprumut = new Imprumut(cititor, carte, LocalDate.now(), dataReturnare);
        imprumuturiActive.add(imprumut);
    }

    public void returneazaCarte(Cititor cititor, Carte carte) {
        Imprumut imprumut = imprumuturiActive.stream()
            .filter(i -> i.getCititor().equals(cititor) 
                    && i.getCarteImprumutata().equals(carte) 
                    && i.esteActiv())
            .findFirst()
            .orElseThrow(() -> new IllegalStateException("Imprumutul nu exista!"));

        cititor.getCartiImprumutate().remove(carte);
        carte.setEsteDisponibil(true);
        imprumut.finalizeazaImprumut();
        imprumuturiActive.remove(imprumut);
        istoricImprumuturi.add(imprumut);
    }

    private void sorteazaListaCarti() {
        listaCarti.sort((carte1, carte2) -> carte1.getNume().compareToIgnoreCase(carte2.getNume()));
    }

    public List<Carte> getListaCarti() {
        return new ArrayList<>(listaCarti);
    }

    public Map<Autor, List<Carte>> getCartiAutor() {
        return new HashMap<>(cartiAutor);
    }

    public List<Sectiune> getNumeSectiune() {
        return listaCarti.stream()
            .map(Carte::getSectiune)
            .distinct()
            .toList();
    }

    public Set<Cititor> getCititoriInregistrati() {
        return new HashSet<>(cititoriInregistrati);
    }

    public List<Imprumut> getIstoricImprumuturi() {
        return new ArrayList<>(istoricImprumuturi);
    }

    public List<Imprumut> getIstoricImprumuturiCititor(Cititor cititor) {
        return istoricImprumuturi.stream()
            .filter(i -> i.getCititor().equals(cititor))
            .toList();
    }

    public boolean verificaEligibilEditieSpeciala(Biblioteca biblioteca, Cititor cititor) {
        return biblioteca.getIstoricImprumuturiCititor(cititor).size() >= 1;
    }

    public List<Imprumut> getImprumuturiActive() {
        return new ArrayList<>(imprumuturiActive);
    }
    public void adaugaSectiune(Sectiune sectiune) {
    listaSectiuni.add(sectiune);
    }

    public void stergeSectiune(Sectiune sectiune) {
        listaSectiuni.remove(sectiune);
    }

    public List<Sectiune> getListaSectiuni() {
        return listaSectiuni;
    }
}


class InitializareDate {
    public static Biblioteca initializeazaBiblioteca() {
        Biblioteca biblioteca = new Biblioteca();
        
        Autor autor1 = new Autor("Mihai", "Eminescu", "Romana");
        Autor autor2 = new Autor("Ion", "Creanga", "Romana");
        Autor autor3 = new Autor("George", "Cozma", "Romana");
        Autor autor4 = new Autor("Ion", "Barbu", "Romana");
        
        Sectiune sectiune1 = new Sectiune("Poezie", "Raft A1");
        Sectiune sectiune2 = new Sectiune("Proza", "Raft B2");
        Sectiune sectiune3 = new Sectiune("Eseuri", "Raft A2");
        Sectiune sectiune4 = new Sectiune("Literatura Universala", "Raft C1");

        biblioteca.adaugaSectiune(sectiune1);
        biblioteca.adaugaSectiune(sectiune2);
        biblioteca.adaugaSectiune(sectiune3);
        biblioteca.adaugaSectiune(sectiune4);

        Carte carte1 = new Carte.Builder<>()
            .setNume("Luceafarul")
            .setAutor(autor1)
            .setSectiune(sectiune1)
            .setAnPublicatie(1883)
            .build();

        Carte carte2 = new Carte.Builder<>()
            .setNume("Povestea lui Harap-Alb")
            .setAutor(autor2)
            .setSectiune(sectiune2)
            .setAnPublicatie(1877)
            .build();

        Roman roman1 = new Roman.RomanBuilder()
            .setNume("O scrisoare pierduta")
            .setAutor(autor3)
            .setSectiune(sectiune3)
            .setAnPublicatie(1884)
            .setGenLiterar("Comedie")
            .setNumarPagini(200)
            .build();

        EditieSpeciala editieSpeciala1 = new EditieSpeciala.EditieSpecialaBuilder()
            .setNume("Mara")
            .setAutor(autor4)
            .setSectiune(sectiune4)
            .setAnPublicatie(2023)
            .setTipEditie("Hardcover")
            .setNumarExemplare(100)
            .build();
        
        biblioteca.adaugaAutor(autor1);
        biblioteca.adaugaAutor(autor2);
        biblioteca.adaugaCarte(carte1);
        biblioteca.adaugaCarte(carte2);
        biblioteca.adaugaCarte(roman1);
        biblioteca.adaugaCarte(editieSpeciala1);
        
        Cititor cititor1 = new Cititor("Ion", "Popescu", 1, "asd");
        Cititor cititor2 = new Cititor("Maria", "Ionescu", 2, "asd");

        biblioteca.inregistreazaCititor(cititor1);
        biblioteca.inregistreazaCititor(cititor2);
        biblioteca.imprumutaCarte(cititor1, carte2, LocalDate.of(2025, 06, 15));
        biblioteca.returneazaCarte(cititor1, carte2);
        biblioteca.imprumutaCarte(cititor1, roman1, LocalDate.of(2025, 06, 20));
        biblioteca.imprumutaCarte(cititor2, carte2, LocalDate.of(2025, 07, 01));

        return biblioteca;
    }
}


class Meniu {
    private Biblioteca biblioteca;
    
    public Meniu(Biblioteca biblioteca) {
        this.biblioteca = biblioteca;
    }

    private int citesteInt(Scanner scanner, String mesaj) {
        while (true) {
            try {
                System.out.print(mesaj);
                return scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Va rugam introduceti un numar valid!");
                scanner.nextLine(); 
            }
        }
    }


    public void afiseazaMeniu() {
        Scanner scanner = new Scanner(System.in);
        int optiune;
        int optiuneAdmin;
        int optiuneCititor;
        
        do {
            System.out.println("\nMeniu");
            System.out.println("1. Cont Admin");
            System.out.println("2. Cont Cititor");
            System.out.println("0. Iesire");

            optiune = citesteInt(scanner, "Alege o optiune: ");
            scanner.nextLine();

            switch (optiune) {
                case 1:
                    boolean autentificat = false;
                    do {
                        System.out.println("Cont Admin");
                        System.out.println("Introdu username: ");
                        String username = scanner.next();
                        System.out.println("Introdu parola: ");
                        String parola = scanner.next();

                        if (username.equals("admin") && parola.equals("admin")){
                            autentificat = true;
                            System.out.println("Autentificare reustita!");
                            do {
                                System.out.println("1. Inregistreaza cititor");
                                System.out.println("2. Adauga carte");
                                System.out.println("3. Sterge carte");
                                System.out.println("4. Adauga autor");
                                System.out.println("5. Sterge autor");
                                System.out.println("6. Sterge cititor");
                                System.out.println("7. Adauga sectiune");
                                System.out.println("8. Sterege sectiune");
                                System.out.println("9. Afiseaza cititorii inregistrati");
                                System.out.println("10. Afiseaza istoric imprumuturi");
                                System.out.println("11. Statistici biblioteca");
                                System.out.println("0. Iesire");

                                optiuneAdmin = citesteInt(scanner, "Alege o optiune: ");
                                scanner.nextLine();

                                switch(optiuneAdmin) {
                                    case 1:
                                        System.out.print("Introdu numele cititorului: ");
                                        String numeCititor = scanner.nextLine();
                                        System.out.print("Introdu prenumele cititorului: ");
                                        String prenumeCititor = scanner.nextLine();
                                        int idCititor = citesteInt(scanner, "ID-ul cititorului: ");
                                        
                                        Cititor cititor = new Cititor(numeCititor, prenumeCititor, idCititor, "123");
                                        biblioteca.inregistreazaCititor(cititor);
                                        System.out.println("Cititorul a fost inregistrat cu succes!");
                                        break;
                                    
                                    case 2:
                                        System.out.print("Introdu numele cartii: ");
                                        String numeCarte = scanner.nextLine();

                                        System.out.println("Autori disponibili:");
                                        List<Autor> autori = new ArrayList<>(biblioteca.getCartiAutor().keySet());
                                        for (int i = 0; i < autori.size(); i++) {
                                            Autor a = autori.get(i);
                                            System.out.println((i + 1) + ". " + a.getPrenume() + " " + a.getNume());
                                        }

                                        int indexAutor = citesteInt(scanner, "Numarul autorului: ") - 1;
                                        scanner.nextLine();

                                        if (indexAutor < 0 || indexAutor >= autori.size()) {
                                            System.out.println("Autorul nu a fost gasit! Te rugam sa il adaugi mai intai.");
                                            break;
                                        }
                                        Autor autor = autori.get(indexAutor);

                                        System.out.println("Sectiuni disponibile:");
                                        List<Sectiune> sectiuni = biblioteca.getListaSectiuni();
                                        for (int i = 0; i < sectiuni.size(); i++) {
                                            System.out.println((i + 1) + ". " + sectiuni.get(i).getNumeSectiune());
                                        }

                                        int indexSectiune = citesteInt(scanner, "Numarul sectiunii: ") - 1;
                                        scanner.nextLine();

                                        if (indexSectiune < 0 || indexSectiune >= sectiuni.size()) {
                                            System.out.println("Sectiunea nu a fost gasita! Te rugam sa o adaugi mai intai.");
                                            break;
                                        }
                                        Sectiune sectiuneGasita = sectiuni.get(indexSectiune);

                                        int anPublicatie = citesteInt(scanner, "Anul publicatiei: ");
                                        scanner.nextLine();

                                        Carte carte = new Carte(numeCarte, autor, sectiuneGasita, anPublicatie);
                                        biblioteca.adaugaCarte(carte);
                                        System.out.println("Cartea a fost adaugata cu succes!");
                                        break;

                                    case 3:
                                        System.out.print("Alege numarul cartii de sters: \n");
                                        List<Carte> cartiBiblioteca = biblioteca.getListaCarti();
                                        for (int i = 0; i < cartiBiblioteca.size(); i++) {
                                            Carte c = cartiBiblioteca.get(i);
                                            System.out.println((i + 1) + ". " + c.getNume() + " - " + c.getAutor().getNume() + " " + c.getAutor().getPrenume());
                                        }
                                        int indexCarteStergere = citesteInt(scanner, "Numarul cartii: ") - 1;
                                        scanner.nextLine();
                                        if (indexCarteStergere < 0 || indexCarteStergere >= cartiBiblioteca.size()) {
                                            System.out.println("Cartea nu a fost gasita!");
                                            break;
                                        }
                                        Carte carteDeSters = cartiBiblioteca.get(indexCarteStergere);
                                        biblioteca.stergeCarte(carteDeSters);
                                        System.out.println("Cartea a fost stearsa cu succes!");
                                        break;
                                        

                                    case 4:
                                        System.out.print("Introdu numele autorului: ");
                                        String numeAutorNou = scanner.nextLine();
                                        System.out.print("Introdu prenumele autorului: ");
                                        String prenumeAutorNou = scanner.nextLine();
                                        System.out.print("Introdu nationalitatea autorului: ");
                                        String nationalitateAutorNou = scanner.nextLine();
                                        
                                        Autor autorNou = new Autor(prenumeAutorNou, numeAutorNou, nationalitateAutorNou);
                                        biblioteca.adaugaAutor(autorNou);
                                        
                                        System.out.println("Autorul a fost adaugat cu succes!");
                                        break;

                                    case 5:
                                        System.out.print("Alege numarul autorului de sters: \n");
                                        List<Autor> autoriBiblioteca = new ArrayList<>(biblioteca.getCartiAutor().keySet());
                                        for (int i = 0; i < autoriBiblioteca.size(); i++) {
                                            Autor a = autoriBiblioteca.get(i);
                                            System.out.println((i + 1) + ". " + a.getPrenume() + " " + a.getNume());
                                        }
                                        int indexAutorStergere = citesteInt(scanner, "Numarul autorului: ") - 1;
                                        scanner.nextLine();
                                        if (indexAutorStergere < 0 || indexAutorStergere >= autoriBiblioteca.size()) {
                                            System.out.println("Autorul nu a fost gasit!");
                                            break;
                                        }
                                        Autor autorDeSters = autoriBiblioteca.get(indexAutorStergere);
                                        biblioteca.stergeAutor(autorDeSters);
                                        System.out.println("Autorul a fost sters cu succes!");
                                        break;                                
                                    
                                    case 6:
                                        System.out.print("Introdu ID-ul cititorului de sters: ");
                                        int idCititorStergere = citesteInt(scanner, "ID-ul cititorului: ");
                                        Set<Cititor> cititori = biblioteca.getCititoriInregistrati();
                                        Cititor cititorDeSters = cititori.stream()
                                                .filter(c -> c.getIdCititor() == idCititorStergere)
                                                .findFirst()
                                                .orElse(null);
                                        if (cititorDeSters == null) {
                                            System.out.println("Cititorul nu a fost gasit!");
                                            break;
                                        }
                                        biblioteca.stergeCititor(cititorDeSters);
                                        System.out.println("Cititorul a fost sters cu succes!");
                                        break;

                                    case 7:
                                        System.out.print("Introdu numele sectiunii: ");
                                        String numeSectiune = scanner.nextLine();
                                        System.out.print("Introdu locatia sectiunii: ");
                                        String locatieSectiune = scanner.nextLine();
                                        
                                        Sectiune sectiuneNoua = new Sectiune(numeSectiune, locatieSectiune);
                                        biblioteca.adaugaSectiune(sectiuneNoua);
                                        
                                        System.out.println("Sectiunea a fost adaugata cu succes!");
                                        break;

                                    case 8:
                                        System.out.print("Alege numarul sectiunii de sters: \n");
                                        List<Sectiune> sectiuniBiblioteca = biblioteca.getListaSectiuni();
                                        for (int i = 0; i < sectiuniBiblioteca.size(); i++) {
                                            Sectiune s = sectiuniBiblioteca.get(i);
                                            System.out.println((i + 1) + ". " + s.getNumeSectiune() + " - " + s.getLocatie());
                                        }
                                        int indexSectiuneStergere = citesteInt(scanner, "Numarul sectiunii: ") - 1;
                                        scanner.nextLine();
                                        if (indexSectiuneStergere < 0 || indexSectiuneStergere >= sectiuniBiblioteca.size()) {
                                            System.out.println("Sectiunea nu a fost gasita!");
                                            break;
                                        }
                                        Sectiune sectiuneDeSters = sectiuniBiblioteca.get(indexSectiuneStergere);
                                        biblioteca.stergeSectiune(sectiuneDeSters);
                                        System.out.println("Sectiunea a fost stearsa cu succes!");
                                        break;
                                        
                                    case 9:
                                        System.out.println("Cititorii inregistrati:");
                                        for (Cititor cit : biblioteca.getCititoriInregistrati()) {
                                            System.out.println("- " + cit.getNume() + " " + cit.getPrenume());
                                        }
                                        break;

                                    case 10:
                                        System.out.println("Istoric imprumuturi:");
                                        for (Imprumut imprumut : biblioteca.getIstoricImprumuturi()) {
                                            System.out.println("- " + imprumut.getCarteImprumutata().getNume() + " imprumutata de " + imprumut.getCititor().getNume() + " " + imprumut.getCititor().getPrenume() + " la data " + imprumut.getDataImprumut());
                                        }
                                        break;
                                    
                                    case 11:
                                        System.out.println("Statistici biblioteca:");
                                        System.out.println("- Numarul total de carti: " + biblioteca.getListaCarti().size());
                                        System.out.println("- Numarul total de cititori inregistrati: " + biblioteca.getCititoriInregistrati().size());
                                        System.out.println("- Numarul total de imprumuturi active: " + biblioteca.getImprumuturiActive().size());
                                        break;

                                    case 0:
                                        System.out.println("Iesire din contul de admin.");
                                        autentificat = false;
                                        break;

                                    default:
                                        System.out.println("Optiune invalida! Va rugam incercati din nou.");
                                        break;
                                }
                        } while (optiuneAdmin != 0);
                    } else {
                            System.out.println("Autentificare esuata! Te rugam sa incerci din nou.");
                    }   
                    } while (autentificat);
                    break;

                case 2:
                    boolean autentificatCititor = false;
                    do{
                        System.out.println("Cont Cititor");
                        System.out.print("Introdu ID-ul cititorului: ");
                        int idCititor = citesteInt(scanner, "ID-ul cititorului: ");
                        System.out.print("Introdu parola: ");
                        String parolaCititor = scanner.next();
                        scanner.nextLine();
                        Cititor cititorGasit = biblioteca.getCititoriInregistrati().stream()
                                .filter(c -> c.getIdCititor() == idCititor && c.getParola().equals(parolaCititor))
                                .findFirst()
                                .orElse(null);
                        if (cititorGasit != null) {
                            autentificatCititor = true;
                            System.out.println("Autentificare reusita!");
                            do {
                                System.out.println("1. Imprumuta carte");
                                System.out.println("2. Returneaza carte");
                                System.out.println("3. Afiseaza cartile disponibile");
                                System.out.println("4. Cauta carte (dupa nume, autor, an publicatie)");
                                System.out.println("5. Numar carti imprumutate cititor si eligibilitate editie speciala");
                                System.out.println("6. Notificari");
                                System.out.println("0. Iesire");

                                optiuneCititor = citesteInt(scanner, "Alege o optiune: ");
                                scanner.nextLine();

                                switch(optiuneCititor){
                                    case 1:
                                        List<Carte> cartiDisponibile = biblioteca.getListaCarti().stream()
                                                .filter(Carte::esteDisponibil)
                                                .toList();

                                        if (cartiDisponibile.isEmpty()) {
                                            System.out.println("Nu exista carti disponibile pentru imprumut!");
                                            break;
                                        }

                                        System.out.println("Carti disponibile:");
                                        for (int i = 0; i < cartiDisponibile.size(); i++) {
                                            System.out.println((i + 1) + ". " + cartiDisponibile.get(i).getNume());
                                        }

                                        int numarCarte = citesteInt(scanner, "Alege numarul cartii: ");
                                        scanner.nextLine();

                                        if (numarCarte < 1 || numarCarte > cartiDisponibile.size()) {
                                            System.out.println("Numar invalid!");
                                            break;
                                        }

                                        Carte carteImprumut = cartiDisponibile.get(numarCarte - 1);

                                        if (carteImprumut instanceof EditieSpeciala && 
                                            !biblioteca.verificaEligibilEditieSpeciala(biblioteca, cititorGasit)) {
                                            System.out.println("Cititorul nu este eligibil pentru a imprumuta o editie speciala!");
                                            break;
                                        }

                                        System.out.print("Introdu data de returnare (YYYY-MM-DD): ");
                                        String dataReturnareStr = scanner.nextLine();

                                        try {
                                            LocalDate dataReturnare = LocalDate.parse(dataReturnareStr);
                                            biblioteca.imprumutaCarte(cititorGasit, carteImprumut, dataReturnare);
                                            System.out.println("Cartea se gaseste in sectiunea " + 
                                                carteImprumut.getSectiune().getNumeSectiune() + " " + 
                                                carteImprumut.getSectiune().getLocatie() + 
                                                " si a fost imprumutata cu succes!");
                                            System.out.println("Data de returnare este: " + dataReturnare);
                                        } catch (DateTimeParseException e) {
                                            System.out.println("Formatul datei este invalid!");
                                        }
                                        break;

                                    case 2:
                                        System.out.print("Alege numarul cartii de returnat: \n");
                                        List<Carte> cartiImprumutate = cititorGasit.getCartiImprumutate();
                                        if (cartiImprumutate.isEmpty()) {
                                            System.out.println("Nu ai carti imprumutate!");
                                            break;
                                        }
                                        for (int i = 0; i < cartiImprumutate.size(); i++) {
                                            Carte c = cartiImprumutate.get(i);
                                            System.out.println((i + 1) + ". " + c.getNume() + " - " + c.getAutor().getNume() + " " + c.getAutor().getPrenume());
                                        }
                                        int indexCarteReturnare = citesteInt(scanner, "Numarul cartii: ") - 1;
                                        scanner.nextLine();
                                        if (indexCarteReturnare < 0 || indexCarteReturnare >= cartiImprumutate.size()) {
                                            System.out.println("Cartea nu a fost gasita!");
                                            break;
                                        }
                                        Carte carteReturnare = cartiImprumutate.get(indexCarteReturnare);
                                        if (!carteReturnare.esteDisponibil()) {
                                            System.out.println("Cartea nu este imprumutata!");
                                            break;
                                        }

                                        biblioteca.returneazaCarte(cititorGasit, carteReturnare);
                                        System.out.println("Cartea a fost returnata cu succes!");
                                        break;
                                    
                                    case 3:
                                        System.out.println("Cartile disponibile:");
                                        for (Carte carte : biblioteca.getListaCarti()) {
                                            if (carte.esteDisponibil()) {
                                                System.out.println("- " + carte.getNume() + " de " + carte.getAutor().getPrenume() + " " + carte.getAutor().getNume() + " (" + carte.getAnPublicatie() + ")");
                                            }
                                        }
                                        break;

                                    case 4:
                                        System.out.print("Dupa ce criteriu doriti sa cautati (n - nume, a - autor, ap - an publicatie): ");
                                        String criteriu = scanner.nextLine();
                                        if (criteriu.equalsIgnoreCase("n")) {
                                            System.out.print("Introdu numele cartii: ");
                                            String numeCarteCautata = scanner.nextLine();
                                            
                                            List<Carte> cartiGasite = biblioteca.getListaCarti().stream()
                                                    .filter(c -> c.getNume().equalsIgnoreCase(numeCarteCautata))
                                                    .toList();
                                            if (cartiGasite.isEmpty()) {
                                                System.out.println("Nu s-au gasit carti cu numele " + numeCarteCautata);
                                            } else {
                                                System.out.println("Cartile gasite cu numele " + numeCarteCautata + ":");
                                                for (Carte carte : cartiGasite) {
                                                    System.out.println("- " + carte.getNume() + " de " + carte.getAutor().getNume() + " (" + carte.getAnPublicatie() + ")");
                                                }
                                            }

                                        } else if(criteriu.equalsIgnoreCase("a")) {
                                            System.out.print("Introdu numele autorului: ");
                                            String numeAutorCautat = scanner.nextLine();
                                            
                                            List<Carte> cartiGasite = biblioteca.getListaCarti().stream()
                                                    .filter(c -> c.getAutor().getNume().equalsIgnoreCase(numeAutorCautat))
                                                    .toList();
                                            
                                            if (cartiGasite.isEmpty()) {
                                                System.out.println("Nu s-au gasit carti pentru autorul " + numeAutorCautat);
                                            } else {
                                                System.out.println("Cartile gasite pentru autorul " + numeAutorCautat + ":");
                                                for (Carte carte : cartiGasite) {
                                                    System.out.println("- " + carte.getNume() + " (" + carte.getAnPublicatie() + ")");
                                                }
                                            }
                                        } else if (criteriu.equalsIgnoreCase("ap")) {
                                            int anPublicatieCautat = citesteInt(scanner, "Introdu anul publicatiei: ");
                                            
                                            List<Carte> cartiGasite = biblioteca.getListaCarti().stream()
                                                    .filter(c -> c.getAnPublicatie() == anPublicatieCautat)
                                                    .toList();
                                            
                                            if (cartiGasite.isEmpty()) {
                                                System.out.println("Nu s-au gasit carti pentru anul publicatiei " + anPublicatieCautat);
                                            } else {
                                                System.out.println("Cartile gasite pentru anul publicatiei " + anPublicatieCautat + ":");
                                                for (Carte carte : cartiGasite) {
                                                    System.out.println("- " + carte.getNume() + " de " + carte.getAutor().getNume() + " (" + carte.getAnPublicatie() + ")");
                                                }
                                            }
                                        } else {
                                            try {
                                                throw new IllegalArgumentException("Criteriu invalid!");
                                            } catch (IllegalArgumentException e) {
                                                System.out.println(e.getMessage());
                                            }
                                        }
                                        break;

                                    case 5:                                   
                                        Cititor cititorStatistici = biblioteca.getCititoriInregistrati().stream()
                                                .filter(c -> c.getIdCititor() == idCititor)
                                                .findFirst()
                                                .orElse(null);
                                        
                                        try {
                                            if (cititorStatistici == null) {
                                                throw new IllegalStateException("Cititorul nu este inregistrat!");
                                            }
                                        } catch (IllegalStateException e) {
                                            System.out.println(e.getMessage());
                                            break;
                                        }
                                        
                                        System.out.println("Numarul de carti imprumutate de " + cititorStatistici.getNume() + " " + cititorStatistici.getPrenume() + ": " + cititorStatistici.getCartiImprumutate().size());
                                        
                                        if (biblioteca.verificaEligibilEditieSpeciala(biblioteca, cititorStatistici)) {
                                            System.out.println("Cititorul este eligibil pentru a imprumuta o editie speciala.");
                                        } else {
                                            System.out.println("Cititorul nu este eligibil pentru a imprumuta o editie speciala.");
                                        }
                                        break;
                                    
                                    case 6:
                                        System.out.println("nu este implementat inca");
                                        break;

                                    case 0:
                                        System.out.println("Iesire din contul de cititor.");
                                        autentificatCititor = false;
                                        break;
                                    
                                    default:
                                        System.out.println("Optiune invalida! Va rugam incercati din nou.");
                                        break;
                                    
                                }

                            } while (optiuneCititor != 0);
                        } else {
                            System.out.println("Autentificare esuata! Te rugam sa incerci din nou.");
                            
                        }
                    } while(autentificatCititor);
                    break;

                case 0:
                    System.out.println("Iesire din program.");
                    break;
                default:
                    System.out.println("Optiune invalida! Va rugam incercati din nou.");
            }
        } while (optiune != 0);

        scanner.close();
    }
}


public class proiect {
    public static void main(String[] args) {
        Biblioteca biblioteca = InitializareDate.initializeazaBiblioteca();
        Meniu meniu = new Meniu(biblioteca);
        meniu.afiseazaMeniu();
    }
}