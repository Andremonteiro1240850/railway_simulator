# US09- As a Player, I want to buy a locomotive


## 4. Tests



**Test 1:** Ensures that a valid locomotive is added to the repository and can be retrieved.

	 @Test
    void shouldAddLocomotiveSuccessfully() {
        Locomotive loco = new Locomotive("L001", "Diesel", 10000.0, 1500, 1945);
        Optional<Locomotive> result = locomotiveRepo.add(loco);

        assertTrue(result.isPresent());
        assertEquals("L001", result.get().getReference());
    }

**Teste 2:** Verifies that adding a null locomotive returns an empty result and does not affect the repository.

     @Test
    void shouldNotAddNullLocomotive() {
        Optional<Locomotive> result = locomotiveRepo.add(null);
        assertTrue(result.isEmpty());
    }
**Teste 3:** Confirms that a previously added locomotive can be retrieved by its unique reference.

    @Test
    void shouldFindLocomotiveByReference() {
        Locomotive loco = new Locomotive("L002", "Steam", 8000.0, 1000, 1920);
        locomotiveRepo.add(loco);

        Optional<Locomotive> found = locomotiveRepo.findByReference("L002");
        assertTrue(found.isPresent());
        assertEquals("Steam", found.get().getType());
    }

**Teste 4:** Ensures that the repository returns an empty result when the scenario name does not exist.

    @Test
    void shouldReturnEmptyIfScenarioNotFound() {
        Optional<Scenario> result = scenarioRepo.findByName("Mars 2100");
        assertTrue(result.isEmpty());
    }



## 5. Construction (Implementation)

### Class LocomotiveController

    public class BuyLocomotiveController {
    private final OrganizationRepository orgRepo;
    private final AuthenticationRepository authRepo;
    private final ListLocomotivesRepository listLocoRepo;
    private final LocomotiveRepository locoRepo;
    private final ScenarioManager scenarioService;  // Usa o Service ao invés do Repository

    public BuyLocomotiveController() {
        this(Repositories.getInstance().getOrganizationRepository(),
                Repositories.getInstance().getAuthenticationRepository(),
                Repositories.getInstance().getListLocomotivesRepository(),
                Repositories.getInstance().getLocomotiveRepository(),
                new ScenarioManager(Repositories.getInstance().getScenarioRepository()));
    }

    public BuyLocomotiveController(OrganizationRepository orgRepo,
                                   AuthenticationRepository authRepo,
                                   ListLocomotivesRepository listLocoRepo,
                                   LocomotiveRepository locoRepo,
                                   ScenarioManager scenarioService) {
        this.orgRepo = orgRepo;
        this.authRepo = authRepo;
        this.listLocoRepo = listLocoRepo;
        this.locoRepo = locoRepo;
        this.scenarioService = scenarioService;
    }

    public List<Locomotive> getAvailableLocomotives() {
        // Obtém o ano atual através do ScenarioService
        int currentYear = scenarioService.getCurrentYear();
        return listLocoRepo.getAvailableLocomotives(currentYear);
    }

    public Optional<Locomotive> buyLocomotive(String reference) {
        Employee employee = getEmployeeFromSession();
        Optional<Organization> org = orgRepo.getOrganizationByEmployee(employee);

        if (org.isEmpty()) return Optional.empty();

        Optional<Locomotive> locoOpt = listLocoRepo.findByReference(reference);
        if (locoOpt.isEmpty()) return Optional.empty();

        // Verifica se a organização tem fundos suficientes
        Organization organization = org.get();
        Locomotive locomotive = locoOpt.get();

        if (organization.getBudget() >= locomotive.getPrice()) {
            organization.deductFromBudget(locomotive.getPrice());
            return locoRepo.add(locomotive);
        }

        return Optional.empty();
    }

    private Employee getEmployeeFromSession() {
        Email email = authRepo.getCurrentUserSession().getUserId();
        return new Employee(email.getEmail());
    }

### Class Locomotive

    public class Locomotive {
    private final String reference;
    private final String type; // Steam, Diesel, Electric
    private final double price;
    private final int power; // em kW
    private final int startYear; // ano de início de operação

    public Locomotive(String reference, String type, double price, int power, int startYear) {
        this.reference = reference;
        this.type = type;
        this.price = price;
        this.power = power;
        this.startYear = startYear;
    }

    // Getters
    public String getReference() { return reference; }
    public String getType() { return type; }
    public double getPrice() { return price; }
    public int getPower() { return power; }
    public int getStartYear() { return startYear; }

    @Override
    public String toString() {
        return String.format("%s - %s (Power: %dkW, Price: %.2f, Year: %d)",
                reference, type, power, price, startYear);
    }

    public Locomotive clone() {
        return new Locomotive(reference, type, price, power, startYear);
    }

### Class Scenario 

    
    public class Scenario {
    private final String name;
    private final Map map;
    private int startYear;
    private int endYear;
    private int currentYear;
    private List<String> availableLocomotiveTypes;
    private PortBehavior portBehavior;
    private List<HistoricalEvent> historicalEvents;

    public Scenario(String name, Map map, int startYear, int endYear) {
        this.name = name;
        this.map = map;
        this.startYear = startYear;
        this.currentYear = startYear;
        this.endYear = endYear;
        this.availableLocomotiveTypes = new ArrayList<>();
        this.historicalEvents = new ArrayList<>();
    }

    // Getters e Setters
    public int getCurrentYear() { return currentYear; }
    public String getName() { return name; }
    public Map getMap() { return map; }
    public int getStartYear() { return startYear; }
    public int getEndYear() { return endYear; }
    public List<String> getAvailableLocomotiveTypes() { return new ArrayList<>(availableLocomotiveTypes); }
    public PortBehavior getPortBehavior() { return portBehavior; }
    public List<HistoricalEvent> getHistoricalEvents() { return new ArrayList<>(historicalEvents); }

    public void setPortBehavior(PortBehavior portBehavior) {
        this.portBehavior = portBehavior;
    }
    public void setCurrentYear(int currentYear) {
        this.currentYear = currentYear;
    }

    public void addLocomotiveType(String type) {
        if (!availableLocomotiveTypes.contains(type)) {
            availableLocomotiveTypes.add(type);
        }
    }

    public void addHistoricalEvent(HistoricalEvent event) {
        historicalEvents.add(event);
    }

    public Scenario clone() {
        Scenario clone = new Scenario(name, map.clone(), startYear, endYear);
        clone.availableLocomotiveTypes = new ArrayList<>(this.availableLocomotiveTypes);
        clone.portBehavior = this.portBehavior != null ? this.portBehavior.clone() : null;
        clone.historicalEvents = new ArrayList<>(this.historicalEvents);
        clone.currentYear = this.currentYear;
        return clone;
    }

### LocomotiveRepository
    
    public class LocomotiveRepository {
    private final List<Locomotive> purchasedLocomotives = new ArrayList<>();

    public Optional<Locomotive> add(Locomotive locomotive) {
        if (locomotive == null) return Optional.empty();

        Locomotive clone = locomotive.clone();
        purchasedLocomotives.add(clone);
        return Optional.of(clone);
    }

    public List<Locomotive> getAll() {
        List<Locomotive> result = new ArrayList<>();
        for (Locomotive loco : purchasedLocomotives) {
            result.add(loco.clone());
        }
        return result;
    }

    public Optional<Locomotive> findByReference(String reference) {
        return purchasedLocomotives.stream()
                .filter(l -> l.getReference().equals(reference))
                .findFirst()
                .map(Locomotive::clone);
    }
### ScenarioReporitory

    public class ScenarioRepository {
    private final List<Scenario> scenarios = new ArrayList<>();

    public Optional<Scenario> add(Scenario scenario) {
        if (scenario == null) return Optional.empty();

        Scenario clone = scenario.clone();
        scenarios.add(clone);
        return Optional.of(clone);
    }

    public List<Scenario> getAll() {
        List<Scenario> result = new ArrayList<>();
        for (Scenario scenario : scenarios) {
            result.add(scenario.clone());
        }
        return result;
    }

    public Optional<Scenario> findByName(String name) {
        return scenarios.stream()
                .filter(s -> s.getName().equals(name))
                .findFirst()
                .map(Scenario::clone);
    }

### Classe BuyLocomotiveUI

    public class BuyLocomotiveUI implements Runnable {
    private final BuyLocomotiveController controller;
    private final Scanner scanner;

    public BuyLocomotiveUI() {
        this.controller = new BuyLocomotiveController();
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        System.out.println("\n=== Buy Locomotive ===");
        listAvailableLocomotives();
        buyLocomotiveProcess();
    }

    private void listAvailableLocomotives() {
        List<Locomotive> locomotives = controller.getAvailableLocomotives();

        if (locomotives.isEmpty()) {
            System.out.println("\nNo locomotives available for purchase.");
            return;
        }

        System.out.println("\nAvailable Locomotives:");
        System.out.println("Ref.    Type      Power  Price    Year");
        System.out.println("-------------------------------------");

        for (Locomotive loco : locomotives) {
            System.out.printf("%-6s  %-8s  %4dkW  %6.2f  %4d%n",
                    loco.getReference(),
                    loco.getType(),
                    loco.getPower(),
                    loco.getPrice(),
                    loco.getStartYear());
        }
    }

    private void buyLocomotiveProcess() {
        System.out.print("\nEnter locomotive reference to buy (or '0' to cancel): ");
        String ref = scanner.nextLine().trim();

        if (ref.equals("0")) {
            System.out.println("Operation canceled.");
            return;
        }

        Optional<Locomotive> purchased = controller.buyLocomotive(ref);

        if (purchased.isPresent()) {
            System.out.printf("%nSuccessfully purchased locomotive:%n%s%n", purchased.get());
        } else {
            System.out.println("\nFailed to purchase locomotive. Invalid reference or insufficient funds.");
        }
    }



## 6. Integration and Demo 

* The "Buy Locomotive" functionality integrates with the system through the use of the ScenarioManager, which provides the current simulation year, ensuring that only locomotives available during that period are listed. It also uses the AuthenticationRepository to identify the user and the OrganizationRepository to validate the organization's budget before completing the purchase.

## 7. Observations
n/a