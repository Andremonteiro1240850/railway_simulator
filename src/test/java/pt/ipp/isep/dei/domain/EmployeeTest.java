package pt.ipp.isep.dei.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EmployeeTest {
    
    @Test
    void testConstructorWithValidEmail() {
        Employee employee = new Employee("test@email.com");
        assertNotNull(employee);
        assertEquals("test@email.com", employee.getEmail());
    }

    @Test
    void testConstructorWithNullEmail() {
        assertThrows(IllegalArgumentException.class, () ->
            new Employee(null)
        );
    }

    @Test
    void testConstructorWithEmptyEmail() {
        assertThrows(IllegalArgumentException.class, () ->
            new Employee("")
        );
    }

    @Test
    void testConstructorWithInvalidEmail() {
        assertThrows(IllegalArgumentException.class, () ->
            new Employee("invalid-email")
        );
    }

    @Test
    void testGetEmail() {
        Employee employee = new Employee("test@email.com");
        assertEquals("test@email.com", employee.getEmail());
    }

    @Test
    void testEquals() {
        Employee employee1 = new Employee("test@email.com");
        Employee employee2 = new Employee("test@email.com");
        Employee employee3 = new Employee("different@email.com");
        
        assertEquals(employee1, employee2);
        assertNotEquals(employee1, employee3);
    }

    @Test
    void testHashCode() {
        Employee employee1 = new Employee("test@email.com");
        Employee employee2 = new Employee("test@email.com");
        assertEquals(employee1.hashCode(), employee2.hashCode());
    }

    @Test
    void testToString() {
        Employee employee = new Employee("test@email.com");
        String toString = employee.toString();
        assertTrue(toString.contains("test@email.com"));
    }
} 