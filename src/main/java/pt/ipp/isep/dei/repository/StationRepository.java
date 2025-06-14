package pt.ipp.isep.dei.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import pt.ipp.isep.dei.domain.Scenario;
import pt.ipp.isep.dei.domain.Station;
import pt.ipp.isep.dei.domain.enums.StationUpgrade;

/**
 * Repository class for managing stations in the system.
 */
public class StationRepository {
    private final List<Station> stations;
    private  ScenarioRepository scenarioRepo;

    /**
     * Constructs a new StationRepository.
     */
    public StationRepository() {
        this.stations = new ArrayList<>();
    }

    /**
     * Adds a station to the repository.
     *
     * @param station the station to add
     * @return Optional containing the added station if successful, empty otherwise
     */
    public Optional<Station> add(Station station) {
        if (station == null) {
            return Optional.empty();
        }
        stations.add(station);
        return Optional.of(station);
    }

    /**
     * Returns a defensive copy of all stations in the repository.
     *
     * @return a list of all stations
     */
    public List<Station> getAll() {
        return new ArrayList<>(stations);
    }

    /**
     * Finds a station by its reference.
     *
     * @param reference the reference to search for
     * @return Optional containing the found station if it exists, empty otherwise
     */
    public Optional<Station> findByReference(String reference) {
        if (reference == null) {
            return Optional.empty();
        }
        return stations.stream()
                .filter(station -> station.getReference().equals(reference))
                .findFirst();
    }

    /**
     * Adds an upgrade to a station.
     *
     * @param reference the reference of the station
     * @param upgrade the upgrade to add
     * @return true if the upgrade was added successfully, false otherwise
     */
    public boolean addUpgradeToStation(String reference, StationUpgrade upgrade) {
        if (reference == null || upgrade == null) {
            return false;
        }

        Optional<Station> stationOpt = findByReference(reference);
        if (stationOpt.isEmpty()) {
            return false;
        }

        Station station = stationOpt.get();
        station.addUpgrade(upgrade);
        return true;
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
                    sb.append(upgrade.getDisplayName()).append(", ");
                }
                // Remove a última vírgula e espaço
                sb.setLength(sb.length() - 2);
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    public Optional<Scenario> findScenarioByStation(String stationReference) {
        return scenarioRepo.getAll().stream()
                .filter(s -> s.getStationsPurchased().stream()
                        .anyMatch(st -> st.getReference().equals(stationReference)))
                .findFirst();
    }
}