package pt.ipp.isep.dei.domain;

public class Locomotive {
    private final String reference;
    private final LocomotiveType type;
    private final int power;
    private final double acceleration;
    private final int startYear;
    private final double maintenanceCostPerYear;
    private final double purchasePrice;

    public Locomotive(String reference, LocomotiveType type, int power, double acceleration,
                      int startYear, double maintenanceCostPerYear, double purchasePrice) {
        this.reference = reference;
        this.type = type;
        this.power = power;
        this.acceleration = acceleration;
        this.startYear = startYear;
        this.maintenanceCostPerYear = maintenanceCostPerYear;
        this.purchasePrice = purchasePrice;
    }

    // Novo construtor para integração com o enum ListLocomotives
    public Locomotive(String name, String fuelType, int buildCost, int maintenanceCost, int fuelCost,
                      int topSpeed, String accelerationRating, String reliability, int availableYear, int retiredYear) {
        this.reference = name;
        this.type = LocomotiveType.fromString(fuelType);  // converte String para enum
        this.power = topSpeed * 10; // suposição simples para potência com base na velocidade
        this.acceleration = convertAcceleration(accelerationRating);
        this.startYear = availableYear;
        this.maintenanceCostPerYear = maintenanceCost;
        this.purchasePrice = buildCost;
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


