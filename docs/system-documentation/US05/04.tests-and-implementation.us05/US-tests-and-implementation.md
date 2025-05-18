# US05 - Build a station

## 4. Tests

**Test 1:** Ensures that a valid station is added to the repository and can be retrieved.

    @Test
    void ensureStationIsAddedSuccessfully() {
    Optional<Station> result = repo.add(station1);
    assertTrue(result.isPresent());
    assertEquals(1, repo.getAll().size());
    }

**Test 2:** Ensures that the repository rejects a station with an already existing reference.


     @Test
    void ensureDuplicateReferenceStationIsNotAdded() {
        repo.add(station1);
        Station duplicate = new Station("ST-001", "Copy", "station", "South");
        Optional<Station> result = repo.add(duplicate);
        assertTrue(result.isEmpty());
        assertEquals(1, repo.getAll().size());
    }
      

**Test 3:** Ensures that a station can be correctly found using its unique reference.
    
    @Test
    void ensureStationIsFoundByReference() {
        repo.add(station2);
        Optional<Station> result = repo.findByReference("ST-002");
        assertTrue(result.isPresent());
        assertEquals("East", result.get().getName());
    }
**Test 4:** Ensures that a valid upgrade is correctly added to an existing station and is stored in the repository.

    @Test
    void ensureUpgradeIsAddedToStation() {
    repo.add(station1);
    StationUpgrade upgrade = new StationUpgrade("Telegraph", null);
    boolean success = repo.addUpgradeToStation("ST-001", upgrade);
    assertTrue(success);
    List<StationUpgrade> upgrades = repo.getUpgradesForStation("ST-001");
    assertFalse(upgrades.isEmpty());
    assertEquals("Telegraph", upgrades.get(0).getType());
    }

## 5. Construction (Implementation)

### class CreateStationController

    public class CreateScenarioController {
    private final OrganizationRepository orgRepo;
    private final AuthenticationRepository authRepo;
    private final CreateMapRepository mapRepo;
    private final ScenarioRepository scenarioRepo;

    public CreateScenarioController() {
        this(Repositories.getInstance().getOrganizationRepository(),
                Repositories.getInstance().getAuthenticationRepository(),
                Repositories.getInstance().getMapRepository(),
                Repositories.getInstance().getScenarioRepository());
    }

    public CreateScenarioController(OrganizationRepository orgRepo,
                                    AuthenticationRepository authRepo,
                                    CreateMapRepository mapRepo,
                                    ScenarioRepository scenarioRepo) {
        this.orgRepo = orgRepo;
        this.authRepo = authRepo;
        this.mapRepo = mapRepo;
        this.scenarioRepo = scenarioRepo;
    }

    public List<Map> getAvailableMaps() {
        return mapRepo.getAll();
    }

    public Optional<Scenario> createScenario(String name, Map map, int startYear, int endYear,
                                             List<String> locomotiveTypes, PortBehavior portBehavior) {
        Employee employee = getEmployeeFromSession();
        Optional<Organization> org = orgRepo.getOrganizationByEmployee(employee);

        if (org.isEmpty()) return Optional.empty();

        Scenario newScenario = new Scenario(name, map, startYear, endYear);

        for (String type : locomotiveTypes) {
            newScenario.addLocomotiveType(type);
        }

        newScenario.setPortBehavior(portBehavior);

        return scenarioRepo.add(newScenario);
    }

    private Employee getEmployeeFromSession() {
        Email email = authRepo.getCurrentUserSession().getUserId();
        return new Employee(email.getEmail());
    }


### Class Station

    public class Station {
    private String name;
    private String type; // "depot", "terminal", "station"
    private String region;
    private Employee employee;
    private String reference;  // Novo campo adicionado
    private List<StationUpgrade> upgrades = new ArrayList<>();

    // Construtor modificado para incluir reference
    public Station(String reference, String name, String type, String region) {
        this.reference = reference;
        this.name = name;
        this.type = type;
        this.region = region;
        validateTypeAndRegion(type, region);
    }

    // Construtor antigo mantido para compatibilidade
    public Station(String name, String type, String region) {
        this(generateDefaultReference(), name, type, region);
    }

    private static String generateDefaultReference() {
        return "ST-" + System.currentTimeMillis(); // Gera uma referência única
    }

    private void validateTypeAndRegion(String type, String region) {
        // ... (mantido igual)
    }

    // Getters
    public String getReference() {
        return reference;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getRegion() {
        return region;
    }

    public Employee getEmployee() {
        return employee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Station)) return false;
        Station station = (Station) o;
        return reference.equals(station.reference); // Agora compara por reference
    }

    @Override
    public int hashCode() {
        return Objects.hash(reference); // Agora usa reference
    }

    public Station clone() {
        Station clone = new Station(this.reference, this.name, this.type, this.region);
        clone.employee = this.employee != null ? this.employee.clone() : null;
        clone.upgrades = new ArrayList<>(this.upgrades);
        return clone;
    }

    public void addUpgrade(StationUpgrade upgrade) {
        this.upgrades.add(upgrade);
    }

    public List<StationUpgrade> getUpgrades() {
        return new ArrayList<>(upgrades);
    }


### Class StationRepository


    public class StationRepository {
    private final List<Station> stations = new ArrayList<>();

    public Optional<Station> add(Station station) {
        if (station == null) {
            return Optional.empty();
        }

        if (!contains(station)) {
            try {
                Station clonedStation = station.clone();
                stations.add(clonedStation);
                return Optional.of(clonedStation);
            } catch (Exception e) {
                System.err.println("Error cloning station: " + e.getMessage());
                return Optional.empty();
            }
        }
        return Optional.empty();
    }

    public boolean contains(Station station) {
        return stations.stream().anyMatch(s -> s.getReference().equals(station.getReference()));
    }

    public Optional<Station> findByReference(String reference) {
        return stations.stream()
                .filter(s -> s.getReference().equals(reference))
                .findFirst()
                .map(Station::clone);
    }

    public List<Station> getAll() {
        // Retorna cópia defensiva com clones para evitar modificações externas
        List<Station> clonedStations = new ArrayList<>();
        for (Station station : stations) {
            clonedStations.add(station.clone());
        }
        return clonedStations;
    }

    public boolean addUpgradeToStation(String stationReference, StationUpgrade upgrade) {
        Optional<Station> stationOpt = findByReference(stationReference);
        if (stationOpt.isPresent()) {
            Station station = stationOpt.get();
            station.addUpgrade(upgrade);

            // Atualiza a estação no repositório
            stations.removeIf(s -> s.getReference().equals(stationReference));
            stations.add(station);
            return true;
        }
        return false;
    }

    public List<StationUpgrade> getUpgradesForStation(String stationReference) {
        return findByReference(stationReference)
                .map(Station::getUpgrades)
                .orElse(new ArrayList<>());
    }

    public String printStationsWithUpgrades() {
        StringBuilder sb = new StringBuilder();
        sb.append("List of Stations with Upgrades:\n");
        for (Station station : getAll()) {
            sb.append("- ").append(station.getReference())
                    .append(" (").append(station.getType()).append("): ");

            if (station.getUpgrades().isEmpty()) {
                sb.append("No upgrades\n");
            } else {
                sb.append("Upgrades: ");
                for (StationUpgrade upgrade : station.getUpgrades()) {
                    sb.append(upgrade.getType());
                    if (upgrade.getSubType() != null) {
                        sb.append(" (").append(upgrade.getSubType()).append(")");
                    }
                    sb.append(", ");
                }
                // Remove a última vírgula e espaço
                sb.setLength(sb.length() - 2);
                sb.append("\n");
            }
        }
        return sb.toString();
    }

### Class BuildStationUI

    
    public class BuildStationUI implements Runnable {
    private final BuildStationController controller;
    private final TypeStationRepository typeStationRepo;
    private String type;
    private String region;
    private String name;

    public BuildStationUI() {
        this.controller = new BuildStationController();
        this.typeStationRepo = new TypeStationRepository();
    }

    public void run() {
        System.out.println("\n\n--- Create Station ------------------------");

        requestStationData();
        submitData();
    }

    private void submitData() {
        Optional<Station> station = controller.createStation(
                name,
                type,
                region);

        if (station.isPresent()) {
            System.out.println("\nStation successfully created!");
        } else {
            System.out.println("\nStation not created!");
        }
    }

    private void requestStationData() {
        // Request basic station information
        name = requestName();
        // Select station type and region if applicable
        selectStationTypeAndRegion();
    }

    private void selectStationTypeAndRegion() {
        // Get available station types from repository
        List<String> types = typeStationRepo.getAllStationTypes();
        type = selectFromList(types, "Station Type");

        // Only ask for region if station type is "station"
        if ("station".equalsIgnoreCase(type)) {
            List<String> regions = typeStationRepo.getAllStationRegions();
            region = selectFromList(regions, "Station Region");
        } else {
            region = null;
        }
    }

    // Helper method to select from a list of items
    private <T> T selectFromList(List<T> items, String prompt) {
        Scanner input = new Scanner(System.in);
        int selection = -1;

        while (selection < 1 || selection > items.size()) {
            System.out.println("\n" + prompt + " options:");
            for (int i = 0; i < items.size(); i++) {
                System.out.printf("  %d - %s%n", i+1, items.get(i).toString());
            }
            System.out.print("Select " + prompt + ": ");
            selection = input.nextInt();
            input.nextLine(); // Consume newline
        }

        return items.get(selection - 1);
    }

    // Helper methods for input
    private String requestName() {
        Scanner input = new Scanner(System.in);
        System.out.print("Name: ");
        return input.nextLine();
    }

}


    
## 6. Integration and Demo

* Connected to budget system to validate construction costs

* Station creation now available in the Player menu options

* For demo purposes, three basic station types (Depot, Station, Terminal) are preloaded

* Station creation triggers map updates through the MapService observer

## 7. Observations

n/a