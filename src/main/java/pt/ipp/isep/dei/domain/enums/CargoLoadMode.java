package pt.ipp.isep.dei.domain.enums;

public enum CargoLoadMode {
    FULL,    // Train only departs when fully loaded
    HALF,    // Train departs when half loaded
    AVAILABLE // Train departs as soon as there is any cargo available
} 