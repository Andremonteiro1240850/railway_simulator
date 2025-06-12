package pt.ipp.isep.dei.controller;

import java.util.List;
import java.util.Optional;

import pt.ipp.isep.dei.domain.Employee;
import pt.ipp.isep.dei.domain.Player;
import pt.ipp.isep.dei.domain.Scenario;
import pt.ipp.isep.dei.domain.Station;
import pt.ipp.isep.dei.domain.enums.StationUpgrade;
import pt.ipp.isep.dei.repository.AuthenticationRepository;
import pt.ipp.isep.dei.repository.OrganizationRepository;
import pt.ipp.isep.dei.repository.Repositories;
import pt.ipp.isep.dei.repository.ScenarioRepository;
import pt.ipp.isep.dei.repository.StationRepository;
import pt.ipp.isep.dei.repository.UpgradeBuildingsRepository;
import pt.isep.lei.esoft.auth.domain.model.Email;

public class UpgradeStationController {
    private   OrganizationRepository organizationRepository;
    private  AuthenticationRepository authenticationRepository;
    private final  StationRepository stationRepository;
    private final  UpgradeBuildingsRepository upgradeBuildingsRepository;
    private  ScenarioRepository scenarioRepository;
    private Player player;
    private Scenario scenario;

    public UpgradeStationController() {
        this(Repositories.getInstance().getOrganizationRepository(),
                Repositories.getInstance().getAuthenticationRepository(),
                Repositories.getInstance().getStationRepository(),
                Repositories.getInstance().getUpgradeBuildingsRepository(),
                Repositories.getInstance().getScenarioRepository());

    }

    public UpgradeStationController(OrganizationRepository organizationRepository,
                                    AuthenticationRepository authenticationRepository,
                                    StationRepository stationRepository,
                                    UpgradeBuildingsRepository upgradeBuildingsRepository, ScenarioRepository scenarioRepository) {
        this.organizationRepository = organizationRepository;
        this.authenticationRepository = authenticationRepository;
        this.stationRepository = stationRepository;
        this.upgradeBuildingsRepository = upgradeBuildingsRepository;
        this.scenarioRepository = scenarioRepository;

    }


    public Scenario getScenario() {
        return scenario;
    }

    public List<Station> getAvailableStations() {
        return stationRepository.getAll();
    }

    public List<String> getAvailableUpgradeTypes() {
        return upgradeBuildingsRepository.getAllUpgradeTypes();
    }

    public List<String> getAvailableSubTypes(String upgradeType) {
        if (upgradeBuildingsRepository.acceptsSubTypes(upgradeType)) {
            return upgradeBuildingsRepository.getAllSubTypes();
        }
        return List.of();
    }
    private OrganizationRepository getOrganizationRepository() {
        if (organizationRepository == null) {
            Repositories repositories = Repositories.getInstance();
            organizationRepository = repositories.getOrganizationRepository();
        }
        return organizationRepository;

    }
    private AuthenticationRepository getAuthenticationRepository() {
        if (authenticationRepository == null) {
            Repositories repositories = Repositories.getInstance();

            //Get the AuthenticationRepository
            authenticationRepository = repositories.getAuthenticationRepository();
        }
        return authenticationRepository;
    }

    public List<Station> getAllStations() {
        return stationRepository.getAll();
    }


    public boolean upgradeStation(String stationId, String type, String subType) {
        Optional<Station> stationOptional = stationRepository.findByReference(stationId);
        if (stationOptional.isEmpty()) return false;
        Station station = stationOptional.get();

        // Verificar se o upgrade já foi aplicado
        boolean upgradeExists = station.getUpgrades().stream()
            .anyMatch(u -> u.getDisplayName().equalsIgnoreCase(type));
        if (upgradeExists) {
            System.out.println("Erro: Este upgrade já foi aplicado a esta estação.");
            return false;
        }

        // Encontrar o upgrade correspondente na enum
        StationUpgrade upgradeToAdd = null;
        for (StationUpgrade upg : StationUpgrade.values()) {
            if (upg.getDisplayName().equalsIgnoreCase(type)) {
                upgradeToAdd = upg;
                break;
            }
        }
        if (upgradeToAdd == null) {
            System.out.println("Erro: Upgrade não encontrado.");
            return false;
        }

        station.addUpgrade(upgradeToAdd);
        stationRepository.addUpgradeToStation(stationId, upgradeToAdd);
        return true;
    }


    private Employee getEmployeeFromSession() {
        Email email = getAuthenticationRepository().getCurrentUserSession().getUserId();
        return new Employee(email.getEmail());
    }
}