package pt.ipp.isep.dei.domain.enums;

public enum StationUpgrade {
    TELEGRAPH("Posto Telegráfico", 1830, 10000, "TELEPHONE", null),
    TELEPHONE("Telefone", 1860, 25000, null, "TELEGRAPH"),
    RESTAURANT_SMALL("Restaurante Pequeno", 1850, 5000, null, null),
    RESTAURANT_LARGE("Restaurante Grande", 1850, 20000, "HOTEL_LARGE", null),
    SILO("Silo", 1870, 15000, "LIQUID_STORAGE", null),
    HOTEL_SMALL("Hotel Pequeno", 1880, 10000, null, null),
    HOTEL_LARGE("Hotel Grande", 1880, 30000, "RESTAURANT_LARGE", null),
    CUSTOMS("Alfândega", 1875, 20000, null, null),
    LIQUID_STORAGE("Armazenamento de Líquidos", 1890, 20000, "SILO", null);

    private final String displayName;
    private final int year;
    private final int cost;
    private final String exclusiveWith;
    private final String replaces;

    StationUpgrade(String displayName, int year, int cost, String exclusiveWith, String replaces) {
        this.displayName = displayName;
        this.year = year;
        this.cost = cost;
        this.exclusiveWith = exclusiveWith;
        this.replaces = replaces;
    }

    public String getDisplayName() { return displayName; }
    public int getYear() { return year; }
    public int getCost() { return cost; }
    public String getExclusiveWith() { return exclusiveWith; }
    public String getReplaces() { return replaces; }
} 