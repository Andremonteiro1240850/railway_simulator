package pt.ipp.isep.dei.controller;

import pt.ipp.isep.dei.domain.*;
import pt.ipp.isep.dei.domain.enums.Eventos;
import pt.ipp.isep.dei.domain.enums.LocomotiveType;
import pt.ipp.isep.dei.repository.*;
import pt.isep.lei.esoft.auth.domain.model.Email;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Controller responsible for creating and managing game scenarios.
 * This controller handles the creation of new scenarios with specific parameters and historical events.
 */
public class CreateScenarioController {
    private final OrganizationRepository orgRepo;
    private final AuthenticationRepository authRepo;
    private final CreateMapRepository mapRepo;
    private final ScenarioRepository scenarioRepo;
    private Scenario scenario;
    private int startYear;
    private List<HistoricalEvent> historicalEvents;

    /**
     * Default constructor that initializes all required repositories.
     */
    public CreateScenarioController() {
        this(Repositories.getInstance().getOrganizationRepository(),
                Repositories.getInstance().getAuthenticationRepository(),
                Repositories.getInstance().getMapRepository(),
                Repositories.getInstance().getScenarioRepository());
    }

    /**
     * Constructor for testing purposes that allows dependency injection.
     *
     * @param orgRepo the organization repository to use
     * @param authRepo the authentication repository to use
     * @param mapRepo the map repository to use
     * @param scenarioRepo the scenario repository to use
     */
    public CreateScenarioController(OrganizationRepository orgRepo,
                                    AuthenticationRepository authRepo,
                                    CreateMapRepository mapRepo,
                                    ScenarioRepository scenarioRepo) {
        this.orgRepo = orgRepo;
        this.authRepo = authRepo;
        this.mapRepo = mapRepo;
        this.scenarioRepo = scenarioRepo;
    }

    /**
     * Retrieves all available maps.
     *
     * @return list of all available maps
     */
    public List<Map> getAvailableMaps() {
        return mapRepo.getAll();
    }

    /**
     * Loads historical events that are applicable from the given start year.
     *
     * @param startYear the year from which to start loading events
     * @return list of historical events that start from or after the given year
     */
    public List<HistoricalEvent> loadApplicableHistoricalEvents(int startYear) {
        List<HistoricalEvent> events = new ArrayList<>();
        for (Eventos evento : Eventos.values()) {
            if (evento.getDataInicio() >= startYear) {
                events.add(new HistoricalEvent(evento));
            }
        }
        return events;
    }

    public boolean createScenario(String name, Map map, int startYear, int endYear,
                                  List<String> locomotiveTypes, PortBehavior portBehavior,
                                  double budget, List<HistoricalEvent> historicalEvents) {

        // 1. Verificar se o usuário está logado
        if (!authRepo.getCurrentUserSession().isLoggedIn()) {
            System.out.println("Erro: Usuário não está logado.");
            return false;
        }

        // 2. Verificar o funcionário e organização
        Employee employee = getEmployeeFromSession();
        Optional<Organization> org = orgRepo.getOrganizationByEmployee(employee);

        if (org.isEmpty()) {
            System.out.println("Erro: Funcionário não pertence a nenhuma organização.");
            return false;
        }

        // 3. Converter strings para LocomotiveType e validar
        List<LocomotiveType> availableLocomotiveTypes = new ArrayList<>();
        for (String typeStr : locomotiveTypes) {
            try {
                LocomotiveType type = LocomotiveType.fromString(typeStr);
                availableLocomotiveTypes.add(type);
            } catch (IllegalArgumentException e) {
                System.out.println("Erro: Tipo de locomotiva desconhecido: " + typeStr);
                return false;
            }
        }

        // 4. Validar anos (startYear < endYear)
        if (startYear >= endYear) {
            System.out.println("Erro: Ano de início deve ser anterior ao ano de fim.");
            return false;
        }

        // 5. Validar orçamento (deve ser positivo)
        if (budget <= 0) {
            System.out.println("Erro: Orçamento deve ser positivo.");
            return false;
        }

        // 6. Criar o cenário
        Scenario newScenario = new Scenario(name, startYear, endYear,
                availableLocomotiveTypes, historicalEvents,
                portBehavior, budget, map);

        // 7. Adicionar ao repositório
        Optional<Scenario> added = scenarioRepo.add(newScenario);

        if (added.isPresent()) {
            this.scenario = added.get();
            return true;
        }

        return false;
    }

    public void spendBudget(double amount) {
        if (scenario == null) {
            throw new IllegalStateException("No scenario has been created yet");
        }
        if (amount < 0) {
            throw new IllegalArgumentException("Valor a gastar não pode ser negativo.");
        }
        if (amount > scenario.getBudget()) {
            throw new IllegalArgumentException("Orçamento insuficiente.");
        }
        scenario.setBudget(scenario.getBudget() - amount);
    }

    public void receiveBudget(double amount) {
        if (scenario == null) {
            throw new IllegalStateException("No scenario has been created yet");
        }
        if (amount < 0) {
            throw new IllegalArgumentException("Amount to receive cannot be negative.");
        }
        scenario.setBudget(scenario.getBudget() + amount);
    }

    public Scenario getScenario() {
        if (scenario == null) {
            throw new IllegalStateException("No scenario has been created yet");
        }
        return scenario;
    }

    private Employee getEmployeeFromSession() {
        if (!authRepo.getCurrentUserSession().isLoggedIn()) {
            throw new IllegalStateException("User is not logged in");
        }
        Email email = authRepo.getCurrentUserSession().getUserId();
        return new Employee(email.getEmail());
    }
}