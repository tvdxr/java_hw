import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Objects;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

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
            throw new IllegalStateException("Cartea nu este disponibilă!");
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
}


class Biblioteca {
    private List<Carte> listaCarti = new ArrayList<>();
    private Map<Autor, List<Carte>> cartiAutor = new HashMap<>();
    private Set<Cititor> cititoriInregistrati = new HashSet<>();
    private List<Imprumut> istoricImprumuturi = new ArrayList<>();
    private final List<Imprumut> imprumuturiActive = new ArrayList<>();

    // Adaugă autor (primul pas în ierarhia ta)
    public void adaugaAutor(Autor autor) {
        Objects.requireNonNull(autor);
        cartiAutor.putIfAbsent(autor, new ArrayList<>());
    }

    // Adaugă secțiune
    public void adaugaSectiune(Sectiune sectiune) {
        Objects.requireNonNull(sectiune);
    }

    // Adaugă carte (al treilea pas)
    public void adaugaCarte(Carte carte) {
        Objects.requireNonNull(carte);
        listaCarti.add(carte);
        sorteazaListaCarti(); 
        
        Autor autor = carte.getAutor();
        cartiAutor.computeIfAbsent(autor, k -> new ArrayList<>()).add(carte);
    }

    // Înregistrează cititor (al patrulea pas)
    public void inregistreazaCititor(Cititor cititor) {
        Objects.requireNonNull(cititor);
        cititoriInregistrati.add(cititor);
    }

    public void imprumutaCarte(Cititor cititor, Carte carte, LocalDate dataReturnare) {
        Objects.requireNonNull(cititor);
        Objects.requireNonNull(carte);

        if (!carte.esteDisponibil()) {
            throw new IllegalStateException("Cartea nu este disponibilă!");
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
            .orElseThrow(() -> new IllegalStateException("Împrumutul nu există!"));

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

    public List<Imprumut> getImprumuturiActive() {
        return new ArrayList<>(imprumuturiActive);
    }
}


/* DE ADAUGAT INCA CATEVA ACTIUNI/INTEROGARI IN CLASA BIBLIOTECA 
 clasa noua pentru card de biblioteca la 3 imprumuturi se deblocheaza editie speciala
*/


class proiect {
    public static void main(String[] args) {
        // Crearea unei instante a clasei Biblioteca
        Biblioteca biblioteca = new Biblioteca();

        // Crearea unor autori
        Autor autor1 = new Autor("Mihai", "Eminescu", "Romana");
        Autor autor2 = new Autor("Ion", "Creanga", "Romana");

        // Adaugarea autorilor in biblioteca
        biblioteca.adaugaAutor(autor1);
        biblioteca.adaugaAutor(autor2);

        // Crearea unor sectiuni
        Sectiune sectiune1 = new Sectiune("Poezie", "Etaj 1");
        Sectiune sectiune2 = new Sectiune("Proza", "Etaj 2");

        // Crearea unor carti
        Carte carte1 = new Carte("Luceafarul", autor1, sectiune1, 1883);
        Carte carte2 = new Carte("Amintiri din copilarie", autor2, sectiune2, 1890);
        Roman roman1 = new Roman("Scrisoarea pierduta", autor2, sectiune2, 1884, "Comedie", 600);
        editieSpeciala editie1 = new editieSpeciala("Poezii", autor1, sectiune1, 1883, "Hardcover", 100);

        // Adaugarea cartilor in biblioteca
        biblioteca.adaugaCarte(carte1);
        biblioteca.adaugaCarte(carte2);
        biblioteca.adaugaCarte(roman1);
        biblioteca.adaugaCarte(editie1);

        // Crearea unui cititor
        Cititor cititor1 = new Cititor("Andrei", "Popescu", 1);
        Cititor cititor2 = new Cititor("Maria", "Ionescu", 2);

        // Inregistrarea cititorului in biblioteca
        biblioteca.inregistreazaCititor(cititor1);
        biblioteca.inregistreazaCititor(cititor2);

        // Imprumutarea unei carti
        biblioteca.imprumutaCarte(cititor1, carte1, LocalDate.now().plusDays(14));
        biblioteca.imprumutaCarte(cititor2, roman1, LocalDate.now().plusDays(7));
        biblioteca.imprumutaCarte(cititor1, carte2, LocalDate.now().plusDays(30));
        biblioteca.imprumutaCarte(cititor2, editie1, LocalDate.now().plusDays(14));

        // Afisarea starii cartii dupa imprumut
        System.out.println("Cartea '" + carte1.getNume() + "' este disponibila: " + carte1.esteDisponibil());
        System.out.println("Romanul '" + roman1.getNume() + "' este disponibil: " + roman1.esteDisponibil());
        System.out.println("Editia speciala '" + editie1.getNume() + "' este disponibila: " + editie1.esteDisponibil());

        // Returnarea cartii
        biblioteca.returneazaCarte(cititor1, carte1);

        // Afisarea starii cartii dupa returnare
        System.out.println("Cartea '" + carte1.getNume() + "' este disponibila: " + carte1.esteDisponibil());

        // Afisarea tuturor cartilor din biblioteca
        System.out.println("Cartile din biblioteca:");
        for (Carte carte : biblioteca.getListaCarti()) {
            System.out.println("- " + carte.getNume() + " de " + carte.getAutor().getNume());
        }

        // Afisarea cititorilor inregistrati
        System.out.println("Cititorii inregistrati:");
        for (Cititor cititor : biblioteca.getCititoriInregistrati()) {
            System.out.println("- " + cititor.getNume() + " " + cititor.getPrenume());
        }

        System.out.println("Cartile imprumutate de " + cititor2.getNume() + ":");
        for (Carte carte : cititor2.getCartiImprumutate()) {
            System.out.println("- " + carte.getNume());
        }
    }
}