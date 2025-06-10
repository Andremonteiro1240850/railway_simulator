package pt.ipp.isep.dei.ui.console;

import pt.ipp.isep.dei.controller.ListStationController;
import pt.ipp.isep.dei.domain.Station;
import java.util.List;
import java.util.Scanner;

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

        stations.forEach(station -> {
            System.out.printf("Reference: %s\n", station.getReference());
            System.out.printf("Name: %s\n", station.getName());
            System.out.printf("Type: %s\n", station.getType());

            if (station.getType().equals("station")) {
                System.out.printf("Region: %s\n", station.getRegion());
            }

            System.out.println("Upgrades:");
            if (station.getUpgrades().isEmpty()) {
                System.out.println("  No upgrades installed");
            } else {
                station.getUpgrades().forEach(upgrade -> {
                    System.out.printf("  - %s", upgrade.getType());
                    if (upgrade.getSubType() != null) {
                        System.out.printf(" (%s)", upgrade.getSubType());
                    }
                    System.out.println();
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