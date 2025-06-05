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

    @Override
    public String toString() {
        return String.format(
                "Station: %s | Cargo Mode: %s (%s)",
                station.getName(), // ou station.toString() se tiveres um bom toString em Station
                cargoMode.name(),
                cargoMode.getDescription()
        );
    }


}
