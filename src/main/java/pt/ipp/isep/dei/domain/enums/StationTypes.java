package pt.ipp.isep.dei.domain.enums;

/**
 * Enum representing different types of train stations in the game,
 * each with a specific cost, area (number of tiles occupied), and label.
 */
public enum StationTypes {

    /**
     * Depot station — the smallest and cheapest station type.
     */
    DEPOT(50000, 9, "depot"),

    /**
     * Regular station — medium-sized station with moderate cost and area.
     */
    STATION(100000, 16, "station"),

    /**
     * Terminal station — the largest and most expensive station type.
     */
    TERMINAL(200000, 25, "terminal");

    private final int cost;
    private final int area;
    private final String type;

    /**
     * Constructs a StationTypes enum with given cost, area, and label.
     *
     * @param cost  the construction cost of the station
     * @param area  the area (in grid units) occupied by the station
     * @param type  the string label of the station type
     */
    StationTypes(int cost, int area, String type) {
        this.cost = cost;
        this.area = area;
        this.type = type;
    }

    /**
     * Returns the construction cost of the station type.
     *
     * @return the cost in game currency
     */
    public int getCost() {
        return cost;
    }

    /**
     * Returns the area occupied by the station type.
     *
     * @return the number of grid tiles
     */
    public int getArea() {
        return area;
    }

    /**
     * Returns the string identifier for the station type.
     *
     * @return the type string (e.g., "depot")
     */
    public String getType() {
        return type;
    }

    /**
     * Returns a nicely formatted version of the station type,
     * with the first letter capitalized.
     *
     * @return formatted string (e.g., "Station")
     */
    @Override
    public String toString() {
        return type.substring(0, 1).toUpperCase() + type.substring(1).toLowerCase();
    }
}

