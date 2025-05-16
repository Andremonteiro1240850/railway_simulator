package pt.ipp.isep.dei.repository;

import pt.ipp.isep.dei.domain.Locomotive;

import java.util.List;

/**
 * Repository interface for retrieving locomotives.
 */
public interface AvailableLocomotivesRepository {
    List<Locomotive> findAvailableByYear(int year);
    Locomotive findById(String id);
}
