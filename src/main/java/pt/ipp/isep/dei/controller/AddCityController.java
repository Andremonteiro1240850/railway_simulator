package pt.ipp.isep.dei.controller;

import pt.ipp.isep.dei.domain.*;
import pt.ipp.isep.dei.repository.AuthenticationRepository;
import pt.ipp.isep.dei.repository.OrganizationRepository;
import pt.ipp.isep.dei.repository.Repositories;
import pt.isep.lei.esoft.auth.domain.model.Email;

import java.util.List;
import java.util.Optional;


/**
 * Controller responsible for managing the addition of cities to maps.
 * This controller handles the creation and validation of new cities within a map.
 */
public class AddCityController {
    private OrganizationRepository organizationRepository;
    private AuthenticationRepository authenticationRepository;

    /**
     * Default constructor that initializes the required repositories.
     */
    public AddCityController() {
        getOrganizationRepository();
        getAuthenticationRepository();
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

    public Optional<City> createCity(Map map, String cityName, int x, int y, List<int[]> houseBlocks) {
        try {
            // 1. Validação básica dos parâmetros
            if (map == null || cityName == null || cityName.trim().isEmpty()) {
                throw new IllegalArgumentException("Invalid city parameters");
            }

            // 2. Valida posição central da cidade
            Optional<String> cityCenterError = validateCityCenter(map, x, y);
            if (cityCenterError.isPresent()) {
                throw new IllegalArgumentException(cityCenterError.get());
            }

            // 3. Valida house blocks (só depois de confirmar que a posição central é válida)
            Optional<String> houseBlocksError = validateHouseBlocks(houseBlocks, map);
            if (houseBlocksError.isPresent()) {
                throw new IllegalArgumentException(houseBlocksError.get());
            }

            // 4. Obtém o employee da sessão
            Employee employee = getEmployeeFromSession();

            // 5. Tenta adicionar a cidade
            return map.addCity(cityName, x, y, houseBlocks, employee);

        } catch (IllegalArgumentException e) {
            System.err.println("Error creating city: " + e.getMessage());
            return Optional.empty();
        }
    }

    private Optional<String> validateCityCenter(Map map, int x, int y) {
        // Verifica limites do mapa
        if (x < 0 || x >= map.getWidth() || y < 0 || y >= map.getHeight()) {
            return Optional.of("City position out of map bounds");
        }

        // Verifica se está ocupado (qualquer tipo de objeto)
        if (map.isOccupied(x, y)) {
            return Optional.of("City center position is already occupied");
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