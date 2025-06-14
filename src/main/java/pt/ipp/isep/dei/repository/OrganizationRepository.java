package pt.ipp.isep.dei.repository;

import pt.ipp.isep.dei.domain.Employee;
import pt.ipp.isep.dei.domain.Organization;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrganizationRepository {
    private final List<Organization> organizations;

    public OrganizationRepository() {
        organizations = new ArrayList<>();
    }

    public Optional<Organization> getOrganizationByEmployee(Employee employee) {
        for (Organization organization : organizations) {
            if (organization.getEmployees().contains(employee)) {
                return Optional.of(organization);
            }
        }
        return Optional.empty();
    }

    public Optional<Organization> getOrganizationByEmployeeEmail(String email) {
        Optional<Organization> returnOrganization = Optional.empty();

        for (Organization organization : organizations) {
            if (organization.anyEmployeeHasEmail(email)) {
                returnOrganization = Optional.of(organization);
            }
        }

        return returnOrganization;
    }

    public Optional<Organization> add(Organization organization) {
        Optional<Organization> newOrganization = Optional.empty();
        boolean operationSuccess = false;

        if (validateOrganization(organization)) {
            newOrganization = Optional.of(organization);
            operationSuccess = organizations.add(organization);
        }

        if (!operationSuccess) {
            newOrganization = Optional.empty();
        }

        return newOrganization;
    }

    private boolean validateOrganization(Organization organization) {
        return !organizations.contains(organization);
    }
}