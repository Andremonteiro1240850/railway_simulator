package pt.ipp.isep.dei.repository;

import pt.ipp.isep.dei.domain.Locomotive;

import java.util.ArrayList;
import java.util.List;

/**
 * Repository responsible for managing purchased locomotives.
 * Stores and retrieves locomotive instances relevant to the scenario.
 */
public class LocomotiveRepository {

    private final List<Locomotive> purchasedLocomotives = new ArrayList<>();

    /**
     * Adds a locomotive to the list of purchased locomotives.
     *
     * @param loco the locomotive to add
     */
    public void addPurchasedLocomotive(Locomotive loco) {
        purchasedLocomotives.add(loco);
    }

    /**
     * Returns a list of all locomotives that have been purchased.
     *
     * @return list of purchased locomotives
     */
    public List<Locomotive> getPurchasedLocomotives() {
        return purchasedLocomotives;
    }
}


