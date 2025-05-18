# US06 - Upgrade Station

## 4. Tests

**Test 1:** Confirms that telephone is listed as a valid upgrade type

    @Test
    void ensureTelephoneIsInAvailableUpgradeTypes() {
    List<String> upgrades = repo.getAllUpgradeTypes();
    assertTrue(upgrades.contains("telephone"));
    }

**Test 2:** Ensures that a station can be upgraded 

    @Test
    void ensureUpgradeIsAddedToStation() {
        StationUpgrade upgrade = new StationUpgrade("telephone", null);
        station.addUpgrade(upgrade);

        List<StationUpgrade> upgrades = station.getUpgrades();
        assertEquals(1, upgrades.size());
        assertEquals("telephone", upgrades.get(0).getType());
    }



## 5. Construction (Implementation)

### Class UpgradeStationController

    public class UpgradeStationController {
    private  OrganizationRepository organizationRepository;
    private  AuthenticationRepository authenticationRepository;
    private  StationRepository stationRepository;
    private  UpgradeBuildingsRepository upgradeBuildingsRepository;

    public UpgradeStationController() {
        this(Repositories.getInstance().getOrganizationRepository(),
                Repositories.getInstance().getAuthenticationRepository(),
                Repositories.getInstance().getStationRepository(),
                Repositories.getInstance().getUpgradeBuildingsRepository());
    }

    public UpgradeStationController(OrganizationRepository organizationRepository,
                                    AuthenticationRepository authenticationRepository,
                                    StationRepository stationRepository,
                                    UpgradeBuildingsRepository upgradeBuildingsRepository) {
        this.organizationRepository = organizationRepository;
        this.authenticationRepository = authenticationRepository;
        this.stationRepository = stationRepository;
        this.upgradeBuildingsRepository = upgradeBuildingsRepository;
    }

    public List<Station> getAvailableStations() {
        return stationRepository.getAll();
    }

    public List<String> getAvailableUpgradeTypes() {
        return upgradeBuildingsRepository.getAllUpgradeTypes();
    }

    public List<String> getAvailableSubTypes(String upgradeType) {
        if (upgradeBuildingsRepository.acceptsSubTypes(upgradeType)) {
            return upgradeBuildingsRepository.getAllSubTypes();
        }
        return List.of();
    }
    private OrganizationRepository getOrganizationRepository() {
        if (organizationRepository == null) {
            Repositories repositories = Repositories.getInstance();
            organizationRepository = repositories.getOrganizationRepository();
        }
        return organizationRepository;

    }
    private AuthenticationRepository getAuthenticationRepository() {
        if (authenticationRepository == null) {
            Repositories repositories = Repositories.getInstance();

            //Get the AuthenticationRepository
            authenticationRepository = repositories.getAuthenticationRepository();
        }
        return authenticationRepository;
    }

    public List<Station> getAllStations() {
        return stationRepository.getAll();
    }

    public Optional<Station> upgradeStation(String stationId, String upgradeType, String subType) {
        Employee employee = getEmployeeFromSession();
        Optional<Organization> organization = organizationRepository.getOrganizationByEmployee(employee);

        // Busca a estação pelo ID (ou referência)
        Optional<Station> stationOptional = stationRepository.getAll().stream()
                .filter(s -> s.getReference().equals(stationId))
                .findFirst();

        if (organization.isEmpty() || stationOptional.isEmpty()) {
            return Optional.empty();
        }

        Station station = stationOptional.get();
        station.addUpgrade(new StationUpgrade(upgradeType, subType));

        return Optional.of(station);
    }
    private Employee getEmployeeFromSession() {
        Email email = getAuthenticationRepository().getCurrentUserSession().getUserId();
        return new Employee(email.getEmail());
    }

### Class StationUpgrader

    public class StationUpgrade {
    private final String type;
    private final String subType;

    public StationUpgrade(String type, String subType) {
        this.type = type;
        this.subType = subType;
    }

    // Getters
    public String getType() { return type; }
    public String getSubType() { return subType; }

    @Override
    public String toString() {
        return type + (subType != null ? " (" + subType + ")" : "");
    }


### Class UpgradeBuilidingsRepository

    public class UpgradeBuildingsRepository {
    private final List<String> upgradeTypes;
    private final List<String> subTypes; // Para upgrades que têm variações (small/large)

    public UpgradeBuildingsRepository() {
        this.upgradeTypes = new ArrayList<>();
        this.subTypes = new ArrayList<>();
        initializeUpgradeTypes();
        initializeSubTypes();
    }

    private void initializeUpgradeTypes() {
        upgradeTypes.add("telegraph");
        upgradeTypes.add("telephone");
        upgradeTypes.add("cafe");
        upgradeTypes.add("customs");
        upgradeTypes.add("post office");
        upgradeTypes.add("hotel");
        upgradeTypes.add("silo");
        upgradeTypes.add("liquid storage");
    }

    private void initializeSubTypes() {
        subTypes.add("small");
        subTypes.add("large");
    }

    // Métodos para obter os tipos de upgrades e subtipos
    public List<String> getAllUpgradeTypes() {
        return new ArrayList<>(upgradeTypes);
    }

    public List<String> getAllSubTypes() {
        return new ArrayList<>(subTypes);
    }

    // Verifica se um tipo de upgrade é válido
    public boolean isValidUpgradeType(String type) {
        return upgradeTypes.contains(type.toLowerCase());
    }

    // Verifica se um subtipo é válido (apenas para upgrades que têm variações)
    public boolean isValidSubType(String subType) {
        return subTypes.contains(subType.toLowerCase());
    }

    // Método para verificar quais upgrades aceitam subtipos
    public boolean acceptsSubTypes(String upgradeType) {
        return upgradeType.equalsIgnoreCase("cafe") ||
                upgradeType.equalsIgnoreCase("hotel");
    }


### Class UpgradeStationUI

    public class UpgradeStationUI implements Runnable {
    private final UpgradeStationController controller;
    private final Scanner scanner;

    public UpgradeStationUI() {
        this.controller = new UpgradeStationController();
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        System.out.println("\n--- Station Upgrade ---");

        // 1. Listar todas as estações
        List<Station> stations = controller.getAllStations();
        if (stations.isEmpty()) {
            System.out.println("No stations available for upgrade.");
            return;
        }

        System.out.println("\nAvailable Stations:");
        for (int i = 0; i < stations.size(); i++) {
            System.out.printf("%d. %s (Type: %s)%n",
                    i+1,
                    stations.get(i).getReference(),
                    stations.get(i).getType());
        }

        // 2. Selecionar estação
        System.out.print("Select station to upgrade (0 to cancel): ");
        int stationChoice = scanner.nextInt();
        scanner.nextLine(); // Limpar buffer

        if (stationChoice < 1 || stationChoice > stations.size()) {
            return;
        }
        Station selectedStation = stations.get(stationChoice - 1);

        // 3. Selecionar tipo de upgrade
        List<String> upgradeTypes = controller.getAvailableUpgradeTypes();
        System.out.println("\nAvailable Upgrade Types:");
        for (int i = 0; i < upgradeTypes.size(); i++) {
            System.out.printf("%d. %s%n", i+1, upgradeTypes.get(i));
        }

        System.out.print("Select upgrade type: ");
        int upgradeChoice = scanner.nextInt();
        scanner.nextLine();

        String upgradeType = upgradeTypes.get(upgradeChoice - 1);

        // 4. Selecionar subtipo (se aplicável)
        String subType = null;
        if (controller.getAvailableSubTypes(upgradeType).size() > 0) {
            List<String> subTypes = controller.getAvailableSubTypes(upgradeType);
            System.out.println("\nAvailable Subtypes:");
            for (int i = 0; i < subTypes.size(); i++) {
                System.out.printf("%d. %s%n", i+1, subTypes.get(i));
            }

            System.out.print("Select subtype: ");
            int subTypeChoice = scanner.nextInt();
            scanner.nextLine();
            subType = subTypes.get(subTypeChoice - 1);
        }

        // 5. Aplicar upgrade
        Optional<Station> result = controller.upgradeStation(
                selectedStation.getReference(),
                upgradeType,
                subType
        );

        if (result.isPresent()) {
            System.out.println("\nUpgrade applied successfully!");
            System.out.println("Station now has upgrades: " + result.get().getUpgrades());
        } else {
            System.out.println("\nFailed to apply upgrade!");
        }
    }
}


## 6. Integration and Demo

* Building upgrade options now appear in the station management menu
* Connected to the economic system for budget validation
* Integrated with the existing station management system through StationRepository

## 7. Observations

n/a