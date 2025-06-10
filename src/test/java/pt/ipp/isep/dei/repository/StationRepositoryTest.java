package pt.ipp.isep.dei.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.domain.Station;
import pt.ipp.isep.dei.domain.StationUpgrade;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;


class StationRepositoryTest {

    private StationRepository repo;
    private Station station1;
    private Station station2;

    @BeforeEach
    void setUp() {
        repo = new StationRepository();
        station1 = new Station("ST-001", "Central", "station", "North");
        station2 = new Station("ST-002", "East", "depot", null);
    }

    @Test
    void ensureStationIsAddedSuccessfully() {
        Optional<Station> result = repo.add(station1);
        assertTrue(result.isPresent());
        assertEquals(1, repo.getAll().size());
    }

    @Test
    void ensureDuplicateReferenceStationIsNotAdded() {
        repo.add(station1);
        Station duplicate = new Station("ST-001", "Copy", "station", "South");
        Optional<Station> result = repo.add(duplicate);
        assertTrue(result.isEmpty());
        assertEquals(1, repo.getAll().size());
    }

    @Test
    void ensureStationIsFoundByReference() {
        repo.add(station2);
        Optional<Station> result = repo.findByReference("ST-002");
        assertTrue(result.isPresent());
        assertEquals("East", result.get().getName());
    }

    @Test
    void ensureUpgradeIsAddedToStation() {
        repo.add(station1);
        StationUpgrade upgrade = new StationUpgrade("Telegraph", null);
        boolean success = repo.addUpgradeToStation("ST-001", upgrade);
        assertTrue(success);
        List<StationUpgrade> upgrades = repo.getUpgradesForStation("ST-001");
        assertFalse(upgrades.isEmpty());
        assertEquals("Telegraph", upgrades.get(0).getType());
    }

}
