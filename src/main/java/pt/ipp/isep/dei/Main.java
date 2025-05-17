package pt.ipp.isep.dei;

import pt.ipp.isep.dei.domain.Organization;
import pt.ipp.isep.dei.repository.*;
import pt.ipp.isep.dei.ui.console.menu.MainMenuUI;

public class Main {

    public static void main(String[] args) {
        Bootstrap bootstrap = new Bootstrap();
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

        }
    }
}