package pt.ipp.isep.dei.domain.enums;

import pt.ipp.isep.dei.domain.Carriage;

public enum CarriageList {

    // Passageiros e Correio
    PASSENGER_CAR("Passenger Car", "PASSENGERS", 35),
    MAIL_CAR("Mail Car", "MAIL", 25),

    // Alimentares e produtos transformados
    REFRIGERATED_CAR("Refrigerated Car", "FOOD", 40),
    BOXCAR("Boxcar", "TEXTILES", 50),
    AUTOMOBILE_CAR("Automobile Car", "CARS", 30),
    BAKERY_SUPPLY_CAR("Bakery Supply Car", "FOOD", 35),

    // Minérios
    COAL_HOPPER("Coal Hopper", "COAL", 60),
    IRON_ORE_HOPPER("Iron Ore Hopper", "IRON_ORE", 60),
    BAUXITE_HOPPER("Bauxite Hopper", "BAUXITE", 60),
    NITRATE_TANKER("Nitrate Tanker", "NITRATE", 45),

    // Agricultura
    CATTLE_CAR("Cattle Car", "CATTLE", 30),
    GRAIN_HOPPER("Grain Hopper", "CEREALS", 55),
    VEGETABLE_CAR("Vegetable Car", "VEGETABLES", 45),
    WOOL_CAR("Wool Car", "WOOL", 40),
    COFFEE_CAR("Coffee Car", "COFFEE", 40),
    RUBBER_CAR("Rubber Car", "RUBBER", 40),

    // Indústria pesada
    STEEL_CAR("Steel Car", "STEEL", 50); // <- ÚLTIMA linha termina com ponto e vírgula

    private final String name;
    private final String cargoType;
    private final int capacity;

    CarriageList(String name, String cargoType, int capacity) {
        this.name = name;
        this.cargoType = cargoType;
        this.capacity = capacity;
    }

    public String getName() {
        return name;
    }

    public String getCargoType() {
        return cargoType;
    }

    public int getCapacity() {
        return capacity;
    }

    public Carriage toCarriage() {
        return new Carriage(name, cargoType, capacity);
    }
}
