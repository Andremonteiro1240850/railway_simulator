package pt.ipp.isep.dei.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.domain.Station;
import pt.ipp.isep.dei.domain.enums.StationTypes;
import pt.ipp.isep.dei.repository.Repositories;
import pt.ipp.isep.dei.repository.StationRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ListStationControllerTest {
    private ListStationController controller;
    private StationRepository stationRepo;
    private Station station1;
    private Station station2;

    @BeforeEach
    void setUp() {
        // Get the singleton instance of StationRepository
        stationRepo = Repositories.getInstance().getStationRepository();
        controller = new ListStationController();
        
        // Create test stations with correct parameters
        station1 = new Station("Test Station 1", StationTypes.STATION.getType(), "NE", "Test Scenario", 
            StationTypes.STATION.getArea(), 10, 10);
        station2 = new Station("Test Station 2", StationTypes.TERMINAL.getType(), "SW", "Test Scenario", 
            StationTypes.TERMINAL.getArea(), 20, 20);
            
        // Clear the repository before each test
        List<Station> stations = stationRepo.getAll();
        for (Station station : stations) {
            stationRepo.findByReference(station.getReference()).ifPresent(s -> {
                // Remove a estação diretamente da lista interna
                stationRepo.addUpgradeToStation(s.getReference(), null);
                stationRepo.addUpgradeToStation(s.getReference(), null);
            });
        }
    }

    @Test
    void testGetAllStationsWhenEmpty() {
        // Act
        List<Station> stations = controller.getAllStations();

        // Assert
        assertNotNull(stations);
        assertTrue(stations.isEmpty());
    }

    @Test
    void testGetAllStationsWithStations() {
        // Arrange
        stationRepo.add(station1);
        stationRepo.add(station2);

        // Act
        List<Station> stations = controller.getAllStations();

        // Assert
        assertNotNull(stations);
        assertEquals(2, stations.size());
        assertTrue(stations.stream().anyMatch(s -> s.getReference().equals(station1.getReference())));
        assertTrue(stations.stream().anyMatch(s -> s.getReference().equals(station2.getReference())));
    }

    @Test
    void testGetAllStationsAfterAddingStation() {
        // Arrange
        stationRepo.add(station1);

        // Act
        List<Station> stations = controller.getAllStations();

        // Assert
        assertNotNull(stations);
        assertEquals(1, stations.size());
        assertTrue(stations.stream().anyMatch(s -> s.getReference().equals(station1.getReference())));
    }

    @Test
    void testGetAllStationsAfterRemovingStation() {
        // Arrange
        stationRepo.add(station1);
        stationRepo.add(station2);
        
        // Remove station1 by using addUpgradeToStation with null twice
        stationRepo.addUpgradeToStation(station1.getReference(), null);
        stationRepo.addUpgradeToStation(station1.getReference(), null);

        // Act
        List<Station> result = controller.getAllStations();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertFalse(result.stream().anyMatch(s -> s.getReference().equals(station1.getReference())));
        assertTrue(result.stream().anyMatch(s -> s.getReference().equals(station2.getReference())));
    }
} 