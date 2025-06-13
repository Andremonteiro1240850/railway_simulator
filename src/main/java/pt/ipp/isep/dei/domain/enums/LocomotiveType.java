package pt.ipp.isep.dei.domain.enums;


/**
 * Represents the type of locomotive based on its propulsion system
 */

public enum LocomotiveType {
    STEAM, DIESEL, ELECTRIC;

    /**
     * Converts a string to its corresponding {@code LocomotiveType}.
     *
     * @param type the string representation of the locomotive type
     * @return the matching {@code LocomotiveType}
     * @throws IllegalArgumentException if the type is null or doesn't match any known type
     */

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
