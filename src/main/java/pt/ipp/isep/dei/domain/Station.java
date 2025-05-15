package pt.ipp.isep.dei.domain;

import pt.ipp.isep.dei.repository.StationTypeRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//us05

public class Station {
    private final StationTypeRepository type;
    private final int locationX;
    private final int locationY;
    private final String name;
    private final int center;
    private List<StationTypeRepository> buildings = new ArrayList<>();
    private List<String> stationUpgrades = new ArrayList<>();

    public Station(StationTypeRepository type, int locationX, int locationY, String name, int center) {
        if ((type == null) || (locationX ==0 ) || (name == null) || (locationY == 0)) {
            throw new IllegalArgumentException("Station parameters cannot be null");
        }
        this.type = type;
        this.locationX = locationX;
        this.locationY = locationY;
        this.name = name;
        this.center = center;
        this.stationUpgrades = new ArrayList<>();
    }
    /**
     * Adiciona uma melhoria à estação
     */
    public void addUpgrade(String upgradeType) {
        if (upgradeType == null || upgradeType.trim().isEmpty()) {
            throw new IllegalArgumentException("Upgrade type cannot be null or empty");
        }

        // Verifica incompatibilidades
        if (upgradeType.equals("Grand Hotel") && stationUpgrades.contains("Small Hotel")) {
            throw new IllegalStateException("Cannot add Grand Hotel when Small Hotel exists");
        }
        // Adicione outras regras conforme necessário

        stationUpgrades.add(upgradeType);
    }

    /**
     * Remove uma melhoria
     */
    public boolean removeUpgrade(String upgradeType) {
        return stationUpgrades.remove(upgradeType);
    }

    /**
     * Verifica se a estação contém determinada melhoria
     */
    public boolean hasUpgrade(String upgradeType) {
        return stationUpgrades.contains(upgradeType);
    }

    /**
     * Retorna a lista de melhorias (imutável)
     */
    public List<String> getUpgrades() {
        return Collections.unmodifiableList(stationUpgrades);
    }



}