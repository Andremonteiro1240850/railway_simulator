package mdisc;

import mdisc.RailwayGraph;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Utility class for reading a railway graph from a CSV file.
 * <p>
 * Expected CSV format (one line per railway connection):
 * <pre>
 * StationA;StationB;Electrified;Distance
 * </pre>
 * Where:
 * <ul>
 *   <li><b>StationA</b> and <b>StationB</b> are the names of the connected stations</li>
 *   <li><b>Electrified</b> is "1" for true, anything else for false</li>
 *   <li><b>Distance</b> is a number representing the length of the line (in km)</li>
 * </ul>
 */
public class CsvReader {

    /**
     * Loads a {@link RailwayGraph} from a CSV file located at the given path.
     *
     * @param path The path to the CSV file.
     * @return A populated {@link RailwayGraph} object based on the file contents.
     */
    public static RailwayGraph loadGraph(String path) {
        RailwayGraph graph = new RailwayGraph();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                graph.addLine(
                        parts[0],
                        parts[1],
                        parts[2].trim().equals("1"),
                        Double.parseDouble(parts[3])
                );
            }
        } catch (Exception e) {
            System.out.println("❌ Error reading CSV: " + e.getMessage());
            return null;
        }
        return graph;
    }
}