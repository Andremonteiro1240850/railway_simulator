package pt.ipp.isep.dei.repository;

import pt.ipp.isep.dei.domain.enums.StationUpgrade;

import java.util.ArrayList;
import java.util.List;

public class UpgradeBuildingsRepository {
    private final List<String> upgradeTypes;
    private final List<String> subTypes; // Para upgrades que têm variações (small/large)

    public UpgradeBuildingsRepository() {
        this.upgradeTypes = new ArrayList<>();
        this.subTypes = new ArrayList<>();
        initializeUpgradeTypes();
        initializeSubTypes();
    }

    private void initializeUpgradeTypes() {
        for (StationUpgrade upgrade : StationUpgrade.values()) {
            upgradeTypes.add(upgrade.getDisplayName());
        }
    }

    private void initializeSubTypes() {
        subTypes.add("small");
        subTypes.add("large");
    }

    // Métodos para obter os tipos de upgrades e subtipos
    public List<String> getAllUpgradeTypes() {
        return new ArrayList<>(upgradeTypes);
    }

    public List<String> getAllSubTypes() {
        return new ArrayList<>(subTypes);
    }

    // Verifica se um tipo de upgrade é válido
    public boolean isValidUpgradeType(String type) {
        return type != null && upgradeTypes.contains(type);
    }

    // Verifica se um subtipo é válido (apenas para upgrades que têm variações)
    public boolean isValidSubType(String subType) {
        return subType != null && subTypes.contains(subType.toLowerCase());
    }

    // Método para verificar quais upgrades aceitam subtipos
    public boolean acceptsSubTypes(String upgradeType) {
        if (upgradeType == null) {
            return false;
        }
        return upgradeType.equals(StationUpgrade.RESTAURANT_SMALL.getDisplayName()) ||
               upgradeType.equals(StationUpgrade.RESTAURANT_LARGE.getDisplayName()) ||
               upgradeType.equals(StationUpgrade.HOTEL_SMALL.getDisplayName()) ||
               upgradeType.equals(StationUpgrade.HOTEL_LARGE.getDisplayName());
    }
}