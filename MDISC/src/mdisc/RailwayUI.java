package mdisc;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Console-based user interface for interacting with the Railway Graph.
 */
public class RailwayUI {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Load railway graph from CSV
        System.out.println("Enter path to CSV file with lines:");
        String path = sc.nextLine();
        RailwayGraph graph = CsvReader.loadGraph(path);

        // Visualize the graph
        GraphVisualizer.show(graph); // mantém a janela do grafo aberta

        boolean running = true;
        boolean us13Done = false;
        boolean us14Done = false;

        while (running) {
            System.out.println("\n--- Menu ---");
            System.out.println("1. Connectivity Check (US13)");
            System.out.println("2. Maintenance Route (US14)");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            String option = sc.nextLine().trim();

            switch (option) {
                case "1":
                    doUS13(sc, graph);
                    us13Done = true;
                    break;
                case "2":
                    doUS14(sc, graph);
                    us14Done = true;
                    break;
                case "3":
                    running = false;
                    continue;
                default:
                    System.out.println("❌ Invalid option! Choose again.");
                    continue;
            }

            // Se as duas US já foram feitas, mostra mensagem e encerra imediatamente
            if (us13Done && us14Done) {
                System.out.println("\n🎉 Both US13 (Connectivity Check) and US14 (Maintenance Route) have been completed successfully!");
                System.out.println("Exiting the program. Thank you!");
                running = false;
                break; // sai do loop
            }

            // Se fez US13 mas não fez US14, oferece fazer US14 ou sair
            if (us13Done && !us14Done) {
                System.out.println("\nWhat do you want to do next?");
                System.out.println("1 - Do US14 (Maintenance Route)");
                System.out.println("2 - Exit");
                System.out.print("Your choice: ");
                String nextChoice = sc.nextLine().trim();
                if (nextChoice.equals("1")) {
                    doUS14(sc, graph);
                    us14Done = true;

                    // Verifica novamente se agora as duas foram feitas e encerra direto
                    if (us13Done && us14Done) {
                        System.out.println("\n🎉 Both US13 (Connectivity Check) and US14 (Maintenance Route) have been completed successfully!");
                        System.out.println("Exiting the program. Thank you!");
                        running = false;
                    }
                } else {
                    running = false;
                }
            }

            // Se fez US14 mas não fez US13, oferece fazer US13 ou sair
            else if (us14Done && !us13Done) {
                System.out.println("\nWhat do you want to do next?");
                System.out.println("1 - Do US13 (Connectivity Check)");
                System.out.println("2 - Exit");
                System.out.print("Your choice: ");
                String nextChoice = sc.nextLine().trim();
                if (nextChoice.equals("1")) {
                    doUS13(sc, graph);
                    us13Done = true;

                    // Verifica novamente se agora as duas foram feitas e encerra direto
                    if (us13Done && us14Done) {
                        System.out.println("\n🎉 Both US13 (Connectivity Check) and US14 (Maintenance Route) have been completed successfully!");
                        System.out.println("Exiting the program. Thank you!");
                        running = false;
                    }
                } else {
                    running = false;
                }
            }
        }

        sc.close();
    }

    private static void doUS13(Scanner sc, RailwayGraph graph) {
        System.out.println("\n--- US13: Connectivity Check ---");
        TrainType train = null;
        while (train == null) {
            try {
                System.out.println("Train type (STEAM, DIESEL, ELECTRIC):");
                train = TrainType.valueOf(sc.nextLine().trim().toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("❌ Invalid type. Choose again.");
            }
        }

        System.out.println("Origin Station:");
        String from = sc.nextLine();

        System.out.println("Target Station:");
        String to = sc.nextLine();

        if (graph.canTravel(from, to, train)) {
            System.out.println("✅ The train can travel between the chosen stations.");
        } else {
            System.out.println("❌ The train cannot travel between the chosen stations.");
        }

        graph.showConnectivityMatrix(train);
    }

    private static void doUS14(Scanner sc, RailwayGraph graph) {
        System.out.println("\n--- US14: Maintenance Route ---");
        System.out.println("Do you want to go through all the lines? (yes/no):");
        boolean allLines = sc.nextLine().trim().equalsIgnoreCase("yes");

        Line[] route = graph.findEulerianPath(!allLines);

        if (route == null) {
            System.out.println("⚠ Não é possível gerar um caminho de Euler.");
            System.out.println("Um caminho de Euler passa por todas as linhas ferroviárias (arestas) exatamente uma vez. \n" +
                    "Se todas as estações (vértices) têm grau par, existe um circuito de Euler (começa e termina na mesma estação).\n" +
                    "Se apenas duas estações têm grau ímpar, existe um caminho de Euler, mas começa e termina em estações diferentes.\n" +
                    "Se houver mais de duas estações com grau ímpar, não é possível fazer esse tal caminho.\n");
            return;
        }

        // Reorganizar a rota para seguir um percurso contínuo
        List<Line> ordered = new ArrayList<>();
        Set<Line> used = new HashSet<>(Arrays.asList(route));

        Map<Station, List<Line>> adj = new HashMap<>();
        for (Line l : route) {
            adj.computeIfAbsent(l.a, k -> new ArrayList<>()).add(l);
            adj.computeIfAbsent(l.b, k -> new ArrayList<>()).add(l);
        }

        Station current = null;
        for (Station s : adj.keySet()) {
            if (adj.get(s).size() % 2 == 1) {
                current = s;
                break;
            }
        }
        if (current == null) {
            current = route[0].a;
        }

        while (ordered.size() < route.length) {
            List<Line> neighbors = adj.get(current);
            for (Line l : neighbors) {
                if (used.contains(l)) {
                    ordered.add(l);
                    used.remove(l);
                    current = l.a.equals(current) ? l.b : l.a;
                    break;
                }
            }
        }

        System.out.println("🔧 Maintenance Route:");
        for (Line l : ordered) {
            System.out.printf("%s <-> %s%n", l.a.name, l.b.name);
        }
    }
}
