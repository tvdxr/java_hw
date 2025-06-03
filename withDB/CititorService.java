package withDB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.sql.Statement;
import java.sql.PreparedStatement;

public class CititorService {
    private static CititorService instance;
    private final CRUDService<Cititor> crudService;
    
    private CititorService() {
        crudService = CRUDService.getInstance();
    }
    
    public static synchronized CititorService getInstance() {
        if (instance == null) {
            instance = new CititorService();
        }
        return instance;
    }
    
    // creare tabel
    public void createTable() {
        String query = "CREATE TABLE IF NOT EXISTS cititori (" +
                       "id INT AUTO_INCREMENT PRIMARY KEY, " +
                       "nume VARCHAR(50) NOT NULL, " +
                       "prenume VARCHAR(50) NOT NULL, " +
                       "id_cititor INT NOT NULL UNIQUE, " +
                       "parola VARCHAR(50) NOT NULL)";
        crudService.executeUpdate(query);
    }
    
    // adaugare cititor
    public void adaugaCititor(Cititor cititor) {
        String query = String.format("INSERT INTO cititori (nume, prenume, id_cititor, parola) " +
                                   "VALUES ('%s', '%s', %d, '%s')",
                                   cititor.getNume(), cititor.getPrenume(), 
                                   cititor.getIdCititor(), cititor.getParola());
        crudService.executeUpdate(query);
    }
    
    // obt toti cititorii
    public List<Cititor> getTotiCititorii() {
        String query = "SELECT * FROM cititori";
        return crudService.executeQuery(query, new CRUDService.ResultSetMapper<Cititor>() {
            @Override
            public Cititor map(ResultSet rs) throws SQLException {
                Cititor cititor = new Cititor(
                    rs.getString("nume"),
                    rs.getString("prenume"),
                    rs.getInt("id_cititor"),
                    rs.getString("parola")
                );
                cititor.setId(rs.getInt("id"));
                return cititor;
            }
        });
    }
    
    // update cititor
    public void actualizeazaCititor(Cititor cititor) {
        String query = String.format("UPDATE cititori SET nume='%s', prenume='%s', " +
                                   "id_cititor=%d, parola='%s' WHERE id=%d",
                                   cititor.getNume(), cititor.getPrenume(), 
                                   cititor.getIdCititor(), cititor.getParola(), 
                                   cititor.getId());
        crudService.executeUpdate(query);
    }
    
    // sterge cititor
    public void stergeCititor(int idCititor) {
        String query = String.format("DELETE FROM cititori WHERE id=%d", idCititor);
        crudService.executeUpdate(query);
    }
    
    // cititor dupa ID
    public Cititor getCititorById(int id) {
        String query = "SELECT * FROM cititori WHERE id=" + id;
        List<Cititor> cititori = crudService.executeQuery(query, new CRUDService.ResultSetMapper<Cititor>() {
            @Override
            public Cititor map(ResultSet rs) throws SQLException {
                Cititor cititor = new Cititor(
                    rs.getString("nume"),
                    rs.getString("prenume"),
                    rs.getInt("id_cititor"),
                    rs.getString("parola")
                );
                cititor.setId(rs.getInt("id"));
                return cititor;
            }
        });
        
        return cititori.isEmpty() ? null : cititori.get(0);
    }

    public void inregistreazaCititor(Cititor cititor) {
        if (getCititorById(cititor.getId()) != null) {
            System.out.println("Cititorul cu ID-ul " + cititor.getId() + " exista deja.");
            return;
        }

        adaugaCititor(cititor);
    }

    public int countCititori() {
        String sql = "SELECT COUNT(*) FROM cititori";
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
}