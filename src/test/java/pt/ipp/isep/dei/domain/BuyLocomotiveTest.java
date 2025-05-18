package pt.ipp.isep.dei.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.repository.LocomotiveRepository;
import pt.ipp.isep.dei.repository.ScenarioRepository;

import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BuyLocomotiveTest {

    private LocomotiveRepository locomotiveRepo;
    private ScenarioRepository scenarioRepo;

    @BeforeEach
    void setUp() {
        locomotiveRepo = new LocomotiveRepository();
        scenarioRepo = new ScenarioRepository();
    }

    /**
     * Test 1: Should add a locomotive to the repository successfully
     * Verifies that a valid locomotive is cloned and stored correctly.
     */
    @Test
    void shouldAddLocomotiveSuccessfully() {
        Locomotive loco = new Locomotive("L001", "Diesel", 10000.0, 1500, 1945);
        Optional<Locomotive> result = locomotiveRepo.add(loco);

        assertTrue(result.isPresent());
        assertEquals("L001", result.get().getReference());
    }

    /**
     * Test 2: Should not add null locomotive to the repository
     * Ensures that null inputs are gracefully rejected.
     */
    @Test
    void shouldNotAddNullLocomotive() {
        Optional<Locomotive> result = locomotiveRepo.add(null);
        assertTrue(result.isEmpty());
    }

    /**
     * Test 3: Should find a locomotive by reference
     * Verifies that a locomotive can be retrieved correctly by its reference.
     */
    @Test
    void shouldFindLocomotiveByReference() {
        Locomotive loco = new Locomotive("L002", "Steam", 8000.0, 1000, 1920);
        locomotiveRepo.add(loco);

        Optional<Locomotive> found = locomotiveRepo.findByReference("L002");
        assertTrue(found.isPresent());
        assertEquals("Steam", found.get().getType());
    }



    /**
     * Test 5: Should return empty when finding non-existing scenario
     * Ensures graceful handling when the scenario name is unknown.
     */
    @Test
    void shouldReturnEmptyIfScenarioNotFound() {
        Optional<Scenario> result = scenarioRepo.findByName("Mars 2100");
        assertTrue(result.isEmpty());
    }
}
