package pt.ipp.isep.dei.controller;

import pt.ipp.isep.dei.domain.*;
import pt.ipp.isep.dei.domain.enums.ListLocomotives;
import pt.ipp.isep.dei.domain.enums.LocomotiveType;
import pt.ipp.isep.dei.repository.*;

import java.util.List;

public class BuyLocomotiveController {


    private final LocomotiveRepository locomotiveRepository;

    public BuyLocomotiveController() {
        this.locomotiveRepository = new LocomotiveRepository();
    }

    public void showAvailableLocomotives(Scenario scenario) {

        List<LocomotiveType> allowedTypes = scenario.getAvailableLocomotiveTypes();
        int startYear = scenario.getStartYear();
        int endYear = scenario.getEndYear();

        System.out.println("\nAvailable locomotives for scenario '" + scenario.getName() + "':\n");

        for (ListLocomotives locoEnum : ListLocomotives.values()) {
            if (allowedTypes.contains(locoEnum.getLocomotiveType())) {
                if (locoEnum.getAvailableYear() >= startYear && locoEnum.getRetiredYear() <= endYear) {
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
            if (l.getName().equalsIgnoreCase(loco.getName())) {
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

