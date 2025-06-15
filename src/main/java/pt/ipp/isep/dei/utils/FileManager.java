package pt.ipp.isep.dei.utils;

import pt.ipp.isep.dei.domain.Map;
import pt.ipp.isep.dei.domain.Scenario;
import pt.ipp.isep.dei.domain.Simulation;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Utility class for managing file operations related to maps and scenarios.
 * Handles saving and loading of maps and scenarios to/from files.
 */
public class FileManager {
    private static final String MAPS_DIR = "saved_maps";
    private static final String SCENARIOS_DIR = "saved_scenarios";
    private static final String GAMES_DIR = "saved_games";

    /**
     * Saves a map to a file.
     *
     * @param map the map to save
     * @param filename the name of the file to save to
     * @return true if the save was successful, false otherwise
     */
    public static boolean saveMap(Map map, String filename) {
        try {
            createDirectoryIfNotExists(MAPS_DIR);
            String filePath = MAPS_DIR + File.separator + filename + ".ser";
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
                oos.writeObject(map);
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Loads a map from a file.
     *
     * @param filename the name of the file to load from
     * @return the loaded map, or null if loading failed
     */
    public static Map loadMap(String filename) {
        try {
            String filePath = MAPS_DIR + File.separator + filename + ".ser";
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
                return (Map) ois.readObject();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Saves a scenario to a file.
     *
     * @param scenario the scenario to save
     * @param filename the name of the file to save to
     * @return true if the save was successful, false otherwise
     */
    public static boolean saveScenario(Scenario scenario, String filename) {
        try {
            createDirectoryIfNotExists(SCENARIOS_DIR);
            String filePath = SCENARIOS_DIR + File.separator + filename + ".ser";
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
                oos.writeObject(scenario);
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Loads a scenario from a file.
     *
     * @param filename the name of the file to load from
     * @return the loaded scenario, or null if loading failed
     */
    public static Scenario loadScenario(String filename) {
        try {
            String filePath = SCENARIOS_DIR + File.separator + filename + ".ser";
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
                return (Scenario) ois.readObject();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Saves a simulation (game) to a file.
     *
     * @param simulation the simulation to save
     * @param filename the name of the file to save to
     * @return true if the save was successful, false otherwise
     */
    public static boolean saveGame(Simulation simulation, String filename) {
        try {
            createDirectoryIfNotExists(GAMES_DIR);
            String filePath = GAMES_DIR + File.separator + filename + ".ser";
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
                oos.writeObject(simulation);
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Loads a simulation (game) from a file.
     *
     * @param filename the name of the file to load from
     * @return the loaded simulation, or null if loading failed
     */
    public static Simulation loadGame(String filename) {
        try {
            String filePath = GAMES_DIR + File.separator + filename + ".ser";
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
                return (Simulation) ois.readObject();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Gets a list of all saved map files.
     *
     * @return array of map filenames without the .ser extension
     */
    public static String[] getSavedMaps() {
        return getSavedFiles(MAPS_DIR);
    }

    /**
     * Gets a list of all saved scenario files.
     *
     * @return array of scenario filenames without the .ser extension
     */
    public static String[] getSavedScenarios() {
        return getSavedFiles(SCENARIOS_DIR);
    }

    /**
     * Gets a list of all saved game files.
     *
     * @return array of game filenames without the .ser extension
     */
    public static String[] getSavedGames() {
        return getSavedFiles(GAMES_DIR);
    }

    private static String[] getSavedFiles(String directory) {
        try {
            createDirectoryIfNotExists(directory);
            return Files.list(Paths.get(directory))
                    .filter(path -> path.toString().endsWith(".ser"))
                    .map(path -> path.getFileName().toString().replace(".ser", ""))
                    .toArray(String[]::new);
        } catch (IOException e) {
            e.printStackTrace();
            return new String[0];
        }
    }

    private static void createDirectoryIfNotExists(String directory) throws IOException {
        Path path = Paths.get(directory);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }
    }
} 