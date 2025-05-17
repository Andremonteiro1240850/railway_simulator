package pt.ipp.isep.dei.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PortBehavior {
    private List<String> importCargo;
    private List<String> exportCargo;
    private List<String> transformCargo;

    public PortBehavior() {
        this.importCargo = new ArrayList<>();
        this.exportCargo = new ArrayList<>();
        this.transformCargo = new ArrayList<>();
    }

    // Getters
    public List<String> getImportCargo() {
        return new ArrayList<>(importCargo);
    }

    public List<String> getExportCargo() {
        return new ArrayList<>(exportCargo);
    }

    public List<String> getTransformCargo() {
        return new ArrayList<>(transformCargo);
    }

    // Setters
    public void setImportCargo(List<String> importCargo) {
        this.importCargo = new ArrayList<>(importCargo);
    }

    public void setExportCargo(List<String> exportCargo) {
        this.exportCargo = new ArrayList<>(exportCargo);
    }

    public void setTransformCargo(List<String> transformCargo) {
        this.transformCargo = new ArrayList<>(transformCargo);
    }

    // Métodos para adicionar/remover itens
    public void addImportCargo(String cargo) {
        if (!importCargo.contains(cargo)) {
            importCargo.add(cargo);
        }
    }

    public void addExportCargo(String cargo) {
        if (!exportCargo.contains(cargo)) {
            exportCargo.add(cargo);
        }
    }

    public void addTransformCargo(String cargo) {
        if (!transformCargo.contains(cargo)) {
            transformCargo.add(cargo);
        }
    }

    public void removeImportCargo(String cargo) {
        importCargo.remove(cargo);
    }

    public void removeExportCargo(String cargo) {
        exportCargo.remove(cargo);
    }

    public void removeTransformCargo(String cargo) {
        transformCargo.remove(cargo);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PortBehavior that = (PortBehavior) o;
        return Objects.equals(importCargo, that.importCargo) &&
                Objects.equals(exportCargo, that.exportCargo) &&
                Objects.equals(transformCargo, that.transformCargo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(importCargo, exportCargo, transformCargo);
    }

    @Override
    public String toString() {
        return "PortBehavior{" +
                "importCargo=" + importCargo +
                ", exportCargo=" + exportCargo +
                ", transformCargo=" + transformCargo +
                '}';
    }

    public PortBehavior clone() {
        PortBehavior clone = new PortBehavior();
        clone.setImportCargo(this.importCargo);
        clone.setExportCargo(this.exportCargo);
        clone.setTransformCargo(this.transformCargo);
        return clone;
    }
}