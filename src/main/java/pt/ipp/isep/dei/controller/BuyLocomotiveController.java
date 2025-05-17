package pt.ipp.isep.dei.controller;

import pt.ipp.isep.dei.domain.*;
import pt.ipp.isep.dei.repository.*;
import pt.isep.lei.esoft.auth.domain.model.Email;

import java.util.List;
import java.util.Optional;

public class BuyLocomotiveController {
    private final OrganizationRepository orgRepo;
    private final AuthenticationRepository authRepo;
    private final ListLocomotivesRepository listLocoRepo;
    private final LocomotiveRepository locoRepo;
    private final ScenarioManager scenarioService;  // Usa o Service ao invés do Repository

    public BuyLocomotiveController() {
        this(Repositories.getInstance().getOrganizationRepository(),
                Repositories.getInstance().getAuthenticationRepository(),
                Repositories.getInstance().getListLocomotivesRepository(),
                Repositories.getInstance().getLocomotiveRepository(),
                new ScenarioManager(Repositories.getInstance().getScenarioRepository()));
    }

    public BuyLocomotiveController(OrganizationRepository orgRepo,
                                   AuthenticationRepository authRepo,
                                   ListLocomotivesRepository listLocoRepo,
                                   LocomotiveRepository locoRepo,
                                   ScenarioManager scenarioService) {
        this.orgRepo = orgRepo;
        this.authRepo = authRepo;
        this.listLocoRepo = listLocoRepo;
        this.locoRepo = locoRepo;
        this.scenarioService = scenarioService;
    }

    public List<Locomotive> getAvailableLocomotives() {
        // Obtém o ano atual através do ScenarioService
        int currentYear = scenarioService.getCurrentYear();
        return listLocoRepo.getAvailableLocomotives(currentYear);
    }

    public Optional<Locomotive> buyLocomotive(String reference) {
        Employee employee = getEmployeeFromSession();
        Optional<Organization> org = orgRepo.getOrganizationByEmployee(employee);

        if (org.isEmpty()) return Optional.empty();

        Optional<Locomotive> locoOpt = listLocoRepo.findByReference(reference);
        if (locoOpt.isEmpty()) return Optional.empty();

        // Verifica se a organização tem fundos suficientes
        Organization organization = org.get();
        Locomotive locomotive = locoOpt.get();

        if (organization.getBudget() >= locomotive.getPrice()) {
            organization.deductFromBudget(locomotive.getPrice());
            return locoRepo.add(locomotive);
        }

        return Optional.empty();
    }

    private Employee getEmployeeFromSession() {
        Email email = authRepo.getCurrentUserSession().getUserId();
        return new Employee(email.getEmail());
    }
}