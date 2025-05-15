package pt.ipp.isep.dei.domain;

public class BudgetManager {
    private static BudgetManager instance;
    private int currentBudget = 1000; // Initial budget

    private BudgetManager() {}

    public static synchronized BudgetManager getInstance() {
        if (instance == null) {
            instance = new BudgetManager();
        }
        return instance;
    }

    public boolean verifyIfPossible(String stationType) {
        int cost = getStationCost(stationType);
        return currentBudget >= cost;
    }

    public void deductCost(int amount) {
        currentBudget -= amount;
    }

    private int getStationCost(String type) {
        switch (type) {
            case "Depot": return 50;
            case "Station": return 100;
            case "Terminal": return 200;
            default: throw new IllegalArgumentException("Unknown station type");
        }
    }
}