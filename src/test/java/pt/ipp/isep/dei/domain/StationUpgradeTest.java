package pt.ipp.isep.dei.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import pt.ipp.isep.dei.domain.enums.Buildings;

class StationUpgradeTest {

    @Test
    void testConstructorWithValidParameters() {
        StationUpgrade upgrade = new StationUpgrade("telegraph", null);
        assertNotNull(upgrade);
        assertEquals("telegraph", upgrade.getType());
        assertNull(upgrade.getSubType());
        assertEquals(10000, upgrade.getPrice());
        assertEquals("+10% receita de passageiros", upgrade.getBonus());
    }

    @Test
    void testConstructorWithSubType() {
        StationUpgrade upgrade = new StationUpgrade("cafe", "small");
        assertNotNull(upgrade);
        assertEquals("cafe", upgrade.getType());
        assertEquals("small", upgrade.getSubType());
        assertEquals(8000, upgrade.getPrice());
        assertEquals("+5% receita de passageiros", upgrade.getBonus());
    }

    @Test
    void testConstructorWithNullType() {
        assertThrows(IllegalArgumentException.class, () ->
            new StationUpgrade(null, null)
        );
    }

    @Test
    void testConstructorWithInvalidType() {
        assertThrows(IllegalArgumentException.class, () ->
            new StationUpgrade("invalid_type", null)
        );
    }

    @Test
    void testConstructorWithInvalidSubType() {
        assertThrows(IllegalArgumentException.class, () ->
            new StationUpgrade("cafe", "invalid_subtype")
        );
    }

    @Test
    void testGetPrice() {
        StationUpgrade upgrade = new StationUpgrade("telegraph", null);
        assertEquals(10000, upgrade.getPrice());
        
        StationUpgrade upgradeWithSubType = new StationUpgrade("cafe", "small");
        assertEquals(8000, upgradeWithSubType.getPrice());
    }

    @Test
    void testGetBonus() {
        StationUpgrade upgrade = new StationUpgrade("telegraph", null);
        assertEquals("+10% receita de passageiros", upgrade.getBonus());
        
        StationUpgrade upgradeWithSubType = new StationUpgrade("cafe", "small");
        assertEquals("+5% receita de passageiros", upgradeWithSubType.getBonus());
    }

    @Test
    void testToString() {
        StationUpgrade upgrade = new StationUpgrade("telegraph", null);
        assertEquals("telegraph", upgrade.toString());
        
        StationUpgrade upgradeWithSubType = new StationUpgrade("cafe", "small");
        assertEquals("cafe (small)", upgradeWithSubType.toString());
    }

    @Test
    void testGetBuilding() {
        StationUpgrade upgrade = new StationUpgrade("telegraph", null);
        assertEquals(Buildings.TELEGRAPH, upgrade.getBuilding());
        
        StationUpgrade upgradeWithSubType = new StationUpgrade("cafe", "small");
        assertEquals(Buildings.CAFE_SMALL, upgradeWithSubType.getBuilding());
    }
} 