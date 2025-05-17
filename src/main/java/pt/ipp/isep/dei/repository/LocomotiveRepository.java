package pt.ipp.isep.dei.repository;

import pt.ipp.isep.dei.domain.Locomotive;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LocomotiveRepository {
    private final List<Locomotive> purchasedLocomotives = new ArrayList<>();

    public Optional<Locomotive> add(Locomotive locomotive) {
        if (locomotive == null) return Optional.empty();

        Locomotive clone = locomotive.clone();
        purchasedLocomotives.add(clone);
        return Optional.of(clone);
    }

    public List<Locomotive> getAll() {
        List<Locomotive> result = new ArrayList<>();
        for (Locomotive loco : purchasedLocomotives) {
            result.add(loco.clone());
        }
        return result;
    }

    public Optional<Locomotive> findByReference(String reference) {
        return purchasedLocomotives.stream()
                .filter(l -> l.getReference().equals(reference))
                .findFirst()
                .map(Locomotive::clone);
    }
}