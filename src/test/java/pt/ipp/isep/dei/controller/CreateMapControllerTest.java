package pt.ipp.isep.dei.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.domain.Map;
import pt.ipp.isep.dei.domain.Organization;
import pt.ipp.isep.dei.domain.enums.ScaleUnit;
import pt.ipp.isep.dei.repository.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CreateMapControllerTest {
    private CreateMapController controller;
    private CreateMapRepository mapRepo;
    private Organization organization;
    private CityRepository cityRepository;
    private IndustryRepository industryRepository;
    private StationRepository stationRepository;

    @BeforeEach
    void setUp() {
        mapRepo = new CreateMapRepository();
        cityRepository = new CityRepository();
        industryRepository = new IndustryRepository();
        stationRepository = new StationRepository();
        controller = new CreateMapController();
        organization = new Organization("123456789", mapRepo, cityRepository, industryRepository, stationRepository);
    }

    @Test
    void testCreateMapWithValidData() {
        // Arrange
        String name = "Test Map";
        int width = 100;
        int height = 100;
        ScaleUnit scale = ScaleUnit.KM;

        // Act
        Optional<String> result = controller.createMap(name, width, height, scale, organization);

        // Assert
        assertTrue(result.isPresent());
        assertTrue(result.get().contains("✅"));
    }

    @Test
    void testCreateMapWithInvalidName() {
        // Arrange
        String name = "Test@Map"; // Invalid character
        int width = 100;
        int height = 100;
        ScaleUnit scale = ScaleUnit.KM;

        // Act
        Optional<String> result = controller.createMap(name, width, height, scale, organization);

        // Assert
        assertTrue(result.isPresent());
        assertTrue(result.get().contains("❌"));
    }

    @Test
    void testCreateMapWithInvalidDimensions() {
        // Arrange
        String name = "Test Map";
        int width = -100; // Invalid dimension
        int height = 100;
        ScaleUnit scale = ScaleUnit.KM;

        // Act
        Optional<String> result = controller.createMap(name, width, height, scale, organization);

        // Assert
        assertTrue(result.isPresent());
        assertTrue(result.get().contains("❌"));
    }

    @Test
    void testCreateMapWithNullScale() {
        // Arrange
        String name = "Test Map";
        int width = 100;
        int height = 100;
        ScaleUnit scale = null;

        // Act
        Optional<String> result = controller.createMap(name, width, height, scale, organization);

        // Assert
        assertTrue(result.isPresent());
        assertTrue(result.get().contains("❌"));
    }

    @Test
    void testCreateMapWithNullOrganization() {
        // Arrange
        String name = "Test Map";
        int width = 100;
        int height = 100;
        ScaleUnit scale = ScaleUnit.KM;
        Organization nullOrg = null;

        // Act
        Optional<String> result = controller.createMap(name, width, height, scale, nullOrg);

        // Assert
        assertTrue(result.isPresent());
        assertTrue(result.get().contains("❌"));
    }

    @Test
    void testCreateDuplicateMap() {
        // Arrange
        String name = "Test Map";
        int width = 100;
        int height = 100;
        ScaleUnit scale = ScaleUnit.KM;

        // Act
        controller.createMap(name, width, height, scale, organization);
        Optional<String> duplicateResult = controller.createMap(name, width, height, scale, organization);

        // Assert
        assertTrue(duplicateResult.isPresent());
        assertTrue(duplicateResult.get().contains("❌"));
    }
} 