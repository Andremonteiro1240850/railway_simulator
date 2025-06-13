package pt.ipp.isep.dei.domain;

import java.util.List;
import java.util.Objects;

/**
 * Represents a city on a map with a name, coordinates, a list of house blocks,
 * and an assigned employee responsible for managing the city.
 */
public class City {
    private final Map map;
    private int x;
    private int y;
    private final String name;
    private final Employee employee;
    private List<int[]> houseBlocks;

    /**
     * Constructs a new City instance with the specified parameters.
     *
     * @param map         the map to which the city belongs
     * @param name        the name of the city (only letters and spaces allowed)
     * @param x           the x-coordinate of the city's location
     * @param y           the y-coordinate of the city's location
     * @param houseBlocks the list of house blocks in the city (each as [x, y] array)
     * @param employee    the employee responsible for this city
     * @throws IllegalArgumentException if any of the parameters are invalid
     */
    public City(Map map, String name, int x, int y, List<int[]> houseBlocks, Employee employee) {
        if (map == null) throw new IllegalArgumentException("Map cannot be null");
        if (name == null || name.trim().isEmpty()) throw new IllegalArgumentException("City name cannot be empty");
        if (!name.matches("^[a-zA-Z ]+$")) throw new IllegalArgumentException("City name can only contain letters and spaces");
        if (houseBlocks == null || houseBlocks.isEmpty()) throw new IllegalArgumentException("House blocks cannot be empty");
        if (employee == null) throw new IllegalArgumentException("Employee cannot be null");

        this.map = map;
        this.name = name.trim();
        this.x = x;
        this.y = y;
        this.houseBlocks = List.copyOf(houseBlocks); // Defensive copy
        this.employee = employee;
    }

    /**
     * Returns the name of the city.
     *
     * @return the city name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the x-coordinate of the city.
     *
     * @return the x-coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * Sets the x-coordinate of the city.
     *
     * @param x the new x-coordinate
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Returns the y-coordinate of the city.
     *
     * @return the y-coordinate
     */
    public int getY() {
        return y;
    }

    /**
     * Sets the y-coordinate of the city.
     *
     * @param y the new y-coordinate
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Returns a copy of the list of house blocks in the city.
     *
     * @return the list of house blocks
     */
    public List<int[]> getHouseBlocks() {
        return List.copyOf(houseBlocks);
    }

    /**
     * Sets the house blocks of the city.
     *
     * @param houseBlocks a new list of house blocks
     * @throws IllegalArgumentException if the list is null or empty
     */
    public void setHouseBlocks(List<int[]> houseBlocks) {
        if (houseBlocks == null || houseBlocks.isEmpty())
            throw new IllegalArgumentException("House blocks cannot be empty");
        this.houseBlocks = List.copyOf(houseBlocks);
    }

    /**
     * Checks if the given block coordinates exist among the house blocks of the city.
     *
     * @param blockX the x-coordinate of the block
     * @param blockY the y-coordinate of the block
     * @return true if the block exists, false otherwise
     */
    public boolean containsBlock(int blockX, int blockY) {
        for (int[] block : houseBlocks) {
            if (block[0] == blockX && block[1] == blockY) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns a string representation of the city, including name and coordinates.
     *
     * @return a string describing the city
     */
    @Override
    public String toString() {
        return "City [name=" + name + ", x=" + x + ", y=" + y + ", houseBlocks=" + houseBlocks.size() + "]";
    }

    /**
     * Checks if this city is equal to another object based on name (case-insensitive),
     * coordinates, and map reference.
     *
     * @param o the object to compare with
     * @return true if the cities are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof City)) return false;
        City city = (City) o;
        return x == city.x &&
                y == city.y &&
                name.equalsIgnoreCase(city.name) &&
                map.equals(city.map);
    }

    /**
     * Returns a hash code for this city based on name, coordinates and map.
     *
     * @return the hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(map, x, y, name.toLowerCase());
    }

    /**
     * Returns a copy of this City object.
     *
     * @return a new City with the same data
     */
    public City clone() {
        return new City(this.map, this.name, this.x, this.y, this.houseBlocks, this.employee);
    }
}
