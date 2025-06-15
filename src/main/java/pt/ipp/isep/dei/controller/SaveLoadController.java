package pt.ipp.isep.dei.controller;

import pt.ipp.isep.dei.repository.CreateMapRepository;
import pt.ipp.isep.dei.repository.ScenarioRepository;
import pt.ipp.isep.dei.repository.Repositories;
import pt.ipp.isep.dei.domain.Map;
import pt.ipp.isep.dei.domain.Scenario;
import pt.ipp.isep.dei.utils.FileManager;

/**
 * Controller responsible for handling save and load operations for maps and scenarios.
 */
public class SaveLoadController {
    private final CreateMapRepository mapRepo;
    private final ScenarioRepository scenarioRepo;

    /**
     * Default constructor that initializes required repositories.
     */
    public SaveLoadController() {
        this.mapRepo = Repositories.getInstance().getMapRepository();
        this.scenarioRepo = Repositories.getInstance().getScenarioRepository();
    }

    /**
     * Constructor for testing purposes that allows dependency injection.
     *
     * @param mapRepo the map repository to use
     * @param scenarioRepo the scenario repository to use
     */
    public SaveLoadController(CreateMapRepository mapRepo, ScenarioRepository scenarioRepo) {
        this.mapRepo = mapRepo;
        this.scenarioRepo = scenarioRepo;
    }

    /**
     * Saves the current map to a file.
     *
     * @param map the map to save
     * @param filename the name of the file to save to
     * @return true if the save was successful, false otherwise
     */
    public boolean saveMap(Map map, String filename) {
        return FileManager.saveMap(map, filename);
    }

    /**
     * Loads a map from a file and adds it to the repository.
     *
     * @param filename the name of the file to load from
     * @return true if the load was successful, false otherwise
     */
    public boolean loadMap(String filename) {
        Map loadedMap = FileManager.loadMap(filename);
        if (loadedMap != null) {
            return mapRepo.save(loadedMap).isPresent();
        }
        return false;
    }

    /**
     * Saves the current scenario to a file.
     *
     * @param scenario the scenario to save
     * @param filename the name of the file to save to
     * @return true if the save was successful, false otherwise
     */
    public boolean saveScenario(Scenario scenario, String filename) {
        return FileManager.saveScenario(scenario, filename);
    }

    /**
     * Loads a scenario from a file and adds it to the repository.
     *
     * @param filename the name of the file to load from
     * @return true if the load was successful, false otherwise
     */
    public boolean loadScenario(String filename) {
        Scenario loadedScenario = FileManager.loadScenario(filename);
        if (loadedScenario != null) {
            return scenarioRepo.add(loadedScenario).isPresent();
        }
        return false;
    }

    /**
     * Gets a list of all saved map files.
     *
     * @return array of map filenames
     */
    public String[] getSavedMaps() {
        return FileManager.getSavedMaps();
    }

    /**
     * Gets a list of all saved scenario files.
     *
     * @return array of scenario filenames
     */
    public String[] getSavedScenarios() {
        return FileManager.getSavedScenarios();
    }
} 