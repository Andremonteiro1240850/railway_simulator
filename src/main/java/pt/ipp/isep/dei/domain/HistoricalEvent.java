package pt.ipp.isep.dei.domain;

import java.util.Objects;

public class HistoricalEvent {
    private String name;
    private int year;
    private String description;
    private String effect;

    public HistoricalEvent(String name, int year, String description, String effect) {
        this.name = name;
        this.year = year;
        this.description = description;
        this.effect = effect;
    }

    // Getters
    public String getName() {
        return name;
    }

    public int getYear() {
        return year;
    }

    public String getDescription() {
        return description;
    }

    public String getEffect() {
        return effect;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }

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

    @Override
    public String toString() {
        return "HistoricalEvent{" +
                "name='" + name + '\'' +
                ", year=" + year +
                ", description='" + description + '\'' +
                ", effect='" + effect + '\'' +
                '}';
    }

    public HistoricalEvent clone() {
        return new HistoricalEvent(name, year, description, effect);
    }
}