package pt.ipp.isep.dei.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import pt.ipp.isep.dei.domain.enums.Buildings;
import pt.ipp.isep.dei.domain.enums.StationTypes;
import pt.ipp.isep.dei.domain.enums.ScaleUnit;
import pt.ipp.isep.dei.domain.enums.StationUpgrade;
import pt.ipp.isep.dei.repository.*;

class StationTest {
    private Station station;
    private String name;
    private String type;
    private String region;
    private String scenario;
    private int area;
    private int x;
    private int y;

    @BeforeEach
    void setUp() {
        name = "Test Station";
        type = StationTypes.STATION.getType();
        region = "Test Region";
        scenario = "Test Scenario";
        area = StationTypes.STATION.getArea();
        x = 5;
        y = 5;
        station = new Station(name, type, region, scenario, area, x, y);
    }

    @Test
    void testConstructor() {
        assertNotNull(station);
        assertEquals(name, station.getName());
        assertEquals(type, station.getType());
        assertEquals(region, station.getRegion());
        assertEquals(scenario, station.getScenario());
        assertEquals(area, station.getArea());
        assertEquals(x, station.getX());
        assertEquals(y, station.getY());
        assertNotNull(station.getReference());
        assertTrue(station.getReference().startsWith("ST-"));
    }

    @Test
    void testConstructorWithReference() {
        String reference = "ST-123";
        Station stationWithRef = new Station(reference, name, type, region, scenario, area, x, y);
        assertEquals(reference, stationWithRef.getReference());
    }

    @Test
    void testGetAvailableCargos() {
        List<String> cargos = station.getAvailableCargos();
        assertNotNull(cargos);
        assertFalse(cargos.isEmpty());
        assertTrue(cargos.contains("passengers"));
        assertTrue(cargos.contains("mail"));
        assertTrue(cargos.contains("food"));
        assertTrue(cargos.contains("textiles"));
        assertTrue(cargos.contains("coal"));
        assertTrue(cargos.contains("iron"));
    }

    @Test
    void testHasCargoAvailable() {
        assertTrue(station.hasCargoAvailable("passengers"));
        assertTrue(station.hasCargoAvailable("mail"));
        assertTrue(station.hasCargoAvailable("coal"));
        assertFalse(station.hasCargoAvailable("invalid_cargo"));
    }

    @Test
    void testAddUpgrade() {
        StationUpgrade upgrade = StationUpgrade.TELEGRAPH;
        station.addUpgrade(upgrade);
        List<StationUpgrade> upgrades = station.getUpgrades();
        assertTrue(upgrades.contains(upgrade));
    }

    @Test
    void testAddDuplicateUpgrade() {
        StationUpgrade upgrade = StationUpgrade.TELEGRAPH;
        station.addUpgrade(upgrade);
        station.addUpgrade(upgrade);
        assertEquals(1, station.getUpgrades().size());
    }

    @Test
    void testGetTotalUpgradeCost() {
        StationUpgrade upgrade1 = StationUpgrade.TELEGRAPH;
        StationUpgrade upgrade2 = StationUpgrade.RESTAURANT_SMALL;
        station.addUpgrade(upgrade1);
        station.addUpgrade(upgrade2);
        int totalCost = station.getTotalUpgradeCost();
        assertEquals(15000, totalCost); // 10000 + 5000
    }

    @Test
    void testGetAllBonuses() {
        StationUpgrade upgrade = StationUpgrade.TELEGRAPH;
        station.addUpgrade(upgrade);
        List<String> bonuses = station.getAllBonuses();
        assertNotNull(bonuses);
        assertFalse(bonuses.isEmpty());
        assertTrue(bonuses.contains("Posto Telegráfico"));
    }

    @Test
    void testGetMaintenanceCost() {
        int cost = station.getMaintenanceCost();
        assertEquals(10000, cost); // Regular station maintenance cost
    }

    @Test
    void testGetProximityRadius() {
        int radius = station.getProximityRadius();
        assertEquals(3, radius); // Regular station radius
    }

    @Test
    void testGetNearbyCities() {
        Map map = new Map("Test Map", 10, 10, ScaleUnit.KM, new Organization("Test Org", 
            new CreateMapRepository(), new CityRepository(), new IndustryRepository(), new StationRepository()));
        List<City> nearbyCities = station.getNearbyCities(map);
        assertNotNull(nearbyCities);
    }

    @Test
    void testGetNearbyIndustries() {
        Map map = new Map("Test Map", 10, 10, ScaleUnit.KM, new Organization("Test Org", 
            new CreateMapRepository(), new CityRepository(), new IndustryRepository(), new StationRepository()));
        List<Industry> nearbyIndustries = station.getNearbyIndustries(map);
        assertNotNull(nearbyIndustries);
    }

    @Test
    void testGetPassengerLoadRate() {
        Map map = new Map("Test Map", 10, 10, ScaleUnit.KM, new Organization("Test Org", 
            new CreateMapRepository(), new CityRepository(), new IndustryRepository(), new StationRepository()));
        int rate = station.getPassengerLoadRate(map);
        assertTrue(rate >= 0);
    }

    @Test
    void testGetCargoLoadRate() {
        Map map = new Map("Test Map", 10, 10, ScaleUnit.KM, new Organization("Test Org", 
            new CreateMapRepository(), new CityRepository(), new IndustryRepository(), new StationRepository()));
        int rate = station.getCargoLoadRate(map, "coal");
        assertTrue(rate >= 0);
    }

    @Test
    void testGetDemanda() {
        assertEquals(5, station.getDemanda("coal")); // Default value
        station.setDemanda("coal", 8);
        assertEquals(8, station.getDemanda("coal"));
    }

    @Test
    void testSetDemanda() {
        station.setDemanda("coal", 8);
        assertEquals(8, station.getDemanda("coal"));
        
        // Test bounds
        station.setDemanda("coal", 0);
        assertEquals(1, station.getDemanda("coal")); // Minimum value
        
        station.setDemanda("coal", 11);
        assertEquals(10, station.getDemanda("coal")); // Maximum value
    }

    @Test
    void testAtualizarDemandaAleatoria() {
        station.setDemanda("coal", 5);
        station.atualizarDemandaAleatoria();
        int newDemanda = station.getDemanda("coal");
        assertTrue(newDemanda >= 1 && newDemanda <= 10);
    }

    @Test
    void testEquals() {
        Station station2 = new Station(name, type, region, scenario, area, x, y);
        assertNotEquals(station, station2); // Different references
        
        Station station3 = new Station(station.getReference(), name, type, region, scenario, area, x, y);
        assertEquals(station, station3);
    }

    @Test
    void testHashCode() {
        Station station2 = new Station(station.getReference(), name, type, region, scenario, area, x, y);
        assertEquals(station.hashCode(), station2.hashCode());
    }

    @Test
    void testClone() {
        Station clone = station.clone();
        assertNotSame(station, clone);
        assertEquals(station.getName(), clone.getName());
        assertEquals(station.getType(), clone.getType());
        assertEquals(station.getRegion(), clone.getRegion());
        assertEquals(station.getReference(), clone.getReference());
    }

    @Test
    void testToString() {
        String result = station.toString();
        assertTrue(result.contains(name));
        assertTrue(result.contains(type));
        assertTrue(result.contains(region));
        assertTrue(result.contains(station.getReference()));
        assertTrue(result.contains(String.valueOf(x)));
        assertTrue(result.contains(String.valueOf(y)));
    }
} 