package pt.ipp.isep.dei.domain;

import pt.ipp.isep.dei.domain.enums.LocomotiveType;

/**
 * Concrete class representing an electric locomotive
 */
public class TypeLocomotiveElectric extends TypeLocomotive {
    
    public TypeLocomotiveElectric() {
        super(LocomotiveType.ELECTRIC);
    }
} 