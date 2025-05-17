package pt.ipp.isep.dei.repository;

import pt.ipp.isep.dei.domain.City;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CityRepository {
    private final List<City> cities = new ArrayList<>();

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

    public boolean contains(City city) {
        return cities.stream().anyMatch(m -> m.equals(city));
    }

    public List<City> getAll() {
        return new ArrayList<>(cities); // Retorna cópia defensiva
    }
}

