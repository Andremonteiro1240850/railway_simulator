package pt.ipp.isep.dei.domain;

import java.util.Objects;
/**
 * Represents a historical event that can occur during a simulation scenario,
 * containing information about the event's name, year, description, and effects.
 */

public class HistoricalEvent {
    private String name;
    private int year;
    private String description;
    private String effect;
    /**
     * Constructs a new HistoricalEvent with the specified details.
     *
     * @param name The name/title of the historical event
     * @param year The year when the event occurs
     * @param description A detailed description of the event
     * @param effect The effects or consequences of the event
     */

    public HistoricalEvent(String name, int year, String description, String effect) {
        this.name = name;
        this.year = year;
        this.description = description;
        this.effect = effect;
    }
    /**
     * Gets the name of the historical event.
     *
     * @return The event name
     */

    // Getters
    public String getName() {
        return name;
    }
    /**
     * Gets the year when the event occurs.
     *
     * @return The event year
     */

    public int getYear() {
        return year;
    }
    /**
     * Gets the description of the event.
     *
     * @return The event description
     */

    public String getDescription() {
        return description;
    }
    /**
     * Gets the effects of the event.
     *
     * @return The event effects
     */

    public String getEffect() {
        return effect;
    }

    /**
     * Sets the name of the historical event.
     *
     * @param name The new name to set
     */

    // Setters
    public void setName(String name) {
        this.name = name;
    }
    /**
     * Sets the year when the event occurs.
     *
     * @param year The new year to set
     */

    public void setYear(int year) {
        this.year = year;
    }
    /**
     * Sets the description of the event.
     *
     * @param description The new description to set
     */

    public void setDescription(String description) {
        this.description = description;
    }
    /**
     * Sets the effects of the event.
     *
     * @param effect The new effects to set
     */

    public void setEffect(String effect) {
        this.effect = effect;
    }

    /**
     * Compares this historical event to another object for equality.
     * Two events are considered equal if all their fields match.
     *
     * @param o The object to compare with
     * @return true if the objects are equal, false otherwise
     */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HistoricalEvent that = (HistoricalEvent) o;
        return year == that.year &&
                Objects.equals(name, that.name) &&
                Objects.equals(description, that.description) &&
                Objects.equals(effect, that.effect);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, year, description, effect);
    }
    /**
     * Returns a string representation of the historical event in the format:
     * "HistoricalEvent{name='[name]', year=[year], description='[description]', effect='[effect]'}"
     *
     * @return A string representation of the event
     */

    @Override
    public String toString() {
        return "HistoricalEvent{" +
                "name='" + name + '\'' +
                ", year=" + year +
                ", description='" + description + '\'' +
                ", effect='" + effect + '\'' +
                '}';
    }
    /**
     * Creates and returns a copy of this HistoricalEvent.
     *
     * @return A new HistoricalEvent with the same properties as this one
     */

    public HistoricalEvent clone() {
        return new HistoricalEvent(name, year, description, effect);
    }
}