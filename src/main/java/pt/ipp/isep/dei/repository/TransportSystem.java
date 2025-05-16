package pt.ipp.isep.dei.repository;


/**
 * Represents the selection of a transport system for a scenario.
 */
public class TransportSystem {
    private String selectedType;

    /**
     * Selects a transport type based on user input option.
     * @param option 1 = steam, 2 = diesel, 3 = electric
     * @return true if selection is valid, false otherwise
     */
    public boolean select(int option) {
        return switch (option) {
            case 1 -> { selectedType = "steam"; yield true; }
            case 2 -> { selectedType = "diesel"; yield true; }
            case 3 -> { selectedType = "electric"; yield true; }
            default -> false;
        };
    }

    /** @return selected transport type */
    public String getSelectedType() {
        return selectedType;
    }
}
