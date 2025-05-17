package pt.ipp.isep.dei.controller;

import pt.ipp.isep.dei.domain.*;
import pt.ipp.isep.dei.repository.*;
import pt.isep.lei.esoft.auth.domain.model.Email;

import java.util.List;
import java.util.Optional;

public class UpgradeStationController {
    private  OrganizationRepository organizationRepository;
    private  AuthenticationRepository authenticationRepository;
    private  StationRepository stationRepository;
    private  UpgradeBuildingsRepository upgradeBuildingsRepository;

    public UpgradeStationController() {
        this(Repositories.getInstance().getOrganizationRepository(),
                Repositories.getInstance().getAuthenticationRepository(),
                Repositories.getInstance().getStationRepository(),
                Repositories.getInstance().getUpgradeBuildingsRepository());
    }

    public UpgradeStationController(OrganizationRepository organizationRepository,
                                    AuthenticationRepository authenticationRepository,
                                    StationRepository stationRepository,
                                    UpgradeBuildingsRepository upgradeBuildingsRepository) {
        this.organizationRepository = organizationRepository;
        this.authenticationRepository = authenticationRepository;
        this.stationRepository = stationRepository;
        this.upgradeBuildingsRepository = upgradeBuildingsRepository;
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

    public Optional<Station> upgradeStation(String stationId, String upgradeType, String subType) {
        Employee employee = getEmployeeFromSession();
        Optional<Organization> organization = organizationRepository.getOrganizationByEmployee(employee);

        // Busca a estação pelo ID (ou referência)
        Optional<Station> stationOptional = stationRepository.getAll().stream()
                .filter(s -> s.getReference().equals(stationId))
                .findFirst();

        if (organization.isEmpty() || stationOptional.isEmpty()) {
            return Optional.empty();
        }

        Station station = stationOptional.get();
        station.addUpgrade(new StationUpgrade(upgradeType, subType));

        return Optional.of(station);
    }
    private Employee getEmployeeFromSession() {
        Email email = getAuthenticationRepository().getCurrentUserSession().getUserId();
        return new Employee(email.getEmail());
    }
}