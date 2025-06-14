package pt.ipp.isep.dei.controller;

import java.util.List;
import java.util.Optional;

import pt.ipp.isep.dei.domain.Employee;
import pt.ipp.isep.dei.domain.Player;
import pt.ipp.isep.dei.domain.Scenario;
import pt.ipp.isep.dei.domain.Station;
import pt.ipp.isep.dei.domain.enums.StationUpgrade;
import pt.ipp.isep.dei.repository.*;
import pt.isep.lei.esoft.auth.domain.model.Email;

/**
 * Controller responsible for managing upgrades applied to stations in a given scenario.
 * This controller handles the validation and application of station upgrades.
 */
public class UpgradeStationController {
    private OrganizationRepository organizationRepository;
    private AuthenticationRepository authenticationRepository;
    private final StationRepository stationRepository;
    private final UpgradeBuildingsRepository upgradeBuildingsRepository;
    private ScenarioRepository scenarioRepository;
    private Player player;
    private Scenario scenario;

    /**
     * Default constructor. Initializes repositories using the singleton instance of Repositories.
     */
    public UpgradeStationController() {
        this(Repositories.getInstance().getOrganizationRepository(),
                Repositories.getInstance().getAuthenticationRepository(),
                Repositories.getInstance().getStationRepository(),
                Repositories.getInstance().getUpgradeBuildingsRepository(),
                Repositories.getInstance().getScenarioRepository());
    }

    /**
     * Constructor with dependency injection. Mainly used for testing purposes.
     *
     * @param organizationRepository the organization repository
     * @param authenticationRepository the authentication repository
     * @param stationRepository the station repository
     * @param upgradeBuildingsRepository the upgrade buildings repository
     * @param scenarioRepository the scenario repository
     */
    public UpgradeStationController(OrganizationRepository organizationRepository,
                                    AuthenticationRepository authenticationRepository,
                                    StationRepository stationRepository,
                                    UpgradeBuildingsRepository upgradeBuildingsRepository,
                                    ScenarioRepository scenarioRepository) {
        this.organizationRepository = organizationRepository;
        this.authenticationRepository = authenticationRepository;
        this.stationRepository = stationRepository;
        this.upgradeBuildingsRepository = upgradeBuildingsRepository;
        this.scenarioRepository = scenarioRepository;
    }

    /**
     * Returns the currently active scenario.
     *
     * @return the current scenario
     */
    public Scenario getScenario() {
        return scenario;
    }

    /**
     * Retrieves all available stations.
     *
     * @return list of all stations
     */
    public List<Station> getAvailableStations() {
        return stationRepository.getAll();
    }

    /**
     * Retrieves all available upgrade types.
     *
     * @return list of upgrade type names
     */
    public List<String> getAvailableUpgradeTypes() {
        return upgradeBuildingsRepository.getAllUpgradeTypes();
    }

    /**
     * Retrieves all available subtypes for a given upgrade type.
     *
     * @param upgradeType the selected upgrade type
     * @return list of subtypes if applicable, empty list otherwise
     */
    public List<String> getAvailableSubTypes(String upgradeType) {
        if (upgradeBuildingsRepository.acceptsSubTypes(upgradeType)) {
            return upgradeBuildingsRepository.getAllSubTypes();
        }
        return List.of();
    }

    /**
     * Retrieves all stations (alternative access point).
     *
     * @return list of all stations
     */
    public List<Station> getAllStations() {
        return stationRepository.getAll();
    }

    /**
     * Attempts to apply a specific upgrade to a station.
     * Validates if the upgrade already exists and whether it is a valid upgrade type.
     *
     * @param stationId the unique reference of the station
     * @param type      the upgrade type name
     * @param subType   optional subtype (not currently used in logic)
     * @return true if the upgrade was successfully applied, false otherwise
     */
    public boolean upgradeStation(String stationId, String type, String subType) {
        Optional<Station> stationOptional = stationRepository.findByReference(stationId);
        if (stationOptional.isEmpty()) return false;

        Station station = stationOptional.get();

        // Check if upgrade already applied
        boolean upgradeExists = station.getUpgrades().stream()
                .anyMatch(u -> u.getDisplayName().equalsIgnoreCase(type));
        if (upgradeExists) {
            System.out.println("Erro: Este upgrade já foi aplicado a esta estação.");
            return false;
        }


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

    /**
     * Retrieves the employee associated with the current session.
     *
     * @return the currently logged-in employee
     */
    private Employee getEmployeeFromSession() {
        Email email = getAuthenticationRepository().getCurrentUserSession().getUserId();
        return new Employee(email.getEmail());
    }

    /**
     * Lazily loads the organization repository if it hasn't been initialized.
     *
     * @return the organization repository
     */
    private OrganizationRepository getOrganizationRepository() {
        if (organizationRepository == null) {
            Repositories repositories = Repositories.getInstance();
            organizationRepository = repositories.getOrganizationRepository();
        }
        return organizationRepository;
    }

    /**
     * Lazily loads the authentication repository if it hasn't been initialized.
     *
     * @return the authentication repository
     */
    private AuthenticationRepository getAuthenticationRepository() {
        if (authenticationRepository == null) {
            Repositories repositories = Repositories.getInstance();
            authenticationRepository = repositories.getAuthenticationRepository();
        }
        return authenticationRepository;
    }
}
