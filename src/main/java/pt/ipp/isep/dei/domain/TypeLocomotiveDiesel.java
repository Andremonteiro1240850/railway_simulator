package pt.ipp.isep.dei.domain;

import pt.ipp.isep.dei.domain.enums.LocomotiveType;
import java.io.Serializable;

/**
 * Concrete class representing a diesel locomotive
 */
public class TypeLocomotiveDiesel extends TypeLocomotive implements Serializable {
    private static final long serialVersionUID = 1L;
    
    public TypeLocomotiveDiesel() {
        super(LocomotiveType.DIESEL);
    }
} 