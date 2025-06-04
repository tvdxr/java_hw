package withDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.sql.Statement;

public class SectiuneService {
    private static SectiuneService instance;
    private final CRUDService<Sectiune> crudService;
    
    private SectiuneService() {
        crudService = CRUDService.getInstance();
    }
    
    public static synchronized SectiuneService getInstance() {
        if (instance == null) {
            instance = new SectiuneService();
        }
        return instance;
    }
    
    // creare tabel
    public void createTable() {
        String query = "CREATE TABLE IF NOT EXISTS sectiuni (" +
                       "id INT AUTO_INCREMENT PRIMARY KEY, " +
                       "nume_sectiune VARCHAR(50) NOT NULL, " +
                       "locatie VARCHAR(50) NOT NULL)";
        crudService.executeUpdate(query);
    }
    
    // adauga secțiune
    public void adaugaSectiune(Sectiune sectiune) {
        String query = String.format("INSERT INTO sectiuni (nume_sectiune, locatie) " +
                                   "VALUES ('%s', '%s')",
                                   sectiune.getNumeSectiune(), sectiune.getLocatie());
        crudService.executeUpdate(query);
    }
    
    // obt toate sectiunile
    public List<Sectiune> getToateSectiunile() {
        String query = "SELECT * FROM sectiuni";
        return crudService.executeQuery(query, new CRUDService.ResultSetMapper<Sectiune>() {
            @Override
            public Sectiune map(ResultSet rs) throws SQLException {
                Sectiune sectiune = new Sectiune(
                    rs.getString("nume_sectiune"),
                    rs.getString("locatie")
                );
                sectiune.setId(rs.getInt("id"));
                return sectiune;
            }
        });
    }
    
    // update secțiune
    public void actualizeazaSectiune(Sectiune sectiune) {
        String query = String.format("UPDATE sectiuni SET nume_sectiune='%s', locatie='%s' WHERE id=%d",
                                   sectiune.getNumeSectiune(), sectiune.getLocatie(), sectiune.getId());
        crudService.executeUpdate(query);
    }
    
    // sterge secțiune
    public void stergeSectiune(int idSectiune) {
        String query = String.format("DELETE FROM sectiuni WHERE id=%d", idSectiune);
        crudService.executeUpdate(query);
    }
    
    public int countSectiuni() {
    String sql = "SELECT COUNT(*) FROM sectiuni";
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

    public Sectiune getSectiuneByNume(String nume) {
        String sql = "SELECT * FROM sectiuni WHERE nume_sectiune = ?";
        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nume);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Sectiune sectiune = new Sectiune(
                        rs.getString("nume_sectiune"),
                        rs.getString("locatie")
                    );
                    sectiune.setId(rs.getInt("id"));
                    return sectiune;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    // sectiune după ID
    public Sectiune getSectiuneById(int id) {
        String query = "SELECT * FROM sectiuni WHERE id=" + id;
        List<Sectiune> sectiuni = crudService.executeQuery(query, new CRUDService.ResultSetMapper<Sectiune>() {
            @Override
            public Sectiune map(ResultSet rs) throws SQLException {
                Sectiune sectiune = new Sectiune(
                    rs.getString("nume_sectiune"),
                    rs.getString("locatie")
                );
                sectiune.setId(rs.getInt("id"));
                return sectiune;
            }
        });
        
        return sectiuni.isEmpty() ? null : sectiuni.get(0);
    }
}