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

    public AddCityController() {
        getOrganizationRepository();
        getAuthenticationRepository();
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
        try {
            // 1. Basic parameter validation
            if (map == null || cityName == null || cityName.trim().isEmpty()) {
                return Optional.empty();
            }

            // 2. Validate city center position
            if (x < 0 || x >= map.getWidth() || y < 0 || y >= map.getHeight()) {
                return Optional.empty();
            }

            // 3. Check if position is occupied
            if (map.isOccupied(x, y)) {
                return Optional.empty();
            }

            // 4. Validate house blocks
            if (houseBlocks == null || houseBlocks.isEmpty()) {
                return Optional.empty();
            }

            for (int[] block : houseBlocks) {
                int blockX = block[0];
                int blockY = block[1];

                if (blockX < 0 || blockX >= map.getWidth() || blockY < 0 || blockY >= map.getHeight()) {
                    return Optional.empty();
                }

                if (map.isOccupied(blockX, blockY)) {
                    return Optional.empty();
                }
            }

            // 5. Get employee from session
            Employee employee = getEmployeeFromSession();

            // 6. Create and add the city
            return map.addCity(cityName, x, y, houseBlocks, employee);

        } catch (Exception e) {
            System.err.println("Error creating city: " + e.getMessage());
            return Optional.empty();
        }
    }

    private Employee getEmployeeFromSession() {
        Email email = getAuthenticationRepository().getCurrentUserSession().getUserId();
        return new Employee(email.getEmail());
    }
}