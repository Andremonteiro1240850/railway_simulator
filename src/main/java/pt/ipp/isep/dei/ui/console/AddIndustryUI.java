package pt.ipp.isep.dei.ui.console;

import pt.ipp.isep.dei.domain.*;
import pt.ipp.isep.dei.domain.enums.IndustryType;
import java.util.*;

public class AddIndustryUI implements Runnable {
    private final AddIndustryController controller;
    private final Scanner input;

    public AddIndustryUI() {
        controller = new AddIndustryController();
        input = new Scanner(System.in);
    }

    public void run() {
        System.out.println("\n--- Adicionar Indústria ---");
        IndustryType type = selectIndustryType();
        int x = requestCoordinate("X");
        int y = requestCoordinate("Y");
        String mapName = requestMap();

        Optional<Industry> result = controller.createIndustry(type, x, y, mapName);
        result.ifPresentOrElse(
                industry -> showSuccess(industry),
                () -> showError("Falha ao criar indústria")
        );
    }

    private IndustryType selectIndustryType() {
        System.out.println("\nTipos disponíveis:");
        System.out.println("1 - Primary (Mines/Farms)");
        System.out.println("2 - Transforming (Factories)");
        System.out.println("3 - Mixed (Ports)");

        int choice = input.nextInt();
        return switch(choice) {
            case 1 -> IndustryType.PRIMARY;
            case 2 -> IndustryType.TRANSFORMING;
            case 3 -> IndustryType.MIXED;
            default -> throw new IllegalArgumentException("Invalid choice");
        };
    }

    private int requestCoordinate(String label) {
        System.out.print(label + ": ");
        return input.nextInt();
    }

    private String requestMap() {
        System.out.print("Map name: ");
        return input.next();
    }

    private void showSuccess(Industry industry) {
        System.out.printf(
                "\n✅ Indústria %s criada em (%d,%d)",
                industry.getType(), industry.getPositionX(), industry.getPositionY()
        );
    }

    private void showError(String message) {
        System.out.println("\n❌ " + message);
    }
}