package pt.ipp.isep.dei.domain;

import java.util.List;

public class City {
    private final String name;
    private final Position position;
    private final List<Position> houseBlocks;

    /**
     * Constructs a City.
     * @param name city name
     * @param position city tag position
     * @param houseBlocks list of house block positions
     */
    public City(String name, Position position, List<Position> houseBlocks) {
        this.name = name;
        this.position = position;
        this.houseBlocks = houseBlocks;
    }

    public String getName() { return name; }
    public Position getPosition() { return position; }
    public List<Position> getHouseBlocks() { return houseBlocks; }
}