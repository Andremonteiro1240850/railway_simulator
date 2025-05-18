package pt.ipp.isep.dei.domain;

public class Locomotive {
    private final String reference;
    private final String type; // Steam, Diesel, Electric
    private final double price;
    private final int power; // em kW
    private final int startYear;// ano de início de operação

    /**
     * Constructs a new Locomotive with the specified characteristics.
     *
     * @param reference The unique reference/identifier for the locomotive
     * @param type      The type of locomotive (Steam, Diesel, Electric)
     * @param price     The price of the locomotive
     * @param power     The power output of the locomotive in kilowatts (kW)
     * @param startYear The year when the locomotive began operation
     */

    public Locomotive(String reference, String type, double price, int power, int startYear) {
        this.reference = reference;
        this.type = type;
        this.price = price;
        this.power = power;
        this.startYear = startYear;
    }
    /**
     * Gets the locomotive's reference identifier.
     *
     * @return The reference string
     */

    // Getters
    public String getReference() { return reference; }
    /**
     * Gets the type of locomotive.
     *
     * @return The locomotive type (Steam, Diesel, Electric)
     */
    public String getType() { return type; }
    /**
     * Gets the price of the locomotive.
     *
     * @return The price as a double value
     */
    public double getPrice() { return price; }

    /**
     * Gets the power output of the locomotive.
     *
     * @return The power in kilowatts (kW)
     */
    public int getPower() { return power; }
    /**
     * Gets the year when the locomotive began operation.
     *
     * @return The starting year
     */
    public int getStartYear() { return startYear; }

    /**
     * Returns a string representation of the locomotive.
     *
     * @return Formatted string containing locomotive details
     */

    @Override
    public String toString() {
        return String.format("%s - %s (Power: %dkW, Price: %.2f, Year: %d)",
                reference, type, power, price, startYear);
    }
    /**
     * Creates and returns a copy of this locomotive.
     *
     * @return A new Locomotive instance with identical properties
     */

    public Locomotive clone() {
        return new Locomotive(reference, type, price, power, startYear);
    }
}