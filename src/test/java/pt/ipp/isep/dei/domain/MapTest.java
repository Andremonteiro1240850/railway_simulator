package pt.ipp.isep.dei.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import pt.ipp.isep.dei.domain.enums.ScaleUnit;
import pt.ipp.isep.dei.repository.Repositories;
import java.util.List;
import java.util.Optional;

class MapTest {
    private Map map;
    private Organization organization;
    private Employee employee;

    @BeforeEach
    void setUp() {
        employee = new Employee("test@email.com");
        organization = new Organization("Test Org", 
            Repositories.getInstance().getMapRepository(),
            Repositories.getInstance().getCityRepository(),
            Repositories.getInstance().getIndustryRepository(),
            Repositories.getInstance().getStationRepository());
        map = new Map("Test Map", 10, 10, ScaleUnit.KM, organization);
    }

    @Test
    void testConstructorWithValidParameters() {
        assertNotNull(map);
        assertEquals("Test Map", map.getName());
        assertEquals(10, map.getWidth());
        assertEquals(10, map.getHeight());
        assertEquals(ScaleUnit.KM, map.getScale());
        assertEquals(organization, map.getOrganization());
    }

    @Test
    void testConstructorWithNullName() {
        assertThrows(IllegalArgumentException.class, () ->
            new Map(null, 10, 10, ScaleUnit.KM, organization)
        );
    }

    @Test
    void testConstructorWithEmptyName() {
        assertThrows(IllegalArgumentException.class, () ->
            new Map("", 10, 10, ScaleUnit.KM, organization)
        );
    }

    @Test
    void testConstructorWithInvalidDimensions() {
        assertThrows(IllegalArgumentException.class, () ->
            new Map("Test Map", 0, 10, ScaleUnit.KM, organization)
        );
        assertThrows(IllegalArgumentException.class, () ->
            new Map("Test Map", 10, 0, ScaleUnit.KM, organization)
        );
        assertThrows(IllegalArgumentException.class, () ->
            new Map("Test Map", -1, 10, ScaleUnit.KM, organization)
        );
    }

    @Test
    void testConstructorWithNullScale() {
        assertThrows(IllegalArgumentException.class, () ->
            new Map("Test Map", 10, 10, null, organization)
        );
    }

    @Test
    void testConstructorWithNullOrganization() {
        assertThrows(IllegalArgumentException.class, () ->
            new Map("Test Map", 10, 10, ScaleUnit.KM, null)
        );
    }

    @Test
    void testIsOccupied() {
        assertFalse(map.isOccupied(5, 5));
        assertThrows(IllegalArgumentException.class, () ->
            map.isOccupied(-1, 5)
        );
        assertThrows(IllegalArgumentException.class, () ->
            map.isOccupied(5, -1)
        );
        assertThrows(IllegalArgumentException.class, () ->
            map.isOccupied(10, 5)
        );
        assertThrows(IllegalArgumentException.class, () ->
            map.isOccupied(5, 10)
        );
    }

    @Test
    void testAddCity() {
        List<int[]> houseBlocks = List.of(new int[]{1, 1}, new int[]{1, 2});
        Optional<City> city = map.addCity("Test City", 5, 5, houseBlocks, employee);
        
        assertTrue(city.isPresent());
        assertEquals("Test City", city.get().getName());
        assertTrue(map.isOccupied(5, 5));
        assertTrue(map.isOccupied(1, 1));
        assertTrue(map.isOccupied(1, 2));
    }

    @Test
    void testAddCityToOccupiedPosition() {
        List<int[]> houseBlocks = List.of(new int[]{1, 1}, new int[]{1, 2});
        map.addCity("Test City", 5, 5, houseBlocks, employee);
        
        Optional<City> secondCity = map.addCity("Second City", 5, 5, houseBlocks, employee);
        assertFalse(secondCity.isPresent());
    }

    @Test
    void testGetObjectsAt() {
        List<int[]> houseBlocks = List.of(new int[]{1, 1}, new int[]{1, 2});
        map.addCity("Test City", 5, 5, houseBlocks, employee);
        
        List<Object> objects = map.getObjectsAt(5, 5);
        assertEquals(1, objects.size());
        assertTrue(objects.get(0) instanceof City);
        
        List<Object> emptyObjects = map.getObjectsAt(0, 0);
        assertTrue(emptyObjects.isEmpty());
    }

    @Test
    void testEquals() {
        Map sameMap = new Map("Test Map", 10, 10, ScaleUnit.KM, organization);
        Map differentMap = new Map("Different Map", 10, 10, ScaleUnit.KM, organization);
        
        assertEquals(map, sameMap);
        assertNotEquals(map, differentMap);
    }

    @Test
    void testHashCode() {
        Map sameMap = new Map("Test Map", 10, 10, ScaleUnit.KM, organization);
        assertEquals(map.hashCode(), sameMap.hashCode());
    }

    @Test
    void testToString() {
        String toString = map.toString();
        assertTrue(toString.contains("Test Map"));
        assertTrue(toString.contains("10"));
        assertTrue(toString.contains("KM"));
    }
} 