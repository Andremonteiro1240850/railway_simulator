package pt.ipp.isep.dei.controller;

import pt.ipp.isep.dei.domain.*;
import pt.ipp.isep.dei.domain.enums.ScaleUnit;
import pt.ipp.isep.dei.repository.CreateMapRepository;
import pt.ipp.isep.dei.repository.Repositories;

import java.util.List;
import java.util.Optional;

/**
 * Controller responsible for creating new maps and placing entities.
 */
public class CreateMapController {
    private final CreateMapRepository repo;
    public CreateMapController() {
        this.repo = Repositories.getInstance().getMapRepository();
    }

    /**
     * Creates a new map for the given organization.
     */
    public Optional<String> createMap(String name, int width, int height, ScaleUnit scale, Organization organization) {
        try {
            if (name == null || name.isEmpty() || !name.matches("^[a-zA-Z0-9 _.-]+$")) {
                return Optional.of("❌ Invalid map name");
            }
            if (width <= 0 || height <= 0) {
                return Optional.of("❌ Dimensions must be positive integers");
            }
            if (scale == null) {
                return Optional.of("❌ Scale is required");
            }
            if (organization == null) {
                return Optional.of("❌ Organization must be provided");
            }

            Map map = new Map(name, width, height, scale, organization);
            return repo.save(map).isPresent()
                    ? Optional.of("✅ Map created successfully:\n" + map)
                    : Optional.of("❌ Map already exists for this organization.");

        } catch (Exception e) {
            return Optional.of("❌ Unexpected error: " + e.getMessage());
        }
    }

    /**
     * Adds a city to the given map after validating input and position.
     */
    public Optional<String> addCityToMap(Map map, String cityName, int x, int y, List<int[]> houseBlocks, Employee employee) {
        if (map == null) return Optional.of("❌ Map is required");
        if (cityName == null || !cityName.matches("^[a-zA-Z ]+$")) {
            return Optional.of("❌ Invalid city name (only letters and spaces allowed)");
        }
        if (houseBlocks == null || houseBlocks.isEmpty()) {
            return Optional.of("❌ At least one house block is required");
        }
        if (x < 0 || x >= map.getWidth() || y < 0 || y >= map.getHeight()) {
            return Optional.of("❌ City position is out of map bounds");
        }
        if (map.isOccupied(x, y)) {
            return Optional.of("❌ City position is already occupied");
        }

        map.addCity(cityName, x, y, houseBlocks, employee);
        return Optional.of("✅ City added at position (" + x + ", " + y + ")");
    }

    /**
     * Adds an industry to the given map after validating the position.
     */
    public Optional<String> addIndustryToMap(Map map, Industry industry) {
        if (map == null || industry == null) return Optional.of("❌ Map and industry are required");

        int x = industry.getPositionX();
        int y = industry.getPositionY();

        if (x < 0 || x >= map.getWidth() || y < 0 || y >= map.getHeight()) {
            return Optional.of("❌ Industry position is out of map bounds");
        }
        if (map.isOccupied(x, y)) {
            return Optional.of("❌ Industry position is already occupied");
        }

        map.addIndustry(industry);
        return Optional.of("✅ Industry added at position (" + x + ", " + y + ")");
    }

    /**
     * Adds a station to the given map after validating the position.
     */
    public Optional<String> addStationToMap(Map map, Station station) {
        if (map == null || station == null) return Optional.of("❌ Map and station are required");

        int x = station.getX();
        int y = station.getY();

        if (x < 0 || x >= map.getWidth() || y < 0 || y >= map.getHeight()) {
            return Optional.of("❌ Station position is out of map bounds");
        }
        if (map.isOccupied(x, y)) {
            return Optional.of("❌ Station position is already occupied");
        }

        map.addStation(station);
        return Optional.of("✅ Station added at position (" + x + ", " + y + ")");
    }
    public List<Map> getAllMaps() {
        return repo.getAll();
    }

}
