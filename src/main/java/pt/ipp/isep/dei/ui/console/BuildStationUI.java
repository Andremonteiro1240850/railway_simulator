package pt.ipp.isep.dei.ui.console;

import pt.ipp.isep.dei.controller.BuildStationController;
import pt.ipp.isep.dei.domain.Player;
import pt.ipp.isep.dei.domain.Station;
import pt.ipp.isep.dei.domain.TaskCategory;
import pt.ipp.isep.dei.domain.enums.Regions;
import pt.ipp.isep.dei.domain.enums.StationTypes;
import pt.ipp.isep.dei.repository.Repositories;
import pt.ipp.isep.dei.repository.TypeStationRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class BuildStationUI implements Runnable {
    private final BuildStationController controller;
    private String type;
    private String region;
    private String name;
    private int x;
    private int y;
    private Player player;

    public BuildStationUI() {
        this.controller = new BuildStationController();
    }

    public void run() {
        System.out.println("\n\n--- Create Station ------------------------");

        requestStationData();
        submitData();
    }

    private void submitData() {
        boolean success = controller.createStation(name, type, region, x, y);

        if (success) {
            System.out.println("\n✅ Station successfully created!");
        } else {
            System.out.println("\n❌ Station not created! Please check if the type, region, location, or budget are valid.");
        }
    }



    private void requestStationData() {
        // Request basic station information
        name = requestName();
        x = requestx();
        y = requesty();
        // Select station type and region if applicable
        selectType();
        selectRegion();

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
        System.out.print("Name: ");
        return input.nextLine();
    }

}