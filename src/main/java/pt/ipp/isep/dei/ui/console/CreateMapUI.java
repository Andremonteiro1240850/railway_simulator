package pt.ipp.isep.dei.ui.console;

import pt.ipp.isep.dei.controller.CreateMapController;

import java.util.Scanner;

/**
 * User Interface for map creation via console.
 */
public class CreateMapUI {

    private final CreateMapController controller;
    private final Scanner scanner = new Scanner(System.in);

    /**
     * Constructs the UI with the given controller.
     * @param controller the CreateMapController instance
     */
    public CreateMapUI(CreateMapController controller) {
        this.controller = controller;
    }

    /**
     * Starts the map creation process.
     */
    public void startCreation() {
        System.out.print("Request Map Name: ");
        String name = scanner.nextLine();
        if (!controller.validateName(name)) {
            showError("Invalid name: only letters, digits, '_' and '-' allowed");
            return;
        }

        System.out.print("Request Map Size (width height): ");
        int width = scanner.nextInt();
        int height = scanner.nextInt();
        scanner.nextLine(); // consume newline
        if (!controller.validateSize(width, height)) {
            showError("Invalid dimensions: must be positive integers");
            return;
        }

        boolean success = controller.createMap(name, width, height);
        if (success) {
            showSuccess("Map '" + name + "' created successfully.");
        } else {
            showError("Map with name '" + name + "' already exists or invalid parameters.");
        }
    }

    private void showSuccess(String message) {
        System.out.println(message);
    }

    private void showError(String error) {
        System.err.println(error);
    }
}
