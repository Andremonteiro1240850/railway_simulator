package pt.ipp.isep.dei.controller;

import pt.ipp.isep.dei.domain.*;
import pt.ipp.isep.dei.domain.enums.ScaleUnit;
import pt.ipp.isep.dei.repository.CreateMapRepository;

import java.util.Optional;

/**
 * Controller responsible for validating and creating new maps.
 */
public class CreateMapController {
    private final MapValidator validator = new MapValidator();
    private final CreateMapRepository repo = new CreateMapRepository();

    /**
     * Creates a new map for the given organization.
     */
    public Optional<String> createMap(String name, int width, int height, ScaleUnit scale, Organization organization) {
        if (!validator.isValidName(name)) return Optional.of("❌ Invalid map name.");
        if (!validator.isValidSize(width, height)) return Optional.of("❌ Invalid dimensions.");
        if (!validator.isValidScale(scale)) return Optional.of("❌ Invalid scale.");
        if (organization == null) return Optional.of("❌ Organization is required.");

        Map map = new Map(name, width, height, scale, organization);
        return repo.save(map).isPresent()
                ? Optional.of("✅ Map created successfully:\n" + map)
                : Optional.of("❌ Map already exists for this organization.");
    }
}
