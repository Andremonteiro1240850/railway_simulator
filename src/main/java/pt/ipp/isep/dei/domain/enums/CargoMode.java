package pt.ipp.isep.dei.domain.enums;


/**
 * Enum representing the cargo loading mode for a point of route.
 */

public enum CargoMode {

        /**
         * The train only departs when fully loaded.
         */
        FULL(Cargo.COAL),

        /**
         * The train departs as soon as it has half of the carriages loaded.
         */
        HALF(Cargo.PASSENGERS),

        /**
         * The train departs with whatever cargo is available at the station.
         */
        AVAILABLE(Cargo.MAIL);

        private final Cargo cargoType;

        CargoMode(Cargo cargoType) {
            this.cargoType = cargoType;
        }

        public Cargo getCargoType() {
            return cargoType;
        }

        /**
         * Gets a user-friendly description of the cargo mode.
         *
         * @return Description of the mode.
         */
        public String getDescription() {
            return switch (this) {
                case FULL -> "The train only departs when fully loaded.";
                case HALF -> "The train departs as soon as it has half of the carriages loaded.";
                case AVAILABLE -> "The train departs with available cargoes in the station.";
            };
        }
    }


