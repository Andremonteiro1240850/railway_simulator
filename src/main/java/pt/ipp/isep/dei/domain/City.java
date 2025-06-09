package pt.ipp.isep.dei.domain;

import java.util.List;
import java.util.Objects;

public class City {
    private final Map map;
    private final int x;
    private final int y;
    private final String name;
    private final Employee employee;
    private final List<int[]> houseBlocks;

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

    public String getName() {
        return name;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public List<int[]> getHouseBlocks() {
        return List.copyOf(houseBlocks); // Defensive copy
    }

    public boolean containsBlock(int blockX, int blockY) {
        for (int[] block : houseBlocks) {
            if (block[0] == blockX && block[1] == blockY) {
                return true;
            }
        }
        return false;
    }

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

    @Override
    public int hashCode() {
        return Objects.hash(map, x, y, name.toLowerCase());
    }

    public City clone() {
        return new City(this.map, this.name, this.x, this.y, this.houseBlocks, this.employee);
    }
}