package pt.ipp.isep.dei.ui.console;

import pt.ipp.isep.dei.controller.CreateMapController;
import pt.ipp.isep.dei.domain.Map;
import pt.ipp.isep.dei.domain.Organization;
import pt.ipp.isep.dei.domain.TaskCategory;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class CreateMapUI implements Runnable {
    private final CreateMapController controller;
    private String name;
    private int dimensionX;
    private int dimensionY;
    private final Organization organization;


    public CreateMapUI(Organization organization) {
        controller = new CreateMapController();
        this.organization = organization;
    }

    private CreateMapController getController() {
        return controller;
    }

    public void run() {
        System.out.println("\n\n--- Create map ------------------------");

        requestData();
        submitData();
    }

    private void submitData() {
        Optional<Map> task = getController().createMap(name,dimensionX,dimensionY);

        if (task.isPresent()) {
            System.out.println("\nMap successfully created!");
        } else {
            System.out.println("\nMap not created!");
        }
    }

    private void requestData() {
        //Request the Task Reference from the console
        name = requestName();

        //Request the Task Description from the console
        dimensionX = requestDimensionX();

        //Request the Task Informal Description from the console
        dimensionY = requestDimensionY();

    }

    private String requestName() {
        Scanner input = new Scanner(System.in);
        System.out.print("Name: ");
        return input.nextLine();
    }

    private int requestDimensionX() {
        Scanner input = new Scanner(System.in);
        System.out.print("X: ");
        return input.nextInt();
    }
    private int requestDimensionY() {
        Scanner input = new Scanner(System.in);
        System.out.print("Y: ");
        return input.nextInt();
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

