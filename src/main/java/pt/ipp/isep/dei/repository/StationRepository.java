package pt.ipp.isep.dei.repository;

import pt.ipp.isep.dei.domain.Map;
import pt.ipp.isep.dei.domain.Station;

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
                System.err.println("Error cloning map: " + e.getMessage());
                return Optional.empty();
            }
        }
        return Optional.empty();
    }

    public boolean contains(Station station) {
        return stations.stream().anyMatch(m -> m.equals(station));
    }

    public List<Station> getAll() {
        return new ArrayList<>(stations); // Retorna cópia defensiva
    }
}
