package pt.ipp.isep.dei.controller;

import pt.ipp.isep.dei.domain.*;
import pt.ipp.isep.dei.domain.enums.CargoMode;
import pt.ipp.isep.dei.repository.*;

import java.util.List;
import java.util.Optional;

/**
 * Controller responsible for managing route assignments in the game.
 * This controller handles the creation of routes and their assignment to trains.
 */
public class AssignRouteController {
    private final ScenarioRepository scenarioRepository;

    /**
     * Default constructor that initializes the scenario repository.
     */
    public AssignRouteController() {
        this.scenarioRepository = Repositories.getInstance().getScenarioRepository();
    }

    /**
     * Retrieves all available scenarios.
     *
     * @return list of all available scenarios
     */
    public List<Scenario> getAvailableScenarios() {
        return scenarioRepository.getAll();
    }

    /**
     * Creates a new point of route with the specified station and cargo mode.
     *
     * @param station the station to be included in the route
     * @param mode the cargo mode for this point of route
     * @return a new PointsOfRoute instance
     * @throws NullPointerException if station or mode is null
     */
    public PointsOfRoute createPointOfRoute(Station station, CargoMode mode) {
        if (station == null) {
            throw new NullPointerException("Station cannot be null");
        }
        if (mode == null) {
            throw new NullPointerException("Cargo mode cannot be null");
        }
        return new PointsOfRoute(station, mode);
    }

    /**
     * Assigns a route to a train in a specific scenario.
     *
     * @param locomotive the locomotive to be used in the train
     * @param carriages the list of carriages to be attached to the train
     * @param points the list of route points defining the route
     * @param scenario the scenario where the route will be assigned
     * @throws NullPointerException if any of the parameters is null
     */
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

