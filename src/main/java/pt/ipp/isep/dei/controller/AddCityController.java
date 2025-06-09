// AddCityController.java
package pt.ipp.isep.dei.controller;

import pt.ipp.isep.dei.domain.*;
import pt.ipp.isep.dei.repository.AuthenticationRepository;
import pt.ipp.isep.dei.repository.OrganizationRepository;
import pt.ipp.isep.dei.repository.Repositories;
import pt.isep.lei.esoft.auth.domain.model.Email;

import java.util.List;
import java.util.Optional;

public class AddCityController {
    private OrganizationRepository organizationRepository;
    private AuthenticationRepository authenticationRepository;
    private CreateMapController createMapController;

    public AddCityController() {
        getOrganizationRepository();
        getAuthenticationRepository();
        this.createMapController = new CreateMapController();
    }

    private OrganizationRepository getOrganizationRepository() {
        if (organizationRepository == null) {
            Repositories repositories = Repositories.getInstance();
            organizationRepository = repositories.getOrganizationRepository();
        }
        return organizationRepository;
    }

    private AuthenticationRepository getAuthenticationRepository() {
        if (authenticationRepository == null) {
            Repositories repositories = Repositories.getInstance();
            authenticationRepository = repositories.getAuthenticationRepository();
        }
        return authenticationRepository;
    }

    public Optional<City> createCity(Map map, String cityName, int x, int y, List<int[]> houseBlocks) {
        // Validate house blocks
        Optional<String> houseBlocksValidation = validateHouseBlocks(houseBlocks, map);
        if (houseBlocksValidation.isPresent()) {
            throw new IllegalArgumentException(houseBlocksValidation.get());
        }

        // Validate industries
        Optional<String> industryValidation = validateIndustries(map, x, y);
        if (industryValidation.isPresent()) {
            throw new IllegalArgumentException(industryValidation.get());
        }
1
        // Validate and add city using CreateMapController
        Optional<String> cityValidation = createMapController.addCityToMap(map, cityName, x, y, houseBlocks, getEmployeeFromSession());
        if (cityValidation.isPresent()) {
            throw new IllegalArgumentException(cityValidation.get());
        }

        // Add city to organization's map
        Employee employee = getEmployeeFromSession();
        Optional<Organization> organization = getOrganizationRepository().getOrganizationByEmployee(employee);

        if (organization.isPresent()) {
            // Find the newly created city in the map
            for (City city : map.getCities()) {
                if (city.getName().equalsIgnoreCase(cityName)) {
                    return Optional.of(city);
                }
            }
        }
        return Optional.empty();
    }

    private Optional<String> validateHouseBlocks(List<int[]> houseBlocks, Map map) {
        if (houseBlocks == null || houseBlocks.isEmpty()) {
            return Optional.of("❌ At least one house block is required");
        }

        for (int[] block : houseBlocks) {
            int blockX = block[0];
            int blockY = block[1];

            // Check if blocks are within map bounds
            if (blockX < 0 || blockX >= map.getWidth() || blockY < 0 || blockY >= map.getHeight()) {
                return Optional.of("❌ Residential block out of map bounds: (" + blockX + ", " + blockY + ")");
            }

            // Check if blocks are already occupied
            if (map.isOccupied(blockX, blockY)) {
                return Optional.of("❌ Residential block already occupied: (" + blockX + ", " + blockY + ")");
            }
        }
        return Optional.empty();
    }

    private Optional<String> validateIndustries(Map map, int x, int y) {
        // Check if city position is within map bounds
        if (x < 0 || x >= map.getWidth() || y < 0 || y >= map.getHeight()) {
            return Optional.of("❌ City position is out of map bounds");
        }

        // Check if city position is already occupied
        if (map.isOccupied(x, y)) {
            return Optional.of("❌ City position is already occupied");
        }

        // Check for industry conflicts
        for (Industry industry : map.getIndustries()) {
            int industryX = industry.getPositionX();
            int industryY = industry.getPositionY();
            if (industryX == x && industryY == y) {
                return Optional.of("❌ City position conflicts with existing industry at: (" + industryX + ", " + industryY + ")");
            }
        }
        return Optional.empty();
    }

    private Employee getEmployeeFromSession() {
        Email email = getAuthenticationRepository().getCurrentUserSession().getUserId();
        return new Employee(email.getEmail());
    }
}