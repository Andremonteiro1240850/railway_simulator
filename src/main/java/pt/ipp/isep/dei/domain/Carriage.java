package pt.ipp.isep.dei.domain;

public class Carriage {

    private String name;
    private String cargoType;
    private double capacity;
    private double cost;

    public Carriage(String name, String cargoType, double capacity, double cost) {
        this.name = name;
        this.cargoType = cargoType;
        this.capacity = capacity;
        this.cost = cost;
    }

    public String getName() { return name; }
    public String getCargoType() { return cargoType; }
    public double getCapacity() { return capacity; }
    public double getCost() {
        return cost;
    }

    public void setCapacity(double capacity) { this.capacity = capacity; }
    public void setName(String name) { this.name = name; }
    public void setCargoType(String cargoType) { this.cargoType = cargoType; }

    // Método copy adicionado
    public Carriage copy() {
        return new Carriage(name, cargoType, capacity, cost);
    }

    @Override
    public String toString() {
        return "Carriage [name=" + name + ", cargoType=" + cargoType + ", capacity=" + capacity + "]";
    }
}

