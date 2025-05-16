package pt.ipp.isep.dei.repository;

/**
 * Represents the selection of a port behavior for a scenario.
 */
public class PortRepository {
    private String selectedBehavior;

    /**
     * Selects a port behavior based on user input option.
     * @param option 1 = import, 2 = export, 3 = transform
     * @return true if selection is valid, false otherwise
     */
    public boolean select(int option) {
        return switch (option) {
            case 1 -> { selectedBehavior = "import"; yield true; }
            case 2 -> { selectedBehavior = "export"; yield true; }
            case 3 -> { selectedBehavior = "transform"; yield true; }
            default -> false;
        };
    }

    /** @return selected port behavior */
    public String getSelectedBehavior() {
        return selectedBehavior;
    }
}