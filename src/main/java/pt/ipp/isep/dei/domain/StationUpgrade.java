package pt.ipp.isep.dei.domain;


public class StationUpgrade {
    private final String type;
    private final String subType;

    public StationUpgrade(String type, String subType) {
        this.type = type;
        this.subType = subType;
    }

    // Getters
    public String getType() { return type; }
    public String getSubType() { return subType; }

    @Override
    public String toString() {
        return type + (subType != null ? " (" + subType + ")" : "");
    }
}