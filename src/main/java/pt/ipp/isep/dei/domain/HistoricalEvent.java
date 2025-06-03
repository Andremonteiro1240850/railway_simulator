package pt.ipp.isep.dei.domain;

import pt.ipp.isep.dei.domain.enums.Eventos;
import java.util.Objects;

/**
 * Represents a historical event defined by a fixed set of scenarios (enum).
 */
public class HistoricalEvent {

    private Eventos evento;

    /**
     * Constructs a new HistoricalEvent from an Eventos enum.
     *
     * @param evento The event enum representing this historical event.
     */
    public HistoricalEvent(Eventos evento) {
        this.evento = evento;
    }

    // Getter for the event enum
    public Eventos getEvento() {
        return evento;
    }

    // Convenience getters
    public String getName() {
        return evento.getNome();
    }

    public int getStartYear() {
        return evento.getDataInicio();
    }

    public int getEndYear() {
        return evento.getDataFim();
    }

    public String getEffect() {
        return evento.getEfeitoNoJogo();
    }

    public void setEvento(Eventos evento) {
        this.evento = evento;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HistoricalEvent)) return false;
        HistoricalEvent that = (HistoricalEvent) o;
        return evento == that.evento;
    }

    @Override
    public int hashCode() {
        return Objects.hash(evento);
    }

    @Override
    public String toString() {
        return "HistoricalEvent{" +
                "evento=" + evento +
                '}';
    }

    public HistoricalEvent clone() {
        return new HistoricalEvent(this.evento);
    }
}
