package pt.ipp.isep.dei.domain.enums;

/**
 * Enum representing different types of buildings that can be constructed in the game.
 * Each building has a type, optional subtype, price, and associated bonus.
 */
public enum Buildings {

    /** Telegraph building that increases passenger revenue by 10% */
    TELEGRAPH("telegraph", null, 10000, "+10% receita de passageiros"),
    
    /** Telephone building that increases passenger revenue by 15% */
    TELEPHONE("telephone", null, 15000, "+15% receita de passageiros"),
    
    /** Small cafe that increases passenger revenue by 5% */
    CAFE_SMALL("cafe", "small", 8000, "+5% receita de passageiros"),
    
    /** Large cafe that increases passenger revenue by 10% */
    CAFE_LARGE("cafe", "large", 12000, "+10% receita de passageiros"),
    
    /** Customs building that increases cargo revenue by 10% */
    CUSTOMS("customs", null, 12000, "+10% receita de mercadorias"),
    
    /** Post office that increases mail revenue by 10% */
    POST_OFFICE("post office", null, 10000, "+10% receita de correio"),
    
    /** Small hotel that increases passenger revenue by 10% */
    HOTEL_SMALL("hotel", "small", 15000, "+10% receita de passageiros"),
    
    /** Large hotel that increases passenger revenue by 20% */
    HOTEL_LARGE("hotel", "large", 25000, "+20% receita de passageiros"),
    
    /** Silo that increases grain revenue by 10% */
    SILO("silo", null, 10000, "+10% receita de cereais"),
    
    /** Liquid storage that increases liquid cargo revenue by 10% */
    LIQUID_STORAGE("liquid storage", null, 10000, "+10% receita de líquidos");

    private final String type;
    private final String subType;
    private final int price;
    private final String bonus;

    /**
     * Constructs a new building type with the specified properties.
     *
     * @param type the type of building
     * @param subType the subtype of building (can be null)
     * @param price the price of the building
     * @param bonus the revenue bonus description
     */
    Buildings(String type, String subType, int price, String bonus) {
        this.type = type;
        this.subType = subType;
        this.price = price;
        this.bonus = bonus;
    }

    /**
     * Gets the type of the building.
     *
     * @return the building type
     */
    public String getType() {
        return type;
    }

    /**
     * Gets the subtype of the building.
     *
     * @return the building subtype, or null if there is no subtype
     */
    public String getSubType() {
        return subType;
    }

    /**
     * Gets the price of the building.
     *
     * @return the building price
     */
    public int getPrice() {
        return price;
    }

    /**
     * Gets the bonus description of the building.
     *
     * @return the revenue bonus description
     */
    public String getBonus() {
        return bonus;
    }

    /**
     * Checks if the building has a subtype.
     *
     * @return true if the building has a subtype, false otherwise
     */
    public boolean hasSubType() {
        return subType != null;
    }

    /**
     * Validates if a given type is a valid building type.
     *
     * @param type the type to validate
     * @return true if the type is valid, false otherwise
     */
    public static boolean isValidType(String type) {
        for (Buildings upgrade : values()) {
            if (upgrade.getType().equalsIgnoreCase(type)) return true;
        }
        return false;
    }

    /**
     * Validates if a given type and subtype combination is valid.
     *
     * @param type the type to validate
     * @param subType the subtype to validate
     * @return true if the type and subtype combination is valid, false otherwise
     */
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

    /**
     * Checks if a given building type accepts subtypes.
     *
     * @param type the type to check
     * @return true if the type accepts subtypes, false otherwise
     */
    public static boolean acceptsSubTypes(String type) {
        for (Buildings upgrade : values()) {
            if (upgrade.getType().equalsIgnoreCase(type) && upgrade.subType != null) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets the Buildings enum value for a given type and subtype combination.
     *
     * @param type the type to look up
     * @param subType the subtype to look up
     * @return the matching Buildings enum value, or null if no match is found
     */
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
