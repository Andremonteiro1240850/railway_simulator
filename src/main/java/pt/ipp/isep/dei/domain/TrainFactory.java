package pt.ipp.isep.dei.domain;

/**
 * Factory responsible for creating new trains.
 */
public class TrainFactory {

    /**
     * Creates a new train for the given locomotive.
     */
    public Train createTrain(Locomotive locomotive) {
        return new Train(locomotive);
    }
}
