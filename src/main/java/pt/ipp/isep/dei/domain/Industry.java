package pt.ipp.isep.dei.domain;

import pt.ipp.isep.dei.domain.enums.IndustryType;
import java.util.Objects;

public class Industry {
    private final IndustryType type;
    private final int positionX;
    private final int positionY;
    private final Map map;

    public Industry(IndustryType type, Map map, int x, int y) {
        validate(type, map, x, y);
        this.type = type;
        this.map = map;
        this.positionX = x;
        this.positionY = y;
    }

    private void validate(IndustryType type, Map map, int x, int y) {
        if (type == null) throw new IllegalArgumentException("Invalid Industry Type");
        if (map == null) throw new IllegalArgumentException("Map can't be null");
        if (x < 0 || x >= map.getWidth() || y < 0 || y >= map.getHeight())
            throw new IllegalArgumentException("Position out of bounds");
        if (map.hasIndustryAt(x, y))
            throw new IllegalArgumentException("Position is already occupied");
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