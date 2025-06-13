package pt.ipp.isep.dei.domain;

import pt.ipp.isep.dei.domain.enums.StationTypes;

public class RegularStationType extends StationType {
    public RegularStationType() {
        super(StationTypes.STATION);
    }

    @Override
    public int getMaintenanceCost() {
        return 10000;
    }
}
