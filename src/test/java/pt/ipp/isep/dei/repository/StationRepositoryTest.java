package pt.ipp.isep.dei.repository;

import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.domain.*;
import pt.ipp.isep.dei.repository.StationRepository;

import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

public class StationRepositoryTest {

    @Test
    public void testAddStation() {
        StationRepository repo = new StationRepository();
        Station station = new Station("ST-123", "Main Station", "station", "NE");

        Optional<Station> result = repo.add(station);

        assertTrue(result.isPresent());
        assertEquals(station, result.get());
        assertEquals(1, repo.getAll().size());
    }

    @Test
    public void testAddDuplicateStation() {
        StationRepository repo = new StationRepository();
        Station station = new Station("ST-123", "Main Station", "station", "NE");

        repo.add(station);
        Optional<Station> duplicateResult = repo.add(station);

        assertFalse(duplicateResult.isPresent());
        assertEquals(1, repo.getAll().size());
    }

    @Test
    public void testFindByReference() {
        StationRepository repo = new StationRepository();
        Station station1 = new Station("ST-123", "Station A", "station", "NE");
        Station station2 = new Station("ST-456", "Station B", "terminal", null);

        repo.add(station1);
        repo.add(station2);

        Optional<Station> found = repo.findByReference("ST-123");
        Optional<Station> notFound = repo.findByReference("ST-999");

        assertTrue(found.isPresent());
        assertEquals("Station A", found.get().getName());
        assertFalse(notFound.isPresent());
    }

    @Test
    public void testAddUpgradeToStation() {
        StationRepository repo = new StationRepository();
        Station station = new Station("ST-123", "Main Station", "station", "NE");
        repo.add(station);

        StationUpgrade upgrade = new StationUpgrade("signal", "advanced");
        boolean result = repo.addUpgradeToStation("ST-123", upgrade);

        assertTrue(result);
        List<StationUpgrade> upgrades = repo.getUpgradesForStation("ST-123");
        assertEquals(1, upgrades.size());
        assertEquals(upgrade, upgrades.get(0));
    }
}