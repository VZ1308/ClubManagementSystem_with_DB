import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class TableCreation {
    public static void createTables(Connection conn) {
        try (Statement stmt = conn.createStatement()) {

            // Tabelle für Vereine erstellen
            String createVereinTableSQL = "CREATE TABLE IF NOT EXISTS Verein (" +
                    "vereinId INT AUTO_INCREMENT PRIMARY KEY, " +
                    "name VARCHAR(100), " +
                    "gruendung INT)";
            stmt.executeUpdate(createVereinTableSQL);

            // Tabelle für Mannschaften erstellen
            String createMannschaftTableSQL = "CREATE TABLE IF NOT EXISTS Mannschaft (" +
                    "mannschaftId INT AUTO_INCREMENT PRIMARY KEY, " +
                    "name VARCHAR(100), " +
                    "sportart VARCHAR(50), " +
                    "vereinId INT, " +
                    "gruendung DATE, " +
                    "FOREIGN KEY (vereinId) REFERENCES Verein(vereinId))";
            stmt.executeUpdate(createMannschaftTableSQL);

            // Tabelle für Trainer erstellen
            String createTrainerTableSQL = "CREATE TABLE IF NOT EXISTS Trainer (" +
                    "trainerId INT AUTO_INCREMENT PRIMARY KEY, " +
                    "name VARCHAR(100), " +
                    "geburtsdatum DATE, " +
                    "qualifikation VARCHAR(100), " +
                    "mannschaftId INT, " +
                    "FOREIGN KEY (mannschaftId) REFERENCES Mannschaft(mannschaftId))";
            stmt.executeUpdate(createTrainerTableSQL);

            // Tabelle für Mitglieder erstellen
            String createMitgliedTableSQL = "CREATE TABLE IF NOT EXISTS Mitglied (" +
                    "mitgliedId INT AUTO_INCREMENT PRIMARY KEY, " +
                    "name VARCHAR(100), " +
                    "geburtsdatum DATE, " +
                    "mitgliedsnummer VARCHAR(50), " +
                    "mannschaftId INT, " +
                    "FOREIGN KEY (mannschaftId) REFERENCES Mannschaft(mannschaftId))";
            stmt.executeUpdate(createMitgliedTableSQL);

            System.out.println("Tabellen erfolgreich erstellt.");

        } catch (SQLException e) {
            System.out.println("Ein Fehler ist aufgetreten: " + e.getMessage());
            e.printStackTrace(); // Detaillierte Fehlerausgabe
        }
    }
}
