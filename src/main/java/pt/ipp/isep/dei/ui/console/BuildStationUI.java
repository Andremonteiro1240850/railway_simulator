package pt.ipp.isep.dei.ui.console;

import pt.ipp.isep.dei.controller.BuildStationController;
import pt.ipp.isep.dei.domain.Station;
import pt.ipp.isep.dei.domain.TaskCategory;
import pt.ipp.isep.dei.repository.TypeStationRepository;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

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