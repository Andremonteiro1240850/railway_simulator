package pt.ipp.isep.dei.repository;

import pt.ipp.isep.dei.domain.Locomotive;

import java.util.ArrayList;
import java.util.List;

/**
 * Repository class for managing locomotives in the system.
 */
public class LocomotiveRepository {
    private final List<Locomotive> purchasedLocomotives;

    /**
     * Constructs a new LocomotiveRepository.
     */
    public LocomotiveRepository() {
        this.purchasedLocomotives = new ArrayList<>();
    }

    /**
     * Adds a purchased locomotive to the repository.
     *
     * @param locomotive the locomotive to add
     * @throws NullPointerException if locomotive is null
     */
    public void addPurchasedLocomotive(Locomotive locomotive) {
        if (locomotive == null) {
            throw new NullPointerException("Locomotive cannot be null");
        }
        purchasedLocomotives.add(locomotive);
    }

    /**
     * Returns a list of all purchased locomotives.
     *
     * @return a list of purchased locomotives
     */
    public List<Locomotive> getPurchasedLocomotives() {
        return new ArrayList<>(purchasedLocomotives);
    }
}

