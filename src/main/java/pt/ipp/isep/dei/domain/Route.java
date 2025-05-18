package pt.ipp.isep.dei.domain;

import java.util.ArrayList;
import java.util.List;

public class Route {
    private final List<Station> stations;
    private final Locomotive locomotive;
    private final List<String> cargosToTransport;

    public Route(List<Station> stations, Locomotive locomotive, List<String> cargosToTransport) {
        this.stations = stations;
        this.locomotive = locomotive;
        this.cargosToTransport = cargosToTransport;
    }

    public List<Station> getStations() {
        return stations;
    }

    public Locomotive getLocomotive() {
        return locomotive;
    }

    public List<String> getCargosToTransport() {
        return cargosToTransport;
    }

    public Route clone() {
        return new Route(
                new ArrayList<>(this.stations),
                this.locomotive.clone(),
                new ArrayList<>(this.cargosToTransport)
        );
    }
}