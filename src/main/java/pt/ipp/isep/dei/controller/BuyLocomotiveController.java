package pt.ipp.isep.dei.controller;

import pt.ipp.isep.dei.domain.*;
import pt.ipp.isep.dei.domain.enums.ListLocomotives;
import pt.ipp.isep.dei.domain.enums.LocomotiveType;
import pt.ipp.isep.dei.repository.*;
import pt.isep.lei.esoft.auth.domain.model.Email;

import java.util.ArrayList;
import java.util.List;

public class BuyLocomotiveController {

      private final Player player;
      private final LocomotiveRepository locomotiveRepository;

      public BuyLocomotiveController(Player player) {
         this.player = player;
         this.locomotiveRepository = new LocomotiveRepository();
      }

   public void showAvailableLocomotives(int yearCurrrent) {
      Scenario scenario = player.getScenario();

      int currentYear = yearCurrrent;

      // Obter os tipos de locomotiva disponíveis no cenário
      List<LocomotiveType> allowedTypes = scenario.getAvailableLocomotiveTypes();


      System.out.println("Available Locomotives:");

      for (ListLocomotives locoEnum : ListLocomotives.values()) {
         Locomotive loco = locoEnum.toLocomotive();

         if (loco.getStartYear() >= currentYear && allowedTypes.contains(loco.getType())) {
            System.out.println(loco); // usa o toString() da classe Locomotive
         }
         else{
            System.out.println("No Available Locomotives in this scenario");
         }
      }
   }



      /**
       * Tenta comprar uma locomotiva com nome fornecido.
       */
      public boolean buyLocomotive(String locomotiveName) {
            List<Locomotive> purchased = locomotiveRepository.getPurchasedLocomotives();

            // Verificar se já foi comprada
            for (Locomotive loco : purchased) {
               if (loco.getName().equalsIgnoreCase(locomotiveName)) {
                  System.out.println("Erro: Locomotiva já comprada.");
                  return false;
               }
            }

            // Procurar no enum
            Locomotive toBuy = null;
            for (ListLocomotives locoEnum : ListLocomotives.values()) {
               if (locoEnum.name().equalsIgnoreCase(locomotiveName)) {
                  toBuy = locoEnum.toLocomotive();
                  break;
               }
            }

            if (toBuy == null) {
            System.out.println("Erro: Locomotiva não encontrada.");
            return false;
         }

         // Verificar orçamento
         double budget = player.getBudget();
         double cost = toBuy. getPurchasePrice() ;

         if (budget < cost) {
            System.out.println("Erro: Orçamento insuficiente.");
            return false;
         }

         // Realizar compra
         double newBudget= budget - cost;
         player.setBudget(newBudget);

         locomotiveRepository.addPurchasedLocomotive(toBuy);
         System.out.println("Compra realizada: " + toBuy.toString());
         return true;
      }
   }


