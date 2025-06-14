package pt.ipp.isep.dei.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class HouseBlocksTest {
    private List<int[]> houseBlocks;
    private HouseBlocks houseBlocksInstance;

    @BeforeEach
    void setUp() {
        houseBlocks = new ArrayList<>();
        houseBlocks.add(new int[]{1, 2});
        houseBlocks.add(new int[]{3, 4});
        houseBlocksInstance = new HouseBlocks(0, 0, houseBlocks);
    }

    @Test
    void testConstructorWithValidParameters() {
        assertNotNull(houseBlocksInstance);
        assertEquals(0, houseBlocksInstance.getX());
        assertEquals(0, houseBlocksInstance.getY());
        assertEquals(2, houseBlocksInstance.getHouseBlocks().size());
    }

    @Test
    void testConstructorWithNullHouseBlocks() {
        assertThrows(IllegalArgumentException.class, () ->
            new HouseBlocks(0, 0, null)
        );
    }

    @Test
    void testConstructorWithEmptyHouseBlocks() {
        assertThrows(IllegalArgumentException.class, () ->
            new HouseBlocks(0, 0, new ArrayList<>())
        );
    }

    @Test
    void testGetX() {
        assertEquals(0, houseBlocksInstance.getX());
    }

    @Test
    void testSetX() {
        houseBlocksInstance.setX(5);
        assertEquals(5, houseBlocksInstance.getX());
    }

    @Test
    void testGetY() {
        assertEquals(0, houseBlocksInstance.getY());
    }

    @Test
    void testSetY() {
        houseBlocksInstance.setY(5);
        assertEquals(5, houseBlocksInstance.getY());
    }

    @Test
    void testGetHouseBlocks() {
        List<int[]> blocks = houseBlocksInstance.getHouseBlocks();
        assertEquals(2, blocks.size());
        assertArrayEquals(new int[]{1, 2}, blocks.get(0));
        assertArrayEquals(new int[]{3, 4}, blocks.get(1));
    }

    @Test
    void testSetHouseBlocks() {
        List<int[]> newBlocks = new ArrayList<>();
        newBlocks.add(new int[]{5, 6});
        houseBlocksInstance.setHouseBlocks(newBlocks);
        assertEquals(1, houseBlocksInstance.getHouseBlocks().size());
        assertArrayEquals(new int[]{5, 6}, houseBlocksInstance.getHouseBlocks().get(0));
    }

    @Test
    void testSetHouseBlocksWithNull() {
        assertThrows(IllegalArgumentException.class, () ->
            houseBlocksInstance.setHouseBlocks(null)
        );
    }

    @Test
    void testSetHouseBlocksWithEmpty() {
        assertThrows(IllegalArgumentException.class, () ->
            houseBlocksInstance.setHouseBlocks(new ArrayList<>())
        );
    }

    @Test
    void testContainsBlock() {
        assertTrue(houseBlocksInstance.containsBlock(1, 2));
        assertTrue(houseBlocksInstance.containsBlock(3, 4));
        assertFalse(houseBlocksInstance.containsBlock(5, 6));
    }

    @Test
    void testEquals() {
        HouseBlocks sameBlocks = new HouseBlocks(0, 0, houseBlocks);
        HouseBlocks differentBlocks = new HouseBlocks(1, 1, houseBlocks);
        HouseBlocks differentCoordinates = new HouseBlocks(0, 0, List.of(new int[]{5, 6}));
        
        assertEquals(houseBlocksInstance, sameBlocks);
        assertNotEquals(houseBlocksInstance, differentBlocks);
        assertNotEquals(houseBlocksInstance, differentCoordinates);
    }

    @Test
    void testHashCode() {
        HouseBlocks sameBlocks = new HouseBlocks(0, 0, houseBlocks);
        assertEquals(houseBlocksInstance.hashCode(), sameBlocks.hashCode());
    }

    @Test
    void testToString() {
        String toString = houseBlocksInstance.toString();
        assertTrue(toString.contains("x=0"));
        assertTrue(toString.contains("y=0"));
        assertTrue(toString.contains("blocks=2"));
    }
} 