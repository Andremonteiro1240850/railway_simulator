package pt.ipp.isep.dei.ui.console;

import pt.ipp.isep.dei.controller.AddIndustryController;
import pt.ipp.isep.dei.domain.Employee;
import pt.ipp.isep.dei.domain.Industry;
import pt.ipp.isep.dei.domain.Map;
import pt.ipp.isep.dei.domain.Organization;
import pt.ipp.isep.dei.domain.enums.IndustryType;

import java.util.Optional;
import java.util.Scanner;

public class AddIndustryUI implements Runnable {
    private final AddIndustryController controller;
    private final Scanner input;
    private final Employee loggedInEmployee;

    public AddIndustryUI(Employee loggedInEmployee) {
        controller = new AddIndustryController();
        input = new Scanner(System.in);
        this.loggedInEmployee = loggedInEmployee;
    }

    public void run() {
        System.out.println("\n--- Add an Industry ---");
        IndustryType type = selectIndustryType();
        int x = requestCoordinate("X");
        int y = requestCoordinate("Y");
        String mapName = requestMap();

        Map map = getMapByName(mapName);

        if (map != null) {
            try {
                Industry tempIndustry = new Industry(type, map, x, y);
                Optional<Industry> result = controller.createIndustry(type, x, y, map, tempIndustry);
                result.ifPresentOrElse(
                        this::showSuccess,
                        () -> showError("Failed to create industry. Please check the details and try again.")
                );
            } catch (IllegalArgumentException e) {
                showError(e.getMessage());
            }
        } else {
            showError("Map not found. Please ensure the map name is correct.");
        }
    }

    private IndustryType selectIndustryType() {
        System.out.println("\nAvailable types:");
        System.out.println("1 - Primary (Mines/Farms)");
        System.out.println("2 - Transforming (Factories)");
        System.out.println("3 - Mixed (Ports)");

        int choice = input.nextInt();
        return switch(choice) {
            case 1 -> IndustryType.PRIMARY;
            case 2 -> IndustryType.TRANSFORMING;
            case 3 -> IndustryType.MIXED;
            default -> throw new IllegalArgumentException("Invalid industry type selected. Please choose 1, 2, or 3.");
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
        System.out.printf("\n✅ Industry %s created in (%d,%d)\n",
                industry.getType(),
                industry.getPositionX(),
                industry.getPositionY());
    }

    private void showError(String message) {
        System.out.println("\n❌ " + message);
    }

    private Map getMapByName(String mapName) {
        return getOrganizationFromSession()
                .flatMap(org -> org.getMaps().stream()
                        .filter(map -> mapName.equals(map.getName()))
                        .findFirst())
                .orElse(null);
    }

    private Optional<Organization> getOrganizationFromSession() {
        return Optional.ofNullable(loggedInEmployee != null ?
                loggedInEmployee.getOrganization() : null);
    }
}