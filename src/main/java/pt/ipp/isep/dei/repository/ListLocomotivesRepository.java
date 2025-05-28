package pt.ipp.isep.dei.repository;

import pt.ipp.isep.dei.domain.Locomotive;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ListLocomotivesRepository {
    private final List<Locomotive> availableLocomotives;

    public ListLocomotivesRepository() {
        this.availableLocomotives = new ArrayList<>();
        initializeDefaultLocomotives();
    }

    private void initializeDefaultLocomotives() {
        // Locomotivas baseadas nos tipos mencionados no enunciado (vapor, diesel, elétrica)
        availableLocomotives.add(new Locomotive("L-001", "Steam", 4000, 100, 1850));
        availableLocomotives.add(new Locomotive("L-002", "Diesel", 5000, 120, 1920));
        availableLocomotives.add(new Locomotive("L-003", "Electric", 6000, 150, 1895));
        availableLocomotives.add(new Locomotive("L-004", "Steam", 4000, 100, 2000));
        availableLocomotives.add(new Locomotive("L-005", "Diesel", 5000, 120, 1950));
        availableLocomotives.add(new Locomotive("L-006", "Electric", 6000, 150, 2010));
    }

    public List<Locomotive> getAvailableLocomotives(int currentYear) {
        // Filtra locomotivas disponíveis no ano atual do cenário
        List<Locomotive> result = new ArrayList<>();
        for (Locomotive loco : availableLocomotives) {
            if (loco.getStartYear() <= currentYear) {
                result.add(loco.clone());
            }
        }
        return result;
    }

    public Optional<Locomotive> findByReference(String reference) {
        return availableLocomotives.stream()
                .filter(l -> l.getReference().equals(reference))
                .findFirst()
                .map(Locomotive::clone);
    }
}