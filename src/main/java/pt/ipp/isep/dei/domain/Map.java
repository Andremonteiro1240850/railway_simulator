package pt.ipp.isep.dei.domain;

import pt.ipp.isep.dei.domain.enums.ScaleUnit;
import java.util.*;

public class Map {
    private final String name;
    private final int width; // Colunas
    private final int height; // Linhas
    private final ScaleUnit scale;
    private final Organization organization;

    private final List<City> cities = new ArrayList<>();
    private final List<Industry> industries = new ArrayList<>();
    private final List<Station> stations = new ArrayList<>();

    private final Object[][] grid; // grade 2D representando o mapa

    public Map(String name, int width, int height, ScaleUnit scale, Organization organization) {
        this.name = name;
        this.width = width;
        this.height = height;
        this.scale = scale;
        this.organization = organization;
        this.grid = new Object[height][width]; // [linhas][colunas]
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

    public List<City> getCities() {
        return Collections.unmodifiableList(cities);
    }

    public boolean isOccupied(int x, int y) {
        return grid[y][x] != null;
    }

    public Optional<City> addCity(String name, int x, int y, List<int[]> houseBlocks, Employee employee) {
        if (isOccupied(x, y)) {
            throw new IllegalArgumentException("Position is already occupied.");
        }

        City city = new City(this, name, x, y, houseBlocks, employee);
        cities.add(city);
        grid[y][x] = city;
        return Optional.of(city);
    }

    public void addIndustry(Industry industry) {
        int x = industry.getPositionX();
        int y = industry.getPositionY();

        if (isOccupied(x, y)) {
            throw new IllegalArgumentException("Position is already occupied");
        }

        industries.add(industry);
        grid[y][x] = industry;
    }

    public void addStation(Station station) {
        int x = station.getX();
        int y = station.getY();

        if (isOccupied(x, y)) {
            throw new IllegalArgumentException("Position is already occupied");
        }

        stations.add(station);
        grid[y][x] = station;
    }

    public List<Object> getObjectsAt(int x, int y) {
        List<Object> list = new ArrayList<>();
        Object obj = grid[y][x];
        if (obj != null) list.add(obj);
        return list;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Map)) return false;
        Map map = (Map) o;
        return width == map.width && height == map.height && scale == map.scale && name.equalsIgnoreCase(map.name) && organization.equals(map.organization);
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
