package pt.ipp.isep.dei.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.domain.*;
import pt.ipp.isep.dei.domain.enums.ScaleUnit;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CityRepositoryTest {

    private CityRepository repository;
    private City testCity;
    private Map testMap;
    private Organization testOrganization;
    private CreateMapRepository createMapRepository;
    private CityRepository cityRepository;
    private IndustryRepository industryRepository;
    private StationRepository stationRepository;
    private Employee testEmployee;
    private List<int[]> houseBlocks;

    @BeforeEach
    void setUp() {
        repository = new CityRepository();
        
        // Initialize repositories
        createMapRepository = new CreateMapRepository();
        cityRepository = new CityRepository();
        industryRepository = new IndustryRepository();
        stationRepository = new StationRepository();

        // Create organization with repositories
        testOrganization = new Organization("123456789", createMapRepository, cityRepository, industryRepository, stationRepository);
        
        // Create map with organization
        testMap = new Map("Test Map", 10, 10, ScaleUnit.KM, testOrganization);

        // Create test employee
        testEmployee = new Employee("test@email.com");

        // Create test house blocks
        houseBlocks = new ArrayList<>();
        houseBlocks.add(new int[]{1, 1});
        houseBlocks.add(new int[]{1, 2});

        // Create test city
        testCity = new City(testMap, "Test City", 5, 5, houseBlocks, testEmployee);
    }

    @Test
    void add_ValidCity_ShouldAddAndReturnCity() {
        // Act
        Optional<City> result = repository.add(testCity);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(testCity.getName(), result.get().getName());
        assertEquals(testCity.getX(), result.get().getX());
        assertEquals(testCity.getY(), result.get().getY());
        assertEquals(testCity.getHouseBlocks().size(), result.get().getHouseBlocks().size());
    }

    @Test
    void add_NullCity_ShouldReturnEmpty() {
        // Act
        Optional<City> result = repository.add(null);

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    void add_DuplicateCity_ShouldReturnEmpty() {
        // Arrange
        repository.add(testCity);

        // Act
        Optional<City> result = repository.add(testCity);

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    void contains_ExistingCity_ShouldReturnTrue() {
        // Arrange
        repository.add(testCity);

        // Act
        boolean result = repository.contains(testCity);

        // Assert
        assertTrue(result);
    }

    @Test
    void contains_NonExistingCity_ShouldReturnFalse() {
        // Act
        boolean result = repository.contains(testCity);

        // Assert
        assertFalse(result);
    }

    @Test
    void getAll_ShouldReturnDefensiveCopy() {
        // Arrange
        repository.add(testCity);
        List<City> cities = repository.getAll();

        // Act
        cities.clear();

        // Assert
        assertFalse(repository.getAll().isEmpty());
    }

    @Test
    void getAll_WithMultipleCities_ShouldReturnAllCities() {
        // Arrange
        City city1 = new City(testMap, "City One", 5, 5, houseBlocks, testEmployee);
        City city2 = new City(testMap, "City Two", 6, 6, houseBlocks, testEmployee);
        repository.add(city1);
        repository.add(city2);

        // Act
        List<City> result = repository.getAll();

        // Assert
        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(c -> c.getName().equals("City One")));
        assertTrue(result.stream().anyMatch(c -> c.getName().equals("City Two")));
    }
} 