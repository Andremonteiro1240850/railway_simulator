package pt.ipp.isep.dei.ui.console;

import pt.ipp.isep.dei.controller.AssignRouteController;
import pt.ipp.isep.dei.controller.BuyLocomotiveController;
import pt.ipp.isep.dei.domain.Locomotive;
import pt.ipp.isep.dei.domain.Scenario;
import pt.ipp.isep.dei.repository.Repositories;
import pt.ipp.isep.dei.repository.ScenarioRepository;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import java.util.List;
import java.util.Scanner;

public class BuyLocomotiveUI implements Runnable {

    private final AssignRouteController controller;
    private final Scanner scanner = new Scanner(System.in);
    private final ScenarioRepository scenarioRepository;

    public BuyLocomotiveUI() {
        this.controller = new AssignRouteController ();
        this.scenarioRepository = Repositories.getInstance().getScenarioRepository();
    }

    @Override
    public void run() {
        List<Scenario> scenarios = scenarioRepository.getAll();

        if (scenarios.isEmpty()) {
            System.out.println("No scenarios available.");
            return;
        }

        boolean keepBuying;
        do {
            System.out.println("\n:: Select a scenario to proceed with the purchase.\n");

            for (int i = 0; i < scenarios.size(); i++) {
                System.out.println("[" + (i + 1) + "]\n" + scenarios.get(i));
            }

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

            System.out.println("\n:: Select the name of locomotive you want.\n");
            controller.showAvailableLocomotives(selectedScenario);

            System.out.print("Enter the name of the locomotive you wish to buy: ");
            String name = scanner.nextLine();

            controller.buyLocomotive(name, selectedScenario);

            System.out.print("\nDo you want to buy another locomotive? (yes/no): ");
            String response = scanner.nextLine().trim().toLowerCase();
            keepBuying = response.equals("yes") || response.equals("y");

        } while (keepBuying);


    }
}
