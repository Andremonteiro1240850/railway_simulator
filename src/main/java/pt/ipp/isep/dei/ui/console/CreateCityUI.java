package pt.ipp.isep.dei.ui.console;

import pt.ipp.isep.dei.controller.CreateCityController;

import java.util.Scanner;

/**
 * UI for creating cities (US03).
 */
public class CreateCityUI {
    private final CreateCityController controller;
    private final Scanner scanner = new Scanner(System.in);

    /**
     * Constructs the UI with the given city controller.
     * @param controller the CreateCityController
     */
    public CreateCityUI(CreateCityController controller) {
        this.controller = controller;
    }

    /**
     * Starts the city creation process via console.
     */
    public void start() {
        System.out.print("City name: ");
        String name = scanner.nextLine();
        System.out.print("Position X: ");
        int x = scanner.nextInt();
        System.out.print("Position Y: ");
        int y = scanner.nextInt();
        System.out.print("Manual blocks? (yes/no): ");
        scanner.nextLine();
        String choice = scanner.nextLine().trim().toLowerCase();
        boolean success;
        if (choice.equals("yes")) {
            System.out.print("Number of house blocks: ");
            int blocks = scanner.nextInt();
            scanner.nextLine();
            success = controller.createCityManual(name, x, y, blocks);
        } else {
            success = controller.createCityAuto(name, x, y);
        }
        if (success) {
            System.out.println("City added successfully.");
        } else {
            System.err.println("Invalid input or parameters.");
        }
    }
}
