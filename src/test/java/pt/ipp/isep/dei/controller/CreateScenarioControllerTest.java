package pt.ipp.isep.dei.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.domain.*;
import pt.ipp.isep.dei.domain.enums.LocomotiveType;
import pt.ipp.isep.dei.domain.enums.ScaleUnit;
import pt.ipp.isep.dei.repository.*;
import pt.isep.lei.esoft.auth.domain.model.Email;
import pt.isep.lei.esoft.auth.UserSession;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class CreateScenarioControllerTest {
    private CreateScenarioController controller;
    private OrganizationRepository orgRepo;
    private AuthenticationRepository authRepo;
    private CreateMapRepository mapRepo;
    private ScenarioRepository scenarioRepo;
    private Organization organization;
    private Employee employee;
    private Map map;
    private CityRepository cityRepository;
    private IndustryRepository industryRepository;
    private StationRepository stationRepository;
    private UserSession mockUserSession;

    @BeforeEach
    void setUp() {
        // Initialize repositories
        orgRepo = new OrganizationRepository();
        mapRepo = new CreateMapRepository();
        scenarioRepo = new ScenarioRepository();
        cityRepository = new CityRepository();
        industryRepository = new IndustryRepository();
        stationRepository = new StationRepository();
        
        // Create organization with repositories
        organization = new Organization("This Company", mapRepo, cityRepository, industryRepository, stationRepository);
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
                return new Email(employee.getEmail()); // Usa o mesmo email do employee
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
        controller = new CreateScenarioController(orgRepo, authRepo, mapRepo, scenarioRepo);
    }

    @Test
    void testGetAvailableMaps() {
        // Act
        List<Map> maps = controller.getAvailableMaps();

        // Assert
        assertNotNull(maps);
        assertFalse(maps.isEmpty());
        assertTrue(maps.contains(map));
    }

    @Test
    void testLoadApplicableHistoricalEvents() {
        // Arrange
        int startYear = 1900;

        // Act
        List<HistoricalEvent> events = controller.loadApplicableHistoricalEvents(startYear);

        // Assert
        assertNotNull(events);
        assertFalse(events.isEmpty());
    }

    @Test
    void testCreateScenarioWithValidData() {
        // Arrange
        String name = "Test Scenario";
        int startYear = 1900;
        int endYear = 2000;
        List<String> locomotiveTypes = Arrays.asList("STEAM", "DIESEL");
        PortBehavior portBehavior = new PortBehavior();
        double budget = 1000000.0;
        List<HistoricalEvent> historicalEvents = new ArrayList<>();

        // Act
        boolean result = controller.createScenario(name, map, startYear, endYear, 
            locomotiveTypes, portBehavior, budget, historicalEvents);

        // Assert
        assertTrue(result);
        assertNotNull(controller.getScenario());
        assertEquals(name, controller.getScenario().getName());
        assertEquals(startYear, controller.getScenario().getStartYear());
        assertEquals(endYear, controller.getScenario().getEndYear());
        assertEquals(budget, controller.getScenario().getBudget());
    }

    @Test
    void testCreateScenarioWithInvalidYears() {
        // Arrange
        String name = "Test Scenario";
        int startYear = 2000;
        int endYear = 1900; // Invalid: end year before start year
        List<String> locomotiveTypes = Arrays.asList("STEAM");
        PortBehavior portBehavior = new PortBehavior();
        double budget = 1000000.0;
        List<HistoricalEvent> historicalEvents = new ArrayList<>();

        // Act
        boolean result = controller.createScenario(name, map, startYear, endYear, 
            locomotiveTypes, portBehavior, budget, historicalEvents);

        // Assert
        assertFalse(result);
    }

    @Test
    void testCreateScenarioWithNegativeBudget() {
        // Arrange
        String name = "Test Scenario";
        int startYear = 1900;
        int endYear = 2000;
        List<String> locomotiveTypes = Arrays.asList("STEAM");
        PortBehavior portBehavior = new PortBehavior();
        double budget = -1000000.0; // Invalid: negative budget
        List<HistoricalEvent> historicalEvents = new ArrayList<>();

        // Act
        boolean result = controller.createScenario(name, map, startYear, endYear, 
            locomotiveTypes, portBehavior, budget, historicalEvents);

        // Assert
        assertFalse(result);
    }

    @Test
    void testCreateScenarioWithInvalidLocomotiveType() {
        // Arrange
        String name = "Test Scenario";
        int startYear = 1900;
        int endYear = 2000;
        List<String> locomotiveTypes = Arrays.asList("INVALID_TYPE");
        PortBehavior portBehavior = new PortBehavior();
        double budget = 1000000.0;
        List<HistoricalEvent> historicalEvents = new ArrayList<>();

        // Act
        boolean result = controller.createScenario(name, map, startYear, endYear, 
            locomotiveTypes, portBehavior, budget, historicalEvents);

        // Assert
        assertFalse(result);
    }

    @Test
    void testSpendBudget() {
        // Arrange
        createValidScenario();
        double amount = 100000.0;

        // Act & Assert
        assertDoesNotThrow(() -> controller.spendBudget(amount));
        assertEquals(900000.0, controller.getScenario().getBudget());
    }

    @Test
    void testSpendBudgetWithNegativeAmount() {
        // Arrange
        createValidScenario();
        double amount = -100000.0;

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> controller.spendBudget(amount));
    }

    @Test
    void testSpendBudgetWithInsufficientFunds() {
        // Arrange
        createValidScenario();
        double amount = 2000000.0; // More than available budget

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> controller.spendBudget(amount));
    }

    @Test
    void testReceiveBudget() {
        // Arrange
        createValidScenario();
        double amount = 100000.0;

        // Act & Assert
        assertDoesNotThrow(() -> controller.receiveBudget(amount));
        assertEquals(1100000.0, controller.getScenario().getBudget());
    }

    @Test
    void testReceiveBudgetWithNegativeAmount() {
        // Arrange
        createValidScenario();
        double amount = -100000.0;

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> controller.receiveBudget(amount));
    }

    private void createValidScenario() {
        String name = "Test Scenario";
        int startYear = 1900;
        int endYear = 2000;
        List<String> locomotiveTypes = Arrays.asList("STEAM", "DIESEL");
        PortBehavior portBehavior = new PortBehavior();
        double budget = 1000000.0;
        List<HistoricalEvent> historicalEvents = new ArrayList<>();
        boolean result = controller.createScenario(name, map, startYear, endYear, 
            locomotiveTypes, portBehavior, budget, historicalEvents);
        assertTrue(result, "Falha ao criar cenário válido no setup do teste.");
    }
} 