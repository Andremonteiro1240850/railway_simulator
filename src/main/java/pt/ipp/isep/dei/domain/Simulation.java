package pt.ipp.isep.dei.domain;

import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

import pt.ipp.isep.dei.domain.enums.CargoMode;

public class Simulation implements Serializable {
    private static final long serialVersionUID = 1L;
    private final Scenario scenario;
    private final Player player;
    private int currentYear;
    private int currentMonth; // 1-12 representing January to December
    private final List<Route> activeRoutes;
    private final List<Station> stations;
    private final List<Locomotive> locomotives;
    private final List<FinancialResults> financialHistory;
    private FinancialResults currentYearResults;

    public Simulation(Scenario scenario, Player player) {
        this.scenario = scenario;
        this.player = player;
        this.currentYear = scenario.getStartYear();
        this.currentMonth = 1; // Start in January
        this.activeRoutes = new ArrayList<>();
        this.stations = new ArrayList<>();
        this.locomotives = new ArrayList<>();
        this.financialHistory = new ArrayList<>();
        this.currentYearResults = new FinancialResults(currentYear);
    }

    public void advanceMonth() {
        currentMonth++;
        if (currentMonth > 12) {
            currentMonth = 1;
            currentYear++;
            calculateYearlyExpenses();
            financialHistory.add(currentYearResults);
            currentYearResults = new FinancialResults(currentYear);
        }
        calculateMonthlyRevenue();
        updateCargoStatus();
        checkNewTechnologies();
    }

    private void calculateYearlyExpenses() {
        // Calculate track maintenance expenses based on total track length
        double totalTrackLength = calculateTotalTrackLength();
        double trackMaintenanceCost = totalTrackLength * 100; // 100 per km
        currentYearResults.addTrackMaintenanceExpense(trackMaintenanceCost, 
            String.format("Maintenance for %.2f km of track", totalTrackLength));

        // Calculate train maintenance expenses
        for (Locomotive locomotive : locomotives) {
            double maintenanceCost = locomotive.getMaintenanceCostPerYear();
            currentYearResults.addTrainMaintenanceExpense(maintenanceCost,
                String.format("Maintenance for locomotive %s", locomotive.getReference()));
        }

        // Calculate fuel expenses based on traveled kilometers
        for (Route route : activeRoutes) {
            double routeLength = calculateRouteLength(route);
            for (Locomotive locomotive : locomotives) {
                double fuelCost = routeLength * locomotive.getFuelCost();
                currentYearResults.addFuelExpense(fuelCost,
                    String.format("Fuel for locomotive %s on route %s", 
                        locomotive.getReference(), route.getName()));
            }
        }
    }

    private void calculateMonthlyRevenue() {
        for (Route route : activeRoutes) {
            double routeRevenue = calculateRouteRevenue(route);
            currentYearResults.addCargoRevenue(routeRevenue,
                String.format("Revenue from route %s", route.getName()));
        }
    }

    private double calculateTotalTrackLength() {
        double totalLength = 0;
        for (Route route : activeRoutes) {
            totalLength += calculateRouteLength(route);
        }
        return totalLength;
    }

    private double calculateRouteLength(Route route) {
        double length = 0;
        List<PointsOfRoute> points = route.getRoute();
        for (int i = 0; i < points.size() - 1; i++) {
            Station currentStation = points.get(i).getStations();
            Station nextStation = points.get(i + 1).getStations();
            length += calculateDistance(currentStation, nextStation);
        }
        return length;
    }

    private double calculateRouteRevenue(Route route) {
        double revenue = 0.0;
        List<PointsOfRoute> points = route.getRoute();
        
        for (int i = 0; i < points.size() - 1; i++) {
            Station currentStation = points.get(i).getStations();
            Station nextStation = points.get(i + 1).getStations();
            CargoMode cargoMode = points.get(i).getCargoMode();
            
            revenue += calculateCargoRevenue(currentStation, nextStation, cargoMode);
        }
        
        return revenue;
    }

    private double calculateCargoRevenue(Station from, Station to, CargoMode cargoMode) {
        double distance = calculateDistance(from, to);
        String cargoType = cargoMode.name().toLowerCase();
        double baseRate = getCargoBaseRate(cargoMode);
        int demanda = to.getDemanda(cargoType);
        double ajusteDemanda = 1.0;
        if (demanda >= 8) ajusteDemanda = 1.6;
        else if (demanda <= 2) ajusteDemanda = 0.6;
        double quantidade = 1.0;
        return distance * baseRate * ajusteDemanda * quantidade;
    }

    private double calculateDistance(Station from, Station to) {
        int dx = to.getX() - from.getX();
        int dy = to.getY() - from.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }

    private double getCargoBaseRate(CargoMode cargoMode) {
        return switch (cargoMode) {
            case FULL -> 100.0;
            case HALF -> 75.0;
            case AVAILABLE -> 50.0;
        };
    }

    private void updateCargoStatus() {
        // Update cargo demands and supplies at each station
        for (Station station : stations) {
            // Update based on current year and historical data
            updateStationCargo(station);
        }
    }

    private void updateStationCargo(Station station) {
        // Update cargo availability based on:
        // 1. Historical trends
        // 2. Station improvements
        // 3. Current year
        // 4. Random factors
    }

    private void checkNewTechnologies() {
        for (pt.ipp.isep.dei.domain.enums.ListLocomotives locoEnum : pt.ipp.isep.dei.domain.enums.ListLocomotives.values()) {
            if (locoEnum.getAvailableYear() == currentYear) {
                notifyNewTechnology(locoEnum.getLocomotiveType());
            }
        }
    }

    private void notifyNewTechnology(pt.ipp.isep.dei.domain.enums.LocomotiveType type) {
        System.out.println("New technology available: " + type.name() + " locomotives");
    }

    public void addRoute(Route route) {
        if (validateRoute(route)) {
            activeRoutes.add(route);
        }
    }

    private boolean validateRoute(Route route) {
        // Validate that:
        // 1. All stations exist
        // 2. There are railway lines connecting the stations
        // 3. The locomotive is suitable for the route
        return true; // Placeholder
    }

    public int getCurrentYear() {
        return currentYear;
    }

    public int getCurrentMonth() {
        return currentMonth;
    }

    public List<Route> getActiveRoutes() {
        return new ArrayList<>(activeRoutes);
    }

    public int getEndYear() {
        return scenario.getEndYear();
    }

    public Scenario getScenario() {
        return scenario;
    }

    public List<Station> getStations() {
        return new ArrayList<>(stations);
    }

    public void addStation(Station station) {
        if (station != null) {
            stations.add(station);
        }
    }

    public void addLocomotive(Locomotive locomotive) {
        this.locomotives.add(locomotive);
    }

    public List<Locomotive> getLocomotives() {
        return new ArrayList<>(locomotives);
    }

    public List<FinancialResults> getFinancialHistory() {
        return new ArrayList<>(financialHistory);
    }

    public FinancialResults getCurrentYearResults() {
        return currentYearResults;
    }
} 