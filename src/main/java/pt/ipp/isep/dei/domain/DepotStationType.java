package pt.ipp.isep.dei.domain;

import pt.ipp.isep.dei.domain.enums.StationTypes;

/**
 * Represents the Depot type of station in the simulation.
 * A depot has a specific type and a fixed maintenance cost.
 */
public class DepotStationType extends StationType {

    /**
     * Constructs a DepotStationType by setting its type
     * as {@code StationTypes.DEPOT}.
     */
    public DepotStationType() {
        super(StationTypes.DEPOT);
    }

    /**
     * Returns the annual maintenance cost for a depot station.
     *
     * @return the maintenance cost (5000)
     */
    @Override
    public int getMaintenanceCost() {
        return 5000;
    }


}

