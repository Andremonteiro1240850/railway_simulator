package pt.ipp.isep.dei.domain;

import java.util.ArrayList;
import java.util.List;

//us06

public class Building {
    private String type;
    private int cost;
    private int availableFromYear;
    private List<String> incompatibleWith;

    public Building(String type, int cost, int availableFromYear) {
        this.type = type;
        this.cost = cost;
        this.availableFromYear = availableFromYear;
        this.incompatibleWith = new ArrayList<>();

        // Define incompatible buildings
        if (type.equals("Small Hotel")) {
            this.incompatibleWith.add("Grand Hotel");
        } else if (type.equals("Grand Hotel")) {
            this.incompatibleWith.add("Small Hotel");
        } else if (type.equals("Telephone")) {
            this.incompatibleWith.add("Telegraph");
        }
    }

    // Getters
    public String getType() { return type; }
    public int getCost() { return cost; }
    public int getAvailableFromYear() { return availableFromYear; }
    public List<String> getIncompatibleWith() { return incompatibleWith; }

    public boolean isAvailableInYear(int year) {
        return year >= availableFromYear;
    }

    public boolean isCompatibleWith(Station station) {
        return incompatibleWith.stream()
                .noneMatch(station::hasBuilding);
    }
}