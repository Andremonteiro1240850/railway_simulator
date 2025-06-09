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

    public BuildStationUI() {
        this.controller = new BuildStationController();
    }


    public void run() {
        System.out.println("\n\n--- Create Station ------------------------");

        if (!requestScenario()) {
            return; // Volta ao menu do jogador
        }

        requestStationData();
        submitData();
    }

    private void submitData() {
        boolean success = controller.createStation(type, name, region, x, y);

        if (success) {
            System.out.println("\n✅ Station successfully created!");
        } else {
            System.out.println("\n❌ Station not created! Please check if the type, region, location, or budget are valid.");
        }
    }



    private void requestStationData() {
        // Request basic station information

        x = requestx();
        y = requesty();
        // Select station type and region if applicable
        selectType();
        selectRegion();
        showNameSuggestion();
        name = requestName();

    }
    private void showNameSuggestion() {
        // Obtém a sugestão do controller
        String suggestedName = controller.suggestStationName(x, y, StationTypes.valueOf(type.toUpperCase()));

        System.out.println("\n💡 Suggested name: " + suggestedName);
        System.out.println("(Press Enter to accept this name or type a new one)");
    }
    private int requestx() {
        Scanner input = new Scanner(System.in);
        System.out.print("X: ");
        return input.nextInt();
    }
    private int requesty() {
        Scanner input = new Scanner(System.in);
        System.out.print("Y: ");
        return input.nextInt();
    }
    private void selectType(){
        List<StationTypes> types = List.of(StationTypes.values());
        StationTypes selectType = selectFromList(types,"Select Type");
        type = selectType.getType();

    }
    private void selectRegion(){
        List<Regions> regions = List.of(Regions.values());
        Regions selectRegion = selectFromList(regions,"Select Region");
        region = selectRegion.getRegion();

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
        System.out.print("Name [" + controller.suggestStationName(x, y, StationTypes.valueOf(type.toUpperCase())) + "]: ");
        String name = input.nextLine();

        // Se o usuário apenas pressionar Enter, usa o nome sugerido
        if (name.trim().isEmpty()) {
            return controller.suggestStationName(x, y, StationTypes.valueOf(type.toUpperCase()));
        }
        return name;
    }
    private boolean requestScenario() {
        ScenarioRepository repo = Repositories.getInstance().getScenarioRepository(); // ou injete esta instância
        List<Scenario> scenarios = repo.getAll();

        if (scenarios.isEmpty()) {
            System.out.println("❌ No scenarios available. Please create one first.");
            return false; // Sinaliza que deve voltar ao menu
        }

        Scenario selected = selectFromList(scenarios, "Select Scenario");
        this.selectedScenario = selected;

        System.out.println("✅ Scenario '" + selected.getName() + "' selected.");
        controller.setScenario(selected); // Garanta que este método existe

        return true;
    }


}