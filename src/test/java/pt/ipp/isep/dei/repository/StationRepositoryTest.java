package pt.ipp.isep.dei.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.domain.*;
import pt.ipp.isep.dei.domain.enums.StationTypes;
import pt.ipp.isep.dei.domain.enums.StationUpgrade;
import pt.ipp.isep.dei.domain.enums.ScaleUnit;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class StationRepositoryTest {

    private StationRepository repository;
    private Station testStation;
    private Map testMap;
    private Organization testOrganization;
    private CreateMapRepository createMapRepository;
    private CityRepository cityRepository;
    private IndustryRepository industryRepository;
    private StationRepository stationRepository;

    @BeforeEach
    void setUp() {
        repository = new StationRepository();
        
        // Initialize repositories
        createMapRepository = new CreateMapRepository();
        cityRepository = new CityRepository();
        industryRepository = new IndustryRepository();
        stationRepository = new StationRepository();

        // Create organization with repositories
        testOrganization = new Organization("123456789", createMapRepository, cityRepository, industryRepository, stationRepository);
        
        // Create map with organization
        testMap = new Map("Test Map", 10, 10, ScaleUnit.KM, testOrganization);

        // Create test station
        testStation = new Station("Test Station", StationTypes.STATION.getType(), "North", "Test Scenario", 
            StationTypes.STATION.getArea(), 5, 5);
    }

    @Test
    void add_ValidStation_ShouldAddAndReturnStation() {
        // Act
        Optional<Station> result = repository.add(testStation);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(testStation.getName(), result.get().getName());
        assertFalse(repository.getAll().isEmpty());
    }

    @Test
    void add_NullStation_ShouldReturnEmpty() {
        // Act
        Optional<Station> result = repository.add(null);

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    void getAll_ShouldReturnDefensiveCopy() {
        // Arrange
        repository.add(testStation);
        List<Station> stations = repository.getAll();

        // Act
        stations.clear();

        // Assert
        assertFalse(repository.getAll().isEmpty());
    }

    @Test
    void findByReference_ExistingStation_ShouldReturnStation() {
        // Arrange
        repository.add(testStation);

        // Act
        Optional<Station> result = repository.findByReference(testStation.getReference());

        // Assert
        assertTrue(result.isPresent());
        assertEquals(testStation.getName(), result.get().getName());
    }

    @Test
    void findByReference_NonExistingStation_ShouldReturnEmpty() {
        // Act
        Optional<Station> result = repository.findByReference("NONEXISTENT");

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    void addUpgradeToStation_ValidUpgrade_ShouldAddUpgrade() {
        // Arrange
        repository.add(testStation);
        StationUpgrade upgrade = StationUpgrade.TELEGRAPH;

        // Act
        boolean result = repository.addUpgradeToStation(testStation.getReference(), upgrade);

        // Assert
        assertTrue(result);
        Optional<Station> updatedStation = repository.findByReference(testStation.getReference());
        assertTrue(updatedStation.isPresent());
        assertTrue(updatedStation.get().getUpgrades().contains(upgrade));
    }

    @Test
    void addUpgradeToStation_NonExistingStation_ShouldReturnFalse() {
        // Arrange
        StationUpgrade upgrade = StationUpgrade.TELEGRAPH;

        // Act
        boolean result = repository.addUpgradeToStation("NONEXISTENT", upgrade);

        // Assert
        assertFalse(result);
    }

    @Test
    void addUpgradeToStation_NullUpgrade_ShouldReturnFalse() {
        // Arrange
        repository.add(testStation);

        // Act
        boolean result = repository.addUpgradeToStation(testStation.getReference(), null);

        // Assert
        assertFalse(result);
    }
} 