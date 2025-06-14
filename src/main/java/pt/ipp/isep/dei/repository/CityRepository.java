package pt.ipp.isep.dei.repository;

import pt.ipp.isep.dei.domain.City;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Repository responsible for managing a collection of City objects.
 * Provides functionality to add, retrieve, and check existence of cities.
 */
public class CityRepository {

    private final List<City> cities = new ArrayList<>();

    /**
     * Attempts to add a new city to the repository.
     * A city is only added if it is not already present and can be cloned successfully.
     *
     * @param city the city to add
     * @return an Optional containing the cloned city if added successfully, or empty if not added
     */
    public Optional<City> add(City city) {
        if (city == null) {
            return Optional.empty();
        }

        if (!contains(city)) {
            try {
                City clonedCity = city.clone();
                cities.add(clonedCity);
                return Optional.of(clonedCity);
            } catch (Exception e) {
                System.err.println("Error cloning city: " + e.getMessage());
                return Optional.empty();
            }
        }
        return Optional.empty();
    }

    /**
     * Checks whether the repository already contains a given city.
     * Comparison is based on the equals method of the City class.
     *
     * @param city the city to check
     * @return true if the city already exists, false otherwise
     */
    public boolean contains(City city) {
        return cities.stream().anyMatch(m -> m.equals(city));
    }

    /**
     * Returns a defensive copy of all stored cities.
     *
     * @return a list of all cities in the repository
     */
    public List<City> getAll() {
        return new ArrayList<>(cities); // Defensive copy
    }
}

