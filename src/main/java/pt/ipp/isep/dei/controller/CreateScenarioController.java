package pt.ipp.isep.dei.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Controller that coordinates the creation of a scenario.
 */
public class CreateScenarioController {
    private final ScenarioRepository repository;
    private final Map currentMap;

    /**
     * Constructs the controller with current map and scenario repository.
     * @param currentMap map selected by the editor
     * @param repository repository to save the scenario
     */
    public CreateScenarioController(Map currentMap, ScenarioRepository repository) {
        this.currentMap = currentMap;
        this.repository = repository;
    }

    /**
     * Creates and saves a scenario based on the given inputs.
     * @param name name of the scenario
     * @param transportOption transport system selected by user
     * @param portOption port behavior selected by user
     * @param industryRate industry generation rate (per turn)
     * @return true if scenario created successfully, false otherwise
     */
    public boolean createScenario(String name, int transportOption, int portOption, int industryRate) {
        if (name == null || name.isBlank() || currentMap == null || industryRate < 0) return false;

        TransportSystem transportSystem = new TransportSystem();
        PortRepository portRepo = new PortRepository();

        boolean transportValid = transportSystem.select(transportOption);
        boolean portValid = portRepo.select(portOption);

        if (!transportValid || !portValid) return false;

        Scenario scenario = new Scenario(
                name,
                currentMap,
                transportSystem.getSelectedType(),
                portRepo.getSelectedBehavior(),
                industryRate
        );

        repository.save(scenario);
        return true;
    }
}