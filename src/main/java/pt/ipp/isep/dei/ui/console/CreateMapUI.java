package pt.ipp.isep.dei.ui.console;

import pt.ipp.isep.dei.controller.CreateMapController;
import pt.ipp.isep.dei.domain.Organization;
import pt.ipp.isep.dei.domain.enums.ScaleUnit;

import java.util.Optional;
import java.util.Scanner;

/**
 * UI for map creation.
 * Handles user interaction to collect map details and delegates creation to the controller.
 */
public class CreateMapUI implements Runnable {
    private final CreateMapController controller = new CreateMapController();
    private final Organization organization;

    /**
     * Constructs the CreateMapUI with a given organization context.
     *
     * @param organization the organization associated with the new map
     */
    public CreateMapUI(Organization organization) {
        this.organization = organization;
    }

    /**
     * Runs the UI flow for creating a map:
     * - Requests name, dimensions, and scale unit
     * - Submits data to the controller
     * - Prints the result message
     */
    public void run() {
        System.out.println("\n--- Create Map ---");

        Scanner input = new Scanner(System.in);
        String name = requestName(input);
        int width = requestDimension(input, "Width (X)");
        int height = requestDimension(input, "Height (Y)");
        ScaleUnit scale = requestScale(input);

        Optional<String> message = controller.createMap(name, width, height, scale, organization);
        message.ifPresent(System.out::println);
    }

    /**
     * Prompts the user to enter the map name.
     *
     * @param input the Scanner for reading user input
     * @return the trimmed map name
     */
    private String requestName(Scanner input) {
        System.out.print("Map Name: ");
        return input.nextLine().trim();
    }

    /**
     * Prompts the user to enter a dimension (width or height) for the map.
     *
     * @param input the Scanner for reading user input
     * @param label the label to display (e.g., "Width (X)" or "Height (Y)")
     * @return the validated positive integer dimension
     */
    private int requestDimension(Scanner input, String label) {
        while (true) {
            try {
                System.out.print(label + ": ");
                int value = Integer.parseInt(input.nextLine());
                if (value > 0) return value;
                System.out.println("❌ Must be positive.");
            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid input.");
            }
        }
    }

    /**
     * Prompts the user to enter a scale unit (KM, MILES, METERS).
     * If input is invalid, defaults to KM.
     *
     * @param input the Scanner for reading user input
     * @return the selected ScaleUnit
     */
    private ScaleUnit requestScale(Scanner input) {
        System.out.print("Scale (KM/MILES/METERS): ");
        try {
            return ScaleUnit.valueOf(input.nextLine().trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Defaulting to KM.");
            return ScaleUnit.KM;
        }
    }
}

