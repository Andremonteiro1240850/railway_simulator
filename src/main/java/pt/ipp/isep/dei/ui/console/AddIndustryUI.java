package pt.ipp.isep.dei.ui.console;

import pt.ipp.isep.dei.domain.Industry;
import pt.ipp.isep.dei.domain.IndustryType;

/**
 * User interface for adding industries to maps.
 */
public class AddIndustryUI {
    private final AddIndustryController controller;

    public AddIndustryUI(AddIndustryController controller) {
        this.controller = controller;
    }

    /**
     * Starts the industry addition process.
     */
    public void startAddIndustry() {
        System.out.println("Starting industry addition process...");
    }

    /**
     * Requests the user to select a map.
     */
    public void requestMapSelection() {
        System.out.println("Please select a map from the available options:");
        // In a real implementation, this would show a list of maps
    }

    /**
     * Requests position and industry type from the user.
     * @param x The x-coordinate
     * @param y The y-coordinate
     * @param type The industry type
     */
    public void inputPositionAndType(int x, int y, IndustryType type) {
        System.out.printf("Adding %s industry at position (%d, %d)%n", type, x, y);
    }

    /**
     * Displays a success message with industry details.
     * @param industry The successfully added industry
     */
    public void showSuccess(Industry industry) {
        System.out.println("SUCCESS: Added " + industry);
    }

    /**
     * Displays an error message.
     * @param message The error message
     */
    public void showError(String message) {
        System.out.println("ERROR: " + message);
    }


}


