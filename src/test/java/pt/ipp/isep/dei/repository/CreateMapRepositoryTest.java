package pt.ipp.isep.dei.repository;

import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.domain.Employee;
import pt.ipp.isep.dei.domain.Map;
import pt.ipp.isep.dei.repository.CreateMapRepository;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

public class CreateMapRepositoryTest {

    @Test
    public void testAddMap() {
        CreateMapRepository repository = new CreateMapRepository();
        Employee employee = new Employee("test@email.com");
        Map map = new Map("Test Map", 10, 20, employee);

        Optional<Map> result = repository.add(map);

        assertTrue(result.isPresent());
        assertEquals(map, result.get());
        assertEquals(1, repository.getAll().size());
    }

    @Test
    public void testAddDuplicateMap() {
        CreateMapRepository repository = new CreateMapRepository();
        Employee employee = new Employee("test@email.com");
        Map map = new Map("Test Map", 10, 20, employee);

        repository.add(map);
        Optional<Map> duplicateResult = repository.add(map);

        assertFalse(duplicateResult.isPresent());
        assertEquals(1, repository.getAll().size());
    }

    @Test
    public void testAddNullMap() {
        CreateMapRepository repository = new CreateMapRepository();
        Optional<Map> result = repository.add(null);

        assertFalse(result.isPresent());
        assertTrue(repository.getAll().isEmpty());
    }

    @Test
    public void testContainsMap() {
        CreateMapRepository repository = new CreateMapRepository();
        Employee employee = new Employee("test@email.com");
        Map map = new Map("Test Map", 10, 20, employee);

        repository.add(map);

        assertTrue(repository.contains(map));
        assertFalse(repository.contains(new Map("Other Map", 5, 5, employee)));
    }
}