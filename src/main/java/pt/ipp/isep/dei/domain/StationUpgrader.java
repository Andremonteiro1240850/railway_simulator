package pt.ipp.isep.dei.domain;

import pt.ipp.isep.dei.repository.BuildingRepository;

//us06

public class StationUpgrader {
    private BudgetManager economy;

    public StationUpgrader(BudgetManager economy) {
        this.economy = economy;
    }

    public boolean applyUpgrade(Station station, String buildingType) {
        // Check for mutually exclusive buildings
        if ((buildingType.equals("Small Hotel") && station.hasBuilding("Grand Hotel")) ||
                (buildingType.equals("Grand Hotel") && station.hasBuilding("Small Hotel"))) {
            throw new IllegalStateException("Cannot have both hotel types");
        }

        if ((buildingType.equals("Telegraph") && station.hasBuilding("Telephone")) ||
                (buildingType.equals("Telephone") && station.hasBuilding("Telegraph"))) {
            station.removeBuilding("Telegraph"); // Telephone replaces telegraph
        }

        Building building = BuildingRepository.getInstance().getBuilding(buildingType);
        economy.deductFunds(building.getCost());
        return station.addBuilding(buildingType);
    }
}
