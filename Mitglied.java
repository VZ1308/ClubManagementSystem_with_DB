import java.util.Date;

public class Mitglied {
    private int mitgliedId;
    private String name;
    private Date geburtsdatum;
    private String mitgliedsnummer;
    private int mannschaftId; // FK zu Mannschaft

    public Mitglied(int mitgliedId, String name, Date geburtsdatum, String mitgliedsnummer, int mannschaftId) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name darf nicht leer sein.");
        }
        if (geburtsdatum == null) {
            throw new IllegalArgumentException("Geburtsdatum darf nicht null sein.");
        }
        if (mitgliedsnummer == null || mitgliedsnummer.isEmpty()) {
            throw new IllegalArgumentException("Mitgliedsnummer darf nicht leer sein.");
        }
        this.mitgliedId = mitgliedId;
        this.name = name;
        this.geburtsdatum = geburtsdatum;
        this.mitgliedsnummer = mitgliedsnummer;
        this.mannschaftId = mannschaftId; // Setze die Mannschafts-ID
    }

    // Getter and Setter methods
    public int getId() {
        return mitgliedId;
    }

    public void setId(int id) {
        this.mitgliedId = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getGeburtsdatum() {
        return geburtsdatum;
    }

    public void setGeburtsdatum(Date geburtsdatum) {
        this.geburtsdatum = geburtsdatum;
    }

    public String getMitgliedsnummer() {
        return mitgliedsnummer;
    }

    public void setMitgliedsnummer(String mitgliedsnummer) {
        this.mitgliedsnummer = mitgliedsnummer;
    }

    public int getMannschaftId() {
        return mannschaftId;
    }

    public void setMannschaftId(int mannschaftId) {
        this.mannschaftId = mannschaftId;
    }

    @Override
    public String toString() {
        return "ID: " + mitgliedId + ", Name: " + name + ", Geburtsdatum: " + geburtsdatum +
                ", Mitgliedsnummer: " + mitgliedsnummer + ", Mannschafts-ID: " + mannschaftId;
    }
}
