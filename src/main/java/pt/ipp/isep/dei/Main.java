package pt.ipp.isep.dei;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.application.Application;
import pt.ipp.isep.dei.domain.Organization;
import pt.ipp.isep.dei.repository.*;
import pt.ipp.isep.dei.ui.console.menu.MainMenuUI;

import static javafx.application.Application.launch;


public class Main extends Application {

    public void start(Stage primaryStage) {
        try {
            // Carregar o FXML para a tela inicial (PlayerMenu)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/pt/ipp/isep/dii/ui/gui/PlayerMenu.fxml"));
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
        /*Bootstrap bootstrap = new Bootstrap();
        bootstrap.run();

        try {
            CreateMapRepository mapRepository = Repositories.getInstance().getMapRepository();
            CityRepository cityRepository = Repositories.getInstance().getCityRepository();
            IndustryRepository industryRepository = Repositories.getInstance().getIndustryRepository();
            StationRepository stationRepository = Repositories.getInstance().getStationRepository();
            Organization organization = new Organization("This Company",mapRepository,cityRepository,industryRepository,stationRepository);
            MainMenuUI menu = new MainMenuUI(organization);
            menu.run();
        } catch (Exception e) {
            e.printStackTrace();

        }*/
    }
}