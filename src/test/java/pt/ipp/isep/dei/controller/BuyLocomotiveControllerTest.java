package pt.ipp.isep.dei.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.domain.*;
import pt.ipp.isep.dei.domain.enums.LocomotiveType;
import pt.ipp.isep.dei.domain.enums.ScaleUnit;
import pt.ipp.isep.dei.repository.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BuyLocomotiveControllerTest {
    private BuyLocomotiveController controller;
    private LocomotiveRepository locomotiveRepo;
    private Scenario scenario;
    private Map map;
    private Organization organization;
    private CreateMapRepository createMapRepository;
    private CityRepository cityRepository;
    private IndustryRepository industryRepository;
    private StationRepository stationRepository;

    @BeforeEach
    void setUp() {
        locomotiveRepo = new LocomotiveRepository();
        controller = new BuyLocomotiveController();
        
        // Initialize repositories
        createMapRepository = new CreateMapRepository();
        cityRepository = new CityRepository();
        industryRepository = new IndustryRepository();
        stationRepository = new StationRepository();
        
        // Create organization
        organization = new Organization("123456789", createMapRepository, cityRepository, industryRepository, stationRepository);
        
        // Create map with correct constructor
        map = new Map("Test Map", 100, 100, ScaleUnit.KM, organization);
        
        // Create scenario with correct constructor
        scenario = new Scenario("Test Scenario", 1900, 2000,
            List.of(LocomotiveType.STEAM, LocomotiveType.DIESEL),
            new ArrayList<>(),
            new PortBehavior(),
            1000000.0,
            map);
    }

    @Test
    void testShowAvailableLocomotives() {
        // Arrange
        scenario.setCurrentYear(1950);

        // Act & Assert
        assertDoesNotThrow(() -> controller.showAvailableLocomotives(scenario));
    }

    @Test
    void testShowAvailableLocomotivesWithNoTypes() {
        // Arrange
        scenario = new Scenario("Test Scenario", 1900, 2000,
            new ArrayList<>(),
            new ArrayList<>(),
            new PortBehavior(),
            1000000.0,
            map);
        scenario.setCurrentYear(1950);

        // Act & Assert
        assertDoesNotThrow(() -> controller.showAvailableLocomotives(scenario));
    }

    @Test
    void testShowAvailableLocomotivesWithNullScenario() {
        // Act & Assert
        assertThrows(NullPointerException.class, () -> controller.showAvailableLocomotives(null));
    }

    @Test
    void testShowAvailableLocomotivesWithFutureYear() {
        // Arrange
        scenario.setCurrentYear(2100);

        // Act & Assert
        assertDoesNotThrow(() -> controller.showAvailableLocomotives(scenario));
    }

    @Test
    void testShowAvailableLocomotivesWithPastYear() {
        // Arrange
        scenario.setCurrentYear(1800);

        // Act & Assert
        assertDoesNotThrow(() -> controller.showAvailableLocomotives(scenario));
    }
} 