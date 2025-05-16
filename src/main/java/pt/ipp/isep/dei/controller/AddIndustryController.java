package pt.ipp.isep.dei.controller;

/**
 * Controller for adding industries to maps.
 */
public class AddIndustryController {
    private final UserService userService;
    private final MapRepository mapRepository;
    private final IndustryTypeRepository industryTypeRepository;
    private Map currentMap;

    public AddIndustryController(UserService userService, MapRepository mapRepository,
                                 IndustryTypeRepository industryTypeRepository) {
        this.userService = userService;
        this.mapRepository = mapRepository;
        this.industryTypeRepository = industryTypeRepository;
    }

    /**
     * Verifies if a user has editor permissions.
     * @param editor The user to check
     * @return true if user has permissions, false otherwise
     */
    public boolean verifyEditorPermission(Editor editor) {
        return userService.verifyEditorPermission(editor);
    }

    /**
     * Sets the current map for industry addition.
     * @param mapId The ID of the map to select
     * @return true if map was found and selected, false otherwise
     */
    public boolean selectMap(String mapId) {
        currentMap = mapRepository.getMapById(mapId);
        return currentMap != null;
    }

    /**
     * Validates industry parameters.
     * @param type The industry type
     * @param x The x-coordinate
     * @param y The y-coordinate
     * @return true if parameters are valid, false otherwise
     */
    public boolean validateIndustry(IndustryType type, int x, int y) {
        return industryTypeRepository.isValidIndustryType(type) &&
                currentMap != null &&
                currentMap.isPositionValid(x, y);
    }

    /**
     * Creates and adds an industry to the current map.
     * @param type The industry type
     * @param x The x-coordinate
     * @param y The y-coordinate
     * @return The created industry, or null if failed
     */
    public Industry createIndustry(IndustryType type, int x, int y) {
        if (!validateIndustry(type, x, y)) {
            return null;
        }

        Industry industry = new Industry(type, x, y);
        if (currentMap.addIndustry(industry) && mapRepository.saveMap(currentMap)) {
            return industry;
        }
        return null;
    }
}
