package pt.ipp.isep.dei.domain;

import pt.ipp.isep.dei.domain.enums.IndustryType;
import java.util.Objects;
import java.io.Serializable;

/**
 * Represents an industry on the map, with a specific type and coordinates.
 * Industries can be of various types and are located at a specific position on a given map.
 */
public class Industry implements Serializable {
    private static final long serialVersionUID = 1L;
    private final IndustryType type;
    private final int positionX;
    private final int positionY;
    private final Map map;

    /**
     * Constructs an Industry with the given type, map and position.
     *
     * @param type the type of the industry
     * @param map  the map where the industry is placed
     * @param x    the x-coordinate of the industry's position
     * @param y    the y-coordinate of the industry's position
     */
    public Industry(IndustryType type, Map map, int x, int y) {
        if (type == null) {
            throw new IllegalArgumentException("Industry type cannot be null");
        }
        if (map == null) {
            throw new IllegalArgumentException("Map cannot be null");
        }
        // Assumes validation is already handled inside Map
        this.type = type;
        this.map = map;
        this.positionX = x;
        this.positionY = y;
    }

    /**
     * Returns the type of this industry.
     *
     * @return the industry type
     */
    public IndustryType getType() {
        return type;
    }

    /**
     * Returns the x-coordinate of the industry position.
     *
     * @return the x position
     */
    public int getPositionX() {
        return positionX;
    }

    /**
     * Returns the y-coordinate of the industry position.
     *
     * @return the y position
     */
    public int getPositionY() {
        return positionY;
    }

    /**
     * Returns the map where this industry is located.
     *
     * @return the map reference
     */
    public Map getMap() {
        return map;
    }

    /**
     * Compares this industry to another object for equality.
     *
     * @param o the object to compare
     * @return true if they are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Industry)) return false;
        Industry industry = (Industry) o;
        return positionX == industry.positionX &&
                positionY == industry.positionY &&
                type == industry.type &&
                map.equals(industry.map);
    }

    /**
     * Returns the hash code of this industry.
     *
     * @return the hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(type, positionX, positionY, map);
    }

    /**
     * Returns a string representation of the industry, including type and coordinates.
     *
     * @return a string describing the industry
     */
    @Override
    public String toString() {
        return "Industry{" +
                "type=" + type +
                ", positionX=" + positionX +
                ", positionY=" + positionY +
                '}';
    }
}

