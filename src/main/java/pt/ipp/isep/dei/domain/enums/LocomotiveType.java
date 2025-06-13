package pt.ipp.isep.dei.domain.enums;

public enum LocomotiveType {
    STEAM, DIESEL, ELECTRIC;

    public static LocomotiveType fromString(String type) {
        if (type == null) throw new IllegalArgumentException("Type cannot be null");
        switch (type.trim().toLowerCase()) {
            case "steam": return STEAM;
            case "diesel": return DIESEL;
            case "electric": return ELECTRIC;
            default: throw new IllegalArgumentException("Unknown type: " + type);
        }
    }
}
