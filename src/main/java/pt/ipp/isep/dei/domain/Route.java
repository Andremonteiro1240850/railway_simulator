package pt.ipp.isep.dei.domain;

import java.util.ArrayList;
import java.util.List;

public class Route {
    private final List<Station> stations;
    private final Locomotive locomotive;
    private final List<String> cargosToTransport;
    /**
     * Constructs a new Route with the specified stations, locomotive, and cargo list.
     *
     * @param stations The list of stations along this route (in order of visitation)
     * @param locomotive The locomotive assigned to this route
     * @param cargosToTransport The list of cargo types to be transported on this route
     * @throws IllegalArgumentException if stations list is null or empty, locomotive is null,
     *         or cargosToTransport is null
     */

    public Route(List<Station> stations, Locomotive locomotive, List<String> cargosToTransport) {
        this.stations = stations;
        this.locomotive = locomotive;
        this.cargosToTransport = cargosToTransport;
    }
    /**
     * Gets an unmodifiable list of stations along this route.
     *
     * @return The list of stations in traversal order
     */

    public List<Station> getStations() {
        return stations;
    }
    /**
     * Gets the locomotive assigned to this route.
     *
     * @return The locomotive for this route
     */

    public Locomotive getLocomotive() {
        return locomotive;
    }
    /**
     * Gets the list of cargo types to be transported on this route.
     *
     * @return The list of cargo types
     */

    public List<String> getCargosToTransport() {
        return cargosToTransport;
    }
    /**
     * Creates and returns a deep copy of this Route.
     * The cloned route will contain copies of the station list, cargo list, and locomotive.
     *
     * @return A new Route instance with the same properties as this route
     */

    public Route clone() {
        return new Route(
                new ArrayList<>(this.stations),
                this.locomotive.clone(),
                new ArrayList<>(this.cargosToTransport)
        );
    }
}