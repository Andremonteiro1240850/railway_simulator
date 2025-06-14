package pt.ipp.isep.dei.domain;

import pt.ipp.isep.dei.domain.enums.Buildings;
import pt.ipp.isep.dei.domain.enums.StationUpgrade;
import pt.ipp.isep.dei.domain.Map;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Represents a train station that can be upgraded and managed with employees.
 * A station has a type (depot, terminal, or station), a name, a region,
 * and may have multiple building upgrades that provide various bonuses.
 */
public class Station {
    private String name;
    private String type;
    private String region;
    private String reference;
    private String scenario;
    private int area;
    private int x;
    private int y;
    private List<StationUpgrade> upgrades = new ArrayList<>();
    private transient StationType stationTypeHandler;
    private List<String> availableCargos = new ArrayList<>();
    private final java.util.Map<String, Integer> demandaPorCarga = new java.util.HashMap<>();

    /**
     * Constructs a new Station with a specific reference.
     *
     * @param reference The unique identifier for the station.
     * @param scenario  The scenario the station is located in.
     * @param name      The name of the station.
     * @param type      The type of the station (e.g., "depot", "terminal", or "station").
     * @param region    The region where the station is located.
     * @throws IllegalArgumentException If the station type or region is invalid.
     */
    public Station(String reference, String name, String type, String region, String scenario, int area,int x, int y) {
        this.reference = reference;
        this.name = name;
        this.type = type;
        this.region = region;
        this.scenario = scenario;
        this.area = area;
        this.x = x;
        this.y = y;
        initializeTypeHandler();
        initializeAvailableCargos();
    }

    /**
     * Constructs a new Station with a generated reference.
     *
     * @param name   The name of the station.
     * @param type   The type of the station (e.g., "depot", "terminal", or "station").
     * @param region The region where the station is located.
     */
    public Station(String name, String type, String region, String scenario, int area,int x, int y) {
        this(generateDefaultReference(), name, type, region, scenario,area,x,y);
    }

    /**
     * Generates a unique default reference using the current system time.
     *
     * @return A unique reference string.
     */
    private static String generateDefaultReference() {
        return "ST-" + System.currentTimeMillis();
    }

    /**
     * Validates the station type and region.
     *
     * @param type   The station type.
     * @param region The station region.
     * @throws IllegalArgumentException If the type is invalid or the region is empty.
     */
    /**
     * @return The unique reference of the station.
     */
    public String getReference() {
        return reference;
    }

    /**
     * @return The name of the station.
     */
    public String getName() {
        return name;
    }

    /**
     * @return The type of the station.
     */
    public String getType() {
        return type;
    }

    /**
     * @return The region where the station is located.
     */
    public String getRegion() {
        return region;
    }
    public String getScenario() {return scenario;}
    public int getArea() {
        return area;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public Station copy() {
        Station copy = new Station(this.reference, this.name, this.type, this.region, this.scenario, this.area, this.x, this.y);
        copy.upgrades = new ArrayList<>(this.upgrades);  // Considera clonar upgrades também se necessário
        return copy;
    }


    private void initializeTypeHandler() {
        switch (type.toLowerCase()) {
            case "depot":
                stationTypeHandler = new DepotStationType(); break;
            case "terminal":
                stationTypeHandler = new TerminalStationType(); break;
            case "station":
                stationTypeHandler = new RegularStationType(); break;
            default:
                stationTypeHandler = null;
        }
    }

    private void initializeAvailableCargos() {
        // Cada tipo de estação tem diferentes cargas disponíveis
        switch (type.toLowerCase()) {
            case "depot":
                availableCargos.addAll(List.of("coal", "iron", "bauxite"));
                break;
            case "terminal":
                availableCargos.addAll(List.of("passengers", "mail", "food", "textiles", "automobiles"));
                break;
            case "station":
                availableCargos.addAll(List.of("passengers", "mail", "food", "textiles", "coal", "iron"));
                break;
        }
    }

    public List<String> getAvailableCargos() {
        return new ArrayList<>(availableCargos);
    }

    public boolean hasCargoAvailable(String cargo) {
        return availableCargos.contains(cargo.toLowerCase());
    }

    /**
     * Adds a building upgrade to the station if not already present.
     *
     * @param upgrade The building upgrade to add.
     */
    public void addUpgrade(StationUpgrade upgrade) {
        if (!upgrades.contains(upgrade)) {
            upgrades.add(upgrade);
        }
    }

    /**
     * @return A list of all upgrades applied to the station.
     */
    public List<StationUpgrade> getUpgrades() {
        return new ArrayList<>(upgrades);
    }

    /**
     * Calculates the total cost of all applied upgrades.
     *
     * @return Total cost in game currency.
     */
    public int getTotalUpgradeCost() {
        return upgrades.stream()
                .mapToInt(StationUpgrade::getCost)
                .sum();
    }

    /**
     * Retrieves the list of all bonuses from applied upgrades.
     *
     * @return A list of bonus descriptions (apenas nomes dos upgrades).
     */
    public List<String> getAllBonuses() {
        return upgrades.stream()
                .map(StationUpgrade::getDisplayName)
                .collect(Collectors.toList());
    }

    public int getMaintenanceCost() {
        return stationTypeHandler != null ? stationTypeHandler.getMaintenanceCost() : 0;
    }


    /**
     * Checks equality based on the station reference.
     *
     * @param o The object to compare.
     * @return True if the stations have the same reference.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Station station = (Station) o;
        return Objects.equals(name, station.name) &&
                Objects.equals(type, station.type) &&
                Objects.equals(region, station.region) &&
                Objects.equals(reference, station.reference) &&
                Objects.equals(scenario, station.scenario) &&
                Objects.equals(x, station.x) &&
                Objects.equals(y, station.y) &&
                Objects.equals(upgrades, station.upgrades);
    }
    /**
     * @return A hash code based on the reference.
     */
    @Override
    public int hashCode() {
        return Objects.hash(reference,name,type,region,scenario,upgrades,x,y);
    }

    /**
     * Creates a deep copy of the station, including upgrades and assigned employee.
     *
     * @return A clone of this station.
     */
    public Station clone() {
        Station clone = new Station(this.reference, this.name, this.type, this.region,this.scenario,this.area,this.x,this.y);
        clone.upgrades = new ArrayList<>(this.upgrades);
        return clone;
    }

    @Override
    public String toString() {
        return String.format(
                "Station [Name: %s, Type: %s, Region: %s, Ref: %s, Scenario: %s,Area: %d, Coordinates: (%d, %d), Upgrades: %d]",
                name,
                type,
                region,
                reference,
                scenario,
                area,
                x,
                y,
                upgrades != null ? upgrades.size() : 0
        );
    }

    public int getProximityRadius() {
        switch (type.toLowerCase()) {
            case "depot": return 2;
            case "terminal": return 4;
            case "station": return 3;
            default: return 2;
        }
    }

    public List<City> getNearbyCities(Map map) {
        int radius = getProximityRadius();
        List<City> nearbyCities = new ArrayList<>();
        for (City city : map.getCities()) {
            int dx = city.getX() - this.x;
            int dy = city.getY() - this.y;
            double distance = Math.sqrt(dx * dx + dy * dy);
            if (distance <= radius) {
                nearbyCities.add(city);
            }
        }
        return nearbyCities;
    }

    public List<Industry> getNearbyIndustries(Map map) {
        int radius = getProximityRadius();
        List<Industry> nearbyIndustries = new ArrayList<>();
        for (Industry industry : map.getIndustries()) {
            int dx = industry.getPositionX() - this.x;
            int dy = industry.getPositionY() - this.y;
            double distance = Math.sqrt(dx * dx + dy * dy);
            if (distance <= radius) {
                nearbyIndustries.add(industry);
            }
        }
        return nearbyIndustries;
    }

    public int getPassengerLoadRate(Map map) {
        int rate = 0;
        for (City city : getNearbyCities(map)) {
            rate += city.getHouseBlocks().size() * 2; // 2 passageiros por house block
        }
        return rate;
    }

    public int getCargoLoadRate(Map map, String cargoType) {
        int rate = 0;
        for (Industry industry : getNearbyIndustries(map)) {
            if (industry.getType().name().equalsIgnoreCase(cargoType)) {
                rate += 10; // 10 unidades por indústria correspondente
            }
        }
        return rate;
    }

    public int getDemanda(String tipoCarga) {
        return demandaPorCarga.getOrDefault(tipoCarga.toLowerCase(), 5); // valor médio padrão
    }

    public void setDemanda(String tipoCarga, int valor) {
        if (valor < 1) valor = 1;
        if (valor > 10) valor = 10;
        demandaPorCarga.put(tipoCarga.toLowerCase(), valor);
    }

    public void atualizarDemandaAleatoria() {
        for (String carga : availableCargos) {
            int atual = getDemanda(carga);
            int variacao = (int) (Math.random() * 3) - 1; // -1, 0 ou +1
            setDemanda(carga, atual + variacao);
        }
    }
}
