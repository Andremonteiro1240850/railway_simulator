package pt.ipp.isep.dei.domain.enums;

import pt.ipp.isep.dei.domain.Carriage;

/**
 * Enum representing predefined types of railway carriages with associated properties.
 * Each entry includes a name, the cargo type it carries, and its capacity.
 */
public enum CarriageList {

    PASSENGER_CAR("Passenger Car", "passengers", 35),
    MAIL_CAR("Mail Car", "mail", 25),
    REFRIGERATED_CAR("Refrigerated Car", "food", 40),
    BOXCAR("Boxcar", "textiles", 50),
    AUTOMOBILE_CAR("Automobile Car", "automobiles", 30),
    BAKERY_SUPPLY_CAR("Bakery Supply Car", "food", 35),
    COAL_HOPPER("Coal Hopper", "coal", 60),
    IRON_ORE_HOPPER("Iron Ore Hopper", "iron", 60),
    BAUXITE_HOPPER("Bauxite Hopper", "bauxite", 60),
    NITRATE_TANKER("Nitrate Tanker", "nitrate", 45),
    CATTLE_CAR("Cattle Car", "cattle", 30),
    GRAIN_HOPPER("Grain Hopper", "cereals", 55),
    VEGETABLE_CAR("Vegetable Car", "vegetables", 45),
    WOOL_CAR("Wool Car", "wool", 40),
    COFFEE_CAR("Coffee Car", "coffee", 40),
    RUBBER_CAR("Rubber Car", "rubber", 40),
    STEEL_CAR("Steel Car", "steel", 50);

    private final String name;
    private final String cargoType;
    private final int capacity;

    /**
     * Constructs a CarriageList entry.
     *
     * @param name       the name of the carriage
     * @param cargoType  the type of cargo it carries
     * @param capacity   the capacity of the carriage
     */
    CarriageList(String name, String cargoType, int capacity) {
        this.name = name;
        this.cargoType = cargoType;
        this.capacity = capacity;
    }

    /**
     * Returns the name of the carriage.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the cargo type of the carriage.
     *
     * @return the cargo type
     */
    public String getCargoType() {
        return cargoType;
    }

    /**
     * Returns the capacity of the carriage.
     *
     * @return the capacity
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Converts this enum constant to a {@link Carriage} object.
     *
     * @return a new {@code Carriage} instance
     */
    public Carriage toCarriage() {
        return new Carriage(name, cargoType, (double) capacity, 0.0);
    }
}
