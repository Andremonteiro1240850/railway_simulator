package pt.ipp.isep.dei.domain;

import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.domain.*;
import pt.ipp.isep.dei.repository.ScenarioRepository;

import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

public class ScenarioRepositoryTest {

    @Test
    public void testAddScenario() {
        ScenarioRepository repo = new ScenarioRepository();
        Map map = new Map("Test Map", 10, 10, new Employee("test@email.com"));
        Scenario scenario = new Scenario("Test Scenario", map, 1900, 1950);

        Optional<Scenario> result = repo.add(scenario);

        assertTrue(result.isPresent());
        assertEquals(scenario.getName(), result.get().getName());
        assertEquals(1, repo.getAll().size());
    }

    @Test
    public void testAddNullScenario() {
        ScenarioRepository repo = new ScenarioRepository();
        Optional<Scenario> result = repo.add(null);

        assertFalse(result.isPresent());
        assertTrue(repo.getAll().isEmpty());
    }

    @Test
    public void testFindByName() {
        ScenarioRepository repo = new ScenarioRepository();
        Map map = new Map("Test Map", 10, 10, new Employee("test@email.com"));
        Scenario scenario = new Scenario("Test Scenario", map, 1900, 1950);
        repo.add(scenario);

        Optional<Scenario> found = repo.findByName("Test Scenario");
        Optional<Scenario> notFound = repo.findByName("Nonexistent");

        assertTrue(found.isPresent());
        assertEquals(scenario.getName(), found.get().getName());
        assertFalse(notFound.isPresent());
    }

    @Test
    public void testGetAllScenarios() {
        ScenarioRepository repo = new ScenarioRepository();
        Map map = new Map("Test Map", 10, 10, new Employee("test@email.com"));

        repo.add(new Scenario("Scenario 1", map, 1900, 1950));
        repo.add(new Scenario("Scenario 2", map, 1950, 2000));

        List<Scenario> allScenarios = repo.getAll();
        assertEquals(2, allScenarios.size());
        assertNotSame(allScenarios.get(0), allScenarios.get(1));
    }
}