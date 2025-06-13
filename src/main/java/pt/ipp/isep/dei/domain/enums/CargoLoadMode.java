package pt.ipp.isep.dei.domain.enums;

/**
 * Defines the loading behavior modes for a train when waiting for cargo at a station.
 */
public enum CargoLoadMode {

    /**
     * The train departs only when it is fully loaded with cargo.
     */
    FULL,

    /**
     * The train departs when it is at least half loaded.
     */
    HALF,

    /**
     * The train departs as soon as any cargo is available, regardless of quantity.
     */
    AVAILABLE
}
