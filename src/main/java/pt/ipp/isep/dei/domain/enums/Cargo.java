package pt.ipp.isep.dei.domain.enums;

/**
 * Represents the different types of cargo that can be transported in the simulation.
 * Each cargo type has a textual label for identification.
 */
public enum Cargo {
    COAL("coal"),
    IRON("iron"),
    BAUXITE("bauxite"),
    NITRATE("nitrate"),
    VEGETABLES("vegetables"),
    CEREALS("cereals"),
    WOOL("wool"),
    COFFEE("coffee"),
    RUBBER("rubber"),
    CATTLE("cattle"),
    STEEL("steel"),
    TEXTILES("textiles"),
    AUTOMOBILES("automobiles"),
    FOOD("food"),
    PASSENGERS("passengers"),
    MAIL("mail");

    /**
     * Textual label associated with the cargo type.
     */
    private final String label;

    /**
     * Constructs a Cargo enum constant with the specified label.
     *
     * @param label the string representation of the cargo type
     */
    Cargo(String label) {
        this.label = label;
    }

    /**
     * Returns the textual label of the cargo type.
     *
     * @return the cargo label
     */
    public String getLabel() {
        return label;
    }

    /**
     * Converts a string label to its corresponding {@code Cargo} enum constant.
     *
     * @param label the cargo label to convert
     * @return the matching {@code Cargo} enum
     * @throws IllegalArgumentException if the label is unknown
     */
    public static Cargo fromLabel(String label) {
        for (Cargo cargo : Cargo.values()) {
            if (cargo.label.equalsIgnoreCase(label)) {
                return cargo;
            }
        }
        throw new IllegalArgumentException("Unknown cargo label: " + label);
    }
}

