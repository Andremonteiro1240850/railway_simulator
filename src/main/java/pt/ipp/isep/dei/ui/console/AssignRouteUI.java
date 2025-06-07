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


        List<Scenario> scenarios = scenarioRepository.getAll();


            int choice = -1;
            do {
                System.out.print("Enter the number of the scenario you want to use: ");
                try {
                    choice = Integer.parseInt(scanner.nextLine());
                    if (choice < 1 || choice > scenarios.size()) {
                        System.out.println("Invalid number. Please try again.");
                        choice = -1;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a number.");
                }
            } while (choice == -1);

            Scenario selectedScenario = scenarios.get(choice - 1);

            List<Station> purchasedStations = selectedScenario.getStationsPurchased();

            if (purchasedStations.isEmpty()) {
                System.out.println("This scenario has no stations.");
            } else {
                System.out.println("\n:: Purchased Stations in this Scenario:");
                for (Station station : purchasedStations) {
                    System.out.println(" - " + station);
                }
            }

            // lista onde vamos guardar os PointsOfRoute selecionados pelo utilizador
            List<PointsOfRoute> routePoints = new ArrayList<>();

            if (!purchasedStations.isEmpty()) {
                boolean addMorePoints;
                do {
                    // 1) Mostrar estações numeradas
                    System.out.println("\nSelect a station to add to the route:");
                    for (int i = 0; i < purchasedStations.size(); i++) {
                        System.out.printf(" [%d] %s%n", i + 1, purchasedStations.get(i).getName());
                    }

                    int stationIndex = -1;
                    do {
                        System.out.print("Enter the number of the station: ");
                        try {
                            stationIndex = Integer.parseInt(scanner.nextLine()) - 1;
                            if (stationIndex < 0 || stationIndex >= purchasedStations.size()) {
                                System.out.println("Invalid number. Please try again.");
                                stationIndex = -1;
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input. Please enter a number.");
                        }
                    } while (stationIndex == -1);

                    Station chosenStation = purchasedStations.get(stationIndex);

                    // 2) Mostrar valores de CargoMode numerados
                    System.out.println("\nSelect a Cargo Mode for this station:");
                    CargoMode[] modes = CargoMode.values();
                    for (int i = 0; i < modes.length; i++) {
                        System.out.printf(" [%d] %s%n", i + 1, modes[i].name());
                    }

                    int modeIndex = -1;
                    do {
                        System.out.print("Enter the number of the Cargo Mode: ");
                        try {
                            modeIndex = Integer.parseInt(scanner.nextLine()) - 1;
                            if (modeIndex < 0 || modeIndex >= modes.length) {
                                System.out.println("Invalid number. Please try again.");
                                modeIndex = -1;
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input. Please enter a number.");
                        }
                    } while (modeIndex == -1);

                    CargoMode chosenMode = modes[modeIndex];

                    // 3) Criar PointsOfRoute e adicionar à lista
                    PointsOfRoute point = new PointsOfRoute(chosenStation, chosenMode);
                    routePoints.add(point);
                    System.out.println("Added to route: " + point);

                    // 4) Perguntar se quer adicionar mais pontos
                    System.out.print("Do you want to add another station to the route? (yes/no): ");
                    String answer = scanner.nextLine().trim().toLowerCase();
                    addMorePoints = answer.equals("yes") || answer.equals("y");
                } while (addMorePoints);
            }

                List<Locomotive> purchasedLocos = selectedScenario.getLocomotivePurchased();

                if (purchasedLocos.isEmpty()) {
                    System.out.println("No locomotives purchased in this scenario, you need to buy one first.");
                    return;
                }

                System.out.println("\n:: Select a locomotive:");
                for (int i = 0; i < purchasedLocos.size(); i++) {
                    System.out.println("[" + (i + 1) + "] " + purchasedLocos.get(i));
                }

                int locoIndex;
                while (true) {
                    System.out.print("Enter the number of the locomotive: ");
                    try {
                        locoIndex = Integer.parseInt(scanner.nextLine());
                        if (locoIndex >= 1 && locoIndex <= purchasedLocos.size()) break;
                        System.out.println("Invalid selection.");
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input.");
                    }
                }
                Locomotive selectedLocomotive = purchasedLocos.get(locoIndex - 1);

                // Add carriages
                List<Carriage> carriages = new ArrayList<>();
                boolean addMore;
                do {
                    System.out.println("\n:: Select a carriage to add:");
                    CarriageList[] values = CarriageList.values();
                    for (int i = 0; i < values.length; i++) {
                        System.out.println("[" + (i + 1) + "] " + values[i]);
                    }

                    int carriageChoice;
                    while (true) {
                        System.out.print("Enter your choice: ");
                        try {
                            carriageChoice = Integer.parseInt(scanner.nextLine());
                            if (carriageChoice >= 1 && carriageChoice <= values.length) break;
                            System.out.println("Invalid choice.");
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input.");
                        }
                    }

                    Carriage selected = values[carriageChoice - 1].toCarriage();
                    carriages.add(selected);

                    System.out.print("Do you want to add another carriage? (yes/no): ");
                    addMore = scanner.nextLine().trim().equalsIgnoreCase("yes");
                } while (addMore);

                // Create train and assign route
                Train train = new Train(selectedLocomotive, carriages);
                Route route= new Route(train,routePoints);

            }
    }






