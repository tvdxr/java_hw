import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Objects;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

class Carte {
    private String nume;
    private Autor autor;
    private Sectiune sectiune;
    private int anPublicatie;
    private boolean esteDisponibil;

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
    protected Carte(Builder builder) {
        this.nume = builder.nume;
        this.autor = builder.autor;
        this.sectiune = builder.sectiune;
        this.anPublicatie = builder.anPublicatie;
        this.esteDisponibil = builder.esteDisponibil;
    }

    public static class Builder {
        private String nume;
        private Autor autor;
        private Sectiune sectiune;
        private int anPublicatie;
        private boolean esteDisponibil = true;

        public Builder setNume(String nume) {
            if (nume == null || nume.isEmpty()) {
                throw new IllegalArgumentException("Numele nu poate fi null sau gol.");
            }
            this.nume = nume;
            return this;
        }

        public Builder setAutor(Autor autor) {
            this.autor = autor;
            return this;
        }

        public Builder setSectiune(Sectiune sectiune) {
            this.sectiune = sectiune;
            return this;
        }

        public Builder setAnPublicatie(int anPublicatie) {
            if (anPublicatie <= 0) {
                throw new IllegalArgumentException("Anul publicatiei trebuie sa fie un numar pozitiv.");
            }
            this.anPublicatie = anPublicatie;
            return this;
        }

        public Builder setEsteDisponibil(boolean esteDisponibil) {
            this.esteDisponibil = esteDisponibil;
            return this;
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

    public Autor(String nume, String prenume, String nationalitate, List<Carte> cartiExistente) {
        setNume(nume);
        setPrenume(prenume);
        setNationalitate(nationalitate);

        if (cartiExistente != null) {
            this.cartiPublicate = new ArrayList<>(cartiExistente);
            this.cartiPublicate.forEach(carte -> carte.setAutor(this));
        } else {
            this.cartiPublicate = new ArrayList<>();
        }
    }

    public Autor(String nume, String prenume, String nationalitate) {
        this(nume, prenume, nationalitate, null);
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

    private static class RomanBuilder extends Carte.Builder {
        private String genLiterar;
        private int numarPagini;

        public Builder setGenLiterar(String genLiterar) {
            this.genLiterar = genLiterar;
            return this;
        }

        public Builder setNumarPagini(int numarPagini) {
            this.numarPagini = numarPagini;
            return this;
        }

        @Override
        public Roman build() {
            return new Roman(this);
        }
    }
}


class editieSpeciala extends Carte {
    private String tipEditie;
    private int numarExemplare;

    public editieSpeciala(String nume, Autor autor, Sectiune sectiune, int anPublicatie, String tipEditie, int numarExemplare) {
        super(nume, autor, sectiune, anPublicatie);
        setTipEditie(tipEditie);
        setNumarExemplare(numarExemplare);
    }

    public void setTipEditie(String tipEditie) {
        if (tipEditie == null || !List.of("Hardcover", "Paperback").contains(tipEditie)) {
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
    private editieSpeciala(EditieSpecialaBuilder builder) {
        super(builder);
        this.tipEditie = builder.tipEditie;
        this.numarExemplare = builder.numarExemplare;
    }

    private static class EditieSpecialaBuilder extends Carte.Builder {
        private String tipEditie;
        private int numarExemplare;

        public Builder setTipEditie(String tipEditie) {
            this.tipEditie = tipEditie;
            return this;
        }

        public Builder setNumarExemplare(int numarExemplare) {
            this.numarExemplare = numarExemplare;
            return this;
        }

        @Override
        public editieSpeciala build() {
            return new editieSpeciala(this);
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

    public void adaugaSectiune(Sectiune sectiune) {
        Objects.requireNonNull(sectiune);
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
        istoricImprumuturi.add(imprumut);
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

    private boolean verificaEligibilEditieSpeciala(Biblioteca biblioteca, Cititor cititor) {
        return biblioteca.getIstoricImprumuturiCititor(cititor).size() >= 3;
    }

    public List<Imprumut> getImprumuturiActive() {
        return new ArrayList<>(imprumuturiActive);
    }
}


/* DE ADAUGAT INCA CATEVA ACTIUNI/INTEROGARI IN CLASA BIBLIOTECA 
 clasa noua pentru card de biblioteca la 3 imprumuturi se deblocheaza editie speciala
*/

class InitializareDate {
    public static Biblioteca initializeazaBiblioteca() {
        Biblioteca biblioteca = new Biblioteca();
        
        Autor autor1 = new Autor("Mihai", "Eminescu", "Romana");
        Autor autor2 = new Autor("Ion", "Creanga", "Romana");
        
        Sectiune sectiune1 = new Sectiune("Poezie", "Raft A1");
        Sectiune sectiune2 = new Sectiune("Proza", "Raft B2");
        
        Carte carte1 = new Carte("Luceafarul", autor1, sectiune1, 1883);
        Carte carte2 = new Carte("Povestea lui Harap-Alb", autor2, sectiune2, 1877);
        Roman roman1 = new Roman("O scrisoare pierduta", autor2, sectiune2, 1884, "Comedie", 200);
        
        biblioteca.adaugaAutor(autor1);
        biblioteca.adaugaAutor(autor2);
        biblioteca.adaugaCarte(carte1);
        biblioteca.adaugaCarte(carte2);
        biblioteca.adaugaCarte(roman1);
        
        return biblioteca;
    }
}


class Meniu {
    private Biblioteca biblioteca;
    
    public Meniu(Biblioteca biblioteca) {
        this.biblioteca = biblioteca;
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
            System.out.println("7. Iesire");
            System.out.print("Alege optiunea: ");
            
            optiune = scanner.nextInt();
            scanner.nextLine();  
            
            switch (optiune) {
                case 1:
                    System.out.print("Introdu numele cititorului: ");
                    String numeCititor = scanner.nextLine();
                    System.out.print("Introdu prenumele cititorului: ");
                    String prenumeCititor = scanner.nextLine();
                    System.out.print("Introdu ID-ul cititorului: ");
                    int idCititor = scanner.nextInt();
                    
                    Cititor cititor = new Cititor(numeCititor, prenumeCititor, idCititor);
                    biblioteca.inregistreazaCititor(cititor);
                    System.out.println("Cititorul a fost inregistrat cu succes!");
                    break;

                case 2:
                    System.out.print("Introdu ID-ul cititorului: ");
                    int idCititorImprumut = scanner.nextInt();
                    scanner.nextLine(); 
                    
                    Cititor cititorImprumut = biblioteca.getCititoriInregistrati().stream()
                            .filter(c -> c.getIdCititor() == idCititorImprumut)
                            .findFirst()
                            .orElse(null);
                    
                    if (cititorImprumut == null) {
                        System.out.println("Cititorul nu este inregistrat!");
                        break;
                    }
                    
                    System.out.print("Introdu numele cartii: ");
                    String numeCarte = scanner.nextLine();
                    
                    Carte carteImprumut = biblioteca.getListaCarti().stream()
                            .filter(c -> c.getNume().equalsIgnoreCase(numeCarte) && c.esteDisponibil())
                            .findFirst()
                            .orElse(null);
                    
                    if (carteImprumut == null) {
                        System.out.println("Cartea nu este  disponibila!");
                        break;
                    }
                    
                    System.out.print("Introdu data de returnare (YYYY-MM-DD): ");
                    String dataReturnareStr = scanner.nextLine();
                    LocalDate dataReturnare = LocalDate.parse(dataReturnareStr);
                    
                    biblioteca.imprumutaCarte(cititorImprumut, carteImprumut, dataReturnare);
                    System.out.println("Cartea a fost imprumutata cu succes!");
                    break;

                case 3:
                    System.out.print("Introdu ID-ul cititorului: ");
                    int idCititorReturnare = scanner.nextInt();
                    scanner.nextLine(); 
                    
                    Cititor cititorReturnare = biblioteca.getCititoriInregistrati().stream()
                            .filter(c -> c.getIdCititor() == idCititorReturnare)
                            .findFirst()
                            .orElse(null);
                    
                    if (cititorReturnare == null) {
                        System.out.println("Cititorul nu este inregistrat!");
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
                                System.out.println("- " + carte.getNume() + " de " + carte.getAutor().getNume() + " " + carte.getAutor().getPrenume() + " (" + carte.getAnPublicatie() + ")");
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
                    
            }
        } while (optiune != 7);

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