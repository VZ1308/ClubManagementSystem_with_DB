import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MitgliedDAO {
    private Connection connection;

    // Konstruktor, um die Verbindung zur Datenbank zu setzen
    public MitgliedDAO(Connection connection) {
        this.connection = connection;
    }

    // Methode zum Erstellen eines neuen Mitglieds in der Datenbank
    public void createMitglied(Mitglied mitglied) throws SQLException {
        // SQL-Abfrage zum Einfügen eines neuen Mitglieds
        String query = "INSERT INTO Mitglied (name, geburtsdatum, mitgliedsnummer, mannschaftId) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, mitglied.getName());
            statement.setDate(2, new java.sql.Date(mitglied.getGeburtsdatum().getTime()));
            statement.setString(3, mitglied.getMitgliedsnummer());
            statement.setInt(4, mitglied.getMannschaftId()); // Hier wird mannschaftId gesetzt
            statement.executeUpdate();

            // Die generierte ID des neuen Mitglieds abrufen und setzen
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    mitglied.setId(generatedKeys.getInt(1));
                }
            }
        }
    }

    // Methode zum Abrufen eines Mitglieds anhand der ID
    public Mitglied getMitgliedById(int mitgliedId) throws SQLException {
        String query = "SELECT * FROM Mitglied WHERE mitgliedId = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, mitgliedId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Mitglied(
                            resultSet.getInt("mitgliedId"),
                            resultSet.getString("name"),
                            resultSet.getDate("geburtsdatum"),
                            resultSet.getString("mitgliedsnummer"),
                            resultSet.getInt("mannschaftId") // Hier wird mannschaftId abgerufen
                    );
                }
            }
        }
        return null;
    }

    // Methode zum Abrufen aller Mitglieder
    public List<Mitglied> getAllMitglieder() throws SQLException {
        List<Mitglied> mitglieder = new ArrayList<>();
        String query = "SELECT * FROM Mitglied";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                Mitglied mitglied = new Mitglied(
                        resultSet.getInt("mitgliedId"),
                        resultSet.getString("name"),
                        resultSet.getDate("geburtsdatum"),
                        resultSet.getString("mitgliedsnummer"),
                        resultSet.getInt("mannschaftId") // Hier wird mannschaftId abgerufen
                );
                mitglieder.add(mitglied);
            }
        }
        return mitglieder;
    }

    // Methode zum Löschen eines Mitglieds anhand der ID
    public void deleteMitglied(int id) throws SQLException {
        String query = "DELETE FROM Mitglied WHERE mitgliedId = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }
}
