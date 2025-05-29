package mdisc;

public enum StationType {
    DEPOT, STATION, TERMINAL;

    public static StationType fromName(String name) {
        if (name == null || name.length() < 2) return STATION;
        char prefix = name.charAt(0);
        if (prefix == 'D') return DEPOT;
        if (prefix == 'T') return TERMINAL;
        return STATION;
    }
}
