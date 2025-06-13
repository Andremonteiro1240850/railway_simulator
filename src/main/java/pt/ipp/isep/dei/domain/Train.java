package pt.ipp.isep.dei.domain;

import pt.ipp.isep.dei.domain.enums.CarriageList;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a train composed of a locomotive and a list of carriages.
 */
public class Train {

    private Locomotive locomotive;
    private final List<Carriage> carriageList;

    /**
     * Constructs a Train with the specified locomotive and list of carriages.
     *
     * @param locomotive   the locomotive of the train
     * @param carriageList the list of carriages attached to the train
     */
    public Train(Locomotive locomotive, List<Carriage> carriageList) {
        this.locomotive = locomotive;
        this.carriageList = carriageList;
    }

    /**
     * Returns the locomotive of the train.
     *
     * @return the locomotive
     */
    public Locomotive getLocomotive() {
        return locomotive;
    }

    /**
     * Returns a copy of the list of carriages attached to the train.
     *
     * @return the list of carriages
     */
    public List<Carriage> getCarriageList() {
        return new ArrayList<>(carriageList);
    }

    /**
     * Sets the locomotive of the train.
     *
     * @param locomotive the new locomotive to set
     */
    public void setLocomotive(Locomotive locomotive) {
        this.locomotive = locomotive;
    }

    /**
     * Adds a carriage to the train.
     *
     * @param carriage the carriage to add
     */
    public void addCarriage(Carriage carriage) {
        if (carriage != null) {
            carriageList.add(carriage);
        }
    }

    /**
     * Adds a carriage to the train from a predefined carriage enum.
     *
     * @param carriageEnum the carriage enum to convert and add
     */
    public void addCarriageFromEnum(CarriageList carriageEnum) {
        if (carriageEnum != null) {
            carriageList.add(carriageEnum.toCarriage());
        }
    }

    /**
     * Removes a carriage from the train.
     *
     * @param carriage the carriage to remove
     * @return true if the carriage was removed, false otherwise
     */
    public boolean removeCarriage(Carriage carriage) {
        return carriageList.remove(carriage);
    }

    /**
     * Returns a string representation of the train, including the locomotive and all carriages.
     *
     * @return a string describing the train
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Train with Locomotive: ").append(locomotive.getReference()).append("\n");
        sb.append("Carriages:\n");
        if (carriageList.isEmpty()) {
            sb.append(" - None\n");
        } else {
            for (Carriage c : carriageList) {
                sb.append(" - ").append(c).append("\n");
            }
        }
        return sb.toString();
    }
}


