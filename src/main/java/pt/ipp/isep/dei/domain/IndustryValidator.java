package pt.ipp.isep.dei.domain;

public class IndustryValidator {
    public boolean isValidPosition(Map map, int x, int y) {
        return x >= 0 && x < map.getWidth() &&
                y >= 0 && y < map.getHeight() &&
                !map.hasIndustryAt(x, y);
    }
}