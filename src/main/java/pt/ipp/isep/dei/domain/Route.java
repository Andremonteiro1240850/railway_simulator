package pt.ipp.isep.dei.domain;

import java.util.List;

public class Route {
    List<PointsOfRoute> route;
    Train train;

    public Route(Train train, List<PointsOfRoute> route) {
        this.train = train;
        this.route = route;
    }

    public List<PointsOfRoute> getRoute() {
        return route;
    }

    public void AddRoute(PointsOfRoute route) {
        this.route.add(route);
    }

    public Train getTrain() {
        return train;
    }

    public void setTrain(Train train) {
        this.train = train;
    }

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
}
