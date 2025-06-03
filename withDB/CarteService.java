package withDB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.sql.Statement;
import java.sql.PreparedStatement;

public class CarteService {
    private static CarteService instance;
    private final CRUDService<Carte> crudService;
    
    private CarteService() {
        crudService = CRUDService.getInstance();
    }
    
    public static synchronized CarteService getInstance() {
        if (instance == null) {
            instance = new CarteService();
        }
        return instance;
    }
    
    // creare tabel
    public void createTable() {
        String query = "CREATE TABLE IF NOT EXISTS carti (" +
                       "id INT AUTO_INCREMENT PRIMARY KEY, " +
                       "nume VARCHAR(100) NOT NULL, " +
                       "id_autor INT NOT NULL, " +
                       "id_sectiune INT NOT NULL, " +
                       "an_publicatie INT NOT NULL, " +
                       "este_disponibil BOOLEAN DEFAULT TRUE, " +
                       "tip_carte VARCHAR(20), " + // pentru a distinge între Carte, Roman, EditieSpeciala
                       "gen_literar VARCHAR(50), " + // doar pentru Roman
                       "numar_pagini INT, " +        // doar pentru Roman
                       "tip_editie VARCHAR(50), " +  // doar pentru EditieSpeciala
                       "numar_exemplare INT, " +     // doar pentru EditieSpeciala
                       "FOREIGN KEY (id_autor) REFERENCES autori(id), " +
                       "FOREIGN KEY (id_sectiune) REFERENCES sectiuni(id))";
        crudService.executeUpdate(query);
    }
    
    // adaugare carte
    public void adaugaCarte(Carte carte) {
        String query;
        if (carte instanceof Roman) {
            Roman roman = (Roman) carte;
            query = String.format("INSERT INTO carti (nume, id_autor, id_sectiune, an_publicatie, este_disponibil, " +
                                 "tip_carte, gen_literar, numar_pagini) VALUES ('%s', %d, %d, %d, %b, 'ROMAN', '%s', %d)",
                                 carte.getNume(), carte.getAutor().getId(), carte.getSectiune().getId(), 
                                 carte.getAnPublicatie(), carte.esteDisponibil(),
                                 roman.getGenLiterar(), roman.getNumarPagini());
        } else if (carte instanceof EditieSpeciala) {
            EditieSpeciala editie = (EditieSpeciala) carte;
            query = String.format("INSERT INTO carti (nume, id_autor, id_sectiune, an_publicatie, este_disponibil, " +
                                 "tip_carte, tip_editie, numar_exemplare) VALUES ('%s', %d, %d, %d, %b, 'EDITIE', '%s', %d)",
                                 carte.getNume(), carte.getAutor().getId(), carte.getSectiune().getId(), 
                                 carte.getAnPublicatie(), carte.esteDisponibil(),
                                 editie.getTipEditie(), editie.getNumarExemplare());
        } else {
            query = String.format("INSERT INTO carti (nume, id_autor, id_sectiune, an_publicatie, este_disponibil, tip_carte) " +
                                 "VALUES ('%s', %d, %d, %d, %b, 'CARTE')",
                                 carte.getNume(), carte.getAutor().getId(), carte.getSectiune().getId(), 
                                 carte.getAnPublicatie(), carte.esteDisponibil());
        }
        crudService.executeUpdate(query);
    }
    
    // obtine toate cartile
    public List<Carte> getToateCartile() {
        String query = "SELECT * FROM carti c " +
                       "JOIN autori a ON c.id_autor = a.id " +
                       "JOIN sectiuni s ON c.id_sectiune = s.id";
        return crudService.executeQuery(query, new CRUDService.ResultSetMapper<Carte>() {
            @Override
            public Carte map(ResultSet rs) throws SQLException {
                Autor autor = new Autor(
                    rs.getString("prenume"),
                    rs.getString("nume"),
                    rs.getString("nationalitate")
                );
                autor.setId(rs.getInt("id"));
                
                Sectiune sectiune = new Sectiune(
                    rs.getString("nume_sectiune"),
                    rs.getString("locatie")
                );
                sectiune.setId(rs.getInt("id"));
                
                String tipCarte = rs.getString("tip_carte");
                if ("ROMAN".equals(tipCarte)) {
                    return new Roman(
                        rs.getString("nume"),
                        autor,
                        sectiune,
                        rs.getInt("an_publicatie"),
                        rs.getString("gen_literar"),
                        rs.getInt("numar_pagini")
                    );
                } else if ("EDITIE".equals(tipCarte)) {
                    return new EditieSpeciala(
                        rs.getString("nume"),
                        autor,
                        sectiune,
                        rs.getInt("an_publicatie"),
                        rs.getString("tip_editie"),
                        rs.getInt("numar_exemplare")
                    );
                } else {
                    Carte carte = new Carte(
                        rs.getString("nume"),
                        autor,
                        sectiune,
                        rs.getInt("an_publicatie")
                    );
                    carte.setEsteDisponibil(rs.getBoolean("este_disponibil"));
                    return carte;
                }
            }
        });
    }
    
    // update carte
    public void actualizeazaCarte(Carte carte) {
        String query = String.format("UPDATE carti SET nume='%s', id_autor=%d, id_sectiune=%d, " +
                                    "an_publicatie=%d, este_disponibil=%b WHERE id=%d",
                                    carte.getNume(), carte.getAutor().getId(), 
                                    carte.getSectiune().getId(), carte.getAnPublicatie(),
                                    carte.esteDisponibil(), carte.getId());
        crudService.executeUpdate(query);
    }
    
    // stergere carte
    public void stergeCarte(int idCarte) {
        String query = String.format("DELETE FROM carti WHERE id=%d", idCarte);
        crudService.executeUpdate(query);
    }
    
    // obt carte dupa ID
    public Carte getCarteById(int id) {
        String query = "SELECT * FROM carti c " +
                       "JOIN autori a ON c.id_autor = a.id " +
                       "JOIN sectiuni s ON c.id_sectiune = s.id " +
                       "WHERE c.id=" + id;
        List<Carte> carti = crudService.executeQuery(query, new CRUDService.ResultSetMapper<Carte>() {
            @Override
            public Carte map(ResultSet rs) throws SQLException {
                Autor autor = new Autor(
                    rs.getString("prenume"),
                    rs.getString("nume"),
                    rs.getString("nationalitate")
                );
                autor.setId(rs.getInt("a.id"));
                
                Sectiune sectiune = new Sectiune(
                    rs.getString("nume_sectiune"),
                    rs.getString("locatie")
                );
                sectiune.setId(rs.getInt("id"));
                
                String tipCarte = rs.getString("tip_carte");
                if ("ROMAN".equals(tipCarte)) {
                    return new Roman(
                        rs.getString("nume"),
                        autor,
                        sectiune,
                        rs.getInt("an_publicatie"),
                        rs.getString("gen_literar"),
                        rs.getInt("numar_pagini")
                    );
                } else if ("EDITIE".equals(tipCarte)) {
                    return new EditieSpeciala(
                        rs.getString("nume"),
                        autor,
                        sectiune,
                        rs.getInt("an_publicatie"),
                        rs.getString("tip_editie"),
                        rs.getInt("numar_exemplare")
                    );
                } else {
                    Carte carte = new Carte(
                        rs.getString("nume"),
                        autor,
                        sectiune,
                        rs.getInt("an_publicatie")
                    );
                    carte.setEsteDisponibil(rs.getBoolean("este_disponibil"));
                    return carte;
                }
            }
        });

        return carti.isEmpty() ? null : carti.get(0);
    }

    public int countCarti() {
        String sql = "SELECT COUNT(*) FROM carti";
        try (Connection conn = DatabaseConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Carte> getCartiPentruAutor(int autorId) {
        List<Carte> carti = getToateCartile();
        return carti.stream()
                .filter(carte -> carte.getAutor().getId() == autorId)
                .collect(java.util.stream.Collectors.toList());
    }
}