package pt.ipp.isep.dei.repository;

import java.util.HashMap;
import java.util.Map;

/**
 * In-memory repository for Map objects.
 */
public class MapRepository {

    private final Map<String, Map> store = new HashMap<>();

    /**
     * Saves the given map in memory.
     * @param map the Map to save
     * @return true if save succeeded; false if a map with the same name already exists
     */
    public boolean save(Map map) {
        if (store.containsKey(map.getName())) {
            return false; // Prevent overwriting existing map
        }
        store.put(map.getName(), map);
        return true;
    }

    /**
     * Retrieves a map by name.
     * @param name the name of the map
     * @return the Map if found; null otherwise
     */
    public Map findByName(String name) {
        return store.get(name);
    }

}