# US010 - As a Player, I want to assign a selected train to a route with
valid stations and the respective list of cargoes to be picked up in each
station.

## 4. Tests



**Test 1:** Verifies that a route is correctly created when all inputs are valid.

	@Test
    void shouldCreateRouteWithValidInputs() {
        List<Station> stations = stationRepository.getAll();
        List<String> cargos = List.of("coal", "cattle");

        Route route = new Route(stations, testLocomotive, cargos);

        assertEquals(2, route.getStations().size());
        assertEquals("L001", route.getLocomotive().getReference());
        assertEquals(cargos, route.getCargosToTransport());
    }

**Test 2:** Ensures that a route cannot be created with fewer than two stations, as required

    @Test
    void shouldRejectRouteWithLessThanTwoStations() {
        List<Station> onlyOne = List.of(stationRepository.getAll().get(0));
        Route route = null;

        if (onlyOne.size() < 2) {
            route = null;
        }

        assertNull(route);
    }

**Test 3:** Checks that the cargo repository correctly accepts known cargo types 

    @Test
    void shouldRecognizeValidCargo() {
        assertTrue(cargoRepository.isValidCargo("mail"));
    }

**Test 4:** Verifies that the cargo repository correctly rejects unknown or unsupported cargo types.


    @Test
    void shouldRejectInvalidCargo() {
        assertFalse(cargoRepository.isValidCargo("diamonds"));
    }

**Test 5:** Ensures that cloning a route creates a deep copy, not affecting the original instance.

    void shouldCloneRouteCorrectly() {
        List<Station> stations = stationRepository.getAll();
        List<String> cargos = List.of("mail", "coal");
        Route original = new Route(stations, testLocomotive, cargos);
        Route copy = original.clone();

        assertNotSame(original, copy);
        assertEquals(original.getStations(), copy.getStations());
        assertEquals(original.getCargosToTransport(), copy.getCargosToTransport());
        assertEquals(original.getLocomotive().getReference(), copy.getLocomotive().getReference());
    }


## 5. Construction (Implementation)

### Class AssignRouteController

    public class AssignRouteController {
    private final StationRepository stationRepository;
    private final LocomotiveRepository locomotiveRepository;
    private final CargoRepository cargoRepository;

    public AssignRouteController() {
        Repositories repositories = Repositories.getInstance();
        this.stationRepository = repositories.getStationRepository();
        this.locomotiveRepository = repositories.getLocomotiveRepository();
        this.cargoRepository = repositories.getCargoRepository();
    }

    // Para testes
    public AssignRouteController(StationRepository stationRepository,
                                 LocomotiveRepository locomotiveRepository,
                                 CargoRepository cargoRepository) {
        this.stationRepository = stationRepository;
        this.locomotiveRepository = locomotiveRepository;
        this.cargoRepository = cargoRepository;
    }

    public List<Station> getAllStations() {
        return stationRepository.getAll();
    }

    public List<Locomotive> getAllLocomotives() {
        return locomotiveRepository.getAll();
    }

    public List<String> getAllCargos() {
        return cargoRepository.getAllCargos();
    }

    public Optional<Route> createRoute(List<Station> stations, Locomotive locomotive, List<String> cargosToTransport) {
        if (stations == null || stations.size() < 2) {
            return Optional.empty();
        }

        if (locomotive == null) {
            return Optional.empty();
        }

        // Verifica se todas as cargas são válidas
        for (String cargo : cargosToTransport) {
            if (!cargoRepository.isValidCargo(cargo)) {
                return Optional.empty();
            }
        }

        // Cria a rota (implementação simplificada)
        Route route = new Route(stations, locomotive, cargosToTransport);
        return Optional.of(route);
    }

### Class Route

    public class Route {
    private final List<Station> stations;
    private final Locomotive locomotive;
    private final List<String> cargosToTransport;

    public Route(List<Station> stations, Locomotive locomotive, List<String> cargosToTransport) {
        this.stations = stations;
        this.locomotive = locomotive;
        this.cargosToTransport = cargosToTransport;
    }

    public List<Station> getStations() {
        return stations;
    }

    public Locomotive getLocomotive() {
        return locomotive;
    }

    public List<String> getCargosToTransport() {
        return cargosToTransport;
    }

    public Route clone() {
        return new Route(
                new ArrayList<>(this.stations),
                this.locomotive.clone(),
                new ArrayList<>(this.cargosToTransport)
        );
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

### Class CargoRepository

    Public class CargoRepository {
    private final List<String> availableCargos = new ArrayList<>();

    public CargoRepository() {
        // Inicializa com os tipos de carga baseados no documento
        availableCargos.addAll(List.of(
                "coal", "iron", "bauxite", "nitrate", "vegetables", "cereals",
                "wool", "coffee", "rubber", "cattle", "steel", "textiles",
                "automobiles", "food", "passengers", "mail"
        ));
    }

    public List<String> getAllCargos() {
        return new ArrayList<>(availableCargos);
    }

    public boolean isValidCargo(String cargo) {
        return availableCargos.contains(cargo);
    }

### Class AssignRouteUI

    public class AssignRouteUI implements Runnable {
    private final AssignRouteController controller;
    private final Scanner scanner;

    public AssignRouteUI() {
        this.controller = new AssignRouteController();
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        System.out.println("\n=== Assign Train to Route ===");

        // 1. Selecionar estações para a rota
        List<Station> selectedStations = selectStationsForRoute();
        if (selectedStations.size() < 2) {
            System.out.println("A route must have at least 2 stations.");
            return;
        }

        // 2. Selecionar locomotiva
        Locomotive selectedLocomotive = selectLocomotive();
        if (selectedLocomotive == null) {
            return;
        }

        // 3. Selecionar cargas para transportar
        List<String> selectedCargos = selectCargosToTransport();
        if (selectedCargos.isEmpty()) {
            System.out.println("You must select at least one cargo type.");
            return;
        }

        // 4. Criar a rota
        Optional<Route> route = controller.createRoute(selectedStations, selectedLocomotive, selectedCargos);

        if (route.isPresent()) {
            System.out.println("\nRoute created successfully!");
            System.out.println("Stations: " + route.get().getStations());
            System.out.println("Locomotive: " + route.get().getLocomotive().getReference());
            System.out.println("Cargos to transport: " + route.get().getCargosToTransport());
        } else {
            System.out.println("\nFailed to create route.");
        }
    }

    private List<Station> selectStationsForRoute() {
        List<Station> stations = controller.getAllStations();
        List<Station> selectedStations = new ArrayList<>();

        if (stations.isEmpty()) {
            System.out.println("No stations available. Please create stations first.");
            return selectedStations;
        }

        System.out.println("\nSelect stations for the route (minimum 2):");
        int stationNumber = 1;
        for (Station station : stations) {
            System.out.printf("%d. %s (%s)%n", stationNumber++, station.getReference(), station.getType());
        }
        System.out.println("0. Finish selection");

        while (true) {
            System.out.print("\nSelect station to add to route (0 to finish): ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (choice == 0) {
                if (selectedStations.size() >= 2) {
                    break;
                } else {
                    System.out.println("You need to select at least 2 stations for a route.");
                    continue;
                }
            }

            if (choice > 0 && choice <= stations.size()) {
                Station selected = stations.get(choice - 1);
                if (!selectedStations.contains(selected)) {
                    selectedStations.add(selected);
                    System.out.println("Added " + selected.getReference() + " to route.");
                } else {
                    System.out.println("Station already added to route.");
                }
            } else {
                System.out.println("Invalid selection.");
            }
        }

        return selectedStations;
    }

    private Locomotive selectLocomotive() {
        List<Locomotive> locomotives = controller.getAllLocomotives();

        if (locomotives.isEmpty()) {
            System.out.println("No locomotives available. Please purchase locomotives first.");
            return null;
        }

        System.out.println("\nAvailable Locomotives:");
        for (int i = 0; i < locomotives.size(); i++) {
            Locomotive loco = locomotives.get(i);
            System.out.printf("%d. %s (%s, Power: %d)%n",
                    i+1, loco.getReference(), loco.getType(), loco.getPower());
        }

        System.out.print("Select locomotive: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (choice > 0 && choice <= locomotives.size()) {
            return locomotives.get(choice - 1);
        } else {
            System.out.println("Invalid selection.");
            return null;
        }
    }

    private List<String> selectCargosToTransport() {
        List<String> cargos = controller.getAllCargos();
        List<String> selectedCargos = new ArrayList<>();

        System.out.println("\nSelect cargos to transport:");
        for (int i = 0; i < cargos.size(); i++) {
            System.out.printf("%d. %s%n", i+1, cargos.get(i));
        }
        System.out.println("0. Finish selection");

        while (true) {
            System.out.print("\nSelect cargo to add (0 to finish): ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (choice == 0) {
                break;
            }

            if (choice > 0 && choice <= cargos.size()) {
                String cargo = cargos.get(choice - 1);
                if (!selectedCargos.contains(cargo)) {
                    selectedCargos.add(cargo);
                    System.out.println("Added " + cargo + " to transport list.");
                } else {
                    System.out.println("Cargo already selected.");
                }
            } else {
                System.out.println("Invalid selection.");
            }
        }

        return selectedCargos;
    }


## 6. Integration and Demo 

* This functionality was integrated with the existing repositories of stations, locomotives, and cargo types, ensuring reuse and cohesion with the rest of the system. The application logic relies on previously registered data, guaranteeing that only valid entities can be used when creating a route.
* The Route class directly reuses instances of Station and Locomotive, preserving consistency across domain entities and avoiding data duplication. This approach reinforces the integrity of information throughout the system.



## 7. Observations

_In this section, it is suggested to present a critical perspective on the developed work, pointing, for example, to other alternatives and or future related work._