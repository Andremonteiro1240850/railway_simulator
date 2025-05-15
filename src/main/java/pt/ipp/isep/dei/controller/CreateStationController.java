package pt.ipp.isep.dei.controller;

import pt.ipp.isep.dei.domain.BudgetManager;
import pt.ipp.isep.dei.domain.RailwayNetwork;
import pt.ipp.isep.dei.repository.StationTypeRepository;

//us05

public class CreateStationController {
    private StationTypeRepository typeRepo;
    private RailwayNetwork railway;
    private BudgetManager budget;
    private CityRegistry cityRegistry;

    public CreateStationController() {
        this.typeRepo = StationTypeRepository.getInstance();
        this.railway = RailwayNetwork.getInstance();
        this.budget = BudgetManager.getInstance();
        this.cityRegistry = CityRegistry.getInstance();
    }

    public Station createStation(StationType type, Location location, String name, Point center) {
        // Validate budget
        if (!budget.canAfford(type.getCost())) {
            throw new IllegalArgumentException("Insufficient budget");
        }

        // Validate position
        PositionValidator.validatePosition(location);

        // Generate name if not provided
        if (name == null) {
            String city = cityRegistry.getNearestCity(location);
            name = StationNamingService.generateName(city, type.getName());
        }

        // Calculate center if station type requires it
        if (type.requiresCenter() && center == null) {
            center = StationGeometry.calculateCenter(type, location);
        }

        // Create and register station
        Station station = new Station(type, location, name, center);
        railway.addStation(station);
        budget.deduct(type.getCost());

        return station;
    }

    public List<StationType> getAvailableStationTypes() {
        return typeRepo.getAllTypes();
    }
}
