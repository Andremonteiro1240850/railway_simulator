package pt.ipp.isep.dei.controller;

package pt.ipp.isep.dei.controller;

import pt.ipp.isep.dei.domain.City;
import pt.ipp.isep.dei.domain.Map;
import pt.ipp.isep.dei.domain.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Controller to manage creation of City (US03).
 */
public class CreateCityController {
    private static final int DEFAULT_BLOCKS = 5;
    private static final double SIGMA = 2.0;

    private final Map map;
    private final Random random = new Random();

    /**
     * Constructs the controller with the map where cities will be added.
     * @param map the current map
     */
    public CreateCityController(Map map) {
        this.map = map;
    }

    /**
     * Validates city name: only letters and spaces
     */
    public boolean validateName(String name) {
        return name != null && name.matches("^[a-zA-Z ]+$");
    }

    /**
     * Validates that the position is within map bounds
     */
    public boolean validatePosition(int x, int y) {
        return x >= 0 && y >= 0 && x < map.getWidth() && y < map.getHeight();
    }

    /**
     * Creates a city with a manual number of house blocks
     */
    public boolean createCityManual(String name, int x, int y, int blocks) {
        if (!validateName(name) || !validatePosition(x, y) || blocks <= 0) return false;
        List<Position> blocksList = new ArrayList<>();
        for (int i = 0; i < blocks; i++) {
            int dx = (int) Math.round(random.nextGaussian() * SIGMA);
            int dy = (int) Math.round(random.nextGaussian() * SIGMA);
            blocksList.add(new Position(x + dx, y + dy));
        }
        City city = new City(name, new Position(x, y), blocksList);
        map.addCity(city);
        return true;
    }

    /**
     * Creates a city with a default number of house blocks
     */
    public boolean createCityAuto(String name, int x, int y) {
        return createCityManual(name, x, y, DEFAULT_BLOCKS);
    }
}
