package pt.ipp.isep.dei.domain;



public class Locomotive {
    private final String reference;
    private final LocomotiveType type;
    private final int power;
    private final double acceleration;
    private final int startYear;
    private final double maintenanceCostPerYear;
    private final double purchasePrice;

    public Locomotive(String reference, LocomotiveType type, int power, double acceleration, int startYear, double maintenanceCostPerYear, double purchasePrice) {

        this.reference = reference;
        this.type = type;
        this.power = power;
        this.acceleration = acceleration;
        this.startYear = startYear;
        this.maintenanceCostPerYear = maintenanceCostPerYear;
        this.purchasePrice = purchasePrice;
    }

    public String getName() { return reference; }
    public LocomotiveType getType() { return type; }
    public int getPower() { return power; }
    public double getAcceleration() { return acceleration; }
    public int getStartYear() { return startYear; }
    public double getMaintenanceCostPerYear() { return maintenanceCostPerYear; }
    public double getPurchasePrice() { return purchasePrice; }



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


