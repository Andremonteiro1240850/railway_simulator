package pt.ipp.isep.dei.controller;

import pt.ipp.isep.dei.domain.*;
import pt.ipp.isep.dei.domain.enums.CargoMode;
import pt.ipp.isep.dei.repository.*;

import java.util.List;

/**
 * Controller responsible for assigning routes to trains within a scenario.
 */
public class AssignRouteController {

    private final ScenarioRepository scenarioRepository;

    /**
     * Constructs the controller and initializes the scenario repository.
     */
    public AssignRouteController() {
        this.scenarioRepository = Repositories.getInstance().getScenarioRepository();
    }

    /**
     * Retrieves all available scenarios from the system.
     *
     * @return A list of available scenarios.
     */
    public List<Scenario> getAvailableScenarios() {
        return scenarioRepository.getAll();
    }

    /**
     * Creates a point of route with the specified station and cargo mode.
     *
     * @param station The station to associate with this route point.
     * @param mode    The cargo mode to be used at the station.
     * @return A {@link PointsOfRoute} object representing the route point.
     */
    public PointsOfRoute createPointOfRoute(Station station, CargoMode mode) {
        return new PointsOfRoute(station, mode);
    }

    /**
     * Assigns a route composed of a train and a sequence of route points to a given scenario.
     *
     * @param locomotive The locomotive to be used in the train.
     * @param carriages  The list of carriages to be attached to the train.
     * @param points     The list of route points (stations and cargo modes).
     * @param scenario   The scenario to which the route will be assigned.
     */
    public void assignRouteToTrain(Locomotive locomotive, List<Carriage> carriages, List<PointsOfRoute> points, Scenario scenario) {
        Train train = new Train(locomotive, carriages);
        Route route = new Route(train, points);
        scenario.addRoute(route);
    }
}

