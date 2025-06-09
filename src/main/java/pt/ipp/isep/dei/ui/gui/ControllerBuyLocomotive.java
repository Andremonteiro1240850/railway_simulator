package pt.ipp.isep.dei.ui.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;

public class ControllerBuyLocomotive {
    public void handleBuyLocomotive() {
        try {
            // Carregar o FXML de BuyLocomotive
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/path/to/BuyLocomotive.fxml"));
            Parent root = loader.load();

            // Obter o estágio atual a partir da cena
            Stage stage = (Stage) (root.getScene().getWindow());

            // Definir a nova cena
            Scene scene = new Scene(root);
            stage.setScene(scene);  // Muda a cena para o BuyLocomotive
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
