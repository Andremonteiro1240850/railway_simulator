package pt.ipp.isep.dei.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
/**
 * Represents a station in the transportation network, which can be of different types
 * (depot, terminal, or regular station) and belongs to a specific region.
 * Stations can have upgrades and may be assigned an employee.
 */

public class Station {
    private String name;
    private String type; // "depot", "terminal", "station"
    private String region;
    private Employee employee;
    private String reference;  // Novo campo adicionado
    private List<StationUpgrade> upgrades = new ArrayList<>();
    /**
     * Constructs a new Station with the specified reference, name, type, and region.
     *
     * @param reference The unique reference identifier for the station
     * @param name The name of the station
     * @param type The type of station ("depot", "terminal", or "station")
     * @param region The region where the station is located
     * @throws IllegalArgumentException if type or region are invalid
     */

    // Construtor modificado para incluir reference
    public Station(String reference, String name, String type, String region) {
        this.reference = reference;
        this.name = name;
        this.type = type;
        this.region = region;
        validateTypeAndRegion(type, region);
    }
    /**
     * Constructs a new Station with auto-generated reference, name, type, and region.
     *
     * @param name The name of the station
     * @param type The type of station ("depot", "terminal", or "station")
     * @param region The region where the station is located
     * @throws IllegalArgumentException if type or region are invalid
     */

    // Construtor antigo mantido para compatibilidade
    public Station(String name, String type, String region) {
        this(generateDefaultReference(), name, type, region);
    }
    /**
     * Generates a default unique reference for the station.
     *
     * @return A unique reference string in format "ST-[timestamp]"
     */

    private static String generateDefaultReference() {
        return "ST-" + System.currentTimeMillis(); // Gera uma referência única
    }
    /**
     * Validates that the station type and region are acceptable values.
     *
     * @param type The station type to validate
     * @param region The region to validate
     * @throws IllegalArgumentException if type or region are invalid
     */

    private void validateTypeAndRegion(String type, String region) {

    }
    /**
     * Gets the station's unique reference identifier.
     *
     * @return The station reference
     */

    // Getters
    public String getReference() {
        return reference;
    }
    /**
     * Gets the station name.
     *
     * @return The station name
     */

    public String getName() {
        return type;
    }
    /**
     * Gets the station type.
     *
     * @return The station type ("depot", "terminal", or "station")
     */

    public String getType() {
        return name;
    }
    /**
     * Gets the station region.
     *
     * @return The region where the station is located
     */

    public String getRegion() {
        return region;
    }
    /**
     * Gets the employee assigned to this station, if any.
     *
     * @return The assigned employee, or null if none is assigned
     */

    public Employee getEmployee() {
        return employee;
    }
    /**
     * Compares stations based on their unique reference.
     *
     * @param o The object to compare with
     * @return true if the stations have the same reference, false otherwise
     */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Station)) return false;
        Station station = (Station) o;
        return reference.equals(station.reference); // Agora compara por reference
    }

    @Override
    public int hashCode() {
        return Objects.hash(reference); // Agora usa reference
    }

    public Station clone() {
        Station clone = new Station(this.reference, this.name, this.type, this.region);
        clone.employee = this.employee != null ? this.employee.clone() : null;
        clone.upgrades = new ArrayList<>(this.upgrades);
        return clone;
    }
    /**
     * Adds an upgrade to this station.
     *
     * @param upgrade The upgrade to add
     */

    public void addUpgrade(StationUpgrade upgrade) {
        this.upgrades.add(upgrade);
    }
    /**
     * Gets a list of all upgrades applied to this station.
     *
     * @return A new ArrayList containing all station upgrades
     */

    public List<StationUpgrade> getUpgrades() {
        return new ArrayList<>(upgrades);
    }
}