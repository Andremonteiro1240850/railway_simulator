package pt.ipp.isep.dei.domain;

import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.domain.*;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class ScenarioTest {

    @Test
    public void testScenarioCreation() {
        Map map = new Map("Test Map", 10, 10, new Employee("test@email.com"));
        Scenario scenario = new Scenario("Test Scenario", map, 1900, 1950);

        assertEquals("Test Scenario", scenario.getName());
        assertEquals(map, scenario.getMap());
        assertEquals(1900, scenario.getStartYear());
        assertEquals(1950, scenario.getEndYear());
        assertEquals(1900, scenario.getCurrentYear());
        assertTrue(scenario.getAvailableLocomotiveTypes().isEmpty());
        assertNull(scenario.getPortBehavior());
        assertTrue(scenario.getHistoricalEvents().isEmpty());
    }

    @Test
    public void testAddLocomotiveTypes() {
        Map map = new Map("Test Map", 10, 10, new Employee("test@email.com"));
        Scenario scenario = new Scenario("Test Scenario", map, 1900, 1950);

        scenario.addLocomotiveType("Steam");
        scenario.addLocomotiveType("Diesel");

        List<String> types = scenario.getAvailableLocomotiveTypes();
        assertEquals(2, types.size());
        assertTrue(types.contains("Steam"));
        assertTrue(types.contains("Diesel"));
    }

    @Test
    public void testSetPortBehavior() {
        Map map = new Map("Test Map", 10, 10, new Employee("test@email.com"));
        Scenario scenario = new Scenario("Test Scenario", map, 1900, 1950);
        PortBehavior behavior = new PortBehavior();

        scenario.setPortBehavior(behavior);

        assertEquals(behavior, scenario.getPortBehavior());
    }

    @Test
    public void testScenarioCloning() {
        Map map = new Map("Test Map", 10, 10, new Employee("test@email.com"));
        Scenario original = new Scenario("Test Scenario", map, 1900, 1950);
        original.addLocomotiveType("Steam");

        PortBehavior behavior = new PortBehavior();
        behavior.addImportCargo("Coal");
        original.setPortBehavior(behavior);

        Scenario clone = original.clone();

        assertEquals(original.getName(), clone.getName());
        assertEquals(original.getMap(), clone.getMap());
        assertEquals(original.getAvailableLocomotiveTypes(), clone.getAvailableLocomotiveTypes());
        assertEquals(original.getPortBehavior(), clone.getPortBehavior());
        assertNotSame(original, clone);
    }
}