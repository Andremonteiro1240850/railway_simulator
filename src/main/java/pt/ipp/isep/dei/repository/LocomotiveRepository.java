package pt.ipp.isep.dei.repository;

import pt.ipp.isep.dei.domain.Locomotive;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;



public class LocomotiveRepository {
    private final List<Locomotive> locomotives = new ArrayList<>();

    public LocomotiveRepository() {
        // Dummy data for testing
        locomotives.add(new Locomotive("Atlantic Express", LocomotiveType.STEAM, 3000, 0.5, 1900, 500, 1000));
        locomotives.add(new Locomotive("Diesel Master", LocomotiveType.DIESEL, 4000, 0.6, 1959, 600, 1500));
        locomotives.add(new Locomotive("Electric Arrow", LocomotiveType.ELECTRIC, 5000,0.7 , 1800, 700, 2000));
    }

    public List<Locomotive> availableLocomotivesForYear(int year) {
        return locomotives.stream()
                .filter(l -> l.getStartYear() <= year)
                .collect(Collectors.toList());
    }

    public Locomotive getByName(String name) {
        return locomotives.stream()
                .filter(l -> l.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }
}
