package pt.ipp.isep.dei.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.domain.City;
import pt.ipp.isep.dei.domain.Employee;
import pt.ipp.isep.dei.domain.Map;
import pt.ipp.isep.dei.domain.Organization;
import pt.ipp.isep.dei.domain.enums.ScaleUnit;
import pt.ipp.isep.dei.repository.*;
import pt.isep.lei.esoft.auth.UserSession;
import pt.isep.lei.esoft.auth.domain.model.Email;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class AddCityControllerTest {
    private AddCityController controller;
    private OrganizationRepository orgRepo;
    private AuthenticationRepository authRepo;
    private CreateMapRepository mapRepo;
    private CityRepository cityRepo;
    private IndustryRepository industryRepo;
    private StationRepository stationRepo;
    private Organization organization;
    private Employee employee;
    private Map map;

    @BeforeEach
    void setUp() {
        orgRepo = new OrganizationRepository();
        authRepo = new AuthenticationRepository();
        mapRepo = new CreateMapRepository();
        cityRepo = new CityRepository();
        industryRepo = new IndustryRepository();
        stationRepo = new StationRepository();
        
        organization = new Organization("Test Org", mapRepo, cityRepo, industryRepo, stationRepo);
        employee = new Employee("test@example.com");
        organization.addEmployee(employee);
        map = new Map("Test Map", 100, 100, ScaleUnit.KM, organization);

        // Create mock user session
        UserSession mockUserSession = new UserSession() {
            @Override
            public boolean isLoggedIn() {
                return true;
            }
            @Override
            public Email getUserId() {
                return new Email(employee.getEmail());
            }
            @Override
            public List<pt.isep.lei.esoft.auth.mappers.dto.UserRoleDTO> getUserRoles() {
                return new ArrayList<>();
            }
        };
        
        // Mock AuthenticationRepository
        authRepo = new AuthenticationRepository() {
            @Override
            public UserSession getCurrentUserSession() {
                return mockUserSession;
            }
        };
        
        // Initialize controller
        controller = new AddCityController();
    }

    @Test
    void testCreateCityWithValidData() {
        // Arrange
        String cityName = "Test City";
        int positionX = 50;
        int positionY = 50;
        List<int[]> houseBlocks = new ArrayList<>();
        houseBlocks.add(new int[]{51, 51});
        houseBlocks.add(new int[]{52, 52});

        // Act
        Optional<City> result = controller.createCity(map, cityName, positionX, positionY, houseBlocks);

        // Assert
        assertTrue(result.isPresent());
        City city = result.get();
        assertEquals(cityName, city.getName());
        assertEquals(positionX, city.getX());
        assertEquals(positionY, city.getY());
    }

    @Test
    void testCreateCityWithInvalidPosition() {
        // Arrange
        String cityName = "Test City";
        int positionX = 150; // Outside map bounds
        int positionY = 50;
        List<int[]> houseBlocks = new ArrayList<>();
        houseBlocks.add(new int[]{51, 51});

        // Act
        Optional<City> result = controller.createCity(map, cityName, positionX, positionY, houseBlocks);

        // Assert
        assertFalse(result.isPresent());
    }

    @Test
    void testCreateCityWithNegativePosition() {
        // Arrange
        String cityName = "Test City";
        int positionX = -10;
        int positionY = 50;
        List<int[]> houseBlocks = new ArrayList<>();
        houseBlocks.add(new int[]{51, 51});

        // Act
        Optional<City> result = controller.createCity(map, cityName, positionX, positionY, houseBlocks);

        // Assert
        assertFalse(result.isPresent());
    }

    @Test
    void testCreateCityWithNullMap() {
        // Arrange
        String cityName = "Test City";
        int positionX = 50;
        int positionY = 50;
        List<int[]> houseBlocks = new ArrayList<>();
        houseBlocks.add(new int[]{51, 51});
        Map nullMap = null;

        // Act
        Optional<City> result = controller.createCity(nullMap, cityName, positionX, positionY, houseBlocks);

        // Assert
        assertFalse(result.isPresent());
    }

    @Test
    void testCreateCityWithNullName() {
        // Arrange
        String cityName = null;
        int positionX = 50;
        int positionY = 50;
        List<int[]> houseBlocks = new ArrayList<>();
        houseBlocks.add(new int[]{51, 51});

        // Act
        Optional<City> result = controller.createCity(map, cityName, positionX, positionY, houseBlocks);

        // Assert
        assertFalse(result.isPresent());
    }

    @Test
    void testCreateCityWithEmptyName() {
        // Arrange
        String cityName = "";
        int positionX = 50;
        int positionY = 50;
        List<int[]> houseBlocks = new ArrayList<>();
        houseBlocks.add(new int[]{51, 51});

        // Act
        Optional<City> result = controller.createCity(map, cityName, positionX, positionY, houseBlocks);

        // Assert
        assertFalse(result.isPresent());
    }

    @Test
    void testCreateCityWithNoHouseBlocks() {
        // Arrange
        String cityName = "Test City";
        int positionX = 50;
        int positionY = 50;
        List<int[]> houseBlocks = new ArrayList<>();

        // Act
        Optional<City> result = controller.createCity(map, cityName, positionX, positionY, houseBlocks);

        // Assert
        assertFalse(result.isPresent());
    }

    @Test
    void testCreateCityWithInvalidHouseBlockPosition() {
        // Arrange
        String cityName = "Test City";
        int positionX = 50;
        int positionY = 50;
        List<int[]> houseBlocks = new ArrayList<>();
        houseBlocks.add(new int[]{150, 150}); // Outside map bounds

        // Act
        Optional<City> result = controller.createCity(map, cityName, positionX, positionY, houseBlocks);

        // Assert
        assertFalse(result.isPresent());
    }

    @Test
    void testCreateCityWithOccupiedPosition() {
        // Arrange
        String cityName = "Test City";
        int positionX = 50;
        int positionY = 50;
        List<int[]> houseBlocks = new ArrayList<>();
        houseBlocks.add(new int[]{51, 51});
        
        // Create first city
        controller.createCity(map, cityName, positionX, positionY, houseBlocks);

        // Act - Try to create second city at same position
        Optional<City> duplicateResult = controller.createCity(map, "Another City", positionX, positionY, houseBlocks);

        // Assert
        assertFalse(duplicateResult.isPresent());
    }
} 