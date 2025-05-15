package pt.ipp.isep.dei.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.isep.lei.esoft.auth.UserSession;

import static org.junit.jupiter.api.Assertions.*;

class AuthenticationRepositoryTest {

    private AuthenticationRepository authRepo;

    @BeforeEach
    void setUp() {
        authRepo = new AuthenticationRepository();
    }

    @Test
    void testAddUserRole() {
        String roleId = "admin";
        String roleDesc = "Administrator Role";

        boolean result = authRepo.addUserRole(roleId, roleDesc);

        assertTrue(result, "Role should be added successfully");
    }

    @Test
    void testAddUserWithRole() {
        authRepo.addUserRole("admin", "Administrator Role");

        String name = "Jane Doe";
        String email = "jane.doe@example.com";
        String password = "securePass";

        boolean result = authRepo.addUserWithRole(name, email, password, "admin");

        assertTrue(result, "User with role should be added successfully");
    }

    @Test
    void testLoginAndGetCurrentUserSession() {
        authRepo.addUserRole("admin", "Administrator Role");
        String email = "test.user@example.com";
        String password = "testPass";

        authRepo.addUserWithRole("Test User", email, password, "admin");

        boolean loginResult = authRepo.doLogin(email, password);
        assertTrue(loginResult, "Login should succeed with correct credentials");

        UserSession session = authRepo.getCurrentUserSession();
        assertNotNull(session, "User session should not be null after login");
    }

    @Test
    void testLogout() {
        authRepo.addUserRole("admin", "Administrator Role");
        String email = "logout.test@example.com";
        String password = "logoutPass";

        authRepo.addUserWithRole("Logout User", email, password, "admin");
        authRepo.doLogin(email, password);

        UserSession sessionBeforeLogout = authRepo.getCurrentUserSession();
        assertNotNull(sessionBeforeLogout, "User session should exist before logout");
        assertTrue(sessionBeforeLogout.isLoggedIn(), "User should be logged in before logout");

        authRepo.doLogout();

        UserSession sessionAfterLogout = authRepo.getCurrentUserSession();
        assertNotNull(sessionAfterLogout, "Session object may still exist after logout");
        assertFalse(sessionAfterLogout.isLoggedIn(), "User should not be logged in after logout");
    }
}
