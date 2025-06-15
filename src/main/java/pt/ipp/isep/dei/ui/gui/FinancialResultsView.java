package pt.ipp.isep.dei.ui.gui;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pt.ipp.isep.dei.domain.FinancialResults;
import pt.ipp.isep.dei.domain.Simulation;

import java.util.List;

public class FinancialResultsView {
    private final Simulation simulation;
    private final Stage stage;

    public FinancialResultsView(Simulation simulation) {
        this.simulation = simulation;
        this.stage = new Stage();
        createView();
    }

    private void createView() {
        VBox root = new VBox(10);
        root.setPadding(new Insets(10));

        // Current Year Results
        Label currentYearLabel = new Label("Current Year Results");
        currentYearLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        TextArea currentYearResults = new TextArea(simulation.getCurrentYearResults().toString());
        currentYearResults.setEditable(false);
        currentYearResults.setPrefRowCount(15);

        // Historical Results
        Label historyLabel = new Label("Historical Results");
        historyLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        ListView<String> historyList = new ListView<>();
        List<FinancialResults> history = simulation.getFinancialHistory();
        for (FinancialResults result : history) {
            historyList.getItems().add(result.toString());
        }

        // Add components to root
        root.getChildren().addAll(
            currentYearLabel,
            currentYearResults,
            new Separator(),
            historyLabel,
            historyList
        );

        // Create scene
        Scene scene = new Scene(root, 600, 800);
        stage.setTitle("Financial Results");
        stage.setScene(scene);
    }

    public void show() {
        stage.show();
    }
} 