package pt.ipp.isep.dei.controller;

import pt.ipp.isep.dei.domain.*;
import pt.ipp.isep.dei.domain.enums.CargoMode;
import pt.ipp.isep.dei.repository.*;

import java.util.List;
import java.util.Optional;


    public class AssignRouteController {
        private final ScenarioRepository scenarioRepository;

        public AssignRouteController() {
            this.scenarioRepository = Repositories.getInstance().getScenarioRepository();
        }

        public List<Scenario> getAvailableScenarios() {
            return scenarioRepository.getAll();
        }

        public PointsOfRoute createPointOfRoute(Station station, CargoMode mode) {
            if (station == null) {
                throw new NullPointerException("Station cannot be null");
            }
            if (mode == null) {
                throw new NullPointerException("Cargo mode cannot be null");
            }
            return new PointsOfRoute(station, mode);
        }

        public void assignRouteToTrain(Locomotive locomotive, List<Carriage> carriages, List<PointsOfRoute> points, Scenario scenario) {
            if (locomotive == null) {
                throw new NullPointerException("Locomotive cannot be null");
            }
            if (carriages == null) {
                throw new NullPointerException("Carriages list cannot be null");
            }
            if (points == null) {
                throw new NullPointerException("Route points list cannot be null");
            }
            if (scenario == null) {
                throw new NullPointerException("Scenario cannot be null");
            }
            
            Train train = new Train(locomotive, carriages);
            Route route = new Route(train, points);
            scenario.addRoute(route);
        }



    }

