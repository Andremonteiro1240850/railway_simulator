package pt.ipp.isep.dei.ui.console;

import pt.ipp.isep.dei.controller.BuyLocomotiveController;
import pt.ipp.isep.dei.domain.Locomotive;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class BuyLocomotiveUI implements Runnable {


        private final BuyLocomotiveController controller;
        private final Scanner scanner = new Scanner(System.in);


        public BuyLocomotiveUI() {
            this.controller = controller;
        }

        public void run() {
            boolean continuar;

            do {

            System.out.print("Enter current year: ");
            int year = Integer.parseInt(scanner.nextLine());

           controller.showAvailableLocomotives(year);

            System.out.println("write the name of the locomotive you want to buy");
            String locomotiveName = scanner.nextLine();

            controller.buyLocomotive(locomotiveName);

                System.out.print("Do you want to buy another locomotive? (yes/no): ");
                String resposta = scanner.nextLine().trim().toLowerCase();
                continuar = resposta.equals("yes") || resposta.equals("y");

            } while (continuar);
        }
    }
