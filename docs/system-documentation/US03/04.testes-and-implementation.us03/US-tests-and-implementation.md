# USXXX - YYY

## 4. Tests
**Test 1:** Test city creation

    @Test
    public void testCityCreation() {
    Employee employee = new Employee("test@email.com");
    Map map = new Map("Test Map", 10, 10, employee);
    City city = new City(map, 5, 5, employee);

        assertNotNull(city);
    }
**Test 2:** Test if two cities are equal

    @Test
    public void testCityEquality() {
        Employee employee = new Employee("test@email.com");
        Map map = new Map("Test Map", 10, 10, employee);

        City city1 = new City(map, 5, 5, employee);
        City city2 = new City(map, 5, 5, employee);
        City city3 = new City(map, 3, 3, employee);

        assertEquals(city1, city2);
        assertNotEquals(city1, city3);
    }
**Test 3:** Test if city are clone correctly

    @Test
    public void testCityCloning() {
        Employee employee = new Employee("test@email.com");
        Map map = new Map("Test Map", 10, 10, employee);
        City original = new City(map, 5, 5, employee);
        City clone = original.clone();

        assertEquals(original, clone);
        assertNotSame(original, clone);
    }
**Test 4:** Test add city to a repository

    @Test
    public void testAddCity() {
        CityRepository repository = new CityRepository();
        Employee employee = new Employee("test@email.com");
        Map map = new Map("Test Map", 10, 10, employee);
        City city = new City(map, 5, 5, employee);

        Optional<City> result = repository.add(city);

        assertTrue(result.isPresent());
        assertEquals(city, result.get());
        assertEquals(1, repository.getAll().size());
    }
**Test 5:** Test if are duplicate cities on repository

    @Test
    public void testAddDuplicateCity() {
        CityRepository repository = new CityRepository();
        Employee employee = new Employee("test@email.com");
        Map map = new Map("Test Map", 10, 10, employee);
        City city = new City(map, 5, 5, employee);

        repository.add(city);
        Optional<City> duplicateResult = repository.add(city);

        assertFalse(duplicateResult.isPresent());
        assertEquals(1, repository.getAll().size());
    }
**Test 6:** Test if is possible to add a null city

    @Test
    public void testAddNullCity() {
        CityRepository repository = new CityRepository();
        Optional<City> result = repository.add(null);

        assertFalse(result.isPresent());
        assertTrue(repository.getAll().isEmpty());
    }
**Test 7:** Test if the repository contains a city

    @Test
    public void testContainsCity() {
        CityRepository repository = new CityRepository();
        Employee employee = new Employee("test@email.com");
        Map map = new Map("Test Map", 10, 10, employee);
        City city = new City(map, 5, 5, employee);

        repository.add(city);

        assertTrue(repository.contains(city));
    }



## 5. Construction (Implementation)
### Class AddCityController
```java
package pt.ipp.isep.dei.controller;

import pt.ipp.isep.dei.domain.*;
import pt.ipp.isep.dei.repository.AuthenticationRepository;
import pt.ipp.isep.dei.repository.OrganizationRepository;
import pt.ipp.isep.dei.repository.Repositories;
import pt.ipp.isep.dei.repository.TaskCategoryRepository;
import pt.isep.lei.esoft.auth.domain.model.Email;

import java.util.List;
import java.util.Optional;

public class AddCityController {
    private OrganizationRepository organizationRepository;

    private AuthenticationRepository authenticationRepository;

    //Repository instances are obtained from the Repositories class
    public AddCityController() {
        getOrganizationRepository();
        getAuthenticationRepository();
    }

    //Allows receiving the repositories as parameters for testing purposes
    public AddCityController(OrganizationRepository organizationRepository,
                                TaskCategoryRepository taskCategoryRepository,
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

    public Optional<City> createCity(Map map, int x, int y) {

        Employee employee = getEmployeeFromSession();
        Optional<Organization> organization = getOrganizationRepository().getOrganizationByEmployee(employee);

        Optional<City> newCity = Optional.empty();

        if (organization.isPresent()) {
            newCity = organization.get()
                    .createCity(map,x,y,employee);
        }
        return newCity;
    }

    private Employee getEmployeeFromSession() {
        Email email = getAuthenticationRepository().getCurrentUserSession().getUserId();
        return new Employee(email.getEmail());
    }

}

```
### Class City
```java
package pt.ipp.isep.dei.domain;

import java.util.Objects;

public class City {
    private Map map;
    private int x;
    private int y;

    private Employee employee;

    public City(Map map, int x, int y, Employee employee) {

        this.map = map;
        this.x = x;
        this.y = y;
        this.employee = employee;
    }

    private void validateReference(String reference) {
        //TODO: missing from the diagrams
        if (reference == null || reference.isEmpty()) {
            throw new IllegalArgumentException("Reference cannot be null or empty.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Task)) {
            return false;
        }
        City task = (City) o;
        return  employee.equals(task.employee);
    }

    @Override
    public int hashCode() {
        return Objects.hash( employee);
    }

    /**
     * Clone method.
     *
     * @return A clone of the current instance.
     */
    public City clone() {
        return new City(this.map,this.x, this.y, this.employee);
    }
}

```
### Class CityRepository
```java
package pt.ipp.isep.dei.repository;

import pt.ipp.isep.dei.domain.City;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CityRepository {
    private final List<City> cities = new ArrayList<>();

    public Optional<City> add(City city) {
        if (city == null) {
            return Optional.empty();
        }

        if (!contains(city)) {
            try {
                City clonedCity = city.clone();
                cities.add(clonedCity);
                return Optional.of(clonedCity);
            } catch (Exception e) {
                System.err.println("Error cloning city: " + e.getMessage());
                return Optional.empty();
            }
        }
        return Optional.empty();
    }

    public boolean contains(City city) {
        return cities.stream().anyMatch(m -> m.equals(city));
    }

    public List<City> getAll() {
        return new ArrayList<>(cities); // Retorna cópia defensiva
    }
}


```
### Class AddCityUI
```java
package pt.ipp.isep.dei.ui.console;

import pt.ipp.isep.dei.controller.AddCityController;
import pt.ipp.isep.dei.domain.City;
import pt.ipp.isep.dei.domain.Map;
import pt.ipp.isep.dei.domain.Organization;
import pt.ipp.isep.dei.domain.TaskCategory;
import pt.ipp.isep.dei.repository.CreateMapRepository;
import pt.ipp.isep.dei.repository.Repositories;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class AddCityUI implements Runnable {
    private final AddCityController controller;
    private Map map;
    private int locationX;
    private int locationY;
    private final Organization organization;



    public AddCityUI(Organization organization) {
        controller = new AddCityController();
        this.organization = organization;
    }

    private AddCityController getController() {
        return controller;
    }

    public void run() {
        System.out.println("\n\n--- Add city Task ------------------------");

        requestData();
        submitData();
    }

    private void submitData() {
        Optional<City> task = getController().createCity(map,locationX,locationY);

        if (task.isPresent()) {
            System.out.println("\nCity successfully created!");
        } else {
            System.out.println("\nCity not created!");
        }
    }

    private void requestData() {
        //Request the Task Reference from the console
        map = requestMap();
        locationX = requestLocationX();
        locationY = requestLocationY();
    }



    private int requestLocationX() {
        Scanner input = new Scanner(System.in);
        System.out.print("X: ");
        return input.nextInt();
    }
    private int requestLocationY() {
        Scanner input = new Scanner(System.in);
        System.out.print("Y: ");
        return input.nextInt();
    }
    private Map requestMap() {

        CreateMapRepository repository = Repositories.getInstance().getMapRepository();
        List<Map> availableMaps = repository.getAll();

        if (availableMaps.isEmpty()) {
            System.out.println("No maps available.");
            return null;
        }

        System.out.println("Available Maps:");
        for (int i = 0; i < availableMaps.size(); i++) {
            System.out.println((i + 1) + " - " + availableMaps.get(i));
        }

        Scanner input = new Scanner(System.in);
        int selectedIndex = -1;

        while (selectedIndex < 1 || selectedIndex > availableMaps.size()) {
            System.out.print("Select a map by number (1 to " + availableMaps.size() + "): ");
            if (input.hasNextInt()) {
                selectedIndex = input.nextInt();
            } else {
                input.next(); // limpar inválido
            }
        }

        return availableMaps.get(selectedIndex - 1).mapClone();
    }

    private void displayTaskCategoryOptions(List<TaskCategory> taskCategories) {
        //display the task categories as a menu with number options to select
        int i = 1;
        for (TaskCategory taskCategory : taskCategories) {
            System.out.println("  " + i + " - " + taskCategory.getDescription());
            i++;
        }
    }
}

```


## 6. Integration and Demo 


## 7. Observations

