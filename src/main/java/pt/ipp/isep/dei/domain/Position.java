package pt.ipp.isep.dei.domain;

/**
 * Represents a position (x, y) on the map.
 */
public class Position {
    private final int x;
    private final int y;

    /**
     * Constructs a position.
     * @param x horizontal coordinate (>= 0)
     * @param y vertical coordinate (>= 0)
     */
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() { return x; }
    public int getY() { return y; }
    @Override public String toString() { return "(" + x + ", " + y + ")"; }
}