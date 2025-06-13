package pt.ipp.isep.dei.domain.enums;

import pt.ipp.isep.dei.domain.Locomotive;


/**
 * Enumeration representing a predefined list of locomotives available in the simulation.
 * Each entry includes metadata such as name, type of fuel, cost, speed, acceleration,
 * reliability, availability period, and associated locomotive type.
 *
 * <p>This enum is primarily used to initialize available locomotives in scenarios
 * and allows for the creation of {@link Locomotive} objects through the {@code toLocomotive()} method.</p>
 */

public enum ListLocomotives {
    // Steam Locomotives
    AMERICAN_C("American_c","Steam", 46000, 5000, 12606, 42, "Poor", "Above Average", 1848, 1885),
    IRON_DUKE("Iron_Duke","Steam", 78000, 9000, 30736, 54, "Poor", "Below Average", 1855, 1895),
    EIGHT_WHEELER("Eight_Wheeler","Steam", 59000, 5000, 17808, 48, "Poor", "Average", 1863, 1903),
    VULCAN("Vulcan","Steam", 29000, 4000, 370, 30, "Poor", "Average", 1872, 1892),
    CONSOLIDATION("CONSOLIDATION","Steam", 51000, 8000, 19512, 45, "Below Average", "Good", 1877, 1905),
    THREE_TRUCK_SHAY("THREE_TRUCK_SHAY","Steam", 43000, 17000, 18227, 12, "Poor", "Average", 1882, 1910),
    MASTODON("MASTODON","Steam", 60000, 13000, 22995, 45, "Average", "Good", 1890, 1911),
    TEN_WHEELER("TEN_WHEELER","Steam", 66000, 11000, 21727, 50, "Average", "Good", 1892, 1911),
    MOGUL("MOGUL","Steam", 83000, 12000, 25131, 50, "Average", "Average", 1895, 1915),
    ATLANTIC( "ATLANTIC","Steam", 93000, 18000, 43556, 80, "Above Average", "Good", 1902, 1925),
    CAMELBACK(" CAMELBACK","Steam", 75000, 9000, 15000, 30, "Average", "Average", 1905, 1932),
    PACIFIC("PACIFIC","Steam", 119000, 21000, 62516, 95, "Above Average", "Good", 1908, 1935),
    CLASS_G10( "CLASS_G10","Steam", 98000, 38000, 50521, 50, "Average", "Good", 1910, 1935),
    PRAIRIE("PRAIRIE","Steam", 85000, 9000, 34157, 60, "Average", "Good", 1912, 1935),
    CLASS_XIII_H( "CLASS_XIII_H","Steam", 102000, 36000, 46289, 40, "Average", "Good", 1917, 1935),
    USRA_0_6_0("USRA_0_6_0","Steam", 90000, 13000, 29993, 40, "Average", "Good", 1918, 1935),
    MIKADO("MIKADO","Steam", 133000, 32000, 51072, 55, "Above Average", "Good", 1919, 1950),

    // Electric Locomotives
    BOBO("BOBO","Electric", 85000, 6000, 33116, 60, "Above Average", "Good", 1895, 1935),

    // Diesel Locomotives
    GP18("GP18","Diesel", 245000, 15000, 53878, 83, "Above Average", "Very Good", 1958, 1975),
    V200("V200","Diesel", 160000, 19000, 53878, 87, "Above Average", "Below Average", 1959, 1985),
    CLASS_55_DELTIC("CLASS_55_DELTIC","Diesel", 480000, 15000, 52155, 100, "Below Average", "Very Good", 1961, 1981),
    FP45("FP45","Diesel", 366000, 22000, 65740, 106, "Above Average", "Above Average", 1968, 9999),
    SD45("SD45","Diesel", 280000, 21000, 36009, 65, "Average", "Average", 1972, 1994),
    SDP40("SDP40","Diesel", 292000, 18000, 61966, 103, "Above Average", "Poor", 1973, 1989),
    DASH_9( "DASH_9","Diesel", 478000, 32000, 68410, 70, "Average", "Average", 1993, 9999),
    AMD_103( "AMD_103","Diesel", 425000, 25000, 88068, 103, "Very Fast", "Above Average", 1993, 9999),
    CLASS_232("CLASS_232","Diesel", 492000, 35000, 72625, 75, "Average", "Average", 1997, 9999);

    private final String name;
    private final String fuelType;
    private final LocomotiveType locomotiveType;
    private final int buildCost;
    private final int maintenanceCost;
    private final int fuelCost;
    private final int topSpeed;
    private final String acceleration;
    private final String reliability;
    private final int availableYear;
    private final int retiredYear;

    /**
     * Constructs a locomotive enumeration entry.
     *
     * @param name            the name identifier
     * @param fuelType        the fuel type (Steam, Diesel, Electric)
     * @param buildCost       the cost to build the locomotive
     * @param maintenanceCost the yearly maintenance cost
     * @param fuelCost        the cost per usage of fuel
     * @param topSpeed        the top speed of the locomotive
     * @param acceleration    qualitative acceleration rating
     * @param reliability     qualitative reliability rating
     * @param availableYear   the year this locomotive becomes available
     * @param retiredYear     the year this locomotive is retired
     */


    ListLocomotives(String name, String fuelType, int buildCost, int maintenanceCost, int fuelCost,
                    int topSpeed, String acceleration, String reliability,
                    int availableYear, int retiredYear) {
        this.name= name;
        this.fuelType = fuelType;
        this.locomotiveType = LocomotiveType.fromString(fuelType);
        this.buildCost = buildCost;
        this.maintenanceCost = maintenanceCost;
        this.fuelCost = fuelCost;
        this.topSpeed = topSpeed;
        this.acceleration = acceleration;
        this.reliability = reliability;
        this.availableYear = availableYear;
        this.retiredYear = retiredYear;
    }

    /** @return The name of the locomotive. */
    public String getName() { return name; }

    /** @return The string representation of the fuel type. */
    public String getFuelType() { return fuelType; }

    /** @return The enum {@link LocomotiveType} corresponding to the fuel type. */
    public LocomotiveType getLocomotiveType() { return locomotiveType; }

    /** @return The build cost of the locomotive. */
    public int getBuildCost() { return buildCost; }

    /** @return The yearly maintenance cost. */
    public int getMaintenanceCost() { return maintenanceCost; }

    /** @return The cost per fuel unit. */
    public int getFuelCost() { return fuelCost; }

    /** @return The top speed in km/h or equivalent units. */
    public int getTopSpeed() { return topSpeed; }

    /** @return The qualitative acceleration level. */
    public String getAcceleration() { return acceleration; }

    /** @return The qualitative reliability level. */
    public String getReliability() { return reliability; }

    /** @return The first year the locomotive is available. */
    public int getAvailableYear() { return availableYear; }

    /** @return The last year the locomotive can be used (inclusive). */
    public int getRetiredYear() { return retiredYear; }

    /**
     * Converts the enum data into a {@link Locomotive} instance.
     *
     * @return a new {@link Locomotive} with the attributes from this enum entry
     */
    public Locomotive toLocomotive() {
        return new Locomotive(
                this.name,
                this.fuelType,
                this.buildCost,
                this.maintenanceCost,
                this.topSpeed,
                this.acceleration,
                this.availableYear
        );
    }
}
