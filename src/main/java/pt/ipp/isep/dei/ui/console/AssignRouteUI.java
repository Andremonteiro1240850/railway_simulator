package pt.ipp.isep.dei.ui.console;

import pt.ipp.isep.dei.controller.AssignRouteController;
import pt.ipp.isep.dei.domain.Locomotive;
import pt.ipp.isep.dei.domain.Route;
import pt.ipp.isep.dei.domain.Station;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class AssignRouteUI implements Runnable {
    private final AssignRouteController controller;
    private final Scanner scanner;

    public AssignRouteUI() {
        this.controller = new AssignRouteController();
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        System.out.println("\n=== Assign Train to Route ===");

        // 1. Selecionar estações para a rota
        List<Station> selectedStations = selectStationsForRoute();
        if (selectedStations.size() < 2) {
            System.out.println("A route must have at least 2 stations.");
            return;
        }

        // 2. Selecionar locomotiva
        Locomotive selectedLocomotive = selectLocomotive();
        if (selectedLocomotive == null) {
            return;
        }

        // 3. Selecionar cargas para transportar
        List<String> selectedCargos = selectCargosToTransport();
        if (selectedCargos.isEmpty()) {
            System.out.println("You must select at least one cargo type.");
            return;
        }

        // 4. Criar a rota
        Optional<Route> route = controller.createRoute(selectedStations, selectedLocomotive, selectedCargos);

        if (route.isPresent()) {
            System.out.println("\nRoute created successfully!");
            System.out.println("Stations: " + route.get().getStations());
            System.out.println("Locomotive: " + route.get().getLocomotive().getReference());
            System.out.println("Cargos to transport: " + route.get().getCargosToTransport());
        } else {
            System.out.println("\nFailed to create route.");
        }
    }

    private List<Station> selectStationsForRoute() {
        List<Station> stations = controller.getAllStations();
        List<Station> selectedStations = new ArrayList<>();

        if (stations.isEmpty()) {
            System.out.println("No stations available. Please create stations first.");
            return selectedStations;
        }

        System.out.println("\nSelect stations for the route (minimum 2):");
        int stationNumber = 1;
        for (Station station : stations) {
            System.out.printf("%d. %s (%s)%n", stationNumber++, station.getReference(), station.getType());
        }
        System.out.println("0. Finish selection");

        while (true) {
            System.out.print("\nSelect station to add to route (0 to finish): ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (choice == 0) {
                if (selectedStations.size() >= 2) {
                    break;
                } else {
                    System.out.println("You need to select at least 2 stations for a route.");
                    continue;
                }
            }

            if (choice > 0 && choice <= stations.size()) {
                Station selected = stations.get(choice - 1);
                if (!selectedStations.contains(selected)) {
                    selectedStations.add(selected);
                    System.out.println("Added " + selected.getReference() + " to route.");
                } else {
                    System.out.println("Station already added to route.");
                }
            } else {
                System.out.println("Invalid selection.");
            }
        }

        return selectedStations;
    }

    private Locomotive selectLocomotive() {
        List<Locomotive> locomotives = controller.getAllLocomotives();

        if (locomotives.isEmpty()) {
            System.out.println("No locomotives available. Please purchase locomotives first.");
            return null;
        }

        System.out.println("\nAvailable Locomotives:");
        for (int i = 0; i < locomotives.size(); i++) {
            Locomotive loco = locomotives.get(i);
            System.out.printf("%d. %s (%s, Power: %d)%n",
                    i+1, loco.getName(), loco.getType(), loco.getPower());
        }

        System.out.print("Select locomotive: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (choice > 0 && choice <= locomotives.size()) {
            return locomotives.get(choice - 1);
        } else {
            System.out.println("Invalid selection.");
            return null;
        }
    }

    private List<String> selectCargosToTransport() {
        List<String> cargos = controller.getAllCargos();
        List<String> selectedCargos = new ArrayList<>();

        System.out.println("\nSelect cargos to transport:");
        for (int i = 0; i < cargos.size(); i++) {
            System.out.printf("%d. %s%n", i+1, cargos.get(i));
        }
        System.out.println("0. Finish selection");

        while (true) {
            System.out.print("\nSelect cargo to add (0 to finish): ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (choice == 0) {
                break;
            }

            if (choice > 0 && choice <= cargos.size()) {
                String cargo = cargos.get(choice - 1);
                if (!selectedCargos.contains(cargo)) {
                    selectedCargos.add(cargo);
                    System.out.println("Added " + cargo + " to transport list.");
                } else {
                    System.out.println("Cargo already selected.");
                }
            } else {
                System.out.println("Invalid selection.");
            }
        }

        return selectedCargos;
    }
}