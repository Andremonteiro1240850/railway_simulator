package pt.ipp.isep.dei.controller;

import pt.ipp.isep.dei.domain.*;
import pt.ipp.isep.dei.domain.enums.Regions;
import pt.ipp.isep.dei.domain.enums.StationTypes;
import pt.ipp.isep.dei.repository.*;
import pt.isep.lei.esoft.auth.domain.model.Email;

import java.util.List;
import java.util.Optional;

/**
 * Controller responsible for creating and managing stations within a scenario.
 */
public class BuildStationController {
    private OrganizationRepository organizationRepository;
    private AuthenticationRepository authenticationRepository;
    private StationRepository stationRepository;
    private StationTypes stationTypes;
    private Station station;
    private Player player;
    private Regions region;
    private Scenario scenario;

    /**
     * Default constructor. Initializes the repositories from the shared Repositories instance.
     */
    public BuildStationController() {
        getOrganizationRepository();
        getAuthenticationRepository();
        Repositories.getInstance().getStationRepository();
    }

    /**
     * Constructor with dependency injection. Used mainly for testing purposes.
     *
     * @param organizationRepository      the organization repository
     * @param authenticationRepository    the authentication repository
     * @param stationRepository           the station repository
     */
    public BuildStationController(OrganizationRepository organizationRepository,
                                  AuthenticationRepository authenticationRepository,
                                  StationRepository stationRepository) {
        this.organizationRepository = organizationRepository;
        this.authenticationRepository = authenticationRepository;
        this.stationRepository = stationRepository;
    }

    /**
     * Retrieves the organization repository, initializing it if necessary.
     *
     * @return the organization repository
     */
    private OrganizationRepository getOrganizationRepository() {
        if (organizationRepository == null) {
            Repositories repositories = Repositories.getInstance();
            organizationRepository = repositories.getOrganizationRepository();
        }
        return organizationRepository;
    }

    /**
     * Retrieves the authentication repository, initializing it if necessary.
     *
     * @return the authentication repository
     */
    private AuthenticationRepository getAuthenticationRepository() {
        if (authenticationRepository == null) {
            Repositories repositories = Repositories.getInstance();
            authenticationRepository = repositories.getAuthenticationRepository();
        }
        return authenticationRepository;
    }

    /**
     * Sets the current scenario to be used for building stations.
     *
     * @param scenario the scenario to set
     */
    public void setScenario(Scenario scenario) {
        this.scenario = scenario;
    }

    /**
     * Suggests a name for the station based on the station type and nearest city.
     *
     * @param x           the x-coordinate of the station
     * @param y           the y-coordinate of the station
     * @param stationType the type of the station
     * @return a suggested name
     */
    public String suggestStationName(int x, int y, StationTypes stationType) {
        if (scenario == null || scenario.getMap() == null) {
            return stationType.getType() + " Nova"; // Fallback name
        }

        Map map = scenario.getMap();
        List<City> cities = map.getCities();

        if (cities.isEmpty()) {
            return stationType.getType() + " Nova";
        }

        City nearestCity = findNearestCity(x, y, cities);
        return String.format("%s de %s", stationType.getType(), nearestCity.getName());
    }

    /**
     * Finds the nearest city to the specified coordinates.
     *
     * @param x      the x-coordinate
     * @param y      the y-coordinate
     * @param cities the list of cities to compare
     * @return the closest city
     */
    private City findNearestCity(int x, int y, List<City> cities) {
        City nearest = cities.get(0);
        double minDistance = calculateDistance(x, y, nearest.getX(), nearest.getY());

        for (City city : cities) {
            double distance = calculateDistance(x, y, city.getX(), city.getY());
            if (distance < minDistance) {
                minDistance = distance;
                nearest = city;
            }
        }
        return nearest;
    }

    /**
     * Calculates the Euclidean distance between two coordinate points.
     *
     * @param x1 first x-coordinate
     * @param y1 first y-coordinate
     * @param x2 second x-coordinate
     * @param y2 second y-coordinate
     * @return the distance between the points
     */
    private double calculateDistance(int x1, int y1, int x2, int y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

    /**
     * Creates a new station on the map if valid.
     * Validates coordinates, station type, region, available budget, and existing stations.
     *
     * @param typeName the name of the station type
     * @param name     the name of the station (can be null for auto-suggestion)
     * @param region   the region of the station
     * @param x        the x-coordinate of the station
     * @param y        the y-coordinate of the station
     * @return true if the station was successfully created, false otherwise
     */
    public boolean createStation(String typeName, String name, String region, int x, int y) {
        if (scenario == null) {
            System.out.println("Erro: Nenhum cenário definido. Selecione um cenário primeiro.");
            return false;
        }

        Map map = scenario.getMap();
        if (x < 0 || x >= map.getWidth() || y < 0 || y >= map.getHeight()) {
            System.out.println("Erro: Coordenadas fora dos limites do mapa.");
            return false;
        }

        StationTypes stationType = null;
        for (StationTypes st : StationTypes.values()) {
            if (st.getType().equalsIgnoreCase(typeName)) {
                stationType = st;
                break;
            }
        }

        if (stationType == null) {
            System.out.println("Erro: Tipo de estação inválido.");
            return false;
        }

        Regions regionType = null;
        for (Regions r : Regions.values()) {
            if (r.getRegion().equalsIgnoreCase(region)) {
                regionType = r;
                break;
            }
        }

        if (regionType == null) {
            System.out.println("Erro: Região inválida.");
            return false;
        }

        List<Station> existingStations = Repositories.getInstance().getStationRepository().getAll();
        for (Station s : existingStations) {
            if (s.getX() == x && s.getY() == y) {
                System.out.println("Erro: Já existe uma estação nessa localização (" + x + ", " + y + ").");
                return false;
            }
        }

        double budget = scenario.getBudget();
        double cost = stationType.getCost();

        if (budget < cost) {
            System.out.println("Erro: Orçamento insuficiente para construir esta estação (necessário: " + cost + ").");
            return false;
        }

        String stationName = name;
        if (name == null || name.trim().isEmpty()) {
            stationName = suggestStationName(x, y, stationType);
        }

        Station newStation = new Station(stationName, stationType.getType(), regionType.getRegion(), scenario.getName(), stationType.getArea(), x, y);
        scenario.addStationPurchased(newStation);
        Optional<Station> added = Repositories.getInstance().getStationRepository().add(newStation);

        if (added.isPresent()) {
            scenario.setBudget(budget - cost);
            System.out.println("Estação criada com sucesso: " + newStation.getReference());
            return true;
        } else {
            System.out.println("Erro: Estação não foi criada (duplicada ou erro interno).");
            return false;
        }
    }
}




