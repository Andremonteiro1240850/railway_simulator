package pt.ipp.isep.dei.repository;

/**
 * Inspired on https://refactoring.guru/design-patterns/singleton/java/example
 *
 * The Repositories class works as a Singleton. It defines the getInstance method that serves as an alternative
 * to the constructor and lets client classes access the same instance of this class over and over.
 */
public class Repositories {
    private static Repositories instance;
    private final OrganizationRepository organizationRepository;
    private final AuthenticationRepository authenticationRepository;
    private final CreateMapRepository mapRepository = new CreateMapRepository();
    private final CityRepository cityRepository = new CityRepository();
    private final IndustryRepository industryRepository = new IndustryRepository();
    private final StationRepository stationRepository = new StationRepository();
    private final UpgradeBuildingsRepository upgradeBuildingsRepository = new UpgradeBuildingsRepository();

    private final ScenarioRepository scenarioRepo = new ScenarioRepository();





    public CreateMapRepository getMapRepository() {
        return mapRepository;
    }
    public CityRepository getCityRepository() {
        return cityRepository;
    }
    public IndustryRepository getIndustryRepository() {
        return industryRepository;
    }
    public StationRepository getStationRepository() {
        return stationRepository;
    }
    public UpgradeBuildingsRepository getUpgradeBuildingsRepository() {
        return upgradeBuildingsRepository;
    }
    private final LocomotiveRepository locomotiveRepo = new LocomotiveRepository();
    private static CargoRepository cargoRepository = null;

    public synchronized CargoRepository getCargoRepository() {
        if (cargoRepository == null) {
            cargoRepository = new CargoRepository();
        }
        return cargoRepository;
    }

    public LocomotiveRepository getLocomotiveRepository() {
        return locomotiveRepo;
    }

    public ScenarioRepository getScenarioRepository() {
        return scenarioRepo;
    }
    private final PlayerRepository playerRepository = new PlayerRepository();
    public PlayerRepository getPlayerRepository() {
        return playerRepository;
    }



    /**
     * The Singleton's constructor should always be private to prevent direct construction calls with the new operator.
     */
    private Repositories() {
        organizationRepository = new OrganizationRepository();
        authenticationRepository = new AuthenticationRepository();
    }

    /**
     * This is the static method that controls the access to the singleton instance.
     * On the first run, it creates a singleton object and places it into the static attribute.
     * On subsequent runs, it returns the existing object stored in the static attribute.
     */
    public static Repositories getInstance() {
        if (instance == null) {
            synchronized (Repositories.class) {
                instance = new Repositories();
            }
        }
        return instance;
    }

    public OrganizationRepository getOrganizationRepository() {
        return organizationRepository;
    }


    public AuthenticationRepository getAuthenticationRepository() {
        return authenticationRepository;
    }



}