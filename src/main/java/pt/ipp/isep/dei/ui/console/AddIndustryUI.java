package pt.ipp.isep.dei.ui.console;

import pt.ipp.isep.dei.controller.AddIndustryController;
import pt.ipp.isep.dei.domain.Industry;
import pt.ipp.isep.dei.domain.Map;
import pt.ipp.isep.dei.domain.TaskCategory;
import pt.ipp.isep.dei.repository.CreateMapRepository;
import pt.ipp.isep.dei.repository.Repositories;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class AddIndustryUI implements Runnable {
    private final AddIndustryController controller;
    private int x;
    private int y;
    private Map map;

    public AddIndustryUI() {
        controller = new AddIndustryController();
    }

    private AddIndustryController getController() {
        return controller;
    }

    public void run() {
        System.out.println("\n\n--- Add Industry ------------------------");

        requestData();
        submitData();
    }

    private void submitData() {
        Optional<Industry> task = getController().createIndustry(x,y,map);

        if (task.isPresent()) {
            System.out.println("\nIndustry successfully created!");
        } else {
            System.out.println("\nIndustry not created!");
        }
    }

    private void requestData() {
        //Request the Task Reference from the console
        x = requestX();
        y = requestY();
        map= requestMap();
    }

    private int requestX() {
        Scanner input = new Scanner(System.in);
        System.out.print("X: ");
        return input.nextInt();
    }
    private int requestY() {
        Scanner input = new Scanner(System.in);
        System.out.print("Y: ");
        return input.nextInt();
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

        return availableMaps.get(selectedIndex - 1).mapClone();
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
