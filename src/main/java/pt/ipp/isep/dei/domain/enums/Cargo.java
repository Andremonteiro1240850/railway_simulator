package pt.ipp.isep.dei.domain.enums;

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

    private final String label;

    Cargo(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public static Cargo fromLabel(String label) {
        for (Cargo cargo : Cargo.values()) {
            if (cargo.label.equalsIgnoreCase(label)) {
                return cargo;
            }
        }
        throw new IllegalArgumentException("Unknown cargo label: " + label);
    }
}
