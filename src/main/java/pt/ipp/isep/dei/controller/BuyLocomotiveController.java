package pt.ipp.isep.dei.controller;

import java.util.List;

import pt.ipp.isep.dei.domain.Locomotive;
import pt.ipp.isep.dei.domain.Scenario;
import pt.ipp.isep.dei.domain.enums.ListLocomotives;
import pt.ipp.isep.dei.domain.enums.LocomotiveType;
import pt.ipp.isep.dei.repository.LocomotiveRepository;

public class BuyLocomotiveController {


    private final LocomotiveRepository locomotiveRepository;

    public BuyLocomotiveController() {
        this.locomotiveRepository = new LocomotiveRepository();
    }

    public void showAvailableLocomotives(Scenario scenario) {

        List<LocomotiveType> allowedTypes = scenario.getAvailableLocomotiveTypes();
        int currentYear = scenario.getCurrentYear();


        System.out.println("\nAvailable locomotives for scenario '" + scenario.getName() + "':\n");

        for (ListLocomotives locoEnum : ListLocomotives.values()) {
            if (allowedTypes.contains(locoEnum.getLocomotiveType())) {
                if (locoEnum.getAvailableYear() >= currentYear && locoEnum.getRetiredYear() <= currentYear) {
                    Locomotive loco = locoEnum.toLocomotive();
                    System.out.println(loco);  // Usa o método toString() da classe Locomotive
                }
            }
        }
    }



    public void buyLocomotive(String name, Scenario scenario) {
        List<Locomotive> purchased = scenario.getLocomotivePurchased();

        Locomotive loco = null;
        for (ListLocomotives locoEnum : ListLocomotives.values()) {
            if (locoEnum.getName().equalsIgnoreCase(name)) {
                // Só permite comprar se o tipo for permitido pelo editor
                if (!scenario.getAvailableLocomotiveTypes().contains(locoEnum.getLocomotiveType())) {
                    System.out.println("This type of locomotive is not available in this scenario.");
                    return;
                }
                loco = locoEnum.toLocomotive();
                break;
            }
        }

        if (loco == null) {
            System.out.println("Locomotive not found.");
            return;
        }

        // Verificar se já foi comprada
        for (Locomotive l : purchased) {
            if (l.getReference().equalsIgnoreCase(loco.getReference())) {
                System.out.println("You have already purchased this locomotive.");
                return;
            }
        }

        // Verificar orçamento
        double price = loco.getPurchasePrice();
        if (scenario.getBudget() < price) {
            System.out.println("You can´t buy this locomotive because you don´t have enough budget in this scenario.");
            return;
        }

        double newBudgdet = scenario.getBudget() - price;
        scenario.setBudget(newBudgdet);
        System.out.println("Purchase completed successfully!");
    }
}

