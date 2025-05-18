package pt.ipp.isep.dei.ui.console;

import pt.ipp.isep.dei.repository.Repositories;
import pt.ipp.isep.dei.repository.StationRepository;

import java.util.Scanner;

public class ListStationUI implements Runnable {
    private final StationRepository stationRepository;
    private final Scanner scanner;

    public ListStationUI() {
        this.stationRepository = Repositories.getInstance().getStationRepository();
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        System.out.println("\n=== List Stations with Upgrades ===");
        listAllStations();
        waitForUserInput();
    }

    private void listAllStations() {
        System.out.println("\nStations List:");
        System.out.println("----------------------------");

        stationRepository.getAll().forEach(station -> {
            System.out.printf("Reference: %s\n", station.getReference());
            System.out.printf("Name: %s\n", station.getType());
            System.out.printf("Type: %s\n", station.getName());

            if (station.getName().equals("station")) {
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