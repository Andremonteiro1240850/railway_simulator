package pt.ipp.isep.dei.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.domain.*;
import pt.ipp.isep.dei.domain.enums.IndustryType;
import pt.ipp.isep.dei.domain.enums.ScaleUnit;
import pt.ipp.isep.dei.repository.*;
import pt.isep.lei.esoft.auth.UserSession;
import pt.isep.lei.esoft.auth.domain.model.Email;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class AddIndustryControllerTest {
    private AddIndustryController controller;
    private OrganizationRepository orgRepo;
    private AuthenticationRepository authRepo;
    private CreateMapController mapController;
    private CreateMapRepository mapRepo;
    private CityRepository cityRepo;
    private IndustryRepository industryRepo;
    private StationRepository stationRepo;
    private Organization organization;
    private Employee employee;
    private Map map;
    private UserSession mockUserSession;

    @BeforeEach
    void setUp() {
        orgRepo = new OrganizationRepository();
        authRepo = new AuthenticationRepository();
        mapController = new CreateMapController();
        mapRepo = new CreateMapRepository();
        cityRepo = new CityRepository();
        industryRepo = new IndustryRepository();
        stationRepo = new StationRepository();
        
        // Create organization with repositories
        organization = new Organization("Test Org", mapRepo, cityRepo, industryRepo, stationRepo);
        orgRepo.add(organization);
        
        // Create and add employee
        employee = new Employee("test@example.com");
        organization.addEmployee(employee);
        
        // Create and add map
        map = new Map("Test Map", 100, 100, ScaleUnit.KM, organization);
        mapRepo.save(map);
        
        // Create mock user session
        mockUserSession = new UserSession() {
            @Override
            public boolean isLoggedIn() {
                return true;
            }
            @Override
            public Email getUserId() {
                return new Email(employee.getEmail());
            }
        };
        
        // Mock AuthenticationRepository
        authRepo = new AuthenticationRepository() {
            @Override
            public UserSession getCurrentUserSession() {
                return mockUserSession;
            }
        };
        
        // Initialize controller with mocked repositories
        controller = new AddIndustryController(orgRepo, authRepo, mapController);
    }

    @Test
    void testCreateIndustryWithValidData() {
        // Arrange
        IndustryType type = IndustryType.PRIMARY;
        int positionX = 50;
        int positionY = 50;

        // Act
        Optional<Industry> result = controller.createIndustry(type, positionX, positionY, map);

        // Assert
        assertTrue(result.isPresent());
        Industry industry = result.get();
        assertEquals(type, industry.getType());
        assertEquals(positionX, industry.getPositionX());
        assertEquals(positionY, industry.getPositionY());
    }

    @Test
    void testCreateIndustryWithNullType() {
        // Arrange
        IndustryType type = null;
        int positionX = 50;
        int positionY = 50;

        // Act
        Optional<Industry> result = controller.createIndustry(type, positionX, positionY, map);

        // Assert
        assertFalse(result.isPresent());
    }

    @Test
    void testCreateIndustryWithInvalidPosition() {
        // Arrange
        IndustryType type = IndustryType.PRIMARY;
        int positionX = 150; // Outside map bounds
        int positionY = 50;

        // Act
        Optional<Industry> result = controller.createIndustry(type, positionX, positionY, map);

        // Assert
        assertFalse(result.isPresent());
    }

    @Test
    void testCreateIndustryWithNegativePosition() {
        // Arrange
        IndustryType type = IndustryType.PRIMARY;
        int positionX = -10;
        int positionY = 50;

        // Act
        Optional<Industry> result = controller.createIndustry(type, positionX, positionY, map);

        // Assert
        assertFalse(result.isPresent());
    }

    @Test
    void testCreateIndustryWithNullMap() {
        // Arrange
        IndustryType type = IndustryType.PRIMARY;
        int positionX = 50;
        int positionY = 50;
        Map nullMap = null;

        // Act
        Optional<Industry> result = controller.createIndustry(type, positionX, positionY, nullMap);

        // Assert
        assertFalse(result.isPresent());
    }

    @Test
    void testCreateIndustryWithOccupiedPosition() {
        // Arrange
        IndustryType type = IndustryType.PRIMARY;
        int positionX = 50;
        int positionY = 50;
        controller.createIndustry(type, positionX, positionY, map);

        // Act
        Optional<Industry> duplicateResult = controller.createIndustry(type, positionX, positionY, map);

        // Assert
        assertFalse(duplicateResult.isPresent());
    }

    @Test
    void testCreateIndustryWithDifferentTypes() {
        // Arrange
        int positionX = 50;
        int positionY = 50;

        // Act & Assert for PRIMARY
        Optional<Industry> primaryResult = controller.createIndustry(IndustryType.PRIMARY, positionX, positionY, map);
        assertTrue(primaryResult.isPresent());
        assertEquals(IndustryType.PRIMARY, primaryResult.get().getType());

        // Act & Assert for TRANSFORMING
        Optional<Industry> transformingResult = controller.createIndustry(IndustryType.TRANSFORMING, positionX + 1, positionY, map);
        assertTrue(transformingResult.isPresent());
        assertEquals(IndustryType.TRANSFORMING, transformingResult.get().getType());

        // Act & Assert for MIXED
        Optional<Industry> mixedResult = controller.createIndustry(IndustryType.MIXED, positionX + 2, positionY, map);
        assertTrue(mixedResult.isPresent());
        assertEquals(IndustryType.MIXED, mixedResult.get().getType());
    }
} 