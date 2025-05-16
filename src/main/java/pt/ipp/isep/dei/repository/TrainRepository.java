package pt.ipp.isep.dei.repository;

import java.util.List;

/**
 * Repository interface for managing trains.
 */
public interface TrainRepository {
    void save(Train train);
    List<Train> findAll();
}
