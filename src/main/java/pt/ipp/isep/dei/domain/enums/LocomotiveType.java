package pt.ipp.isep.dei.domain.enums;

public enum LocomotiveType {
    STEAM, DIESEL, ELECTRIC;

    public static LocomotiveType fromString(String type) {
        return switch (type.toLowerCase()) {
            case "steam" -> STEAM;
            case "diesel" -> DIESEL;
            case "electric" -> ELECTRIC;
            default -> throw new IllegalArgumentException("Unknown type: " + type);
        };
    }
}
