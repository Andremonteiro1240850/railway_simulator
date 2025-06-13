package pt.ipp.isep.dei.domain;

import pt.ipp.isep.dei.domain.enums.CargoMode;

/**
 * Represents a point in a train's route, containing a station and the cargo mode used at that station.
 */
public class PointsOfRoute {

    private Station station;
    private CargoMode cargoMode;

    /**
     * Constructs a PointsOfRoute with a specified station and cargo mode.
     *
     * @param station   the station in the route
     * @param cargoMode the cargo loading mode at this station
     */
    public PointsOfRoute(Station station, CargoMode cargoMode) {
        this.station = station;
        this.cargoMode = cargoMode;
    }

    /**
     * Returns the station associated with this route point.
     *
     * @return the station
     */
    public Station getStations() {
        return station;
    }

    /**
     * Sets the station for this point of route.
     *
     * @param station the station to set
     */
    public void setStation(Station station) {
        this.station = station;
    }

    /**
     * Returns the cargo mode for this route point.
     *
     * @return the cargo mode
     */
    public CargoMode getCargoMode() {
        return cargoMode;
    }

    /**
     * Sets the cargo mode for this route point.
     *
     * @param cargoMode the cargo mode to set
     */
    public void setCargoMode(CargoMode cargoMode) {
        this.cargoMode = cargoMode;
    }

    /**
     * Returns a string representation of this point of route.
     *
     * @return a string including station name and cargo mode details
     */
    @Override
    public String toString() {
        return String.format(
                "Station: %s | Cargo Mode: %s (%s)",
                station.getName(),
                cargoMode.name(),
                cargoMode.getDescription()
        );
    }
}
