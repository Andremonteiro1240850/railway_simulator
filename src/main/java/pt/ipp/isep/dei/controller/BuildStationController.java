package pt.ipp.isep.dei.controller;

import pt.ipp.isep.dei.domain.*;
import pt.ipp.isep.dei.domain.enums.Regions;
import pt.ipp.isep.dei.domain.enums.StationTypes;
import pt.ipp.isep.dei.repository.*;
import pt.isep.lei.esoft.auth.domain.model.Email;

import java.util.List;
import java.util.Optional;

public class BuildStationController {
    private OrganizationRepository organizationRepository;
    private AuthenticationRepository authenticationRepository;
    private StationRepository stationRepository;
    private StationTypes stationTypes;
    private Station station;
    private Player player;
    private Regions region;
    private Scenario scenario;

    //Repository instances are obtained from the Repositories class
    public BuildStationController() {
        getOrganizationRepository();
        getAuthenticationRepository();
        Repositories.getInstance().getStationRepository();
    }

    //Allows receiving the repositories as parameters for testing purposes
    public BuildStationController(OrganizationRepository organizationRepository,
                                AuthenticationRepository authenticationRepository,StationRepository stationRepository) {
        this.organizationRepository = organizationRepository;
        this.authenticationRepository = authenticationRepository;
        this.stationRepository = stationRepository;
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


    public boolean createStation(String typeName, String name, String region, int x, int y) {
        // 1. Verificar se o tipo é válido
        StationTypes stationType = null;
        for (StationTypes st : StationTypes.values()) {
            if (st.getType().equalsIgnoreCase(typeName)) {
                stationType = st;
                break;
            }
        }

        if (stationType == null) {
            System.out.println("Erro: Tipo de estação inválido.");
            return false;
        }

        //Valida região

        Regions regionType = null;
        for (Regions r : Regions.values()) {
            if(r.getRegion().equalsIgnoreCase(region)) {
                regionType = r;
                break;
            }
        }
        if (regionType == null) {
            System.out.println("Erro: Região inválida.");
            return false;

        }

        // 2. Verificar se já existe uma estação com a mesma localização
        List<Station> existingStations = Repositories.getInstance().getStationRepository().getAll();
        for (Station s : existingStations) {
            if (s.getX() == x && s.getY() == y) {
                System.out.println("Erro: Já existe uma estação nessa localização (" + x + ", " + y + ").");
                return false;
            }
        }

        // 3. Verificar orçamento do jogador

        double budget = scenario.getBudget();
        double cost = stationType.getCost();

        //Valida orçamento

        if (budget < cost) {
            System.out.println("Erro: Orçamento insuficiente para construir esta estação (necessário: " + cost + ").");
            return false;
        }

        // 4. Criar e adicionar estação
        Station newStation = new Station(name, stationType.getType(), regionType.getRegion(), stationType.getArea(), x, y);
        scenario.addStationPurchased(newStation);
        Optional<Station> added = Repositories.getInstance().getStationRepository().add(newStation);

        if (added.isPresent()) {
            // 5. Descontar orçamento
            scenario.setBudget(budget - cost);
            System.out.println("Estação criada com sucesso: " + newStation.getReference());
            return true;
        } else {
            System.out.println("Erro: Estação não foi criada (duplicada ou erro interno).");
            return false;
        }
    }


}
