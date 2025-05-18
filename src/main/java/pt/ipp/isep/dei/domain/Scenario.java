package pt.ipp.isep.dei.domain;



import java.util.ArrayList;
import java.util.List;

public class Scenario {
    private final String name;
    private final Map map;
    private int startYear;
    private int endYear;
    private int currentYear;
    private List<String> availableLocomotiveTypes;
    private PortBehavior portBehavior;
    private List<HistoricalEvent> historicalEvents;

    /**
     * Constructs a new Scenario with the given parameters.
     *
     * @param name The name of the scenario.
     * @param map The map associated with the scenario.
     * @param startYear The starting year of the scenario.
     * @param endYear The ending year of the scenario.
     */

    public Scenario(String name, Map map, int startYear, int endYear) {
        this.name = name;
        this.map = map;
        this.startYear = startYear;
        this.currentYear = startYear;
        this.endYear = endYear;
        this.availableLocomotiveTypes = new ArrayList<>();
        this.historicalEvents = new ArrayList<>();
    }

    /**
     * Gets the current year of the scenario.
     *
     * @return The current year.
     */

    // Getters e Setters
    public int getCurrentYear() { return currentYear; }
    /**
     * Gets the name of the scenario.
     *
     * @return The scenario name.
     */
    public String getName() { return name; }
    /**
     * Gets the map associated with the scenario.
     *
     * @return The scenario map.
     */
    public Map getMap() { return map; }
    /**
     * Gets the starting year of the scenario.
     *
     * @return The starting year.
     */
    public int getStartYear() { return startYear; }

    /**
     * Gets the ending year of the scenario.
     *
     * @return The ending year.
     */
    public int getEndYear() { return endYear; }
    /**
     * Gets a copy of the list of available locomotive types.
     *
     * @return A new ArrayList containing the available locomotive types.
     */
    public List<String> getAvailableLocomotiveTypes() { return new ArrayList<>(availableLocomotiveTypes); }
    /**
     * Gets the port behavior configuration.
     *
     * @return The port behavior.
     */
    public PortBehavior getPortBehavior() { return portBehavior; }
    /**
     * Gets a copy of the list of historical events.
     *
     * @return A new ArrayList containing the historical events.
     */
    public List<HistoricalEvent> getHistoricalEvents() { return new ArrayList<>(historicalEvents); }
    /**
     * Sets the port behavior configuration.
     *
     * @param portBehavior The port behavior to set.
     */

    public void setPortBehavior(PortBehavior portBehavior) {
        this.portBehavior = portBehavior;
    }
    /**
     * Sets the current year of the scenario.
     *
     * @param currentYear The current year to set.
     */
    public void setCurrentYear(int currentYear) {
        this.currentYear = currentYear;
    }

    /**
     * Adds a locomotive type to the list of available types if it's not already present.
     *
     * @param type The locomotive type to add.
     */

    public void addLocomotiveType(String type) {
        if (!availableLocomotiveTypes.contains(type)) {
            availableLocomotiveTypes.add(type);
        }
    }
    /**
     * Adds a historical event to the scenario.
     *
     * @param event The historical event to add.
     */

    public void addHistoricalEvent(HistoricalEvent event) {
        historicalEvents.add(event);
    }
    /**
     * Creates and returns a deep copy of this Scenario.
     *
     * @return A cloned Scenario object.
     */

    public Scenario clone() {
        Scenario clone = new Scenario(name, map.clone(), startYear, endYear);
        clone.availableLocomotiveTypes = new ArrayList<>(this.availableLocomotiveTypes);
        clone.portBehavior = this.portBehavior != null ? this.portBehavior.clone() : null;
        clone.historicalEvents = new ArrayList<>(this.historicalEvents);
        clone.currentYear = this.currentYear;
        return clone;
    }
}

