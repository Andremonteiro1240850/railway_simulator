package pt.ipp.isep.dei.domain;

import pt.ipp.isep.dei.domain.enums.Buildings;

/**
 * Represents an upgrade that can be applied to a station, consisting of a type
 * and optional subtype. This class is immutable.
 */
public class StationUpgrade {
    private final String type;
    private final String subType;
    private final Buildings building;

    /**
     * Constructs a new StationUpgrade with the specified type and subtype.
     *
     * @param type The main type of the upgrade (cannot be null)
     * @param subType The specific subtype of the upgrade (can be null)
     * @throws IllegalArgumentException if the type is invalid (not in Buildings enum)
     */
    public StationUpgrade(String type, String subType) {
        if (type == null) throw new IllegalArgumentException("Type cannot be null");

        Buildings matchedBuilding = Buildings.from(type, subType);
        if (matchedBuilding == null) {
            throw new IllegalArgumentException("Invalid upgrade type or subtype: " + type + (subType != null ? " (" + subType + ")" : ""));
        }

        this.type = matchedBuilding.getType();
        this.subType = matchedBuilding.getSubType();
        this.building = matchedBuilding;
    }

    // Getters
    public String getType() {
        return type;
    }

    public String getSubType() {
        return subType;
    }

    public int getPrice() {
        return building.getPrice();
    }

    public String getBonus() {
        return building.getBonus();
    }

    public Buildings getBuilding() {
        return building;
    }

    @Override
    public String toString() {
        return type + (subType != null ? " (" + subType + ")" : "");
    }
}
