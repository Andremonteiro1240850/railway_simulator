# US01 - Creating a Map


## 4. Tests

**Test 1:** Check that map name validation works correctly 

        @Test
        public void testValidMapName() {
            assertTrue(MapEditorController.isValidMapName("valid_name"));
            assertFalse(MapEditorController.isValidMapName("invalid/name"));
            assertFalse(MapEditorController.isValidMapName(null));
        }

**Test 2:** Check that map dimensions validation works correctly

        @Test
        public void testValidMapDimensions() {
            assertTrue(MapEditorController.isValidDimensions(10, 20));
            assertFalse(MapEditorController.isValidDimensions(0, 10));
            assertFalse(MapEditorController.isValidDimensions(-5, 10));
        }

**Test 3:** Check that map creation works with valid parameters

        @Test
        public void testMapCreation() {
            Map map = new Map("test_map", 100, 100);
            assertNotNull(map);
            assertEquals("test_map", map.getName());
            assertEquals(100, map.getWidth());
            assertEquals(100, map.getHeight());
        }


## 5. Construction (Implementation)

***Key implementation classes***

**Map.java**

    public class Map {
        private final String name;
        private final int width;
        private final int height;
        
        public Map(String name, int width, int height) {
            if (name == null || name.isBlank()) {
                throw new IllegalArgumentException("Map name cannot be null or empty");
            }
            this.name = name;
            this.width = width;
            this.height = height;
        }
    }

**MapEditorController.java**

    public class MapEditorController {
    public static boolean isValidMapName(String name) {
    return name != null && name.matches("^[a-zA-Z0-9_\\- ]+$");
    }
    
        public static boolean isValidDimensions(int width, int height) {
            return width > 0 && height > 0;
        }
        
        public Map createMap(String name, int width, int height) {
            if (!isValidMapName(name) || !isValidDimensions(width, height)) {
                throw new IllegalArgumentException("Invalid map parameters");
            }
            return new Map(name, width, height);
        }
    }


## 6. Integration and Demo 

The implementation follows these integration points:

1. UI will call MapEditorController for validation and creation
2. MapRepository will handle persistence
3. All components follow dependency injection principles


## 7. Observations

1. The design could be extended to support more complex map features in future sprints
2. Error handling could be enhanced with specific exception types
3. The validation rules could be made configurable