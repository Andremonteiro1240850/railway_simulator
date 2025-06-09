package pt.ipp.isep.dei;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pt.ipp.isep.dei.domain.Organization;
import pt.ipp.isep.dei.repository.*;
import pt.ipp.isep.dei.ui.console.menu.MainMenuUI;

import static javafx.application.Application.launch;


public class Main extends Application {

    public void start(Stage primaryStage) {
        try {
            // Carregar o FXML para a tela inicial (PlayerMenu)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/pt/ipp/isep/dei/ui/gui/PlayerMenu.fxml"));
            Parent root = loader.load();

            // Configurar a cena e o estágio (janela)
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Menu do Player");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}