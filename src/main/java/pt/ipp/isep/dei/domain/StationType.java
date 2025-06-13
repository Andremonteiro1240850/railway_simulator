package pt.ipp.isep.dei.domain;

import pt.ipp.isep.dei.domain.enums.StationTypes;

public abstract class StationType {
    protected StationTypes type;

    public StationType(StationTypes type) {
        this.type = type;
    }

    public abstract int getMaintenanceCost();

    public StationTypes getEnumType() {
        return type;
    }
}
