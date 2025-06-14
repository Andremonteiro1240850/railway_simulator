package pt.ipp.isep.dei.controller;

import pt.ipp.isep.dei.domain.*;
import pt.ipp.isep.dei.repository.AuthenticationRepository;
import pt.ipp.isep.dei.repository.OrganizationRepository;
import pt.ipp.isep.dei.repository.Repositories;
import pt.isep.lei.esoft.auth.domain.model.Email;

import java.util.List;
import java.util.Optional;

/**
 * Controller responsible for creating and adding a new city to a map.
 */
public class AddCityController {
    private OrganizationRepository organizationRepository;
    private AuthenticationRepository authenticationRepository;

    /**
     * Constructs the AddCityController and initializes repository references.
     */
    public AddCityController() {
        getOrganizationRepository();
        getAuthenticationRepository();
    }

    /**
     * Gets the organization repository (singleton instance).
     *
     * @return the organization repository.
     */
    private OrganizationRepository getOrganizationRepository() {
        if (organizationRepository == null) {
            Repositories repositories = Repositories.getInstance();
            organizationRepository = repositories.getOrganizationRepository();
        }
        return organizationRepository;
    }

    /**
     * Gets the authentication repository (singleton instance).
     *
     * @return the authentication repository.
     */
    private AuthenticationRepository getAuthenticationRepository() {
        if (authenticationRepository == null) {
            Repositories repositories = Repositories.getInstance();
            authenticationRepository = repositories.getAuthenticationRepository();
        }
        return authenticationRepository;
    }

    /**
     * Attempts to create and add a city to the map.
     *
     * @param map         The map where the city should be added.
     * @param cityName    The name of the city.
     * @param x           The X coordinate of the city center.
     * @param y           The Y coordinate of the city center.
     * @param houseBlocks A list of coordinates representing the house blocks of the city.
     * @return An Optional containing the created city if successful, or an empty Optional if failed.
     */
    public Optional<City> createCity(Map map, String cityName, int x, int y, List<int[]> houseBlocks) {
        try {
            if (map == null || cityName == null || cityName.trim().isEmpty()) {
                throw new IllegalArgumentException("Invalid city parameters");
            }

            Optional<String> cityCenterError = validateCityCenter(map, x, y);
            if (cityCenterError.isPresent()) {
                throw new IllegalArgumentException(cityCenterError.get());
            }

            Optional<String> houseBlocksError = validateHouseBlocks(houseBlocks, map);
            if (houseBlocksError.isPresent()) {
                throw new IllegalArgumentException(houseBlocksError.get());
            }

            Employee employee = getEmployeeFromSession();
            return map.addCity(cityName, x, y, houseBlocks, employee);

        } catch (IllegalArgumentException e) {
            System.err.println("Error creating city: " + e.getMessage());
            return Optional.empty();
        }
    }

    /**
     * Validates whether the given coordinates for the city center are within bounds and unoccupied.
     *
     * @param map The map to validate against.
     * @param x   X coordinate.
     * @param y   Y coordinate.
     * @return An Optional error message if invalid, or empty if valid.
     */
    private Optional<String> validateCityCenter(Map map, int x, int y) {
        if (x < 0 || x >= map.getWidth() || y < 0 || y >= map.getHeight()) {
            return Optional.of("City position out of map bounds");
        }
        if (map.isOccupied(x, y)) {
            return Optional.of("City center position is already occupied");
        }
        return Optional.empty();
    }

    /**
     * Validates whether all house blocks are within bounds and unoccupied.
     *
     * @param houseBlocks List of coordinates to validate.
     * @param map         The map to validate against.
     * @return An Optional error message if invalid, or empty if valid.
     */
    private Optional<String> validateHouseBlocks(List<int[]> houseBlocks, Map map) {
        if (houseBlocks == null || houseBlocks.isEmpty()) {
            return Optional.of("❌ At least one house block is required");
        }

        for (int[] block : houseBlocks) {
            int blockX = block[0];
            int blockY = block[1];

            if (blockX < 0 || blockX >= map.getWidth() || blockY < 0 || blockY >= map.getHeight()) {
                return Optional.of("❌ Residential block out of map bounds: (" + blockX + ", " + blockY + ")");
            }

            if (map.isOccupied(blockX, blockY)) {
                return Optional.of("❌ Residential block already occupied: (" + blockX + ", " + blockY + ")");
            }
        }
        return Optional.empty();
    }

    /**
     * Validates whether the city can be placed without overlapping existing industries.
     *
     * @param map The map to check.
     * @param x   X coordinate of the proposed city.
     * @param y   Y coordinate of the proposed city.
     * @return An Optional error message if invalid, or empty if valid.
     */
    private Optional<String> validateIndustries(Map map, int x, int y) {
        if (x < 0 || x >= map.getWidth() || y < 0 || y >= map.getHeight()) {
            return Optional.of("❌ City position out of map bounds: (" + x + ", " + y + ")");
        }

        if (map.isOccupied(x, y)) {
            return Optional.of("❌ City position is already occupied");
        }

        for (Industry industry : map.getIndustries()) {
            if (industry.getPositionX() == x && industry.getPositionY() == y) {
                return Optional.of("❌ City position conflicts with existing industry at: (" + x + ", " + y + ")");
            }
        }
        return Optional.empty();
    }

    /**
     * Retrieves the currently logged-in employee from the session.
     *
     * @return The Employee object associated with the current session.
     */
    private Employee getEmployeeFromSession() {
        Email email = getAuthenticationRepository().getCurrentUserSession().getUserId();
        return new Employee(email.getEmail());
    }
}
