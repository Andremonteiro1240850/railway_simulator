package pt.ipp.isep.dei.repository;

import pt.isep.lei.esoft.auth.AuthFacade;
import pt.isep.lei.esoft.auth.UserSession;

/**
 * Repository that encapsulates authentication logic using the AuthFacade.
 * Provides methods for login, logout, session management, and role-based user registration.
 */
public class AuthenticationRepository {

    private final AuthFacade authenticationFacade;

    /**
     * Default constructor. Initializes the underlying authentication facade.
     */
    public AuthenticationRepository() {
        authenticationFacade = new AuthFacade();
    }

    /**
     * Attempts to log in a user with the given credentials.
     *
     * @param email the user's email
     * @param pwd   the user's password
     * @return true if login is successful; false otherwise
     */
    public boolean doLogin(String email, String pwd) {
        return authenticationFacade.doLogin(email, pwd).isLoggedIn();
    }

    /**
     * Logs out the currently authenticated user.
     */
    public void doLogout() {
        authenticationFacade.doLogout();
    }

    /**
     * Retrieves the current authenticated user session.
     *
     * @return the current user session
     */
    public UserSession getCurrentUserSession() {
        return authenticationFacade.getCurrentUserSession();
    }

    /**
     * Adds a new user role to the system.
     *
     * @param id          the unique ID of the role
     * @param description a human-readable description of the role
     * @return true if the role was successfully added; false otherwise
     */
    public boolean addUserRole(String id, String description) {
        return authenticationFacade.addUserRole(id, description);
    }

    /**
     * Registers a new user with a specific role.
     *
     * @param name   the full name of the user
     * @param email  the user's email
     * @param pwd    the user's password
     * @param roleId the role ID to assign to the user
     * @return true if the user was successfully added; false otherwise
     */
    public boolean addUserWithRole(String name, String email, String pwd, String roleId) {
        return authenticationFacade.addUserWithRole(name, email, pwd, roleId);
    }
}
