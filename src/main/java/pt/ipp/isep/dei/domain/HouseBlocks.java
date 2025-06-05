
package pt.ipp.isep.dei.domain;

import java.util.List;
import java.util.Objects;

/**
 * Represents a collection of house blocks in a city.
 * House blocks are used to determine passenger and mail generation in cities.
 */
public class HouseBlocks {
    private final int x;
    private final int y;
    private final List<int[]> houseBlocks;

    /**
     * Constructs a HouseBlocks object.
     *
     * @param x           the x-coordinate of the reference point
     * @param y           the y-coordinate of the reference point
     * @param houseBlocks list of [x,y] coordinates representing house blocks
     * @throws IllegalArgumentException if map is null or houseBlocks is null/empty
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
     * Gets the y-coordinate of the reference point.
     *
     * @return the y-coordinate
     */
    public int getY() {
        return y;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HouseBlocks)) return false;
        HouseBlocks that = (HouseBlocks) o;
        return x == that.x &&
                y == that.y &&
                houseBlocks.equals(that.houseBlocks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, houseBlocks);
    }

    @Override
    public String toString() {
        return String.format("HouseBlocks{x=%d, y=%d, blocks=%d}",
                 x, y, houseBlocks.size());
    }
}