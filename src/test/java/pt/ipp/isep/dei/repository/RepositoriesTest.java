package pt.ipp.isep.dei.repository;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RepositoriesTest {

    @Test
    void getInstance_ShouldReturnSameInstance() {
        // Act
        Repositories instance1 = Repositories.getInstance();
        Repositories instance2 = Repositories.getInstance();

        // Assert
        assertNotNull(instance1);
        assertNotNull(instance2);
        assertSame(instance1, instance2);
    }

    @Test
    void getOrganizationRepository_ShouldReturnOrganizationRepository() {
        // Act
        OrganizationRepository repository = Repositories.getInstance().getOrganizationRepository();

        // Assert
        assertNotNull(repository);
        assertTrue(repository instanceof OrganizationRepository);
    }

    @Test
    void getTaskCategoryRepository_ShouldReturnTaskCategoryRepository() {
        // Act
        TaskCategoryRepository repository = Repositories.getInstance().getTaskCategoryRepository();

        // Assert
        assertNotNull(repository);
        assertTrue(repository instanceof TaskCategoryRepository);
    }

    @Test
    void getAuthenticationRepository_ShouldReturnAuthenticationRepository() {
        // Act
        AuthenticationRepository repository = Repositories.getInstance().getAuthenticationRepository();

        // Assert
        assertNotNull(repository);
        assertTrue(repository instanceof AuthenticationRepository);
    }

    @Test
    void getMapRepository_ShouldReturnCreateMapRepository() {
        // Act
        CreateMapRepository repository = Repositories.getInstance().getMapRepository();

        // Assert
        assertNotNull(repository);
        assertTrue(repository instanceof CreateMapRepository);
    }

    @Test
    void getCityRepository_ShouldReturnCityRepository() {
        // Act
        CityRepository repository = Repositories.getInstance().getCityRepository();

        // Assert
        assertNotNull(repository);
        assertTrue(repository instanceof CityRepository);
    }

    @Test
    void getIndustryRepository_ShouldReturnIndustryRepository() {
        // Act
        IndustryRepository repository = Repositories.getInstance().getIndustryRepository();

        // Assert
        assertNotNull(repository);
        assertTrue(repository instanceof IndustryRepository);
    }

    @Test
    void getStationRepository_ShouldReturnStationRepository() {
        // Act
        StationRepository repository = Repositories.getInstance().getStationRepository();

        // Assert
        assertNotNull(repository);
        assertTrue(repository instanceof StationRepository);
    }

    @Test
    void getUpgradeBuildingsRepository_ShouldReturnUpgradeBuildingsRepository() {
        // Act
        UpgradeBuildingsRepository repository = Repositories.getInstance().getUpgradeBuildingsRepository();

        // Assert
        assertNotNull(repository);
        assertTrue(repository instanceof UpgradeBuildingsRepository);
    }

    @Test
    void getLocomotiveRepository_ShouldReturnLocomotiveRepository() {
        // Act
        LocomotiveRepository repository = Repositories.getInstance().getLocomotiveRepository();

        // Assert
        assertNotNull(repository);
        assertTrue(repository instanceof LocomotiveRepository);
    }

    @Test
    void getCargoRepository_ShouldReturnCargoRepository() {
        // Act
        CargoRepository repository = Repositories.getInstance().getCargoRepository();

        // Assert
        assertNotNull(repository);
        assertTrue(repository instanceof CargoRepository);
    }

    @Test
    void getScenarioRepository_ShouldReturnScenarioRepository() {
        // Act
        ScenarioRepository repository = Repositories.getInstance().getScenarioRepository();

        // Assert
        assertNotNull(repository);
        assertTrue(repository instanceof ScenarioRepository);
    }

    @Test
    void getCargoRepository_ShouldReturnSameInstance() {
        // Act
        CargoRepository repository1 = Repositories.getInstance().getCargoRepository();
        CargoRepository repository2 = Repositories.getInstance().getCargoRepository();

        // Assert
        assertNotNull(repository1);
        assertNotNull(repository2);
        assertSame(repository1, repository2);
    }
} 