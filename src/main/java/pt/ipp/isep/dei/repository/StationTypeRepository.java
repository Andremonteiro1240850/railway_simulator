package pt.ipp.isep.dei.repository;

import java.util.ArrayList;
import java.util.List;

//us05

public class StationTypeRepository {
    private static StationTypeRepository instance;
    private List<String> stationTypes = List.of("Depot", "Station", "Terminal");

    private StationTypeRepository() {}

    public static synchronized StationTypeRepository getInstance() {
        if (instance == null) {
            instance = new StationTypeRepository();
        }
        return instance;
    }

    public List<String> getStationTypeList() {
        return new ArrayList<>(stationTypes);
    }
}