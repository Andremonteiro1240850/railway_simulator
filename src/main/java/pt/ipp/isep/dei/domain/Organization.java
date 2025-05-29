package pt.ipp.isep.dei.domain;

import pt.ipp.isep.dei.repository.CityRepository;
import pt.ipp.isep.dei.repository.CreateMapRepository;
import pt.ipp.isep.dei.repository.IndustryRepository;
import pt.ipp.isep.dei.repository.StationRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class Organization {
    private final String vatNumber;
    private final List<Employee> employees;
    private final List<Map> maps = new ArrayList<>();
    private final List<Task> tasks;
    private final List<City> cities;
    private final List<Industry> industries;
    private final List<Station> stations;
    private final CreateMapRepository mapRepository;
    private final CityRepository cityRepository;
    private final IndustryRepository industryRepository;
    private final StationRepository stationRepository;
    private String name;
    private String website;
    private String phone;
    private String email;
    private  CreateMapRepository createMapRepository;

    /**
     * This method is the constructor of the organization.
     *
     * @param vatNumber The vat number of the organization. This is the identity of the organization, therefore it
     *                  cannot be changed.
     */
    public Organization(String vatNumber, CreateMapRepository mapRepository,CityRepository cityRepository, IndustryRepository industryRepository, StationRepository stationRepository) {
        if (mapRepository == null) {
            throw new IllegalArgumentException("MapRepository cannot be null");
        }
        this.vatNumber = vatNumber;
        employees = new ArrayList<>();
        tasks = new ArrayList<>();
        cities = new ArrayList<>();
        industries = new ArrayList<>();
        stations = new ArrayList<>();
        this.mapRepository = mapRepository;
        this.cityRepository = cityRepository;
        this.industryRepository = new IndustryRepository();
        this.stationRepository = stationRepository;
    }



    /**
     * This method checks if an employee works for the organization.
     *
     * @param employee The employee to be checked.
     * @return True if the employee works for the organization.
     */
    public boolean employs(Employee employee) {
        return employees.contains(employee);
    }

    /**
     * This method creates a new task.
     *
     * @param reference            The reference of the task to be created.
     * @param description          The description of the task to be created.
     * @param informalDescription  The informal description of the task to be created.
     * @param technicalDescription The technical description of the task to be created.
     * @param duration             The duration of the task to be created.
     * @param cost                 The cost of the task to be created.
     * @param taskCategory         The task category of the task to be created.
     * @param employee             The employee of the task to be created.
     * @return
     */
    public Optional<Task> createTask(String reference, String description, String informalDescription,
                                     String technicalDescription, int duration, double cost,
                                     TaskCategory taskCategory, Employee employee) {

        //TODO: we could also check if the employee works for the organization before proceeding
        //checkIfEmployeeWorksForOrganization(employee);

        // When a Task is added, it should fail if the Task already exists in the list of Tasks.
        // In order to not return null if the operation fails, we use the Optional class.
        Optional<Task> optionalValue = Optional.empty();

        Task task = new Task(reference, description, informalDescription, technicalDescription, duration, cost,
                taskCategory, employee);

        if (addTask(task)) {
            optionalValue = Optional.of(task);
        }
        return optionalValue;
    }
    public Optional<Station> createStation(String reference, String name, String region, String type, Employee employee) {
        Optional<Station> optionalValue = Optional.empty();
        Station station = new Station(reference, name, type, region);

        Optional<Station> repositoryResult = stationRepository.add(station);
        if (repositoryResult.isPresent()) {
            if (addStation(station)) {
                return Optional.of(station);
            }
            stationRepository.getAll().remove(station);
        }

        if (addStation(station)) {
            optionalValue = Optional.of(station);
        }
        return optionalValue;
    }
    public Optional<Industry> createIndustry(int x, int y,Map map,  Employee employee) {

        //TODO: we could also check if the employee works for the organization before proceeding
        //checkIfEmployeeWorksForOrganization(employee);

        // When a Task is added, it should fail if the Task already exists in the list of Tasks.
        // In order to not return null if the operation fails, we use the Optional class.
        Optional<Industry> optionalValue = Optional.empty();

        Industry task = new Industry(map,x,y, employee);
        Optional<Industry> repositoryResult = industryRepository.add(task);
        if (repositoryResult.isPresent()) {
            if (addIndustry(task)) {
                return Optional.of(task);
            }
            // Se falhar em adicionar localmente, remove do repositório
            industryRepository.getAll().remove(task);
        }

        if (addIndustry(task)) {
            optionalValue = Optional.of(task);
        }
        return optionalValue;
    }
    public Optional<Map> createMap(String name, int x, int y,Employee employee) {

        //TODO: we could also check if the employee works for the organization before proceeding
        //checkIfEmployeeWorksForOrganization(employee);

        // When a Task is added, it should fail if the Task already exists in the list of Tasks.
        // In order to not return null if the operation fails, we use the Optional class.
        Optional<Map> optionalValue = Optional.empty();

         Map task = new Map(name,x,y,employee);
        Optional<Map> repositoryResult = mapRepository.add(task);
        if (repositoryResult.isPresent()) {
            if (addMap(task)) {
                return Optional.of(task);
            }
            // Se falhar em adicionar localmente, remove do repositório
            mapRepository.getAll().remove(task);
        }

        if (addMap(task)) {
            optionalValue = Optional.of(task);
        }
        return optionalValue;
    }
    public Optional<City> createCity(Map map,int x, int y, Employee employee) {

        //TODO: we could also check if the employee works for the organization before proceeding
        //checkIfEmployeeWorksForOrganization(employee);

        // When a Task is added, it should fail if the Task already exists in the list of Tasks.
        // In order to not return null if the operation fails, we use the Optional class.
        Optional<City> optionalValue = Optional.empty();

        City task = new City(map,x,y, employee);

        if (addCity(task)) {
            optionalValue = Optional.of(task);
        }
        return optionalValue;
    }
    private double budget=10000.0;

    public double getBudget() { return budget; }

    public void deductFromBudget(double amount) {
        if (amount > 0 && amount <= budget) {
            budget -= amount;
        }
    }

    /**
     * This method adds a task to the list of tasks.
     *
     * @param task The task to be added.
     * @return True if the task was added successfully.
     */
    private boolean addTask(Task task) {
        boolean success = false;
        if (validate(task)) {
            // A clone of the task is added to the list of tasks, to avoid side effects and outside manipulation.
            success = tasks.add(task.clone());
        }
        return success;
    }
    private boolean addStation(Station task) {
        boolean success = false;
        if (validateStation(task)) {
            // A clone of the task is added to the list of tasks, to avoid side effects and outside manipulation.
            success = stations.add(task.clone());
        }
        return success;
    }
    private boolean addMap(Map map) {
        boolean success = false;
        if (validateMap(map)) {
            // A clone of the task is added to the list of tasks, to avoid side effects and outside manipulation.
            success = maps.add(map.mapClone());
        }
        return success;
    }
    private boolean addCity(City city) {
        boolean success = false;
        if (validateCity(city)) {
            // A clone of the task is added to the list of tasks, to avoid side effects and outside manipulation.
            success = cities.add(city.clone());
        }
        return success;
    }
    private boolean addIndustry(Industry industry) {
        boolean success = false;
        if (validateIndustry(industry)) {
            // A clone of the task is added to the list of tasks, to avoid side effects and outside manipulation.
            success = industries.add(industry.clone());
        }
        return success;
    }


    /**
     * This method validates the task, checking for duplicates.
     *
     * @param task The task to be validated.
     * @return True if the task is valid.
     */
    private boolean validateMap (Map task) {
        return tasksDoNotContain(task);
    }
    private boolean validate (Task task) {
        return tasksDoNotContain(task);
    }
    private boolean validateStation (Station task) {
        return tasksDoNotContain(task);
    }
    private boolean validateCity (City task) {
        return tasksDoNotContain(task);
    }
    private boolean validateIndustry (Industry task){return tasksDoNotContain(task);}

    /**
     * This method checks if the task is already in the list of tasks.
     *
     * @param task The task to be checked.
     * @return True if the task is not in the list of tasks.
     */
    private boolean tasksDoNotContain(Map task) {
        return !maps.contains(task);
    }
    private boolean tasksDoNotContain(Task task) {
        return !tasks.contains(task);
    }
    private boolean tasksDoNotContain(City task) {
        return !cities.contains(task);
    }
    private boolean tasksDoNotContain(Industry task) {return !industries.contains(task);}
    private boolean tasksDoNotContain(Station task) {return !industries.contains(task);}

    /**
     * This methos checks if the organization has an employee with the given email.
     *
     * @param email The email to be checked.
     * @return True if the organization has an employee with the given email.
     */
    public boolean anyEmployeeHasEmail(String email) {
        boolean result = false;
        for (Employee employee : employees) {
            if (employee.hasEmail(email)) {
                result = true;
            }
        }
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Organization)) {
            return false;
        }
        Organization that = (Organization) o;
        return vatNumber.equals(that.vatNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vatNumber);
    }

    //add employee to organization
    public boolean addEmployee(Employee employee) {
        boolean success = false;
        if (validateEmployee(employee)) {
            success = employees.add(employee);
        }
        return success;
    }

    private boolean validateEmployee(Employee employee) {
        return employeesDoNotContain(employee);
    }

    private boolean employeesDoNotContain(Employee employee) {
        return !employees.contains(employee);
    }

    //Clone organization
    public Organization clone() {
        Organization clone = new Organization(this.vatNumber, this.mapRepository,this.cityRepository,this.industryRepository,this.stationRepository);
        clone.name = (this.name);
        clone.website = (this.website);
        clone.phone = (this.phone);
        clone.email = (this.email);

        for (Employee in : this.employees) {
            clone.employees.add(in.clone());
        }


        for (Task in : this.tasks) {
            clone.tasks.add(in.clone());
        }

        return clone;
    }
    public Organization mapClone() {
        Organization mapClone = new Organization(this.vatNumber, this.mapRepository,this.cityRepository,this.industryRepository,this.stationRepository);
        mapClone.name = (this.name);
        mapClone.website = (this.website);
        mapClone.phone = (this.phone);
        mapClone.email = (this.email);

        for (Employee in : this.employees) {
            mapClone.employees.add(in.clone());
        }


        for (Map in : this.maps) {
            mapClone.maps.add(in.mapClone());
        }

        return mapClone;
    }

    public List<Map> getMaps() {
        return maps;
    }
}
