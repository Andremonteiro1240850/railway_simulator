package pt.ipp.isep.dei.controller;

import pt.ipp.isep.dei.domain.*;
import pt.ipp.isep.dei.repository.AuthenticationRepository;
import pt.ipp.isep.dei.repository.CreateMapRepository;
import pt.ipp.isep.dei.repository.OrganizationRepository;
import pt.ipp.isep.dei.repository.Repositories;
import pt.ipp.isep.dei.domain.Map;
import pt.isep.lei.esoft.auth.domain.model.Email;
import pt.ipp.isep.dei.domain.Organization;

import java.util.List;
import java.util.Optional;

public class CreateMapController {
    private OrganizationRepository organizationRepository;
    private AuthenticationRepository authenticationRepository;
    private CreateMapRepository createMapRepository;


    //Repository instances are obtained from the Repositories class
    public CreateMapController() {
        getOrganizationRepository();
        getAuthenticationRepository();
        Repositories.getInstance().getMapRepository();
    }

    //Allows receiving the repositories as parameters for testing purposes
    public CreateMapController(OrganizationRepository organizationRepository,
                               AuthenticationRepository authenticationRepository, CreateMapRepository createMapRepository) {
        this.organizationRepository = organizationRepository;
        this.authenticationRepository = authenticationRepository;
        this.createMapRepository = createMapRepository;

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

    public Optional<Map> createMap(String name, int x, int y) {

        Employee employee = getEmployeeFromSession();
        Optional<Organization> organization = getOrganizationRepository().getOrganizationByEmployee(employee);

        Optional<Map> newMap = Optional.empty();

        if (organization.isPresent()) {
            newMap = organization.get()
                    .createMap(name, x, y, employee);
        }
        return newMap;
    }

    private Employee getEmployeeFromSession() {
        Email email = getAuthenticationRepository().getCurrentUserSession().getUserId();
        return new Employee(email.getEmail());
    }
}



