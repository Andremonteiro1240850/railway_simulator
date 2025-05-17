package pt.ipp.isep.dei;

import pt.ipp.isep.dei.domain.Organization;
import pt.ipp.isep.dei.repository.CreateMapRepository;
import pt.ipp.isep.dei.repository.Repositories;
import pt.ipp.isep.dei.ui.console.menu.MainMenuUI;

public class Main {

    public static void main(String[] args) {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.run();

        try {
            CreateMapRepository mapRepository = Repositories.getInstance().getMapRepository();
            Organization organization = new Organization("This Company",mapRepository);
            MainMenuUI menu = new MainMenuUI(organization);
            menu.run();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}