package pt.ipp.isep.dei.ui.console;

import pt.ipp.isep.dei.controller.AddIndustryController;
import pt.ipp.isep.dei.domain.*;
import pt.ipp.isep.dei.domain.enums.IndustryType;
import pt.ipp.isep.dei.repository.CreateMapRepository;
import pt.ipp.isep.dei.repository.Repositories;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class AddIndustryUI implements Runnable {
    private final AddIndustryController controller;
    private final Scanner input;

    public AddIndustryUI() {
        controller = new AddIndustryController();
        input = new Scanner(System.in);
    }

    public void run() {
        System.out.println("\n--- Add an Industry ---");
        IndustryType type = selectIndustryType();
        int x = requestCoordinate("X");
        int y = requestCoordinate("Y");

        Map map = requestMap();

        if (map != null) {
            try {
                Industry tempIndustry = new Industry(type, map, x, y);
                Optional<Industry> result = controller.createIndustry(type, x, y, map);
                result.ifPresentOrElse(
                        this::showSuccess,
                        () -> showError("Failed to create industry. Please check the details and try again.")
                );
            } catch (IllegalArgumentException e) {
                showError(e.getMessage());
            }
        } else {
            showError("Map not found or no map selected.");
        }
    }

    private IndustryType selectIndustryType() {
        System.out.println("\nAvailable types:");
        System.out.println("1 - Primary (Mines/Farms)");
        System.out.println("2 - Transforming (Factories)");
        System.out.println("3 - Mixed (Ports)");

        while (true) {
            System.out.print("Select industry type (1-3): ");
            if (input.hasNextInt()) {
                int choice = input.nextInt();
                input.nextLine(); // consumir quebra de linha
                switch (choice) {
                    case 1: return IndustryType.PRIMARY;
                    case 2: return IndustryType.TRANSFORMING;
                    case 3: return IndustryType.MIXED;
                    default:
                        System.out.println("Invalid choice. Please enter 1, 2, or 3.");
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                input.nextLine(); // limpar entrada inválida
            }
        }
    }

    private int requestCoordinate(String label) {
        while (true) {
            System.out.print(label + ": ");
            if (input.hasNextInt()) {
                int coord = input.nextInt();
                input.nextLine(); // consumir quebra de linha
                return coord;
            } else {
                System.out.println("Invalid number. Please enter an integer.");
                input.nextLine(); // limpar entrada inválida
            }
        }
    }

    private Map requestMap() {
        CreateMapRepository repository = Repositories.getInstance().getMapRepository();
        List<Map> availableMaps = repository.getAll();

        if (availableMaps.isEmpty()) {
            System.out.println("No maps available.");
            return null;
        }

        System.out.println("Available Maps:");
        for (int i = 0; i < availableMaps.size(); i++) {
            System.out.println((i + 1) + " - " + availableMaps.get(i).getName());
        }

        while (true) {
            System.out.print("Select a map by number (1 to " + availableMaps.size() + "): ");
            if (input.hasNextInt()) {
                int selectedIndex = input.nextInt();
                input.nextLine(); // consumir quebra de linha
                if (selectedIndex >= 1 && selectedIndex <= availableMaps.size()) {
                    return availableMaps.get(selectedIndex - 1);
                } else {
                    System.out.println("Invalid number. Please select a number between 1 and " + availableMaps.size());
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                input.nextLine(); // limpar entrada inválida
            }
        }
    }

    private void showSuccess(Industry industry) {
        System.out.printf("\n✅ Industry %s created in (%d,%d)\n",
                industry.getType(),
                industry.getPositionX(),
                industry.getPositionY());
    }

    private void showError(String message) {
        System.out.println("\n❌ " + message);
    }
}
