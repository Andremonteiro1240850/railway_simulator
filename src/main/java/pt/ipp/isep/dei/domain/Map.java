package pt.ipp.isep.dei.domain;

/**
 * Represents a Map with a name and dimensions (width and height).
 */
public class Map {
    private String name;
    private int width;
    private int height;

    /**
     * Constructs a new Map.
     * @param name the name of the map
     * @param width the width of the map (must be positive)
     * @param height the height of the map (must be positive)
     */
    public Map(String name, int width, int height) {
        this.name = name;
        this.width = width;
        this.height = height;
    }

    /**
     * @return the map name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the map width
     */
    public int getWidth() {
        return width;
    }

    /**
     * @return the map height
     */
    public int getHeight() {
        return height;
    }
}
