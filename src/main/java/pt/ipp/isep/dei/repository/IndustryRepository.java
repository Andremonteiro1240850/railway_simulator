package pt.ipp.isep.dei.repository;

import pt.ipp.isep.dei.domain.Industry;

import java.util.*;

/**
 * Repository responsible for managing Industry entities.
 * Provides methods to add and retrieve industry instances.
 */
public class IndustryRepository {

    private final List<Industry> industries = new ArrayList<>();

    /**
     * Attempts to add a new industry to the repository.
     * The industry is only added if it is not null and not already present.
     *
     * @param industry the industry to add
     * @return an Optional containing the added industry if successful, or empty if it was null or already exists
     */
    public Optional<Industry> add(Industry industry) {
        if (industry == null || industries.contains(industry)) {
            return Optional.empty();
        }
        industries.add(industry);
        return Optional.of(industry);
    }

    /**
     * Retrieves all stored industries in a defensive copy list.
     *
     * @return a list containing all industries
     */
    public List<Industry> getAll() {
        return new ArrayList<>(industries); // Defensive copy
    }
}
