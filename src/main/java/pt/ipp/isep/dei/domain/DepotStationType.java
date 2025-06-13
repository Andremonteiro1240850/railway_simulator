package pt.ipp.isep.dei.domain;

import pt.ipp.isep.dei.domain.enums.StationTypes;

public class DepotStationType extends StationType {
    public DepotStationType() {
        super(StationTypes.DEPOT);
    }

    @Override
    public int getMaintenanceCost() {
        return 5000;
    }
}
