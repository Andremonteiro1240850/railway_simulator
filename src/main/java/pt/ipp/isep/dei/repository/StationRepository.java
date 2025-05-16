package pt.ipp.isep.dei.repository;

import java.util.HashMap;
import java.util.List;

//us06 e 05, vai buscar as estaçoes da 05

public class StationRepository {
    private static StationRepository instance;
    private Map<Player, List<Station>> playerStations = new HashMap<>();

    private StationRepository() {}

    public static synchronized StationRepository getInstance() {
        if (instance == null) {
            instance = new StationRepository();
        }
        return instance;
    }

    public List<Station> getStationsByPlayer(Player player) {
        return playerStations.getOrDefault(player, new ArrayList<>());
    }

    public void addStation(Player player, Station station) {
        playerStations.computeIfAbsent(player, k -> new ArrayList<>()).add(station);
    }
}