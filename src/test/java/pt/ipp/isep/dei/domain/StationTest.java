package pt.ipp.isep.dei.domain;

import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.domain.*;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class StationTest {

    @Test
    public void testStationCreationWithReference() {
        Station station = new Station("ST-123", "Main Station", "station", "NE");

        assertEquals("ST-123", station.getReference());
        assertEquals("Main Station", station.getName());
        assertEquals("station", station.getType());
        assertEquals("NE", station.getRegion());
    }

    @Test
    public void testStationCreationWithoutReference() {
        Station station = new Station("Main Station", "terminal", null);

        assertNotNull(station.getReference());
        assertTrue(station.getReference().startsWith("ST-"));
        assertEquals("terminal", station.getType());
        assertNull(station.getRegion());
    }

    @Test
    public void testStationEquality() {
        Station station1 = new Station("ST-123", "Station A", "station", "NE");
        Station station2 = new Station("ST-123", "Station B", "station", "NW");
        Station station3 = new Station("ST-456", "Station A", "station", "NE");

        assertEquals(station1, station2); // Mesma referência
        assertNotEquals(station1, station3); // Referências diferentes
    }

    @Test
    public void testAddUpgrade() {
        Station station = new Station("ST-123", "Main Station", "station", "NE");
        StationUpgrade upgrade = new StationUpgrade("signal", "advanced");

        station.addUpgrade(upgrade);
        List<StationUpgrade> upgrades = station.getUpgrades();

        assertEquals(1, upgrades.size());
        assertEquals(upgrade, upgrades.get(0));
    }

    @Test
    public void testStationCloning() {
        Station original = new Station("ST-123", "Main Station", "station", "NE");
        original.addUpgrade(new StationUpgrade("signal", "basic"));

        Station clone = original.clone();

        assertEquals(original, clone);
        assertNotSame(original, clone);
        assertEquals(original.getUpgrades().size(), clone.getUpgrades().size());
    }
}