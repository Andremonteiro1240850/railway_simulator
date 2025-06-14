package pt.ipp.isep.dei.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import pt.ipp.isep.dei.domain.enums.ScaleUnit;
import pt.ipp.isep.dei.repository.Repositories;

class CityTest {
    private Map map;
    private Employee employee;
    private List<int[]> houseBlocks;
    private City city;
    private Organization organization;

    @BeforeEach
    void setUp() {
        employee = new Employee("test@email.com");
        organization = new Organization("Test Org", 
            Repositories.getInstance().getMapRepository(),
            Repositories.getInstance().getCityRepository(),
            Repositories.getInstance().getIndustryRepository(),
            Repositories.getInstance().getStationRepository());
        map = new Map("Test Map", 10, 10, ScaleUnit.KM, organization);
        houseBlocks = new ArrayList<>();
        houseBlocks.add(new int[]{1, 1});
        houseBlocks.add(new int[]{1, 2});
        city = new City(map, "Test City", 5, 5, houseBlocks, employee);
    }

    @Test
    void testConstructorWithValidParameters() {
        assertNotNull(city);
        assertEquals("Test City", city.getName());
        assertEquals(5, city.getX());
        assertEquals(5, city.getY());
        assertEquals(2, city.getHouseBlocks().size());
    }

    @Test
    void testConstructorWithNullMap() {
        assertThrows(IllegalArgumentException.class, () ->
            new City(null, "Test City", 5, 5, houseBlocks, employee)
        );
    }

    @Test
    void testConstructorWithEmptyName() {
        assertThrows(IllegalArgumentException.class, () ->
            new City(map, "", 5, 5, houseBlocks, employee)
        );
    }

    @Test
    void testConstructorWithInvalidName() {
        assertThrows(IllegalArgumentException.class, () ->
            new City(map, "Test123", 5, 5, houseBlocks, employee)
        );
    }

    @Test
    void testConstructorWithNullHouseBlocks() {
        assertThrows(IllegalArgumentException.class, () ->
            new City(map, "Test City", 5, 5, null, employee)
        );
    }

    @Test
    void testConstructorWithEmptyHouseBlocks() {
        assertThrows(IllegalArgumentException.class, () ->
            new City(map, "Test City", 5, 5, new ArrayList<>(), employee)
        );
    }

    @Test
    void testConstructorWithNullEmployee() {
        assertThrows(IllegalArgumentException.class, () ->
            new City(map, "Test City", 5, 5, houseBlocks, null)
        );
    }

    @Test
    void testGetHouseBlocksReturnsDefensiveCopy() {
        List<int[]> blocks = city.getHouseBlocks();
        blocks.add(new int[]{2, 2}); // Try to modify the returned list
        
        assertEquals(2, city.getHouseBlocks().size()); // Original list should be unchanged
    }

    @Test
    void testContainsBlock() {
        assertTrue(city.containsBlock(1, 1));
        assertTrue(city.containsBlock(1, 2));
        assertFalse(city.containsBlock(2, 2));
    }

    @Test
    void testEquals() {
        City sameCity = new City(map, "Test City", 5, 5, houseBlocks, employee);
        City differentCity = new City(map, "Different City", 3, 3, houseBlocks, employee);
        
        assertEquals(city, sameCity);
        assertNotEquals(city, differentCity);
    }

    @Test
    void testHashCode() {
        City sameCity = new City(map, "Test City", 5, 5, houseBlocks, employee);
        assertEquals(city.hashCode(), sameCity.hashCode());
    }

    @Test
    void testClone() {
        City clonedCity = city.clone();
        
        assertNotSame(city, clonedCity);
        assertEquals(city.getName(), clonedCity.getName());
        assertEquals(city.getX(), clonedCity.getX());
        assertEquals(city.getY(), clonedCity.getY());
        assertEquals(city.getHouseBlocks().size(), clonedCity.getHouseBlocks().size());
    }
}