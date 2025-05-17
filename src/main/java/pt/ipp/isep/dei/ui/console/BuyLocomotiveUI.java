package pt.ipp.isep.dei.ui.console;

import pt.ipp.isep.dei.controller.BuyLocomotiveController;
import pt.ipp.isep.dei.domain.Locomotive;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class BuyLocomotiveUI implements Runnable {
    private final BuyLocomotiveController controller;
    private final Scanner scanner;

    public BuyLocomotiveUI() {
        this.controller = new BuyLocomotiveController();
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        System.out.println("\n=== Buy Locomotive ===");
        listAvailableLocomotives();
        buyLocomotiveProcess();
    }

    private void listAvailableLocomotives() {
        List<Locomotive> locomotives = controller.getAvailableLocomotives();

        if (locomotives.isEmpty()) {
            System.out.println("\nNo locomotives available for purchase.");
            return;
        }

        System.out.println("\nAvailable Locomotives:");
        System.out.println("Ref.    Type      Power  Price    Year");
        System.out.println("-------------------------------------");

        for (Locomotive loco : locomotives) {
            System.out.printf("%-6s  %-8s  %4dkW  %6.2f  %4d%n",
                    loco.getReference(),
                    loco.getType(),
                    loco.getPower(),
                    loco.getPrice(),
                    loco.getStartYear());
        }
    }

    private void buyLocomotiveProcess() {
        System.out.print("\nEnter locomotive reference to buy (or '0' to cancel): ");
        String ref = scanner.nextLine().trim();

        if (ref.equals("0")) {
            System.out.println("Operation canceled.");
            return;
        }

        Optional<Locomotive> purchased = controller.buyLocomotive(ref);

        if (purchased.isPresent()) {
            System.out.printf("%nSuccessfully purchased locomotive:%n%s%n", purchased.get());
        } else {
            System.out.println("\nFailed to purchase locomotive. Invalid reference or insufficient funds.");
        }
    }
}