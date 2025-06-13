package pt.ipp.isep.dei.domain;

import pt.ipp.isep.dei.domain.enums.ScaleUnit;
import java.util.*;

/**
 * Represents a map with a defined width, height, scale, and associated organization.
 * It includes cities, industries, stations, and a grid to manage placement of elements.
 */
public class Map {
    private String name;
    private int width;
    private int height;
    private ScaleUnit scale;
    private final Organization organization;

    private final List<City> cities = new ArrayList<>();
    private final List<Industry> industries = new ArrayList<>();
    private final List<Station> stations = new ArrayList<>();

    private final Object[][] grid;

    /**
     * Constructs a new Map instance with given parameters.
     *
     * @param name         the name of the map
     * @param width        map width (number of columns)
     * @param height       map height (number of rows)
     * @param scale        the scale unit of the map
     * @param organization the organization that owns the map
     * @throws IllegalArgumentException if any parameter is invalid
     */
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

    /**
     * Returns the name of the map.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the map.
     *
     * @param name the new name
     * @throws IllegalArgumentException if name is null or empty
     */
    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) throw new IllegalArgumentException("Map name cannot be empty");
        this.name = name.trim();
    }

    /**
     * Returns the width of the map.
     *
     * @return the width
     */
    public int getWidth() {
        return width;
    }

    /**
     * Sets the width of the map.
     *
     * @param width the new width
     * @throws IllegalArgumentException if width is not positive
     */
    public void setWidth(int width) {
        if (width <= 0) throw new IllegalArgumentException("Width must be positive");
        this.width = width;
    }

    /**
     * Returns the height of the map.
     *
     * @return the height
     */
    public int getHeight() {
        return height;
    }

    /**
     * Sets the height of the map.
     *
     * @param height the new height
     * @throws IllegalArgumentException if height is not positive
     */
    public void setHeight(int height) {
        if (height <= 0) throw new IllegalArgumentException("Height must be positive");
        this.height = height;
    }

    /**
     * Returns the scale unit of the map.
     *
     * @return the scale
     */
    public ScaleUnit getScale() {
        return scale;
    }

    /**
     * Sets the scale unit of the map.
     *
     * @param scale the new scale
     * @throws IllegalArgumentException if scale is null
     */
    public void setScale(ScaleUnit scale) {
        if (scale == null) throw new IllegalArgumentException("Scale cannot be null");
        this.scale = scale;
    }

    /**
     * Returns the organization associated with the map.
     *
     * @return the organization
     */
    public Organization getOrganization() {
        return organization;
    }

    /**
     * Returns the list of industries on the map (read-only).
     *
     * @return list of industries
     */
    public List<Industry> getIndustries() {
        return Collections.unmodifiableList(industries);
    }

    /**
     * Returns the list of cities on the map (read-only).
     *
     * @return list of cities
     */
    public List<City> getCities() {
        return Collections.unmodifiableList(cities);
    }

    /**
     * Checks if a given position is occupied on the map.
     *
     * @param x x-coordinate
     * @param y y-coordinate
     * @return true if the cell is occupied, false otherwise
     * @throws IllegalArgumentException if coordinates are out of bounds
     */
    public boolean isOccupied(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= height) {
            throw new IllegalArgumentException("Coordinates out of bounds: (" + x + ", " + y + ")");
        }
        return grid[y][x] != null;
    }

    /**
     * Adds a new city to the map at the specified location.
     *
     * @param name         the name of the city
     * @param x            x-coordinate of the city
     * @param y            y-coordinate of the city
     * @param houseBlocks  list of house block positions
     * @param employee     the employee managing the city
     * @return Optional containing the city if successful, or empty if invalid/occupied
     */
    public Optional<City> addCity(String name, int x, int y, List<int[]> houseBlocks, Employee employee) {
        try {
            if (isOccupied(x, y)) {
                return Optional.empty();
            }

            City city = new City(this, name, x, y, houseBlocks, employee);
            cities.add(city);
            grid[y][x] = city;

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

    /**
     * Adds an industry to the map at its specified location.
     *
     * @param industry the industry to add
     * @throws IllegalArgumentException if the position is already occupied
     */
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

    /**
     * Adds a station to the map at its specified location.
     *
     * @param station the station to add
     * @throws IllegalArgumentException if the position is already occupied
     */
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

    /**
     * Returns the list of objects at the specified coordinates.
     *
     * @param x x-coordinate
     * @param y y-coordinate
     * @return list containing one object if occupied, or empty if not
     */
    public List<Object> getObjectsAt(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= height) {
            return Collections.emptyList();
        }
        Object obj = grid[y][x];
        return obj != null ? Collections.singletonList(obj) : Collections.emptyList();
    }

    /**
     * Compares this map with another object for equality.
     *
     * @param o the object to compare
     * @return true if all relevant attributes are equal
     */
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

    /**
     * Returns a hash code for this map.
     *
     * @return the hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(name.toLowerCase(), width, height, scale, organization);
    }

    /**
     * Returns a string representation of the map.
     *
     * @return formatted string with map metadata
     */
    @Override
    public String toString() {
        return String.format("Map: %s [%dx%d %s] | Org: %s | Cities: %d | Industries: %d | Stations: %d",
                name, width, height, scale, organization, cities.size(), industries.size(), stations.size());
    }
}
