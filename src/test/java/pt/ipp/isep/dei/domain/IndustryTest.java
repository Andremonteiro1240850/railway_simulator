package pt.ipp.isep.dei.domain;

import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.domain.Employee;
import pt.ipp.isep.dei.domain.Industry;
import pt.ipp.isep.dei.domain.Map;
import static org.junit.jupiter.api.Assertions.*;

public class IndustryTest {

    @Test
    public void testIndustryCreation() {
        Employee employee = new Employee("test@email.com");
        Map map = new Map("Test Map", 10, 10, employee);
        Industry industry = new Industry(map, 5, 5, employee);

        assertNotNull(industry);
    }

    @Test
    public void testIndustryEquality() {
        Employee employee = new Employee("test@email.com");
        Map map = new Map("Test Map", 10, 10, employee);

        Industry industry1 = new Industry(map, 5, 5, employee);
        Industry industry2 = new Industry(map, 5, 5, employee);
        Industry industry3 = new Industry(map, 3, 3, employee);

        assertEquals(industry1, industry2);
        assertNotEquals(industry1, industry3);
    }

    @Test
    public void testIndustryCloning() {
        Employee employee = new Employee("test@email.com");
        Map map = new Map("Test Map", 10, 10, employee);
        Industry original = new Industry(map, 5, 5, employee);
        Industry clone = original.clone();

        assertEquals(original, clone);
        assertNotSame(original, clone);
    }

    @Test
    public void testHashCodeConsistency() {
        Employee employee = new Employee("test@email.com");
        Map map = new Map("Test Map", 10, 10, employee);
        Industry industry1 = new Industry(map, 5, 5, employee);
        Industry industry2 = new Industry(map, 5, 5, employee);

        assertEquals(industry1.hashCode(), industry2.hashCode());
    }
}