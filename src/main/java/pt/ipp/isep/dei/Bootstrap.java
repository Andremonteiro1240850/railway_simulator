package pt.ipp.isep.dei;

import pt.ipp.isep.dei.controller.AuthenticationController;
import pt.ipp.isep.dei.domain.Employee;
import pt.ipp.isep.dei.domain.Organization;
import pt.ipp.isep.dei.domain.TaskCategory;
import pt.ipp.isep.dei.repository.*;

public class Bootstrap implements Runnable {

    //Add some task categories to the repository as bootstrap
    public void run() {
        CreateMapRepository mapRepository = Repositories.getInstance().getMapRepository();
        IndustryRepository industryRepository = Repositories.getInstance().getIndustryRepository();
        CityRepository cityRepository = Repositories.getInstance().getCityRepository();
        StationRepository stationRepository = Repositories.getInstance().getStationRepository();
        OrganizationRepository organizationRepository = Repositories.getInstance().getOrganizationRepository();
        addTaskCategories();
        addOrganization(mapRepository, industryRepository, cityRepository, stationRepository);
        addUsers();

    }

    private void addOrganization(CreateMapRepository mapRepository, IndustryRepository industryRepository,CityRepository cityRepository,StationRepository stationRepository) {
        //TODO: add organizations bootstrap here
        //get organization repository
        OrganizationRepository organizationRepository = Repositories.getInstance().getOrganizationRepository();

        Organization organization = new Organization("This Company",mapRepository,cityRepository,industryRepository,stationRepository);
        organization.addEmployee(new Employee("admin@this.app"));
        organization.addEmployee(new Employee("employee@this.app"));
        organization.addEmployee(new Employee("player@this.app"));
        organization.addEmployee(new Employee("editor@this.app"));
        organizationRepository.add(organization);
    }

    private void addTaskCategories() {
        //TODO: add bootstrap Task Categories here
        //get task category repository
        TaskCategoryRepository taskCategoryRepository = Repositories.getInstance().getTaskCategoryRepository();

        taskCategoryRepository.add(new TaskCategory("Analysis"));
        taskCategoryRepository.add(new TaskCategory("Design"));
        taskCategoryRepository.add(new TaskCategory("Implementation"));
        taskCategoryRepository.add(new TaskCategory("Development"));
        taskCategoryRepository.add(new TaskCategory("Testing"));
        taskCategoryRepository.add(new TaskCategory("Deployment"));
        taskCategoryRepository.add(new TaskCategory("Maintenance"));
    }

    private void addUsers() {
        //TODO: add Authentication users here: should be created for each user in the organization
        //get authentication repository
        AuthenticationRepository authenticationRepository = Repositories.getInstance().getAuthenticationRepository();

        authenticationRepository.addUserRole(AuthenticationController.ROLE_ADMIN, AuthenticationController.ROLE_ADMIN);
        authenticationRepository.addUserRole(AuthenticationController.ROLE_EMPLOYEE, AuthenticationController.ROLE_EMPLOYEE);
        authenticationRepository.addUserRole(AuthenticationController.ROLE_EDITOR, AuthenticationController.ROLE_EDITOR);
        authenticationRepository.addUserRole(AuthenticationController.ROLE_PLAYER, AuthenticationController.ROLE_PLAYER);

        authenticationRepository.addUserWithRole("Main Administrator", "admin@this.app", "admin", AuthenticationController.ROLE_ADMIN);
        authenticationRepository.addUserWithRole("Employee", "employee@this.app", "pwd", AuthenticationController.ROLE_EMPLOYEE);
        authenticationRepository.addUserWithRole("Player", "player@this.app", "plr", AuthenticationController.ROLE_PLAYER);
            authenticationRepository.addUserWithRole("Editor", "editor@this.app", "edt", AuthenticationController.ROLE_EDITOR);
    }
}