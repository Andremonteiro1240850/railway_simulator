package pt.ipp.isep.dei.controller;

import pt.ipp.isep.dei.domain.*;
import pt.ipp.isep.dei.repository.*;
import pt.isep.lei.esoft.auth.domain.model.Email;

import java.util.List;
import java.util.Optional;

public class BuildStationController {
    private OrganizationRepository organizationRepository;
    private AuthenticationRepository authenticationRepository;
    private StationRepository stationRepository;

    //Repository instances are obtained from the Repositories class
    public BuildStationController() {
        getOrganizationRepository();
        getAuthenticationRepository();
        Repositories.getInstance().getStationRepository();
    }

    //Allows receiving the repositories as parameters for testing purposes
    public BuildStationController(OrganizationRepository organizationRepository,
                                AuthenticationRepository authenticationRepository,StationRepository stationRepository) {
        this.organizationRepository = organizationRepository;
        this.authenticationRepository = authenticationRepository;
        this.stationRepository = stationRepository;
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

    public Optional<Station> createStation(String type, String name,String region) {



        Employee employee = getEmployeeFromSession();
        Optional<Organization> organization = getOrganizationRepository().getOrganizationByEmployee(employee);

        Optional<Station> newTask = Optional.empty();

        if (organization.isPresent()) {
            newTask = organization.get()
                    .createStation(type,name,region, employee);
        }
        return newTask;
    }

    private Employee getEmployeeFromSession() {
        Email email = getAuthenticationRepository().getCurrentUserSession().getUserId();
        return new Employee(email.getEmail());
    }


}

