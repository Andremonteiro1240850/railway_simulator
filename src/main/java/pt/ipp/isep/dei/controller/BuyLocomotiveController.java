package pt.ipp.isep.dei.controller;

import pt.ipp.isep.dei.domain.BuyLocomotiveService;
import pt.ipp.isep.dei.domain.Locomotive;

import java.util.List;

/**
 * Controller responsible for coordinating the process of buying a locomotive.
 */
public class BuyLocomotiveController {

    private final BuyLocomotiveService service;

    /**
     * Constructs the controller with its dependent service.
     */
    public BuyLocomotiveController(BuyLocomotiveService service) {
        this.service = service;
    }

    /**
     * Returns the list of available locomotives.
     */
    public List<Locomotive> listAvailableLocomotives() {
        return service.getAvailableLocomotives();
    }

    /**
     * Buys a locomotive by ID and registers a train.
     */
    public Train buyLocomotive(String locomotiveId) {
        return service.buyLocomotive(locomotiveId);
    }
}
