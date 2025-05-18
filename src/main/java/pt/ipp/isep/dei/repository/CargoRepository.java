package pt.ipp.isep.dei.repository;

import java.util.ArrayList;
import java.util.List;

public class CargoRepository {
    private final List<String> availableCargos = new ArrayList<>();

    public CargoRepository() {
        // Inicializa com os tipos de carga baseados no documento
        availableCargos.addAll(List.of(
                "coal", "iron", "bauxite", "nitrate", "vegetables", "cereals",
                "wool", "coffee", "rubber", "cattle", "steel", "textiles",
                "automobiles", "food", "passengers", "mail"
        ));
    }

    public List<String> getAllCargos() {
        return new ArrayList<>(availableCargos);
    }

    public boolean isValidCargo(String cargo) {
        return availableCargos.contains(cargo);
    }
}