package pt.ipp.isep.dei.domain;

import pt.ipp.isep.dei.domain.enums.StationTypes;

/**
 * Concrete class representing a terminal station
 */
public class TypeStationTerminal extends TypeStation {
    
    public TypeStationTerminal() {
        super(StationTypes.TERMINAL);
    }

    @Override
    public int getMaintenanceCost() {
        return 20000;
    }

    @Override
    public int getProximityRadius() {
        return 4;
    }
} 