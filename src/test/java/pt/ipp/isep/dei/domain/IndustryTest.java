package pt.ipp.isep.dei.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.domain.enums.IndustryType;
import pt.ipp.isep.dei.domain.enums.ScaleUnit;
import pt.ipp.isep.dei.repository.Repositories;
import static org.junit.jupiter.api.Assertions.*;

class IndustryTest {
    private Industry industry;
    private Map map;
    private IndustryType type;
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
        type = IndustryType.PRIMARY;
        industry = new Industry(type, map, 5, 5);
    }

    @Test
    void testConstructorWithValidParameters() {
        assertNotNull(industry);
        assertEquals(type, industry.getType());
        assertEquals(5, industry.getPositionX());
        assertEquals(5, industry.getPositionY());
        assertEquals(map, industry.getMap());
    }

    @Test
    void testConstructorWithNullType() {
        assertThrows(IllegalArgumentException.class, () ->
            new Industry(null, map, 5, 5)
        );
    }

    @Test
    void testConstructorWithNullMap() {
        assertThrows(IllegalArgumentException.class, () ->
            new Industry(type, null, 5, 5)
        );
    }

    @Test
    void testGetType() {
        assertEquals(type, industry.getType());
    }

    @Test
    void testGetPositionX() {
        assertEquals(5, industry.getPositionX());
    }

    @Test
    void testGetPositionY() {
        assertEquals(5, industry.getPositionY());
    }

    @Test
    void testGetMap() {
        assertEquals(map, industry.getMap());
    }

    @Test
    void testEquals() {
        Industry sameIndustry = new Industry(type, map, 5, 5);
        Industry differentType = new Industry(IndustryType.TRANSFORMING, map, 5, 5);
        Industry differentPosition = new Industry(type, map, 3, 3);
        Industry differentMap = new Industry(type, new Map("Different Map", 10, 10, ScaleUnit.KM, organization), 5, 5);
        
        assertEquals(industry, sameIndustry);
        assertNotEquals(industry, differentType);
        assertNotEquals(industry, differentPosition);
        assertNotEquals(industry, differentMap);
    }

    @Test
    void testHashCode() {
        Industry sameIndustry = new Industry(type, map, 5, 5);
        assertEquals(industry.hashCode(), sameIndustry.hashCode());
    }

    @Test
    void testToString() {
        String toString = industry.toString();
        assertTrue(toString.contains(type.toString()));
        assertTrue(toString.contains("positionX=5"));
        assertTrue(toString.contains("positionY=5"));
    }
} 