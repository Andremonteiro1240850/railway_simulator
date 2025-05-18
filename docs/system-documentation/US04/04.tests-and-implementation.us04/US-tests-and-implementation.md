# US04 - Create a scenario

## 4. Tests

**Test 1:** Test if scenario is correctly created

    @Test
    public void testScenarioCreation() {
    Map map = new Map("Test Map", 10, 10, new Employee("test@email.com"));
    Scenario scenario = new Scenario("Test Scenario", map, 1900, 1950);

        assertEquals("Test Scenario", scenario.getName());
        assertEquals(map, scenario.getMap());
        assertEquals(1900, scenario.getStartYear());
        assertEquals(1950, scenario.getEndYear());
        assertEquals(1900, scenario.getCurrentYear());
        assertTrue(scenario.getAvailableLocomotiveTypes().isEmpty());
        assertNull(scenario.getPortBehavior());
        assertTrue(scenario.getHistoricalEvents().isEmpty());
    }
**Test 2:** Test add locomotive types to scenario

    @Test
    public void testAddLocomotiveTypes() {
        Map map = new Map("Test Map", 10, 10, new Employee("test@email.com"));
        Scenario scenario = new Scenario("Test Scenario", map, 1900, 1950);

        scenario.addLocomotiveType("Steam");
        scenario.addLocomotiveType("Diesel");

        List<String> types = scenario.getAvailableLocomotiveTypes();
        assertEquals(2, types.size());
        assertTrue(types.contains("Steam"));
        assertTrue(types.contains("Diesel"));
    }
**Test 3:** Test set port behavior

    @Test
    public void testSetPortBehavior() {
        Map map = new Map("Test Map", 10, 10, new Employee("test@email.com"));
        Scenario scenario = new Scenario("Test Scenario", map, 1900, 1950);
        PortBehavior behavior = new PortBehavior();

        scenario.setPortBehavior(behavior);

        assertEquals(behavior, scenario.getPortBehavior());
    }
**Test 4:** Test if scenario is correctly cloned

    @Test
    public void testScenarioCloning() {
        Map map = new Map("Test Map", 10, 10, new Employee("test@email.com"));
        Scenario original = new Scenario("Test Scenario", map, 1900, 1950);
        original.addLocomotiveType("Steam");

        PortBehavior behavior = new PortBehavior();
        behavior.addImportCargo("Coal");
        original.setPortBehavior(behavior);

        Scenario clone = original.clone();

        assertEquals(original.getName(), clone.getName());
        assertEquals(original.getMap(), clone.getMap());
        assertEquals(original.getAvailableLocomotiveTypes(), clone.getAvailableLocomotiveTypes());
        assertEquals(original.getPortBehavior(), clone.getPortBehavior());
        assertNotSame(original, clone);
    }
**Test 5:** Test if Scenario is correctly added to repository

    @Test
    public void testAddScenario() {
        ScenarioRepository repo = new ScenarioRepository();
        Map map = new Map("Test Map", 10, 10, new Employee("test@email.com"));
        Scenario scenario = new Scenario("Test Scenario", map, 1900, 1950);

        Optional<Scenario> result = repo.add(scenario);

        assertTrue(result.isPresent());
        assertEquals(scenario.getName(), result.get().getName());
        assertEquals(1, repo.getAll().size());
    }
**Test 6:** Test if is possible to add a null scenario to the repository

    @Test
    public void testAddNullScenario() {
        ScenarioRepository repo = new ScenarioRepository();
        Optional<Scenario> result = repo.add(null);

        assertFalse(result.isPresent());
        assertTrue(repo.getAll().isEmpty());
    }

**Test 7:** Test if is possible to find Scenario by name

    @Test
    public void testFindByName() {
        ScenarioRepository repo = new ScenarioRepository();
        Map map = new Map("Test Map", 10, 10, new Employee("test@email.com"));
        Scenario scenario = new Scenario("Test Scenario", map, 1900, 1950);
        repo.add(scenario);

        Optional<Scenario> found = repo.findByName("Test Scenario");
        Optional<Scenario> notFound = repo.findByName("Nonexistent");

        assertTrue(found.isPresent());
        assertEquals(scenario.getName(), found.get().getName());
        assertFalse(notFound.isPresent());
    }

**Test 8:** Test if is possible to get all scenarios saved on repository

    @Test
    public void testGetAllScenarios() {
        ScenarioRepository repo = new ScenarioRepository();
        Map map = new Map("Test Map", 10, 10, new Employee("test@email.com"));

        repo.add(new Scenario("Scenario 1", map, 1900, 1950));
        repo.add(new Scenario("Scenario 2", map, 1950, 2000));

        List<Scenario> allScenarios = repo.getAll();
        assertEquals(2, allScenarios.size());
        assertNotSame(allScenarios.get(0), allScenarios.get(1));
    }



## 5. Construction (Implementation)
### Class CreateScenarioController
```java
package pt.ipp.isep.dei.controller;

import pt.ipp.isep.dei.domain.*;
import pt.ipp.isep.dei.repository.*;
import pt.isep.lei.esoft.auth.domain.model.Email;

import java.util.List;
import java.util.Optional;

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
}
```
### Class Scenario
```java
package pt.ipp.isep.dei.domain;



import java.util.ArrayList;
import java.util.List;

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
}

```
### Class ScenarioManager
```java
package pt.ipp.isep.dei.domain;

import pt.ipp.isep.dei.domain.Scenario;
import pt.ipp.isep.dei.repository.ScenarioRepository;

import java.util.Optional;

public class ScenarioManager {
    private final ScenarioRepository scenarioRepo;
    private Scenario currentScenario;


    public ScenarioManager(ScenarioRepository scenarioRepo) {
        this.scenarioRepo = scenarioRepo;
    }

    public boolean setCurrentScenario(String scenarioName) {
        Optional<Scenario> scenario = scenarioRepo.findByName(scenarioName);
        scenario.ifPresent(s -> this.currentScenario = s);
        return scenario.isPresent();
    }

    public Optional<Scenario> getCurrentScenario() {
        return Optional.ofNullable(currentScenario);
    }

    public int getCurrentYear() {
        return currentScenario != null ? currentScenario.getCurrentYear() : -1;
    }
}
```
### Class ScenarioRepository
```java
package pt.ipp.isep.dei.repository;

import pt.ipp.isep.dei.domain.Scenario;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
}

```
### Class CreateScenarioUI
```java
package pt.ipp.isep.dei.ui.console;

import pt.ipp.isep.dei.controller.CreateScenarioController;
import pt.ipp.isep.dei.domain.Map;
import pt.ipp.isep.dei.domain.PortBehavior;
import pt.ipp.isep.dei.domain.Scenario;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class CreateScenarioUI implements Runnable {
    private final CreateScenarioController controller;
    private final Scanner scanner;

    public CreateScenarioUI() {
        this.controller = new CreateScenarioController();
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        System.out.println("\n=== Create New Scenario ===");

        // 1. Selecionar mapa
        Map selectedMap = selectMap();
        if (selectedMap == null) return;

        // 2. Obter detalhes do cenário
        System.out.print("\nEnter scenario name: ");
        String name = scanner.nextLine();

        System.out.print("Start year: ");
        int startYear = scanner.nextInt();

        System.out.print("End year: ");
        int endYear = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        // 3. Selecionar tipos de locomotivas
        List<String> locomotiveTypes = selectLocomotiveTypes();

        // 4. Configurar comportamento dos portos
        PortBehavior portBehavior = configurePortBehavior();

        // 5. Criar cenário
        Optional<Scenario> result = controller.createScenario(
                name, selectedMap, startYear, endYear, locomotiveTypes, portBehavior);

        if (result.isPresent()) {
            System.out.println("\nScenario created successfully!");
        } else {
            System.out.println("\nFailed to create scenario.");
        }
    }

    private Map selectMap() {
        List<Map> maps = controller.getAvailableMaps();

        if (maps.isEmpty()) {
            System.out.println("No maps available. Please create a map first.");
            return null;
        }

        System.out.println("\nAvailable Maps:");
        for (int i = 0; i < maps.size(); i++) {
            System.out.printf("%d. %s%n", i+1, maps.get(i).getName());
        }

        System.out.print("Select a map (0 to cancel): ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (choice == 0 || choice > maps.size()) return null;

        return maps.get(choice - 1);
    }

    private List<String> selectLocomotiveTypes() {
        List<String> types = new ArrayList<>();
        String[] options = {"Steam", "Diesel", "Electric"};

        System.out.println("\nSelect available locomotive types:");
        for (int i = 0; i < options.length; i++) {
            System.out.printf("%d. %s%n", i+1, options[i]);
        }
        System.out.println("0. Done selecting");

        while (true) {
            System.out.print("Select type to add (0 to finish): ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (choice == 0) break;
            if (choice > 0 && choice <= options.length) {
                String type = options[choice - 1];
                if (!types.contains(type)) {
                    types.add(type);
                    System.out.printf("Added %s locomotives%n", type);
                }
            }
        }

        return types;
    }
    private PortBehavior configurePortBehavior() {
        PortBehavior behavior = new PortBehavior();
        System.out.println("\n=== Configure Port Behavior ===");

        // Lista de cargas possíveis baseada no domínio do problema (Railroad Tycoon)
        List<String> possibleCargos = List.of(
                "coal", "iron", "bauxite", "nitrate", "vegetables", "cereals",
                "wool", "coffee", "rubber", "cattle", "steel", "textiles",
                "automobiles", "food", "passengers", "mail"
        );

        // Configurar cargas de importação
        System.out.println("\nConfigure Import Cargos:");
        List<String> selectedImports = selectCargosFromList(possibleCargos, "import");
        selectedImports.forEach(behavior::addImportCargo);

        // Configurar cargas de exportação
        System.out.println("\nConfigure Export Cargos:");
        List<String> selectedExports = selectCargosFromList(possibleCargos, "export");
        selectedExports.forEach(behavior::addExportCargo);

        // Configurar cargas de transformação
        System.out.println("\nConfigure Transformation Cargos:");
        List<String> selectedTransforms = selectCargosFromList(possibleCargos, "transform");
        selectedTransforms.forEach(behavior::addTransformCargo);

        System.out.println("\nPort behavior configuration completed!");
        System.out.println("Import cargos: " + behavior.getImportCargo());
        System.out.println("Export cargos: " + behavior.getExportCargo());
        System.out.println("Transform cargos: " + behavior.getTransformCargo());

        return behavior;
    }

    private List<String> selectCargosFromList(List<String> possibleCargos, String cargoType) {
        List<String> selected = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Available cargos for " + cargoType + ":");
        for (int i = 0; i < possibleCargos.size(); i++) {
            System.out.printf("%2d. %s%n", i+1, possibleCargos.get(i));
        }

        System.out.println("\nEnter the numbers of cargos to " + cargoType + " (comma separated, 0 to finish):");

        while (true) {
            System.out.print("Selection: ");
            String input = scanner.nextLine().trim();

            if (input.equals("0")) {
                break;
            }

            try {
                String[] selections = input.split(",");
                for (String s : selections) {
                    int index = Integer.parseInt(s.trim()) - 1;
                    if (index >= 0 && index < possibleCargos.size()) {
                        String cargo = possibleCargos.get(index);
                        if (!selected.contains(cargo)) {
                            selected.add(cargo);
                            System.out.println("Added '" + cargo + "' to " + cargoType + " cargos.");
                        }
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter numbers separated by commas.");
            }

            System.out.println("Current " + cargoType + " cargos: " + selected);
            System.out.print("Add more? (y/n): ");
            if (!scanner.nextLine().trim().equalsIgnoreCase("y")) {
                break;
            }
        }

        return selected;
    }


}
```

## 6. Integration and Demo

* The scenario creation process have:
    * Map Manager to look for the maps
    * Port Manager to retrieve behavior options
    * Industry Manager to apply generation frequencies

* User interaction:
    * Editor selects a map and defines port behavior, locomotive types
    *

## 7. Observations

n/ahis section, it is suggested to present a critical perspective on the developed work, pointing, for example, to other alternatives and or future related work._