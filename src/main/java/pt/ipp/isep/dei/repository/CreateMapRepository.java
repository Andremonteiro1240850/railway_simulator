package pt.ipp.isep.dei.repository;

import pt.ipp.isep.dei.domain.Map;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Repository for managing maps created by organizations.
 */
public class CreateMapRepository {
    private final List<Map> maps = new ArrayList<>();

    /**
     * Adds a new map if it does not already exist.
     *
     * @param map the map to add
     * @return Optional with the added map, or empty if it already exists
     */
    public Optional<Map> add(Map map) {
        if (map == null) return Optional.empty();

        if (!contains(map)) {
            maps.add(map); // no clone
            return Optional.of(map);
        }

        return Optional.empty();
    }

    /**
     * Checks if a map already exists in the repository.
     * Uses Map.equals(), which includes name, dimensions, scale and organization.
     *
     * @param map the map to check
     * @return true if already exists
     */
    public boolean contains(Map map) {
        return maps.contains(map);
    }

    /**
     * Returns a defensive copy of all maps.
     *
     * @return list of maps
     */
    public List<Map> getAll() {
        return new ArrayList<>(maps);
    }
}
