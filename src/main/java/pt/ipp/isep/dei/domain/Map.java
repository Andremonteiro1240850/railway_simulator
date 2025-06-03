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
    private final List<Scenario> scenarios = new ArrayList<>();

    public Map(String name, int width, int height, ScaleUnit scale, Organization organization) {
        if (name == null || name.isEmpty() || !name.matches("^[a-zA-Z0-9 _.-]+$")) {
            throw new IllegalArgumentException("Invalid map name");
        }
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Invalid dimensions");
        }
        if (scale == null) {
            throw new IllegalArgumentException("Invalid scale");
        }
        if (organization == null) {
            throw new IllegalArgumentException("Organization is required");
        }

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
        return Collections.unmodifiableList(cities);
    }

    // Método para adicionar cidade com a opção de definir blocos de casas manualmente
    public Optional<City> addCity(String name, int x, int y, List<int[]> houseBlocks, Employee employee) {
        if (!isValidCityName(name)) {
            throw new IllegalArgumentException("City name cannot have special characters or digits.");
        }

        if (houseBlocks == null || houseBlocks.isEmpty()) {
            throw new IllegalArgumentException("There must be at least one house block.");
        }

        City city = new City(this, name, x, y, houseBlocks, employee); // Criando cidade no mapa com as coordenadas, blocos e funcionário
        cities.add(city);

        return Optional.of(city);
    }

    // Método para validar nome da cidade
    private boolean isValidCityName(String name) {
        return name.matches("^[a-zA-Z ]+$"); // Apenas letras e espaços
    }

    // Método para adicionar indústria
    public void addIndustry(Industry industry) {
        if (industry == null) throw new IllegalArgumentException("Industry cannot be null");

        // Valida se a posição está ocupada
        if (hasIndustryAt(industry.getPositionX(), industry.getPositionY())) {
            throw new IllegalArgumentException("Position is already occupied by another industry");
        }
        industries.add(industry);
    }

    // Verifica se já existe uma indústria na posição indicada
    public boolean hasIndustryAt(int x, int y) {
        return industries.stream().anyMatch(i -> i.getPositionX() == x && i.getPositionY() == y);
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
        return String.format("Map: %s [%dx%d %s] | Org: %s | Cities: %d | Industries: %d | Stations: %d | Events: %d",
                name, width, height, scale, organization, cities.size(), industries.size(), stations.size(), events.size());
    }
}
