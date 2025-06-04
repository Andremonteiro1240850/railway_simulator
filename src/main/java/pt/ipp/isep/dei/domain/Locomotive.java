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
    private final int retiredYear;



    // Novo construtor para integração com o enum ListLocomotives
    public Locomotive(String name, String fuelType, int buildCost, int maintenanceCost,
                      int topSpeed, String accelerationRating, int availableYear, int retiredYear) {
        this.reference = name;
        this.type = LocomotiveType.valueOf(fuelType);// converte String para enum
        this.purchasePrice = buildCost;
        this.maintenanceCostPerYear = maintenanceCost;
        this.power = topSpeed * 10; // suposição simples para potência com base na velocidade
        this.acceleration = convertAcceleration(accelerationRating);
        this.startYear = availableYear;
        this.retiredYear = retiredYear;


    }
    public Locomotive copy() {
        String fuelType = type.name();
        int buildCost = (int) purchasePrice;
        int maintenanceCost = (int) maintenanceCostPerYear;
        int topSpeed = power / 10;

        // Reverter aceleração para rating aproximado
        String accelerationRating;
        if (acceleration >= 3.5) {
            accelerationRating = "Very Fast";
        } else if (acceleration >= 3.0) {
            accelerationRating = "Above Average";
        } else if (acceleration >= 2.5) {
            accelerationRating = "Average";
        } else if (acceleration >= 2.0) {
            accelerationRating = "Below Average";
        } else {
            accelerationRating = "Poor";
        }

        return new Locomotive(reference, fuelType, buildCost, maintenanceCost, topSpeed, accelerationRating, startYear, retiredYear);
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

    public String getName() { return reference; }
    public LocomotiveType getType() { return type; }
    public int getPower() { return power; }
    public double getAcceleration() { return acceleration; }
    public int getStartYear() { return startYear; }
    public double getMaintenanceCostPerYear() { return maintenanceCostPerYear; }
    public double getPurchasePrice() { return purchasePrice; }


    @Override
    public String toString() {
        return String.format(
                "Locomotive [Reference: %s, Type: %s, Power: %d kW, Acceleration: %.2f m/s², Start Year: %d, Maintenance Cost/Year: %.2f €, Purchase Price: %.2f €]",
                reference,
                type,
                power,
                acceleration,
                startYear,
                maintenanceCostPerYear,
                purchasePrice
        );
    }
}


