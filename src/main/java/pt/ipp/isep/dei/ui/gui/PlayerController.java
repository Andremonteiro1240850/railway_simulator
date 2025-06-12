package pt.ipp.isep.dei.ui.gui;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import pt.ipp.isep.dei.controller.CreateScenarioController;
import pt.ipp.isep.dei.domain.HistoricalEvent;
import pt.ipp.isep.dei.domain.PortBehavior;
import pt.ipp.isep.dei.domain.Scenario;
import pt.ipp.isep.dei.domain.enums.LocomotiveType;
import pt.ipp.isep.dei.repository.Repositories;
import pt.ipp.isep.dei.repository.ScenarioRepository;

public class PlayerController implements Initializable {
    private ScenarioRepository scenarioRepo;
    @FXML private MenuItem buildStationMenuItem;
    @FXML private Label statusLabel;
    private final CreateScenarioController controller;
    private final Scanner scanner;
    
    private String scenarioName;
    private double budget;
    private int startYear;
    private int endYear;
    private List<LocomotiveType> availableLocomotiveTypes;
    private PortBehavior portBehavior;
    private List<HistoricalEvent> historicalEvents;

    public PlayerController() {
        this.controller = new CreateScenarioController();
        this.scanner = new Scanner(System.in);
        this.scenarioRepo = Repositories.getInstance().getScenarioRepository();
    }

    public void run() {
        List<Scenario> scenarios = scenarioRepo.getAll();
        if (scenarios.isEmpty()) {
            System.out.println("No scenarios available. Please create a scenario first, as an editor.");
            return;
        }
        System.out.println("\n=== Available Scenarios ===");
        for (int i = 0; i < scenarios.size(); i++) {
            Scenario scenario = scenarios.get(i);
            System.out.println((i + 1) + ". " + scenario.getName() + " | Budget: " + String.format("%.2f€", scenario.getBudget()));
        }
        System.out.print("\nSelect a scenario by number (0 to cancel) : ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (choice == 0 || choice > scenarios.size()) {
            System.out.println("No scenario selected. Returning to main menu.");
            return;
        }
        Scenario selectedScenario = scenarios.get(choice - 1);
        System.out.println("You selected: " + selectedScenario.getName());
        
        // Update scenario information
        this.scenarioName = selectedScenario.getName();
        this.budget = selectedScenario.getBudget();
        this.startYear = selectedScenario.getStartYear();
        this.endYear = selectedScenario.getEndYear();
        this.availableLocomotiveTypes = selectedScenario.getAvailableLocomotiveTypes();
        this.portBehavior = selectedScenario.getPortBehavior();
        this.historicalEvents = selectedScenario.getHistoricalEvents();
    }

    @FXML
    private void chooseScenario() {
        List<Scenario> scenarios = scenarioRepo.getAll();
        if (scenarios.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Scenarios Available");
            alert.setHeaderText(null);
            alert.setContentText("No scenarios available. Please create a scenario first, as an editor.");
            alert.showAndWait();
            return;
        }

        Dialog<Scenario> dialog = new Dialog<>();
        dialog.setTitle("Choose Scenario");
        dialog.setHeaderText("Select a scenario to play");

        ButtonType selectButtonType = new ButtonType("Select", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(selectButtonType, ButtonType.CANCEL);

        VBox content = new VBox(10);
        content.setPadding(new javafx.geometry.Insets(10));

        Label titleLabel = new Label("Available Scenarios:");
        ListView<Scenario> scenarioListView = new ListView<>();
        scenarioListView.getItems().addAll(scenarios);
        scenarioListView.setCellFactory(lv -> new ListCell<Scenario>() {
            @Override
            protected void updateItem(Scenario scenario, boolean empty) {
                super.updateItem(scenario, empty);
                if (empty || scenario == null) {
                    setText(null);
                } else {
                    setText(scenario.getName() + " | Budget: " + String.format("%.2f€", scenario.getBudget()));
                }
            }
        });

        content.getChildren().addAll(titleLabel, scenarioListView);
        dialog.getDialogPane().setContent(content);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == selectButtonType) {
                return scenarioListView.getSelectionModel().getSelectedItem();
            }
            return null;
        });

        dialog.showAndWait().ifPresent(selectedScenario -> {
            if (selectedScenario != null) {
                this.scenarioName = selectedScenario.getName();
                this.budget = selectedScenario.getBudget();
                this.startYear = selectedScenario.getStartYear();
                this.endYear = selectedScenario.getEndYear();
                this.availableLocomotiveTypes = selectedScenario.getAvailableLocomotiveTypes();
                this.portBehavior = selectedScenario.getPortBehavior();
                this.historicalEvents = selectedScenario.getHistoricalEvents();
                
                // Update the status label
                statusLabel.setText("Welcome to " + scenarioName + " | Budget: " + String.format("%.2f€", budget));
            }
        });
    }

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
        Label timePeriodLabel = new Label("Time Period: " + startYear + " - " + endYear);

        // Exibindo os tipos de locomotivas disponíveis
        Label locomotivesLabel = new Label("Available Locomotive Types:");
        ListView<LocomotiveType> locomotivesList = new ListView<>();
        locomotivesList.getItems().addAll(availableLocomotiveTypes);

        // Exibindo o comportamento do porto
        Label portBehaviorLabel = new Label("Port Behavior:\n" + portBehavior.toString());

        // Exibindo os eventos históricos
        Label historicalEventsLabel = new Label("Historical Events:");
        ListView<HistoricalEvent> historicalEventsList = new ListView<>();
        historicalEventsList.getItems().addAll(historicalEvents);

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
                portBehaviorLabel, historicalEventsLabel, historicalEventsList,
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
        // Show scenario selection dialog when the application starts
        chooseScenario();
    }
}
