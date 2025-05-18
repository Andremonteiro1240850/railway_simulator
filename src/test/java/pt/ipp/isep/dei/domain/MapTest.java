package pt.ipp.isep.dei.domain;

import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.domain.Employee;
import pt.ipp.isep.dei.domain.Map;
import static org.junit.jupiter.api.Assertions.*;

public class MapTest {

    @Test
    public void testMapCreation() {
        Employee employee = new Employee("test@email.com");
        Map map = new Map("Test Map", 10, 20, employee);

        assertEquals("Test Map", map.getName());
        assertEquals(10, map.getDimensionX());
        assertEquals(20, map.getDimensionY());
    }

    @Test
    public void testMapEquality() {
        Employee employee = new Employee("test@email.com");
        Map map1 = new Map("Test Map", 10, 20, employee);
        Map map2 = new Map("Test Map", 10, 20, employee);
        Map map3 = new Map("Different Map", 10, 20, employee);

        assertEquals(map1, map2);
        assertNotEquals(map1, map3);
    }

    @Test
    public void testMapCloning() {
        Employee employee = new Employee("test@email.com");
        Map original = new Map("Test Map", 10, 20, employee);
        Map clone = original.clone();

        assertEquals(original, clone);
        assertNotSame(original, clone);
    }

    @Test
    public void testHashCodeConsistency() {
        Employee employee = new Employee("test@email.com");
        Map map1 = new Map("Test Map", 10, 20, employee);
        Map map2 = new Map("Test Map", 10, 20, employee);

        assertEquals(map1.hashCode(), map2.hashCode());
    }
}