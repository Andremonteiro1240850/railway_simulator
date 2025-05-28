package pt.ipp.isep.dei.domain;

/**
 * Validates map data such as name, dimensions, and scale.
 */
public class MapValidator {

    /**
     * Validates a map name to ensure it is not null or contains invalid characters.
     *
     * @param name the map name to validate
     * @return true if valid, false otherwise
     */
    public boolean isValidName(String name) {
        return name != null && name.matches("^[a-zA-Z0-9 _.-]+$");
    }

    /**
     * Validates map size dimensions.
     *
     * @param width horizontal size
     * @param height vertical size
     * @return true if both dimensions are positive
     */
    public boolean isValidSize(int width, int height) {
        return width > 0 && height > 0;
    }

    /**
     * Validates if the scale is non-null.
     *
     * @param scale the scale unit
     * @return true if scale is valid
     */
    public boolean isValidScale(Enum<?> scale) {
        return scale != null;
    }
}
