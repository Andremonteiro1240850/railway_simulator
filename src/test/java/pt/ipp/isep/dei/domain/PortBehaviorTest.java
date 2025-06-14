package pt.ipp.isep.dei.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

class PortBehaviorTest {
    private PortBehavior portBehavior;
    private List<String> importCargo;
    private List<String> exportCargo;
    private List<String> transformCargo;

    @BeforeEach
    void setUp() {
        portBehavior = new PortBehavior();
        importCargo = new ArrayList<>();
        exportCargo = new ArrayList<>();
        transformCargo = new ArrayList<>();
    }

    @Test
    void testConstructor() {
        assertNotNull(portBehavior);
        assertTrue(portBehavior.getImportCargo().isEmpty());
        assertTrue(portBehavior.getExportCargo().isEmpty());
        assertTrue(portBehavior.getTransformCargo().isEmpty());
    }

    @Test
    void testGetImportCargo() {
        portBehavior.addImportCargo("coal");
        List<String> result = portBehavior.getImportCargo();
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("coal", result.get(0));
        // Test defensive copy
        result.add("iron");
        assertNotEquals(result.size(), portBehavior.getImportCargo().size());
    }

    @Test
    void testGetExportCargo() {
        portBehavior.addExportCargo("steel");
        List<String> result = portBehavior.getExportCargo();
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("steel", result.get(0));
        // Test defensive copy
        result.add("iron");
        assertNotEquals(result.size(), portBehavior.getExportCargo().size());
    }

    @Test
    void testGetTransformCargo() {
        portBehavior.addTransformCargo("iron");
        List<String> result = portBehavior.getTransformCargo();
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("iron", result.get(0));
        // Test defensive copy
        result.add("coal");
        assertNotEquals(result.size(), portBehavior.getTransformCargo().size());
    }

    @Test
    void testSetImportCargo() {
        importCargo.add("coal");
        importCargo.add("iron");
        portBehavior.setImportCargo(importCargo);
        List<String> result = portBehavior.getImportCargo();
        assertEquals(2, result.size());
        assertTrue(result.contains("coal"));
        assertTrue(result.contains("iron"));
        // Test defensive copy
        importCargo.add("steel");
        assertNotEquals(importCargo.size(), result.size());
    }

    @Test
    void testSetExportCargo() {
        exportCargo.add("steel");
        exportCargo.add("textiles");
        portBehavior.setExportCargo(exportCargo);
        List<String> result = portBehavior.getExportCargo();
        assertEquals(2, result.size());
        assertTrue(result.contains("steel"));
        assertTrue(result.contains("textiles"));
        // Test defensive copy
        exportCargo.add("automobiles");
        assertNotEquals(exportCargo.size(), result.size());
    }

    @Test
    void testSetTransformCargo() {
        transformCargo.add("iron");
        transformCargo.add("coal");
        portBehavior.setTransformCargo(transformCargo);
        List<String> result = portBehavior.getTransformCargo();
        assertEquals(2, result.size());
        assertTrue(result.contains("iron"));
        assertTrue(result.contains("coal"));
        // Test defensive copy
        transformCargo.add("steel");
        assertNotEquals(transformCargo.size(), result.size());
    }

    @Test
    void testAddImportCargo() {
        portBehavior.addImportCargo("coal");
        portBehavior.addImportCargo("coal"); // Duplicate
        assertEquals(1, portBehavior.getImportCargo().size());
        portBehavior.addImportCargo("iron");
        assertEquals(2, portBehavior.getImportCargo().size());
    }

    @Test
    void testAddExportCargo() {
        portBehavior.addExportCargo("steel");
        portBehavior.addExportCargo("steel"); // Duplicate
        assertEquals(1, portBehavior.getExportCargo().size());
        portBehavior.addExportCargo("textiles");
        assertEquals(2, portBehavior.getExportCargo().size());
    }

    @Test
    void testAddTransformCargo() {
        portBehavior.addTransformCargo("iron");
        portBehavior.addTransformCargo("iron"); // Duplicate
        assertEquals(1, portBehavior.getTransformCargo().size());
        portBehavior.addTransformCargo("coal");
        assertEquals(2, portBehavior.getTransformCargo().size());
    }

    @Test
    void testRemoveImportCargo() {
        portBehavior.addImportCargo("coal");
        portBehavior.addImportCargo("iron");
        portBehavior.removeImportCargo("coal");
        assertEquals(1, portBehavior.getImportCargo().size());
        assertFalse(portBehavior.getImportCargo().contains("coal"));
    }

    @Test
    void testRemoveExportCargo() {
        portBehavior.addExportCargo("steel");
        portBehavior.addExportCargo("textiles");
        portBehavior.removeExportCargo("steel");
        assertEquals(1, portBehavior.getExportCargo().size());
        assertFalse(portBehavior.getExportCargo().contains("steel"));
    }

    @Test
    void testRemoveTransformCargo() {
        portBehavior.addTransformCargo("iron");
        portBehavior.addTransformCargo("coal");
        portBehavior.removeTransformCargo("iron");
        assertEquals(1, portBehavior.getTransformCargo().size());
        assertFalse(portBehavior.getTransformCargo().contains("iron"));
    }

    @Test
    void testEquals() {
        PortBehavior sameBehavior = new PortBehavior();
        PortBehavior differentBehavior = new PortBehavior();
        
        // Same behavior
        portBehavior.addImportCargo("coal");
        sameBehavior.addImportCargo("coal");
        assertEquals(portBehavior, sameBehavior);
        
        // Different behavior
        differentBehavior.addImportCargo("iron");
        assertNotEquals(portBehavior, differentBehavior);
        
        // Null comparison
        assertNotEquals(portBehavior, null);
        
        // Different class
        assertNotEquals(portBehavior, "Not a PortBehavior");
    }

    @Test
    void testHashCode() {
        PortBehavior sameBehavior = new PortBehavior();
        PortBehavior differentBehavior = new PortBehavior();
        
        portBehavior.addImportCargo("coal");
        sameBehavior.addImportCargo("coal");
        differentBehavior.addImportCargo("iron");
        
        assertEquals(portBehavior.hashCode(), sameBehavior.hashCode());
        assertNotEquals(portBehavior.hashCode(), differentBehavior.hashCode());
    }

    @Test
    void testToString() {
        portBehavior.addImportCargo("coal");
        portBehavior.addExportCargo("steel");
        portBehavior.addTransformCargo("iron");
        
        String result = portBehavior.toString();
        assertTrue(result.contains("coal"));
        assertTrue(result.contains("steel"));
        assertTrue(result.contains("iron"));
        assertTrue(result.contains("importCargo"));
        assertTrue(result.contains("exportCargo"));
        assertTrue(result.contains("transformCargo"));
    }

    @Test
    void testClone() {
        portBehavior.addImportCargo("coal");
        portBehavior.addExportCargo("steel");
        portBehavior.addTransformCargo("iron");
        
        PortBehavior clone = portBehavior.clone();
        assertNotNull(clone);
        assertNotSame(portBehavior, clone);
        assertEquals(portBehavior.getImportCargo(), clone.getImportCargo());
        assertEquals(portBehavior.getExportCargo(), clone.getExportCargo());
        assertEquals(portBehavior.getTransformCargo(), clone.getTransformCargo());
        
        // Test that modifications to clone don't affect original
        clone.addImportCargo("iron");
        assertNotEquals(portBehavior.getImportCargo().size(), clone.getImportCargo().size());
    }
} 