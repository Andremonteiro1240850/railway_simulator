package pt.ipp.isep.dei.domain.enums;

import pt.ipp.isep.dei.domain.Locomotive;

public enum ListLocomotives {
    // Steam Locomotives
    AMERICAN_C("Steam", 46000, 5000, 12606, 42, "Poor", "Above Average", 1848, 1885),
    IRON_DUKE("Steam", 78000, 9000, 30736, 54, "Poor", "Below Average", 1855, 1895),
    EIGHT_WHEELER("Steam", 59000, 5000, 17808, 48, "Poor", "Average", 1863, 1903),
    VULCAN("Steam", 29000, 4000, 370, 30, "Poor", "Average", 1872, 1892),
    CONSOLIDATION("Steam", 51000, 8000, 19512, 45, "Below Average", "Good", 1877, 1905),
    THREE_TRUCK_SHAY("Steam", 43000, 17000, 18227, 12, "Poor", "Average", 1882, 1910),
    MASTODON("Steam", 60000, 13000, 22995, 45, "Average", "Good", 1890, 1911),
    TEN_WHEELER("Steam", 66000, 11000, 21727, 50, "Average", "Good", 1892, 1911),
    MOGUL("Steam", 83000, 12000, 25131, 50, "Average", "Average", 1895, 1915),
    ATLANTIC("Steam", 93000, 18000, 43556, 80, "Above Average", "Good", 1902, 1925),
    CAMELBACK("Steam", 75000, 9000, 15000, 30, "Average", "Average", 1905, 1932),
    PACIFIC("Steam", 119000, 21000, 62516, 95, "Above Average", "Good", 1908, 1935),
    CLASS_G10("Steam", 98000, 38000, 50521, 50, "Average", "Good", 1910, 1935),
    PRAIRIE("Steam", 85000, 9000, 34157, 60, "Average", "Good", 1912, 1935),
    CLASS_XIII_H("Steam", 102000, 36000, 46289, 40, "Average", "Good", 1917, 1935),
    USRA_0_6_0("Steam", 90000, 13000, 29993, 40, "Average", "Good", 1918, 1935),
    MIKADO("Steam", 133000, 32000, 51072, 55, "Above Average", "Good", 1919, 1950),

    // Electric Locomotives
    BOBO("Electric", 85000, 6000, 33116, 60, "Above Average", "Good", 1895, 1935),

    // Diesel Locomotives
    GP18("Diesel", 245000, 15000, 53878, 83, "Above Average", "Very Good", 1958, 1975),
    V200("Diesel", 160000, 19000, 53878, 87, "Above Average", "Below Average", 1959, 1985),
    CLASS_55_DELTIC("Diesel", 480000, 15000, 52155, 100, "Below Average", "Very Good", 1961, 1981),
    FP45("Diesel", 366000, 22000, 65740, 106, "Above Average", "Above Average", 1968, 9999),
    SD45("Diesel", 280000, 21000, 36009, 65, "Average", "Average", 1972, 1994),
    SDP40("Diesel", 292000, 18000, 61966, 103, "Above Average", "Poor", 1973, 1989),
    DASH_9("Diesel", 478000, 32000, 68410, 70, "Average", "Average", 1993, 9999),
    AMD_103("Diesel", 425000, 25000, 88068, 103, "Very Fast", "Above Average", 1993, 9999),
    CLASS_232("Diesel", 492000, 35000, 72625, 75, "Average", "Average", 1997, 9999);

    private final String fuelType;
    private final int buildCost;
    private final int maintenanceCost;
    private final int fuelCost;
    private final int topSpeed;
    private final String acceleration;
    private final String reliability;
    private final int availableYear;
    private final int retiredYear;

    ListLocomotives(String fuelType, int buildCost, int maintenanceCost, int fuelCost,
                    int topSpeed, String acceleration, String reliability,
                    int availableYear, int retiredYear) {
        this.fuelType = fuelType;
        this.buildCost = buildCost;
        this.maintenanceCost = maintenanceCost;
        this.fuelCost = fuelCost;
        this.topSpeed = topSpeed;
        this.acceleration = acceleration;
        this.reliability = reliability;
        this.availableYear = availableYear;
        this.retiredYear = retiredYear;
    }

    public String getFuelType() {
        return fuelType;
    }

    public int getBuildCost() {
        return buildCost;
    }

    public int getMaintenanceCost() {
        return maintenanceCost;
    }

    public int getFuelCost() {
        return fuelCost;
    }

    public int getTopSpeed() {
        return topSpeed;
    }

    public String getAcceleration() {
        return acceleration;
    }

    public String getReliability() {
        return reliability;
    }

    public int getAvailableYear() {
        return availableYear;
    }

    public int getRetiredYear() {
        return retiredYear;
    }

    public Locomotive toLocomotive() {
        return new Locomotive(this.name(), fuelType, buildCost, maintenanceCost, fuelCost,
                topSpeed, acceleration, reliability, availableYear, retiredYear);
    }
}

