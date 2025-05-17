package pt.ipp.isep.dei.repository;

import pt.ipp.isep.dei.domain.Scenario;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ScenarioRepository {
    private final List<Scenario> scenarios = new ArrayList<>();

    public Optional<Scenario> add(Scenario scenario) {
        if (scenario == null) return Optional.empty();

        Scenario clone = scenario.clone();
        scenarios.add(clone);
        return Optional.of(clone);
    }

    public List<Scenario> getAll() {
        List<Scenario> result = new ArrayList<>();
        for (Scenario scenario : scenarios) {
            result.add(scenario.clone());
        }
        return result;
    }

    public Optional<Scenario> findByName(String name) {
        return scenarios.stream()
                .filter(s -> s.getName().equals(name))
                .findFirst()
                .map(Scenario::clone);
    }
}
