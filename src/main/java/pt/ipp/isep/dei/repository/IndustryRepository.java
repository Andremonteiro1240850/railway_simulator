package pt.ipp.isep.dei.repository;

import pt.ipp.isep.dei.domain.Industry;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class IndustryRepository {
    private final List<Industry> industries = new ArrayList<>();

    public Optional<Industry> add(Industry industry) {
        if (industry == null) {
            return Optional.empty();
        }

        if (!contains(industry)) {
            try {
                Industry clonedIndustry = industry.clone();
                industries.add(clonedIndustry);
                return Optional.of(clonedIndustry);
            } catch (Exception e) {
                System.err.println("Error cloning map: " + e.getMessage());
                return Optional.empty();
            }
        }
        return Optional.empty();
    }

    public boolean contains(Industry industry) {
        return industries.stream().anyMatch(m -> m.equals(industry));
    }

    public List<Industry> getAll() {
        return new ArrayList<>(industries); // Retorna cópia defensiva
    }
}

