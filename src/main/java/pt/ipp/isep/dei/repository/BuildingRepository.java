package pt.ipp.isep.dei.repository;

//us06

public class BuildingRepository {
    private static BuildingRepository instance;
    private Map<String, Building> buildings = new HashMap<>();

    private BuildingRepository() {
        // Initialize with default buildings
        buildings.put("Telegraph", new Building("Telegraph", 20, 1870));
        buildings.put("Telephone", new Building("Telephone", 30, 1890));
        buildings.put("Small Hotel", new Building("Small Hotel", 50, 1850));
        buildings.put("Grand Hotel", new Building("Grand Hotel", 100, 1880));
        // Add more buildings as needed
    }

    public static synchronized BuildingRepository getInstance() {
        if (instance == null) {
            instance = new BuildingRepository();
        }
        return instance;
    }

    public List<String> getBuildingsByType(List<String> types) {
        return types.stream()
                .filter(buildings::containsKey)
                .collect(Collectors.toList());
    }

    public Building getBuilding(String type) {
        return buildings.get(type);
    }
}