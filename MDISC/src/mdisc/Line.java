package mdisc;

/**
 * Represents a railway line connecting two stations.
 * Each line may be electrified and has a specific distance.
 */
public class Line {

    /**
     * The first station of the line.
     */
    public final Station a;

    /**
     * The second station of the line.
     */
    public final Station b;

    /**
     * Indicates whether the line is electrified.
     */
    public final boolean electrified;

    /**
     * The distance between the two stations in kilometers.
     */
    public final double distance;

    /**
     * Constructs a new Line connecting two stations.
     *
     * @param a            The starting station.
     * @param b            The ending station.
     * @param electrified  True if the line is electrified; false otherwise.
     * @param distance     The distance of the line in kilometers.
     */
    public Line(Station a, Station b, boolean electrified, double distance) {
        this.a = a;
        this.b = b;
        this.electrified = electrified;
        this.distance = distance;
    }
}
