package pt.ipp.isep.dei.controller;

import pt.ipp.isep.dei.domain.Employee;
import pt.ipp.isep.dei.domain.Organization;
import pt.ipp.isep.dei.repository.AuthenticationRepository;
import pt.ipp.isep.dei.repository.Repositories;
import pt.isep.lei.esoft.auth.mappers.dto.UserRoleDTO;

import java.util.List;

/**
 * Controller responsible for handling user authentication operations.
 * This controller manages user login, logout, and role-based access control.
 */
public class AuthenticationController {

    public static final String ROLE_ADMIN = "ADMINISTRATOR";
    public static final String ROLE_EMPLOYEE = "EMPLOYEE";
    public static final String ROLE_EDITOR = "EDITOR";
    public static final String ROLE_PLAYER= "PLAYER";

    //private final ApplicationSession applicationSession;
    private final AuthenticationRepository authenticationRepository;
    private String lastLoggedInEmail = null;

    /**
     * Default constructor that initializes the authentication repository.
     */
    public AuthenticationController() {
        this.authenticationRepository = Repositories.getInstance().getAuthenticationRepository();
    }

    /**
     * Constructor for testing purposes that allows dependency injection.
     *
     * @param authenticationRepository the authentication repository to use
     */
    public AuthenticationController(AuthenticationRepository authenticationRepository) {
        this.authenticationRepository = authenticationRepository;
    }

    /**
     * Attempts to log in a user with the provided credentials.
     *
     * @param email the user's email address
     * @param pwd the user's password
     * @return true if login is successful, false otherwise
     */
    public boolean doLogin(String email, String pwd) {
        try {
            boolean success = authenticationRepository.doLogin(email, pwd);
            if (success) {
                lastLoggedInEmail = email; // <-- guardamos o email
            }
            return success;
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }

    /**
     * Retrieves the roles associated with the currently logged-in user.
     *
     * @return list of user roles if user is logged in, null otherwise
     */
    public List<UserRoleDTO> getUserRoles() {
        if (authenticationRepository.getCurrentUserSession().isLoggedIn()) {
            return authenticationRepository.getCurrentUserSession().getUserRoles();
        }
        return null;
    }

    /**
     * Logs out the current user and clears the last logged in email.
     */
    public void doLogout() {
        authenticationRepository.doLogout();
        lastLoggedInEmail = null;
    }

    public Employee loggedInEmployee(Organization organization) {
        if (lastLoggedInEmail == null) return null;
        return organization.findEmployeeByEmail(lastLoggedInEmail);
    }

}