package pt.ipp.isep.dei.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.domain.*;
import pt.ipp.isep.dei.domain.enums.*;
import pt.ipp.isep.dei.repository.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class BuildStationControllerTest {
    private BuildStationController controller;
    private OrganizationRepository orgRepo;
    private AuthenticationRepository authRepo;
    private StationRepository stationRepo;
    private Organization organization;
    private Employee employee;
    private Map map;
    private Scenario scenario;

    @BeforeEach
    void setUp() {
        // Initialize repositories
        CreateMapRepository createMapRepository = new CreateMapRepository();
        CityRepository cityRepository = new CityRepository();
        IndustryRepository industryRepository = new IndustryRepository();
        stationRepo = new StationRepository();
        orgRepo = new OrganizationRepository();
        authRepo = new AuthenticationRepository();

        // Create organization with repositories
        organization = new Organization("123456789", createMapRepository, cityRepository, industryRepository, stationRepo);
        
        // Create employee
        employee = new Employee("test@example.com");
        organization.addEmployee(employee);

        // Create map with organization
        map = new Map("Test Map", 100, 100, ScaleUnit.KM, organization);

        // Create scenario
        scenario = new Scenario("Test Scenario", 1900, 2000, 
            List.of(LocomotiveType.STEAM, LocomotiveType.DIESEL),
            new ArrayList<>(),
            new PortBehavior(),
            1000000.0,
            map);

        controller = new BuildStationController(orgRepo, authRepo, stationRepo);
    }

    @Test
    void testBuildStationWithValidData() {
        // Arrange
        int positionX = 50;
        int positionY = 50;
        String type = StationTypes.STATION.getType();
        String region = Regions.NE.getRegion();
        
        // Set the scenario on the controller
        controller.setScenario(scenario);

        // Act
        boolean result = controller.createStation(type, "Test Station", region, positionX, positionY);

        // Assert
        assertTrue(result);
        List<Station> stations = stationRepo.getAll();
        assertFalse(stations.isEmpty());
        Station station = stations.get(0);
        assertEquals(positionX, station.getX());
        assertEquals(positionY, station.getY());
        assertEquals(type, station.getType());
        assertEquals(region, station.getRegion());
    }

    @Test
    void testBuildStationWithInvalidPosition() {
        // Arrange
        int positionX = 150; // Outside map bounds
        int positionY = 50;
        String type = StationTypes.STATION.getType();
        String region = Regions.NE.getRegion();
        
        // Set the scenario on the controller
        controller.setScenario(scenario);

        // Act
        boolean result = controller.createStation(type, "Test Station", region, positionX, positionY);

        // Assert
        assertFalse(result);
    }

    @Test
    void testBuildStationWithNegativePosition() {
        // Arrange
        int positionX = -10;
        int positionY = 50;
        String type = StationTypes.STATION.getType();
        String region = Regions.NE.getRegion();
        
        // Set the scenario on the controller
        controller.setScenario(scenario);

        // Act
        boolean result = controller.createStation(type, "Test Station", region, positionX, positionY);

        // Assert
        assertFalse(result);
    }

    @Test
    void testBuildStationWithNullType() {
        // Arrange
        int positionX = 50;
        int positionY = 50;
        String type = null;
        String region = Regions.NE.getRegion();
        
        // Set the scenario on the controller
        controller.setScenario(scenario);

        // Act
        boolean result = controller.createStation(type, "Test Station", region, positionX, positionY);

        // Assert
        assertFalse(result);
    }

    @Test
    void testBuildStationWithNullRegion() {
        // Arrange
        int positionX = 50;
        int positionY = 50;
        String type = StationTypes.STATION.getType();
        String region = null;
        
        // Set the scenario on the controller
        controller.setScenario(scenario);

        // Act
        boolean result = controller.createStation(type, "Test Station", region, positionX, positionY);

        // Assert
        assertFalse(result);
    }

    @Test
    void testBuildStationWithOccupiedPosition() {
        // Arrange
        int positionX = 50;
        int positionY = 50;
        String type = StationTypes.STATION.getType();
        String region = Regions.NE.getRegion();
        
        // Set the scenario on the controller
        controller.setScenario(scenario);

        // Act
        controller.createStation(type, "Test Station 1", region, positionX, positionY);
        boolean duplicateResult = controller.createStation(type, "Test Station 2", region, positionX, positionY);

        // Assert
        assertFalse(duplicateResult);
    }
} 