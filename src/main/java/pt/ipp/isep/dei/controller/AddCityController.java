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

    public Optional<City> createCity(Map map, int x, int y, String name, List<int[]> houseBlocks) {
        Employee employee = getEmployeeFromSession();
        Optional<Organization> organization = getOrganizationRepository().getOrganizationByEmployee(employee);

        if (organization.isPresent()) {
            return organization.get().addCity(name, x, y, houseBlocks, employee);
        }
        return Optional.empty();
    }

    private Employee getEmployeeFromSession() {
        Email email = getAuthenticationRepository().getCurrentUserSession().getUserId();
        return new Employee(email.getEmail());
    }
}