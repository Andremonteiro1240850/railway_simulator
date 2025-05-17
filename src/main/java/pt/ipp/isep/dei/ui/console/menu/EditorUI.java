package pt.ipp.isep.dei.ui.console.menu;

import pt.ipp.isep.dei.domain.Organization;
import pt.ipp.isep.dei.ui.console.AddCityUI;
import pt.ipp.isep.dei.ui.console.CreateMapUI;
import pt.ipp.isep.dei.ui.console.CreateTaskUI;
import pt.ipp.isep.dei.ui.console.ShowTextUI;
import pt.ipp.isep.dei.ui.console.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class EditorUI implements Runnable {

    private final Organization organization;

    public EditorUI(Organization organization) {
        this.organization = organization;
    }

    public void run() {
        List<MenuItem> options = new ArrayList<MenuItem>();
        options.add(new MenuItem("Create a scenario", new CreateTaskUI()));
        options.add(new MenuItem("Create a map", new CreateMapUI(organization)));
        options.add(new MenuItem("Add an industry", new ShowTextUI("opçao 3")));
        options.add(new MenuItem("Add city", new AddCityUI(organization)));

        int option = 0;
        do {
            option = Utils.showAndSelectIndex(options, "\n\n--- EDITOR MENU -------------------------");

            if ((option >= 0) && (option < options.size())) {
                options.get(option).run();
            }
        } while (option != -1);
    }
}

