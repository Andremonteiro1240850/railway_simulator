package pt.ipp.isep.dei.controller;

import pt.ipp.isep.dei.domain.Station;
import pt.ipp.isep.dei.repository.Repositories;
import pt.ipp.isep.dei.repository.StationRepository;
import java.util.List;

public class ListStationController {
    private final StationRepository stationRepository;

    public ListStationController() {
        this.stationRepository = Repositories.getInstance().getStationRepository();
    }

    public List<Station> getAllStations() {
        return stationRepository.getAll(); // Returns raw data for UI to format
    }
}