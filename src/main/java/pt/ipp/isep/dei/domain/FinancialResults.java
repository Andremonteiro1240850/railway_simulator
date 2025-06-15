package pt.ipp.isep.dei.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the financial results for a given year in the simulation.
 * Tracks expenses (track maintenance, train maintenance, fuel) and revenues (cargo deliveries).
 */
public class FinancialResults implements Serializable {
    private static final long serialVersionUID = 1L;
    private final int year;
    private double trackMaintenanceExpense;
    private double trainMaintenanceExpense;
    private double fuelExpense;
    private double cargoRevenue;
    private final List<String> details;

    public FinancialResults(int year) {
        this.year = year;
        this.details = new ArrayList<>();
    }

    public void addTrackMaintenanceExpense(double expense, String detail) {
        this.trackMaintenanceExpense += expense;
        this.details.add(String.format("Track Maintenance: %.2f - %s", expense, detail));
    }

    public void addTrainMaintenanceExpense(double expense, String detail) {
        this.trainMaintenanceExpense += expense;
        this.details.add(String.format("Train Maintenance: %.2f - %s", expense, detail));
    }

    public void addFuelExpense(double expense, String detail) {
        this.fuelExpense += expense;
        this.details.add(String.format("Fuel: %.2f - %s", expense, detail));
    }

    public void addCargoRevenue(double revenue, String detail) {
        this.cargoRevenue += revenue;
        this.details.add(String.format("Cargo Revenue: %.2f - %s", revenue, detail));
    }

    public double getTotalExpenses() {
        return trackMaintenanceExpense + trainMaintenanceExpense + fuelExpense;
    }

    public double getTotalRevenue() {
        return cargoRevenue;
    }

    public double getNetResult() {
        return getTotalRevenue() - getTotalExpenses();
    }

    public int getYear() {
        return year;
    }

    public double getTrackMaintenanceExpense() {
        return trackMaintenanceExpense;
    }

    public double getTrainMaintenanceExpense() {
        return trainMaintenanceExpense;
    }

    public double getFuelExpense() {
        return fuelExpense;
    }

    public double getCargoRevenue() {
        return cargoRevenue;
    }

    public List<String> getDetails() {
        return new ArrayList<>(details);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Financial Results for Year ").append(year).append("\n");
        sb.append("----------------------------------------\n");
        sb.append("Expenses:\n");
        sb.append(String.format("  Track Maintenance: %.2f\n", trackMaintenanceExpense));
        sb.append(String.format("  Train Maintenance: %.2f\n", trainMaintenanceExpense));
        sb.append(String.format("  Fuel: %.2f\n", fuelExpense));
        sb.append(String.format("Total Expenses: %.2f\n", getTotalExpenses()));
        sb.append("\nRevenues:\n");
        sb.append(String.format("  Cargo Deliveries: %.2f\n", cargoRevenue));
        sb.append(String.format("Total Revenue: %.2f\n", getTotalRevenue()));
        sb.append("\nNet Result: ").append(String.format("%.2f\n", getNetResult()));
        sb.append("----------------------------------------\n");
        sb.append("Details:\n");
        for (String detail : details) {
            sb.append("  ").append(detail).append("\n");
        }
        return sb.toString();
    }
} 