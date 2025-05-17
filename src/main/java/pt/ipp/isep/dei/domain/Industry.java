package pt.ipp.isep.dei.domain;

import java.util.Objects;

public class Industry {
   private int positionX;
   private int positionY;
   private Map map;

    private Employee employee;

    public Industry(Map map, int positionX, int positionY, Employee employee) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.employee = employee;
        this.map = map;
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
        Industry task = (Industry) o;
        return  employee.equals(task.employee);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employee);
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
