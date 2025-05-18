package pt.ipp.isep.dei.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Map implements Cloneable {
    private String name;
    private int dimensionX;
    private int dimensionY;
    /**
     * Constructs a new Map with the specified characteristics.
     *
     * @param name       The unique name/identifier for the map
     * @param dimensionX The horizontal dimension (width) of the map
     * @param dimensionY The vertical dimension (height) of the map
     * @param employee   The employee associated with this map
     */

    private Employee employee;

    public Map(String name, int dimensionX, int dimensionY, Employee employee) {

        this.name = name;
        this.dimensionX = dimensionX;
        this.dimensionY = dimensionY;
        this.employee = employee;

    }
    /**
     * Creates and returns a shallow copy of this map using Object.clone().
     *
     * @return A cloned Map instance
     * @throws AssertionError if cloning is not supported (should never happen as class implements Cloneable)
     */
    @Override
    public Map clone() {
        try {
            return (Map) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(); // Nunca deve acontecer pois implementamos Cloneable
        }
    }
    /**
     * Validates that a reference string is not null or empty.
     *
     * @param reference The reference string to validate
     * @throws IllegalArgumentException if the reference is null or empty
     */

    private void validateReference(String reference) {
        //TODO: missing from the diagrams
        if (reference == null || reference.isEmpty()) {
            throw new IllegalArgumentException("Reference cannot be null or empty.");
        }
    }
    /**
     * Gets the name of the map.
     *
     * @return The map's name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the horizontal dimension (width) of the map.
     *
     * @return The X dimension
     */
    public int getDimensionX() {
        return dimensionX;
    }
    /**
     * Gets the vertical dimension (height) of the map.
     *
     * @return The Y dimension
     */

    public int getDimensionY() {
        return dimensionY;
    }
    /**
     * Compares this map to another object for equality.
     * Two maps are considered equal if they have the same name.
     *
     * @param o The object to compare with
     * @return true if the objects are equal (same name), false otherwise
     */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Map map = (Map) o;
        return Objects.equals(name, map.name); // assumindo que 'name' identifica unicamente o mapa
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    /**
     * Clone method.
     *
     * @return A clone of the current instance.
     */
    public  Map mapClone() {
        return new Map(this.name,this.dimensionX,this.dimensionY,this.employee);
    }

}

