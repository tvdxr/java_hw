package withDB;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Audit {
    private static Audit instance;
    private static final String FILE_NAME = "audit.csv";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private Audit() {}

    public static Audit getInstance() {
        if (instance == null) {
            instance = new Audit();
        }
        return instance;
    }

    public void logAction(String actionName) {
        try (FileWriter writer = new FileWriter(FILE_NAME, true)) {
            String timestamp = LocalDateTime.now().format(FORMATTER);
            writer.append(actionName).append(",").append(timestamp).append("\n");
        } catch (IOException e) {
            System.err.println("Eroare la scrierea in audit.csv: " + e.getMessage());
        }
    }
}