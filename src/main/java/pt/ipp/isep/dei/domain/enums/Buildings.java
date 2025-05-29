package pt.ipp.isep.dei.domain.enums;

public enum Buildings {
    TELEGRAPH("telegraph", null, 10000, "+10% receita de passageiros"),
    TELEPHONE("telephone", null, 15000, "+15% receita de passageiros"),
    CAFE_SMALL("cafe", "small", 8000, "+5% receita de passageiros"),
    CAFE_LARGE("cafe", "large", 12000, "+10% receita de passageiros"),
    CUSTOMS("customs", null, 12000, "+10% receita de mercadorias"),
    POST_OFFICE("post office", null, 10000, "+10% receita de correio"),
    HOTEL_SMALL("hotel", "small", 15000, "+10% receita de passageiros"),
    HOTEL_LARGE("hotel", "large", 25000, "+20% receita de passageiros"),
    SILO("silo", null, 10000, "+10% receita de cereais"),
    LIQUID_STORAGE("liquid storage", null, 10000, "+10% receita de líquidos");

    private final String type;
    private final String subType;
    private final int price;
    private final String bonus;

    Buildings(String type, String subType, int price, String bonus) {
        this.type = type;
        this.subType = subType;
        this.price = price;
        this.bonus = bonus;
    }

    public String getType() {
        return type;
    }

    public String getSubType() {
        return subType;
    }

    public int getPrice() {
        return price;
    }

    public String getBonus() {
        return bonus;
    }

    public boolean hasSubType() {
        return subType != null;
    }

    public static boolean isValidType(String type) {
        for (Buildings upgrade : values()) {
            if (upgrade.getType().equalsIgnoreCase(type)) return true;
        }
        return false;
    }

    public static boolean isValidSubType(String type, String subType) {
        for (Buildings upgrade : values()) {
            if (upgrade.getType().equalsIgnoreCase(type) &&
                    ((subType == null && upgrade.subType == null) ||
                            (subType != null && subType.equalsIgnoreCase(upgrade.subType)))) {
                return true;
            }
        }
        return false;
    }

    public static boolean acceptsSubTypes(String type) {
        for (Buildings upgrade : values()) {
            if (upgrade.getType().equalsIgnoreCase(type) && upgrade.subType != null) {
                return true;
            }
        }
        return false;
    }

    public static Buildings from(String type, String subType) {
        for (Buildings upgrade : values()) {
            if (upgrade.getType().equalsIgnoreCase(type) &&
                    ((subType == null && upgrade.subType == null) ||
                            (subType != null && subType.equalsIgnoreCase(upgrade.subType)))) {
                return upgrade;
            }
        }
        return null;
    }
}
