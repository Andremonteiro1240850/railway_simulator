package pt.ipp.isep.dei.repository;

import pt.ipp.isep.dei.controller.BuildStationController;
import pt.ipp.isep.dei.domain.Scenario;
import pt.ipp.isep.dei.domain.Station;
import pt.ipp.isep.dei.domain.StationUpgrade;
import pt.ipp.isep.dei.domain.enums.Buildings;

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
                System.err.println("Error cloning station: " + e.getMessage());
                return Optional.empty();
            }
        }
        return Optional.empty();
    }

    public boolean contains(Station station) {
        return stations.stream().anyMatch(s -> s.getReference().equals(station.getReference()));
    }

    public Optional<Station> findByReference(String reference) {
        return stations.stream()
                .filter(s -> s.getReference().equals(reference))
                .findFirst()
                .map(Station::clone);
    }

    public List<Station> getAll() {
        // Retorna cópia defensiva com clones para evitar modificações externas
        List<Station> clonedStations = new ArrayList<>();
        for (Station station : stations) {
            clonedStations.add(station.clone());
        }
        return clonedStations;
    }

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
                    sb.append(upgrade.getType());
                    if (upgrade.getSubType() != null) {
                        sb.append(" (").append(upgrade.getSubType()).append(")");
                    }
                    sb.append(", ");
                }
                // Remove a última vírgula e espaço
                sb.setLength(sb.length() - 2);
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    public Optional<Scenario> findScenarioByStation(String stationReference, ScenarioRepository scenarioRepo) {
        return scenarioRepo.getAll().stream()
                .filter(s -> s.getStationsPurchased().stream()
                        .anyMatch(st -> st.getReference().equals(stationReference)))
                .findFirst();
    }
}