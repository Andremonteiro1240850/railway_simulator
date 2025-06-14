package pt.ipp.isep.dei.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.domain.Map;
import pt.ipp.isep.dei.domain.Organization;
import pt.ipp.isep.dei.domain.enums.ScaleUnit;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CreateMapRepositoryTest {

    private CreateMapRepository repository;
    private Map testMap;
    private Organization testOrganization;
    private CityRepository cityRepository;
    private IndustryRepository industryRepository;
    private StationRepository stationRepository;

    @BeforeEach
    void setUp() {
        repository = new CreateMapRepository();
        cityRepository = new CityRepository();
        industryRepository = new IndustryRepository();
        stationRepository = new StationRepository();
        testOrganization = new Organization("123456789", repository, cityRepository, industryRepository, stationRepository);
        testMap = new Map("Test Map", 100, 100, ScaleUnit.KM, testOrganization);
    }

    @Test
    void save_ValidMap_ShouldSaveAndReturnMap() {
        // Act
        Optional<Map> result = repository.save(testMap);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(testMap, result.get());
        assertTrue(repository.contains(testMap));
        assertEquals(1, repository.getAll().size());
    }

    @Test
    void save_NullMap_ShouldReturnEmpty() {
        // Act
        Optional<Map> result = repository.save(null);

        // Assert
        assertTrue(result.isEmpty());
        assertEquals(0, repository.getAll().size());
    }

    @Test
    void save_DuplicateMap_ShouldReturnEmpty() {
        // Arrange
        repository.save(testMap);
        Map duplicateMap = new Map("Test Map", 100, 100, ScaleUnit.KM, testOrganization);

        // Act
        Optional<Map> result = repository.save(duplicateMap);

        // Assert
        assertTrue(result.isEmpty());
        assertEquals(1, repository.getAll().size());
    }

    @Test
    void save_MultipleMaps_ShouldSaveAllMaps() {
        // Arrange
        Map map1 = new Map("Map 1", 100, 100, ScaleUnit.KM, testOrganization);
        Map map2 = new Map("Map 2", 200, 200, ScaleUnit.MILES, testOrganization);
        Map map3 = new Map("Map 3", 300, 300, ScaleUnit.METERS, testOrganization);

        // Act
        repository.save(map1);
        repository.save(map2);
        repository.save(map3);

        // Assert
        assertEquals(3, repository.getAll().size());
        assertTrue(repository.contains(map1));
        assertTrue(repository.contains(map2));
        assertTrue(repository.contains(map3));
    }

    @Test
    void contains_ExistingMap_ShouldReturnTrue() {
        // Arrange
        repository.save(testMap);

        // Act
        boolean result = repository.contains(testMap);

        // Assert
        assertTrue(result);
    }

    @Test
    void contains_NonExistingMap_ShouldReturnFalse() {
        // Act
        boolean result = repository.contains(testMap);

        // Assert
        assertFalse(result);
    }

    @Test
    void contains_MapWithSameNameDifferentOrganization_ShouldReturnFalse() {
        // Arrange
        repository.save(testMap);
        Organization otherOrg = new Organization("987654321", repository, cityRepository, industryRepository, stationRepository);
        Map similarMap = new Map("Test Map", 100, 100, ScaleUnit.KM, otherOrg);

        // Act
        boolean result = repository.contains(similarMap);

        // Assert
        assertFalse(result);
    }

    @Test
    void getAll_ShouldReturnDefensiveCopy() {
        // Arrange
        repository.save(testMap);
        List<Map> maps = repository.getAll();

        // Act
        maps.clear();

        // Assert
        assertFalse(repository.getAll().isEmpty());
        assertEquals(1, repository.getAll().size());
    }

    @Test
    void getAll_EmptyRepository_ShouldReturnEmptyList() {
        // Act
        List<Map> result = repository.getAll();

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    void getAll_WithMultipleMaps_ShouldReturnAllMaps() {
        // Arrange
        Map map1 = new Map("Map 1", 100, 100, ScaleUnit.KM, testOrganization);
        Map map2 = new Map("Map 2", 200, 200, ScaleUnit.MILES, testOrganization);
        repository.save(map1);
        repository.save(map2);

        // Act
        List<Map> result = repository.getAll();

        // Assert
        assertEquals(2, result.size());
        assertTrue(result.contains(map1));
        assertTrue(result.contains(map2));
    }

    @Test
    void save_MapWithDifferentDimensions_ShouldSaveAsDifferentMap() {
        // Arrange
        repository.save(testMap);
        Map differentMap = new Map("Test Map", 200, 200, ScaleUnit.KM, testOrganization);

        // Act
        Optional<Map> result = repository.save(differentMap);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(2, repository.getAll().size());
    }

    @Test
    void save_MapWithDifferentScale_ShouldSaveAsDifferentMap() {
        // Arrange
        repository.save(testMap);
        Map differentMap = new Map("Test Map", 100, 100, ScaleUnit.MILES, testOrganization);

        // Act
        Optional<Map> result = repository.save(differentMap);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(2, repository.getAll().size());
    }
} 