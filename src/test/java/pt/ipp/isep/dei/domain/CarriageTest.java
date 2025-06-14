package pt.ipp.isep.dei.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CarriageTest {
    
    @Test
    void testConstructorWithValidParameters() {
        Carriage carriage = new Carriage("Test Carriage", "Cargo", 1000.0, 50000.0);
        assertNotNull(carriage);
        assertEquals("Test Carriage", carriage.getName());
        assertEquals("Cargo", carriage.getCargoType());
        assertEquals(1000.0, carriage.getCapacity());
        assertEquals(50000.0, carriage.getCost());
    }

    @Test
    void testConstructorWithNullName() {
        assertThrows(IllegalArgumentException.class, () ->
            new Carriage(null, "Cargo", 1000.0, 50000.0)
        );
    }

    @Test
    void testConstructorWithEmptyName() {
        assertThrows(IllegalArgumentException.class, () ->
            new Carriage("", "Cargo", 1000.0, 50000.0)
        );
    }

    @Test
    void testConstructorWithNullCargoType() {
        assertThrows(IllegalArgumentException.class, () ->
            new Carriage("Test Carriage", null, 1000.0, 50000.0)
        );
    }

    @Test
    void testConstructorWithEmptyCargoType() {
        assertThrows(IllegalArgumentException.class, () ->
            new Carriage("Test Carriage", "", 1000.0, 50000.0)
        );
    }

    @Test
    void testConstructorWithNegativeCapacity() {
        assertThrows(IllegalArgumentException.class, () ->
            new Carriage("Test Carriage", "Cargo", -1000.0, 50000.0)
        );
    }

    @Test
    void testConstructorWithNegativeCost() {
        assertThrows(IllegalArgumentException.class, () ->
            new Carriage("Test Carriage", "Cargo", 1000.0, -50000.0)
        );
    }

    @Test
    void testGetName() {
        Carriage carriage = new Carriage("Test Carriage", "Cargo", 1000.0, 50000.0);
        assertEquals("Test Carriage", carriage.getName());
    }

    @Test
    void testGetCargoType() {
        Carriage carriage = new Carriage("Test Carriage", "Cargo", 1000.0, 50000.0);
        assertEquals("Cargo", carriage.getCargoType());
    }

    @Test
    void testGetCapacity() {
        Carriage carriage = new Carriage("Test Carriage", "Cargo", 1000.0, 50000.0);
        assertEquals(1000.0, carriage.getCapacity());
    }

    @Test
    void testGetCost() {
        Carriage carriage = new Carriage("Test Carriage", "Cargo", 1000.0, 50000.0);
        assertEquals(50000.0, carriage.getCost());
    }

    @Test
    void testSetName() {
        Carriage carriage = new Carriage("Test Carriage", "Cargo", 1000.0, 50000.0);
        carriage.setName("New Name");
        assertEquals("New Name", carriage.getName());
    }

    @Test
    void testSetNameWithNull() {
        Carriage carriage = new Carriage("Test Carriage", "Cargo", 1000.0, 50000.0);
        assertThrows(IllegalArgumentException.class, () -> carriage.setName(null));
    }

    @Test
    void testSetNameWithEmpty() {
        Carriage carriage = new Carriage("Test Carriage", "Cargo", 1000.0, 50000.0);
        assertThrows(IllegalArgumentException.class, () -> carriage.setName(""));
    }

    @Test
    void testSetCargoType() {
        Carriage carriage = new Carriage("Test Carriage", "Cargo", 1000.0, 50000.0);
        carriage.setCargoType("New Cargo Type");
        assertEquals("New Cargo Type", carriage.getCargoType());
    }

    @Test
    void testSetCargoTypeWithNull() {
        Carriage carriage = new Carriage("Test Carriage", "Cargo", 1000.0, 50000.0);
        assertThrows(IllegalArgumentException.class, () -> carriage.setCargoType(null));
    }

    @Test
    void testSetCargoTypeWithEmpty() {
        Carriage carriage = new Carriage("Test Carriage", "Cargo", 1000.0, 50000.0);
        assertThrows(IllegalArgumentException.class, () -> carriage.setCargoType(""));
    }

    @Test
    void testSetCapacity() {
        Carriage carriage = new Carriage("Test Carriage", "Cargo", 1000.0, 50000.0);
        carriage.setCapacity(2000.0);
        assertEquals(2000.0, carriage.getCapacity());
    }

    @Test
    void testSetCapacityWithNegative() {
        Carriage carriage = new Carriage("Test Carriage", "Cargo", 1000.0, 50000.0);
        assertThrows(IllegalArgumentException.class, () -> carriage.setCapacity(-1000.0));
    }

    @Test
    void testCopy() {
        Carriage original = new Carriage("Test Carriage", "Cargo", 1000.0, 50000.0);
        Carriage copy = original.copy();
        
        assertNotSame(original, copy);
        assertEquals(original.getName(), copy.getName());
        assertEquals(original.getCargoType(), copy.getCargoType());
        assertEquals(original.getCapacity(), copy.getCapacity());
        assertEquals(original.getCost(), copy.getCost());
    }

    @Test
    void testToString() {
        Carriage carriage = new Carriage("Test Carriage", "Cargo", 1000.0, 50000.0);
        String toString = carriage.toString();
        assertTrue(toString.contains("Test Carriage"));
        assertTrue(toString.contains("Cargo"));
        assertTrue(toString.contains("1000.0"));
    }
} 