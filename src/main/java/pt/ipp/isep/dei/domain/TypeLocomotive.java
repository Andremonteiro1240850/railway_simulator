package pt.ipp.isep.dei.domain;

import pt.ipp.isep.dei.domain.enums.LocomotiveType;

/**
 * Abstract base class for different types of locomotives
 */
public abstract class TypeLocomotive {
    protected final LocomotiveType type;

    protected TypeLocomotive(LocomotiveType type) {
        this.type = type;
    }

    public LocomotiveType getType() {
        return type;
    }

    @Override
    public String toString() {
        return type.toString();
    }

    /**
     * Factory method to create a TypeLocomotive instance from a LocomotiveType
     * @param type the LocomotiveType enum value
     * @return a new instance of the appropriate TypeLocomotive subclass
     */
    public static TypeLocomotive fromLocomotiveType(LocomotiveType type) {
        return switch (type) {
            case STEAM -> new TypeLocomotiveSteam();
            case DIESEL -> new TypeLocomotiveDiesel();
            case ELECTRIC -> new TypeLocomotiveElectric();
        };
    }
} 