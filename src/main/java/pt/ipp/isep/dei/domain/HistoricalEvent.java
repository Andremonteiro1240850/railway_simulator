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
     * @throws IllegalArgumentException if evento is null
     */
    public HistoricalEvent(Eventos evento) {
        if (evento == null) {
            throw new IllegalArgumentException("Event cannot be null");
        }
        this.evento = evento;
    }

    /**
     * Returns the Eventos enum associated with this historical event.
     *
     * @return the event enum.
     */
    public Eventos getEvento() {
        return evento;
    }

    /**
     * Returns the name of the historical event.
     *
     * @return the event name.
     */
    public String getName() {
        return evento.getNome();
    }

    /**
     * Returns the start year of the event.
     *
     * @return the starting year.
     */
    public int getStartYear() {
        return evento.getDataInicio();
    }

    /**
     * Returns the end year of the event.
     *
     * @return the ending year.
     */
    public int getEndYear() {
        return evento.getDataFim();
    }

    /**
     * Returns the in-game effect description of the event.
     *
     * @return the event effect.
     */
    public String getEffect() {
        return evento.getEfeitoNoJogo();
    }

    /**
     * Sets a new Eventos enum for this historical event.
     *
     * @param evento the new event to assign.
     * @throws IllegalArgumentException if evento is null
     */
    public void setEvento(Eventos evento) {
        if (evento == null) {
            throw new IllegalArgumentException("Event cannot be null");
        }
        this.evento = evento;
    }

    /**
     * Compares this HistoricalEvent to another object for equality.
     *
     * @param o the object to compare with.
     * @return true if both objects are HistoricalEvent instances with the same Eventos enum.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HistoricalEvent)) return false;
        HistoricalEvent that = (HistoricalEvent) o;
        return evento == that.evento;
    }

    /**
     * Computes the hash code of this HistoricalEvent based on the Eventos enum.
     *
     * @return the hash code.
     */
    @Override
    public int hashCode() {
        return Objects.hash(evento);
    }

    /**
     * Returns a string representation of the HistoricalEvent.
     *
     * @return a string containing the event information.
     */
    @Override
    public String toString() {
        return "HistoricalEvent{evento=" + evento.name() + "}";
    }

    /**
     * Creates and returns a copy of this HistoricalEvent.
     *
     * @return a cloned HistoricalEvent instance.
     */
    public HistoricalEvent clone() {
        return new HistoricalEvent(this.evento);
    }
}

