// AddCityUI.java
package pt.ipp.isep.dei.ui.console;

import pt.ipp.isep.dei.controller.AddCityController;
import pt.ipp.isep.dei.domain.City;
import pt.ipp.isep.dei.domain.Map;
import pt.ipp.isep.dei.domain.Organization;
import pt.ipp.isep.dei.repository.CreateMapRepository;
import pt.ipp.isep.dei.repository.Repositories;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Random;

public class AddCityUI implements Runnable {
    private final AddCityController controller;
    private Map map;
    private int locationX;
    private int locationY;
    private final Organization organization;

    public AddCityUI(Organization organization) {
        this.controller = new AddCityController();
        this.organization = organization;
    }

    public void run() {
        System.out.println("\n\n--- Add City ------------------------");
        requestData();
        submitData();
    }

    private void submitData() {
        String name = requestCityName();
        List<int[]> houseBlocks = requestHouseBlocks();

        Optional<City> city = controller.createCity(map,name, locationX, locationY, houseBlocks);

        if (city.isPresent()) {
            System.out.println("\nCity successfully created!");
        } else {
            System.out.println("\nCity not created!");
        }
    }

    private void requestData() {
        map = requestMap();
        locationX = requestCoordinate("X");
        locationY = requestCoordinate("Y");

    }

    private int requestCoordinate(String axis) {
        Scanner input = new Scanner(System.in);
        System.out.print(axis + ": ");
        return input.nextInt();
    }

    private String requestCityName() {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter city name (letters only): ");
        String name = input.nextLine();
        while (!name.matches("^[a-zA-Z ]+$")) {
            System.out.println("Invalid name - only letters and spaces allowed");
            System.out.print("Enter city name: ");
            name = input.nextLine();
        }
        return name;
    }

    private List<int[]> requestHouseBlocks() {
        Scanner input = new Scanner(System.in);
        System.out.print("Choose house block mode (manual/automatic): ");
        String mode = input.nextLine().toLowerCase();

        while (!mode.equals("manual") && !mode.equals("automatic")) {
            System.out.println("Invalid mode - please enter 'manual' or 'automatic'");
            System.out.print("Choose house block mode: ");
            mode = input.nextLine().toLowerCase();
        }

        System.out.print("Enter number of house blocks: ");
        int count = input.nextInt();
        while (count <= 0) {
            System.out.println("Number must be positive");
            System.out.print("Enter number of house blocks: ");
            count = input.nextInt();
        }

        if (mode.equals("manual")) {
            return requestManualHouseBlocks(count);
        } else {
            return generateAutomaticHouseBlocks(count);
        }
    }

    private List<int[]> requestManualHouseBlocks(int count) {
        Scanner input = new Scanner(System.in);
        List<int[]> blocks = new ArrayList<>();

        System.out.println("Enter coordinates for each house block:");
        for (int i = 0; i < count; i++) {
            System.out.println("Block " + (i+1) + ":");
            int x = requestCoordinate("X");
            int y = requestCoordinate("Y");
            blocks.add(new int[]{x, y});
            if(map.getObjectsAt(x, y).stream().anyMatch(obj -> obj instanceof City)) {
                System.out.println("❌ Block at (" + x + ", " + y + ") overlaps with an existing city. Please enter a different block.");
                i--; // Decrement to retry this block
            } else if (x < 0 || x >= map.getWidth() || y < 0 || y >= map.getHeight()) {
                System.out.println("❌ Coordinates out of bounds. Please enter valid coordinates.");
                i--; // Decrement to retry this block
            }
        }
        return blocks;
    }

    private List<int[]> generateAutomaticHouseBlocks(int count) {
        List<int[]> blocks = new ArrayList<>();
        Random random = new Random();

        // Generate blocks around city center with normal distribution
        for (int i = 0; i < count; i++) {
            // Using normal distribution around city center (mean=0, stddev=5)
            int x = (int) (locationX + random.nextGaussian() * 5);
            int y = (int) (locationY + random.nextGaussian() * 5);
            blocks.add(new int[]{x, y});
        }
        return blocks;
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
                input.next(); // clear invalid input
            }
        }
        return availableMaps.get(selectedIndex - 1);
    }
}