package pt.ipp.isep.dei.repository;

import pt.ipp.isep.dei.domain.Player;

/**
 * Repository responsible for managing the current player instance.
 * Typically used in scenarios with a single active player per session.
 */
public class PlayerRepository {

    private Player player;

    /**
     * Sets the current player instance.
     *
     * @param player the player to set
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * Retrieves the current player instance.
     *
     * @return the player currently stored, or null if not set
     */
    public Player getPlayer() {
        return player;
    }
}

