package pt.ipp.isep.dei.domain.enums;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class StationUpgradeTest {

    @Test
    void getDisplayName_ShouldReturnCorrectDisplayName() {
        // Assert
        assertEquals("Posto Telegráfico", StationUpgrade.TELEGRAPH.getDisplayName());
        assertEquals("Telefone", StationUpgrade.TELEPHONE.getDisplayName());
        assertEquals("Restaurante Pequeno", StationUpgrade.RESTAURANT_SMALL.getDisplayName());
        assertEquals("Restaurante Grande", StationUpgrade.RESTAURANT_LARGE.getDisplayName());
        assertEquals("Silo", StationUpgrade.SILO.getDisplayName());
        assertEquals("Hotel Pequeno", StationUpgrade.HOTEL_SMALL.getDisplayName());
        assertEquals("Hotel Grande", StationUpgrade.HOTEL_LARGE.getDisplayName());
        assertEquals("Alfândega", StationUpgrade.CUSTOMS.getDisplayName());
        assertEquals("Armazenamento de Líquidos", StationUpgrade.LIQUID_STORAGE.getDisplayName());
    }

    @Test
    void getYear_ShouldReturnCorrectYear() {
        // Assert
        assertEquals(1830, StationUpgrade.TELEGRAPH.getYear());
        assertEquals(1860, StationUpgrade.TELEPHONE.getYear());
        assertEquals(1850, StationUpgrade.RESTAURANT_SMALL.getYear());
        assertEquals(1850, StationUpgrade.RESTAURANT_LARGE.getYear());
        assertEquals(1870, StationUpgrade.SILO.getYear());
        assertEquals(1880, StationUpgrade.HOTEL_SMALL.getYear());
        assertEquals(1880, StationUpgrade.HOTEL_LARGE.getYear());
        assertEquals(1875, StationUpgrade.CUSTOMS.getYear());
        assertEquals(1890, StationUpgrade.LIQUID_STORAGE.getYear());
    }

    @Test
    void getMonth_ShouldReturnCorrectMonth() {
        // Assert
        for (StationUpgrade upgrade : StationUpgrade.values()) {
            assertEquals(1, upgrade.getMonth());
        }
    }

    @Test
    void getCost_ShouldReturnCorrectCost() {
        // Assert
        assertEquals(10000, StationUpgrade.TELEGRAPH.getCost());
        assertEquals(25000, StationUpgrade.TELEPHONE.getCost());
        assertEquals(5000, StationUpgrade.RESTAURANT_SMALL.getCost());
        assertEquals(20000, StationUpgrade.RESTAURANT_LARGE.getCost());
        assertEquals(15000, StationUpgrade.SILO.getCost());
        assertEquals(10000, StationUpgrade.HOTEL_SMALL.getCost());
        assertEquals(30000, StationUpgrade.HOTEL_LARGE.getCost());
        assertEquals(20000, StationUpgrade.CUSTOMS.getCost());
        assertEquals(20000, StationUpgrade.LIQUID_STORAGE.getCost());
    }

    @Test
    void getExclusiveWith_ShouldReturnCorrectExclusiveUpgrades() {
        // Assert
        assertEquals("TELEPHONE", StationUpgrade.TELEGRAPH.getExclusiveWith());
        assertNull(StationUpgrade.TELEPHONE.getExclusiveWith());
        assertNull(StationUpgrade.RESTAURANT_SMALL.getExclusiveWith());
        assertEquals("HOTEL_LARGE", StationUpgrade.RESTAURANT_LARGE.getExclusiveWith());
        assertEquals("LIQUID_STORAGE", StationUpgrade.SILO.getExclusiveWith());
        assertNull(StationUpgrade.HOTEL_SMALL.getExclusiveWith());
        assertEquals("RESTAURANT_LARGE", StationUpgrade.HOTEL_LARGE.getExclusiveWith());
        assertNull(StationUpgrade.CUSTOMS.getExclusiveWith());
        assertEquals("SILO", StationUpgrade.LIQUID_STORAGE.getExclusiveWith());
    }

    @Test
    void getReplaces_ShouldReturnCorrectReplacedUpgrades() {
        // Assert
        assertNull(StationUpgrade.TELEGRAPH.getReplaces());
        assertEquals("TELEGRAPH", StationUpgrade.TELEPHONE.getReplaces());
        assertNull(StationUpgrade.RESTAURANT_SMALL.getReplaces());
        assertNull(StationUpgrade.RESTAURANT_LARGE.getReplaces());
        assertNull(StationUpgrade.SILO.getReplaces());
        assertNull(StationUpgrade.HOTEL_SMALL.getReplaces());
        assertNull(StationUpgrade.HOTEL_LARGE.getReplaces());
        assertNull(StationUpgrade.CUSTOMS.getReplaces());
        assertNull(StationUpgrade.LIQUID_STORAGE.getReplaces());
    }

    @Test
    void values_ShouldReturnAllUpgrades() {
        // Act
        StationUpgrade[] upgrades = StationUpgrade.values();

        // Assert
        assertEquals(9, upgrades.length);
        assertTrue(containsUpgrade(upgrades, StationUpgrade.TELEGRAPH));
        assertTrue(containsUpgrade(upgrades, StationUpgrade.TELEPHONE));
        assertTrue(containsUpgrade(upgrades, StationUpgrade.RESTAURANT_SMALL));
        assertTrue(containsUpgrade(upgrades, StationUpgrade.RESTAURANT_LARGE));
        assertTrue(containsUpgrade(upgrades, StationUpgrade.SILO));
        assertTrue(containsUpgrade(upgrades, StationUpgrade.HOTEL_SMALL));
        assertTrue(containsUpgrade(upgrades, StationUpgrade.HOTEL_LARGE));
        assertTrue(containsUpgrade(upgrades, StationUpgrade.CUSTOMS));
        assertTrue(containsUpgrade(upgrades, StationUpgrade.LIQUID_STORAGE));
    }

    private boolean containsUpgrade(StationUpgrade[] upgrades, StationUpgrade upgrade) {
        for (StationUpgrade u : upgrades) {
            if (u == upgrade) {
                return true;
            }
        }
        return false;
    }
} 