package pt.ipp.isep.dei.repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Repository that manages available station types and regions.
 * Supports validation and retrieval of supported values.
 */
public class TypeStationRepository {
    private final List<String> stationTypes;
    private final List<String> stationRegions; // Apenas para estações do tipo "station"

    /**
     * Default constructor. Initializes lists of station types and regions.
     */
    public TypeStationRepository() {
        this.stationTypes = new ArrayList<>();
        this.stationRegions = new ArrayList<>();
        initializeStationTypes();
        initializeStationRegions();
    }

    /**
     * Initializes the list of allowed station types.
     */
    private void initializeStationTypes() {
        stationTypes.add("depot");
        stationTypes.add("terminal");
        stationTypes.add("station");
    }

    /**
     * Initializes the list of allowed regions (only for stations of type "station").
     */
    private void initializeStationRegions() {
        stationRegions.add("NE");
        stationRegions.add("NW");
        stationRegions.add("SE");
        stationRegions.add("SW");
    }

    /**
     * Returns a list of all available station types.
     *
     * @return list of station types
     */
    public List<String> getAllStationTypes() {
        return new ArrayList<>(stationTypes);
    }

    /**
     * Returns a list of all available station regions.
     *
     * @return list of station regions
     */
    public List<String> getAllStationRegions() {
        return new ArrayList<>(stationRegions);
    }

    /**
     * Checks whether a given station type is valid.
     *
     * @param type the station type to check
     * @return true if valid, false otherwise
     */
    public boolean isValidStationType(String type) {
        return stationTypes.contains(type.toLowerCase());
    }

    /**
     * Checks whether a given station region is valid.
     * Only applies to stations of type "station".
     *
     * @param region the region to check
     * @return true if valid, false otherwise
     */
    public boolean isValidStationRegion(String region) {
        return stationRegions.contains(region.toUpperCase());
    }
}


