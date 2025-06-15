package pt.ipp.isep.dei.domain;

import java.io.Serializable;
import pt.ipp.isep.dei.domain.enums.LocomotiveType;

/**
 * Represents a locomotive with technical and economic attributes,
 * such as type, power, acceleration, start year, and costs.
 */
public class Locomotive implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String reference;
    private final TypeLocomotive type;
    private final int power;
    private final double acceleration;
    private final int startYear;
    private final double maintenanceCostPerYear;
    private final double purchasePrice;
    private final double fuelCost;

    /**
     * Constructs a Locomotive with full detailed attributes.
     *
     * @param reference              unique reference or name of the locomotive
     * @param type                   type of the locomotive (e.g., steam, diesel, electric)
     * @param purchasePrice          cost to purchase the locomotive
     * @param maintenanceCostPerYear yearly maintenance cost
     * @param power                  locomotive power (e.g., max speed * 10)
     * @param acceleration           acceleration value (e.g., between 1.5 and 3.5)
     * @param startYear              year the locomotive becomes available
     * @param fuelCost               cost of fuel per usage/km
     */
    public Locomotive(String reference, LocomotiveType type, double purchasePrice, double maintenanceCostPerYear,
                      int power, double acceleration, int startYear, double fuelCost) {
        if (reference == null) {
            throw new IllegalArgumentException("Reference cannot be null");
        }
        if (reference.trim().isEmpty()) {
            throw new IllegalArgumentException("Reference cannot be empty");
        }
        if (type == null) {
            throw new IllegalArgumentException("Type cannot be null");
        }
        if (purchasePrice < 0) {
            throw new IllegalArgumentException("Purchase price cannot be negative");
        }
        if (maintenanceCostPerYear < 0) {
            throw new IllegalArgumentException("Maintenance cost cannot be negative");
        }
        if (power < 0) {
            throw new IllegalArgumentException("Power cannot be negative");
        }
        if (acceleration < 0) {
            throw new IllegalArgumentException("Acceleration cannot be negative");
        }
        if (startYear < 0) {
            throw new IllegalArgumentException("Start year cannot be negative");
        }
        if (fuelCost < 0) {
            throw new IllegalArgumentException("Fuel cost cannot be negative");
        }
        
        this.reference = reference;
        this.type = TypeLocomotive.fromLocomotiveType(type);
        this.purchasePrice = purchasePrice;
        this.maintenanceCostPerYear = maintenanceCostPerYear;
        this.power = power;
        this.acceleration = acceleration;
        this.startYear = startYear;
        this.fuelCost = fuelCost;
    }

    /**
     * Constructs a Locomotive from simplified raw data (e.g., from CSV or enum integration).
     *
     * @param name              the name or reference of the locomotive
     * @param fuelType          string representing the fuel type
     * @param buildCost         the purchase price of the locomotive
     * @param maintenanceCost   the yearly maintenance cost
     * @param topSpeed          the top speed of the locomotive
     * @param accelerationRating a textual description of acceleration performance
     * @param availableYear     the year when the locomotive becomes available
     */
    public Locomotive(String name, String fuelType, int buildCost, int maintenanceCost,
                      int topSpeed, String accelerationRating, int availableYear) {
        this.reference = name;
        this.type = TypeLocomotive.fromLocomotiveType(LocomotiveType.fromString(fuelType));
        this.purchasePrice = buildCost;
        this.maintenanceCostPerYear = maintenanceCost;
        this.power = topSpeed * 10;
        this.acceleration = convertAcceleration(accelerationRating);
        this.startYear = availableYear;
        this.fuelCost = 0.0; // Assuming no fuel cost for the new constructor
    }

    /**
     * Converts a textual acceleration rating into a numerical value.
     *
     * @param rating the acceleration rating (e.g., "very fast", "average")
     * @return numerical acceleration value
     */
    private double convertAcceleration(String rating) {
        return switch (rating.toLowerCase()) {
            case "very fast" -> 3.5;
            case "above average" -> 3.0;
            case "average" -> 2.5;
            case "below average" -> 2.0;
            case "poor" -> 1.5;
            default -> 2.0;
        };
    }

    /**
     * Returns the reference (name or ID) of the locomotive.
     *
     * @return the locomotive reference
     */
    public String getReference() {
        return reference;
    }

    /**
     * Returns the type of the locomotive (e.g., steam, diesel).
     *
     * @return the locomotive type
     */
    public LocomotiveType getType() {
        return type.getType();
    }

    /**
     * Returns the power value of the locomotive.
     *
     * @return the power
     */
    public int getPower() {
        return power;
    }

    /**
     * Returns the acceleration value of the locomotive.
     *
     * @return the acceleration
     */
    public double getAcceleration() {
        return acceleration;
    }

    /**
     * Returns the year the locomotive becomes available.
     *
     * @return the start year
     */
    public int getStartYear() {
        return startYear;
    }

    /**
     * Returns the annual maintenance cost.
     *
     * @return the maintenance cost per year
     */
    public double getMaintenanceCostPerYear() {
        return maintenanceCostPerYear;
    }

    /**
     * Returns the purchase price of the locomotive.
     *
     * @return the purchase price
     */
    public double getPurchasePrice() {
        return purchasePrice;
    }

    /**
     * Returns the cost of fuel for this locomotive.
     *
     * @return the fuel cost
     */
    public double getFuelCost() {
        return fuelCost;
    }

    /**
     * Returns the main cost of the locomotive, which is the purchase price.
     *
     * @return the cost
     */
    public double getCost() {
        return getPurchasePrice();
    }

    /**
     * Creates and returns a copy of this Locomotive instance.
     *
     * @return a cloned locomotive with identical attributes
     */
    public Locomotive copy() {
        return new Locomotive(reference, type.getType(), purchasePrice, maintenanceCostPerYear, power, acceleration, startYear, fuelCost);
    }

    /**
     * Returns a string representation of the locomotive, including reference, type, power, and year.
     *
     * @return a string describing the locomotive
     */
    @Override
    public String toString() {
        return reference + " - Type: " + type + ", Power: " + power + ", Year: " + startYear;
    }
}
