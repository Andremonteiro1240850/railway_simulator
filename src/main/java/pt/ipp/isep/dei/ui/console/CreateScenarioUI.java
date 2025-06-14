package pt.ipp.isep.dei.ui.console;

import pt.ipp.isep.dei.controller.CreateScenarioController;
import pt.ipp.isep.dei.domain.HistoricalEvent;
import pt.ipp.isep.dei.domain.Map;
import pt.ipp.isep.dei.domain.PortBehavior;
import pt.ipp.isep.dei.domain.Scenario;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * UI class responsible for creating new scenarios.
 * Guides the user through selecting a map, inputting scenario details,
 * choosing locomotive types, configuring port behavior, and assigning historical events.
 */
public class CreateScenarioUI implements Runnable {
    private final CreateScenarioController controller;
    private final Scanner scanner;

    /**
     * Constructs the CreateScenarioUI, initializing controller and scanner.
     */
    public CreateScenarioUI() {
        this.controller = new CreateScenarioController();
        this.scanner = new Scanner(System.in);
    }

    /**
     * Runs the scenario creation flow:
     * - Selects map
     * - Requests scenario name, start/end years
     * - Selects locomotive types
     * - Configures port behavior
     * - Loads historical events
     * - Creates and confirms the scenario
     */
    public void run() {
        System.out.println("\n=== Create New Scenario ===");

        Map selectedMap = selectMap();
        if (selectedMap == null) return;

        System.out.print("\nEnter scenario name: ");
        String name = scanner.nextLine();

        System.out.print("Start year: ");
        int startYear = scanner.nextInt();

        System.out.print("End year: ");
        int endYear = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        List<String> locomotiveTypes = selectLocomotiveTypes();
        PortBehavior portBehavior = configurePortBehavior();

        List<HistoricalEvent> historicalEvents = controller.loadApplicableHistoricalEvents(startYear);
        showHistoricalEvents(startYear);

        double budget = 700000;

        boolean success = controller.createScenario(name, selectedMap, startYear, endYear,
                locomotiveTypes, portBehavior, budget, historicalEvents);

        if (success) {
            System.out.println("\nScenario created successfully!");
            Scenario scenario = controller.getScenario();
            System.out.println("Initial budget: " + scenario.getBudget() + "€");
        } else {
            System.out.println("\nFailed to create scenario.");
        }
    }

    /**
     * Displays a list of available maps and allows the user to select one.
     *
     * @return the selected Map or null if canceled
     */
    private Map selectMap() {
        List<Map> maps = controller.getAvailableMaps();

        if (maps.isEmpty()) {
            System.out.println("No maps available. Please create a map first.");
            return null;
        }

        System.out.println("\nAvailable Maps:");
        for (int i = 0; i < maps.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, maps.get(i).getName());
        }

        System.out.print("Select a map (0 to cancel): ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (choice == 0 || choice > maps.size()) return null;

        return maps.get(choice - 1);
    }

    /**
     * Displays historical events applicable to the scenario's start year.
     *
     * @param startYear the year from which to filter historical events
     */
    private void showHistoricalEvents(int startYear) {
        List<HistoricalEvent> events = controller.loadApplicableHistoricalEvents(startYear);

        if (events.isEmpty()) {
            System.out.println("Não existem eventos históricos aplicáveis para o ano " + startYear);
            return;
        }

        System.out.println("\nEventos Históricos Aplicáveis:");
        System.out.println("----------------------------");
        for (HistoricalEvent event : events) {
            System.out.println("- " + event.getName() +
                    " (" + event.getStartYear() + "-" + event.getEndYear() + ")");
            System.out.println("  " + event.getEffect());
            System.out.println();
        }
    }

    /**
     * Lets the user select multiple locomotive types from predefined options.
     *
     * @return list of selected locomotive type names
     */
    private List<String> selectLocomotiveTypes() {
        List<String> types = new ArrayList<>();
        String[] options = {"Steam", "Diesel", "Electric"};

        System.out.println("\nSelect available locomotive types:");
        for (int i = 0; i < options.length; i++) {
            System.out.printf("%d. %s%n", i + 1, options[i]);
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

    /**
     * Guides the user to configure the import, export, and transformation cargos of a port.
     *
     * @return a configured PortBehavior object
     */
    private PortBehavior configurePortBehavior() {
        PortBehavior behavior = new PortBehavior();
        System.out.println("\n=== Configure Port Behavior ===");

        List<String> possibleCargos = List.of(
                "coal", "iron", "bauxite", "nitrate", "vegetables", "cereals",
                "wool", "coffee", "rubber", "cattle", "steel", "textiles",
                "automobiles", "food", "passengers", "mail"
        );

        System.out.println("\nConfigure Import Cargos:");
        List<String> selectedImports = selectCargosFromList(possibleCargos, "import");
        selectedImports.forEach(behavior::addImportCargo);

        System.out.println("\nConfigure Export Cargos:");
        List<String> selectedExports = selectCargosFromList(possibleCargos, "export");
        selectedExports.forEach(behavior::addExportCargo);

        System.out.println("\nConfigure Transformation Cargos:");
        List<String> selectedTransforms = selectCargosFromList(possibleCargos, "transform");
        selectedTransforms.forEach(behavior::addTransformCargo);

        System.out.println("\nPort behavior configuration completed!");
        System.out.println("Import cargos: " + behavior.getImportCargo());
        System.out.println("Export cargos: " + behavior.getExportCargo());
        System.out.println("Transform cargos: " + behavior.getTransformCargo());

        return behavior;
    }

    /**
     * Allows the user to select cargo types for import, export, or transformation.
     *
     * @param possibleCargos list of all cargo options
     * @param cargoType      type of cargo selection (e.g., "import", "export", "transform")
     * @return list of selected cargos
     */
    private List<String> selectCargosFromList(List<String> possibleCargos, String cargoType) {
        List<String> selected = new ArrayList<>();

        System.out.println("Available cargos for " + cargoType + ":");
        for (int i = 0; i < possibleCargos.size(); i++) {
            System.out.printf("%2d. %s%n", i + 1, possibleCargos.get(i));
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
