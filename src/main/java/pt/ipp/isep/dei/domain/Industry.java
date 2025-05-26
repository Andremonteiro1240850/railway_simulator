package pt.ipp.isep.dei.domain;

import java.util.Objects;

public class Industry {
   private int positionX;
   private int positionY;
   private Map map;

    private Employee employee;
    /**
     * Constructs a new Industry with the specified map coordinates and employee.
     *
     * @param map       The map to which this industry belongs
     * @param positionX The x-coordinate of the industry on the map
     * @param positionY The y-coordinate of the industry on the map
     * @param employee  The employee associated with this industry
     */

    public Industry(Map map, int positionX, int positionY, Employee employee) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.employee = employee;
        this.map = map;
    }
    /**
     * Validates a reference string.
     * This method checks if the reference is not null and not empty.
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
     * Compares this industry to another object for equality.
     * Two industries are considered equal if they have the same employee.
     *
     * @param o The object to compare with
     * @return true if the objects are equal (same employee), false otherwise
     */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Industry)) return false;
        Industry industry = (Industry) o;
        return positionX == industry.positionX &&
                positionY == industry.positionY &&
                map.equals(industry.map);
    }

    @Override
    public int hashCode() {
        return Objects.hash(map, positionX, positionY);
    }


    /**
     * Clone method.
     *
     * @return A clone of the current instance.
     */
    public Industry clone() {
        return new Industry(this.map,this.positionX,positionY, this.employee);
    }
}
