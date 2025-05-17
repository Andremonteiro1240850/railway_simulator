package pt.ipp.isep.dei.domain;

import java.util.Objects;

public class Station {
    private String name;
    private String type; // "depot", "terminal", "station"
    private String region;
    private Employee employee;
    // Apenas se type = "station" (NE, NW, SE, SW)

    public Station(String name, String type, String region) {
        this.name = name;
        this.type = type;
        this.region = region;
        validateTypeAndRegion(type, region); // Valida o tipo e a região (se aplicável)
    }

    private void validateTypeAndRegion(String type, String region) {
        if (type == null || (!type.equals("depot") && !type.equals("terminal") && !type.equals("station"))) {
            throw new IllegalArgumentException("Invalid station type. Must be 'depot', 'terminal', or 'station'.");
        }

        if (type.equals("station") && (region == null || !region.matches("NE|NW|SE|SW"))) {
            throw new IllegalArgumentException("Station type 'station' must have a valid region (NE, NW, SE, SW).");
        }

        if (!type.equals("station") && region != null) {
            throw new IllegalArgumentException("Only 'station' type can have a region.");
        }
    }

    // Getters
    public String getType() {
        return type;
    }

    public String getRegion() {
        return region;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Station)) return false;
        Station station = (Station) o;
        return  employee.equals(station.employee);
    }

    @Override
    public int hashCode() {
        return Objects.hash( employee);
    }

    public Station clone() {
        return new Station(
                this.name,
                this.type,
                this.region
        );
    }
}