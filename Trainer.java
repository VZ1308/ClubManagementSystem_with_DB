import java.util.Date;



public class Trainer {
    private int trainerId;
    private String name;
    private Date geburtsdatum;
    private String qualifikation;
    private int mannschaftId;

    public Trainer(int trainerId, String name, Date geburtsdatum, String qualifikation, int mannschaftId) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name darf nicht leer sein.");
        }
        if (qualifikation == null || qualifikation.isEmpty()) {
            throw new IllegalArgumentException("Qualifikation darf nicht leer sein.");
        }
        if (mannschaftId <= 0) {
            throw new IllegalArgumentException("Mannschaft ID muss eine positive Zahl sein.");
        }
        this.trainerId = trainerId;
        this.name = name;
        this.geburtsdatum = geburtsdatum;
        this.qualifikation = qualifikation;
        this.mannschaftId = mannschaftId;
    }

    // Getter and Setter methods
    public int getId() {
        return trainerId;
    }

    public void setId(int id) {
        this.trainerId = id;
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

    public String getQualifikation() {
        return qualifikation;
    }

    public void setQualifikation(String qualifikation) {
        this.qualifikation = qualifikation;
    }

    public int getMannschaftId() {
        return mannschaftId;
    }

    public void setMannschaftId(int mannschaftId) {
        this.mannschaftId = mannschaftId;
    }
}
