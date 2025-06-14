package pt.ipp.isep.dei.repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Repository that holds the list of valid cargo types used in the system.
 */
public class CargoRepository {

    private final List<String> availableCargos = new ArrayList<>();

    /**
     * Default constructor. Initializes the repository with a predefined list of cargo types.
     */
    public CargoRepository() {
        // Initializes with base cargo types defined in the specification
        availableCargos.addAll(List.of(
                "coal", "iron", "bauxite", "nitrate", "vegetables", "cereals",
                "wool", "coffee", "rubber", "cattle", "steel", "textiles",
                "automobiles", "food", "passengers", "mail"
        ));
    }

    /**
     * Returns a list of all available cargo types.
     *
     * @return list of cargo names
     */
    public List<String> getAllCargos() {
        return new ArrayList<>(availableCargos); // Return a copy to prevent external mutation
    }

    /**
     * Checks whether a given cargo type is valid (i.e., defined in the repository).
     *
     * @param cargo the name of the cargo to check
     * @return true if the cargo is valid, false otherwise
     */
    public boolean isValidCargo(String cargo) {
        return availableCargos.contains(cargo);
    }
}
