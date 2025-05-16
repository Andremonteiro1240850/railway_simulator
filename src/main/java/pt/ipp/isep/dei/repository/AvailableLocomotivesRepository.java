package pt.ipp.isep.dei.repository;

import java.util.List;

/**
 * Repository interface for retrieving locomotives.
 */
public interface AvailableLocomotivesRepository {
    List<Locomotive> findAvailableByYear(int year);
    Locomotive findById(String id);
}
