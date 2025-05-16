package pt.ipp.isep.dei.ui.console.authorization;

import java.util.regex.Pattern;

public class MapValidator {

    private static final String NAME_REGEX = "^[a-zA-Z0-9_-]+$";

    private MapValidator() {}

    /**
     * Checks if the given name is valid.
     * @param name the map name to validate
     * @return true if name matches the required pattern; false otherwise
     */
    public static boolean isValidName(String name) {
        if (name == null || name.isEmpty()) {
            return false;
        }
        return Pattern.matches(NAME_REGEX, name);
    }

    /**
     * Checks if given dimensions are valid (positive integers).
     * @param width the width to validate
     * @param height the height to validate
     * @return true if both width and height are > 0; false otherwise
     */
    public static boolean isValidSize(int width, int height) {
        return width > 0 && height > 0;
    }
}
