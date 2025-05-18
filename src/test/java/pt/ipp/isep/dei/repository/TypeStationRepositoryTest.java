package pt.ipp.isep.dei.repository;

import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.repository.TypeStationRepository;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class TypeStationRepositoryTest {

    @Test
    public void testGetAllStationTypes() {
        TypeStationRepository repo = new TypeStationRepository();
        List<String> types = repo.getAllStationTypes();

        assertEquals(3, types.size());
        assertTrue(types.contains("depot"));
        assertTrue(types.contains("terminal"));
        assertTrue(types.contains("station"));
    }

    @Test
    public void testGetAllStationRegions() {
        TypeStationRepository repo = new TypeStationRepository();
        List<String> regions = repo.getAllStationRegions();

        assertEquals(4, regions.size());
        assertTrue(regions.contains("NE"));
        assertTrue(regions.contains("NW"));
        assertTrue(regions.contains("SE"));
        assertTrue(regions.contains("SW"));
    }

    @Test
    public void testIsValidStationType() {
        TypeStationRepository repo = new TypeStationRepository();

        assertTrue(repo.isValidStationType("depot"));
        assertTrue(repo.isValidStationType("STATION")); // Test case insensitivity
        assertFalse(repo.isValidStationType("invalid"));
    }

    @Test
    public void testIsValidStationRegion() {
        TypeStationRepository repo = new TypeStationRepository();

        assertTrue(repo.isValidStationRegion("NE"));
        assertTrue(repo.isValidStationRegion("se")); // Test case insensitivity
        assertFalse(repo.isValidStationRegion("XX"));
    }
}