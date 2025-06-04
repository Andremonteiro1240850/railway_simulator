package pt.ipp.isep.dei.domain.enums;

    /**
     * Enum que representa os tipos de carruagens disponíveis em Railroad Tycoon II.
     */
    public enum Carriage {



            // Carruagens de passageiros
            PASSENGER_CAR("Passageiros", 35),
            DINING_CAR("Passageiros", 20),

            // Carruagens de carga
            MAIL_CAR("Carga", 40),
            COAL_CAR("Carga", 65),
            OIL_TANKER("Carga", 65),
            CATTLE_CAR("Carga", 30),
            REFRIGERATED_CAR("Carga", 45),
            FLATBED_CAR("Carga", 50),
            HOPPER_CAR("Carga", 60),
            BOXCAR("Carga", 50),
            CABOOSE("Carga", 20); // geralmente usada no final dos comboios

            private final String cargoType;
            private final int capacity;

            Carriage(String cargoType, int capacity) {
                this.cargoType = cargoType;
                this.capacity = capacity;
            }

            /**
             * Retorna o tipo de carga: "Passageiros" ou "Carga".
             */
            public String getCargoType() {
                return cargoType;
            }

            /**
             * Retorna a capacidade da carruagem (número de passageiros ou unidade de carga).
             */
            public int getCapacity() {
                return capacity;
            }


    }


