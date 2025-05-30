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

    // Variável para armazenar o funcionário logado
    private Employee loggedInEmployee;

    public AddIndustryUI(Employee loggedInEmployee) {
        controller = new AddIndustryController();
        input = new Scanner(System.in);
        this.loggedInEmployee = loggedInEmployee;  // Armazena o funcionário logado
    }


    public void run() {
        System.out.println("\n--- Add an Industry ---");
        IndustryType type = selectIndustryType();  // Solicita o tipo de indústria
        int x = requestCoordinate("X");  // Solicita coordenada X
        int y = requestCoordinate("Y");  // Solicita coordenada Y
        String mapName = requestMap();  // Solicita o nome do mapa

        Map map = getMapByName(mapName);

        if (map != null) {
            try {
                // Chama o controlador passando o objeto Map
                Optional<Industry> result = controller.createIndustry(type, x, y, map);
                result.ifPresentOrElse(
                        industry -> showSuccess(industry),
                        () -> showError("Failqed to create industry. Please check the details and try again.")
                );
            } catch (IllegalArgumentException e) {
                showError(e.getMessage());
            }
        } else {
            showError("Map not found. Please ensure the map name is correct.");
        }
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
            default -> throw new IllegalArgumentException("Invalid industry type selected. Please choose 1, 2, or 3.");
        };
    }

    private int requestCoordinate(String label) {
        System.out.print(label + ": ");
        return input.nextInt();
    }

    private String requestMap() {
        System.out.print("Map name: ");
        return input.next();  // Solicita o nome do mapa
    }

    private void showSuccess(Industry industry) {
        System.out.printf("\n✅ Industry %s created in (%d,%d)\n", industry.getType(), industry.getPositionX(), industry.getPositionY());
    }

    private void showError(String message) {
        System.out.println("\n❌ " + message);
    }

    private Map getMapByName(String mapName) {
        // Usa o método getOrganizationFromSession para buscar a organização associada ao funcionário logado
        Optional<Organization> organization = getOrganizationFromSession();
        if (organization.isPresent()) {
            return organization.get().getMaps().stream()
                    .filter(map -> map.getName().equals(mapName))
                    .findFirst()
                    .orElse(null);
        }
        return null;
    }

    private Optional<Organization> getOrganizationFromSession() {
        // Aqui, o loggedInEmployee já foi passado para a classe AddIndustryUI e está acessível.
        return Optional.ofNullable(loggedInEmployee != null ? loggedInEmployee.getOrganization() : null);
    }
}
