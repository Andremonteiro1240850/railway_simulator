package pt.ipp.isep.dei.controller;

import pt.ipp.isep.dei.domain.Station;
import pt.ipp.isep.dei.domain.Player;
import pt.ipp.isep.dei.repository.StationRepository;

import java.util.List;

public class StationController {
    private final StationRepository stationRepo;

    public StationController() {
        this.stationRepo = StationRepository.getInstance();
    }

    /**
     * Gets all stations for a player with basic information
     * @param player The player whose stations to list
     * @return List of stations
     */
    public List<Station> getStationsByPlayer(Player player) {
        return stationRepo.getStationsByPlayer(player);
    }

    /**
     * Gets detailed information about a specific station
     * @param player The player who owns the station
     * @param stationName The name of the station to get details for
     * @return Station object with details
     * @throws IllegalArgumentException if station not found
     */
    public Station getStationDetails(Player player, String stationName) {
        return stationRepo.getStationsByPlayer(player).stream()
                .filter(s -> s.getName().equals(stationName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Station not found"));
    }

    /**
     * Formats station information for display
     * @param station The station to format
     * @return Formatted string with station details
     */
    public String formatStationDetails(Station station) {
        StringBuilder details = new StringBuilder();
        details.append(String.format("Station Name: %s\n", station.getName()));
        details.append(String.format("Location: (%d,%d)\n", station.getLocationX(), station.getLocationY()));
        details.append(String.format("Type: %s\n", station.getType()));
        details.append(String.format("Center: %d\n", station.getCenter()));

        // Upgrades section
        details.append("\nStation Upgrades:\n");
        List<String> upgrades = station.getUpgrades();
        if (upgrades.isEmpty()) {
            details.append("  No upgrades\n");
        } else {
            upgrades.forEach(upgrade ->
                    details.append(String.format("  - %s\n", upgrade)));
        }

        return details.toString();
    }
}