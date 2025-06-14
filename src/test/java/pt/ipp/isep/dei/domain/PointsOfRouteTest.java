package pt.ipp.isep.dei.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import pt.ipp.isep.dei.domain.enums.CargoMode;
import pt.ipp.isep.dei.domain.enums.ScaleUnit;
import pt.ipp.isep.dei.repository.Repositories;

class PointsOfRouteTest {
    private PointsOfRoute pointOfRoute;
    private Station station;
    private CargoMode cargoMode;

    @BeforeEach
    void setUp() {
        Organization organization = new Organization("Test Org", 
            Repositories.getInstance().getMapRepository(),
            Repositories.getInstance().getCityRepository(),
            Repositories.getInstance().getIndustryRepository(),
            Repositories.getInstance().getStationRepository());
        Map map = new Map("Test Map", 10, 10, ScaleUnit.KM, organization);
        station = new Station("Test Station", "station", "Test Region", "Test Scenario", 16, 5, 5);
        cargoMode = CargoMode.FULL;
        pointOfRoute = new PointsOfRoute(station, cargoMode);
    }

    @Test
    void testConstructorWithValidParameters() {
        assertNotNull(pointOfRoute);
        assertEquals(station, pointOfRoute.getStations());
        assertEquals(cargoMode, pointOfRoute.getCargoMode());
    }

    @Test
    void testConstructorWithNullStation() {
        assertThrows(NullPointerException.class, () ->
            new PointsOfRoute(null, CargoMode.FULL)
        );
    }

    @Test
    void testConstructorWithNullCargoMode() {
        assertThrows(NullPointerException.class, () ->
            new PointsOfRoute(station, null)
        );
    }

    @Test
    void testGetStations() {
        assertEquals(station, pointOfRoute.getStations());
    }

    @Test
    void testGetCargoMode() {
        assertEquals(cargoMode, pointOfRoute.getCargoMode());
    }

    @Test
    void testSetStation() {
        Station newStation = new Station("New Station", "station", "New Region", "Test Scenario", 16, 6, 6);
        pointOfRoute.setStation(newStation);
        assertEquals(newStation, pointOfRoute.getStations());
    }

    @Test
    void testSetCargoMode() {
        CargoMode newMode = CargoMode.HALF;
        pointOfRoute.setCargoMode(newMode);
        assertEquals(newMode, pointOfRoute.getCargoMode());
    }

    @Test
    void testToString() {
        String expected = String.format(
            "Station: %s | Cargo Mode: %s (%s)",
            station.getName(),
            cargoMode.name(),
            cargoMode.getDescription()
        );
        assertEquals(expected, pointOfRoute.toString());
    }

    @Test
    void testEquals() {
        PointsOfRoute samePoint = new PointsOfRoute(station, cargoMode);
        PointsOfRoute differentStation = new PointsOfRoute(
            new Station("Different Station", "station", "Different Region", "Test Scenario", 16, 6, 6),
            cargoMode
        );
        PointsOfRoute differentCargoMode = new PointsOfRoute(station, CargoMode.HALF);

        assertEquals(pointOfRoute, samePoint);
        assertNotEquals(pointOfRoute, differentStation);
        assertNotEquals(pointOfRoute, differentCargoMode);
    }

    @Test
    void testHashCode() {
        PointsOfRoute samePoint = new PointsOfRoute(station, cargoMode);
        assertEquals(pointOfRoute.hashCode(), samePoint.hashCode());
    }
} 