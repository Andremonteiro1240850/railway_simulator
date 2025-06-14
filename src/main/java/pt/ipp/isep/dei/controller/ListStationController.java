package pt.ipp.isep.dei.controller;

import pt.ipp.isep.dei.domain.Station;
import pt.ipp.isep.dei.repository.Repositories;
import pt.ipp.isep.dei.repository.StationRepository;
import java.util.List;

/**
 * Controller responsible for retrieving and exposing a list of all stations.
 */
public class ListStationController {

    private final StationRepository stationRepository;

    /**
     * Default constructor. Initializes the station repository from the shared Repositories instance.
     */
    public ListStationController() {
        this.stationRepository = Repositories.getInstance().getStationRepository();
    }

    /**
     * Retrieves all stations stored in the repository.
     *
     * @return a list of all existing stations
     */
    public List<Station> getAllStations() {
        return stationRepository.getAll(); // Returns raw data for UI to format
    }
}
