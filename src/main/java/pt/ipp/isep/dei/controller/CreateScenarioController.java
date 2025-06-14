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
 * Controller responsible for creating a new game scenario, including validation
 * of locomotive types, budget, time period, and historical events.
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
     * Default constructor. Initializes all repositories from the shared Repositories instance.
     */
    public CreateScenarioController() {
        this(Repositories.getInstance().getOrganizationRepository(),
                Repositories.getInstance().getAuthenticationRepository(),
                Repositories.getInstance().getMapRepository(),
                Repositories.getInstance().getScenarioRepository());
    }

    /**
     * Constructor with repository injection, useful for testing purposes.
     *
     * @param orgRepo      the organization repository
     * @param authRepo     the authentication repository
     * @param mapRepo      the map repository
     * @param scenarioRepo the scenario repository
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
     * Returns a list of available maps that can be used for scenario creation.
     *
     * @return list of available maps
     */
    public List<Map> getAvailableMaps() {
        return mapRepo.getAll();
    }

    /**
     * Loads historical events that are applicable for the specified starting year.
     *
     * @param startYear the year from which events will be considered
     * @return list of applicable historical events
     */
    public List<HistoricalEvent> loadApplicableHistoricalEvents(int startYear) {
        List<HistoricalEvent> applicableEvents = new ArrayList<>();
        for (Eventos eventoEnum : Eventos.values()) {
            if (eventoEnum.getDataInicio() <= startYear && startYear <= eventoEnum.getDataFim()) {
                applicableEvents.add(new HistoricalEvent(eventoEnum));
            }
        }
        this.historicalEvents = applicableEvents;
        return applicableEvents;
    }

    /**
     * Attempts to create a new scenario with the given data.
     * Validates the employee, organization, locomotive types, years, and budget.
     *
     * @param name              scenario name
     * @param map               map associated with the scenario
     * @param startYear         starting year of the scenario
     * @param endYear           ending year of the scenario
     * @param locomotiveTypes   list of locomotive type names (as strings)
     * @param portBehavior      port behavior configuration
     * @param budget            scenario starting budget
     * @param historicalEvents  optional historical events (currently overridden)
     * @return true if the scenario was successfully created, false otherwise
     */
    public boolean createScenario(String name, Map map, int startYear, int endYear,
                                  List<String> locomotiveTypes, PortBehavior portBehavior,
                                  double budget, List<HistoricalEvent> historicalEvents) {

        Employee employee = getEmployeeFromSession();
        Optional<Organization> org = orgRepo.getOrganizationByEmployee(employee);

        if (org.isEmpty()) {
            System.out.println("Erro: Funcionário não pertence a nenhuma organização.");
            return false;
        }

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

        if (startYear >= endYear) {
            System.out.println("Erro: Ano de início deve ser anterior ao ano de fim.");
            return false;
        }

        if (budget <= 0) {
            System.out.println("Erro: Orçamento deve ser positivo.");
            return false;
        }

        this.startYear = startYear;
        List<HistoricalEvent> historicalEvents1 = loadApplicableHistoricalEvents(startYear);

        Scenario newScenario = new Scenario(name, startYear, endYear,
                availableLocomotiveTypes, historicalEvents1,
                portBehavior, budget, map);

        Optional<Scenario> added = scenarioRepo.add(newScenario);

        if (added.isPresent()) {
            this.scenario = added.get();
            System.out.println("Cenário criado com sucesso: " + name);
            return true;
        } else {
            System.out.println("Erro: Cenário não foi criado (possível duplicado ou erro interno).");
            return false;
        }
    }

    /**
     * Deducts a given amount from the scenario's budget.
     *
     * @param amount the amount to be spent
     * @throws IllegalArgumentException if the amount is negative or exceeds the current budget
     */
    public void spendBudget(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Valor a gastar não pode ser negativo.");
        }
        if (amount > scenario.getBudget()) {
            throw new IllegalArgumentException("Orçamento insuficiente.");
        }
        scenario.setBudget(scenario.getBudget() - amount);
    }

    /**
     * Adds a given amount to the scenario's budget.
     *
     * @param amount the amount to add
     * @throws IllegalArgumentException if the amount is negative
     */
    public void receiveBudget(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount to receive cannot be negative.");
        }
        scenario.setBudget(scenario.getBudget() + amount);
    }

    /**
     * Retrieves the currently created scenario.
     *
     * @return the created scenario
     * @throws IllegalStateException if no scenario has been created yet
     */
    public Scenario getScenario() {
        if (scenario == null) {
            throw new IllegalStateException("No scenario has been created yet");
        }
        return scenario;
    }

    /**
     * Gets the employee associated with the current user session.
     *
     * @return the logged-in employee
     */
    private Employee getEmployeeFromSession() {
        Email email = authRepo.getCurrentUserSession().getUserId();
        return new Employee(email.getEmail());
    }
}
