package pt.ipp.isep.dei.ui.console;

import pt.ipp.isep.dei.controller.CreateMapController;
import pt.ipp.isep.dei.domain.Organization;
import pt.ipp.isep.dei.domain.enums.ScaleUnit;

import java.util.Optional;
import java.util.Scanner;

/**
 * UI for map creation.
 */
public class CreateMapUI implements Runnable {
    private final CreateMapController controller = new CreateMapController();
    private final Organization organization;

    public CreateMapUI(Organization organization) {
        this.organization = organization;
    }

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

    private String requestName(Scanner input) {
        System.out.print("Map Name: ");
        return input.nextLine().trim();
    }

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
