package pt.ipp.isep.dei.repository;

import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.domain.City;
import pt.ipp.isep.dei.domain.Employee;
import pt.ipp.isep.dei.domain.Map;
import pt.ipp.isep.dei.repository.CityRepository;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

public class CityRepositoryTest {

    @Test
    public void testAddCity() {
        CityRepository repository = new CityRepository();
        Employee employee = new Employee("test@email.com");
        Map map = new Map("Test Map", 10, 10, employee);
        City city = new City(map, 5, 5, employee);

        Optional<City> result = repository.add(city);

        assertTrue(result.isPresent());
        assertEquals(city, result.get());
        assertEquals(1, repository.getAll().size());
    }

    @Test
    public void testAddDuplicateCity() {
        CityRepository repository = new CityRepository();
        Employee employee = new Employee("test@email.com");
        Map map = new Map("Test Map", 10, 10, employee);
        City city = new City(map, 5, 5, employee);

        repository.add(city);
        Optional<City> duplicateResult = repository.add(city);

        assertFalse(duplicateResult.isPresent());
        assertEquals(1, repository.getAll().size());
    }

    @Test
    public void testAddNullCity() {
        CityRepository repository = new CityRepository();
        Optional<City> result = repository.add(null);

        assertFalse(result.isPresent());
        assertTrue(repository.getAll().isEmpty());
    }

    @Test
    public void testContainsCity() {
        CityRepository repository = new CityRepository();
        Employee employee = new Employee("test@email.com");
        Map map = new Map("Test Map", 10, 10, employee);
        City city = new City(map, 5, 5, employee);

        repository.add(city);

        assertTrue(repository.contains(city));
    }
}