package pt.ipp.isep.dei.ui.console;

import pt.ipp.isep.dei.controller.BuildStationController;
import pt.ipp.isep.dei.domain.Player;
import pt.ipp.isep.dei.domain.Scenario;
import pt.ipp.isep.dei.domain.enums.Regions;
import pt.ipp.isep.dei.domain.enums.StationTypes;
import pt.ipp.isep.dei.repository.Repositories;
import pt.ipp.isep.dei.repository.ScenarioRepository;

import java.util.List;
import java.util.Scanner;

/**
 * UI class for building a station within a scenario.
 * It allows users to input location, type, region, and optionally set the name of the station.
 */
public class BuildStationUI implements Runnable {
    private final BuildStationController controller;
    private String type;
    private String region;
    private String name;
    private int x;
    private int y;
    private Player player;
    private ScenarioRepository scenarioRepository;
    private Scenario selectedScenario;

    /**
     * Constructs the BuildStationUI and initializes the controller.
     */
    public BuildStationUI() {
        this.controller = new BuildStationController();
    }

    /**
     * Runs the station creation flow. User selects a scenario and inputs station details.
     */
    public void run() {
        System.out.println("\n\n--- Create Station ------------------------");

        if (!requestScenario()) {
            return; // Volta ao menu do jogador
        }

        requestStationData();
        submitData();
    }

    /**
     * Submits the collected data to the controller and shows the result.
     */
    private void submitData() {
        boolean success = controller.createStation(type, name, region, x, y);

        if (success) {
            System.out.println("\n✅ Station successfully created!");
        } else {
            System.out.println("\n❌ Station not created! Please check if the type, region, location, or budget are valid.");
        }
    }

    /**
     * Prompts the user to enter all necessary station data.
     */
    private void requestStationData() {
        x = requestx();
        y = requesty();
        selectType();
        selectRegion();
        showNameSuggestion();
        name = requestName();
    }

    /**
     * Displays a suggested name based on location and type.
     */
    private void showNameSuggestion() {
        String suggestedName = controller.suggestStationName(x, y, StationTypes.valueOf(type.toUpperCase()));
        System.out.println("\n💡 Suggested name: " + suggestedName);
        System.out.println("(Press Enter to accept this name or type a new one)");
    }

    /**
     * Prompts the user to enter the X coordinate.
     *
     * @return the X coordinate
     */
    private int requestx() {
        Scanner input = new Scanner(System.in);
        System.out.print("X: ");
        return input.nextInt();
    }

    /**
     * Prompts the user to enter the Y coordinate.
     *
     * @return the Y coordinate
     */
    private int requesty() {
        Scanner input = new Scanner(System.in);
        System.out.print("Y: ");
        return input.nextInt();
    }

    /**
     * Lets the user select the station type from a list.
     */
    private void selectType() {
        List<StationTypes> types = List.of(StationTypes.values());
        StationTypes selectType = selectFromList(types, "Select Type");
        type = selectType.getType();
    }

    /**
     * Lets the user select the station region from a list.
     */
    private void selectRegion() {
        List<Regions> regions = List.of(Regions.values());
        Regions selectRegion = selectFromList(regions, "Select Region");
        region = selectRegion.getRegion();
    }

    /**
     * Generic helper method to prompt the user to select an option from a list.
     *
     * @param items  the list of items to choose from
     * @param prompt the prompt text
     * @param <T>    the type of items
     * @return the selected item
     */
    private <T> T selectFromList(List<T> items, String prompt) {
        Scanner input = new Scanner(System.in);
        int selection = -1;

        while (selection < 1 || selection > items.size()) {
            System.out.println("\n" + prompt + " options:");
            for (int i = 0; i < items.size(); i++) {
                System.out.printf("  %d - %s%n", i + 1, items.get(i).toString());
            }
            System.out.print("Select " + prompt + ": ");
            selection = input.nextInt();
            input.nextLine(); // Consume newline
        }

        return items.get(selection - 1);
    }

    /**
     * Prompts the user to input a custom name or accept the suggested one.
     *
     * @return the chosen or suggested station name
     */
    private String requestName() {
        Scanner input = new Scanner(System.in);
        System.out.print("Name [" + controller.suggestStationName(x, y, StationTypes.valueOf(type.toUpperCase())) + "]: ");
        String name = input.nextLine();

        if (name.trim().isEmpty()) {
            return controller.suggestStationName(x, y, StationTypes.valueOf(type.toUpperCase()));
        }
        return name;
    }

    /**
     * Prompts the user to select a scenario from the list of existing ones.
     *
     * @return true if a scenario was selected, false otherwise
     */
    private boolean requestScenario() {
        ScenarioRepository repo = Repositories.getInstance().getScenarioRepository();
        List<Scenario> scenarios = repo.getAll();

        if (scenarios.isEmpty()) {
            System.out.println("❌ No scenarios available. Please create one first.");
            return false;
        }

        Scenario selected = selectFromList(scenarios, "Select Scenario");
        this.selectedScenario = selected;

        System.out.println("✅ Scenario '" + selected.getName() + "' selected.");
        controller.setScenario(selected); // Ensure this method exists in controller

        return true;
    }
}
