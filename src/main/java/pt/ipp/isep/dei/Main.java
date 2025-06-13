package pt.ipp.isep.dei;

import javafx.application.Application;
import javafx.stage.Stage;
import pt.ipp.isep.dei.domain.Organization;
import pt.ipp.isep.dei.repository.*;
import pt.ipp.isep.dei.ui.console.menu.MainMenuUI;

public class Main extends Application {
    private static Organization organization;

    @Override
    public void start(Stage primaryStage) {
        // Iniciar a interface de console em uma thread separada
        new Thread(() -> {
            try {
                MainMenuUI menu = new MainMenuUI(organization);
                menu.run();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static void main(String[] args) {
        // Inicializar o bootstrap e criar a organização
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.run();

        try {
            CreateMapRepository mapRepository = Repositories.getInstance().getMapRepository();
            CityRepository cityRepository = Repositories.getInstance().getCityRepository();
            IndustryRepository industryRepository = Repositories.getInstance().getIndustryRepository();
            StationRepository stationRepository = Repositories.getInstance().getStationRepository();
            organization = new Organization("This Company", mapRepository, cityRepository, industryRepository, stationRepository);
            
            // Iniciar a aplicação JavaFX
            launch(args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}