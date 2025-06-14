package pt.ipp.isep.dei.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.domain.*;
import pt.ipp.isep.dei.domain.enums.LocomotiveType;
import pt.ipp.isep.dei.domain.enums.ScaleUnit;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ScenarioRepositoryTest {

    private ScenarioRepository repository;
    private Scenario testScenario;
    private Station testStation;
    private Map testMap;
    private Organization testOrganization;
    private CreateMapRepository createMapRepository;
    private CityRepository cityRepository;
    private IndustryRepository industryRepository;
    private StationRepository stationRepository;

    @BeforeEach
    void setUp() {
        repository = new ScenarioRepository();
        
        // Initialize repositories
        createMapRepository = new CreateMapRepository();
        cityRepository = new CityRepository();
        industryRepository = new IndustryRepository();
        stationRepository = new StationRepository();

        // Create organization with repositories
        testOrganization = new Organization("123456789", createMapRepository, cityRepository, industryRepository, stationRepository);
        
        // Create map with organization
        testMap = new Map("Test Map", 10, 10, ScaleUnit.KM, testOrganization);

        // Create test station
        testStation = new Station("Test Station", "station", "North", "Test Scenario", 16, 5, 5);

        // Create test scenario with all required parameters
        List<LocomotiveType> availableLocomotiveTypes = new ArrayList<>();
        availableLocomotiveTypes.add(LocomotiveType.STEAM);
        availableLocomotiveTypes.add(LocomotiveType.DIESEL);

        List<HistoricalEvent> historicalEvents = new ArrayList<>();
        PortBehavior portBehavior = new PortBehavior();
        double budget = 700000.0;

        testScenario = new Scenario("Test Scenario", 1900, 1950, availableLocomotiveTypes,
                historicalEvents, portBehavior, budget, testMap);
        testScenario.addStationPurchased(testStation);
    }

    @Test
    void add_ValidScenario_ShouldAddAndReturnScenario() {
        // Act
        Optional<Scenario> result = repository.add(testScenario);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(testScenario.getName(), result.get().getName());
        assertFalse(repository.getAll().isEmpty());
    }

    @Test
    void add_NullScenario_ShouldReturnEmpty() {
        // Act
        Optional<Scenario> result = repository.add(null);

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    void getAll_ShouldReturnDefensiveCopy() {
        // Arrange
        repository.add(testScenario);
        List<Scenario> scenarios = repository.getAll();

        // Act
        scenarios.clear();

        // Assert
        assertFalse(repository.getAll().isEmpty());
    }

    @Test
    void findByName_ExistingScenario_ShouldReturnScenario() {
        // Arrange
        repository.add(testScenario);

        // Act
        Optional<Scenario> result = repository.findByName("Test Scenario");

        // Assert
        assertTrue(result.isPresent());
        assertEquals("Test Scenario", result.get().getName());
    }

    @Test
    void findByStationReference_ExistingStation_ShouldReturnScenario() {
        // Arrange
        repository.add(testScenario);

        // Act
        Optional<Scenario> result = repository.findByStationReference(testStation.getReference());

        // Assert
        assertTrue(result.isPresent());
        assertEquals("Test Scenario", result.get().getName());
    }

    @Test
    void findByStationReference_NonExistingStation_ShouldReturnEmpty() {
        // Arrange
        repository.add(testScenario);

        // Act
        Optional<Scenario> result = repository.findByStationReference("NONEXISTENT");

        // Assert
        assertTrue(result.isEmpty());
    }
} 