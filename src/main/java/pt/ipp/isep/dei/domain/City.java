package pt.ipp.isep.dei.domain;

import java.util.Objects;

public class City {
    private Map map;
    private int x;
    private int y;

    private Employee employee;

    public City(Map map, int x, int y, Employee employee) {

        this.map = map;
        this.x = x;
        this.y = y;
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
        if (!(o instanceof Task)) {
            return false;
        }
        City task = (City) o;
        return  employee.equals(task.employee);
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
    public City clone() {
        return new City(this.map,this.x, this.y, this.employee);
    }
}
