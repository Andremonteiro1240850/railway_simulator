package pt.ipp.isep.dei.domain;

/**
 * Represents a scenario associated with a specific map and configuration.
 */
public class Scenario {
    private final String name;
    private final Map map;
    private final String transportType;
    private final String portType;
    private final int industryGenerationRate;

    /**
     * Constructs a Scenario.
     * @param name name of the scenario
     * @param map the associated map
     * @param transportType selected transport system
     * @param portType selected port behavior
     * @param industryGenerationRate industry cargo generation rate per turn
     */
    public Scenario(String name, Map map, String transportType, String portType, int industryGenerationRate) {
        this.name = name;
        this.map = map;
        this.transportType = transportType;
        this.portType = portType;
        this.industryGenerationRate = industryGenerationRate;
    }

    /** @return scenario name */
    public String getName() { return name; }

    /** @return associated map */
    public Map getMap() { return map; }

    /** @return selected transport type */
    public String getTransportType() { return transportType; }

    /** @return selected port behavior */
    public String getPortType() { return portType; }

    /** @return industry generation rate per turn */
    public int getIndustryGenerationRate() { return industryGenerationRate; }
}
