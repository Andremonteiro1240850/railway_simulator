package pt.ipp.isep.dei.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.domain.enums.LocomotiveType;
import static org.junit.jupiter.api.Assertions.*;

class LocomotiveTest {
    private Locomotive locomotive;
    private final String reference = "Test Locomotive";
    private final LocomotiveType type = LocomotiveType.STEAM;
    private final double purchasePrice = 100000.0;
    private final double maintenanceCostPerYear = 5000.0;
    private final int power = 1000;
    private final double acceleration = 2.5;
    private final int startYear = 1900;
    private final double fuelCost = 50.0;

    @BeforeEach
    void setUp() {
        locomotive = new Locomotive(reference, type, purchasePrice, maintenanceCostPerYear,
                power, acceleration, startYear, fuelCost);
    }

    @Test
    void testConstructorWithValidParameters() {
        assertNotNull(locomotive);
        assertEquals(reference, locomotive.getReference());
        assertEquals(type, locomotive.getType());
        assertEquals(purchasePrice, locomotive.getPurchasePrice());
        assertEquals(maintenanceCostPerYear, locomotive.getMaintenanceCostPerYear());
        assertEquals(power, locomotive.getPower());
        assertEquals(acceleration, locomotive.getAcceleration());
        assertEquals(startYear, locomotive.getStartYear());
        assertEquals(fuelCost, locomotive.getFuelCost());
    }

    @Test
    void testConstructorWithNullReference() {
        assertThrows(IllegalArgumentException.class, () ->
            new Locomotive(null, type, purchasePrice, maintenanceCostPerYear,
                    power, acceleration, startYear, fuelCost)
        );
    }

    @Test
    void testConstructorWithEmptyReference() {
        assertThrows(IllegalArgumentException.class, () ->
            new Locomotive("", type, purchasePrice, maintenanceCostPerYear,
                    power, acceleration, startYear, fuelCost)
        );
    }

    @Test
    void testConstructorWithNullType() {
        assertThrows(IllegalArgumentException.class, () ->
            new Locomotive(reference, null, purchasePrice, maintenanceCostPerYear,
                    power, acceleration, startYear, fuelCost)
        );
    }

    @Test
    void testConstructorWithNegativePurchasePrice() {
        assertThrows(IllegalArgumentException.class, () ->
            new Locomotive(reference, type, -1.0, maintenanceCostPerYear,
                    power, acceleration, startYear, fuelCost)
        );
    }

    @Test
    void testConstructorWithNegativeMaintenanceCost() {
        assertThrows(IllegalArgumentException.class, () ->
            new Locomotive(reference, type, purchasePrice, -1.0,
                    power, acceleration, startYear, fuelCost)
        );
    }

    @Test
    void testConstructorWithNegativePower() {
        assertThrows(IllegalArgumentException.class, () ->
            new Locomotive(reference, type, purchasePrice, maintenanceCostPerYear,
                    -1, acceleration, startYear, fuelCost)
        );
    }

    @Test
    void testConstructorWithNegativeAcceleration() {
        assertThrows(IllegalArgumentException.class, () ->
            new Locomotive(reference, type, purchasePrice, maintenanceCostPerYear,
                    power, -1.0, startYear, fuelCost)
        );
    }

    @Test
    void testConstructorWithNegativeStartYear() {
        assertThrows(IllegalArgumentException.class, () ->
            new Locomotive(reference, type, purchasePrice, maintenanceCostPerYear,
                    power, acceleration, -1, fuelCost)
        );
    }

    @Test
    void testConstructorWithNegativeFuelCost() {
        assertThrows(IllegalArgumentException.class, () ->
            new Locomotive(reference, type, purchasePrice, maintenanceCostPerYear,
                    power, acceleration, startYear, -1.0)
        );
    }

    @Test
    void testGetReference() {
        assertEquals(reference, locomotive.getReference());
    }

    @Test
    void testGetType() {
        assertEquals(type, locomotive.getType());
    }

    @Test
    void testGetPurchasePrice() {
        assertEquals(purchasePrice, locomotive.getPurchasePrice());
    }

    @Test
    void testGetMaintenanceCostPerYear() {
        assertEquals(maintenanceCostPerYear, locomotive.getMaintenanceCostPerYear());
    }

    @Test
    void testGetPower() {
        assertEquals(power, locomotive.getPower());
    }

    @Test
    void testGetAcceleration() {
        assertEquals(acceleration, locomotive.getAcceleration());
    }

    @Test
    void testGetStartYear() {
        assertEquals(startYear, locomotive.getStartYear());
    }

    @Test
    void testGetFuelCost() {
        assertEquals(fuelCost, locomotive.getFuelCost());
    }

    @Test
    void testGetCost() {
        assertEquals(purchasePrice, locomotive.getCost());
    }

    @Test
    void testCopy() {
        Locomotive copy = locomotive.copy();
        assertNotSame(locomotive, copy);
        assertEquals(locomotive.getReference(), copy.getReference());
        assertEquals(locomotive.getType(), copy.getType());
        assertEquals(locomotive.getPurchasePrice(), copy.getPurchasePrice());
        assertEquals(locomotive.getMaintenanceCostPerYear(), copy.getMaintenanceCostPerYear());
        assertEquals(locomotive.getPower(), copy.getPower());
        assertEquals(locomotive.getAcceleration(), copy.getAcceleration());
        assertEquals(locomotive.getStartYear(), copy.getStartYear());
        assertEquals(locomotive.getFuelCost(), copy.getFuelCost());
    }

    @Test
    void testToString() {
        String toString = locomotive.toString();
        assertTrue(toString.contains(reference));
        assertTrue(toString.contains(type.toString()));
        assertTrue(toString.contains(String.valueOf(power)));
        assertTrue(toString.contains(String.valueOf(startYear)));
    }

    @Test
    void testConstructorWithStringParameters() {
        Locomotive loco = new Locomotive("Test", "Steam", 100000, 5000, 100, "Average", 1900);
        assertNotNull(loco);
        assertEquals("Test", loco.getReference());
        assertEquals(LocomotiveType.STEAM, loco.getType());
        assertEquals(100000, loco.getPurchasePrice());
        assertEquals(5000, loco.getMaintenanceCostPerYear());
        assertEquals(1000, loco.getPower()); // topSpeed * 10
        assertEquals(2.5, loco.getAcceleration()); // "Average" converts to 2.5
        assertEquals(1900, loco.getStartYear());
        assertEquals(0.0, loco.getFuelCost()); // Default value for this constructor
    }

    @Test
    void testConstructorWithInvalidFuelType() {
        assertThrows(IllegalArgumentException.class, () ->
            new Locomotive("Test", "Invalid", 100000, 5000, 100, "Average", 1900)
        );
    }

    @Test
    void testConstructorWithInvalidAccelerationRating() {
        Locomotive loco = new Locomotive("Test", "Steam", 100000, 5000, 100, "Invalid", 1900);
        assertEquals(2.0, loco.getAcceleration()); // Default value for invalid rating
    }
} 