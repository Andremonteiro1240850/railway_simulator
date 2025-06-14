package pt.ipp.isep.dei.controller;

import pt.ipp.isep.dei.domain.*;
import pt.ipp.isep.dei.domain.enums.Eventos;
import pt.ipp.isep.dei.domain.enums.LocomotiveType;
import pt.ipp.isep.dei.repository.*;
import pt.isep.lei.esoft.auth.domain.model.Email;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CreateScenarioController {
    private final OrganizationRepository orgRepo;
    private final AuthenticationRepository authRepo;
    private final CreateMapRepository mapRepo;
    private final ScenarioRepository scenarioRepo;
    private Scenario scenario;
    private int startYear; // Adicionar este campo
    private List<HistoricalEvent> historicalEvents;


    public CreateScenarioController() {
        this(Repositories.getInstance().getOrganizationRepository(),
                Repositories.getInstance().getAuthenticationRepository(),
                Repositories.getInstance().getMapRepository(),
                Repositories.getInstance().getScenarioRepository());
    }

    public CreateScenarioController(OrganizationRepository orgRepo,
                                    AuthenticationRepository authRepo,
                                    CreateMapRepository mapRepo,
                                    ScenarioRepository scenarioRepo) {
        this.orgRepo = orgRepo;
        this.authRepo = authRepo;
        this.mapRepo = mapRepo;
        this.scenarioRepo = scenarioRepo;
    }

    public List<Map> getAvailableMaps() {
        return mapRepo.getAll();
    }
    public List<HistoricalEvent> loadApplicableHistoricalEvents(int startYear) {
        List<HistoricalEvent> applicableEvents = new ArrayList<>();
        try {
            Class<?> eventosClass = Class.forName("pt.ipp.isep.dei.domain.enums.Eventos");
            if (eventosClass.isEnum()) {
                Object[] enumConstants = eventosClass.getEnumConstants();
                for (Object enumConstant : enumConstants) {
                    Eventos eventoEnum = (Eventos) enumConstant;
                    if (eventoEnum.getDataInicio() <= startYear && startYear <= eventoEnum.getDataFim()) {
                        applicableEvents.add(new HistoricalEvent(eventoEnum));
                    }
                }
            }
        } catch (ClassNotFoundException e) {
            System.err.println("Error: Eventos enum class not found: " + e.getMessage());
            throw new RuntimeException("Failed to load historical events: Eventos enum not found", e);
        }

        this.historicalEvents = applicableEvents;
        return applicableEvents;
    }

    public boolean createScenario(String name, Map map, int startYear, int endYear,
                                  List<String> locomotiveTypes, PortBehavior portBehavior,
                                  double budget, List<HistoricalEvent> historicalEvents) {

        // 1. Verificar o funcionário e organização
        Employee employee = getEmployeeFromSession();
        Optional<Organization> org = orgRepo.getOrganizationByEmployee(employee);

        if (org.isEmpty()) {
            System.out.println("Erro: Funcionário não pertence a nenhuma organização.");
            return false;
        }


        // 2. Converter strings para LocomotiveType e validar
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

        // 3. Validar anos (startYear < endYear)
        if (startYear >= endYear) {
            System.out.println("Erro: Ano de início deve ser anterior ao ano de fim.");
            return false;
        }

        // 4. Validar orçamento (deve ser positivo)
        if (budget <= 0) {
            System.out.println("Erro: Orçamento deve ser positivo.");
            return false;
        }

        // Definir o startYear no controller para uso no loadApplicableHistoricalEvents
        this.startYear = startYear;

        // Carregar eventos históricos aplicáveis
        List<HistoricalEvent> historicalEvents1 = loadApplicableHistoricalEvents(startYear);

        // Criar o cenário com os eventos carregados
        Scenario newScenario = new Scenario(name, startYear, endYear,
                availableLocomotiveTypes, historicalEvents1,
                portBehavior, budget,map); // <-- agora o mapa é usado


        // 6. Adicionar ao repositório e verificar resultado
        Optional<Scenario> added = scenarioRepo.add(newScenario);

        if (added.isPresent()) {
            this.scenario = added.get(); // Armazenar o cenário criado
            System.out.println("Cenário criado com sucesso: " + name);
            return true;
        } else {
            System.out.println("Erro: Cenário não foi criado (possível duplicado ou erro interno).");
            return false;
        }
    }

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
     * Increases the budget by uma certa quantia (ex: ao entregar carga na estação).
     *
     * @param amount O valor a adicionar ao orçamento.
     * @throws IllegalArgumentException se o amount for negativo.
     */
    public void receiveBudget(double amount) {
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
        Email email = authRepo.getCurrentUserSession().getUserId();
        return new Employee(email.getEmail());
    }
}