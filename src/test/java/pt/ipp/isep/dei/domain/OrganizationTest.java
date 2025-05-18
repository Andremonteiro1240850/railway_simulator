package pt.ipp.isep.dei.domain;

import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.repository.*;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

class OrganizationTest {
    private CreateMapRepository mapRepository = new CreateMapRepository();
    private CityRepository cityRepository = new CityRepository();
    private IndustryRepository industryRepository = new IndustryRepository();
    private StationRepository stationRepository = new StationRepository();

    @Test
    void testEqualsSameObject() {
        Organization organization = new Organization("123456789", mapRepository, cityRepository, industryRepository, stationRepository);
        assertEquals(organization, organization);
    }

    @Test
    void testEqualsDifferentClass() {
        Organization organization = new Organization("123456789", mapRepository, cityRepository, industryRepository, stationRepository);
        assertNotEquals("", organization);
    }

    @Test
    void testEqualsNull() {
        Organization organization = new Organization("123456789", mapRepository, cityRepository, industryRepository, stationRepository);
        assertNotEquals(null, organization);
    }

    @Test
    void testEqualsDifferentObject() {
        Organization organization = new Organization("123456789", mapRepository, cityRepository, industryRepository, stationRepository);
        Organization organization1 = new Organization("123456789", mapRepository, cityRepository, industryRepository, stationRepository);
        assertEquals(organization, organization1);
    }

    @Test
    void testHashCodeSameObject() {
        Organization organization = new Organization("123456789", mapRepository, cityRepository, industryRepository, stationRepository);
        assertEquals(organization.hashCode(), organization.hashCode());
    }

    @Test
    void testHashCodeDifferentObject() {
        Organization organization = new Organization("123456789", mapRepository, cityRepository, industryRepository, stationRepository);
        Organization organization1 = new Organization("123456789", mapRepository, cityRepository, industryRepository, stationRepository);
        assertEquals(organization.hashCode(), organization1.hashCode());
    }

    @Test
    void testHashCodeSameObjectSameVATNumber() {
        Organization organization = new Organization("123456789", mapRepository, cityRepository, industryRepository, stationRepository);
        Organization organization1 = new Organization("123456789", mapRepository, cityRepository, industryRepository, stationRepository);
        assertEquals(organization.hashCode(), organization1.hashCode());
    }

    @Test
    void ensureHashCodeFailsForDifferentVatNumbers() {
        Organization organization = new Organization("123456789", mapRepository, cityRepository, industryRepository, stationRepository);
        Organization organization1 = new Organization("123456788", mapRepository, cityRepository, industryRepository, stationRepository);
        assertNotEquals(organization.hashCode(), organization1.hashCode());
    }

    @Test
    void ensureEqualsFailsForDifferentObjectType() {
        Organization organization = new Organization("123456789", mapRepository, cityRepository, industryRepository, stationRepository);
        Organization organization1 = new Organization("123456788", mapRepository, cityRepository, industryRepository, stationRepository);
        assertNotEquals(organization, organization1);
    }

    @Test
    void ensureEqualsFailsWhenComparingNull() {
        Organization organization = new Organization("123456789", mapRepository, cityRepository, industryRepository, stationRepository);
        assertNotEquals(organization, null);
    }

    @Test
    void ensureEqualsSuccessWhenComparingSameObject() {
        Organization organization = new Organization("123456789", mapRepository, cityRepository, industryRepository, stationRepository);
        assertEquals(organization, organization);
    }

    @Test
    void testThatCreateTaskWorks() {
        Organization organization = new Organization("123456789", mapRepository, cityRepository, industryRepository, stationRepository);
        Employee employee = new Employee("john.doe@this.company.com");
        TaskCategory taskCategory = new TaskCategory("Task Category Description");

        Task expected = new Task("Task Description", "Task Category Description", "informal description",
                "technical description", 1, 1d, taskCategory, employee);

        Optional<Task> task = organization.createTask("Task Description", "Task Category Description",
                "informal description", "technical description", 1, 1d, taskCategory, employee);

        assertNotNull(task);
        assertTrue(task.isPresent());
        assertEquals(expected, task.get());
    }

    @Test
    void ensureAddingDuplicateTaskFails() {
        Organization organization = new Organization("123456789", mapRepository, cityRepository, industryRepository, stationRepository);
        Employee employee = new Employee("john.doe@this.company.com");
        TaskCategory taskCategory = new TaskCategory("Task Category Description");

        organization.createTask("Task Description", "Task Category Description", "informal description",
                "technical description", 1, 1d, taskCategory, employee);

        Optional<Task> duplicateTask = organization.createTask("Task Description", "Task Category Description",
                "informal description", "technical description", 1, 1d, taskCategory, employee);

        assertTrue(duplicateTask.isEmpty());
    }

    @Test
    void testCreateStationSuccess() {
        Organization organization = new Organization("123456789", mapRepository, cityRepository, industryRepository, stationRepository);
        Employee employee = new Employee("john.doe@this.company.com");

        Optional<Station> station = organization.createStation("ST-001", "Main Station", "NE", "station", employee);

        assertTrue(station.isPresent());
        assertEquals("ST-001", station.get().getReference());
    }

    @Test
    void testCreateIndustrySuccess() {
        Organization organization = new Organization("123456789", mapRepository, cityRepository, industryRepository, stationRepository);
        Employee employee = new Employee("john.doe@this.company.com");
        Map map = new Map("Test Map", 10, 10, employee);

        Optional<Industry> industry = organization.createIndustry(5, 5, map, employee);

        assertTrue(industry.isPresent());
    }

    @Test
    void testCreateMapSuccess() {
        Organization organization = new Organization("123456789", mapRepository, cityRepository, industryRepository, stationRepository);
        Employee employee = new Employee("john.doe@this.company.com");

        Optional<Map> map = organization.createMap("Test Map", 10, 10, employee);

        assertTrue(map.isPresent());
        assertEquals("Test Map", map.get().getName());
    }

    @Test
    void testCreateCitySuccess() {
        Organization organization = new Organization("123456789", mapRepository, cityRepository, industryRepository, stationRepository);
        Employee employee = new Employee("john.doe@this.company.com");
        Map map = new Map("Test Map", 10, 10, employee);

        Optional<City> city = organization.createCity(map, 5, 5, employee);

        assertTrue(city.isPresent());
    }

    @Test
    void ensureEmploysFails() {
        Organization organization = new Organization("123456789", mapRepository, cityRepository, industryRepository, stationRepository);
        Employee employee = new Employee("john.doe@this.company.com");
        assertFalse(organization.employs(employee));
    }

    @Test
    void ensureEmploysSuccess() {
        Organization organization = new Organization("123456789", mapRepository, cityRepository, industryRepository, stationRepository);
        Employee employee = new Employee("john.doe@this.company.com");
        organization.addEmployee(employee);
        assertTrue(organization.employs(employee));
    }

    @Test
    void ensureAnyEmployeeHasEmailFails() {
        Organization organization = new Organization("123456789", mapRepository, cityRepository, industryRepository, stationRepository);
        Employee employee = new Employee("john.doe@this.company.com");
        organization.addEmployee(employee);
        assertFalse(organization.anyEmployeeHasEmail("jane.doe@this.company.com"));
    }

    @Test
    void ensureAnyEmployeeHasEmailWorks() {
        Organization organization = new Organization("123456789", mapRepository, cityRepository, industryRepository, stationRepository);
        Employee employee = new Employee("john.doe@this.company.com");
        organization.addEmployee(employee);
        assertTrue(organization.anyEmployeeHasEmail("john.doe@this.company.com"));
    }

    @Test
    void ensureAddDuplicateEmployeeFails() {
        Organization organization = new Organization("123456789", mapRepository, cityRepository, industryRepository, stationRepository);
        Employee employee = new Employee("john.doe@this.company.com");
        assertTrue(organization.addEmployee(employee));
        assertFalse(organization.addEmployee(employee));
    }

    @Test
    void ensureAddEmployeeWorks() {
        Organization organization = new Organization("123456789", mapRepository, cityRepository, industryRepository, stationRepository);
        Employee employee = new Employee("john.doe@this.company.com");
        assertTrue(organization.addEmployee(employee));
    }

    @Test
    void ensureCloneWorks() {
        Organization organization = new Organization("123456789", mapRepository, cityRepository, industryRepository, stationRepository);
        Employee employee = new Employee("john.doe@this.company.com");
        organization.addEmployee(employee);
        organization.createTask("Task Description", "Task Category Description", "informal description",
                "technical description", 1, 1d, new TaskCategory("Task Category Description"), employee);

        Organization clone = organization.clone();
        assertEquals(organization, clone);
    }

    @Test
    void testGetMaps() {
        Organization organization = new Organization("123456789", mapRepository, cityRepository, industryRepository, stationRepository);
        Employee employee = new Employee("john.doe@this.company.com");
        organization.createMap("Map1", 10, 10, employee);
        organization.createMap("Map2", 20, 20, employee);

        assertEquals(2, organization.getMaps().size());
    }
}