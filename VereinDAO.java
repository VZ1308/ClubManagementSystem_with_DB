import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VereinDAO {

    private Connection conn;

    public VereinDAO(Connection conn) {
        this.conn = conn;
    }

    public void createVerein(Verein verein) throws SQLException {
        String sql = "INSERT INTO Verein (name, gruendung) VALUES (?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, verein.getName());
            pstmt.setInt(2, verein.getGruendung());
            pstmt.executeUpdate();

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    verein.setId(generatedKeys.getInt(1)); // Setzt die generierte ID
                } else {
                    throw new SQLException("Fehler beim Abrufen der generierten ID.");
                }
            }
        }
    }

    public void deleteVerein(int vereinId) throws SQLException {
        String sql = "DELETE FROM Verein WHERE vereinId = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, vereinId);
            pstmt.executeUpdate();
        }
    }

    public Verein getVereinById(int vereinId) throws SQLException {
        String sql = "SELECT * FROM Verein WHERE vereinId = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, vereinId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Verein(
                            rs.getInt("vereinId"),
                            rs.getString("name"),
                            rs.getInt("gruendung")
                    );
                } else {
                    return null;
                }
            }
        }
    }

    public List<Verein> getAllVereine() throws SQLException {
        String sql = "SELECT * FROM Verein";
        List<Verein> vereine = new ArrayList<>();
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                vereine.add(new Verein(
                        rs.getInt("vereinId"),
                        rs.getString("name"),
                        rs.getInt("gruendung")
                ));
            }
        }
        return vereine;
    }
}
