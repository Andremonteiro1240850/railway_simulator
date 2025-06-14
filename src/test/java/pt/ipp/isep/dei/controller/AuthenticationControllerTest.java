package pt.ipp.isep.dei.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.domain.Employee;
import pt.ipp.isep.dei.domain.Organization;
import pt.ipp.isep.dei.repository.*;
import pt.isep.lei.esoft.auth.mappers.dto.UserRoleDTO;
import pt.isep.lei.esoft.auth.UserSession;
import pt.isep.lei.esoft.auth.domain.model.Email;

import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class AuthenticationControllerTest {
    private AuthenticationController controller;
    private AuthenticationRepository authRepo;
    private Organization organization;
    private CreateMapRepository createMapRepository;
    private CityRepository cityRepository;
    private IndustryRepository industryRepository;
    private StationRepository stationRepository;
    private UserSession mockUserSession;
    private boolean isLoggedIn = false;

    @BeforeEach
    void setUp() {
        // Initialize repositories
        createMapRepository = new CreateMapRepository();
        cityRepository = new CityRepository();
        industryRepository = new IndustryRepository();
        stationRepository = new StationRepository();
        
        // Create organization with repositories
        organization = new Organization("123456789", createMapRepository, cityRepository, industryRepository, stationRepository);
        
        // Create mock user session
        mockUserSession = new UserSession() {
            @Override
            public boolean isLoggedIn() {
                return isLoggedIn;
            }
            @Override
            public Email getUserId() {
                return new Email("test@example.com");
            }
            @Override
            public List<UserRoleDTO> getUserRoles() {
                if (!isLoggedIn) {
                    return null;
                }
                List<UserRoleDTO> roles = new ArrayList<>();
                roles.add(new UserRoleDTO("EMPLOYEE", "Employee Role"));
                return roles;
            }
        };
        
        // Create mock authentication repository
        authRepo = new AuthenticationRepository() {
            @Override
            public boolean doLogin(String email, String pwd) {
                if (email.equals("test@example.com") && pwd.equals("password123")) {
                    isLoggedIn = true;
                    return true;
                }
                return false;
            }
            @Override
            public void doLogout() {
                isLoggedIn = false;
            }
            @Override
            public UserSession getCurrentUserSession() {
                return mockUserSession;
            }
        };
        
        // Create controller with mock repository
        controller = new AuthenticationController(authRepo);
    }

    @Test
    void testDoLoginWithValidCredentials() {
        // Arrange
        String email = "test@example.com";
        String password = "password123";

        // Act
        boolean result = controller.doLogin(email, password);

        // Assert
        assertTrue(result);
    }

    @Test
    void testDoLoginWithInvalidCredentials() {
        // Arrange
        String email = "test@example.com";
        String password = "wrongpassword";

        // Act
        boolean result = controller.doLogin(email, password);

        // Assert
        assertFalse(result);
    }

    @Test
    void testGetUserRolesWhenLoggedIn() {
        // Arrange
        String email = "test@example.com";
        String password = "password123";
        controller.doLogin(email, password);

        // Act
        List<UserRoleDTO> roles = controller.getUserRoles();

        // Assert
        assertNotNull(roles);
        assertFalse(roles.isEmpty());
        assertEquals("EMPLOYEE", roles.get(0).getId());
        assertEquals("Employee Role", roles.get(0).getDescription());
    }

    @Test
    void testGetUserRolesWhenNotLoggedIn() {
        // Act
        List<UserRoleDTO> roles = controller.getUserRoles();

        // Assert
        assertNull(roles);
    }

    @Test
    void testDoLogout() {
        // Arrange
        String email = "test@example.com";
        String password = "password123";
        controller.doLogin(email, password);
        assertNotNull(controller.getUserRoles()); // Verify we're logged in

        // Act
        controller.doLogout();

        // Assert
        assertNull(controller.getUserRoles());
    }

    @Test
    void testLoggedInEmployee() {
        // Arrange
        String email = "test@example.com";
        String password = "password123";
        Employee employee = new Employee(email);
        organization.addEmployee(employee);
        controller.doLogin(email, password);

        // Act
        Employee loggedInEmployee = controller.loggedInEmployee(organization);

        // Assert
        assertNotNull(loggedInEmployee);
        assertEquals(email, loggedInEmployee.getEmail());
    }
} 