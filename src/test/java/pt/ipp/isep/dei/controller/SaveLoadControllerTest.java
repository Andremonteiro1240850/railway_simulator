package pt.ipp.isep.dei.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.domain.Map;
import pt.ipp.isep.dei.domain.Organization;
import pt.ipp.isep.dei.domain.Scenario;
import pt.ipp.isep.dei.domain.enums.ScaleUnit;
import pt.ipp.isep.dei.repository.*;

import static org.junit.jupiter.api.Assertions.*;

class SaveLoadControllerTest {
    private static final String TEST_MAP_NAME = "test_map";
    private static final String TEST_SCENARIO_NAME = "test_scenario";
    private SaveLoadController controller;
    private CreateMapRepository mapRepo;
    private ScenarioRepository scenarioRepo;
    private Map testMap;
    private Scenario testScenario;

    @BeforeEach
    void setUp() {
        // Initialize repositories
        mapRepo = new CreateMapRepository();
        scenarioRepo = new ScenarioRepository();
        controller = new SaveLoadController(mapRepo, scenarioRepo);

        // Initialize other repositories
        CityRepository cityRepository = new CityRepository();
        IndustryRepository industryRepository = new IndustryRepository();
        StationRepository stationRepository = new StationRepository();

        // Create organization with repositories
        Organization organization = new Organization("Test Organization", mapRepo, cityRepository, industryRepository, stationRepository);
        
        // Create test map
        testMap = new Map("Test Map", 10, 10, ScaleUnit.KM, organization);

        // Create test scenario
        testScenario = new Scenario("Test Scenario", 1900, 1950, null, null, null, 700000.0, testMap);
    }

    @Test
    void saveAndLoadMap() {
        // Save map
        assertTrue(controller.saveMap(testMap, TEST_MAP_NAME));

        // Load map
        assertTrue(controller.loadMap(TEST_MAP_NAME));

        // Verify map was added to repository
        assertTrue(mapRepo.getAll().stream()
                .anyMatch(m -> m.getName().equals(testMap.getName())));
    }

    @Test
    void saveAndLoadScenario() {
        // Save scenario
        assertTrue(controller.saveScenario(testScenario, TEST_SCENARIO_NAME));

        // Load scenario
        assertTrue(controller.loadScenario(TEST_SCENARIO_NAME));

        // Verify scenario was added to repository
        assertTrue(scenarioRepo.getAll().stream()
                .anyMatch(s -> s.getName().equals(testScenario.getName())));
    }

    @Test
    void getSavedMaps() {
        // Save a map
        controller.saveMap(testMap, TEST_MAP_NAME);

        // Get saved maps
        String[] savedMaps = controller.getSavedMaps();
        assertTrue(savedMaps.length > 0);
        assertTrue(contains(savedMaps, TEST_MAP_NAME));
    }

    @Test
    void getSavedScenarios() {
        // Save a scenario
        controller.saveScenario(testScenario, TEST_SCENARIO_NAME);

        // Get saved scenarios
        String[] savedScenarios = controller.getSavedScenarios();
        assertTrue(savedScenarios.length > 0);
        assertTrue(contains(savedScenarios, TEST_SCENARIO_NAME));
    }

    private boolean contains(String[] array, String value) {
        for (String item : array) {
            if (item.equals(value)) {
                return true;
            }
        }
        return false;
    }
} 