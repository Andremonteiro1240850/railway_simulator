package pt.ipp.isep.dei.domain;

import java.io.Serializable;

/**
 * Represents a train carriage used to transport a specific type of cargo.
 * Each carriage has a name, cargo type, capacity, and cost.
 */
public class Carriage implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
    private String cargoType;
    private double capacity;
    private double cost;

    /**
     * Constructs a new Carriage instance with the given attributes.
     *
     * @param name      the name of the carriage
     * @param cargoType the type of cargo the carriage is designed to carry
     * @param capacity  the maximum cargo capacity of the carriage
     * @param cost      the cost associated with the carriage
     * @throws IllegalArgumentException if name or cargoType is null or empty, or if capacity or cost is negative
     */
    public Carriage(String name, String cargoType, double capacity, double cost) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        if (cargoType == null || cargoType.trim().isEmpty()) {
            throw new IllegalArgumentException("Cargo type cannot be null or empty");
        }
        if (capacity < 0) {
            throw new IllegalArgumentException("Capacity cannot be negative");
        }
        if (cost < 0) {
            throw new IllegalArgumentException("Cost cannot be negative");
        }
        this.name = name;
        this.cargoType = cargoType;
        this.capacity = capacity;
        this.cost = cost;
    }

    /**
     * Returns the name of the carriage.
     *
     * @return the carriage name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the type of cargo the carriage carries.
     *
     * @return the cargo type
     */
    public String getCargoType() {
        return cargoType;
    }

    /**
     * Returns the cargo capacity of the carriage.
     *
     * @return the capacity
     */
    public double getCapacity() {
        return capacity;
    }

    /**
     * Returns the cost of the carriage.
     *
     * @return the cost
     */
    public double getCost() {
        return cost;
    }

    /**
     * Sets the cargo capacity of the carriage.
     *
     * @param capacity the new capacity value
     * @throws IllegalArgumentException if capacity is negative
     */
    public void setCapacity(double capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException("Capacity cannot be negative");
        }
        this.capacity = capacity;
    }

    /**
     * Sets the name of the carriage.
     *
     * @param name the new name value
     * @throws IllegalArgumentException if name is null or empty
     */
    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        this.name = name;
    }

    /**
     * Sets the cargo type of the carriage.
     *
     * @param cargoType the new cargo type
     * @throws IllegalArgumentException if cargoType is null or empty
     */
    public void setCargoType(String cargoType) {
        if (cargoType == null || cargoType.trim().isEmpty()) {
            throw new IllegalArgumentException("Cargo type cannot be null or empty");
        }
        this.cargoType = cargoType;
    }

    /**
     * Creates and returns a copy of this carriage instance.
     *
     * @return a new Carriage object with the same attributes
     */
    public Carriage copy() {
        return new Carriage(name, cargoType, capacity, cost);
    }

    /**
     * Returns a string representation of the carriage, including name, cargo type, capacity and cost.
     *
     * @return a string describing the carriage
     */
    @Override
    public String toString() {
        return "Carriage [name=" + name + ", cargoType=" + cargoType + ", capacity=" + capacity + ", cost=" + cost + "]";
    }
}


