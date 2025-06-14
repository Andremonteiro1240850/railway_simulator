package pt.ipp.isep.dei.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.domain.*;
import pt.ipp.isep.dei.domain.enums.IndustryType;
import pt.ipp.isep.dei.domain.enums.ScaleUnit;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class IndustryRepositoryTest {

    private IndustryRepository repository;
    private Industry testIndustry;
    private Map testMap;
    private Organization testOrganization;
    private CreateMapRepository createMapRepository;
    private CityRepository cityRepository;
    private IndustryRepository industryRepository;
    private StationRepository stationRepository;

    @BeforeEach
    void setUp() {
        repository = new IndustryRepository();
        
        // Initialize repositories
        createMapRepository = new CreateMapRepository();
        cityRepository = new CityRepository();
        industryRepository = new IndustryRepository();
        stationRepository = new StationRepository();

        // Create organization with repositories
        testOrganization = new Organization("123456789", createMapRepository, cityRepository, industryRepository, stationRepository);
        
        // Create map with organization
        testMap = new Map("Test Map", 10, 10, ScaleUnit.KM, testOrganization);

        // Create test industry
        testIndustry = new Industry(IndustryType.PRIMARY, testMap, 5, 5);
    }

    @Test
    void add_ValidIndustry_ShouldAddAndReturnIndustry() {
        // Act
        Optional<Industry> result = repository.add(testIndustry);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(testIndustry.getType(), result.get().getType());
        assertEquals(testIndustry.getPositionX(), result.get().getPositionX());
        assertEquals(testIndustry.getPositionY(), result.get().getPositionY());
        assertEquals(testIndustry.getMap(), result.get().getMap());
    }

    @Test
    void add_NullIndustry_ShouldReturnEmpty() {
        // Act
        Optional<Industry> result = repository.add(null);

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    void add_DuplicateIndustry_ShouldReturnEmpty() {
        // Arrange
        repository.add(testIndustry);

        // Act
        Optional<Industry> result = repository.add(testIndustry);

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    void getAll_ShouldReturnDefensiveCopy() {
        // Arrange
        repository.add(testIndustry);
        List<Industry> industries = repository.getAll();

        // Act
        industries.clear();

        // Assert
        assertFalse(repository.getAll().isEmpty());
    }

    @Test
    void getAll_WithMultipleIndustries_ShouldReturnAllIndustries() {
        // Arrange
        Industry industry1 = new Industry(IndustryType.PRIMARY, testMap, 5, 5);
        Industry industry2 = new Industry(IndustryType.TRANSFORMING, testMap, 6, 6);
        repository.add(industry1);
        repository.add(industry2);

        // Act
        List<Industry> result = repository.getAll();

        // Assert
        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(i -> i.getType() == IndustryType.PRIMARY));
        assertTrue(result.stream().anyMatch(i -> i.getType() == IndustryType.TRANSFORMING));
    }
} 