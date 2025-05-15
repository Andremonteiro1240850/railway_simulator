package pt.ipp.isep.dei.domain;

import pt.ipp.isep.dei.repository.AvailableLocomotivesRepository;
import pt.ipp.isep.dei.repository.TrainRepository;

import java.util.List;

/**
 * Application service that handles the locomotive purchase logic.
 */
public class BuyLocomotiveService {

    private final AvailableLocomotivesRepository locomotivesRepo;
    private final TrainRepository trainRepo;
    private final Scenario scenario;
    private final TrainFactory trainFactory;

    /**
     * Constructs the service with its dependencies.
     */
    public BuyLocomotiveService(AvailableLocomotivesRepository locomotivesRepo,
                                TrainRepository trainRepo,
                                Scenario scenario,
                                TrainFactory trainFactory) {
        this.locomotivesRepo = locomotivesRepo;
        this.trainRepo = trainRepo;
        this.scenario = scenario;
        this.trainFactory = trainFactory;
    }

    /**
     * Retrieves locomotives available in the current scenario and date.
     */
    public List<Locomotive> getAvailableLocomotives() {
        int currentYear = scenario.getCurrentYear();
        return locomotivesRepo.findAvailableByYear(currentYear);
    }

    /**
     * Purchases a locomotive by ID and creates a train with it.
     */
    public Train buyLocomotive(String locomotiveId) {
        Locomotive loco = locomotivesRepo.findById(locomotiveId);
        if (loco == null || loco.getAvailableYear() > scenario.getCurrentYear()) {
            throw new IllegalArgumentException("Invalid or unavailable locomotive.");
        }

        Train train = trainFactory.createTrain(loco);
        trainRepo.save(train);
        return train;
    }
}
