package pt.ipp.isep.dei.domain;

import pt.ipp.isep.dei.domain.enums.StationTypes;

public class TerminalStationType extends StationType {
    public TerminalStationType() {
        super(StationTypes.TERMINAL);
    }

    @Override
    public int getMaintenanceCost() {
        return 20000;
    }
}
