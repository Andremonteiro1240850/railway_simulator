package pt.ipp.isep.dei.domain;

import pt.ipp.isep.dei.domain.enums.Carriage;

public class Train {

    private Locomotive locomotive;
    private Carriage carriage;

    public Train(Locomotive locomotive, Carriage carriage) {
        this.locomotive = locomotive;
        this.carriage = carriage;
    }

    public Locomotive getLocomotive() {return locomotive;}

    public void setLocomotive(Locomotive locomotive) {this.locomotive = locomotive;}

    public Carriage getCarriage() {return carriage;}

    public void setCarriage(Carriage carriage) {this.carriage = carriage;}
}


