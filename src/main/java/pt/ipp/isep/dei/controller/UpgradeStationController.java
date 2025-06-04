package pt.ipp.isep.dei.controller;

import pt.ipp.isep.dei.domain.*;
import pt.ipp.isep.dei.domain.enums.Buildings;
import pt.ipp.isep.dei.repository.*;
import pt.isep.lei.esoft.auth.domain.model.Email;

import java.util.List;
import java.util.Optional;

public class UpgradeStationController {
    private  OrganizationRepository organizationRepository;
    private  AuthenticationRepository authenticationRepository;
    private  StationRepository stationRepository;
    private  UpgradeBuildingsRepository upgradeBuildingsRepository;
    private Player player;
    private Scenario scenario;

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


    public boolean upgradeStation(String stationId, String type, String subType) {
        // 1. Validar tipo e subtipo
        Buildings upgrade = Buildings.from(type, subType);
        if (upgrade == null) {
            System.out.println("Erro: Tipo ou subtipo de upgrade inválido.");
            return false;
        }

        // 2. Procurar estação
        Optional<Station> stationOptional = stationRepository.findByReference(stationId);
        if (stationOptional.isEmpty()) {
            System.out.println("Erro: Estação não encontrada com ID " + stationId);
            return false;
        }

        Station station = stationOptional.get();

        // ❗️ NOVO: Verificar se o upgrade já foi aplicado
        boolean upgradeExists = station.getUpgrades().stream()
                .anyMatch(u -> u.getType().equalsIgnoreCase(type) &&
                        ((subType == null && u.getSubType() == null) ||
                                (subType != null && subType.equalsIgnoreCase(u.getSubType()))));

        if (upgradeExists) {
            System.out.println("Erro: Este upgrade já foi aplicado a esta estação.");
            return false;
        }

        // 3. Verificar orçamento
        double price = upgrade.getPrice();
        if (scenario.getBudget() < price) {
            System.out.printf("Erro: Orçamento insuficiente (necessário: €%.2f, disponível: €%.2f).\n", price, scenario.getBudget());
            return false;
        }

        // 4. Aplicar upgrade
        StationUpgrade stationUpgrade = new StationUpgrade(type, subType);
        station.addUpgrade(stationUpgrade);
        stationRepository.addUpgradeToStation(station.getReference(), stationUpgrade);

        // 5. Descontar orçamento
        scenario.setBudget(scenario.getBudget() - price);
        System.out.printf("✅ Upgrade aplicado com sucesso: %s (%s) - Custo: €%.2f\n", type,
                subType != null ? subType : "sem subtipo", price);

        return true;
    }

    private Employee getEmployeeFromSession() {
        Email email = getAuthenticationRepository().getCurrentUserSession().getUserId();
        return new Employee(email.getEmail());
    }
}