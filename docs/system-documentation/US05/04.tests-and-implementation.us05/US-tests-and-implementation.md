# US05 - Build a station

## 4. Tests
**Test 1:** Test create a station with reference

    @Test
    public void testStationCreationWithReference() {
    Station station = new Station("ST-123", "Main Station", "station", "NE");

        assertEquals("ST-123", station.getReference());
        assertEquals("Main Station", station.getName());
        assertEquals("station", station.getType());
        assertEquals("NE", station.getRegion());
    }

**Test 2:** Teste create a station without reference

    @Test
    public void testStationCreationWithoutReference() {
        Station station = new Station("Main Station", "terminal", null);

        assertNotNull(station.getReference());
        assertTrue(station.getReference().startsWith("ST-"));
        assertEquals("terminal", station.getType());
        assertNull(station.getRegion());
    }
**Test 3:** Teste if 2 stations are equal

    @Test
    public void testStationEquality() {
        Station station1 = new Station("ST-123", "Station A", "station", "NE");
        Station station2 = new Station("ST-123", "Station B", "station", "NW");
        Station station3 = new Station("ST-456", "Station A", "station", "NE");

        assertEquals(station1, station2); // Mesma referência
        assertNotEquals(station1, station3); // Referências diferentes
    }
**Test 4:** Test if stations are correctly cloned

    @Test
    public void testStationCloning() {
        Station original = new Station("ST-123", "Main Station", "station", "NE");
        original.addUpgrade(new StationUpgrade("signal", "basic"));

        Station clone = original.clone();

        assertEquals(original, clone);
        assertNotSame(original, clone);
        assertEquals(original.getUpgrades().size(), clone.getUpgrades().size());
    }
**Tets 5:** Test add a station to a repository

    @Test
    public void testAddStation() {
        StationRepository repo = new StationRepository();
        Station station = new Station("ST-123", "Main Station", "station", "NE");

        Optional<Station> result = repo.add(station);

        assertTrue(result.isPresent());
        assertEquals(station, result.get());
        assertEquals(1, repo.getAll().size());
    }
**Test 6:** Test if is possible to add the same station

    @Test
    public void testAddDuplicateStation() {
        StationRepository repo = new StationRepository();
        Station station = new Station("ST-123", "Main Station", "station", "NE");

        repo.add(station);
        Optional<Station> duplicateResult = repo.add(station);

        assertFalse(duplicateResult.isPresent());
        assertEquals(1, repo.getAll().size());
    }

**Test 7:** Test if is possible to find a station by reference

    @Test
    public void testFindByReference() {
        StationRepository repo = new StationRepository();
        Station station1 = new Station("ST-123", "Station A", "station", "NE");
        Station station2 = new Station("ST-456", "Station B", "terminal", null);

        repo.add(station1);
        repo.add(station2);

        Optional<Station> found = repo.findByReference("ST-123");
        Optional<Station> notFound = repo.findByReference("ST-999");

        assertTrue(found.isPresent());
        assertEquals("Station A", found.get().getName());
        assertFalse(notFound.isPresent());
    }
**Test 8:** Test if is possible to see all station types

    @Test
    public void testGetAllStationTypes() {
        TypeStationRepository repo = new TypeStationRepository();
        List<String> types = repo.getAllStationTypes();

        assertEquals(3, types.size());
        assertTrue(types.contains("depot"));
        assertTrue(types.contains("terminal"));
        assertTrue(types.contains("station"));
    }
**Test 9:** Test if is possible to see all station regions

    @Test
    public void testGetAllStationRegions() {
        TypeStationRepository repo = new TypeStationRepository();
        List<String> regions = repo.getAllStationRegions();

        assertEquals(4, regions.size());
        assertTrue(regions.contains("NE"));
        assertTrue(regions.contains("NW"));
        assertTrue(regions.contains("SE"));
        assertTrue(regions.contains("SW"));
    }
**Test 10:** Test if a station type exist and is valid

    @Test
    public void testIsValidStationType() {
        TypeStationRepository repo = new TypeStationRepository();

        assertTrue(repo.isValidStationType("depot"));
        assertTrue(repo.isValidStationType("STATION")); // Test case insensitivity
        assertFalse(repo.isValidStationType("invalid"));
    }

**Test 11:** Test if a region is valid or exist

    @Test
    public void testIsValidStationRegion() {
        TypeStationRepository repo = new TypeStationRepository();

        assertTrue(repo.isValidStationRegion("NE"));
        assertTrue(repo.isValidStationRegion("se")); // Test case insensitivity
        assertFalse(repo.isValidStationRegion("XX"));
    }


## 5. Construction (Implementation)
### Class BuildStationController
```java
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


        public Optional<Station> createStation(String type, String name, String region) {
            Employee employee = getEmployeeFromSession();
            Optional<Organization> organization = getOrganizationRepository().getOrganizationByEmployee(employee);

            // Gera uma referência automática
            String reference = "ST-" + System.currentTimeMillis();

            Optional<Station> newStation = Optional.empty();

            if (organization.isPresent()) {
                newStation = organization.get()
                        .createStation(reference, name, region, type, employee);
            }
            return newStation;
        }
    private Employee getEmployeeFromSession() {
        Email email = getAuthenticationRepository().getCurrentUserSession().getUserId();
        return new Employee(email.getEmail());
    }

}

```
### Class Station
```java
package pt.ipp.isep.dei.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Station {
    private String name;
    private String type; // "depot", "terminal", "station"
    private String region;
    private Employee employee;
    private String reference;  // Novo campo adicionado
    private List<StationUpgrade> upgrades = new ArrayList<>();

    // Construtor modificado para incluir reference
    public Station(String reference, String name, String type, String region) {
        this.reference = reference;
        this.name = name;
        this.type = type;
        this.region = region;
        validateTypeAndRegion(type, region);
    }

    // Construtor antigo mantido para compatibilidade
    public Station(String name, String type, String region) {
        this(generateDefaultReference(), name, type, region);
    }

    private static String generateDefaultReference() {
        return "ST-" + System.currentTimeMillis(); // Gera uma referência única
    }

    private void validateTypeAndRegion(String type, String region) {
        // ... (mantido igual)
    }

    // Getters
    public String getReference() {
        return reference;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getRegion() {
        return region;
    }

    public Employee getEmployee() {
        return employee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Station)) return false;
        Station station = (Station) o;
        return reference.equals(station.reference); // Agora compara por reference
    }

    @Override
    public int hashCode() {
        return Objects.hash(reference); // Agora usa reference
    }

    public Station clone() {
        Station clone = new Station(this.reference, this.name, this.type, this.region);
        clone.employee = this.employee != null ? this.employee.clone() : null;
        clone.upgrades = new ArrayList<>(this.upgrades);
        return clone;
    }

    public void addUpgrade(StationUpgrade upgrade) {
        this.upgrades.add(upgrade);
    }

    public List<StationUpgrade> getUpgrades() {
        return new ArrayList<>(upgrades);
    }
}
```
### Class StationRepository
```java
package pt.ipp.isep.dei.repository;

import pt.ipp.isep.dei.domain.Station;
import pt.ipp.isep.dei.domain.StationUpgrade;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StationRepository {
    private final List<Station> stations = new ArrayList<>();

    public Optional<Station> add(Station station) {
        if (station == null) {
            return Optional.empty();
        }

        if (!contains(station)) {
            try {
                Station clonedStation = station.clone();
                stations.add(clonedStation);
                return Optional.of(clonedStation);
            } catch (Exception e) {
                System.err.println("Error cloning station: " + e.getMessage());
                return Optional.empty();
            }
        }
        return Optional.empty();
    }

    public boolean contains(Station station) {
        return stations.stream().anyMatch(s -> s.getReference().equals(station.getReference()));
    }

    public Optional<Station> findByReference(String reference) {
        return stations.stream()
                .filter(s -> s.getReference().equals(reference))
                .findFirst()
                .map(Station::clone);
    }

    public List<Station> getAll() {
        // Retorna cópia defensiva com clones para evitar modificações externas
        List<Station> clonedStations = new ArrayList<>();
        for (Station station : stations) {
            clonedStations.add(station.clone());
        }
        return clonedStations;
    }

    public boolean addUpgradeToStation(String stationReference, StationUpgrade upgrade) {
        Optional<Station> stationOpt = findByReference(stationReference);
        if (stationOpt.isPresent()) {
            Station station = stationOpt.get();
            station.addUpgrade(upgrade);

            // Atualiza a estação no repositório
            stations.removeIf(s -> s.getReference().equals(stationReference));
            stations.add(station);
            return true;
        }
        return false;
    }

    public List<StationUpgrade> getUpgradesForStation(String stationReference) {
        return findByReference(stationReference)
                .map(Station::getUpgrades)
                .orElse(new ArrayList<>());
    }

    public String printStationsWithUpgrades() {
        StringBuilder sb = new StringBuilder();
        sb.append("List of Stations with Upgrades:\n");
        for (Station station : getAll()) {
            sb.append("- ").append(station.getReference())
                    .append(" (").append(station.getType()).append("): ");

            if (station.getUpgrades().isEmpty()) {
                sb.append("No upgrades\n");
            } else {
                sb.append("Upgrades: ");
                for (StationUpgrade upgrade : station.getUpgrades()) {
                    sb.append(upgrade.getType());
                    if (upgrade.getSubType() != null) {
                        sb.append(" (").append(upgrade.getSubType()).append(")");
                    }
                    sb.append(", ");
                }
                // Remove a última vírgula e espaço
                sb.setLength(sb.length() - 2);
                sb.append("\n");
            }
        }
        return sb.toString();
    }
}
```
### Class BuildStationUI
```java
package pt.ipp.isep.dei.ui.console;

import pt.ipp.isep.dei.controller.BuildStationController;
import pt.ipp.isep.dei.domain.Station;
import pt.ipp.isep.dei.domain.TaskCategory;
import pt.ipp.isep.dei.repository.TypeStationRepository;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class BuildStationUI implements Runnable {
    private final BuildStationController controller;
    private final TypeStationRepository typeStationRepo;
    private String type;
    private String region;
    private String name;

    public BuildStationUI() {
        this.controller = new BuildStationController();
        this.typeStationRepo = new TypeStationRepository();
    }

    public void run() {
        System.out.println("\n\n--- Create Station ------------------------");

        requestStationData();
        submitData();
    }

    private void submitData() {
        Optional<Station> station = controller.createStation(
                name,
                type,
                region);

        if (station.isPresent()) {
            System.out.println("\nStation successfully created!");
        } else {
            System.out.println("\nStation not created!");
        }
    }

    private void requestStationData() {
        // Request basic station information
        name = requestName();
        // Select station type and region if applicable
        selectStationTypeAndRegion();
    }

    private void selectStationTypeAndRegion() {
        // Get available station types from repository
        List<String> types = typeStationRepo.getAllStationTypes();
        type = selectFromList(types, "Station Type");

        // Only ask for region if station type is "station"
        if ("station".equalsIgnoreCase(type)) {
            List<String> regions = typeStationRepo.getAllStationRegions();
            region = selectFromList(regions, "Station Region");
        } else {
            region = null;
        }
    }

    // Helper method to select from a list of items
    private <T> T selectFromList(List<T> items, String prompt) {
        Scanner input = new Scanner(System.in);
        int selection = -1;

        while (selection < 1 || selection > items.size()) {
            System.out.println("\n" + prompt + " options:");
            for (int i = 0; i < items.size(); i++) {
                System.out.printf("  %d - %s%n", i+1, items.get(i).toString());
            }
            System.out.print("Select " + prompt + ": ");
            selection = input.nextInt();
            input.nextLine(); // Consume newline
        }

        return items.get(selection - 1);
    }

    // Helper methods for input
    private String requestName() {
        Scanner input = new Scanner(System.in);
        System.out.print("Name: ");
        return input.nextLine();
    }

}
```
### Class TypeStationRepository
```java
package pt.ipp.isep.dei.repository;


 import java.util.ArrayList;
import java.util.List;

    public class TypeStationRepository {
        private final List<String> stationTypes;
        private final List<String> stationRegions; // Apenas para estações do tipo "station"

        public TypeStationRepository() {
            this.stationTypes = new ArrayList<>();
            this.stationRegions = new ArrayList<>();
            initializeStationTypes();
            initializeStationRegions();
        }

        private void initializeStationTypes() {
            stationTypes.add("depot");
            stationTypes.add("terminal");
            stationTypes.add("station");
        }

        private void initializeStationRegions() {
            stationRegions.add("NE");
            stationRegions.add("NW");
            stationRegions.add("SE");
            stationRegions.add("SW");
        }

        // Métodos para obter os tipos de estações e regiões
        public List<String> getAllStationTypes() {
            return new ArrayList<>(stationTypes);
        }

        public List<String> getAllStationRegions() {
            return new ArrayList<>(stationRegions);
        }

        // Verifica se um tipo de estação é válido
        public boolean isValidStationType(String type) {
            return stationTypes.contains(type.toLowerCase());
        }

        // Verifica se uma região é válida (apenas para estações do tipo "station")
        public boolean isValidStationRegion(String region) {
            return stationRegions.contains(region.toUpperCase());
        }
    }
```

## 6. Integration and Demo

* Connected to budget system to validate construction costs

* Station creation now available in the Player menu options

* For demo purposes, three basic station types (Depot, Station, Terminal) are preloaded

* Station creation triggers map updates through the MapService observer
*
## 7. Observations

n/ais section, it is suggested to present a critical perspective on the developed work, pointing, for example, to other alternatives and or future related work._