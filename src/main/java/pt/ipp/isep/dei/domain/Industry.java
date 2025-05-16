package pt.ipp.isep.dei.domain;

/**
 * Represents an industry placed on the map.
 */
public class Industry {
    private final IndustryType type;
    private final int x;
    private final int y;

    /**
     * Creates a new Industry instance.
     * @param type The type of industry
     * @param x The x-coordinate on the map
     * @param y The y-coordinate on the map
     */
    public Industry(IndustryType type, int x, int y) {
        this.type = type;
        this.x = x;
        this.y = y;
    }

    public IndustryType getType() {
        return type;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return type + " Industry at (" + x + "," + y + ")";
    }
}