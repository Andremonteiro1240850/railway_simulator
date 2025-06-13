package pt.ipp.isep.dei.domain;

import pt.ipp.isep.dei.domain.enums.StationTypes;

/**
 * Represents a terminal station type in the simulation.
 * A terminal station typically has a higher maintenance cost compared to other station types.
 */
public class TerminalStationType extends StationType {

    /**
     * Constructs a TerminalStationType by setting its type to {@code StationTypes.TERMINAL}.
     */
    public TerminalStationType() {
        super(StationTypes.TERMINAL);
    }

    /**
     * Returns the annual maintenance cost for a terminal station.
     *
     * @return the maintenance cost (20,000)
     */
    @Override
    public int getMaintenanceCost() {
        return 20000;
    }
}
