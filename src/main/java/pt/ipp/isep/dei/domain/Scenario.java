package pt.ipp.isep.dei.domain;

/**
 * Represents the current simulation scenario.
 */
public class Scenario {
    private final int currentYear;

    public Scenario(int currentYear) {
        this.currentYear = currentYear;
    }

    public int getCurrentYear() {
        return currentYear;
    }
}
