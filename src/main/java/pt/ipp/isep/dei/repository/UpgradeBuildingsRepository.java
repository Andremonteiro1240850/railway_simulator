package pt.ipp.isep.dei.repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Repository that manages building upgrades and their optional subtypes.
 * Provides validation and access to supported upgrade types and variations.
 */
public class UpgradeBuildingsRepository {
    private final List<String> upgradeTypes;
    private final List<String> subTypes; // Para upgrades que têm variações (small/large)

    /**
     * Default constructor. Initializes upgrade types and subtypes.
     */
    public UpgradeBuildingsRepository() {
        this.upgradeTypes = new ArrayList<>();
        this.subTypes = new ArrayList<>();
        initializeUpgradeTypes();
        initializeSubTypes();
    }

    /**
     * Initializes the list of available upgrade types.
     */
    private void initializeUpgradeTypes() {
        upgradeTypes.add("telegraph");
        upgradeTypes.add("telephone");
        upgradeTypes.add("cafe");
        upgradeTypes.add("customs");
        upgradeTypes.add("post office");
        upgradeTypes.add("hotel");
        upgradeTypes.add("silo");
        upgradeTypes.add("liquid storage");
    }

    /**
     * Initializes the list of available subtypes (e.g., small, large).
     */
    private void initializeSubTypes() {
        subTypes.add("small");
        subTypes.add("large");
    }

    /**
     * Returns a list of all available upgrade types.
     *
     * @return list of upgrade types
     */
    public List<String> getAllUpgradeTypes() {
        return new ArrayList<>(upgradeTypes);
    }

    /**
     * Returns a list of all available upgrade subtypes.
     *
     * @return list of subtypes
     */
    public List<String> getAllSubTypes() {
        return new ArrayList<>(subTypes);
    }

    /**
     * Checks if the given type is a valid upgrade type.
     *
     * @param type the type to check
     * @return true if valid, false otherwise
     */
    public boolean isValidUpgradeType(String type) {
        return upgradeTypes.contains(type.toLowerCase());
    }

    /**
     * Checks if the given subtype is valid (only applies to certain upgrades).
     *
     * @param subType the subtype to check
     * @return true if valid, false otherwise
     */
    public boolean isValidSubType(String subType) {
        return subTypes.contains(subType.toLowerCase());
    }

    /**
     * Determines whether a specific upgrade type supports subtypes.
     *
     * @param upgradeType the upgrade type
     * @return true if the upgrade type accepts subtypes, false otherwise
     */
    public boolean acceptsSubTypes(String upgradeType) {
        return upgradeType.equalsIgnoreCase("cafe") ||
                upgradeType.equalsIgnoreCase("hotel");
    }
}
