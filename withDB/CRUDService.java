package withDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class CRUDService<T> {
    private static CRUDService<?> instance;
    
    private CRUDService() {}
    
    public static synchronized <T> CRUDService<T> getInstance() {
        if (instance == null) {
            instance = new CRUDService<>();
        }
        return (CRUDService<T>) instance;
    }
    
    public List<T> executeQuery(String query, ResultSetMapper<T> mapper) {
        List<T> results = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                results.add(mapper.map(rs));
            }
        } catch (SQLException e) {
            System.out.println("Eroare query: " + e.getMessage());
        }
        return results;
    }
    
    public void executeUpdate(String sql) {
        try (Connection conn = DatabaseConnection.getConnection();
            Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            System.err.println("Eroare update: " + e.getMessage());
        }
    }
    
    public interface ResultSetMapper<T> {
        T map(ResultSet rs) throws SQLException;
    }
}