// City.java
package pt.ipp.isep.dei.domain;

import java.util.List;
import java.util.Objects;

public class City {
    private Map map;
    private int x;
    private int y;
    private String name;
    private Employee employee;
    private List<int[]> houseBlocks; // List of [x,y] coordinates for house blocks

    public City(Map map, String name, int x, int y, List<int[]> houseBlocks, Employee employee) {
        if (map == null) throw new IllegalArgumentException("Map cannot be null");
        if (name == null || name.isEmpty()) throw new IllegalArgumentException("City name cannot be empty");
        if (!name.matches("^[a-zA-Z ]+$")) throw new IllegalArgumentException("City name cannot have special characters or digits");
        if (houseBlocks == null || houseBlocks.isEmpty()) throw new IllegalArgumentException("House blocks cannot be empty");

        this.map = map;
        this.name = name;
        this.x = x;
        this.y = y;
        this.houseBlocks = houseBlocks;
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
        return houseBlocks;
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