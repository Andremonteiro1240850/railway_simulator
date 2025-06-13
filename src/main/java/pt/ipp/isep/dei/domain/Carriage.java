package pt.ipp.isep.dei.domain;

/**
 * Represents a train carriage used to transport a specific type of cargo.
 * Each carriage has a name, cargo type, capacity, and cost.
 */
public class Carriage {

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
     */
    public Carriage(String name, String cargoType, double capacity, double cost) {
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
     */
    public void setCapacity(double capacity) {
        this.capacity = capacity;
    }

    /**
     * Sets the name of the carriage.
     *
     * @param name the new name value
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the cargo type of the carriage.
     *
     * @param cargoType the new cargo type
     */
    public void setCargoType(String cargoType) {
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
     * Returns a string representation of the carriage, including name, cargo type, and capacity.
     *
     * @return a string describing the carriage
     */
    @Override
    public String toString() {
        return "Carriage [name=" + name + ", cargoType=" + cargoType + ", capacity=" + capacity + "]";
    }
}


