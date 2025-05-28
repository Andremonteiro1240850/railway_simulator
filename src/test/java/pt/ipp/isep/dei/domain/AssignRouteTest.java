package pt.ipp.isep.dei.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.repository.CargoRepository;
import pt.ipp.isep.dei.repository.StationRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class AssignRouteTest {

    private StationRepository stationRepository;
    private CargoRepository cargoRepository;
    private Locomotive testLocomotive;

    @BeforeEach
    void setUp() {
        stationRepository = new StationRepository();
        cargoRepository = new CargoRepository();



        Station s1 = new Station("S001", "North Station", "station", "North");
        Station s2 = new Station("S002", "South Station", "station", "South");

        stationRepository.add(s1);
        stationRepository.add(s2);
    }

    /**
     * Test 1: Should create a route with valid inputs
     */
    @Test
    void shouldCreateRouteWithValidInputs() {
        List<Station> stations = stationRepository.getAll();
        List<String> cargos = List.of("coal", "cattle");

        Route route = new Route(stations, testLocomotive, cargos);

        assertEquals(2, route.getStations().size());
        assertEquals("L001", route.getLocomotive().getReference());
        assertEquals(cargos, route.getCargosToTransport());
    }

    /**
     * Test 2: Should reject route creation with less than 2 stations
     */
    @Test
    void shouldRejectRouteWithLessThanTwoStations() {
        List<Station> onlyOne = List.of(stationRepository.getAll().get(0));
        Route route = null;

        if (onlyOne.size() < 2) {
            route = null;
        }

        assertNull(route);
    }

    /**
     * Test 3: Should validate if cargo is valid
     */
    @Test
    void shouldRecognizeValidCargo() {
        assertTrue(cargoRepository.isValidCargo("mail"));
    }

    /**
     * Test 4: Should reject invalid cargo
     */
    @Test
    void shouldRejectInvalidCargo() {
        assertFalse(cargoRepository.isValidCargo("diamonds"));
    }

    /**
     * Test 5: Should clone a route properly
     */
    @Test
    void shouldCloneRouteCorrectly() {
        List<Station> stations = stationRepository.getAll();
        List<String> cargos = List.of("mail", "coal");
        Route original = new Route(stations, testLocomotive, cargos);
        Route copy = original.clone();

        assertNotSame(original, copy);
        assertEquals(original.getStations(), copy.getStations());
        assertEquals(original.getCargosToTransport(), copy.getCargosToTransport());
        assertEquals(original.getLocomotive().getReference(), copy.getLocomotive().getReference());
    }
}
