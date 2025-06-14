package pt.ipp.isep.dei.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import pt.ipp.isep.dei.domain.enums.CargoMode;
import pt.ipp.isep.dei.domain.enums.LocomotiveType;
import pt.ipp.isep.dei.domain.enums.ScaleUnit;
import pt.ipp.isep.dei.repository.CreateMapRepository;
import pt.ipp.isep.dei.repository.CityRepository;
import pt.ipp.isep.dei.repository.IndustryRepository;
import pt.ipp.isep.dei.repository.StationRepository;

class SimulationTest {
    private Simulation simulation;
    private Scenario scenario;
    private Player player;
    private Map map;
    private Organization organization;
    private CreateMapRepository createMapRepository;
    private CityRepository cityRepository;
    private IndustryRepository industryRepository;
    private StationRepository stationRepository;

    @BeforeEach
    void setUp() {
        // Initialize repositories
        createMapRepository = new CreateMapRepository();
        cityRepository = new CityRepository();
        industryRepository = new IndustryRepository();
        stationRepository = new StationRepository();

        // Create organization
        organization = new Organization("Test Organization", createMapRepository, cityRepository, industryRepository, stationRepository);

        // Create map
        map = new Map("Test Map", 10, 10, ScaleUnit.KM, organization);

        // Create scenario
        List<LocomotiveType> availableTypes = new ArrayList<>();
        availableTypes.add(LocomotiveType.DIESEL);
        availableTypes.add(LocomotiveType.ELECTRIC);
        scenario = new Scenario("Test Scenario", 1900, 1950, availableTypes, new ArrayList<>(), new PortBehavior(), 700000.0, map);

        // Create player
        player = new Player("Test Player");

        // Create simulation
        simulation = new Simulation(scenario, player);
    }

    @Test
    void testConstructor() {
        assertNotNull(simulation);
        assertEquals(scenario, simulation.getScenario());
        assertEquals(scenario.getStartYear(), simulation.getCurrentYear());
        assertEquals(1, simulation.getCurrentMonth());
        assertNotNull(simulation.getActiveRoutes());
        assertTrue(simulation.getActiveRoutes().isEmpty());
        assertNotNull(simulation.getStations());
        assertTrue(simulation.getStations().isEmpty());
        assertNotNull(simulation.getLocomotives());
        assertTrue(simulation.getLocomotives().isEmpty());
    }

    @Test
    void testAdvanceMonth() {
        int initialYear = simulation.getCurrentYear();
        int initialMonth = simulation.getCurrentMonth();
        double initialBudget = scenario.getBudget();

        simulation.advanceMonth();

        assertEquals(initialMonth + 1, simulation.getCurrentMonth());
        assertEquals(initialYear, simulation.getCurrentYear());
        // Budget changes would depend on active routes and stations
    }

    @Test
    void testAdvanceMonthYearChange() {
        // Set to December
        simulation = new Simulation(scenario, player);
        for (int i = 0; i < 11; i++) {
            simulation.advanceMonth();
        }

        int initialYear = simulation.getCurrentYear();
        simulation.advanceMonth();

        assertEquals(1, simulation.getCurrentMonth());
        assertEquals(initialYear + 1, simulation.getCurrentYear());
    }

    @Test
    void testAddRoute() {
        // Create test route
        Locomotive locomotive = new Locomotive("Test Locomotive", LocomotiveType.DIESEL, 100.0, 10.0, 2020, 5000.0, 1000, 100.0);
        List<Carriage> carriages = new ArrayList<>();
        carriages.add(new Carriage("Test Carriage", "coal", 50.0, 1000.0));
        Train train = new Train(locomotive, carriages);
        
        Station station1 = new Station("Station 1", "station", "Region 1", scenario.getName(), 16, 1, 1);
        Station station2 = new Station("Station 2", "station", "Region 2", scenario.getName(), 16, 2, 2);
        List<PointsOfRoute> routePoints = new ArrayList<>();
        routePoints.add(new PointsOfRoute(station1, CargoMode.FULL));
        routePoints.add(new PointsOfRoute(station2, CargoMode.HALF));
        
        Route route = new Route(train, routePoints);
        
        simulation.addRoute(route);
        assertTrue(simulation.getActiveRoutes().contains(route));
    }

    @Test
    void testAddStation() {
        Station station = new Station("Test Station", "station", "Test Region", scenario.getName(), 16, 5, 5);
        simulation.addStation(station);
        assertTrue(simulation.getStations().contains(station));
    }

    @Test
    void testAddLocomotive() {
        Locomotive locomotive = new Locomotive("Test Locomotive", LocomotiveType.DIESEL, 100.0, 10.0, 2020, 5000.0, 1000, 100.0);
        simulation.addLocomotive(locomotive);
        assertTrue(simulation.getLocomotives().contains(locomotive));
    }

    @Test
    void testGetEndYear() {
        assertEquals(scenario.getEndYear(), simulation.getEndYear());
    }

    @Test
    void testGetActiveRoutes() {
        // Create and add a route
        Locomotive locomotive = new Locomotive("Test Locomotive", LocomotiveType.DIESEL, 100.0, 10.0, 2020, 5000.0, 1000, 100.0);
        List<Carriage> carriages = new ArrayList<>();
        carriages.add(new Carriage("Test Carriage", "coal", 50.0, 1000.0));
        Train train = new Train(locomotive, carriages);
        
        Station station1 = new Station("Station 1", "station", "Region 1", scenario.getName(), 16, 1, 1);
        Station station2 = new Station("Station 2", "station", "Region 2", scenario.getName(), 16, 2, 2);
        List<PointsOfRoute> routePoints = new ArrayList<>();
        routePoints.add(new PointsOfRoute(station1, CargoMode.FULL));
        routePoints.add(new PointsOfRoute(station2, CargoMode.HALF));
        
        Route route = new Route(train, routePoints);
        simulation.addRoute(route);

        List<Route> routes = simulation.getActiveRoutes();
        assertNotNull(routes);
        assertEquals(1, routes.size());
        assertEquals(route, routes.get(0));
    }

    @Test
    void testGetStations() {
        Station station = new Station("Test Station", "station", "Test Region", scenario.getName(), 16, 5, 5);
        simulation.addStation(station);

        List<Station> stations = simulation.getStations();
        assertNotNull(stations);
        assertEquals(1, stations.size());
        assertEquals(station, stations.get(0));
    }

    @Test
    void testGetLocomotives() {
        Locomotive locomotive = new Locomotive("Test Locomotive", LocomotiveType.DIESEL, 100.0, 10.0, 2020, 5000.0, 1000, 100.0);
        simulation.addLocomotive(locomotive);

        List<Locomotive> locomotives = simulation.getLocomotives();
        assertNotNull(locomotives);
        assertEquals(1, locomotives.size());
        assertEquals(locomotive, locomotives.get(0));
    }
} 