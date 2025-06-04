package pt.ipp.isep.dei.domain;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private String email;
    private List<Locomotive> locomotivePurchased;
    private Scenario scenario;

    public Player (String email) {
        this.email = email;

    }


    public String getEmail() {return email;}


    public void setEmail(String email) {this.email = email;}

    public void addLocomotive(Locomotive locomotive) {
        this.locomotivePurchased.add(locomotive);
    }





}
