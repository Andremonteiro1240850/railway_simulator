package pt.ipp.isep.dei.domain;
/**
 * Represents an upgrade that can be applied to a station, consisting of a type
 * and optional subtype. This class is immutable.
 */


public class StationUpgrade {
    private final String type;
    private final String subType;
    /**
     * Constructs a new StationUpgrade with the specified type and subtype.
     *
     * @param type The main type of the upgrade (cannot be null)
     * @param subType The specific subtype of the upgrade (can be null)
     * @throws IllegalArgumentException if type is null
     */

    public StationUpgrade(String type, String subType) {
        this.type = type;
        this.subType = subType;
    }
    /**
     * Gets the main type of this upgrade.
     *
     * @return The upgrade type (never null)
     */

    // Getters
    public String getType() { return type; }
    /**
     * Gets the subtype of this upgrade.
     *
     * @return The upgrade subtype (may be null if not specified)
     */
    public String getSubType() { return subType; }
    /**
     * Returns a string representation of this upgrade in the format:
     * "type" or "type (subType)" if a subtype exists.
     *
     * @return A formatted string representing this upgrade
     */

    @Override
    public String toString() {
        return type + (subType != null ? " (" + subType + ")" : "");
    }
}