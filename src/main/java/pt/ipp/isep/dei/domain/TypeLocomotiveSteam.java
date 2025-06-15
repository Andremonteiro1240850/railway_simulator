package pt.ipp.isep.dei.domain;

import pt.ipp.isep.dei.domain.enums.LocomotiveType;
import java.io.Serializable;

/**
 * Concrete class representing a steam locomotive
 */
public class TypeLocomotiveSteam extends TypeLocomotive implements Serializable {
    private static final long serialVersionUID = 1L;
    
    public TypeLocomotiveSteam() {
        super(LocomotiveType.STEAM);
    }
} 