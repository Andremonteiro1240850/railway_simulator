package pt.ipp.isep.dei.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
/**
 * Represents the behavior of a port in terms of cargo operations.
 * Tracks three types of cargo operations: import, export, and transformation.
 * This class provides methods to manage and query these cargo operations.
 */

public class PortBehavior {
    private List<String> importCargo;
    private List<String> exportCargo;
    private List<String> transformCargo;

    public PortBehavior() {
        this.importCargo = new ArrayList<>();
        this.exportCargo = new ArrayList<>();
        this.transformCargo = new ArrayList<>();
    }
    /**
     * Gets a copy of the import cargo list.
     *
     * @return A new ArrayList containing all import cargo items
     */


    // Getters
    public List<String> getImportCargo() {
        return new ArrayList<>(importCargo);
    }
    /**
     * Gets a copy of the export cargo list.
     *
     * @return A new ArrayList containing all export cargo items
     */

    public List<String> getExportCargo() {
        return new ArrayList<>(exportCargo);
    }
    /**
     * Gets a copy of the transformation cargo list.
     *
     * @return A new ArrayList containing all transformation cargo items
     */

    public List<String> getTransformCargo() {
        return new ArrayList<>(transformCargo);
    }
    /**
     * Replaces the current import cargo list with a new one.
     *
     * @param importCargo The new list of import cargo items
     */

    // Setters
    public void setImportCargo(List<String> importCargo) {
        this.importCargo = new ArrayList<>(importCargo);
    }
    /**
     * Replaces the current export cargo list with a new one.
     *
     * @param exportCargo The new list of export cargo items
     */

    public void setExportCargo(List<String> exportCargo) {
        this.exportCargo = new ArrayList<>(exportCargo);
    }
    /**
     * Replaces the current transformation cargo list with a new one.
     *
     * @param transformCargo The new list of transformation cargo items
     */

    public void setTransformCargo(List<String> transformCargo) {
        this.transformCargo = new ArrayList<>(transformCargo);
    }
    /**
     * Adds a new cargo item to the import list if not already present.
     *
     * @param cargo The cargo item to add
     */

    // Métodos para adicionar/remover itens
    public void addImportCargo(String cargo) {
        if (!importCargo.contains(cargo)) {
            importCargo.add(cargo);
        }
    }

    /**
     * Adds a new cargo item to the export list if not already present.
     *
     * @param cargo The cargo item to add
     */

    public void addExportCargo(String cargo) {
        if (!exportCargo.contains(cargo)) {
            exportCargo.add(cargo);
        }
    }
    /**
     * Adds a new cargo item to the transformation list if not already present.
     *
     * @param cargo The cargo item to add
     */

    public void addTransformCargo(String cargo) {
        if (!transformCargo.contains(cargo)) {
            transformCargo.add(cargo);
        }
    }
    /**
     * Removes a cargo item from the import list.
     *
     * @param cargo The cargo item to remove
     */

    public void removeImportCargo(String cargo) {
        importCargo.remove(cargo);
    }
    /**
     * Removes a cargo item from the export list.
     *
     * @param cargo The cargo item to remove
     */

    public void removeExportCargo(String cargo) {
        exportCargo.remove(cargo);
    }
    /**
     * Removes a cargo item from the transformation list.
     *
     * @param cargo The cargo item to remove
     */

    public void removeTransformCargo(String cargo) {
        transformCargo.remove(cargo);
    }

    /**
     * Compares this PortBehavior to another object for equality.
     * Two PortBehaviors are equal if they have the same cargo items in all three categories.
     *
     * @param o The object to compare with
     * @return true if the objects are equal, false otherwise
     */

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