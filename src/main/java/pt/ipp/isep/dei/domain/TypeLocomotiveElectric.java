package pt.ipp.isep.dei.domain;

import pt.ipp.isep.dei.domain.enums.LocomotiveType;
import java.io.Serializable;

/**
 * Concrete class representing an electric locomotive
 */
public class TypeLocomotiveElectric extends TypeLocomotive implements Serializable {
    private static final long serialVersionUID = 1L;
    
    public TypeLocomotiveElectric() {
        super(LocomotiveType.ELECTRIC);
    }
} 