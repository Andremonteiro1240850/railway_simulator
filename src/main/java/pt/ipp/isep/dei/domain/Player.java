package pt.ipp.isep.dei.domain;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private String email;
    private double budget=100000;
    private List<Locomotive> locomotivePurchased;
    private Scenario scenario;

    public Player (String email, Scenario scenario) {
        this.email = email;
        this.scenario = scenario;
        this.locomotivePurchased = new ArrayList<>();
    }

    public String getEmail() {return email;}
    public double getBudget() {return budget;}
    public List<Locomotive> getLocomotivePurchased() {return locomotivePurchased;}
    public Scenario getScenario() {return scenario;}

    public void setEmail(String email) {this.email = email;}
    public void setBudget(double budget) {this.budget = budget;}

    public void addLocomotive(Locomotive locomotive) {
        this.locomotivePurchased.add(locomotive);
    }





}
