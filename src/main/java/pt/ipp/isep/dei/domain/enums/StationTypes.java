package pt.ipp.isep.dei.domain.enums;

public enum StationTypes {
    DEPOT(50000, 9,"depot"),
    STATION(100000, 16,"station"),
    TERMINAL(200000, 25,"terminal");

    private final int cost;
    private final int area;
    private final String type;

    StationTypes(int cost, int area, String type) {
        this.cost = cost;
        this.area = area;
        this.type = type;
    }


    public int getCost() {
        return cost;
    }

    public int getArea() {
        return area;
    }

    public String getType() { return type;}

    public String toString() {
        return type.substring(0, 1).toUpperCase() + type.substring(1).toLowerCase(); // Ex: "Station"
    }

}
