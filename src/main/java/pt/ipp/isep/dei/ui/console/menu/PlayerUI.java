package pt.ipp.isep.dei.ui.console.menu;

import pt.ipp.isep.dei.ui.console.*;
import pt.ipp.isep.dei.ui.console.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class PlayerUI implements Runnable{
    public PlayerUI() {}

    public void run() {
        List<MenuItem> options = new ArrayList<MenuItem>();
        options.add(new MenuItem("Build station", new BuildStationUI()));
        options.add(new MenuItem("Upgrade Station", new UpgradeStationUI()));
        options.add(new MenuItem("List stations", new ListStationUI()));
        options.add(new MenuItem("Buy locomotive", new ShowTextUI("You have chosen Option 4.")));
        options.add(new MenuItem("Assign train to route", new ShowTextUI("You have chosen Option 5.")));

        int option = 0;
        do {
            option = Utils.showAndSelectIndex(options, "\n\n--- PLAYER MENU -------------------------");

            if ((option >= 0) && (option < options.size())) {
                options.get(option).run();
            }
        } while (option != -1);
    }
}


