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
 * Controller responsible for managing the addition of industries to maps.
 * This controller handles the creation and validation of new industries within a map.
 */
public class AddIndustryController {
    private OrganizationRepository organizationRepository;
    private AuthenticationRepository authenticationRepository;
    private CreateMapController createMapController;

    /**
     * Default constructor that initializes the required repositories and controllers.
     */
    public AddIndustryController() {
        this.createMapController = new CreateMapController();
        getOrganizationRepository();
        getAuthenticationRepository();
    }

    /**
     * Constructor for testing purposes that allows dependency injection.
     *
     * @param organizationRepository the organization repository to use
     * @param authenticationRepository the authentication repository to use
     * @param createMapController the map controller to use
     */
    public AddIndustryController(OrganizationRepository organizationRepository,
                                 AuthenticationRepository authenticationRepository,
                                 CreateMapController createMapController) {
        this.organizationRepository = organizationRepository;
        this.authenticationRepository = authenticationRepository;
        this.createMapController = createMapController;
    }

    /**
     * Retrieves the organization repository instance.
     * If not initialized, creates a new instance from the Repositories singleton.
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
     * Retrieves the authentication repository instance.
     * If not initialized, creates a new instance from the Repositories singleton.
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
     * Creates a new industry in the specified map at the given coordinates.
     *
     * @param type the type of industry to create
     * @param positionX the X coordinate for the industry
     * @param positionY the Y coordinate for the industry
     * @param map the map where the industry will be placed
     * @return Optional containing the created industry if successful, empty otherwise
     * @throws IllegalArgumentException if the industry type is null, coordinates are out of bounds,
     *         or the position is already occupied
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
     * Validates if a position in the map is valid for placing an industry.
     *
     * @param map the map to validate the position in
     * @param x the X coordinate to validate
     * @param y the Y coordinate to validate
     * @param industry the industry to be placed
     * @throws IllegalArgumentException if the position is invalid or already occupied
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
     * Retrieves the current employee from the authentication session.
     *
     * @return the employee associated with the current session
     */
    private Employee getEmployeeFromSession() {
        Email email = getAuthenticationRepository().getCurrentUserSession().getUserId();
        return new Employee(email.getEmail());
    }

}
