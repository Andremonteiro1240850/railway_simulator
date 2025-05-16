package pt.ipp.isep.dei.ui.console;

import java.util.Scanner;

/**
 * User Interface for scenario creation.
 */
public class CreateScenarioUI {
    private final CreateScenarioController controller;

    /**
     * Constructs the UI with a given scenario controller.
     * @param controller scenario creation controller
     */
    public CreateScenarioUI(CreateScenarioController controller) {
        this.controller = controller;
    }

    /**
     * Runs the scenario creation flow with user interaction.
     */
    public void run() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter scenario name: ");
        String name = scanner.nextLine();

        System.out.println("Select transport type:");
        System.out.println("1. Steam\n2. Diesel\n3. Electric");
        int transportOption = scanner.nextInt();

        System.out.println("Select port behavior:");
        System.out.println("1. Import\n2. Export\n3. Transform");
        int portOption = scanner.nextInt();

        System.out.print("Set industry generation rate (per turn): ");
        int rate = scanner.nextInt();
        scanner.nextLine();

        if (controller.createScenario(name, transportOption, portOption, rate)) {
            System.out.println("Scenario successfully created.");
        } else {
            System.err.println("Scenario creation failed. Invalid input.");
        }
    }
}
