package pt.ipp.isep.dei.domain;

import java.util.Objects;

public class Map {
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

    private void validateReference(String reference) {
        //TODO: missing from the diagrams
        if (reference == null || reference.isEmpty()) {
            throw new IllegalArgumentException("Reference cannot be null or empty.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Map)) {
            return false;
        }
        Map task = (Map) o;
        return employee.equals(task.employee);
    }

    @Override
    public int hashCode() {
        return Objects.hash( employee);
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

