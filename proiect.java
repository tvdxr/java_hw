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
    private List<Carte> cartiImprumutate;

    public Cititor(String nume, String prenume, int idCititor, List<Carte> cartiImprumutate) {
        setNume(nume);
        setPrenume(prenume);
        setIdCititor(idCititor);
        if (cartiImprumutate != null) {
            this.cartiImprumutate = new ArrayList<>(cartiImprumutate);
            this.cartiImprumutate.forEach(carte -> carte.setEsteDisponibil(false));
        } else {
            this.cartiImprumutate = new ArrayList<>();
        }
    }

    public Cititor(String nume, String prenume, int idCititor) {
        this(nume, prenume, idCititor, null);
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

    public void inregistreazaCititor(Cititor cititor) {
        Objects.requireNonNull(cititor);
        cititoriInregistrati.add(cititor);
    }

    public void imprumutaCarte(Cititor cititor, Carte carte, LocalDate dataReturnare) {
        Objects.requireNonNull(cititor);
        Objects.requireNonNull(carte);

        if (!carte.esteDisponibil()) {
            throw new IllegalStateException("Cartea nu este  disponibila!");
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
        
        Cititor cititor1 = new Cititor("Ion", "Popescu", 1);

        biblioteca.inregistreazaCititor(cititor1);
        biblioteca.imprumutaCarte(cititor1, carte2, LocalDate.of(2025, 06, 15));
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
        
        do {
            System.out.println("\n=== MENIU BIBLIOTECA ===");
            System.out.println("1. Inregistreaza cititor");
            System.out.println("2. Imprumuta carte");
            System.out.println("3. Returneaza carte");
            System.out.println("4. Afiseaza carti disponibile");
            System.out.println("5. Afiseaza cititorii inregistrati");
            System.out.println("6. Afiseaza istoric imprumuturi");
            System.out.println("7. Cauta carte (dupa nume, autor, an publicatie)");
            System.out.println("8. Statistici biblioteca");
            System.out.println("9. Iesire");
            
            optiune = citesteInt(scanner, "Alege o optiune: ");
            scanner.nextLine();  
            
            switch (optiune) {
                case 1:
                    System.out.print("Introdu numele cititorului: ");
                    String numeCititor = scanner.nextLine();
                    System.out.print("Introdu prenumele cititorului: ");
                    String prenumeCititor = scanner.nextLine();
                    int idCititor = citesteInt(scanner, "ID-ul cititorului: ");
                    
                    Cititor cititor = new Cititor(numeCititor, prenumeCititor, idCititor);
                    biblioteca.inregistreazaCititor(cititor);
                    System.out.println("Cititorul a fost inregistrat cu succes!");
                    break;

                case 2:
                    int idCititorImprumut = citesteInt(scanner, "ID-ul cititorului: ");
                    scanner.nextLine(); 
                    
                    Cititor cititorImprumut = biblioteca.getCititoriInregistrati().stream()
                            .filter(c -> c.getIdCititor() == idCititorImprumut)
                            .findFirst()
                            .orElse(null);
                    
                    try {
                        if (cititorImprumut == null) {
                            throw new IllegalStateException("Cititorul nu este inregistrat!");
                        }
                    } catch (IllegalStateException e) {
                        System.out.println(e.getMessage());
                        break;
                    }
                    
                    System.out.print("Introdu numele cartii: ");
                    String numeCarte = scanner.nextLine();
                    
                    Carte carteImprumut = biblioteca.getListaCarti().stream()
                            .filter(c -> c.getNume().equalsIgnoreCase(numeCarte) && c.esteDisponibil())
                            .findFirst()
                            .orElse(null);
                    
                    try {
                        if (carteImprumut == null) {
                            throw new IllegalStateException("Cartea nu este disponibila!");
                        }
                    } catch (IllegalStateException e) {
                        System.out.println(e.getMessage());
                        break;
                    }
                    
                    try {
                        if (carteImprumut instanceof EditieSpeciala && !biblioteca.verificaEligibilEditieSpeciala(biblioteca, cititorImprumut)) {
                            throw new IllegalStateException("Cititorul nu este eligibil pentru a imprumuta o editie speciala!");
                        }
                    } catch (IllegalStateException e) {
                        System.out.println(e.getMessage());
                        break;
                    }

                    System.out.print("Introdu data de returnare (YYYY-MM-DD): ");
                    String dataReturnareStr = scanner.nextLine();
                    LocalDate dataReturnare = LocalDate.parse(dataReturnareStr);
                    
                    biblioteca.imprumutaCarte(cititorImprumut, carteImprumut, dataReturnare);
                    System.out.println("Cartea se gaseste in sectiunea " + carteImprumut.getSectiune().getNumeSectiune() + " " + carteImprumut.getSectiune().getLocatie() + " si a fost imprumutata cu succes!");
                    System.out.println("Data de returnare este: " + dataReturnare);
                    break;

                case 3:
                    int idCititorReturnare = citesteInt(scanner, "ID-ul cititorului: ");
                    scanner.nextLine(); 
                    
                    Cititor cititorReturnare = biblioteca.getCititoriInregistrati().stream()
                            .filter(c -> c.getIdCititor() == idCititorReturnare)
                            .findFirst()
                            .orElse(null);
                    
                    try {
                        if (cititorReturnare == null) {
                            throw new IllegalStateException("Cititorul nu este inregistrat!");
                        }
                    } catch (IllegalStateException e) {
                        System.out.println(e.getMessage());
                        break;
                    }
                    
                    System.out.print("Introdu numele cartii: ");
                    String numeCarteReturnare = scanner.nextLine();
                    
                    Carte carteReturnare = biblioteca.getListaCarti().stream()
                            .filter(c -> c.getNume().equalsIgnoreCase(numeCarteReturnare) && !c.esteDisponibil())
                            .findFirst()
                            .orElse(null);
                    
                    if (carteReturnare == null) {
                        System.out.println("Cartea nu a fost imprumutata!");
                        break;
                    }
                    
                    biblioteca.returneazaCarte(cititorReturnare, carteReturnare);
                    System.out.println("Cartea a fost returnata cu succes!");
                    break;
                
                    case 4:
                        System.out.println("Cartile disponibile:");
                        for (Carte carte : biblioteca.getListaCarti()) {
                            if (carte.esteDisponibil()) {
                                System.out.println("- " + carte.getNume() + " de " + carte.getAutor().getPrenume() + " " + carte.getAutor().getNume() + " (" + carte.getAnPublicatie() + ")");
                            }
                        }
                        break;
                    
                    case 5:
                        System.out.println("Cititorii inregistrati:");
                        for (Cititor cit : biblioteca.getCititoriInregistrati()) {
                            System.out.println("- " + cit.getNume() + " " + cit.getPrenume());
                        }
                        break;
                    
                    case 6:
                        System.out.println("Istoric imprumuturi:");
                        for (Imprumut imprumut : biblioteca.getIstoricImprumuturi()) {
                            System.out.println("- " + imprumut.getCarteImprumutata().getNume() + " imprumutata de " + imprumut.getCititor().getNume() + " " + imprumut.getCititor().getPrenume() + " la data " + imprumut.getDataImprumut());
                        }
                        break;
                    
                    case 7:
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
                    
                    case 8:
                        System.out.println("Statistici biblioteca:");
                        System.out.println("- Numărul total de cărți: " + biblioteca.getListaCarti().size());
                        System.out.println("- Numărul total de cititori înregistrați: " + biblioteca.getCititoriInregistrati().size());
                        System.out.println("- Numărul total de împrumuturi active: " + biblioteca.getImprumuturiActive().size());
                        break;
            }
        } while (optiune != 9);

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