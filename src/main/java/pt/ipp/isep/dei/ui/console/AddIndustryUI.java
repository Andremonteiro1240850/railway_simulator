package pt.ipp.isep.dei.ui.console;

import pt.ipp.isep.dei.domain.*;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class AddIndustryUI {
    private final AddIndustryController controller;
    private Industry Industry;
    private int locationX;
    private int locationY;
    private final Organization organization;



    public AddIndustryUI(Organization organization) {
        controller = new AddIndustryUI();
        this.organization = organization;
    }

    private AddIndustryController getController() {
        return controller;
    }

    public void run() {
        System.out.println("\n\n--- Add Industry Task ------------------------");

        requestData();
        submitData();
    }

    private void submitData() {
        Optional<City> task = getController().createCity(map,locationX,locationY);

        if (task.isPresent()) {
            System.out.println("\nCity successfully created!");
        } else {
            System.out.println("\nCity not created!");
        }
    }

    private void requestData() {
        //Request the Task Reference from the console
        map = requestMap();
        locationX = requestLocationX();
        locationY = requestLocationY();
    }



    private int requestLocationX() {
        Scanner input = new Scanner(System.in);
        System.out.print("X: ");
        return input.nextInt();
    }
    private int requestLocationY() {
        Scanner input = new Scanner(System.in);
        System.out.print("Y: ");
        return input.nextInt();
    }
    private Map requestMap() {
        List<Map> availableMaps = organization.getMaps();

        if (availableMaps.isEmpty()) {
            System.out.println("No maps available.");
            return null;
        }

        System.out.println("Available Maps:");
        for (int i = 0; i < availableMaps.size(); i++) {
            System.out.println((i + 1) + " - " + availableMaps.get(i));
        }

        Scanner input = new Scanner(System.in);
        int selectedIndex = -1;

        while (selectedIndex < 1 || selectedIndex > availableMaps.size()) {
            System.out.print("Select a map by number (1 to " + availableMaps.size() + "): ");
            if (input.hasNextInt()) {
                selectedIndex = input.nextInt();
            } else {
                input.next(); // limpar inválido
            }
        }

        return availableMaps.get(selectedIndex - 1);
    }

    private void displayTaskCategoryOptions(List<TaskCategory> taskCategories) {
        //display the task categories as a menu with number options to select
        int i = 1;
        for (TaskCategory taskCategory : taskCategories) {
            System.out.println("  " + i + " - " + taskCategory.getDescription());
            i++;
        }
    }
}
