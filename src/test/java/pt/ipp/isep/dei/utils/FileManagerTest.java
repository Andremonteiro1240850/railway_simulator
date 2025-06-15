package pt.ipp.isep.dei.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.domain.Map;
import pt.ipp.isep.dei.domain.Scenario;
import pt.ipp.isep.dei.domain.enums.ScaleUnit;
import pt.ipp.isep.dei.repository.*;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class FileManagerTest {
    private static final String TEST_MAP_NAME = "test_map";
    private static final String TEST_SCENARIO_NAME = "test_scenario";
    private Map testMap;
    private Scenario testScenario;

    @BeforeEach
    void setUp() {
        // Initialize repositories
        CreateMapRepository createMapRepository = new CreateMapRepository();
        CityRepository cityRepository = new CityRepository();
        IndustryRepository industryRepository = new IndustryRepository();
        StationRepository stationRepository = new StationRepository();

        // Create organization with repositories
        Organization organization = new Organization("Test Organization", createMapRepository, cityRepository, industryRepository, stationRepository);
        
        // Create test map
        testMap = new Map("Test Map", 10, 10, ScaleUnit.KM, organization);

        // Create test scenario
        testScenario = new Scenario("Test Scenario", 1900, 1950, null, null, null, 700000.0, testMap);
    }

    @Test
    void saveAndLoadMap() {
        // Save map
        assertTrue(FileManager.saveMap(testMap, TEST_MAP_NAME));

        // Load map
        Map loadedMap = FileManager.loadMap(TEST_MAP_NAME);
        assertNotNull(loadedMap);
        assertEquals(testMap.getName(), loadedMap.getName());
        assertEquals(testMap.getWidth(), loadedMap.getWidth());
        assertEquals(testMap.getHeight(), loadedMap.getHeight());
    }

    @Test
    void saveAndLoadScenario() {
        // Save scenario
        assertTrue(FileManager.saveScenario(testScenario, TEST_SCENARIO_NAME));

        // Load scenario
        Scenario loadedScenario = FileManager.loadScenario(TEST_SCENARIO_NAME);
        assertNotNull(loadedScenario);
        assertEquals(testScenario.getName(), loadedScenario.getName());
        assertEquals(testScenario.getStartYear(), loadedScenario.getStartYear());
        assertEquals(testScenario.getEndYear(), loadedScenario.getEndYear());
    }

    @Test
    void getSavedMaps() {
        // Save a map
        FileManager.saveMap(testMap, TEST_MAP_NAME);

        // Get saved maps
        String[] savedMaps = FileManager.getSavedMaps();
        assertTrue(savedMaps.length > 0);
        assertTrue(contains(savedMaps, TEST_MAP_NAME));
    }

    @Test
    void getSavedScenarios() {
        // Save a scenario
        FileManager.saveScenario(testScenario, TEST_SCENARIO_NAME);

        // Get saved scenarios
        String[] savedScenarios = FileManager.getSavedScenarios();
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

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
        // Clean up test files
        try {
            Files.deleteIfExists(Paths.get("saved_maps", TEST_MAP_NAME + ".ser"));
            Files.deleteIfExists(Paths.get("saved_scenarios", TEST_SCENARIO_NAME + ".ser"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
} 