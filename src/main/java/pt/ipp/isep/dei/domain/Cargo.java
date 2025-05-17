package pt.ipp.isep.dei.domain;

/**
 * Represents a cargo item that can be either demanded or supplied by a station
 */
public class Cargo {
    private final String type;
    private final int amount;
    private final boolean isDemand; // true for demand, false for supply
    private final int pricePerUnit; // in currency units

    public Cargo(String type, int amount, boolean isDemand, int pricePerUnit) {
        if (type == null || type.trim().isEmpty()) {
            throw new IllegalArgumentException("Cargo type cannot be null or empty");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Cargo amount must be positive");
        }
        if (pricePerUnit <= 0) {
            throw new IllegalArgumentException("Price per unit must be positive");
        }

        this.type = type;
        this.amount = amount;
        this.isDemand = isDemand;
        this.pricePerUnit = pricePerUnit;
    }

    // Getters
    public String getType() {
        return type;
    }

    public int getAmount() {
        return amount;
    }

    public boolean isDemand() {
        return isDemand;
    }

    public int getPricePerUnit() {
        return pricePerUnit;
    }

    public int getTotalValue() {
        return amount * pricePerUnit;
    }

    @Override
    public String toString() {
        return String.format("%s: %s %d units @ %d/unit (Total: %d)",
                type,
                isDemand ? "Demand" : "Supply",
                amount,
                pricePerUnit,
                getTotalValue());
    }
}