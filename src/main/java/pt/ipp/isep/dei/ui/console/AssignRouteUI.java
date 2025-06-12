package pt.ipp.isep.dei.ui.console;

import pt.ipp.isep.dei.controller.AssignRouteController;
import pt.ipp.isep.dei.controller.BuyLocomotiveController;
import pt.ipp.isep.dei.domain.*;
import pt.ipp.isep.dei.domain.enums.CargoMode;
import pt.ipp.isep.dei.domain.enums.CarriageList;
import pt.ipp.isep.dei.repository.Repositories;
import pt.ipp.isep.dei.repository.ScenarioRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class AssignRouteUI implements Runnable {
    private final AssignRouteController controller;
    private final Scanner scanner;
    private final ScenarioRepository scenarioRepository;

    public AssignRouteUI() {
        this.controller = new AssignRouteController();
        this.scanner = new Scanner(System.in);
        this.scenarioRepository = Repositories.getInstance().getScenarioRepository();
    }

    public void run() {

        List<Scenario> scenarios = controller.getAvailableScenarios();
        if (scenarios.isEmpty()) {
            System.out.println("No scenarios found");
            return;
        }

        System.out.println("Available scenarios:");
        for (int i = 0; i < scenarios.size(); i++) {
            System.out.printf("[%d] %s%n", i + 1, scenarios.get(i).getSummary());
        }

        Scenario selectedScenario = scenarios.get(readIndex("Enter the number of the scenario you want to use:", scenarios.size()));

        List<Station> stations = selectedScenario.getStationsPurchased();
        if (stations.isEmpty()) {
            System.out.println("This scenario has no stations.");
            return;
        }

        List<PointsOfRoute> routePoints = new ArrayList<>();
        boolean addMore;
        do {
            System.out.println("\nSelect a station to add to the route:");
            for (int i = 0; i < stations.size(); i++) {
                System.out.printf("[%d] %s%n", i + 1, stations.get(i).getName());
            }

            Station chosenStation = stations.get(readIndex("Enter the number of the station:", stations.size()));

            CargoMode[] modes = CargoMode.values();
            System.out.println("\nSelect a Cargo Mode for this station:");
            for (int i = 0; i < modes.length; i++) {
                System.out.printf("[%d] %s%n", i + 1, modes[i].name());
            }

            CargoMode chosenMode = modes[readIndex("Enter the number of the Cargo Mode:", modes.length)];

            routePoints.add(controller.createPointOfRoute(chosenStation, chosenMode));

            System.out.print("Do you want to add another station to the route? (yes/no): ");
            addMore = scanner.nextLine().trim().equalsIgnoreCase("yes");
        } while (addMore);

        List<Locomotive> locomotives = selectedScenario.getLocomotivePurchased();
        if (locomotives.isEmpty()) {
            System.out.println("No locomotives purchased in this scenario.");
            return;
        }

        System.out.println("\nSelect a locomotive:");
        for (int i = 0; i < locomotives.size(); i++) {
            System.out.printf("[%d] %s%n", i + 1, locomotives.get(i));
        }
        Locomotive selectedLoco = locomotives.get(readIndex("Enter the number of the locomotive:", locomotives.size()));

        List<Carriage> carriages = new ArrayList<>();
        do {
            System.out.println("\nSelect a carriage to add:");
            CarriageList[] options = CarriageList.values();
            for (int i = 0; i < options.length; i++) {
                System.out.printf("[%d] %s%n", i + 1, options[i]);
            }

            Carriage selectedCarriage = options[readIndex("Enter your choice:", options.length)].toCarriage();
            carriages.add(selectedCarriage);

            System.out.print("Do you want to add another carriage? (yes/no): ");
        } while (scanner.nextLine().trim().equalsIgnoreCase("yes"));

        controller.assignRouteToTrain(selectedLoco, carriages, routePoints,selectedScenario);

        System.out.println("Route assigned successfully.");



    }

    private int readIndex(String prompt, int max) {
        int index = -1;
        do {
            System.out.print(prompt + " ");
            try {
                index = Integer.parseInt(scanner.nextLine()) - 1;
                if (index < 0 || index >= max) {
                    System.out.println("Invalid number. Try again.");
                    index = -1;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Enter a number.");
            }
        } while (index == -1);
        return index;
    }
}







