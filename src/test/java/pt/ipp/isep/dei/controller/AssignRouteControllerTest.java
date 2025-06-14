package pt.ipp.isep.dei.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import pt.ipp.isep.dei.domain.*;
import pt.ipp.isep.dei.domain.enums.*;
import pt.ipp.isep.dei.repository.*;

import java.util.ArrayList;
import java.util.List;

class AssignRouteControllerTest {
    private AssignRouteController controller;
    private ScenarioRepository scenarioRepository;
    private Organization organization;
    private Employee employee;
    private Map map;
    private Scenario scenario;
    private Station station1;
    private Station station2;
    private Locomotive locomotive;
    private List<Carriage> carriages;
    private List<PointsOfRoute> routePoints;

    @BeforeEach
    void setUp() {
        // Initialize repositories
        CreateMapRepository createMapRepository = new CreateMapRepository();
        CityRepository cityRepository = new CityRepository();
        IndustryRepository industryRepository = new IndustryRepository();
        StationRepository stationRepository = new StationRepository();
        scenarioRepository = new ScenarioRepository();

        // Create organization with repositories
        organization = new Organization("123456789", createMapRepository, cityRepository, industryRepository, stationRepository);
        
        // Create map with organization
        map = new Map("Test Map", 10, 10, ScaleUnit.KM, organization);

        // Create scenario
        scenario = new Scenario("Test Scenario", 1900, 1950, 
            List.of(LocomotiveType.STEAM, LocomotiveType.DIESEL),
            new ArrayList<>(),
            new PortBehavior(),
            1000000.0,
            map);

        // Create test stations
        station1 = new Station("Station 1", StationTypes.STATION.getType(), "North", scenario.getName(), 
            StationTypes.STATION.getArea(), 1, 1);
        station2 = new Station("Station 2", StationTypes.STATION.getType(), "South", scenario.getName(), 
            StationTypes.STATION.getArea(), 2, 2);

        // Create test locomotive
        locomotive = new Locomotive("Test Locomotive", LocomotiveType.DIESEL, 100000.0, 10000.0, 1000, 2.5, 1900, 50.0);

        // Create test carriages
        carriages = new ArrayList<>();
        carriages.add(new Carriage("Test Carriage", "PASSENGERS", 1000.0, 50000.0));

        // Create route points
        routePoints = new ArrayList<>();
        routePoints.add(new PointsOfRoute(station1, CargoMode.FULL));
        routePoints.add(new PointsOfRoute(station2, CargoMode.HALF));

        // Initialize controller
        controller = new AssignRouteController();
    }

    @Test
    void testGetAvailableScenarios() {
        List<Scenario> scenarios = controller.getAvailableScenarios();
        assertNotNull(scenarios);
        assertTrue(scenarios.isEmpty());
    }

    @Test
    void testCreatePointOfRoute() {
        PointsOfRoute point = controller.createPointOfRoute(station1, CargoMode.FULL);
        assertNotNull(point);
        assertEquals(station1, point.getStations());
        assertEquals(CargoMode.FULL, point.getCargoMode());
    }

    @Test
    void testCreatePointOfRouteWithNullStation() {
        assertThrows(NullPointerException.class, () ->
            controller.createPointOfRoute(null, CargoMode.FULL)
        );
    }

    @Test
    void testCreatePointOfRouteWithNullCargoMode() {
        assertThrows(NullPointerException.class, () ->
            controller.createPointOfRoute(station1, null)
        );
    }

    @Test
    void testAssignRouteToTrain() {
        controller.assignRouteToTrain(locomotive, carriages, routePoints, scenario);
        List<Route> routes = scenario.getRoutes();
        assertNotNull(routes);
        assertFalse(routes.isEmpty());
        assertEquals(1, routes.size());
        
        Route route = routes.get(0);
        assertNotNull(route);
        assertEquals(locomotive, route.getTrain().getLocomotive());
        assertEquals(carriages.size(), route.getTrain().getCarriageList().size());
        assertEquals(routePoints.size(), route.getRoute().size());
    }

    @Test
    void testAssignRouteToTrainWithNullLocomotive() {
        assertThrows(NullPointerException.class, () ->
            controller.assignRouteToTrain(null, carriages, routePoints, scenario)
        );
    }

    @Test
    void testAssignRouteToTrainWithNullCarriages() {
        assertThrows(NullPointerException.class, () ->
            controller.assignRouteToTrain(locomotive, null, routePoints, scenario)
        );
    }

    @Test
    void testAssignRouteToTrainWithNullRoutePoints() {
        assertThrows(NullPointerException.class, () ->
            controller.assignRouteToTrain(locomotive, carriages, null, scenario)
        );
    }

    @Test
    void testAssignRouteToTrainWithNullScenario() {
        assertThrows(NullPointerException.class, () ->
            controller.assignRouteToTrain(locomotive, carriages, routePoints, null)
        );
    }

    @Test
    void testCreatePointOfRouteWithDifferentCargoModes() {
        // Test FULL mode
        PointsOfRoute fullPoint = controller.createPointOfRoute(station1, CargoMode.FULL);
        assertNotNull(fullPoint);
        assertEquals(CargoMode.FULL, fullPoint.getCargoMode());

        // Test HALF mode
        PointsOfRoute halfPoint = controller.createPointOfRoute(station2, CargoMode.HALF);
        assertNotNull(halfPoint);
        assertEquals(CargoMode.HALF, halfPoint.getCargoMode());

        // Test AVAILABLE mode
        PointsOfRoute availablePoint = controller.createPointOfRoute(station1, CargoMode.AVAILABLE);
        assertNotNull(availablePoint);
        assertEquals(CargoMode.AVAILABLE, availablePoint.getCargoMode());
    }
} 