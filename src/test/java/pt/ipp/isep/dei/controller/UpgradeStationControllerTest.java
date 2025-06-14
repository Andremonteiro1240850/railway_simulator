package pt.ipp.isep.dei.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.domain.*;
import pt.ipp.isep.dei.domain.enums.Eventos;
import pt.ipp.isep.dei.domain.enums.LocomotiveType;
import pt.ipp.isep.dei.domain.enums.ScaleUnit;
import pt.ipp.isep.dei.domain.enums.StationTypes;
import pt.ipp.isep.dei.domain.enums.StationUpgrade;
import pt.ipp.isep.dei.repository.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UpgradeStationControllerTest {
    private UpgradeStationController controller;
    private OrganizationRepository orgRepo;
    private AuthenticationRepository authRepo;
    private StationRepository stationRepo;
    private UpgradeBuildingsRepository upgradeRepo;
    private ScenarioRepository scenarioRepo;
    private CreateMapRepository mapRepo;
    private CityRepository cityRepo;
    private IndustryRepository industryRepo;
    private Organization organization;
    private Employee employee;
    private Map map;
    private Scenario scenario;
    private Station station;

    @BeforeEach
    void setUp() {
        orgRepo = new OrganizationRepository();
        authRepo = new AuthenticationRepository();
        stationRepo = new StationRepository();
        upgradeRepo = new UpgradeBuildingsRepository();
        scenarioRepo = new ScenarioRepository();
        mapRepo = new CreateMapRepository();
        cityRepo = new CityRepository();
        industryRepo = new IndustryRepository();
        
        controller = new UpgradeStationController(orgRepo, authRepo, stationRepo, upgradeRepo, scenarioRepo);
        
        organization = new Organization("Test Organization", mapRepo, cityRepo, industryRepo, stationRepo);
        employee = new Employee("test@example.com");
        organization.addEmployee(employee);
        map = new Map("Test Map", 100, 100, ScaleUnit.KM, organization);
        
        List<HistoricalEvent> historicalEvents = new ArrayList<>();
        historicalEvents.add(new HistoricalEvent(Eventos.THE_IRON_SEED));
        scenario = new Scenario("Test Scenario", 1900, 2000, List.of(LocomotiveType.STEAM, LocomotiveType.DIESEL), 
            historicalEvents, new PortBehavior(), 1000000, map);
        scenarioRepo.add(scenario);
        station = new Station("ST001", "Test Station", StationTypes.STATION.getType(), "NE", "Test Scenario", 
            StationTypes.STATION.getArea(), 10, 10);
    }

    @Test
    void testGetAvailableStations() {
        // Arrange
        stationRepo.add(station);

        // Act
        List<Station> stations = controller.getAvailableStations();

        // Assert
        assertNotNull(stations);
        assertFalse(stations.isEmpty());
        assertTrue(stations.contains(station));
    }

    @Test
    void testGetAvailableUpgradeTypes() {
        // Act
        List<String> upgradeTypes = controller.getAvailableUpgradeTypes();

        // Assert
        assertNotNull(upgradeTypes);
        assertFalse(upgradeTypes.isEmpty());
        assertTrue(upgradeTypes.contains(StationUpgrade.TELEPHONE.getDisplayName()));
        assertTrue(upgradeTypes.contains(StationUpgrade.TELEGRAPH.getDisplayName()));
        assertTrue(upgradeTypes.contains(StationUpgrade.RESTAURANT_SMALL.getDisplayName()));
    }

    @Test
    void testGetAvailableSubTypes() {
        // Arrange
        String upgradeType = StationUpgrade.RESTAURANT_LARGE.getDisplayName();

        // Act
        List<String> subTypes = controller.getAvailableSubTypes(upgradeType);

        // Assert
        assertNotNull(subTypes);
        assertTrue(subTypes.isEmpty()); // No subtypes for this upgrade
    }

    @Test
    void testGetAvailableSubTypesWithInvalidType() {
        // Arrange
        String upgradeType = "INVALID_TYPE";

        // Act
        List<String> subTypes = controller.getAvailableSubTypes(upgradeType);

        // Assert
        assertNotNull(subTypes);
        assertTrue(subTypes.isEmpty());
    }

    @Test
    void testGetAvailableSubTypesWithNullType() {
        // Act
        List<String> subTypes = controller.getAvailableSubTypes(null);

        // Assert
        assertNotNull(subTypes);
        assertTrue(subTypes.isEmpty());
    }
} 