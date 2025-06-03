package pt.ipp.isep.dei.domain;



import pt.ipp.isep.dei.domain.enums.Eventos;
import pt.ipp.isep.dei.domain.enums.LocomotiveType;

import java.util.ArrayList;
import java.util.List;

public class Scenario {
    private final String name;
    private double budget = 150000;
    private int startYear;
    private int endYear;
    private int currentYear;
    private List<LocomotiveType> availableLocomotiveTypes;
    private PortBehavior portBehavior;
    private List<HistoricalEvent> historicalEvents;

    /**
     * Constructs a new Scenario with the given parameters.
     *
     * @param name The name of the scenario.

     * @param startYear The starting year of the scenario.
     * @param endYear The ending year of the scenario.
     */

    public Scenario(String name, int startYear, int endYear, List<LocomotiveType> availableLocomotiveTypes,
                    List<HistoricalEvent> historicalEvents, PortBehavior portBehavior, double budget) {
        this.name = name;
        this.startYear = startYear;
        this.currentYear = startYear;
        this.endYear = endYear;
        this.availableLocomotiveTypes = availableLocomotiveTypes;
        this.historicalEvents = (historicalEvents != null) ? new ArrayList<>(historicalEvents) : new ArrayList<>();
        this.portBehavior = portBehavior;
        this.budget = budget;
        loadApplicableHistoricalEvents();
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
    public List<LocomotiveType> getAvailableLocomotiveTypes() {return new ArrayList<>(availableLocomotiveTypes);}

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
    public double getBudget() { return budget; }
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
    public void setBudget(double budget) { this.budget = budget; }

    private void loadApplicableHistoricalEvents() {
        for (Eventos eventoEnum : Eventos.values()) {
            if (eventoEnum.getDataInicio() <= this.startYear && this.startYear <= eventoEnum.getDataFim()) {
                this.historicalEvents.add(new HistoricalEvent(eventoEnum));
            }
        }
    }

    /**
     * Creates and returns a deep copy of this Scenario.
     *
     * @return A cloned Scenario object.
     */

    public Scenario clone() {
        Scenario clone = new Scenario(name, startYear, endYear, availableLocomotiveTypes, historicalEvents, portBehavior, budget);
        clone.availableLocomotiveTypes = new ArrayList<>(this.availableLocomotiveTypes);
        clone.portBehavior = this.portBehavior != null ? this.portBehavior.clone() : null;
        clone.historicalEvents = new ArrayList<>(this.historicalEvents);
        clone.currentYear = this.currentYear;
        return clone;
    }
}

