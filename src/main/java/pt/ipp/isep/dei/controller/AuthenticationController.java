package pt.ipp.isep.dei.controller;

import pt.ipp.isep.dei.domain.Employee;
import pt.ipp.isep.dei.domain.Organization;
import pt.ipp.isep.dei.repository.AuthenticationRepository;
import pt.ipp.isep.dei.repository.Repositories;
import pt.isep.lei.esoft.auth.mappers.dto.UserRoleDTO;

import java.util.List;

/**
 * Controller responsible for managing user authentication, roles, and session-related operations.
 */
public class AuthenticationController {

    public static final String ROLE_ADMIN = "ADMINISTRATOR";
    public static final String ROLE_EMPLOYEE = "EMPLOYEE";
    public static final String ROLE_EDITOR = "EDITOR";
    public static final String ROLE_PLAYER = "PLAYER";

    private final AuthenticationRepository authenticationRepository;
    private String lastLoggedInEmail = null;

    /**
     * Default constructor. Initializes the authentication repository.
     */
    public AuthenticationController() {
        this.authenticationRepository = Repositories.getInstance().getAuthenticationRepository();
    }

    /**
     * Attempts to log in a user using the provided email and password.
     * If successful, the user's email is stored for later use.
     *
     * @param email the user's email
     * @param pwd   the user's password
     * @return true if login is successful; false otherwise
     */
    public boolean doLogin(String email, String pwd) {
        try {
            boolean success = authenticationRepository.doLogin(email, pwd);
            if (success) {
                lastLoggedInEmail = email;
            }
            return success;
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }

    /**
     * Retrieves the roles associated with the currently logged-in user.
     *
     * @return a list of user role DTOs, or null if no user is logged in
     */
    public List<UserRoleDTO> getUserRoles() {
        if (authenticationRepository.getCurrentUserSession().isLoggedIn()) {
            return authenticationRepository.getCurrentUserSession().getUserRoles();
        }
        return null;
    }

    /**
     * Logs out the current user session.
     */
    public void doLogout() {
        authenticationRepository.doLogout();
    }

    /**
     * Returns the employee object associated with the last logged-in user's email,
     * if that employee belongs to the specified organization.
     *
     * @param organization the organization to search for the employee
     * @return the matching employee, or null if not found or not logged in
     */
    public Employee loggedInEmployee(Organization organization) {
        if (lastLoggedInEmail == null) return null;
        return organization.findEmployeeByEmail(lastLoggedInEmail);
    }
}
