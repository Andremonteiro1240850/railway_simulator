package pt.ipp.isep.dei.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import pt.ipp.isep.dei.domain.Scenario;
import pt.ipp.isep.dei.domain.Station;
import pt.ipp.isep.dei.domain.enums.StationUpgrade;

/**
 * Repository responsible for managing Station entities, including adding stations,
 * applying upgrades, and retrieving associated scenarios.
 */
public class StationRepository {
    private final List<Station> stations = new ArrayList<>();
    private ScenarioRepository scenarioRepo;

    /**
     * Adds a new station to the repository if it does not already exist.
     * The station is cloned before being stored.
     *
     * @param station the station to add
     * @return an Optional containing the added station, or empty if null or already exists
     */
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

    /**
     * Checks whether the repository contains a station with the same reference.
     *
     * @param station the station to check
     * @return true if a station with the same reference exists, false otherwise
     */
    public boolean contains(Station station) {
        return stations.stream().anyMatch(s -> s.getReference().equals(station.getReference()));
    }

    /**
     * Finds a station by its reference.
     *
     * @param reference the unique reference of the station
     * @return an Optional containing the station if found, otherwise empty
     */
    public Optional<Station> findByReference(String reference) {
        return stations.stream()
                .filter(s -> s.getReference().equals(reference))
                .findFirst()
                .map(Station::clone);
    }

    /**
     * Returns a defensive copy of all stored stations.
     *
     * @return a list of cloned stations
     */
    public List<Station> getAll() {
        // Retorna cópia defensiva com clones para evitar modificações externas
        List<Station> clonedStations = new ArrayList<>();
        for (Station station : stations) {
            clonedStations.add(station.clone());
        }
        return clonedStations;
    }

    /**
     * Adds an upgrade to the specified station by reference.
     *
     * @param stationReference the reference of the station to upgrade
     * @param upgrade the upgrade to add
     * @return true if the upgrade was added successfully, false otherwise
     */
    public boolean addUpgradeToStation(String stationReference, StationUpgrade upgrade) {
        Optional<Station> stationOpt = findByReference(stationReference);
        if (stationOpt.isPresent()) {
            Station station = stationOpt.get();
            station.addUpgrade(upgrade);
            stations.removeIf(s -> s.getReference().equals(stationReference));
            stations.add(station);
            return true;
        }
        return false;
    }

    /**
     * Retrieves the list of upgrades for a given station.
     *
     * @param stationReference the reference of the station
     * @return list of upgrades, or empty list if not found
     */
    public List<StationUpgrade> getUpgradesForStation(String stationReference) {
        return findByReference(stationReference)
                .map(Station::getUpgrades)
                .orElse(new ArrayList<>());
    }

    /**
     * Returns a formatted string listing all stations and their applied upgrades.
     *
     * @return formatted string with station references and their upgrades
     */
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

    /**
     * Finds the scenario that contains the specified station.
     *
     * @param stationReference the reference of the station
     * @return an Optional containing the scenario if found, otherwise empty
     */
    public Optional<Scenario> findScenarioByStation(String stationReference) {
        return scenarioRepo.getAll().stream()
                .filter(s -> s.getStationsPurchased().stream()
                        .anyMatch(st -> st.getReference().equals(stationReference)))
                .findFirst();
    }
}
