package pt.ipp.isep.dei.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Repository to store all created scenarios.
 */
public class ScenarioRepository {
    private final List<Scenario> scenarioList = new ArrayList<>();

    /**
     * Saves a scenario to the repository.
     * @param scenario scenario to be saved
     */
    public void save(Scenario scenario) {
        scenarioList.add(scenario);
    }

    /**
     * Retrieves an unmodifiable list of all saved scenarios.
     * @return list of scenarios
     */
    public List<Scenario> all() {
        return Collections.unmodifiableList(scenarioList);
    }
}