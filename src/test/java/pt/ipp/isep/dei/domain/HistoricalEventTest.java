package pt.ipp.isep.dei.domain;

import pt.ipp.isep.dei.domain.enums.Eventos;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class HistoricalEventTest {
    
    @Test
    void testConstructorWithValidParameters() {
        HistoricalEvent event = new HistoricalEvent(Eventos.THE_IRON_SEED);
        assertNotNull(event);
        assertEquals(Eventos.THE_IRON_SEED, event.getEvento());
        assertEquals("The Iron Seed", event.getName());
        assertEquals(1830, event.getStartYear());
        assertEquals(1850, event.getEndYear());
        assertEquals("Início das ferrovias nos EUA, abre novas oportunidades de transporte e industrialização.", event.getEffect());
    }

    @Test
    void testConstructorWithNullEvento() {
        assertThrows(IllegalArgumentException.class, () ->
            new HistoricalEvent(null)
        );
    }

    @Test
    void testGetEvento() {
        HistoricalEvent event = new HistoricalEvent(Eventos.THE_IRON_SEED);
        assertEquals(Eventos.THE_IRON_SEED, event.getEvento());
    }

    @Test
    void testGetName() {
        HistoricalEvent event = new HistoricalEvent(Eventos.THE_IRON_SEED);
        assertEquals("The Iron Seed", event.getName());
    }

    @Test
    void testGetStartYear() {
        HistoricalEvent event = new HistoricalEvent(Eventos.THE_IRON_SEED);
        assertEquals(1830, event.getStartYear());
    }

    @Test
    void testGetEndYear() {
        HistoricalEvent event = new HistoricalEvent(Eventos.THE_IRON_SEED);
        assertEquals(1850, event.getEndYear());
    }

    @Test
    void testGetEffect() {
        HistoricalEvent event = new HistoricalEvent(Eventos.THE_IRON_SEED);
        assertEquals("Início das ferrovias nos EUA, abre novas oportunidades de transporte e industrialização.", event.getEffect());
    }

    @Test
    void testSetEvento() {
        HistoricalEvent event = new HistoricalEvent(Eventos.THE_IRON_SEED);
        event.setEvento(Eventos.WHISTLE_STOPS_AND_PROMISES);
        assertEquals(Eventos.WHISTLE_STOPS_AND_PROMISES, event.getEvento());
    }

    @Test
    void testEquals() {
        HistoricalEvent event1 = new HistoricalEvent(Eventos.THE_IRON_SEED);
        HistoricalEvent event2 = new HistoricalEvent(Eventos.THE_IRON_SEED);
        HistoricalEvent event3 = new HistoricalEvent(Eventos.WHISTLE_STOPS_AND_PROMISES);
        
        assertEquals(event1, event2);
        assertNotEquals(event1, event3);
    }

    @Test
    void testHashCode() {
        HistoricalEvent event1 = new HistoricalEvent(Eventos.THE_IRON_SEED);
        HistoricalEvent event2 = new HistoricalEvent(Eventos.THE_IRON_SEED);
        assertEquals(event1.hashCode(), event2.hashCode());
    }

    @Test
    void testToString() {
        HistoricalEvent event = new HistoricalEvent(Eventos.THE_IRON_SEED);
        String toString = event.toString();
        assertTrue(toString.contains("HistoricalEvent"));
        assertTrue(toString.contains("THE_IRON_SEED"));
    }

    @Test
    void testClone() {
        HistoricalEvent event1 = new HistoricalEvent(Eventos.THE_IRON_SEED);
        HistoricalEvent event2 = event1.clone();
        assertEquals(event1, event2);
        assertNotSame(event1, event2);
    }
} 