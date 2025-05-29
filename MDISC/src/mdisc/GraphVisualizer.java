package mdisc;


import mdisc.Line;
import mdisc.RailwayGraph;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Edge;

public class GraphVisualizer {
    public static void show(RailwayGraph graph) {
        System.setProperty("org.graphstream.ui", "swing");
        Graph g = new MultiGraph("Railway");

        for (int i = 0; i < graph.stationCount; i++) {
            String name = graph.stations[i].name;
            g.addNode(name).setAttribute("ui.label", name);
        }


        for (int i = 0; i < graph.lineCount; i++) {
            Line line = graph.lines[i];
            String id = "E" + i;
            Edge e = g.addEdge(id, line.a.name, line.b.name);
            e.setAttribute("ui.label", String.valueOf(line.distance));
            if (line.electrified) {
                e.setAttribute("ui.style", "fill-color: red;");
            }
        }

        g.display();
    }
}
