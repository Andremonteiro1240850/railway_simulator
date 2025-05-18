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

    public List<Station> getAllStations() {
        return stationRepository.getAll();
    }

    public List<Locomotive> getAllLocomotives() {
        return locomotiveRepository.getAll();
    }

    public List<String> getAllCargos() {
        return cargoRepository.getAllCargos();
    }

    public Optional<Route> createRoute(List<Station> stations, Locomotive locomotive, List<String> cargosToTransport) {
        if (stations == null || stations.size() < 2) {
            return Optional.empty();
        }

        if (locomotive == null) {
            return Optional.empty();
        }

        // Verifica se todas as cargas são válidas
        for (String cargo : cargosToTransport) {
            if (!cargoRepository.isValidCargo(cargo)) {
                return Optional.empty();
            }
        }

        // Cria a rota (implementação simplificada)
        Route route = new Route(stations, locomotive, cargosToTransport);
        return Optional.of(route);
    }
}