package pt.ipp.isep.dei.domain;

import pt.ipp.isep.dei.domain.enums.StationTypes;

/**
 * Concrete class representing a depot station
 */
public class TypeStationDepot extends TypeStation {
    
    public TypeStationDepot() {
        super(StationTypes.DEPOT);
    }

    @Override
    public int getMaintenanceCost() {
        return 5000;
    }

    @Override
    public int getProximityRadius() {
        return 2;
    }
} 