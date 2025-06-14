package pt.ipp.isep.dei.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import pt.ipp.isep.dei.domain.enums.CargoMode;
import pt.ipp.isep.dei.domain.enums.LocomotiveType;

class RouteTest {
    private Route route;
    private Train train;
    private List<PointsOfRoute> routePoints;
    private Station station1;
    private Station station2;
    private PointsOfRoute point1;
    private PointsOfRoute point2;
    private Locomotive locomotive;
    private List<Carriage> carriages;

    @BeforeEach
    void setUp() {
        // Create test stations
        station1 = new Station("Station 1", "station", "Region 1", "Test Scenario", 16, 1, 1);
        station2 = new Station("Station 2", "station", "Region 2", "Test Scenario", 16, 2, 2);

        // Create test points
        point1 = new PointsOfRoute(station1, CargoMode.FULL);
        point2 = new PointsOfRoute(station2, CargoMode.HALF);

        // Create route points list
        routePoints = new ArrayList<>();
        routePoints.add(point1);

        // Create test locomotive and carriages
        locomotive = new Locomotive("Test Locomotive", LocomotiveType.DIESEL, 100.0, 10.0, 2020, 5000.0, 1000, 100.0);
        carriages = new ArrayList<>();
        carriages.add(new Carriage("Test Carriage", "coal", 50.0, 1000.0));

        // Create test train
        train = new Train(locomotive, carriages);

        // Create route
        route = new Route(train, routePoints);
    }

    @Test
    void testConstructor() {
        assertNotNull(route);
        assertEquals(train, route.getTrain());
        assertEquals(routePoints, route.getRoute());
    }

    @Test
    void testConstructorWithNullTrain() {
        assertThrows(NullPointerException.class, () ->
            new Route(null, routePoints)
        );
    }

    @Test
    void testConstructorWithNullRoute() {
        assertThrows(NullPointerException.class, () ->
            new Route(train, null)
        );
    }

    @Test
    void testGetRoute() {
        List<PointsOfRoute> result = route.getRoute();
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(point1, result.get(0));
    }

    @Test
    void testAddRoute() {
        route.AddRoute(point2);
        List<PointsOfRoute> result = route.getRoute();
        assertEquals(2, result.size());
        assertEquals(point1, result.get(0));
        assertEquals(point2, result.get(1));
    }

    @Test
    void testAddRouteWithNull() {
        assertThrows(NullPointerException.class, () ->
            route.AddRoute(null)
        );
    }

    @Test
    void testGetTrain() {
        assertEquals(train, route.getTrain());
    }

    @Test
    void testSetTrain() {
        Locomotive newLocomotive = new Locomotive("New Locomotive", LocomotiveType.ELECTRIC, 150.0, 15.0, 2021, 6000.0, 1200, 150.0);
        List<Carriage> newCarriages = new ArrayList<>();
        newCarriages.add(new Carriage("New Carriage", "passengers", 60.0, 2000.0));
        Train newTrain = new Train(newLocomotive, newCarriages);
        
        route.setTrain(newTrain);
        assertEquals(newTrain, route.getTrain());
    }

    @Test
    void testSetTrainWithNull() {
        assertThrows(NullPointerException.class, () ->
            route.setTrain(null)
        );
    }

    @Test
    void testToString() {
        String result = route.toString();
        assertTrue(result.contains("Train: " + train));
        assertTrue(result.contains("Route Points:"));
        assertTrue(result.contains(" - " + point1));
        
        // Add another point and test again
        route.AddRoute(point2);
        result = route.toString();
        assertTrue(result.contains(" - " + point2));
    }
} 