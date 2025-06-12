package pt.ipp.isep.dei.ui.console;

import java.util.List;
import java.util.Scanner;

import pt.ipp.isep.dei.controller.ListStationController;
import pt.ipp.isep.dei.domain.Station;

public class ListStationUI implements Runnable {
    private final ListStationController controller;
    private final Scanner scanner;

    public ListStationUI() {
        this.controller = new ListStationController();
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void run() {
        System.out.println("\n=== List Stations with Upgrades ===");
        displayAllStations();
        waitForUserInput();
    }

    private void displayAllStations() {
        List<Station> stations = controller.getAllStations();

        System.out.println("\nStations List:");
        System.out.println("----------------------------");

        if (stations.isEmpty()) {
            System.out.println("No stations built");
        }

        stations.forEach(station -> {
            System.out.printf("Reference: %s\n", station.getReference());
            System.out.printf("Scenario: %s\n", station.getScenario());
            System.out.printf("Name: %s\n", station.getName());
            System.out.printf("Type: %s\n", station.getType());
            System.out.printf("Region: %S\n", station.getRegion());

            System.out.println("Upgrades:");
            if (station.getUpgrades().isEmpty()) {
                System.out.println("  No upgrades installed");
            } else {
                station.getUpgrades().forEach(upgrade -> {
                    System.out.printf("  - %s", upgrade.getDisplayName());
                });
            }
            System.out.println("----------------------------");
        });
    }

    private void waitForUserInput() {
        System.out.println("\nPress Enter to return to the main menu...");
        scanner.nextLine();
    }
}