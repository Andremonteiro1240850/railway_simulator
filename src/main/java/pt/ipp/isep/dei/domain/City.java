package pt.ipp.isep.dei.domain;

import java.util.Objects;

public class City {
    private Map map;
    private int x;
    private int y;

    private Employee employee;
    /**
     * Constructs a new City with the specified map coordinates and employee.
     *
     * @param map      The map to which this city belongs
     * @param x        The x-coordinate of the city on the map
     * @param y        The y-coordinate of the city on the map
     * @param employee The employee associated with this city
     */

    public City(Map map, int x, int y, Employee employee) {

        this.map = map;
        this.x = x;
        this.y = y;
        this.employee = employee;
    }
    /**
     * Validates a reference string.
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
     * Compares this city to another object for equality.
     * Two cities are considered equal if they have the same employee.
     *
     * @param o The object to compare with
     * @return true if the objects are equal, false otherwise
     */

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
    /**
     * Returns a hash code value for the city based on the employee.
     *
     * @return A hash code value for this city
     */

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
