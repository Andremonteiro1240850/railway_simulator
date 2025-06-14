package pt.ipp.isep.dei.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import pt.ipp.isep.dei.domain.enums.CargoMode;
import pt.ipp.isep.dei.domain.enums.LocomotiveType;
import pt.ipp.isep.dei.domain.enums.Eventos;
import pt.ipp.isep.dei.domain.enums.ScaleUnit;
import pt.ipp.isep.dei.repository.CreateMapRepository;
import pt.ipp.isep.dei.repository.CityRepository;
import pt.ipp.isep.dei.repository.IndustryRepository;
import pt.ipp.isep.dei.repository.StationRepository;

class ScenarioTest {
    private Scenario scenario;
    private String name;
    private int startYear;
    private int endYear;
    private List<LocomotiveType> availableLocomotiveTypes;
    private List<HistoricalEvent> historicalEvents;
    private PortBehavior portBehavior;
    private double budget;
    private Map map;
    private Organization organization;
    private CreateMapRepository createMapRepository;
    private CityRepository cityRepository;
    private IndustryRepository industryRepository;
    private StationRepository stationRepository;

    @BeforeEach
    void setUp() {
        name = "Test Scenario";
        startYear = 1900;
        endYear = 1950;
        availableLocomotiveTypes = new ArrayList<>();
        availableLocomotiveTypes.add(LocomotiveType.DIESEL);
        availableLocomotiveTypes.add(LocomotiveType.ELECTRIC);
        historicalEvents = new ArrayList<>();
        portBehavior = new PortBehavior();
        budget = 700000.0;

        // Initialize repositories
        createMapRepository = new CreateMapRepository();
        cityRepository = new CityRepository();
        industryRepository = new IndustryRepository();
        stationRepository = new StationRepository();

        // Create organization with repositories
        organization = new Organization("Test Organization", createMapRepository, cityRepository, industryRepository, stationRepository);
        
        // Create map with organization
        map = new Map("Test Map", 10, 10, ScaleUnit.KM, organization);

        scenario = new Scenario(name, startYear, endYear, availableLocomotiveTypes,
                historicalEvents, portBehavior, budget, map);
    }

    @Test
    void testConstructor() {
        assertNotNull(scenario);
        assertEquals(name, scenario.getName());
        assertEquals(startYear, scenario.getStartYear());
        assertEquals(endYear, scenario.getEndYear());
        assertEquals(startYear, scenario.getCurrentYear());
        assertEquals(1, scenario.getStartMonth());
        assertEquals(1, scenario.getCurrentMonth());
        assertEquals(budget, scenario.getBudget());
        assertNotNull(scenario.getAvailableLocomotiveTypes());
        assertNotNull(scenario.getHistoricalEvents());
        assertNotNull(scenario.getPortBehavior());
        assertNotNull(scenario.getMap());
        assertNotNull(scenario.getLocomotivePurchased());
        assertNotNull(scenario.getCarriagePurchased());
        assertNotNull(scenario.getStationsPurchased());
        assertNotNull(scenario.getRoutes());
    }

    @Test
    void testGettersAndSetters() {
        // Test current year
        int newYear = 1920;
        scenario.setCurrentYear(newYear);
        assertEquals(newYear, scenario.getCurrentYear());

        // Test current month
        int newMonth = 6;
        scenario.setCurrentMonth(newMonth);
        assertEquals(newMonth, scenario.getCurrentMonth());

        // Test budget
        double newBudget = 800000.0;
        scenario.setBudget(newBudget);
        assertEquals(newBudget, scenario.getBudget());

        // Test port behavior
        PortBehavior newPortBehavior = new PortBehavior();
        scenario.setPortBehavior(newPortBehavior);
        assertEquals(newPortBehavior, scenario.getPortBehavior());
    }

    @Test
    void testAddPurchasedItems() {
        // Test adding locomotive
        Locomotive locomotive = new Locomotive("Test Locomotive", LocomotiveType.DIESEL, 100.0, 10.0, 2020, 5000.0, 1000, 100.0);
        scenario.addLocomotivePurchased(locomotive);
        assertTrue(scenario.getLocomotivePurchased().contains(locomotive));

        // Test adding carriage
        Carriage carriage = new Carriage("Test Carriage", "coal", 50.0, 1000.0);
        scenario.addCarriagePurchased(carriage);
        assertTrue(scenario.getCarriagePurchased().contains(carriage));

        // Test adding station
        Station station = new Station("Test Station", "station", "Test Region", name, 16, 5, 5);
        scenario.addStationPurchased(station);
        assertTrue(scenario.getStationsPurchased().contains(station));
    }

    @Test
    void testAddRoute() {
        // Create test route
        Locomotive locomotive = new Locomotive("Test Locomotive", LocomotiveType.DIESEL, 100.0, 10.0, 2020, 5000.0, 1000, 100.0);
        List<Carriage> carriages = new ArrayList<>();
        carriages.add(new Carriage("Test Carriage", "coal", 50.0, 1000.0));
        Train train = new Train(locomotive, carriages);
        
        Station station1 = new Station("Station 1", "station", "Region 1", name, 16, 1, 1);
        Station station2 = new Station("Station 2", "station", "Region 2", name, 16, 2, 2);
        List<PointsOfRoute> routePoints = new ArrayList<>();
        routePoints.add(new PointsOfRoute(station1, CargoMode.FULL));
        routePoints.add(new PointsOfRoute(station2, CargoMode.HALF));
        
        Route route = new Route(train, routePoints);
        
        // Add route and verify
        scenario.addRoute(route);
        assertTrue(scenario.getRoutes().contains(route));
    }

    @Test
    void testClone() {
        Scenario clonedScenario = scenario.clone();
        
        // Verify basic properties
        assertEquals(scenario.getName(), clonedScenario.getName());
        assertEquals(scenario.getStartYear(), clonedScenario.getStartYear());
        assertEquals(scenario.getEndYear(), clonedScenario.getEndYear());
        assertEquals(scenario.getCurrentYear(), clonedScenario.getCurrentYear());
        assertEquals(scenario.getBudget(), clonedScenario.getBudget());
        
        // Verify collections are deep copied
        assertNotSame(scenario.getAvailableLocomotiveTypes(), clonedScenario.getAvailableLocomotiveTypes());
        assertNotSame(scenario.getHistoricalEvents(), clonedScenario.getHistoricalEvents());
        assertNotSame(scenario.getLocomotivePurchased(), clonedScenario.getLocomotivePurchased());
        assertNotSame(scenario.getCarriagePurchased(), clonedScenario.getCarriagePurchased());
        assertNotSame(scenario.getStationsPurchased(), clonedScenario.getStationsPurchased());
    }

    @Test
    void testToString() {
        String result = scenario.toString();
        
        // Verify string contains key information
        assertTrue(result.contains("=== Scenario: " + name + " ==="));
        assertTrue(result.contains("Budget: " + String.format("%.2f", budget)));
        assertTrue(result.contains("Time Period: " + startYear + " - " + endYear));
        assertTrue(result.contains("Available Locomotive Types:"));
        assertTrue(result.contains("Port Behavior:"));
        assertTrue(result.contains("Historical Events:"));
    }

    @Test
    void testGetSummary() {
        String summary = scenario.getSummary();
        String expected = String.format("%s (%d–%d) – Budget: %.2f€", name, startYear, endYear, budget);
        assertEquals(expected, summary);
    }

    @Test
    void testAtualizarLocomotivasDisponiveis() {
        int testYear = 1920;
        scenario.atualizarLocomotivasDisponiveis(testYear);
        List<Locomotive> availableLocomotives = scenario.getLocomotivesDisponiveis();
        assertNotNull(availableLocomotives);
        // Note: The actual number of locomotives will depend on the ListLocomotives enum values
        // that are available in the test year
    }
} 