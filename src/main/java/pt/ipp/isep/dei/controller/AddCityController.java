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
        // Validar house blocks
        Optional<String> houseBlocksValidation = validateHouseBlocks(houseBlocks, map);
        if (houseBlocksValidation.isPresent()) {
            throw new IllegalArgumentException(houseBlocksValidation.get());
        }

        // Validar indústrias
        Optional<String> industryValidation = validateIndustries(map, x, y);
        if (industryValidation.isPresent()) {
            throw new IllegalArgumentException(industryValidation.get());
        }

        if (x < 0 || x >= map.getWidth() || y < 0 || y >= map.getHeight()) {
            throw new IllegalArgumentException("As coordenadas da cidade estão fora dos limites do mapa: (" + x + ", " + y + ")");
        }

        try {
            Employee employee = getEmployeeFromSession();
            return map.addCity(cityName, x, y, houseBlocks, employee);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("❌ Failed to create city: " + e.getMessage());
        }
    }

    private Optional <String> validateCityCenter(Map map, int x, int y) {
        if (x < 0 || x >= map.getWidth() || y < 0 || y >= map.getHeight()) {
            return Optional.of("❌ City position out of map bounds: (" + x + ", " + y + ")");
        }

        if (map.isOccupied(x, y)) {
            return Optional.of("❌ City position is already occupied");
        }

        for (City city : map.getCities()) {
            if (city.getX() == x && city.getY() == y) {
                return Optional.of("❌ City position conflicts with existing city at: (" + x + ", " + y + ")");
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

            if (blockX < 0 || blockX >= map.getWidth() || blockY < 0 || blockY >= map.getHeight()) {
                return Optional.of("❌ Residential block out of map bounds: (" + blockX + ", " + blockY + ")");
            }

            if (map.isOccupied(blockX, blockY)) {
                return Optional.of("❌ Residential block already occupied: (" + blockX + ", " + blockY + ")");
            }
        }
        return Optional.empty();
    }

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

    private Employee getEmployeeFromSession() {
        Email email = getAuthenticationRepository().getCurrentUserSession().getUserId();
        return new Employee(email.getEmail());
    }
}