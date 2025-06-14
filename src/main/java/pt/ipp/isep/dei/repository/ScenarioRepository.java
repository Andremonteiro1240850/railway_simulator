package pt.ipp.isep.dei.repository;

import pt.ipp.isep.dei.domain.Scenario;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Repository responsible for storing and accessing {@link Scenario} objects.
 * Ensures cloning to protect internal state from external modifications.
 */
public class ScenarioRepository {

    private final List<Scenario> scenarios = new ArrayList<>();

    /**
     * Adds a scenario to the repository. A defensive clone is stored.
     *
     * @param scenario the scenario to add
     * @return an Optional containing the added clone, or empty if input is null
     */
    public Optional<Scenario> add(Scenario scenario) {
        if (scenario == null) return Optional.empty();

        Scenario clone = scenario.clone();
        scenarios.add(clone);
        return Optional.of(clone);
    }

    /**
     * Retrieves all scenarios stored in the repository as defensive clones.
     *
     * @return a list of cloned scenarios
     */
    public List<Scenario> getAll() {
        List<Scenario> result = new ArrayList<>();
        for (Scenario scenario : scenarios) {
            result.add(scenario.clone());
        }
        return result;
    }

    /**
     * Searches for a scenario by its name.
     *
     * @param name the name of the scenario
     * @return an Optional containing the matched scenario (cloned), or empty if not found
     */
    public Optional<Scenario> findByName(String name) {
        return scenarios.stream()
                .filter(s -> s.getName().equals(name))
                .findFirst()
                .map(Scenario::clone);
    }

    /**
     * Searches for a scenario that contains a station with the specified reference.
     *
     * @param stationReference the reference of the station to match
     * @return an Optional containing the matched scenario (cloned), or empty if not found
     */
    public Optional<Scenario> findByStationReference(String stationReference) {
        return scenarios.stream()
                .filter(s -> s.getStationsPurchased().stream()
                        .anyMatch(st -> st.getReference().equals(stationReference)))
                .findFirst()
                .map(Scenario::clone);
    }
}

