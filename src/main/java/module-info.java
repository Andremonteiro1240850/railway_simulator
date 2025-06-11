module pt.ipp.isep.dei {
    requires javafx.controls;
    requires javafx.fxml;
    requires AuthLib;
    requires java.logging;
    requires org.apache.commons.lang3;

    opens pt.ipp.isep.dei.ui.gui to javafx.fxml;
    exports pt.ipp.isep.dei;
}