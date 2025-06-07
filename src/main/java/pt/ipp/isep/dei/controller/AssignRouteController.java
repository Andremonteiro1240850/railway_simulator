package pt.ipp.isep.dei.controller;

import pt.ipp.isep.dei.domain.Locomotive;
import pt.ipp.isep.dei.domain.Route;
import pt.ipp.isep.dei.domain.Station;
import pt.ipp.isep.dei.repository.*;

import java.util.List;
import java.util.Optional;

public class AssignRouteController {
    private final StationRepository stationRepository;
    private final LocomotiveRepository locomotiveRepository;
    private final CargoRepository cargoRepository;

    public AssignRouteController() {
        Repositories repositories = Repositories.getInstance();
        this.stationRepository = repositories.getStationRepository();
        this.locomotiveRepository = repositories.getLocomotiveRepository();
        this.cargoRepository = repositories.getCargoRepository();
    }

    // Para testes
    public AssignRouteController(StationRepository stationRepository,
                                 LocomotiveRepository locomotiveRepository,
                                 CargoRepository cargoRepository) {
        this.stationRepository = stationRepository;
        this.locomotiveRepository = locomotiveRepository;
        this.cargoRepository = cargoRepository;
    }


    }
