package pt.ipp.isep.dei.domain.enums;

public enum StationTypes {
    DEPOT(50000, 3),
    STATION(100000, 4),
    TERMINAL(200000, 5);

    private final int cost;
    private final int coverageDiameter;

    StationTypes(int cost, int coverageDiameter) {
        this.cost = cost;
        this.coverageDiameter = coverageDiameter;
    }

    public int getCost() {
        return cost;
    }

    public int getCoverageDiameter() {
        return coverageDiameter;
    }

}
