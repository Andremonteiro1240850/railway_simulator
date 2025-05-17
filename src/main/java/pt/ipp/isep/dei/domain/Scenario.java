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

    public Scenario(String name, Map map, int startYear, int endYear) {
        this.name = name;
        this.map = map;
        this.startYear = startYear;
        this.currentYear = startYear;
        this.endYear = endYear;
        this.availableLocomotiveTypes = new ArrayList<>();
        this.historicalEvents = new ArrayList<>();
    }

    // Getters e Setters
    public int getCurrentYear() { return currentYear; }
    public String getName() { return name; }
    public Map getMap() { return map; }
    public int getStartYear() { return startYear; }
    public int getEndYear() { return endYear; }
    public List<String> getAvailableLocomotiveTypes() { return new ArrayList<>(availableLocomotiveTypes); }
    public PortBehavior getPortBehavior() { return portBehavior; }
    public List<HistoricalEvent> getHistoricalEvents() { return new ArrayList<>(historicalEvents); }

    public void setPortBehavior(PortBehavior portBehavior) {
        this.portBehavior = portBehavior;
    }
    public void setCurrentYear(int currentYear) {
        this.currentYear = currentYear;
    }

    public void addLocomotiveType(String type) {
        if (!availableLocomotiveTypes.contains(type)) {
            availableLocomotiveTypes.add(type);
        }
    }

    public void addHistoricalEvent(HistoricalEvent event) {
        historicalEvents.add(event);
    }

    public Scenario clone() {
        Scenario clone = new Scenario(name, map.clone(), startYear, endYear);
        clone.availableLocomotiveTypes = new ArrayList<>(this.availableLocomotiveTypes);
        clone.portBehavior = this.portBehavior != null ? this.portBehavior.clone() : null;
        clone.historicalEvents = new ArrayList<>(this.historicalEvents);
        clone.currentYear = this.currentYear;
        return clone;
    }
}

