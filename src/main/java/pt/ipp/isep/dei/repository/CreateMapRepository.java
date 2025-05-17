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