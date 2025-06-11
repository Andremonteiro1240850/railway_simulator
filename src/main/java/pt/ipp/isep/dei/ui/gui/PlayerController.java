package pt.ipp.isep.dei.ui.gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;

import java.net.URL;
import java.util.ResourceBundle;

public class PlayerController implements Initializable {

    @FXML private MenuItem buildStationMenuItem;
    @FXML private Label statusLabel;

    // Exemplo de dados do cenário
    private String scenarioName = "Map";
    private double budget = 700000.00;
    private String timePeriod = "1800 - 2010 (Current Year: 1800)";
    private String[] availableLocomotives = {"STEAM", "DIESEL", "ELECTRIC"};
    private String portBehavior = "PortBehavior{importCargo=[steel, textiles, automobiles], exportCargo=[nitrate, vegetables, cereals], transformCargo=[coal, iron, bauxite]}";
    private String historicalEvents = "None";

    // Método chamado quando o jogador clica em "Build Station"
    @FXML
    private void handleBuildStation() {
        // Exibe a janela de diálogo para comprar estação com detalhes do cenário
        showScenarioDialog();
    }

    // Função para exibir o diálogo de cenário e compra de estação
    private void showScenarioDialog() {
        // Criação de uma nova janela de diálogo
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Select Scenario Options");

        // Definindo o botão padrão para fechar o diálogo
        ButtonType buyButtonType = new ButtonType("Build Station", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(buyButtonType, ButtonType.CANCEL);

        // Criando o conteúdo do diálogo com as informações do cenário
        VBox content = new VBox();

        // Exibindo informações do cenário
        Label scenarioLabel = new Label("=== Scenario: " + scenarioName + " ===");
        Label budgetLabel = new Label("Budget: " + String.format("%.2f", budget) + "€");
        Label timePeriodLabel = new Label("Time Period: " + timePeriod);

        // Exibindo os tipos de locomotivas disponíveis
        Label locomotivesLabel = new Label("Available Locomotive Types:");
        ListView<String> locomotivesList = new ListView<>();
        locomotivesList.getItems().addAll(availableLocomotives);

        // Exibindo o comportamento do porto
        Label portBehaviorLabel = new Label("Port Behavior:\n" + portBehavior);

        // Exibindo os eventos históricos
        Label historicalEventsLabel = new Label("Historical Events:\n" + historicalEvents);

        // Adicionando as novas opções
        Label stationTypeLabel = new Label("Choose Station Type:");
        ComboBox<String> stationTypeComboBox = new ComboBox<>();
        stationTypeComboBox.getItems().addAll("Depot", "Station", "Terminal");

        Label locationLabel = new Label("Choose Location:");
        ComboBox<String> locationComboBox = new ComboBox<>();
        locationComboBox.getItems().addAll("NW", "SW", "NE", "SE");

        Label nameLabel = new Label("Enter Station Name:");
        TextField nameTextField = new TextField();

        // Adicionando os componentes ao conteúdo
        content.getChildren().addAll(
                scenarioLabel, budgetLabel, timePeriodLabel,
                locomotivesLabel, locomotivesList,
                portBehaviorLabel, historicalEventsLabel,
                stationTypeLabel, stationTypeComboBox,
                locationLabel, locationComboBox,
                nameLabel, nameTextField
        );

        // Configurando o conteúdo do diálogo
        dialog.getDialogPane().setContent(content);

        // Esperando o jogador escolher o cenário e tomar a decisão de construir a estação
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == buyButtonType) {
                // Lógica para construir a estação
                String selectedStationType = stationTypeComboBox.getValue();
                String selectedLocation = locationComboBox.getValue();
                String stationName = nameTextField.getText();

                // Aqui você pode adicionar a lógica para construir a estação no mapa
                // Exemplo: Mostrar status de construção da estação
                statusLabel.setText("Station built: " + selectedStationType + " at " + selectedLocation + " with name: " + stationName);

                // Aqui, você pode também verificar a validade do nome e das opções
                if (stationName.isEmpty()) {
                    statusLabel.setText("Please enter a valid station name.");
                }
            }
            return null;
        });

        // Exibe o diálogo
        dialog.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Configuração inicial da tabela de trens

        // Configuração inicial do status
        statusLabel.setText("Welcome to " + scenarioName + " | Budget: " + String.format("%.2f€", budget));
    }
}
