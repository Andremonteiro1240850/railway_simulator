package pt.ipp.isep.dei.domain;

import pt.ipp.isep.dei.domain.Scenario;
import pt.ipp.isep.dei.repository.ScenarioRepository;

import java.util.Optional;

public class ScenarioManager {
    private final ScenarioRepository scenarioRepo;
    private Scenario currentScenario;


    public ScenarioManager(ScenarioRepository scenarioRepo) {
        this.scenarioRepo = scenarioRepo;
    }

    public boolean setCurrentScenario(String scenarioName) {
        Optional<Scenario> scenario = scenarioRepo.findByName(scenarioName);
        scenario.ifPresent(s -> this.currentScenario = s);
        return scenario.isPresent();
    }

    public Optional<Scenario> getCurrentScenario() {
        return Optional.ofNullable(currentScenario);
    }

    public int getCurrentYear() {
        return currentScenario != null ? currentScenario.getCurrentYear() : -1;
    }
}