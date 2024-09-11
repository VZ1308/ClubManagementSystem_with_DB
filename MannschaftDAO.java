import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MannschaftDAO {
    private Connection connection;

    // Konstruktor, um die Verbindung zur Datenbank zu setzen
    public MannschaftDAO(Connection connection) {
        this.connection = connection;
    }

    // Methode zum Erstellen einer neuen Mannschaft in der Datenbank
    public void createMannschaft(Mannschaft mannschaft) throws SQLException {
        // SQL-Abfrage zum Einfügen einer neuen Mannschaft
        String query = "INSERT INTO mannschaft (name, sportart, vereinId, gruendung ) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, mannschaft.getName());
            statement.setString(2, mannschaft.getSportart());
            statement.setInt(3, mannschaft.getVereinId());
            statement.setInt(4, mannschaft.getGruendungsjahr());
            statement.executeUpdate();

            // Die generierte ID der neuen Mannschaft abrufen und setzen
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    mannschaft.setId(generatedKeys.getInt(1));
                }
            }
        }
    }

    // Methode zum Abrufen einer Mannschaft anhand der ID
    public Mannschaft getMannschaftById(int mannschaftId) throws SQLException {
        String query = "SELECT * FROM mannschaft WHERE mannschaftId = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, mannschaftId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Mannschaft(
                            resultSet.getInt("mannschaftId"),
                            resultSet.getString("name"),
                            resultSet.getString("sportart"),
                            resultSet.getInt("vereinId"),
                            resultSet.getInt("gruendung")
                    );
                }
            }
        }
        return null;
    }

    // Methode zum Abrufen aller Mannschaften
    public List<Mannschaft> getAllMannschaften() throws SQLException {
        List<Mannschaft> mannschaften = new ArrayList<>();
        String query = "SELECT * FROM mannschaft";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                Mannschaft mannschaft = new Mannschaft(
                        resultSet.getInt("mannschaftId"),
                        resultSet.getString("name"),
                        resultSet.getString("sportart"),
                        resultSet.getInt("vereinId"),
                        resultSet.getInt("gruendung")
                );
                mannschaften.add(mannschaft);
            }
        }
        return mannschaften;
    }

    // Methode zum Löschen einer Mannschaft anhand der ID
    public void deleteMannschaft(int mannschaftId) throws SQLException {
        String query = "DELETE FROM mannschaft WHERE mannschaftId = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, mannschaftId);
            statement.executeUpdate();
        }
    }
}
