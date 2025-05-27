package pt.ipp.isep.dei.domain;

import pt.ipp.isep.dei.domain.enums.ScaleUnit;

import java.util.*;

/**
 * Represents a map with all elements and associated to an organization.
 */
public class Map {
    private final String name;
    private final int width;
    private final int height;
    private final ScaleUnit scale;
    private final Organization organization;

    private final List<City> cities = new ArrayList<>();
    private final List<Industry> industries = new ArrayList<>();
    private final List<Station> stations = new ArrayList<>();
    private final List<HistoricalEvent> events = new ArrayList<>();

    public Map(String name, int width, int height, ScaleUnit scale, Organization organization) {
        this.name = name;
        this.width = width;
        this.height = height;
        this.scale = scale;
        this.organization = organization;
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
        return new ArrayList<>(cities);
    }

    public List<Industry> getIndustries() {
        return new ArrayList<>(industries);
    }

    public List<Station> getStations() {
        return new ArrayList<>(stations);
    }


    public List<HistoricalEvent> getEvents() {
        return new ArrayList<>(events);
    }

    public void addCity(City city) {
        cities.add(city);
    }

    public void addIndustry(Industry industry) {
        industries.add(industry);
    }

    public void addStation(Station station) {
        stations.add(station);
    }


    public void addHistoricalEvent(HistoricalEvent event) {
        events.add(event);
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
        return String.format("Map: %s [%dx%d %s] | Org: %s | Cities: %d | Industries: %d",
                name, width, height, scale, cities.size(), industries.size());
    }
}
