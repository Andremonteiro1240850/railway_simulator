package mdisc;

/**
 * Represents a graph of railway stations and connecting lines.
 * Provides methods to manage stations and lines, verify connectivity,
 * generate a connectivity matrix, and compute an Eulerian path.
 */
public class RailwayGraph {

    /**
     * Array of stations in the graph (maximum 100).
     */
    public Station[] stations = new Station[100];

    /**
     * Array of lines connecting stations (maximum 200).
     */
    public Line[] lines = new Line[200];

    /**
     * The current number of stations in the graph.
     */
    public int stationCount = 0;

    /**
     * The current number of lines in the graph.
     */
    public int lineCount = 0;

    /**
     * Adds a new line between two stations. If the stations do not exist, they are created.
     *
     * @param nameA        Name of the first station.
     * @param nameB        Name of the second station.
     * @param electrified  Whether the line is electrified.
     * @param dist         The distance between the stations.
     */
    public void addLine(String nameA, String nameB, boolean electrified, double dist) {
        Station a = findOrCreateStation(nameA);
        Station b = findOrCreateStation(nameB);
        Line line = new Line(a, b, electrified, dist);
        lines[lineCount++] = line;
    }

    /**
     * Finds a station by name or creates a new one if it doesn't exist.
     *
     * @param name  Name of the station.
     * @return The found or newly created station.
     */
    private Station findOrCreateStation(String name) {
        for (int i = 0; i < stationCount; i++) {
            if (stations[i].name.equals(name)) {
                return stations[i];
            }
        }
        Station newStation = new Station(name);
        stations[stationCount++] = newStation;
        return newStation;
    }

    /**
     * Verifies if a train of a certain type can travel from one station to another.
     *
     * @param from   Name of the departure station.
     * @param to     Name of the destination station.
     * @param train  Type of train to consider (e.g., ELECTRIC).
     * @return True if travel is possible, false otherwise.
     */
    public boolean canTravel(String from, String to, TrainType train) {
        int[][] adj = new int[stationCount][stationCount];

        for (int i = 0; i < lineCount; i++) {
            Line l = lines[i];
            if (train == TrainType.ELECTRIC && !l.electrified) continue;
            int idxA = indexOf(l.a.name);
            int idxB = indexOf(l.b.name);
            adj[idxA][idxB] = 1;
            adj[idxB][idxA] = 1;
        }

        int idxFrom = indexOf(from);
        int idxTo = indexOf(to);
        if (idxFrom == -1 || idxTo == -1) return false;

        boolean[] visited = new boolean[stationCount];
        return dfs(idxFrom, idxTo, adj, visited);
    }

    /**
     * Depth-First Search helper for checking connectivity.
     *
     * @param current  Current station index.
     * @param target   Destination station index.
     * @param adj      Adjacency matrix.
     * @param visited  Visited flags.
     * @return True if a path exists, false otherwise.
     */
    private boolean dfs(int current, int target, int[][] adj, boolean[] visited) {
        if (current == target) return true;
        visited[current] = true;
        for (int i = 0; i < stationCount; i++) {
            if (adj[current][i] == 1 && !visited[i]) {
                if (dfs(i, target, adj, visited)) return true;
            }
        }
        return false;
    }

    /**
     * Gets the index of a station by name.
     *
     * @param name  Name of the station.
     * @return Index of the station or -1 if not found.
     */
    private int indexOf(String name) {
        for (int i = 0; i < stationCount; i++) {
            if (stations[i].name.equals(name)) return i;
        }
        return -1;
    }

    /**
     * Displays the connectivity matrix for a given train type.
     *
     * @param train  Type of train (e.g., ELECTRIC).
     */
    public void showConnectivityMatrix(TrainType train) {
        int[][] adj = new int[stationCount][stationCount];

        // Construção da matriz de adjacência
        for (int i = 0; i < lineCount; i++) {
            Line l = lines[i];
            if (train == TrainType.ELECTRIC && !l.electrified) continue;
            int idxA = indexOf(l.a.name);
            int idxB = indexOf(l.b.name);
            adj[idxA][idxB] = 1;
            adj[idxB][idxA] = 1;
        }

        // Calcular o comprimento máximo dos nomes das estações válidas
        int maxNameLength = 0;
        for (Station station : stations) {
            if (station != null) {
                maxNameLength = Math.max(maxNameLength, station.name.length());
            }
        }
        int columnWidth = maxNameLength + 2;

        // Cabeçalho
        System.out.println("\nConnectivity Matrix (TrainType: " + train + "):");
        System.out.printf("%" + columnWidth + "s", "");
        for (int i = 0; i < stationCount; i++) {
            if (stations[i] != null) {
                System.out.printf("%" + columnWidth + "s", stations[i].name);
            } else {
                System.out.printf("%" + columnWidth + "s", "null");
            }
        }
        System.out.println();

        // Linhas da matriz
        for (int i = 0; i < stationCount; i++) {
            if (stations[i] != null) {
                System.out.printf("%" + columnWidth + "s", stations[i].name);
            } else {
                System.out.printf("%" + columnWidth + "s", "null");
            }

            for (int j = 0; j < stationCount; j++) {
                System.out.printf("%" + columnWidth + "d", adj[i][j]);
            }
            System.out.println();
        }
    }

    /**
     * Finds an Eulerian path using Hierholzer’s algorithm.
     *
     * @param onlyElectrified  If true, considers only electrified lines.
     * @return Array of lines representing the Eulerian path, or null if not possible.
     */
    public Line[] findEulerianPath(boolean onlyElectrified) {
        Line[] path = new Line[lines.length];
        int pathIndex = 0;

        int[][] graph = new int[stationCount][stationCount];
        for (int i = 0; i < lineCount; i++) {
            if (onlyElectrified && !lines[i].electrified) continue;
            int a = indexOf(lines[i].a.name);
            int b = indexOf(lines[i].b.name);
            graph[a][b]++;
            graph[b][a]++;
        }

        int start = -1, odd = 0;
        for (int i = 0; i < stationCount; i++) {
            int degree = 0;
            for (int j = 0; j < stationCount; j++) degree += graph[i][j];
            if (degree % 2 != 0) odd++;
            if (degree > 0 && start == -1) start = i;
        }
        if (odd != 0 && odd != 2) return null;

        int[] stack = new int[lines.length];
        int[] circuit = new int[lines.length * 2];
        int stackSize = 0, circuitSize = 0;

        stack[stackSize++] = start;

        while (stackSize > 0) {
            int u = stack[stackSize - 1];
            int v = -1;
            for (int i = 0; i < stationCount; i++) {
                if (graph[u][i] > 0) {
                    v = i;
                    break;
                }
            }
            if (v == -1) {
                circuit[circuitSize++] = u;
                stackSize--;
            } else {
                stack[stackSize++] = v;
                graph[u][v]--;
                graph[v][u]--;
            }
        }

        for (int i = 0; i < circuitSize - 1; i++) {
            int u = circuit[i];
            int v = circuit[i + 1];
            for (int j = 0; j < lineCount; j++) {
                int a = indexOf(lines[j].a.name);
                int b = indexOf(lines[j].b.name);
                if (((a == u && b == v) || (a == v && b == u)) &&
                        (!onlyElectrified || lines[j].electrified)) {
                    path[pathIndex++] = lines[j];
                    break;
                }
            }
        }

        Line[] finalPath = new Line[pathIndex];
        for (int i = 0; i < pathIndex; i++) {
            finalPath[i] = path[i];
        }
        return finalPath;
    }
}
