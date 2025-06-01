package pt.ipp.isep.dei.domain.enums;

public enum Regions {
    NE("ne"), NW("nw"), SE("se"), SW("sw");
    private final String region;
    Regions(String region) {
        this.region = region;
    }
    public String getRegion() {return region;}

}
