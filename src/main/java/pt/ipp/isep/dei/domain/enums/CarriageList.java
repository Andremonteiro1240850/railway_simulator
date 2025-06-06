package pt.ipp.isep.dei.domain.enums;

import pt.ipp.isep.dei.domain.Carriage;

/**
     * Enum que representa os tipos de carruagens disponíveis em Railroad Tycoon II.
     */
    public enum CarriageList {



            // Carruagens de passageiros
            PASSENGER_CAR(" PASSENGER_CAR","Passageiros", 35),
            DINING_CAR("DINING_CAR","Passageiros", 20),

            // Carruagens de carga
            MAIL_CAR("MAIL_CAR","Carga", 40),
            COAL_CAR("COAL_CAR","Carga", 65),
            OIL_TANKER("OIL_TANKER","Carga", 65),
            CATTLE_CAR("CATTLE_CAR","Carga", 30),
            REFRIGERATED_CAR("REFRIGERATED_CAR","Carga", 45),
            FLATBED_CAR("FLATBED_CAR","Carga", 50),
            HOPPER_CAR("HOPPER_CAR","Carga", 60),
            BOXCAR("BOXCAr","Carga", 50),
            CABOOSE("CABOOSE","Carga", 20); // geralmente usada no final dos comboios

            private String name;
            private final String cargoType;
            private final int capacity;

            CarriageList(String name, String cargoType, int capacity) {
                this.name = name;
                this.cargoType = cargoType;
                this.capacity = capacity;
            }

    public Carriage toCarriage() {
        return new Carriage(name, cargoType, capacity);
    }






}


