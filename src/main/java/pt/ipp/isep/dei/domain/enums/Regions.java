package pt.ipp.isep.dei.domain.enums;

/**
 * Enum representing the geographical regions in the simulation.
 */
public enum Regions {
    NE("ne"),
    NW("nw"),
    SE("se"),
    SW("sw");

    private final String region;

    /**
     * Constructs a {@code Regions} enum with a specific abbreviation.
     *
     * @param region the abbreviation for the region
     */
    Regions(String region) {
        this.region = region;
    }

    /**
     * Gets the lowercase abbreviation for the region.
     *
     * @return the region abbreviation as a lowercase string
     */
    public String getRegion() {
        return region;
    }
}
