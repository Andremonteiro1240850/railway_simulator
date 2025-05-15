package pt.ipp.isep.dei.domain;


/**
 * Represents a locomotive.
 */
public class Locomotive {

    private final String id;
    private final String name;
    private final String type;
    private final int availableYear;

    public Locomotive(String id, String name, String type, int availableYear) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.availableYear = availableYear;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getType() { return type; }
    public int getAvailableYear() { return availableYear; }
}

