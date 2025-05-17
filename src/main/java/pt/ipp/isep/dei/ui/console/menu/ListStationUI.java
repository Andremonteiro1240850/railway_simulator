package pt.ipp.isep.dei.ui.console.menu;

import pt.ipp.isep.dei.controller.StationController;
import pt.ipp.isep.dei.domain.Player;
import pt.ipp.isep.dei.domain.Station;

import java.util.List;
import java.util.Scanner;

public class ListStationUI {
    private final StationController controller;
    private final Player currentPlayer;

    public ListStationUI(Player player) {
        this.controller = new StationController();
        this.currentPlayer = player;
    }

    public void listAndSelectStation() {
        System.out.println("\n=== MY STATIONS ===");
        listAllStations();

        System.out.println("\nEnter station name to view details (or 'exit' to cancel):");
        String input = new Scanner(System.in).nextLine();

        if (!input.equalsIgnoreCase("exit")) {
            displayStationDetails(input);
        }
    }

    private void listAllStations() {
        List<Station> stations = controller.getStationsByPlayer(currentPlayer);

        if (stations.isEmpty()) {
            System.out.println("You don't have any stations yet.");
            return;
        }

        System.out.println("Your Stations:");
        stations.forEach(station ->
                System.out.printf("- %s (Location: %d,%d)\n",
                        station.getName(),
                        station.getLocationX(),
                        station.getLocationY()));
    }

    private void displayStationDetails(String stationName) {
        try {
            Station station = controller.getStationDetails(currentPlayer, stationName);
            String details = controller.formatStationDetails(station);

            System.out.println("\n=== STATION DETAILS ===");
            System.out.println(details);

            // Show menu for further actions
            showStationActionsMenu(station);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void showStationActionsMenu(Station station) {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("\nAvailable Actions:");
            System.out.println("1. View upgrades");
            System.out.println("2. Back to station list");
            System.out.println("3. Exit");
            System.out.print("Select option: ");

            String option = scanner.nextLine();

            switch (option) {
                case "1":
                    displayUpgrades(station);
                    break;
                case "2":
                    exit = true;
                    listAndSelectStation();
                    break;
                case "3":
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid option");
            }
        }
    }

    private void displayUpgrades(Station station) {
        System.out.println("\nCurrent Upgrades:");
        station.getUpgrades().forEach(upgrade ->
                System.out.println("- " + upgrade));
    }
}