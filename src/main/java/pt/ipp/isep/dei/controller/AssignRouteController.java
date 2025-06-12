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
            return new PointsOfRoute(station, mode);
        }

        public void assignRouteToTrain(Locomotive locomotive, List<Carriage> carriages, List<PointsOfRoute> points,Scenario scenario) {
            Train train = new Train(locomotive, carriages);
            Route route = new Route(train, points);
            scenario.addRoute(route);

        }



    }

