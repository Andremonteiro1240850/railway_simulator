package pt.ipp.isep.dei.domain;

import pt.ipp.isep.dei.domain.enums.StationTypes;

/**
 * Represents a regular station type in the simulation.
 * This type corresponds to {@code StationTypes.STATION} and has a fixed maintenance cost.
 */
public class RegularStationType extends StationType {

    /**
     * Constructs a RegularStationType by setting its type to {@code StationTypes.STATION}.
     */
    public RegularStationType() {
        super(StationTypes.STATION);
    }

    /**
     * Returns the annual maintenance cost for a regular station.
     *
     * @return the maintenance cost (10,000)
     */
    @Override
    public int getMaintenanceCost() {
        return 10000;
    }
}
