package pt.ipp.isep.dei.domain;

import pt.ipp.isep.dei.domain.enums.ScaleUnit;
import java.util.*;

public class Map {
    private final String name;
    private final int width;
    private final int height;
    private final ScaleUnit scale;
    private final Organization organization;

    private final List<City> cities = new ArrayList<>();
    private final List<Industry> industries = new ArrayList<>();
    private final List<Station> stations = new ArrayList<>();

    private final Object[][] grid;

    public Map(String name, int width, int height, ScaleUnit scale, Organization organization) {
        if (name == null || name.trim().isEmpty()) throw new IllegalArgumentException("Map name cannot be empty");
        if (width <= 0 || height <= 0) throw new IllegalArgumentException("Map dimensions must be positive");
        if (scale == null) throw new IllegalArgumentException("Scale unit cannot be null");
        if (organization == null) throw new IllegalArgumentException("Organization cannot be null");

        this.name = name.trim();
        this.width = width;
        this.height = height;
        this.scale = scale;
        this.organization = organization;
        this.grid = new Object[height][width];
    }

    public String getName() {
        return name;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public ScaleUnit getScale() {
        return scale;
    }

    public Organization getOrganization() {
        return organization;
    }

    public List<Industry> getIndustries() {
        return Collections.unmodifiableList(industries);
    }

    public List<City> getCities() {
        return Collections.unmodifiableList(cities);
    }

    public boolean isOccupied(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= height) {
            throw new IllegalArgumentException("Coordinates out of bounds: (" + x + ", " + y + ")");
        }
        return grid[y][x] != null;
    }

    public Optional<City> addCity(String name, int x, int y, List<int[]> houseBlocks, Employee employee) {
        try {
            // Validate position
            if (isOccupied(x, y)) {
                return Optional.empty();
            }

            // Create and add city
            City city = new City(this, name, x, y, houseBlocks, employee);
            cities.add(city);
            grid[y][x] = city;

            // Mark house blocks as occupied
            for (int[] block : houseBlocks) {
                int blockX = block[0];
                int blockY = block[1];
                if (!isOccupied(blockX, blockY)) {
                    grid[blockY][blockX] = city;
                }
            }

            return Optional.of(city);
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }

    public void addIndustry(Industry industry) {
        int x = industry.getPositionX();
        int y = industry.getPositionY();

        if (!isOccupied(x, y)) {
            industries.add(industry);
            grid[y][x] = industry;
        } else {
            throw new IllegalArgumentException("Position already occupied");
        }
    }

    public void addStation(Station station) {
        int x = station.getX();
        int y = station.getY();

        if (!isOccupied(x, y)) {
            stations.add(station);
            grid[y][x] = station;
        } else {
            throw new IllegalArgumentException("Position already occupied");
        }
    }

    public List<Object> getObjectsAt(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= height) {
            return Collections.emptyList();
        }
        Object obj = grid[y][x];
        return obj != null ? Collections.singletonList(obj) : Collections.emptyList();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Map)) return false;
        Map map = (Map) o;
        return width == map.width &&
                height == map.height &&
                scale == map.scale &&
                name.equalsIgnoreCase(map.name) &&
                organization.equals(map.organization);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name.toLowerCase(), width, height, scale, organization);
    }

    @Override
    public String toString() {
        return String.format("Map: %s [%dx%d %s] | Org: %s | Cities: %d | Industries: %d | Stations: %d",
                name, width, height, scale, organization, cities.size(), industries.size(), stations.size());
    }
}