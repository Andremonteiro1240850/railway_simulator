package pt.ipp.isep.dei.controller;

import pt.ipp.isep.dei.domain.*;
import pt.ipp.isep.dei.domain.enums.ScaleUnit;
import pt.ipp.isep.dei.repository.CreateMapRepository;

import java.util.Optional;

/**
 * Controller responsible for creating new maps.
 */
public class CreateMapController {
    private final CreateMapRepository repo = new CreateMapRepository();

    /**
     * Creates a new map for the given organization.
     */
    public Optional<String> createMap(String name, int width, int height, ScaleUnit scale, Organization organization) {
        try {
            Map map = new Map(name, width, height, scale, organization);
            return repo.save(map).isPresent()
                    ? Optional.of("✅ Map created successfully:\n" + map)
                    : Optional.of("❌ Map already exists for this organization.");
        } catch (IllegalArgumentException e) {
            return Optional.of("❌ " + e.getMessage());
        }
    }
}
