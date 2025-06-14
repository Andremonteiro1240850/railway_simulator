package pt.ipp.isep.dei.controller;

import pt.ipp.isep.dei.domain.Station;
import pt.ipp.isep.dei.repository.Repositories;
import pt.ipp.isep.dei.repository.StationRepository;
import java.util.List;

/**
 * Controller responsible for retrieving and exposing a list of all stations.
 * This controller provides functionality to access and manage station information.
 */
public class ListStationController {

    private final StationRepository stationRepository;

    /**
     * Default constructor that initializes the station repository from the shared Repositories instance.
     */
    public ListStationController() {
        this.stationRepository = Repositories.getInstance().getStationRepository();
    }

    /**
     * Constructor for testing purposes that allows dependency injection.
     *
     * @param stationRepository the station repository to use
     */
    public ListStationController(StationRepository stationRepository) {
        this.stationRepository = stationRepository;
    }

    /**
     * Retrieves all stations from the repository.
     *
     * @return list of all stations
     */
    public List<Station> getAllStations() {
        return stationRepository.getAll(); // Returns raw data for UI to format
    }
}
