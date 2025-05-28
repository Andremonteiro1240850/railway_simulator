package pt.ipp.isep.dei.ui.console;

import pt.ipp.isep.dei.controller.BuyLocomotiveController;
import pt.ipp.isep.dei.domain.Locomotive;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class BuyLocomotiveUI implements Runnable {


        private final BuyLocomotiveController controller;
        private final Scanner scanner = new Scanner(System.in);

        public BuyLocomotiveUI(BuyLocomotiveController controller) {
            this.controller = controller;
        }

        public void run() {
            System.out.print("Enter current year: ");
            int year = Integer.parseInt(scanner.nextLine());

            List<Locomotive> locomotives = controller.listAvailableLocomotives(year);
            if (locomotives.isEmpty()) {
                System.out.println("No locomotives available for this year.");
                return;
            }

            System.out.println("Available Locomotives:");
            for (int i = 0; i < locomotives.size(); i++) {
                System.out.printf("%d - %s%n", i + 1, locomotives.get(i));
            }

            System.out.print("Choose a locomotive (number): ");
            int choice = Integer.parseInt(scanner.nextLine());

            if (choice < 1 || choice > locomotives.size()) {
                System.out.println("Invalid choice.");
                return;
            }

            Locomotive bought = controller.buyLocomotive(locomotives.get(choice - 1).getName());
            System.out.println("You bought: " + bought);
        }
    }
