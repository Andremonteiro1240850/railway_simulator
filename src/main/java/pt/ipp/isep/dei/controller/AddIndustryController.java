package pt.ipp.isep.dei.controller;

import pt.ipp.isep.dei.domain.Employee;
import pt.ipp.isep.dei.domain.Industry;
import pt.ipp.isep.dei.domain.Map;
import pt.ipp.isep.dei.domain.Organization;
import pt.ipp.isep.dei.repository.AuthenticationRepository;
import pt.ipp.isep.dei.repository.OrganizationRepository;
import pt.ipp.isep.dei.repository.Repositories;
import pt.isep.lei.esoft.auth.domain.model.Email;

import java.util.Optional;
import pt.ipp.isep.dei.domain.enums.IndustryType;

/**
 * Controller responsible for handling the creation of new industries
 * and associating them with maps and organizations.
 */
public class AddIndustryController {
    private OrganizationRepository organizationRepository;
    private AuthenticationRepository authenticationRepository;
    private CreateMapController createMapController;

    /**
     * Default constructor. Initializes repositories and the map controller.
     */
    public AddIndustryController() {
        this.createMapController = new CreateMapController();
        getOrganizationRepository();
        getAuthenticationRepository();
    }

    /**
     * Constructor for dependency injection, useful for testing.
     *
     * @param organizationRepository      the organization repository
     * @param authenticationRepository    the authentication repository
     * @param createMapController         the controller responsible for map-related actions
     */
    public AddIndustryController(OrganizationRepository organizationRepository,
                                 AuthenticationRepository authenticationRepository,
                                 CreateMapController createMapController) {
        this.organizationRepository = organizationRepository;
        this.authenticationRepository = authenticationRepository;
        this.createMapController = createMapController;
    }

    /**
     * Retrieves the organization repository, initializing it if necessary.
     *
     * @return the organization repository instance
     */
    private OrganizationRepository getOrganizationRepository() {
        if (organizationRepository == null) {
            Repositories repositories = Repositories.getInstance();
            organizationRepository = repositories.getOrganizationRepository();
        }
        return organizationRepository;
    }

    /**
     * Retrieves the authentication repository, initializing it if necessary.
     *
     * @return the authentication repository instance
     */
    private AuthenticationRepository getAuthenticationRepository() {
        if (authenticationRepository == null) {
            Repositories repositories = Repositories.getInstance();
            authenticationRepository = repositories.getAuthenticationRepository();
        }
        return authenticationRepository;
    }

    /**
     * Creates a new industry in the specified position of the map if valid.
     *
     * @param type      the type of the industry
     * @param positionX the X coordinate on the map
     * @param positionY the Y coordinate on the map
     * @param map       the map where the industry will be placed
     * @return an Optional containing the created industry, or empty if creation failed
     */
    public Optional<Industry> createIndustry(IndustryType type, int positionX, int positionY, Map map) {
        try {
            if (type == null) {
                throw new IllegalArgumentException("Industry type cannot be null");
            }

            if (positionX < 0 || positionX >= map.getWidth() || positionY < 0 || positionY >= map.getHeight()) {
                throw new IllegalArgumentException("Coordinates out of bounds");
            }

            if (map.isOccupied(positionX, positionY)) {
                throw new IllegalArgumentException("Position already occupied");
            }

            Employee employee = getEmployeeFromSession();
            Optional<Organization> organization = getOrganizationRepository().getOrganizationByEmployee(employee);

            return organization.map(org -> {
                Industry newIndustry = new Industry(type, map, positionX, positionY);
                map.addIndustry(newIndustry);
                return newIndustry;
            });
        } catch (IllegalArgumentException e) {
            System.err.println("[Error US02] " + e.getMessage());
            return Optional.empty();
        }
    }

    /**
     * Validates the position of an industry on the map.
     *
     * @param map      the map to validate against
     * @param x        the X coordinate
     * @param y        the Y coordinate
     * @param industry the industry to be validated
     */
    private void validatePosition(Map map, int x, int y, Industry industry) {
        if (x < 0 || x >= map.getWidth() || y < 0 || y >= map.getHeight()) {
            throw new IllegalArgumentException("Coordinates out of bounds");
        }

        Optional<String> result = createMapController.addIndustryToMap(map, industry);
        if (result.isPresent()) {
            throw new IllegalArgumentException("Position already occupied by another industry");
        }
    }

    /**
     * Retrieves the employee associated with the current user session.
     *
     * @return the employee currently logged in
     */
    private Employee getEmployeeFromSession() {
        Email email = getAuthenticationRepository().getCurrentUserSession().getUserId();
        return new Employee(email.getEmail());
    }
}

