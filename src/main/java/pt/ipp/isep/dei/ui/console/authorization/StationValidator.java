package pt.ipp.isep.dei.ui.console.authorization;

import java.util.ArrayList;
import java.util.List;

//us06

public class StationValidator {
    public List<String> getAvailableBuildings(String stationType, int currentYear) {
        List<String> available = new ArrayList<>();

        // All station types can have these basic upgrades
        available.add("Telegraph");
        available.add("Small Hotel");

        // Additional checks based on station type and year
        if (stationType.equals("Terminal")) {
            available.add("Grand Hotel");
        }

        if (currentYear >= 1890) {
            available.add("Telephone");
        }

        return available;
    }
}