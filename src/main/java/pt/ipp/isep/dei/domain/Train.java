package pt.ipp.isep.dei.domain;

import pt.ipp.isep.dei.domain.enums.CarriageList;

public class Train {

    private Locomotive locomotive;
    private CarriageList carriage;

    public Train(Locomotive locomotive, CarriageList carriage) {
        this.locomotive = locomotive;
        this.carriage = carriage;
    }

    public Locomotive getLocomotive() {return locomotive;}

    public void setLocomotive(Locomotive locomotive) {this.locomotive = locomotive;}

    public CarriageList getCarriage() {return carriage;}

    public void setCarriage(CarriageList carriage) {this.carriage = carriage;}
}


