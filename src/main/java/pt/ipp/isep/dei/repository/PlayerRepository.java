package pt.ipp.isep.dei.repository;

import pt.ipp.isep.dei.domain.Player;

public class PlayerRepository {

    private Player player;

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}
