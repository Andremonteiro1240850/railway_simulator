package pt.ipp.isep.dei.domain;

import pt.ipp.isep.dei.domain.enums.IndustryType;
import java.util.Objects;

public class Industry {
    private final IndustryType type;
    private final int positionX;
    private final int positionY;
    private final Map map;

    public Industry(IndustryType type, Map map, int x, int y) {
        // Validação já está implementada em Map
        this.type = type;
        this.map = map;
        this.positionX = x;
        this.positionY = y;
    }

    // Getters
    public IndustryType getType() { return type; }
    public int getPositionX() { return positionX; }
    public int getPositionY() { return positionY; }

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

    @Override
    public int hashCode() {
        return Objects.hash(type, positionX, positionY, map);
    }
}
