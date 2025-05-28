package pt.ipp.isep.dei.controller;

import pt.ipp.isep.dei.domain.*;
import pt.ipp.isep.dei.repository.*;
import pt.isep.lei.esoft.auth.domain.model.Email;
import java.util.List;

public class BuyLocomotiveController {
    private final LocomotiveRepository repository;

    public BuyLocomotiveController(LocomotiveRepository repository) {
        this.repository = repository;
    }

    public List<Locomotive> listAvailableLocomotives(int year) {
        return repository.availableLocomotivesForYear(year);
    }

    public Locomotive buyLocomotive(String name) {
        return repository.getByName(name); // Simples — em cenário real, seria necessário validar saldo, etc.
    }
}
