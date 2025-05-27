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

public class AddIndustryController {
    private OrganizationRepository organizationRepository;
    private AuthenticationRepository authenticationRepository;

    //Repository instances are obtained from the Repositories class
    public AddIndustryController() {
        getOrganizationRepository();
        getAuthenticationRepository();
    }

    //Allows receiving the repositories as parameters for testing purposes
    public AddIndustryController(OrganizationRepository organizationRepository,
                                 AuthenticationRepository authenticationRepository) {
        this.organizationRepository = organizationRepository;
        this.authenticationRepository = authenticationRepository;
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

            //Get the AuthenticationRepository
            authenticationRepository = repositories.getAuthenticationRepository();
        }
        return authenticationRepository;
    }

    public Optional<Industry> createIndustry(int positionX, int positionY, Map map) {
        try {
            // Validações específicas para US02
            validatePosition(map, positionX, positionY);

            Employee employee = getEmployeeFromSession();
            Optional<Organization> organization = getOrganizationRepository().getOrganizationByEmployee(employee);

            return organization.flatMap(org ->
                    org.createIndustry(positionX, positionY, map, employee)
            );
        } catch (IllegalArgumentException e) {
            System.err.println("[Erro US02] " + e.getMessage());
            return Optional.empty();
        }
    }

    private void validatePosition(Map map, int x, int y) {
        if (x < 0 || x >= map.getWidth() || y < 0 || y >= map.getHeight()) {
            throw new IllegalArgumentException("Coordinates out of bounds");
        }
        if (map.hasIndustryAt(x, y)) {
            throw new IllegalArgumentException("Position industry j<UNK> existe");
        }
    }

    private Employee getEmployeeFromSession() {
        Email email = getAuthenticationRepository().getCurrentUserSession().getUserId();
        return new Employee(email.getEmail());
    }


}
