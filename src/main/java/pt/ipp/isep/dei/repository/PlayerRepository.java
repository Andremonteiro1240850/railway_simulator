package pt.ipp.isep.dei.repository;

public class PlayerRepository {
    private static PlayerRepository instance;
    private Player currentPlayer;

    private PlayerRepository() {}

    public static synchronized PlayerRepository getInstance() {
        if (instance == null) {
            instance = new PlayerRepository();
            instance.currentPlayer = new Player(); // Initialize with default player
        }
        return instance;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }
}