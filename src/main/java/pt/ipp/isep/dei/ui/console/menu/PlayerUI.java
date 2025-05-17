package pt.ipp.isep.dei.ui.console.menu;

import pt.ipp.isep.dei.ui.console.CreateTaskUI;
import pt.ipp.isep.dei.ui.console.ShowTextUI;
import pt.ipp.isep.dei.ui.console.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class PlayerUI implements Runnable{
    public PlayerUI() {}

    public void run() {
        List<MenuItem> options = new ArrayList<MenuItem>();
        options.add(new MenuItem("Create a scenario", new CreateTaskUI()));
        options.add(new MenuItem("Create a map", new ShowTextUI("You have chosen Option 2.")));
        options.add(new MenuItem("Add an industry", new ShowTextUI("You have chosen Option 3.")));
        options.add(new MenuItem("Add city", new ShowTextUI("You have chosen Option 4.")));

        int option = 0;
        do {
            option = Utils.showAndSelectIndex(options, "\n\n--- EDITOR MENU -------------------------");

            if ((option >= 0) && (option < options.size())) {
                options.get(option).run();
            }
        } while (option != -1);
    }
}


