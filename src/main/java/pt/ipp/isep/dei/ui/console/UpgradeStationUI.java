package pt.ipp.isep.dei.ui.console;

import pt.ipp.isep.dei.controller.UpgradeStationController;
import pt.ipp.isep.dei.domain.Station;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class UpgradeStationUI implements Runnable {
    private final UpgradeStationController controller;
    private final Scanner scanner;

    public UpgradeStationUI() {
        this.controller = new UpgradeStationController();
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        System.out.println("\n--- Station Upgrade ---");

        // 1. Listar todas as estações
        List<Station> stations = controller.getAllStations();
        if (stations.isEmpty()) {
            System.out.println("No stations available for upgrade.");
            return;
        }

        System.out.println("\nAvailable Stations:");
        for (int i = 0; i < stations.size(); i++) {
            System.out.printf("%d. %s (Type: %s)%n",
                    i+1,
                    stations.get(i).getReference(),
                    stations.get(i).getType());
        }

        // 2. Selecionar estação
        System.out.print("Select station to upgrade (0 to cancel): ");
        int stationChoice = scanner.nextInt();
        scanner.nextLine(); // Limpar buffer

        if (stationChoice < 1 || stationChoice > stations.size()) {
            return;
        }
        Station selectedStation = stations.get(stationChoice - 1);

        // 3. Selecionar tipo de upgrade
        List<String> upgradeTypes = controller.getAvailableUpgradeTypes();
        System.out.println("\nAvailable Upgrade Types:");
        for (int i = 0; i < upgradeTypes.size(); i++) {
            System.out.printf("%d. %s%n", i+1, upgradeTypes.get(i));
        }

        System.out.print("Select upgrade type: ");
        int upgradeChoice = scanner.nextInt();
        scanner.nextLine();

        String upgradeType = upgradeTypes.get(upgradeChoice - 1);

        // 4. Selecionar subtipo (se aplicável)
        String subType = null;
        if (controller.getAvailableSubTypes(upgradeType).size() > 0) {
            List<String> subTypes = controller.getAvailableSubTypes(upgradeType);
            System.out.println("\nAvailable Subtypes:");
            for (int i = 0; i < subTypes.size(); i++) {
                System.out.printf("%d. %s%n", i+1, subTypes.get(i));
            }

            System.out.print("Select subtype: ");
            int subTypeChoice = scanner.nextInt();
            scanner.nextLine();
            subType = subTypes.get(subTypeChoice - 1);
        }

        // 5. Aplicar upgrade
        Optional<Station> result = controller.upgradeStation(
                selectedStation.getReference(),
                upgradeType,
                subType
        );

        if (result.isPresent()) {
            System.out.println("\nUpgrade applied successfully!");
            System.out.println("Station now has upgrades: " + result.get().getUpgrades());
        } else {
            System.out.println("\nFailed to apply upgrade!");
        }
    }
}