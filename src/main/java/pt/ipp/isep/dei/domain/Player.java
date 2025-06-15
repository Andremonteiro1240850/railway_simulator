package pt.ipp.isep.dei.domain;

import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

/**
 * Represents a player in the simulation. A player has an email,
 * can purchase locomotives, and is associated with a scenario.
 */
public class Player implements Serializable {

    private String email;
    private List<Locomotive> locomotivePurchased;
    private Scenario scenario;

    /**
     * Constructs a Player with the specified email.
     *
     * @param email the player's email address
     */
    public Player(String email) {
        this.email = email;
        this.locomotivePurchased = new ArrayList<>();
    }

    /**
     * Returns the scenario associated with the player.
     *
     * @return the scenario
     */
    public Scenario getScenario() {
        return scenario;
    }

    /**
     * Returns the player's email.
     *
     * @return the email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the player's email.
     *
     * @param email the new email address
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Adds a locomotive to the list of locomotives purchased by the player.
     *
     * @param locomotive the locomotive to add
     */
    public void addLocomotive(Locomotive locomotive) {
        this.locomotivePurchased.add(locomotive);
    }


}
