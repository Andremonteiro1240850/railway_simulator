# USXXX - YYY


## 4. Tests
**Test 1:** Test industry Creation

    @Test
    public void testIndustryCreation() {
    Employee employee = new Employee("test@email.com");
    Map map = new Map("Test Map", 10, 10, employee);
    Industry industry = new Industry(map, 5, 5, employee);

        assertNotNull(industry);
    }
**Test 2:** Test if 2 industry are equal

    @Test
    public void testIndustryEquality() {
        Employee employee = new Employee("test@email.com");
        Map map = new Map("Test Map", 10, 10, employee);

        Industry industry1 = new Industry(map, 5, 5, employee);
        Industry industry2 = new Industry(map, 5, 5, employee);
        Industry industry3 = new Industry(map, 3, 3, employee);

        assertEquals(industry1, industry2);
        assertNotEquals(industry1, industry3);
    }
**Test 3:** Test industry clone

    @Test
    public void testIndustryCloning() {
        Employee employee = new Employee("test@email.com");
        Map map = new Map("Test Map", 10, 10, employee);
        Industry original = new Industry(map, 5, 5, employee);
        Industry clone = original.clone();

        assertEquals(original, clone);
        assertNotSame(original, clone);
    }
**Test 4:** Test add industry to the repository


    @Test
    public void testAddIndustry() {
    IndustryRepository repository = new IndustryRepository();
    Employee employee = new Employee("test@email.com");
    Map map = new Map("Test Map", 10, 10, employee);
    Industry industry = new Industry(map, 5, 5, employee);

        Optional<Industry> result = repository.add(industry);

        assertTrue(result.isPresent());
        assertEquals(industry, result.get());
        assertEquals(1, repository.getAll().size());
    }
**Test 5:** Test if is possible to add the same industry

    @Test
    public void testAddDuplicateIndustry() {
        IndustryRepository repository = new IndustryRepository();
        Employee employee = new Employee("test@email.com");
        Map map = new Map("Test Map", 10, 10, employee);
        Industry industry = new Industry(map, 5, 5, employee);

        repository.add(industry);
        Optional<Industry> duplicateResult = repository.add(industry);

        assertFalse(duplicateResult.isPresent());
        assertEquals(1, repository.getAll().size());
    }

**Test 6:** Test add a null industry

    @Test
    public void testAddNullIndustry() {
        IndustryRepository repository = new IndustryRepository();
        Optional<Industry> result = repository.add(null);

        assertFalse(result.isPresent());
        assertTrue(repository.getAll().isEmpty());
    }

**Test 7:** Test if repository contains an industry

    @Test
    public void testContainsIndustry() {
        IndustryRepository repository = new IndustryRepository();
        Employee employee = new Employee("test@email.com");
        Map map = new Map("Test Map", 10, 10, employee);
        Industry industry = new Industry(map, 5, 5, employee);

        repository.add(industry);

        assertTrue(repository.contains(industry));
    }



## 5. Construction (Implementation)
### Class AddIndustryController
```java
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

        Employee employee = getEmployeeFromSession();
        Optional<Organization> organization = getOrganizationRepository().getOrganizationByEmployee(employee);

        Optional<Industry> newIndustry = Optional.empty();

        if (organization.isPresent()) {
            newIndustry = organization.get()
                    .createIndustry(positionX,positionY,map,employee);
        }
        return newIndustry;
    }

    private Employee getEmployeeFromSession() {
        Email email = getAuthenticationRepository().getCurrentUserSession().getUserId();
        return new Employee(email.getEmail());
    }


}

```
### Class AddIndustryUI
```java
package pt.ipp.isep.dei.ui.console;

import pt.ipp.isep.dei.controller.AddIndustryController;
import pt.ipp.isep.dei.domain.Industry;
import pt.ipp.isep.dei.domain.Map;
import pt.ipp.isep.dei.domain.TaskCategory;
import pt.ipp.isep.dei.repository.CreateMapRepository;
import pt.ipp.isep.dei.repository.Repositories;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class AddIndustryUI implements Runnable {
    private final AddIndustryController controller;
    private int x;
    private int y;
    private Map map;

    public AddIndustryUI() {
        controller = new AddIndustryController();
    }

    private AddIndustryController getController() {
        return controller;
    }

    public void run() {
        System.out.println("\n\n--- Add Industry ------------------------");

        requestData();
        submitData();
    }

    private void submitData() {
        Optional<Industry> task = getController().createIndustry(x,y,map);

        if (task.isPresent()) {
            System.out.println("\nIndustry successfully created!");
        } else {
            System.out.println("\nIndustry not created!");
        }
    }

    private void requestData() {
        //Request the Task Reference from the console
        x = requestX();
        y = requestY();
        map= requestMap();
    }

    private int requestX() {
        Scanner input = new Scanner(System.in);
        System.out.print("X: ");
        return input.nextInt();
    }
    private int requestY() {
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
### Class Industry
```java
package pt.ipp.isep.dei.domain;

import java.util.Objects;

public class Industry {
   private int positionX;
   private int positionY;
   private Map map;

    private Employee employee;

    public Industry(Map map, int positionX, int positionY, Employee employee) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.employee = employee;
        this.map = map;
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
        Industry task = (Industry) o;
        return  employee.equals(task.employee);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employee);
    }

    /**
     * Clone method.
     *
     * @return A clone of the current instance.
     */
    public Industry clone() {
        return new Industry(this.map,this.positionX,positionY, this.employee);
    }
}

```
### Class IndustryRepository
```java
package pt.ipp.isep.dei.repository;

import pt.ipp.isep.dei.domain.Industry;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class IndustryRepository {
    private final List<Industry> industries = new ArrayList<>();

    public Optional<Industry> add(Industry industry) {
        if (industry == null) {
            return Optional.empty();
        }

        if (!contains(industry)) {
            try {
                Industry clonedIndustry = industry.clone();
                industries.add(clonedIndustry);
                return Optional.of(clonedIndustry);
            } catch (Exception e) {
                System.err.println("Error cloning map: " + e.getMessage());
                return Optional.empty();
            }
        }
        return Optional.empty();
    }

    public boolean contains(Industry industry) {
        return industries.stream().anyMatch(m -> m.equals(industry));
    }

    public List<Industry> getAll() {
        return new ArrayList<>(industries); // Retorna cópia defensiva
    }
}


```

## 6. Integration and Demo 



## 7. Observations
