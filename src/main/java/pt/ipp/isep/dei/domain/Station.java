package pt.ipp.isep.dei.domain;

import pt.ipp.isep.dei.domain.enums.Buildings;
import pt.ipp.isep.dei.domain.enums.StationUpgrade;
import pt.ipp.isep.dei.domain.enums.StationTypes;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.UUID;
import java.util.Random;
import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;
import java.io.Serializable;

/**
 * Represents a train station that can be upgraded and managed with employees.
 * A station has a type (depot, terminal, or station), a name, a region,
 * and may have multiple building upgrades that provide various bonuses.
 */
public class Station implements Serializable {
    private static final long serialVersionUID = 1L;
    private String reference;
    private String name;
    private String type;
    private String region;
    private String scenario;
    private int area;
    private int x;
    private int y;
    private List<StationUpgrade> upgrades = new ArrayList<>();
    private transient StationType stationTypeHandler;
    private List<String> availableCargos = new ArrayList<>();
    private Map<String, Integer> demandas = new HashMap<>();

    /**
     * Constructs a new Station with auto-generated reference.
     */
    public Station(String name, String type, String region, String scenario, int area, int x, int y) {
        this.name = name;
        this.type = type;
        this.region = region;
        this.scenario = scenario;
        this.area = area;
        this.x = x;
        this.y = y;
        this.upgrades = new ArrayList<>();
        this.reference = generateReference();
        initializeTypeHandler();
        initializeAvailableCargos();
        initializeDefaultDemandas();
    }

    /**
     * Constructs a new Station with specified reference.
     */
    public Station(String reference, String name, String type, String region, String scenario, int area, int x, int y) {
        this(name, type, region, scenario, area, x, y);
        this.reference = reference;
    }

    private String generateReference() {
        return "ST-" + UUID.randomUUID().toString().substring(0, 8);
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

    private void initializeDefaultDemandas() {
        for (String cargo : getAvailableCargos()) {
            demandas.put(cargo, 5); // Default value
        }
    }

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

    public String getScenario() {
        return scenario;
    }

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

    public List<City> getNearbyCities(pt.ipp.isep.dei.domain.Map map) {
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

    public List<Industry> getNearbyIndustries(pt.ipp.isep.dei.domain.Map map) {
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

    public int getPassengerLoadRate(pt.ipp.isep.dei.domain.Map map) {
        int rate = 0;
        for (City city : getNearbyCities(map)) {
            rate += city.getHouseBlocks().size() * 2; // 2 passageiros por house block
        }
        return rate;
    }

    public int getCargoLoadRate(pt.ipp.isep.dei.domain.Map map, String cargoType) {
        int rate = 0;
        for (Industry industry : getNearbyIndustries(map)) {
            if (industry.getType().name().equalsIgnoreCase(cargoType)) {
                rate += 10; // 10 unidades por indústria correspondente
            }
        }
        return rate;
    }

    public int getDemanda(String tipoCarga) {
        return demandas.getOrDefault(tipoCarga.toLowerCase(), 5); // valor médio padrão
    }

    public void setDemanda(String tipoCarga, int valor) {
        if (valor < 1) valor = 1;
        if (valor > 10) valor = 10;
        demandas.put(tipoCarga.toLowerCase(), valor);
    }

    public void atualizarDemandaAleatoria() {
        Random random = new Random();
        for (String carga : demandas.keySet()) {
            int atual = getDemanda(carga);
            int variacao = random.nextInt(3) - 1; // -1, 0 ou +1
            setDemanda(carga, atual + variacao);
        }
    }

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

    @Override
    public int hashCode() {
        return Objects.hash(reference,name,type,region,scenario,upgrades,x,y);
    }
}
