package pt.ipp.isep.dei.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.repository.UpgradeBuildingsRepository;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class UpgradeStationTest {

    private Station station;
    private UpgradeBuildingsRepository repo;

    @BeforeEach
    void setUp() {
        station = new Station("ST-123", "Main Station", "station", "North");
        repo = new UpgradeBuildingsRepository();
    }

    /**
     * Test 1: Should include 'telephone' in the list of available upgrade types
     * Ensures that the available upgrades include 'telephone' as a valid option.
     */
    @Test
    void ensureTelephoneIsInAvailableUpgradeTypes() {
        List<String> upgrades = repo.getAllUpgradeTypes();
        assertTrue(upgrades.contains("telephone"));
    }

    /**
     * Test 2: Should apply upgrade to station successfully
     * Confirms that a valid upgrade can be added to a station and is stored.
     */
    @Test
    void ensureUpgradeIsAddedToStation() {
        StationUpgrade upgrade = new StationUpgrade("telephone", null);
        station.addUpgrade(upgrade);

        List<StationUpgrade> upgrades = station.getUpgrades();
        assertEquals(1, upgrades.size());
        assertEquals("telephone", upgrades.get(0).getType());
    }
}
