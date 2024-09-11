import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class MenuHandling {

    private static Scanner scanner = new Scanner(System.in);
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public static void startMenu() {
        while (true) {
            System.out.println("--- Vereinsverwaltung ---");
            System.out.println("1. Verein verwalten");
            System.out.println("2. Mannschaft verwalten");
            System.out.println("3. Trainer verwalten");
            System.out.println("4. Mitglieder verwalten");
            System.out.println("5. Programm beenden");
            System.out.print("Ihre Auswahl: ");

            int choice = getValidInteger();

            switch (choice) {
                case 1:
                    verwalteVerein();
                    break;
                case 2:
                    verwalteMannschaft();
                    break;
                case 3:
                    verwalteTrainer();
                    break;
                case 4:
                    verwalteMitglieder();
                    break;
                case 5:
                    System.out.println("Programm wird beendet.");
                    scanner.close();
                    return; // Programm beenden
                default:
                    System.out.println("Ungültige Auswahl! Bitte wählen Sie erneut.");
            }
        }
    }

    private static void verwalteVerein() {
        VereinDAO vereinDAO = new VereinDAO(DatabaseConnector.connect());

        while (true) {
            System.out.println("--- Vereinsverwaltung ---");
            System.out.println("1. Verein hinzufügen");
            System.out.println("2. Verein entfernen");
            System.out.println("3. Verein anzeigen");
            System.out.println("4. Alle Vereine anzeigen");
            System.out.println("5. Zurück zum Hauptmenü");
            System.out.print("Ihre Auswahl: ");

            int choice = getValidInteger();

            try {
                switch (choice) {
                    case 1:
                        System.out.print("Vereinsname: ");
                        String name = scanner.nextLine();

                        System.out.print("Gründungsjahr: ");
                        int gruendung = getValidInteger();

                        Verein neuerVerein = new Verein(0, name, gruendung);
                        vereinDAO.createVerein(neuerVerein);
                        System.out.println("Verein hinzugefügt: " + neuerVerein.getName());
                        break;
                    case 2:
                        System.out.print("ID des zu entfernenden Vereins: ");
                        int vereinId = getValidInteger();

// Überprüfen, ob der Verein existiert
                        if (vereinDAO.getVereinById(vereinId) == null) {
                            System.out.println("Verein nicht gefunden. Bitte überprüfen Sie die ID und versuchen Sie es erneut.");
                            break;
                        }

// Überprüfen, ob noch Mannschaften mit diesem Verein verknüpft sind
                        String checkQuery = "SELECT COUNT(*) FROM mannschaft WHERE vereinId = ?";
                        DatabaseMetaData trainerDAO = null;
                        try (PreparedStatement checkStmt = trainerDAO.getConnection().prepareStatement(checkQuery)) {
                            checkStmt.setInt(1, vereinId);
                            ResultSet rs = checkStmt.executeQuery();
                            if (rs.next() && rs.getInt(1) > 0) {
                                System.out.println("Kann den Verein nicht löschen, da noch Mannschaften damit verknüpft sind.");
                                break; // Löschen verhindern, da noch verknüpfte Mannschaften existieren
                            }
                        } catch (SQLException e) {
                            System.out.println("Fehler bei der Überprüfung der Mannschaften: " + e.getMessage());
                            break;
                        }

// Wenn keine Mannschaften verknüpft sind, Verein löschen
                        try {
                            vereinDAO.deleteVerein(vereinId);
                            System.out.println("Verein erfolgreich entfernt.");
                        } catch (SQLException e) {
                            System.out.println("Fehler bei der Datenbankoperation: " + e.getMessage());
                        }
                        break;


                    case 3:
                        System.out.print("ID des anzuzeigenden Vereins: ");
                        int anzeigeVereinId = getValidInteger();

                        Verein anzeigeVerein = vereinDAO.getVereinById(anzeigeVereinId);
                        if (anzeigeVerein != null) {
                            System.out.println("Verein: " + anzeigeVerein.getName() + ", Gründungsjahr: " + anzeigeVerein.getGruendung());
                        } else {
                            System.out.println("Verein nicht gefunden.");
                        }
                        break;
                    case 4:
                        System.out.println("--- Alle Vereine ---");
                        List<Verein> alleVereine = vereinDAO.getAllVereine();
                        for (Verein v : alleVereine) {
                            System.out.println("ID: " + v.getId() + ", Name: " + v.getName() + ", Gründungsjahr: " + v.getGruendung());
                        }
                        break;
                    case 5:
                        return; // Zurück zum Hauptmenü
                    default:
                        System.out.println("Ungültige Auswahl! Bitte wählen Sie erneut.");
                }
            } catch (SQLException e) {
                System.out.println("Fehler bei der Datenbankoperation: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private static void verwalteMannschaft() {
        MannschaftDAO mannschaftDAO = new MannschaftDAO(DatabaseConnector.connect());

        while (true) {
            System.out.println("--- Mannschaftsverwaltung ---");
            System.out.println("1. Mannschaft hinzufügen");
            System.out.println("2. Mannschaft entfernen");
            System.out.println("3. Mannschaft anzeigen");
            System.out.println("4. Alle Mannschaften anzeigen");
            System.out.println("5. Zurück zum Hauptmenü");
            System.out.print("Ihre Auswahl: ");

            int choice = getValidInteger();

            try {
                switch (choice) {
                    case 1:
                        System.out.print("Mannschaftsname: ");
                        String name = scanner.nextLine();

                        System.out.print("Sportart: ");
                        String sportart = scanner.nextLine();

                        System.out.print("Gründungsjahr: ");
                        int gruendung = getValidInteger();

                        System.out.print("Vereins-ID: ");
                        int vereinId = getValidInteger();

                        if (mannschaftDAO.getMannschaftById(vereinId) == null) {
                            System.out.println("Verein nicht gefunden. Bitte überprüfen Sie die ID und versuchen Sie es erneut.");
                            break;
                        }

                        Mannschaft neueMannschaft = new Mannschaft(0, name, sportart, gruendung, vereinId);
                        mannschaftDAO.createMannschaft(neueMannschaft);
                        System.out.println("Mannschaft hinzugefügt: " + neueMannschaft.getName());
                        break;

                    case 2:
                        System.out.print("ID der zu entfernenden Mannschaft: ");
                        int mannschaftId = getValidInteger();

                        if (mannschaftDAO.getMannschaftById(mannschaftId) == null) {
                            System.out.println("Mannschaft nicht gefunden. Bitte überprüfen Sie die ID und versuchen Sie es erneut.");
                            break;
                        }

                        mannschaftDAO.deleteMannschaft(mannschaftId);
                        System.out.println("Mannschaft entfernt.");
                        break;

                    case 3:
                        System.out.print("ID der anzuzeigenden Mannschaft: ");
                        int anzeigeMannschaftId = getValidInteger();

                        Mannschaft anzeigeMannschaft = mannschaftDAO.getMannschaftById(anzeigeMannschaftId);
                        if (anzeigeMannschaft != null) {
                            System.out.println("Mannschaft: " + anzeigeMannschaft.getName() +
                                    ", Sportart: " + anzeigeMannschaft.getSportart() +
                                    ", Gründungsjahr: " + anzeigeMannschaft.getGruendungsjahr());
                        } else {
                            System.out.println("Mannschaft nicht gefunden.");
                        }
                        break;

                    case 4:
                        System.out.println("--- Alle Mannschaften ---");
                        List<Mannschaft> alleMannschaften = mannschaftDAO.getAllMannschaften();
                        for (Mannschaft m : alleMannschaften) {
                            System.out.println("ID: " + m.getId() +
                                    ", Name: " + m.getName() +
                                    ", Sportart: " + m.getSportart() +
                                    ", Gründungsjahr: " + m.getGruendungsjahr());
                        }
                        break;

                    case 5:
                        return; // Zurück zum Hauptmenü
                    default:
                        System.out.println("Ungültige Auswahl! Bitte wählen Sie erneut.");
                }
            } catch (SQLException e) {
                System.out.println("Fehler bei der Datenbankoperation: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private static void verwalteTrainer() {
        TrainerDAO trainerDAO = new TrainerDAO(DatabaseConnector.connect());

        sdf.setLenient(false); // Verhindert ungenaue Datumseingaben

        while (true) {
            System.out.println("--- Trainerverwaltung ---");
            System.out.println("1. Trainer hinzufügen");
            System.out.println("2. Trainer entfernen");
            System.out.println("3. Trainer anzeigen");
            System.out.println("4. Alle Trainer anzeigen");
            System.out.println("5. Zurück zum Hauptmenü");
            System.out.print("Ihre Auswahl: ");

            int choice = getValidInteger();

            try {
                switch (choice) {
                    case 1:
                        System.out.print("Trainername: ");
                        String name = scanner.nextLine();

                        System.out.print("Mannschafts-ID: ");
                        int mannschaftId = getValidInteger();

                        MannschaftDAO mannschaftDAO = new MannschaftDAO(DatabaseConnector.connect());
                        if (mannschaftDAO.getMannschaftById(mannschaftId) == null) {
                            System.out.println("Mannschaft nicht gefunden. Bitte überprüfen Sie die ID und versuchen Sie es erneut.");
                            break;
                        }

                        System.out.print("Geburtsdatum (im Format yyyy-MM-dd): ");
                        String geburtsdatumString = scanner.nextLine();
                        Date geburtsdatum = null;
                        try {
                            geburtsdatum = sdf.parse(geburtsdatumString); // Konvertiere String in Date
                        } catch (ParseException e) {
                            System.out.println("Fehler beim Parsen des Datums. Bitte im Format yyyy-MM-dd eingeben.");
                            break;
                        }

                        System.out.print("Qualifikation: ");
                        String qualifikation = scanner.nextLine();

                        Trainer neuerTrainer = new Trainer(0, name, geburtsdatum, qualifikation, mannschaftId);
                        trainerDAO.createTrainer(neuerTrainer);
                        System.out.println("Trainer hinzugefügt: " + neuerTrainer.getName());
                        break;

                    case 2:
                        System.out.print("ID des zu entfernenden Trainers: ");
                        int trainerId = getValidInteger();

                        if (trainerDAO.getTrainerById(trainerId) == null) {
                            System.out.println("Trainer nicht gefunden. Bitte überprüfen Sie die ID und versuchen Sie es erneut.");
                            break;
                        }

                        trainerDAO.deleteTrainer(trainerId);
                        System.out.println("Trainer entfernt.");
                        break;

                    case 3:
                        System.out.print("ID des anzuzeigenden Trainers: ");
                        int anzeigeTrainerId = getValidInteger();

                        Trainer anzeigeTrainer = trainerDAO.getTrainerById(anzeigeTrainerId);
                        if (anzeigeTrainer != null) {
                            System.out.println("Trainer: " + anzeigeTrainer.getName() +
                                    ", Geburtsdatum: " + sdf.format(anzeigeTrainer.getGeburtsdatum()) +
                                    ", Qualifikation: " + anzeigeTrainer.getQualifikation());
                        } else {
                            System.out.println("Trainer nicht gefunden.");
                        }
                        break;

                    case 4:
                        System.out.println("--- Alle Trainer ---");
                        List<Trainer> alleTrainer = trainerDAO.getAllTrainers();
                        for (Trainer t : alleTrainer) {
                            System.out.println("ID: " + t.getId() +
                                    ", Name: " + t.getName() +
                                    ", Geburtsdatum: " + sdf.format(t.getGeburtsdatum()) +
                                    ", Qualifikation: " + t.getQualifikation());
                        }
                        break;

                    case 5:
                        return; // Zurück zum Hauptmenü
                    default:
                        System.out.println("Ungültige Auswahl! Bitte wählen Sie erneut.");
                }
            } catch (SQLException e) {
                System.out.println("Fehler bei der Datenbankoperation: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private static void verwalteMitglieder() {
        MitgliedDAO mitgliedDAO = new MitgliedDAO(DatabaseConnector.connect());

        sdf.setLenient(false); // Verhindert ungenaue Datumseingaben

        while (true) {
            System.out.println("--- Mitgliederverwaltung ---");
            System.out.println("1. Mitglied hinzufügen");
            System.out.println("2. Mitglied entfernen");
            System.out.println("3. Mitglied anzeigen");
            System.out.println("4. Alle Mitglieder anzeigen");
            System.out.println("5. Zurück zum Hauptmenü");
            System.out.print("Ihre Auswahl: ");

            int choice = getValidInteger();

            try {
                switch (choice) {
                    case 1:
                        System.out.print("Mitgliedsname: ");
                        String name = scanner.nextLine();

                        System.out.print("Geburtsdatum (im Format yyyy-MM-dd): ");
                        String geburtsdatumString = scanner.nextLine();
                        Date geburtsdatum = null;
                        try {
                            geburtsdatum = sdf.parse(geburtsdatumString); // Konvertiere String in Date
                        } catch (ParseException e) {
                            System.out.println("Fehler beim Parsen des Datums. Bitte im Format yyyy-MM-dd eingeben.");
                            break;
                        }

                        System.out.print("Mitgliedsnummer: ");
                        String mitgliedsnummer = scanner.nextLine();

                        System.out.print("Mannschafts-ID: ");
                        int mannschaftId = getValidInteger();

                        MannschaftDAO mannschaftDAO = new MannschaftDAO(DatabaseConnector.connect());
                        if (mannschaftDAO.getMannschaftById(mannschaftId) == null) {
                            System.out.println("Mannschaft nicht gefunden. Bitte überprüfen Sie die ID und versuchen Sie es erneut.");
                            break;
                        }


                        Mitglied neuesMitglied = new Mitglied(0, name, geburtsdatum, mitgliedsnummer, mannschaftId);
                        mitgliedDAO.createMitglied(neuesMitglied);
                        System.out.println("Mitglied hinzugefügt: " + neuesMitglied.getName());
                        break;

                    case 2:
                        System.out.print("ID des zu entfernenden Mitglieds: ");
                        int mitgliedId = getValidInteger();

                        if (mitgliedDAO.getMitgliedById(mitgliedId) == null) {
                            System.out.println("Mitglied nicht gefunden. Bitte überprüfen Sie die ID und versuchen Sie es erneut.");
                            break;
                        }

                        mitgliedDAO.deleteMitglied(mitgliedId);
                        System.out.println("Mitglied entfernt.");
                        break;

                    case 3:
                        System.out.print("ID des anzuzeigenden Mitglieds: ");
                        int anzeigeMitgliedId = getValidInteger();

                        Mitglied anzeigeMitglied = mitgliedDAO.getMitgliedById(anzeigeMitgliedId);
                        if (anzeigeMitglied != null) {
                            System.out.println("Mitglied: " + anzeigeMitglied.getName() +
                                    ", Geburtsdatum: " + sdf.format(anzeigeMitglied.getGeburtsdatum()) +
                                    ", Mitgliedsnummer: " + anzeigeMitglied.getMitgliedsnummer());
                        } else {
                            System.out.println("Mitglied nicht gefunden.");
                        }
                        break;

                    case 4:
                        System.out.println("--- Alle Mitglieder ---");
                        List<Mitglied> alleMitglieder = mitgliedDAO.getAllMitglieder();
                        for (Mitglied m : alleMitglieder) {
                            System.out.println("ID: " + m.getId() +
                                    ", Name: " + m.getName() +
                                    ", Geburtsdatum: " + sdf.format(m.getGeburtsdatum()) +
                                    ", Mitgliedsnummer: " + m.getMitgliedsnummer());
                        }
                        break;

                    case 5:
                        return; // Zurück zum Hauptmenü
                    default:
                        System.out.println("Ungültige Auswahl! Bitte wählen Sie erneut.");
                }
            } catch (SQLException e) {
                System.out.println("Fehler bei der Datenbankoperation: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private static int getValidInteger() {
        while (true) {
            if (scanner.hasNextInt()) {
                int value = scanner.nextInt();
                scanner.nextLine(); // Zeilenumbruch nach der Eingabe entfernen
                return value;
            } else {
                System.out.println("Ungültige Eingabe! Bitte eine gültige Zahl eingeben.");
                scanner.next(); // Den ungültigen Input löschen
            }
        }
    }
}
