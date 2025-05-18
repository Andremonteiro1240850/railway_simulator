package pt.ipp.isep.dei.domain;

import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.domain.City;
import pt.ipp.isep.dei.domain.Employee;
import pt.ipp.isep.dei.domain.Map;
import static org.junit.jupiter.api.Assertions.*;

public class CityTest {

    @Test
    public void testCityCreation() {
        Employee employee = new Employee("test@email.com");
        Map map = new Map("Test Map", 10, 10, employee);
        City city = new City(map, 5, 5, employee);

        assertNotNull(city);
    }

    @Test
    public void testCityEquality() {
        Employee employee = new Employee("test@email.com");
        Map map = new Map("Test Map", 10, 10, employee);

        City city1 = new City(map, 5, 5, employee);
        City city2 = new City(map, 5, 5, employee);
        City city3 = new City(map, 3, 3, employee);

        assertEquals(city1, city2);
        assertNotEquals(city1, city3);
    }

    @Test
    public void testCityCloning() {
        Employee employee = new Employee("test@email.com");
        Map map = new Map("Test Map", 10, 10, employee);
        City original = new City(map, 5, 5, employee);
        City clone = original.clone();

        assertEquals(original, clone);
        assertNotSame(original, clone);
    }

    @Test
    public void testHashCodeConsistency() {
        Employee employee = new Employee("test@email.com");
        Map map = new Map("Test Map", 10, 10, employee);
        City city1 = new City(map, 5, 5, employee);
        City city2 = new City(map, 5, 5, employee);

        assertEquals(city1.hashCode(), city2.hashCode());
    }
}