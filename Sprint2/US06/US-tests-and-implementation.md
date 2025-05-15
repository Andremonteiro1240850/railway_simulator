# US06 - Upgrade Station

## 4. Tests

**Test 1:** Test if upgrade does not work with null vallues

    @Test(expected = IllegalArgumentException.class)
        public void ensureNullIsNotAllowed() {
            UpgradeStationController controller = new UpgradeStationController();
            controller.executeUpgrade(null, null);
        }

**Test 2:** Test compatibility of the buildings

    @Test(expected = IllegalStateException.class)
        public void preventIncompatibleUpgrades() {
            Station station = new Station(...);
            station.addBuilding(new Building("Small Hotel", 500, 1900));
            Building upgrade = new Building("Grand Hotel", 1000, 1920);
            
                StationValidator validator = new StationValidator();
                validator.validateUpgrade(station, upgrade); // Should fail - mutually exclusive
        }



## 5. Construction (Implementation)

### Class UpgradeStationController

    public class UpgradeStationController {
    private final StationRepository stationRepo;
    private final BuildingRepository buildingRepo;
    private final EconomyManager economy;
    private final StationUpgrader upgrader;
    
        public UpgradeStationController() {
            this.stationRepo = StationRepository.getInstance();
            this.buildingRepo = BuildingRepository.getInstance();
            this.economy = EconomyManager.getInstance();
            this.upgrader = new StationUpgrader();
        }
        
        public List<Station> getAvailableStations(Player player) {
            return stationRepo.getStationsByPlayer(player);
        }
        
        public List<Building> getAvailableUpgrades(Station station) {
            List<Building> allBuildings = buildingRepo.getAllBuildings();
            return StationValidator.filterAvailableUpgrades(station, allBuildings);
        }
        
        public boolean executeUpgrade(Station station, Building upgrade) {
            // Validate funds
            if (!economy.canAfford(upgrade.getCost())) {
                throw new IllegalArgumentException("Insufficient funds for upgrade");
            }
            
            // Validate upgrade compatibility
            if (!StationValidator.isValidUpgrade(station, upgrade)) {
                throw new IllegalStateException("Incompatible upgrade");
            }
            
            // Apply upgrade
            boolean success = upgrader.applyUpgrade(station, upgrade);
            if (success) {
                economy.deduct(upgrade.getCost());
                stationRepo.updateStation(station);
            }
            
            return success;
        }
    }

### Class StationUpgrader

    public class StationUpgrader {
    public boolean applyUpgrade(Station station, Building upgrade) {
    // Handle replacement buildings (e.g., telephone replaces telegraph)
    if (upgrade.replacesExisting()) {
    station.removeBuildingsOfType(upgrade.getReplacedType());
    }
    
            // Handle mutually exclusive buildings
            if (upgrade.isMutuallyExclusive()) {
                station.removeBuildingsInCategory(upgrade.getExclusiveCategory());
            }
            
            return station.addBuilding(upgrade);
        }
    }

### Class StationValidator

    public class StationValidator {
    public static boolean isValidUpgrade(Station station, Building upgrade) {
    // Check historical availability
    if (station.getYear() < upgrade.getAvailableFrom()) {
    return false;
    }
    
            // Check mutual exclusivity
            if (upgrade.isMutuallyExclusive() && 
                station.hasBuildingInCategory(upgrade.getExclusiveCategory())) {
                return false;
            }
            
            // Check replacement rules
            if (upgrade.replacesExisting() && 
                !station.hasBuildingOfType(upgrade.getReplacedType())) {
                return false;
            }
            
            return true;
        }
        
        public static List<Building> filterAvailableUpgrades(Station station, List<Building> buildings) {
            return buildings.stream()
                .filter(b -> isValidUpgrade(station, b))
                .collect(Collectors.toList());
        }
    }
## 6. Integration and Demo

* Building upgrade options now appear in the station management menu
* Connected to the economic system for budget validation
* Integrated with the existing station management system through StationRepository

## 7. Observations

n/a