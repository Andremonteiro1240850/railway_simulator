package pt.ipp.isep.dei.domain;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Route {
    List<PointsOfRoute> route;
    Train train;

    public Route( Train train) {
        this.train = train;
        List<PointsOfRoute> route = new ArrayList<>();
    }

    public List<PointsOfRoute> getRoute() {return route;}
    public void AddRoute(PointsOfRoute route) { this.route.add(route);}
    public Train getTrain() {return train;}
    public void setTrain(Train train) {this.train = train;}

}