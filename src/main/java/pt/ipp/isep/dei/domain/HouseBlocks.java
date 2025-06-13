package pt.ipp.isep.dei.domain;

import java.util.List;
import java.util.Objects;

/**
 * Represents a collection of house blocks in a city.
 * House blocks are used to determine passenger and mail generation in cities.
 */
public class HouseBlocks {
    private int x;
    private int y;
    private List<int[]> houseBlocks;

    /**
     * Constructs a HouseBlocks object.
     *
     * @param x           the x-coordinate of the reference point
     * @param y           the y-coordinate of the reference point
     * @param houseBlocks list of [x,y] coordinates representing house blocks
     * @throws IllegalArgumentException if houseBlocks is null or empty
     */
    public HouseBlocks(int x, int y, List<int[]> houseBlocks) {
        if (houseBlocks == null || houseBlocks.isEmpty()) {
            throw new IllegalArgumentException("House blocks cannot be empty");
        }

        this.x = x;
        this.y = y;
        this.houseBlocks = houseBlocks;
    }

    /**
     * Gets the x-coordinate of the reference point.
     *
     * @return the x-coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * Sets the x-coordinate of the reference point.
     *
     * @param x the new x-coordinate
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Gets the y-coordinate of the reference point.
     *
     * @return the y-coordinate
     */
    public int getY() {
        return y;
    }

    /**
     * Sets the y-coordinate of the reference point.
     *
     * @param y the new y-coordinate
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Gets the list of house block coordinates.
     *
     * @return an unmodifiable list of house block coordinates
     */
    public List<int[]> getHouseBlocks() {
        return List.copyOf(houseBlocks);
    }

    /**
     * Sets a new list of house block coordinates.
     *
     * @param houseBlocks the new list of house blocks
     * @throws IllegalArgumentException if the list is null or empty
     */
    public void setHouseBlocks(List<int[]> houseBlocks) {
        if (houseBlocks == null || houseBlocks.isEmpty()) {
            throw new IllegalArgumentException("House blocks cannot be empty");
        }
        this.houseBlocks = houseBlocks;
    }

    /**
     * Checks if a specific block is contained in these house blocks.
     *
     * @param blockX the x-coordinate of the block to check
     * @param blockY the y-coordinate of the block to check
     * @return true if the block is found, false otherwise
     */
    public boolean containsBlock(int blockX, int blockY) {
        for (int[] block : houseBlocks) {
            if (block[0] == blockX && block[1] == blockY) {
                return true;
            }
        }
        return false;
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     *
     * @param o the reference object with which to compare
     * @return true if this object is the same as the obj argument
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HouseBlocks)) return false;
        HouseBlocks that = (HouseBlocks) o;
        return x == that.x &&
                y == that.y &&
                houseBlocks.equals(that.houseBlocks);
    }

    /**
     * Returns a hash code value for the object.
     *
     * @return a hash code value
     */
    @Override
    public int hashCode() {
        return Objects.hash(x, y, houseBlocks);
    }

    /**
     * Returns a string representation of the object.
     *
     * @return a string representing this HouseBlocks instance
     */
    @Override
    public String toString() {
        return String.format("HouseBlocks{x=%d, y=%d, blocks=%d}",
                x, y, houseBlocks.size());
    }
}
