package pt.ipp.isep.dei.domain;

import pt.ipp.isep.dei.domain.enums.CarriageList;

import java.util.ArrayList;
import java.util.List;

public class Train {



        private Locomotive locomotive;
        private final List<Carriage> carriageList;

        public Train(Locomotive locomotive, List<Carriage> carriageList) {
            this.locomotive = locomotive;
            this.carriageList = carriageList;
        }

        // GETTERS
        public Locomotive getLocomotive() {
            return locomotive;
        }

        public List<Carriage> getCarriageList() {
            return new ArrayList<>(carriageList);
        }

        // SETTERS
        public void setLocomotive(Locomotive locomotive) {
            this.locomotive = locomotive;
        }

        // MÉTODOS PARA GESTÃO DAS CARRUAGENS

        public void addCarriage(Carriage carriage) {
            if (carriage != null) {
                carriageList.add(carriage);
            }
        }

        public void addCarriageFromEnum(CarriageList carriageEnum) {
            if (carriageEnum != null) {
                carriageList.add(carriageEnum.toCarriage());
            }
        }

        public boolean removeCarriage(Carriage carriage) {
            return carriageList.remove(carriage);
        }

        // TO STRING
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


