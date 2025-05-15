package pt.ipp.isep.dei.domain;

import java.util.ArrayList;
import java.util.List;

public class RailwayNetwork {
    private static RailwayNetwork instance;
    private BudgetManager budgetManager;
    private List<Station> stations = new ArrayList<>();

    private RailwayNetwork(BudgetManager budgetManager) {
        this.budgetManager = budgetManager;
    }

    public static synchronized RailwayNetwork getInstance() {
        if (instance == null) {
            instance = new RailwayNetwork(BudgetManager.getInstance());
        }
        return instance;
    }

    public Station buildStation(String type, Point location, String name, Point center) {
        Station station = new Station(type, location, name, center);
        stations.add(station);
        budgetManager.deductCost(getStationCost(type));
        return station;
    }

    private int getStationCost(String type) {
        switch (type) {
            case "Depot": return 50;
            case "Station": return 100;
            case "Terminal": return 200;
            default: throw new IllegalArgumentException("Unknown station type");
        }
    }
}