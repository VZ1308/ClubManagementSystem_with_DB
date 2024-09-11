import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Verein {
    private int vereinId;
    private String name;
    private int gruendung;
    private List<Mannschaft> mannschaften;

    // Konstruktor
    public Verein(int vereinId, String name, int gruendung) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name darf nicht leer sein.");
        }
        if (gruendung < 1800 || gruendung > 2024) {
            throw new IllegalArgumentException("Gründungsjahr muss zwischen 1800 und 2024 liegen.");
        }

        this.vereinId = vereinId;
        this.name = name;
        this.gruendung = gruendung;
        this.mannschaften = new ArrayList<>();
    }

    // Getter und Setter
    public int getId() {
        return vereinId;
    }

    public void setId(int id) {
        this.vereinId = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGruendung() {
        return gruendung;
    }

    public void setGruendung(int gruendung) {
        this.gruendung = gruendung;
    }

    public List<Mannschaft> getMannschaften() {
        return mannschaften;
    }

    public void setMannschaften(List<Mannschaft> mannschaften) {
        this.mannschaften = mannschaften;
    }

    // Methode zum Hinzufügen einer Mannschaft
    public void addMannschaft(Mannschaft mannschaft) {
        mannschaften.add(mannschaft);
    }

    // Methode zum Entfernen einer Mannschaft anhand der Mannschafts-ID
    public void removeMannschaft(int mannschaftId) {
        Iterator<Mannschaft> iterator = mannschaften.iterator();  // Iterator über die Liste der Mannschaften erstellen
        while (iterator.hasNext()) {
            Mannschaft mannschaft = iterator.next();  // Das nächste Mannschaftsobjekt holen
            if (mannschaft.getId() == mannschaftId) {  // Prüfen, ob die ID übereinstimmt
                iterator.remove();  // Mannschaft entfernen
                break;  // Schleife beenden, da die Mannschaft gefunden und entfernt wurde
            }
        }
    }
}
