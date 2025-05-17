package pt.ipp.isep.dei.domain;

public class Locomotive {
    private final String reference;
    private final String type; // Steam, Diesel, Electric
    private final double price;
    private final int power; // em kW
    private final int startYear; // ano de início de operação

    public Locomotive(String reference, String type, double price, int power, int startYear) {
        this.reference = reference;
        this.type = type;
        this.price = price;
        this.power = power;
        this.startYear = startYear;
    }

    // Getters
    public String getReference() { return reference; }
    public String getType() { return type; }
    public double getPrice() { return price; }
    public int getPower() { return power; }
    public int getStartYear() { return startYear; }

    @Override
    public String toString() {
        return String.format("%s - %s (Power: %dkW, Price: %.2f, Year: %d)",
                reference, type, power, price, startYear);
    }

    public Locomotive clone() {
        return new Locomotive(reference, type, price, power, startYear);
    }
}