package pt.ipp.isep.dei.controller;


import pt.ipp.isep.dei.domain.Map;
import pt.ipp.isep.dei.repository.MapRepository;
import pt.ipp.isep.dei.ui.console.authorization.MapValidator;

/**
 * Controller handling map creation logic.
 */
public class CreateMapController {

    private final MapRepository repository;

    /**
     * Constructs a new CreateMapController.
     */
    public CreateMapController() {
        this.repository = new MapRepository();
    }

    /**
     * Validates the map name using MapValidator.
     * @param name the name to validate
     * @return true if valid; false otherwise
     */
    public boolean validateName(String name) {
        return MapValidator.isValidName(name);
    }

    /**
     * Validates the map dimensions.
     * @param width the width to validate
     * @param height the height to validate
     * @return true if dimensions are valid; false otherwise
     */
    public boolean validateSize(int width, int height) {
        return MapValidator.isValidSize(width, height);
    }

    /**
     * Creates and stores a map if valid.
     * @param name the map name
     * @param width the map width
     * @param height the map height
     * @return true if creation and storage succeed; false otherwise
     */
    public boolean createMap(String name, int width, int height) {
        if (!validateName(name) || !validateSize(width, height)) {
            return false;
        }
        Map map = new Map(name, width, height);
        return repository.save(map);
    }
}
