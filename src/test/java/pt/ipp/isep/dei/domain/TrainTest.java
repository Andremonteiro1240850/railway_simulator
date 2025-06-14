package pt.ipp.isep.dei.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;
import pt.ipp.isep.dei.domain.enums.CarriageList;
import pt.ipp.isep.dei.domain.enums.ListLocomotives;

class TrainTest {
    private Train train;
    private Locomotive locomotive;
    private List<Carriage> carriages;

    @BeforeEach
    void setUp() {
        // Create a locomotive using the ListLocomotives enum
        locomotive = ListLocomotives.AMERICAN_C.toLocomotive();
        
        // Create carriages using the CarriageList enum
        carriages = new ArrayList<>();
        carriages.add(CarriageList.COAL_HOPPER.toCarriage());
        carriages.add(CarriageList.IRON_ORE_HOPPER.toCarriage());
        
        train = new Train(locomotive, carriages);
    }

    @Test
    void testConstructor() {
        assertNotNull(train);
        assertEquals(locomotive, train.getLocomotive());
        assertEquals(2, train.getCarriageList().size());
        assertTrue(train.getCarriageList().contains(carriages.get(0)));
        assertTrue(train.getCarriageList().contains(carriages.get(1)));
    }

    @Test
    void testGetLocomotive() {
        assertEquals(locomotive, train.getLocomotive());
    }

    @Test
    void testGetCarriageList() {
        List<Carriage> returnedCarriages = train.getCarriageList();
        assertEquals(2, returnedCarriages.size());
        assertTrue(returnedCarriages.contains(carriages.get(0)));
        assertTrue(returnedCarriages.contains(carriages.get(1)));
        
        // Test that the returned list is a copy
        returnedCarriages.add(CarriageList.PASSENGER_CAR.toCarriage());
        assertEquals(2, train.getCarriageList().size());
    }

    @Test
    void testSetLocomotive() {
        Locomotive newLocomotive = ListLocomotives.IRON_DUKE.toLocomotive();
        train.setLocomotive(newLocomotive);
        assertEquals(newLocomotive, train.getLocomotive());
    }

    @Test
    void testAddCarriage() {
        Carriage newCarriage = CarriageList.PASSENGER_CAR.toCarriage();
        train.addCarriage(newCarriage);
        assertEquals(3, train.getCarriageList().size());
        assertTrue(train.getCarriageList().contains(newCarriage));
    }

    @Test
    void testAddNullCarriage() {
        train.addCarriage(null);
        assertEquals(2, train.getCarriageList().size());
    }

    @Test
    void testAddCarriageFromEnum() {
        train.addCarriageFromEnum(CarriageList.PASSENGER_CAR);
        assertEquals(3, train.getCarriageList().size());
        assertTrue(train.getCarriageList().stream()
            .anyMatch(c -> c.getName().equals("Passenger Car")));
    }

    @Test
    void testAddNullCarriageFromEnum() {
        train.addCarriageFromEnum(null);
        assertEquals(2, train.getCarriageList().size());
    }

    @Test
    void testRemoveCarriage() {
        Carriage carriageToRemove = carriages.get(0);
        assertTrue(train.removeCarriage(carriageToRemove));
        assertEquals(1, train.getCarriageList().size());
        assertFalse(train.getCarriageList().contains(carriageToRemove));
    }

    @Test
    void testRemoveNonExistentCarriage() {
        Carriage nonExistentCarriage = CarriageList.PASSENGER_CAR.toCarriage();
        assertFalse(train.removeCarriage(nonExistentCarriage));
        assertEquals(2, train.getCarriageList().size());
    }

    @Test
    void testToString() {
        String trainString = train.toString();
        assertTrue(trainString.contains("Train with Locomotive: " + locomotive.getReference()));
        assertTrue(trainString.contains("Carriages:"));
        assertTrue(trainString.contains(" - " + carriages.get(0).toString()));
        assertTrue(trainString.contains(" - " + carriages.get(1).toString()));
    }

    @Test
    void testToStringWithNoCarriages() {
        Train emptyTrain = new Train(locomotive, new ArrayList<>());
        String trainString = emptyTrain.toString();
        assertTrue(trainString.contains("Train with Locomotive: " + locomotive.getReference()));
        assertTrue(trainString.contains("Carriages:"));
        assertTrue(trainString.contains(" - None"));
    }
} 