# US01 - Creating a Map


## 4. Tests
**Test 1:** Add map

        @Test
        public void testAddMap() {
        CreateMapRepository repository = new CreateMapRepository();
        Employee employee = new Employee("test@email.com");
        Map map = new Map("Test Map", 10, 20, employee);
    
            Optional<Map> result = repository.add(map);
            
            assertTrue(result.isPresent());
            assertEquals(map, result.get());
            assertEquals(1, repository.getAll().size());
        }
**Test 2:** Verify duplicate map
    
        @Test
        public void testAddDuplicateMap() {
            CreateMapRepository repository = new CreateMapRepository();
            Employee employee = new Employee("test@email.com");
            Map map = new Map("Test Map", 10, 20, employee);
            
            repository.add(map);
            Optional<Map> duplicateResult = repository.add(map);
            
            assertFalse(duplicateResult.isPresent());
            assertEquals(1, repository.getAll().size());
        }
**Test 3:** Verify add null map to repository
    
        @Test
        public void testAddNullMap() {
            CreateMapRepository repository = new CreateMapRepository();
            Optional<Map> result = repository.add(null);
            
            assertFalse(result.isPresent());
            assertTrue(repository.getAll().isEmpty());
        }
**Test 4:** Verify if map exist on list
    
        @Test
        public void testContainsMap() {
            CreateMapRepository repository = new CreateMapRepository();
            Employee employee = new Employee("test@email.com");
            Map map = new Map("Test Map", 10, 20, employee);
            
            repository.add(map);
            
            assertTrue(repository.contains(map));
            assertFalse(repository.contains(new Map("Other Map", 5, 5, employee)));
        }
**Test 5:** Verify if map is created

    @Test
    public void testMapCreation() {
        Employee employee = new Employee("test@email.com");
        Map map = new Map("Test Map", 10, 20, employee);
        
        assertEquals("Test Map", map.getName());
        assertEquals(10, map.getDimensionX());
        assertEquals(20, map.getDimensionY());
    }
**Test 6:** Verify if 2 map are the same

    @Test
    public void testMapEquality() {
        Employee employee = new Employee("test@email.com");
        Map map1 = new Map("Test Map", 10, 20, employee);
        Map map2 = new Map("Test Map", 10, 20, employee);
        Map map3 = new Map("Different Map", 10, 20, employee);
        
        assertEquals(map1, map2);
        assertNotEquals(map1, map3);
    }
**Test 7:** Verify if map is cloned right

    @Test
    public void testMapCloning() {
        Employee employee = new Employee("test@email.com");
        Map original = new Map("Test Map", 10, 20, employee);
        Map clone = original.clone();
        
        assertEquals(original, clone);
        assertNotSame(original, clone);
    }

## 5. Construction (Implementation)

### Class CreateMapController
```java
    
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
```
### Class Map
```java
package pt.ipp.isep.dei.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Map implements Cloneable {
    private String name;
    private int dimensionX;
    private int dimensionY;




    private Employee employee;

    public Map(String name, int dimensionX, int dimensionY, Employee employee) {

        this.name = name;
        this.dimensionX = dimensionX;
        this.dimensionY = dimensionY;
        this.employee = employee;

    }
    @Override
    public Map clone() {
        try {
            return (Map) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(); // Nunca deve acontecer pois implementamos Cloneable
        }
    }

    private void validateReference(String reference) {
        //TODO: missing from the diagrams
        if (reference == null || reference.isEmpty()) {
            throw new IllegalArgumentException("Reference cannot be null or empty.");
        }
    }
    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Map map = (Map) o;
        return Objects.equals(name, map.name); // assumindo que 'name' identifica unicamente o mapa
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    /**
     * Clone method.
     *
     * @return A clone of the current instance.
     */
    public  Map mapClone() {
        return new Map(this.name,this.dimensionX,this.dimensionY,this.employee);
    }

}


```
### Class CreateMapUI
```java
package pt.ipp.isep.dei.ui.console;

import pt.ipp.isep.dei.controller.CreateMapController;
import pt.ipp.isep.dei.domain.Map;
import pt.ipp.isep.dei.domain.Organization;
import pt.ipp.isep.dei.domain.TaskCategory;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class CreateMapUI implements Runnable {
    private final CreateMapController controller;
    private String name;
    private int dimensionX;
    private int dimensionY;
    private final Organization organization;


    public CreateMapUI(Organization organization) {
        controller = new CreateMapController();
        this.organization = organization;
    }

    private CreateMapController getController() {
        return controller;
    }

    public void run() {
        System.out.println("\n\n--- Create map ------------------------");

        requestData();
        submitData();
    }

    private void submitData() {
        Optional<Map> task = getController().createMap(name,dimensionX,dimensionY);

        if (task.isPresent()) {
            System.out.println("\nMap successfully created!");
        } else {
            System.out.println("\nMap not created!");
        }
    }

    private void requestData() {
        //Request the Task Reference from the console
        name = requestName();

        //Request the Task Description from the console
        dimensionX = requestDimensionX();

        //Request the Task Informal Description from the console
        dimensionY = requestDimensionY();

    }

    private String requestName() {
        Scanner input = new Scanner(System.in);
        System.out.print("Name: ");
        return input.nextLine();
    }

    private int requestDimensionX() {
        Scanner input = new Scanner(System.in);
        System.out.print("X: ");
        return input.nextInt();
    }
    private int requestDimensionY() {
        Scanner input = new Scanner(System.in);
        System.out.print("Y: ");
        return input.nextInt();
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
### Class CreateMapRepository
```java
package pt.ipp.isep.dei.repository;

import pt.ipp.isep.dei.domain.Map;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CreateMapRepository {
    private final List<Map> maps = new ArrayList<>();

    public Optional<Map> add(Map map) {
        if (map == null) {
            return Optional.empty();
        }

        if (!contains(map)) {
            try {
                Map clonedMap = map.clone();
                maps.add(clonedMap);
                return Optional.of(clonedMap);
            } catch (Exception e) {
                System.err.println("Error cloning map: " + e.getMessage());
                return Optional.empty();
            }
        }
        return Optional.empty();
    }

    public boolean contains(Map map) {
        return maps.stream().anyMatch(m -> m.equals(map));
    }

    public List<Map> getAll() {
        return new ArrayList<>(maps); // Retorna cópia defensiva
    }
}
```

## 6. Integration and Demo 

The implementation follows these integration points:

1. UI will call MapEditorController for validation and creation
2. MapRepository will handle persistence
3. All components follow dependency injection principles


## 7. Observations

1. The design could be extended to support more complex map features in future sprints
2. Error handling could be enhanced with specific exception types
3. The validation rules could be made configurable