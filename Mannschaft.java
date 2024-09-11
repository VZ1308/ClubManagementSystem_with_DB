public class Mannschaft {
    private int mannschaftId;
    private String name;
    private String sportart;
    private int gruendung; // Das Gründungsjahr hinzufügen
    private int vereinId;

    public Mannschaft(int mannschaftId, String name, String sportart, int gruendung, int vereinId) {
        this.mannschaftId = mannschaftId;
        this.name = name;
        this.sportart = sportart;
        this.gruendung = gruendung;
        this.vereinId = vereinId;
    }

    public int getId() {
        return mannschaftId;
    }

    public int setId(int id) {
        return this.mannschaftId = id;
    }

    public String getName() {
        return name;
    }

    public String getSportart() {
        return sportart;
    }

    public int getGruendungsjahr() {
        return gruendung;
    }

    public int getVereinId() {
        return vereinId;
    }

    @Override
    public String toString() {
        return "ID: " + mannschaftId + ", Name: " + name + ", Sportart: " + sportart + ", Gründungsjahr: " + gruendung + ", Vereins-ID: " + vereinId;
    }
}
