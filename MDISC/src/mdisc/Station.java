package mdisc;


/**
 * Represents a railway station with a name and a type.
 * The station type is inferred from the station name.
 */
public class Station {

    /**
     * The name of the station.
     */
    public final String name;

    /**
     * The type of the station (e.g., TERMINAL, DEPOT).
     */
    public final StationType type;

    /**
     * Constructs a new Station with a given name.
     * The type is automatically determined based on the name.
     *
     * @param name The name of the station.
     */
    public Station(String name) {
        this.name = name;
        this.type = StationType.fromName(name);
    }
}
