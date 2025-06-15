package pt.ipp.isep.dei.domain;

import pt.ipp.isep.dei.domain.enums.StationTypes;

/**
 * Abstract base class for different types of stations
 */
public abstract class TypeStation {
    protected final StationTypes type;

    protected TypeStation(StationTypes type) {
        this.type = type;
    }

    public StationTypes getType() {
        return type;
    }

    @Override
    public String toString() {
        return type.toString();
    }

    /**
     * Factory method to create a TypeStation instance from a StationTypes
     * @param type the StationTypes enum value
     * @return a new instance of the appropriate TypeStation subclass
     */
    public static TypeStation fromStationType(StationTypes type) {
        return switch (type) {
            case DEPOT -> new TypeStationDepot();
            case STATION -> new TypeStationRegular();
            case TERMINAL -> new TypeStationTerminal();
        };
    }

    /**
     * Gets the maintenance cost associated with this station type.
     * @return the maintenance cost
     */
    public abstract int getMaintenanceCost();

    /**
     * Gets the proximity radius for this station type.
     * @return the proximity radius
     */
    public abstract int getProximityRadius();
} 