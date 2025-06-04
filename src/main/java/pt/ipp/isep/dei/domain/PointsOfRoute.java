package pt.ipp.isep.dei.domain;

import pt.ipp.isep.dei.domain.enums.CargoMode;

public class PointsOfRoute {
    private Station station;
    private CargoMode cargoMode;

    public PointsOfRoute(Station stations, CargoMode cargoMode) {
        this.station = stations;
        this.cargoMode = cargoMode;
    }

    public Station getStations() {
        return station;
    }

    public CargoMode getCargoMode() {
        return cargoMode;
    }

    public void setCargoMode(CargoMode cargoMode) {
        this.cargoMode = cargoMode;
    }


}
