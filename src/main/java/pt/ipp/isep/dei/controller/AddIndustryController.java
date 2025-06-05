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

public class AddIndustryController {
    private OrganizationRepository organizationRepository;
    private AuthenticationRepository authenticationRepository;
    private CreateMapController createMapController;

    // Inicialização dos repositórios
    public AddIndustryController() {
        this.createMapController = new CreateMapController(); // Inicializando o controlador de mapa
        getOrganizationRepository();
        getAuthenticationRepository();
    }

    // Permite receber os repositórios como parâmetros para fins de teste
    public AddIndustryController(OrganizationRepository organizationRepository,
                                 AuthenticationRepository authenticationRepository,
                                 CreateMapController createMapController) {
        this.organizationRepository = organizationRepository;
        this.authenticationRepository = authenticationRepository;
        this.createMapController = createMapController; // Atribuindo o CreateMapController
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

    public Optional<Industry> createIndustry(IndustryType type, int positionX, int positionY, Map map, Industry industry) {
        try {
            // Verifica se o tipo de indústria é válido
            if (type == null) {
                throw new IllegalArgumentException("Industry type cannot be null");
            }

            // Validação da posição no mapa
            validatePosition(map, positionX, positionY, industry);

            // Obtém o funcionário atual da sessão
            Employee employee = getEmployeeFromSession();

            // Obtém a organização do funcionário
            Optional<Organization> organization = getOrganizationRepository().getOrganizationByEmployee(employee);

            // Cria a indústria e adiciona ao mapa
            return organization.flatMap(org -> {
                // Cria a nova indústria
                Industry newIndustry = new Industry(type, map, positionX, positionY);

                // Adiciona a indústria ao mapa
                map.addIndustry(newIndustry);

                return Optional.of(newIndustry);
            });
        } catch (IllegalArgumentException e) {
            System.err.println("[Error US02] " + e.getMessage());
            return Optional.empty();
        }
    }

    private void validatePosition(Map map, int x, int y, Industry industry) {
        // Verificação se a posição está fora dos limites do mapa
        if (x < 0 || x >= map.getWidth() || y < 0 || y >= map.getHeight()) {
            throw new IllegalArgumentException("Coordinates out of bounds");
        }

        // Verificação se a posição já está ocupada por outra indústria
        Optional<String> result = createMapController.addIndustryToMap(map, industry);
        if (result.isPresent()) {
            throw new IllegalArgumentException("Position already occupied by another industry");
        }
    }

    private Employee getEmployeeFromSession() {
        Email email = getAuthenticationRepository().getCurrentUserSession().getUserId();
        return new Employee(email.getEmail());
    }

}
