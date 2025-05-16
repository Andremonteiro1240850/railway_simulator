package pt.ipp.isep.dei.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a Map with a name and dimensions (width and height).
 */
public class Map {
    private final String name;
    private final int width;
    private final int height;
    private final List<Industry> industries;
    private final List<City> cities;

    /**
     * Constructs a new Map.
     * @param name the name of the map
     * @param width the width of the map (must be positive)
     * @param height the height of the map (must be positive)
     */
    public Map(String name, int width, int height) {
        this.name = name;
        this.width = width;
        this.height = height;
        this.industries = new ArrayList<>();
        this.cities = new ArrayList<>();
    }

    public String getName() { return name; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }

    public List<Industry> getIndustries() { return Collections.unmodifiableList(industries); }
    public List<City> getCities() { return Collections.unmodifiableList(cities); }

    /** Adds an industry to the map */
    public void addIndustry(Industry industry) { industries.add(industry); }

    /** Adds a city to the map */
    public void addCity(City city) { cities.add(city); }

    public boolean isPositionFree(int x, int y) {
        return cities.stream().noneMatch(c -> c.getX() == x && c.getY() == y);
    }
    public boolean addCity(City city) {
        if (isPositionValid(city.getX(), city.getY())) {
            cities.add(city);
            return true;
        }
        return false;
    }

}