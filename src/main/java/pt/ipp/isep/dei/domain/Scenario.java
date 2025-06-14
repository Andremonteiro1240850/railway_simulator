package pt.ipp.isep.dei.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import pt.ipp.isep.dei.domain.enums.Eventos;
import pt.ipp.isep.dei.domain.enums.ListLocomotives;
import pt.ipp.isep.dei.domain.enums.LocomotiveType;

/**
 * Represents a scenario in the game simulation.
 * A scenario defines a time window, available locomotive types,
 * historical events, budget, map, and behavior of ports.
 */
public class Scenario {
    private final String name;
    private double budget = 700000;
    private int startYear;
    private int endYear;
    private int currentYear;
    private int startMonth = 1; // Default to January
    private int currentMonth = 1;
    private List<LocomotiveType> availableLocomotiveTypes;
    private PortBehavior portBehavior;
    private List<HistoricalEvent> historicalEvents;
    private List<Locomotive> locomotivePurchased;
    private List<Carriage> cariagePurchased;
    private List<Station> stationsPurchased;
    private List<Locomotive> locomotivesDisponiveis = new ArrayList<>();
    private Map map;
    private List<Route> routes;

    /**
     * Constructs a new Scenario with the given parameters.
     *
     * @param name name of the scenario
     * @param startYear the starting year of the scenario
     * @param endYear the ending year of the scenario
     * @param availableLocomotiveTypes list of locomotive types available in this scenario
     * @param historicalEvents list of historical events influencing this scenario
     * @param portBehavior the port behavior configuration
     * @param budget the initial budget
     * @param map the associated map
     */
    public Scenario(String name, int startYear, int endYear, List<LocomotiveType> availableLocomotiveTypes,
                    List<HistoricalEvent> historicalEvents, PortBehavior portBehavior, double budget, Map map) {
        this.name = name;
        this.startYear = startYear;
        this.currentYear = startYear;
        this.endYear = endYear;
        this.startMonth = 1;
        this.currentMonth = 1;
        this.availableLocomotiveTypes = availableLocomotiveTypes;
        this.historicalEvents = (historicalEvents != null) ? new ArrayList<>(historicalEvents) : new ArrayList<>();
        this.portBehavior = portBehavior;
        this.budget = budget;
        loadApplicableHistoricalEvents();
        this.locomotivePurchased = new ArrayList<>();
        this.cariagePurchased = new ArrayList<>();
        this.stationsPurchased = new ArrayList<>();
        this.map = map;
        this.routes = new ArrayList<>();
    }

    /**
     * Gets the current year of the scenario.
     *
     * @return the current year
     */
    public int getCurrentYear() { return currentYear; }

    /**
     * Gets the name of the scenario.
     *
     * @return the name
     */
    public String getName() { return name; }

    /**
     * Gets the starting year of the scenario.
     *
     * @return the start year
     */
    public int getStartYear() { return startYear; }

    /**
     * Gets the ending year of the scenario.
     *
     * @return the end year
     */
    public int getEndYear() { return endYear; }

    /**
     * Gets the list of available locomotive types.
     *
     * @return a copy of the available locomotive types
     */
    public List<LocomotiveType> getAvailableLocomotiveTypes() {
        return new ArrayList<>(availableLocomotiveTypes);
    }

    /**
     * Gets the port behavior configuration.
     *
     * @return the port behavior
     */
    public PortBehavior getPortBehavior() { return portBehavior; }

    /**
     * Gets the list of historical events.
     *
     * @return a copy of the historical events
     */
    public List<HistoricalEvent> getHistoricalEvents() { return new ArrayList<>(historicalEvents); }

    /**
     * Gets the scenario's current budget.
     *
     * @return the budget
     */
    public double getBudget() { return budget; }

    /**
     * Gets the list of purchased locomotives.
     *
     * @return the locomotives purchased
     */
    public List<Locomotive> getLocomotivePurchased() { return locomotivePurchased; }

    /**
     * Gets the list of purchased carriages.
     *
     * @return the carriages purchased
     */
    public List<Carriage> getCarriagePurchased() { return cariagePurchased; }

    /**
     * Gets the list of purchased stations.
     *
     * @return the stations purchased
     */
    public List<Station> getStationsPurchased() { return stationsPurchased; }

    /**
     * Gets the map associated with the scenario.
     *
     * @return the map
     */
    public Map getMap() { return map; }

    /**
     * Sets the port behavior configuration.
     *
     * @param portBehavior the new port behavior
     */
    public void setPortBehavior(PortBehavior portBehavior) { this.portBehavior = portBehavior; }

    /**
     * Sets the current year of the scenario.
     *
     * @param currentYear the year to set
     */
    public void setCurrentYear(int currentYear) { this.currentYear = currentYear; }

    /**
     * Sets the budget of the scenario.
     *
     * @param budget the budget to set
     */
    public void setBudget(double budget) { this.budget = budget; }

    /**
     * Adds a locomotive to the list of purchased locomotives.
     *
     * @param locomotive the locomotive to add
     */
    public void addLomotivePurchased(Locomotive locomotive) { locomotivePurchased.add(locomotive); }

    /**
     * Adds a carriage to the list of purchased carriages.
     *
     * @param carriage the carriage to add
     */
    public void addCarriagePurchased(Carriage carriage) { cariagePurchased.add(carriage); }

    /**
     * Adds a station to the list of purchased stations.
     *
     * @param station the station to add
     */
    public void addStationPurchased(Station station) { stationsPurchased.add(station); }

    /**
     * Loads historical events applicable to the scenario's start year.
     */
    private void loadApplicableHistoricalEvents() {
        for (Eventos eventoEnum : Eventos.values()) {
            if (eventoEnum.getDataInicio() <= this.startYear && this.startYear <= eventoEnum.getDataFim()) {
                this.historicalEvents.add(new HistoricalEvent(eventoEnum));
            }
        }
    }

    /**
     * Creates a deep copy of the scenario.
     *
     * @return a new Scenario object with the same data
     */
    @Override
    public Scenario clone() {
        Scenario clone = new Scenario(name, startYear, endYear, availableLocomotiveTypes, historicalEvents, portBehavior, budget, map);
        clone.availableLocomotiveTypes = new ArrayList<>(this.availableLocomotiveTypes);
        clone.portBehavior = this.portBehavior != null ? this.portBehavior.clone() : null;
        clone.historicalEvents = new ArrayList<>(this.historicalEvents);
        clone.currentYear = this.currentYear;
        clone.stationsPurchased = this.stationsPurchased.stream().map(Station::copy).collect(Collectors.toList());
        clone.locomotivePurchased = this.locomotivePurchased.stream().map(Locomotive::copy).collect(Collectors.toList());
        clone.cariagePurchased = this.cariagePurchased.stream().map(Carriage::copy).collect(Collectors.toList());
        return clone;
    }

    /**
     * Returns a textual representation of the scenario.
     *
     * @return the scenario as a string
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== Scenario: ").append(name).append(" ===\n");
        sb.append("Budget: ").append(String.format("%.2f", budget)).append("€\n");
        sb.append("Time Period: ").append(startYear).append(" - ").append(endYear)
                .append(" (Current Year: ").append(currentYear).append(")\n\n");

        sb.append("Available Locomotive Types:\n");
        if (availableLocomotiveTypes != null && !availableLocomotiveTypes.isEmpty()) {
            for (LocomotiveType lt : availableLocomotiveTypes) {
                sb.append("  - ").append(lt).append("\n");
            }
        } else {
            sb.append("  None\n");
        }

        sb.append("\nPort Behavior:\n");
        sb.append(portBehavior != null ? portBehavior : "  None").append("\n");

        sb.append("\nHistorical Events:\n");
        if (historicalEvents != null && !historicalEvents.isEmpty()) {
            for (HistoricalEvent event : historicalEvents) {
                sb.append("  - ").append(event).append("\n");
            }
        } else {
            sb.append("  None\n");
        }

        return sb.toString();
    }

    /**
     * Updates the list of available locomotives based on the current year.
     *
     * @param anoAtual the current year to check availability
     */
    public void atualizarLocomotivasDisponiveis(int anoAtual) {
        for (ListLocomotives entry : ListLocomotives.values()) {
            if (entry.getAvailableYear() <= anoAtual && entry.getRetiredYear() >= anoAtual) {
                Locomotive loco = entry.toLocomotive();
                if (!locomotivesDisponiveis.contains(loco)) {
                    locomotivesDisponiveis.add(loco);
                }
            }
        }
    }

    /**
     * Returns a summary of the scenario.
     *
     * @return the summary string
     */
    public String getSummary() {
        return String.format("%s (%d–%d) – Budget: %.2f€", name, startYear, endYear, budget);
    }

    /**
     * Gets the list of currently available locomotives.
     *
     * @return a copy of the list of available locomotives
     */
    public List<Locomotive> getLocomotivesDisponiveis() {
        return new ArrayList<>(locomotivesDisponiveis);
    }

    /**
     * Adds a route to the scenario.
     *
     * @param route the route to add
     */
    public void addRoute(Route route) {
        routes.add(route);
    }

    /**
     * Gets the list of routes associated with the scenario.
     *
     * @return a copy of the list of routes
     */
    public List<Route> getRoutes() {
        return new ArrayList<>(routes);
    }

    /**
     * Adds a locomotive to the list of purchased locomotives.
     *
     * @param locomotive the locomotive to add
     */
    public void addLocomotivePurchased(Locomotive locomotive) {
        this.locomotivePurchased.add(locomotive);
    }

    public int getStartMonth() {
        return startMonth;
    }

    public int getCurrentMonth() {
        return currentMonth;
    }

    public void setCurrentMonth(int currentMonth) {
        this.currentMonth = currentMonth;
    }
}
