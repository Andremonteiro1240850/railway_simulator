package pt.ipp.isep.dei.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Station {
    private String name;
    private String type; // "depot", "terminal", "station"
    private String region;
    private Employee employee;
    private String reference;  // Novo campo adicionado
    private List<StationUpgrade> upgrades = new ArrayList<>();

    // Construtor modificado para incluir reference
    public Station(String reference, String name, String type, String region) {
        this.reference = reference;
        this.name = name;
        this.type = type;
        this.region = region;
        validateTypeAndRegion(type, region);
    }

    // Construtor antigo mantido para compatibilidade
    public Station(String name, String type, String region) {
        this(generateDefaultReference(), name, type, region);
    }

    private static String generateDefaultReference() {
        return "ST-" + System.currentTimeMillis(); // Gera uma referência única
    }

    private void validateTypeAndRegion(String type, String region) {
        // ... (mantido igual)
    }

    // Getters
    public String getReference() {
        return reference;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getRegion() {
        return region;
    }

    public Employee getEmployee() {
        return employee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Station)) return false;
        Station station = (Station) o;
        return reference.equals(station.reference); // Agora compara por reference
    }

    @Override
    public int hashCode() {
        return Objects.hash(reference); // Agora usa reference
    }

    public Station clone() {
        Station clone = new Station(this.reference, this.name, this.type, this.region);
        clone.employee = this.employee != null ? this.employee.clone() : null;
        clone.upgrades = new ArrayList<>(this.upgrades);
        return clone;
    }

    public void addUpgrade(StationUpgrade upgrade) {
        this.upgrades.add(upgrade);
    }

    public List<StationUpgrade> getUpgrades() {
        return new ArrayList<>(upgrades);
    }
}