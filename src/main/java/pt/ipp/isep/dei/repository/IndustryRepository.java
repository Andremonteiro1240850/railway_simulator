package pt.ipp.isep.dei.repository;

import pt.ipp.isep.dei.domain.Industry;
import java.util.*;

public class IndustryRepository {
    private final List<Industry> industries = new ArrayList<>();

    public Optional<Industry> add(Industry industry) {
        if (industry == null || industries.contains(industry)) {
            return Optional.empty();
        }
        industries.add(industry);
        return Optional.of(industry);
    }

    public List<Industry> getAll() {
        return new ArrayList<>(industries);
    }
}