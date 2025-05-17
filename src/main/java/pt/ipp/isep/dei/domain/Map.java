package pt.ipp.isep.dei.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Map implements Cloneable {
    private String name;
    private int dimensionX;
    private int dimensionY;




    private Employee employee;

    public Map(String name, int dimensionX, int dimensionY, Employee employee) {

        this.name = name;
        this.dimensionX = dimensionX;
        this.dimensionY = dimensionY;
        this.employee = employee;

    }
    @Override
    public Map clone() {
        try {
            return (Map) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(); // Nunca deve acontecer pois implementamos Cloneable
        }
    }

    private void validateReference(String reference) {
        //TODO: missing from the diagrams
        if (reference == null || reference.isEmpty()) {
            throw new IllegalArgumentException("Reference cannot be null or empty.");
        }
    }
    public String getName() {
        return name;
    }

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

