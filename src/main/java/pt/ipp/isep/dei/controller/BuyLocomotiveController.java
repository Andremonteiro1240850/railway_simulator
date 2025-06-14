package pt.ipp.isep.dei.controller;

import java.util.List;

import pt.ipp.isep.dei.domain.Locomotive;
import pt.ipp.isep.dei.domain.Scenario;
import pt.ipp.isep.dei.domain.enums.ListLocomotives;
import pt.ipp.isep.dei.domain.enums.LocomotiveType;
import pt.ipp.isep.dei.repository.LocomotiveRepository;

/**
 * Controller responsible for displaying available locomotives and processing purchases.
 */
public class BuyLocomotiveController {

    private final LocomotiveRepository locomotiveRepository;

    /**
     * Default constructor. Initializes the locomotive repository.
     */
    public BuyLocomotiveController() {
        this.locomotiveRepository = new LocomotiveRepository();
    }

    /**
     * Displays all locomotives available for purchase in the given scenario.
     * The availability is based on the current year and the allowed types defined in the scenario.
     *
     * @param scenario the current scenario context
     */
    public void showAvailableLocomotives(Scenario scenario) {
        List<LocomotiveType> allowedTypes = scenario.getAvailableLocomotiveTypes();
        int currentYear = scenario.getCurrentYear();

        System.out.println("\nAvailable locomotives for scenario '" + scenario.getName() + "':\n");

        for (ListLocomotives locoEnum : ListLocomotives.values()) {
            if (allowedTypes.contains(locoEnum.getLocomotiveType())) {
                if (locoEnum.getAvailableYear() >= currentYear && locoEnum.getRetiredYear() <= currentYear) {
                    Locomotive loco = locoEnum.toLocomotive();
                    System.out.println(loco); // Uses Locomotive's toString() method
                }
            }
        }
    }

    /**
     * Attempts to purchase a locomotive by name and update the scenario budget accordingly.
     * It checks if the locomotive is already purchased and whether the scenario has sufficient budget.
     *
     * @param name     the name of the locomotive to purchase
     * @param scenario the current scenario context
     */
    public void buyLocomotive(String name, Scenario scenario) {
        List<Locomotive> purchased = scenario.getLocomotivePurchased();

        Locomotive loco = null;
        for (ListLocomotives locoEnum : ListLocomotives.values()) {
            if (locoEnum.getName().equalsIgnoreCase(name)) {
                loco = locoEnum.toLocomotive();
                break;
            }
        }

        // Check if the locomotive was already purchased
        for (Locomotive l : purchased) {
            if (l.getReference().equalsIgnoreCase(loco.getReference())) {
                System.out.println("You have already purchased this locomotive.");
                break;
            }
        }

        // Check if there's enough budget
        double price = loco.getPurchasePrice();
        if (scenario.getBudget() < price) {
            System.out.println("You can't buy this locomotive because you don't have enough budget in this scenario.");
            return;
        } else {
            double newBudget = scenario.getBudget() - price;
            scenario.setBudget(newBudget);
            System.out.println("Purchase completed successfully!");
        }
    }
}


