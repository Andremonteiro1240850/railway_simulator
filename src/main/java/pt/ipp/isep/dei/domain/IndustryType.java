package pt.ipp.isep.dei.domain;

/**
 * Represents the types of industries available in the system.
 */
public enum IndustryType {
    PRIMARY,       // Industries that generate resources (mines, farms)
    TRANSFORMING,  // Industries that transform resources (steel mills, bakeries)
    MIXED          // Industries that can do both (ports)
}