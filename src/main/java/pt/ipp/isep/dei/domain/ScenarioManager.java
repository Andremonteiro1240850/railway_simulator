package pt.ipp.isep.dei.domain;

import pt.ipp.isep.dei.domain.Scenario;
import pt.ipp.isep.dei.repository.ScenarioRepository;

import java.util.Optional;
/**
 * Manages scenarios in the application, including setting and retrieving the current scenario
 * and its properties.
 */

public class ScenarioManager {
    private final ScenarioRepository scenarioRepo;
    private Scenario currentScenario;
    /**
     * Constructs a ScenarioManager with the specified ScenarioRepository.
     *
     * @param scenarioRepo The repository used to access stored scenarios.
     */


    public ScenarioManager(ScenarioRepository scenarioRepo) {
        this.scenarioRepo = scenarioRepo;
    }
    /**
     * Sets the current scenario by name.
     *
     * @param scenarioName The name of the scenario to set as current.
     * @return true if the scenario was found and set, false otherwise.
     */

    public boolean setCurrentScenario(String scenarioName) {
        Optional<Scenario> scenario = scenarioRepo.findByName(scenarioName);
        scenario.ifPresent(s -> this.currentScenario = s);
        return scenario.isPresent();
    }
    /**
     * Gets the current scenario if one is set.
     *
     * @return An Optional containing the current scenario if present, empty Optional otherwise.
     */

    public Optional<Scenario> getCurrentScenario() {
        return Optional.ofNullable(currentScenario);
    }
    /**
     * Gets the current year of the active scenario.
     *
     * @return The current year of the active scenario, or -1 if no scenario is set.
     */

    public int getCurrentYear() {
        return currentScenario != null ? currentScenario.getCurrentYear() : -1;
    }
}