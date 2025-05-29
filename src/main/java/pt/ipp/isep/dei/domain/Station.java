package pt.ipp.isep.dei.domain;

import pt.ipp.isep.dei.domain.enums.Buildings;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a train station that can be upgraded and managed with employees.
 * A station has a type (depot, terminal, or station), a name, a region,
 * and may have multiple building upgrades that provide various bonuses.
 */
public class Station {
    private String name;
    private String type;
    private String region;
    private Employee employee;
    private String reference;
    private List<Buildings> upgrades = new ArrayList<>();

    /**
     * Constructs a new Station with a specific reference.
     *
     * @param reference The unique identifier for the station.
     * @param name      The name of the station.
     * @param type      The type of the station (e.g., "depot", "terminal", or "station").
     * @param region    The region where the station is located.
     * @throws IllegalArgumentException If the station type or region is invalid.
     */
    public Station(String reference, String name, String type, String region) {
        this.reference = reference;
        this.name = name;
        this.type = type;
        this.region = region;
        validateTypeAndRegion(type, region);
    }

    /**
     * Constructs a new Station with a generated reference.
     *
     * @param name   The name of the station.
     * @param type   The type of the station (e.g., "depot", "terminal", or "station").
     * @param region The region where the station is located.
     */
    public Station(String name, String type, String region) {
        this(generateDefaultReference(), name, type, region);
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
    private void validateTypeAndRegion(String type, String region) {
        List<String> validTypes = List.of("depot", "terminal", "station");
        if (!validTypes.contains(type.toLowerCase())) {
            throw new IllegalArgumentException("Invalid station type: " + type);
        }

        if (region == null || region.trim().isEmpty()) {
            throw new IllegalArgumentException("Region must not be empty.");
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

    /**
     * @return The employee assigned to this station, or null if none.
     */
    public Employee getEmployee() {
        return employee;
    }

    /**
     * Assigns an employee to the station.
     *
     * @param employee The employee to assign.
     */
    public void assignEmployee(Employee employee) {
        this.employee = employee;
    }

    /**
     * Adds a building upgrade to the station if not already present.
     *
     * @param upgrade The building upgrade to add.
     */
    public void addUpgrade(Buildings upgrade) {
        if (!upgrades.contains(upgrade)) {
            upgrades.add(upgrade);
        }
    }

    /**
     * @return A list of all upgrades applied to the station.
     */
    public List<Buildings> getUpgrades() {
        return new ArrayList<>(upgrades);
    }

    /**
     * Calculates the total cost of all applied upgrades.
     *
     * @return Total cost in game currency.
     */
    public int getTotalUpgradeCost() {
        return upgrades.stream().mapToInt(Buildings::getPrice).sum();
    }

    /**
     * Retrieves the list of all bonuses from applied upgrades.
     *
     * @return A list of bonus descriptions.
     */
    public List<String> getAllBonuses() {
        return upgrades.stream().map(Buildings::getBonus).toList();
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
        if (!(o instanceof Station)) return false;
        Station station = (Station) o;
        return reference.equals(station.reference);
    }

    /**
     * @return A hash code based on the reference.
     */
    @Override
    public int hashCode() {
        return Objects.hash(reference);
    }

    /**
     * Creates a deep copy of the station, including upgrades and assigned employee.
     *
     * @return A clone of this station.
     */
    public Station clone() {
        Station clone = new Station(this.reference, this.name, this.type, this.region);
        clone.employee = this.employee != null ? this.employee.clone() : null;
        clone.upgrades = new ArrayList<>(this.upgrades);
        return clone;
    }
}
