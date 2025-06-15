package pt.ipp.isep.dei.domain;

import pt.ipp.isep.dei.domain.enums.StationTypes;

/**
 * Concrete class representing a regular station
 */
public class TypeStationRegular extends TypeStation {
    
    public TypeStationRegular() {
        super(StationTypes.STATION);
    }

    @Override
    public int getMaintenanceCost() {
        return 10000;
    }

    @Override
    public int getProximityRadius() {
        return 3;
    }
} 