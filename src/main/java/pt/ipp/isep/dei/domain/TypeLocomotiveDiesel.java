package pt.ipp.isep.dei.domain;

import pt.ipp.isep.dei.domain.enums.LocomotiveType;

/**
 * Concrete class representing a diesel locomotive
 */
public class TypeLocomotiveDiesel extends TypeLocomotive {
    
    public TypeLocomotiveDiesel() {
        super(LocomotiveType.DIESEL);
    }
} 