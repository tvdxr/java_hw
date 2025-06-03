package withDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.sql.Statement;

public class AutorService {
    private static AutorService instance;
    private final CRUDService<Autor> crudService;
    private static final String TABLE_NAME = "autori"; // Consistent table name
    
    private AutorService() {
        crudService = CRUDService.getInstance();
    }
    
    public static synchronized AutorService getInstance() {
        if (instance == null) {
            instance = new AutorService();
        }
        return instance;
    }
    
    // creare tabel
    public void createTable() {
        String query = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                       "id INT AUTO_INCREMENT PRIMARY KEY, " +
                       "prenume VARCHAR(50) NOT NULL, " +
                       "nume VARCHAR(50) NOT NULL, " +
                       "nationalitate VARCHAR(50) NOT NULL)";
        crudService.executeUpdate(query);
    }
    
    // adaugare autor
    public void adaugaAutor(Autor autor) {
        String query = String.format("INSERT INTO " + TABLE_NAME + " (prenume, nume, nationalitate) " +
                                   "VALUES ('%s', '%s', '%s')",
                                   autor.getPrenume(), autor.getNume(), autor.getNationalitate());
        crudService.executeUpdate(query);
    }
    
    // obtinere toti autorii
    public List<Autor> getTotiAutorii() {
        String query = "SELECT * FROM " + TABLE_NAME;
        return crudService.executeQuery(query, new CRUDService.ResultSetMapper<Autor>() {
            @Override
            public Autor map(ResultSet rs) throws SQLException {
                Autor autor = new Autor(
                    rs.getString("prenume"),
                    rs.getString("nume"),
                    rs.getString("nationalitate")
                );
                autor.setId(rs.getInt("id"));
                return autor;
            }
        });
    }
    
    // update autor
    public void actualizeazaAutor(Autor autor) {
        String query = String.format("UPDATE " + TABLE_NAME + " SET prenume='%s', nume='%s', nationalitate='%s' WHERE id=%d",
                                   autor.getPrenume(), autor.getNume(), autor.getNationalitate(), autor.getId());
        crudService.executeUpdate(query);
    }
    
    // sterge autor
    public void stergeAutor(int idAutor) {
        String query = String.format("DELETE FROM " + TABLE_NAME + " WHERE id=%d", idAutor);
        crudService.executeUpdate(query);
    }
    
    public int countAutori() {
        String sql = "SELECT COUNT(*) FROM " + TABLE_NAME;
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

    public Autor getAutorByNumeComplet(String prenume, String nume) {
        String sql = "SELECT * FROM autori WHERE prenume = ? AND nume = ?";
        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, prenume);
            pstmt.setString(2, nume);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Autor autor = new Autor(
                        rs.getString("prenume"),
                        rs.getString("nume"),
                        rs.getString("nationalitate")
                    );
                    autor.setId(rs.getInt("id"));
                    return autor;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    // autor dupa ID
    public Autor getAutorById(int id) {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE id=" + id;
        List<Autor> autori = crudService.executeQuery(query, new CRUDService.ResultSetMapper<Autor>() {
            @Override
            public Autor map(ResultSet rs) throws SQLException {
                Autor autor = new Autor(
                    rs.getString("prenume"),
                    rs.getString("nume"),
                    rs.getString("nationalitate")
                );
                autor.setId(rs.getInt("id"));
                return autor;
            }
        });
        
        return autori.isEmpty() ? null : autori.get(0);
    }
}