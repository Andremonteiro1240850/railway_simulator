# US05 - Build a station

## 4. Tests

**Test 1:** Test if is posssible to build a station

        @Test(expected = IllegalArgumentException.class)
            public void ensureNullIsNotAllowed() {
                Station instance = new Station(null, null, null, null);
            }

**Test 2:** Test if station name follow the convention

        @Test
    public void ensureStationNamingConvention() {
        StationNamingService naming = new StationNamingService();
        String name = naming.generateName("Porto", "Terminal");
        assertEquals("Porto Terminal", name);
    }

**Test 3:** Test if position is valid to avoid overbuilding

    @Test(expected = IllegalArgumentException.class)
        public void preventOverbuilding() {
            PositionValidator validator = new PositionValidator();
            validator.validatePosition(new Location(10,10)); // Should fail if position occupied
        }


## 5. Construction (Implementation)

### class CreateStationController

    public class CreateStationController {
    private StationTypeRepository typeRepo;
    private RailwayNetwork railway;
    private BudgetManager budget;
    private CityRegistry cityRegistry;
    
        public CreateStationController() {
            this.typeRepo = StationTypeRepository.getInstance();
            this.railway = RailwayNetwork.getInstance();
            this.budget = BudgetManager.getInstance();
            this.cityRegistry = CityRegistry.getInstance();
        }
        
        public Station createStation(StationType type, Location location, String name, Point center) {
            // Validate budget
            if (!budget.canAfford(type.getCost())) {
                throw new IllegalArgumentException("Insufficient budget");
            }
            
            // Validate position
            PositionValidator.validatePosition(location);
            
            // Generate name if not provided
            if (name == null) {
                String city = cityRegistry.getNearestCity(location);
                name = StationNamingService.generateName(city, type.getName());
            }
            
            // Calculate center if station type requires it
            if (type.requiresCenter() && center == null) {
                center = StationGeometry.calculateCenter(type, location);
            }
            
            // Create and register station
            Station station = new Station(type, location, name, center);
            railway.addStation(station);
            budget.deduct(type.getCost());
            
            return station;
        }
        
        public List<StationType> getAvailableStationTypes() {
            return typeRepo.getAllTypes();
        }
    }

### Class Station

    public class Station {
    private final StationType type;
    private final Location location;
    private final String name;
    private final Point center;
    private List<StationBuilding> buildings = new ArrayList<>();
    
        public Station(StationType type, Location location, String name, Point center) {
            if (type == null || location == null || name == null) {
                throw new IllegalArgumentException("Station parameters cannot be null");
            }
            this.type = type;
            this.location = location;
            this.name = name;
            this.center = center;
        }
        
        // Additional methods for station operations
    }
## 6. Integration and Demo

* Connected to budget system to validate construction costs

* Station creation now available in the Player menu options

* For demo purposes, three basic station types (Depot, Station, Terminal) are preloaded

* Station creation triggers map updates through the MapService observer
*
## 7. Observations

n/ais section, it is suggested to present a critical perspective on the developed work, pointing, for example, to other alternatives and or future related work._