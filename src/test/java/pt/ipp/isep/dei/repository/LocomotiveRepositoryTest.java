package pt.ipp.isep.dei.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.domain.Locomotive;
import pt.ipp.isep.dei.domain.enums.LocomotiveType;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LocomotiveRepositoryTest {

    private LocomotiveRepository repository;
    private Locomotive testLocomotive;

    @BeforeEach
    void setUp() {
        repository = new LocomotiveRepository();
        testLocomotive = new Locomotive("Test Loco", LocomotiveType.STEAM, 100000.0, 5000.0, 1000, 2.5, 1900, 50.0);
    }

    @Test
    void addPurchasedLocomotive_ValidLocomotive_ShouldAddLocomotive() {
        // Act
        repository.addPurchasedLocomotive(testLocomotive);

        // Assert
        List<Locomotive> locomotives = repository.getPurchasedLocomotives();
        assertEquals(1, locomotives.size());
        assertEquals(testLocomotive, locomotives.get(0));
    }

    @Test
    void addPurchasedLocomotive_NullLocomotive_ShouldThrowException() {
        // Assert
        assertThrows(NullPointerException.class, () -> {
            repository.addPurchasedLocomotive(null);
        });
    }

    @Test
    void getPurchasedLocomotives_EmptyRepository_ShouldReturnEmptyList() {
        // Act
        List<Locomotive> result = repository.getPurchasedLocomotives();

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    void getPurchasedLocomotives_WithLocomotives_ShouldReturnAllLocomotives() {
        // Arrange
        Locomotive loco1 = new Locomotive("Loco 1", LocomotiveType.DIESEL, 150000.0, 7500.0, 1500, 3.0, 1950, 75.0);
        Locomotive loco2 = new Locomotive("Loco 2", LocomotiveType.ELECTRIC, 200000.0, 10000.0, 2000, 3.5, 1960, 100.0);
        repository.addPurchasedLocomotive(loco1);
        repository.addPurchasedLocomotive(loco2);

        // Act
        List<Locomotive> result = repository.getPurchasedLocomotives();

        // Assert
        assertEquals(2, result.size());
        assertTrue(result.contains(loco1));
        assertTrue(result.contains(loco2));
    }
} 