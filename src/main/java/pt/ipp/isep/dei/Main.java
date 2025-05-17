package pt.ipp.isep.dei;

import pt.ipp.isep.dei.domain.Organization;
import pt.ipp.isep.dei.ui.console.menu.MainMenuUI;

public class Main {

    public static void main(String[] args) {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.run();

        try {
            Organization organization = new Organization("PT123456789");
            MainMenuUI menu = new MainMenuUI(organization);
            menu.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}