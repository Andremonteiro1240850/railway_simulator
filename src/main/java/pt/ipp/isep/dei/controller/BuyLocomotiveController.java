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
                loco = locoEnum.toLocomotive();
                break;
            }
        }


        // Verificar se já foi comprada
        for (Locomotive l : purchased) {
            if (l.getReference().equalsIgnoreCase(loco.getReference())) {
                System.out.println("You have already purchased this locomotive.");
                break;
            }
        }

        // Verificar orçamento
        double price = loco.getPurchasePrice();
        if (scenario.getBudget() < price) {
            System.out.println("You can´t buy this locomotive because you don´t have enough budget in this scenario.");
            return;
        }

        else{
            double newBudgdet=scenario.getBudget()-price;
            scenario.setBudget(newBudgdet);

            System.out.println("Purchase completed successfully!");

        }


    }
}

