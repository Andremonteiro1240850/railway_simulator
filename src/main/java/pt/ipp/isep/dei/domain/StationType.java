package pt.ipp.isep.dei.domain;

import pt.ipp.isep.dei.domain.enums.StationTypes;

/**
 * Abstract class representing a type of station.
 * Each specific type must define its own maintenance cost.
 */
public abstract class StationType {

    /**
     * The enumeration representing the station type.
     */
    protected StationTypes type;

    /**
     * Constructs a StationType with the specified type.
     *
     * @param type the station type enum
     */
    public StationType(StationTypes type) {
        this.type = type;
    }

    /**
     * Gets the maintenance cost associated with this station type.
     *
     * @return the maintenance cost
     */
    public abstract int getMaintenanceCost();

    /**
     * Returns the enum representing this station type.
     *
     * @return the StationTypes enum
     */
    public StationTypes getEnumType() {
        return type;
    }
}