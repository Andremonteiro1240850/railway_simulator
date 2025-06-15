package pt.ipp.isep.dei.domain;

import pt.ipp.isep.dei.domain.enums.LocomotiveType;

/**
 * Concrete class representing a steam locomotive
 */
public class TypeLocomotiveSteam extends TypeLocomotive {
    
    public TypeLocomotiveSteam() {
        super(LocomotiveType.STEAM);
    }
} 