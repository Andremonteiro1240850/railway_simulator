package pt.ipp.isep.dei.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * Represents a route assigned to a train, composed of a list of route points (stations and cargo modes).
 */
public class Route implements Serializable {

    private static final long serialVersionUID = 1L;
    private List<PointsOfRoute> route;
    private Train train;

    /**
     * Constructs a new Route for a given train with an initial list of route points.
     *
     * @param train the train assigned to this route
     * @param route the list of points that define the route
     * @throws NullPointerException if train or route is null
     */
    public Route(Train train, List<PointsOfRoute> route) {
        if (train == null) {
            throw new NullPointerException("Train cannot be null");
        }
        if (route == null) {
            throw new NullPointerException("Route points list cannot be null");
        }
        this.train = train;
        this.route = route;
    }

    /**
     * Returns the list of route points.
     *
     * @return the route as a list of {@link PointsOfRoute}
     */
    public List<PointsOfRoute> getRoute() {
        return route;
    }

    /**
     * Adds a new point to the route.
     *
     * @param route the point of route to add
     * @throws NullPointerException if route point is null
     */
    public void AddRoute(PointsOfRoute route) {
        if (route == null) {
            throw new NullPointerException("Route point cannot be null");
        }
        this.route.add(route);
    }

    /**
     * Returns the train associated with this route.
     *
     * @return the train
     */
    public Train getTrain() {
        return train;
    }

    /**
     * Sets the train associated with this route.
     *
     * @param train the train to assign
     * @throws NullPointerException if train is null
     */
    public void setTrain(Train train) {
        if (train == null) {
            throw new NullPointerException("Train cannot be null");
        }
        this.train = train;
    }

    /**
     * Returns a string representation of the route, including the train and all route points.
     *
     * @return a formatted string describing the route
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Train: ").append(train).append("\n");
        sb.append("Route Points:\n");
        for (PointsOfRoute point : route) {
            sb.append(" - ").append(point).append("\n");
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Route)) return false;
        Route route1 = (Route) o;
        return Objects.equals(route, route1.route) &&
               Objects.equals(train, route1.train);
    }

    @Override
    public int hashCode() {
        return Objects.hash(route, train);
    }

    /**
     * Returns a name for this route based on its stations.
     * The name is in the format "Station1 - Station2 - ... - StationN"
     *
     * @return a string representing the route name
     */
    public String getName() {
        if (route == null || route.isEmpty()) {
            return "Empty Route";
        }
        
        StringBuilder name = new StringBuilder();
        for (int i = 0; i < route.size(); i++) {
            if (i > 0) {
                name.append(" - ");
            }
            name.append(route.get(i).getStations().getName());
        }
        return name.toString();
    }
}

