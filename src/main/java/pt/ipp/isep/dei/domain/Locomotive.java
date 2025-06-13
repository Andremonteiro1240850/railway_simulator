package pt.ipp.isep.dei.domain;

import pt.ipp.isep.dei.domain.enums.LocomotiveType;

public class Locomotive {
    private final String reference;
    private final LocomotiveType type;
    private final int power;
    private final double acceleration;
    private final int startYear;
    private final double maintenanceCostPerYear;
    private final double purchasePrice;
    private final double fuelCost;

    // Construtor principal para uso geral
    public Locomotive(String reference, LocomotiveType type, double purchasePrice, double maintenanceCostPerYear,
                      int power, double acceleration, int startYear, double fuelCost) {
        this.reference = reference;
        this.type = type;
        this.purchasePrice = purchasePrice;
        this.maintenanceCostPerYear = maintenanceCostPerYear;
        this.power = power;
        this.acceleration = acceleration;
        this.startYear = startYear;
        this.fuelCost = fuelCost;
    }

    // Construtor usado para integração com enums ou ficheiros externos
    public Locomotive(String name, String fuelType, int buildCost, int maintenanceCost,
                      int topSpeed, String accelerationRating, int availableYear) {
        this.reference = name;
        this.type = LocomotiveType.fromString(fuelType);
        this.purchasePrice = buildCost;
        this.maintenanceCostPerYear = maintenanceCost;
        this.power = topSpeed * 10;
        this.acceleration = convertAcceleration(accelerationRating);
        this.startYear = availableYear;
        this.fuelCost = 0.0; // Assuming no fuel cost for the new constructor
    }

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

    public String getReference() {
        return reference;
    }

    public LocomotiveType getType() {
        return type;
    }

    public int getPower() {
        return power;
    }

    public double getAcceleration() {
        return acceleration;
    }

    public int getStartYear() {
        return startYear;
    }

    public double getMaintenanceCostPerYear() {
        return maintenanceCostPerYear;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public double getFuelCost() {
        return fuelCost;
    }

    // Método copy atualizado
    public Locomotive copy() {
        return new Locomotive(reference, type, purchasePrice, maintenanceCostPerYear, power, acceleration, startYear, fuelCost);
    }

    @Override
    public String toString() {
        return reference + " - Type: " + type + ", Power: " + power + ", Year: " + startYear;
    }
}
