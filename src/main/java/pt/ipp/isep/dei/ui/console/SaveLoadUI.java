package pt.ipp.isep.dei.ui.console;

import pt.ipp.isep.dei.controller.SaveLoadController;
import pt.ipp.isep.dei.domain.Map;
import pt.ipp.isep.dei.domain.Scenario;
import pt.ipp.isep.dei.repository.Repositories;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * UI class responsible for handling save and load operations for maps and scenarios.
 */
public class SaveLoadUI implements Runnable {
    private final SaveLoadController controller;
    private final Scanner scanner;

    /**
     * Constructs the SaveLoadUI, initializing controller and scanner.
     */
    public SaveLoadUI() {
        this.controller = new SaveLoadController();
        this.scanner = new Scanner(System.in);
    }

    /**
     * Runs the save/load menu.
     */
    public void run() {
        boolean exit = false;
        while (!exit) {
            System.out.println("\n=== Save/Load Menu ===");
            System.out.println("1. Save Map");
            System.out.println("2. Load Map");
            System.out.println("3. Save Scenario");
            System.out.println("4. Load Scenario");
            System.out.println("0. Exit");

            System.out.print("\nSelect an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    saveMap();
                    break;
                case 2:
                    loadMap();
                    break;
                case 3:
                    saveScenario();
                    break;
                case 4:
                    loadScenario();
                    break;
                case 0:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void saveMap() {
        System.out.println("\n=== Save Map ===");
        List<Map> allMaps = Repositories.getInstance().getMapRepository().getAll();
        String[] savedMaps = controller.getSavedMaps();
        List<Map> unsavedMaps = allMaps.stream()
                .filter(map -> !contains(savedMaps, map.getName()))
                .collect(Collectors.toList());
        if (unsavedMaps.isEmpty()) {
            System.out.println("No unsaved maps available.");
            return;
        }
        System.out.println("Available maps to save:");
        for (int i = 0; i < unsavedMaps.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, unsavedMaps.get(i).getName());
        }
        System.out.print("Select a map to save (0 to cancel): ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        if (choice <= 0 || choice > unsavedMaps.size()) {
            System.out.println("Cancelled.");
            return;
        }
        Map selectedMap = unsavedMaps.get(choice - 1);
        System.out.print("Enter file name to save as: ");
        String filename = scanner.nextLine();
        if (controller.saveMap(selectedMap, filename)) {
            System.out.println("Map saved successfully!");
        } else {
            System.out.println("Failed to save map.");
        }
    }

    private void loadMap() {
        System.out.println("\n=== Load Map ===");
        String[] savedMaps = controller.getSavedMaps();

        if (savedMaps.length == 0) {
            System.out.println("No saved maps found.");
            return;
        }

        System.out.println("\nAvailable maps:");
        for (int i = 0; i < savedMaps.length; i++) {
            System.out.printf("%d. %s%n", i + 1, savedMaps[i]);
        }

        System.out.print("\nSelect a map to load (0 to cancel): ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (choice > 0 && choice <= savedMaps.length) {
            if (controller.loadMap(savedMaps[choice - 1])) {
                System.out.println("Map loaded successfully!");
            } else {
                System.out.println("Failed to load map.");
            }
        }
    }

    private void saveScenario() {
        System.out.println("\n=== Save Scenario ===");
        List<Scenario> allScenarios = Repositories.getInstance().getScenarioRepository().getAll();
        String[] savedScenarios = controller.getSavedScenarios();
        List<Scenario> unsavedScenarios = allScenarios.stream()
                .filter(scenario -> !contains(savedScenarios, scenario.getName()))
                .collect(Collectors.toList());
        if (unsavedScenarios.isEmpty()) {
            System.out.println("No unsaved scenarios available.");
            return;
        }
        System.out.println("Available scenarios to save:");
        for (int i = 0; i < unsavedScenarios.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, unsavedScenarios.get(i).getName());
        }
        System.out.print("Select a scenario to save (0 to cancel): ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        if (choice <= 0 || choice > unsavedScenarios.size()) {
            System.out.println("Cancelled.");
            return;
        }
        Scenario selectedScenario = unsavedScenarios.get(choice - 1);
        System.out.print("Enter file name to save as: ");
        String filename = scanner.nextLine();
        if (controller.saveScenario(selectedScenario, filename)) {
            System.out.println("Scenario saved successfully!");
        } else {
            System.out.println("Failed to save scenario.");
        }
    }

    private void loadScenario() {
        System.out.println("\n=== Load Scenario ===");
        String[] savedScenarios = controller.getSavedScenarios();

        if (savedScenarios.length == 0) {
            System.out.println("No saved scenarios found.");
            return;
        }

        System.out.println("\nAvailable scenarios:");
        for (int i = 0; i < savedScenarios.length; i++) {
            System.out.printf("%d. %s%n", i + 1, savedScenarios[i]);
        }

        System.out.print("\nSelect a scenario to load (0 to cancel): ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (choice > 0 && choice <= savedScenarios.length) {
            if (controller.loadScenario(savedScenarios[choice - 1])) {
                System.out.println("Scenario loaded successfully!");
            } else {
                System.out.println("Failed to load scenario.");
            }
        }
    }

    private boolean contains(String[] array, String value) {
        for (String item : array) {
            if (item.equals(value)) {
                return true;
            }
        }
        return false;
    }
} 