import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TrainerDAO {
    private Connection connection;

    // Konstruktor, um die Verbindung zur Datenbank zu setzen
    public TrainerDAO(Connection connection) {
        this.connection = connection;
    }

    // Methode, um die Verbindung zu erhalten
    public Connection getConnection() {
        return connection;
    }

    // Methode zum Erstellen eines neuen Trainers in der Datenbank
    public void createTrainer(Trainer trainer) throws SQLException {
        // SQL-Abfrage zum Einfügen eines neuen Trainers
        String query = "INSERT INTO Trainer (name, geburtsdatum, qualifikation, mannschaftId) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, trainer.getName());
            statement.setDate(2, new java.sql.Date(trainer.getGeburtsdatum().getTime()));
            statement.setString(3, trainer.getQualifikation());
            statement.setInt(4, trainer.getMannschaftId());
            statement.executeUpdate();

            // Die generierte ID des neuen Trainers abrufen und setzen
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    trainer.setId(generatedKeys.getInt(1));
                }
            }
        }
    }

    // Methode zum Abrufen eines Trainers anhand der ID
    public Trainer getTrainerById(int trainerId) throws SQLException {
        String query = "SELECT * FROM Trainer WHERE trainerId = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, trainerId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Trainer(
                            resultSet.getInt("trainerId"),
                            resultSet.getString("name"),
                            resultSet.getDate("geburtsdatum"),
                            resultSet.getString("qualifikation"),
                            resultSet.getInt("mannschaftId")
                    );
                }
            }
        }
        return null;
    }

    // Methode zum Abrufen aller Trainer
    public List<Trainer> getAllTrainers() throws SQLException {
        List<Trainer> trainers = new ArrayList<>();
        String query = "SELECT * FROM Trainer";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                Trainer trainer = new Trainer(
                        resultSet.getInt("trainerId"),
                        resultSet.getString("name"),
                        resultSet.getDate("geburtsdatum"),
                        resultSet.getString("qualifikation"),
                        resultSet.getInt("mannschaftId")
                );
                trainers.add(trainer);
            }
        }
        return trainers;
    }

    // Methode zum Löschen eines Trainers anhand der ID
    public void deleteTrainer(int trainerId) throws SQLException {
        String query = "DELETE FROM Trainer WHERE trainerId = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, trainerId);
            statement.executeUpdate();
        }
    }
}
