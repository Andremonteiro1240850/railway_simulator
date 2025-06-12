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
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import pt.ipp.isep.dei.controller.CreateScenarioController;
import pt.ipp.isep.dei.domain.HistoricalEvent;
import pt.ipp.isep.dei.domain.PortBehavior;
import pt.ipp.isep.dei.domain.Scenario;
import pt.ipp.isep.dei.domain.enums.LocomotiveType;
import pt.ipp.isep.dei.repository.Repositories;
import pt.ipp.isep.dei.repository.ScenarioRepository;
import pt.ipp.isep.dei.domain.Simulation;
import pt.ipp.isep.dei.domain.Player;
import pt.ipp.isep.dei.domain.Station;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class PlayerController implements Initializable {
    private ScenarioRepository scenarioRepo;
    @FXML private MenuItem buildStationMenuItem;
    @FXML private MenuItem startSimulationMenuItem;
    @FXML private MenuItem pauseSimulationMenuItem;
    @FXML private Label statusLabel;
    @FXML private Label yearLabel;
    @FXML private Label budgetLabel;
    @FXML private TableView<Station> stationList;
    @FXML private TableColumn<Station, String> stationNameColumn;
    @FXML private TableColumn<Station, String> stationTypeColumn;
    @FXML private TableColumn<Station, String> stationCoordinatesColumn;
    private final CreateScenarioController controller;
    private final Scanner scanner;
    
    private String scenarioName;
    private double budget;
    private int startYear;
    private int endYear;
    private List<LocomotiveType> availableLocomotiveTypes;
    private PortBehavior portBehavior;
    private List<HistoricalEvent> historicalEvents;
    private Simulation simulation;
    private Timeline simulationTimeline;
    private Player player;

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
                startSimulationWithScenario(selectedScenario);
                updateLabels("Cenário selecionado: " + selectedScenario.getName(), selectedScenario.getStartYear());
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupStationTable();
        chooseScenario();
    }

    private void setupStationTable() {
        stationNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        stationTypeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getType()));
        stationCoordinatesColumn.setCellValueFactory(cellData -> 
            new SimpleStringProperty("(" + cellData.getValue().getX() + ", " + cellData.getValue().getY() + ")"));
    }

    private void updateStationList() {
        if (simulation != null && simulation.getScenario() != null) {
            stationList.getItems().clear();
            stationList.getItems().addAll(simulation.getScenario().getStationsPurchased());
        }
    }

    private void startSimulationWithScenario(Scenario selectedScenario) {
        if (player == null) {
            player = new Player("player@email.com");
        }
        simulation = new Simulation(selectedScenario, player);
        updateLabels("Cenário selecionado: " + selectedScenario.getName(), selectedScenario.getStartYear());
        updateStationList();
    }

    @FXML
    private void onStartSimulation() {
        if (simulation == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Simulação não inicializada");
            alert.setHeaderText(null);
            alert.setContentText("Por favor, selecione um cenário antes de iniciar a simulação.");
            alert.showAndWait();
            return;
        }
        if (simulationTimeline != null && simulationTimeline.getStatus() == Timeline.Status.RUNNING) {
            statusLabel.setText("Simulação já em curso");
            return;
        }
        simulationTimeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            simulation.advanceYear();
            updateLabels("Simulação em curso", simulation.getCurrentYear());
            if (simulation.getCurrentYear() >= simulation.getEndYear()) {
                simulationTimeline.stop();
                updateLabels("Simulação terminada", simulation.getCurrentYear());
            }
        }));
        simulationTimeline.setCycleCount(Timeline.INDEFINITE);
        simulationTimeline.play();
        updateLabels("Simulação em curso", simulation.getCurrentYear());
    }

    @FXML
    private void onPauseSimulation() {
        if (simulationTimeline != null) {
            simulationTimeline.stop();
            updateLabels("Simulação pausada", simulation != null ? simulation.getCurrentYear() : null);
        }
    }

    private void updateLabels(String status, Integer year) {
        if (statusLabel != null) statusLabel.setText(status);
        if (yearLabel != null) yearLabel.setText("Ano atual: " + (year != null ? year : "-"));
        if (budgetLabel != null && simulation != null && simulation.getScenario() != null) {
            budgetLabel.setText("Budget: " + String.format("%.2f€", simulation.getScenario().getBudget()));
        }
    }

    // Método chamado quando o jogador clica em "Build Station"
    @FXML
    private void handleBuildStation() {
        Scenario scenario = simulation != null ? simulation.getScenario() : null;
        if (scenario == null) {
            statusLabel.setText("Erro: selecione um cenário antes de construir uma estação.");
            return;
        }

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Build Station");
        dialog.setHeaderText("Construir nova estação");

        VBox content = new VBox(10);
        content.setPadding(new javafx.geometry.Insets(10));

        // Tipo de estação
        Label typeLabel = new Label("Tipo de estação:");
        ComboBox<String> typeComboBox = new ComboBox<>();
        typeComboBox.getItems().addAll("Depot", "Station", "Terminal");
        Label costLabel = new Label("Custo: -");
        Label radiusLabel = new Label("Raio: -");
        typeComboBox.valueProperty().addListener((obs, oldVal, newVal) -> {
            switch (newVal) {
                case "Depot":
                    costLabel.setText("Custo: 50000");
                    radiusLabel.setText("Raio: 3x3");
                    break;
                case "Station":
                    costLabel.setText("Custo: 100000");
                    radiusLabel.setText("Raio: 4x4");
                    break;
                case "Terminal":
                    costLabel.setText("Custo: 200000");
                    radiusLabel.setText("Raio: 5x5");
                    break;
                default:
                    costLabel.setText("Custo: -");
                    radiusLabel.setText("Raio: -");
            }
        });

        // Coordenadas
        Label coordLabel = new Label("Coordenadas (X, Y):");
        TextField xField = new TextField();
        xField.setPromptText("X");
        TextField yField = new TextField();
        yField.setPromptText("Y");

        // Nome
        Label nameLabel = new Label("Nome da estação:");
        TextField nameField = new TextField();
        nameField.setPromptText("Nome");

        content.getChildren().addAll(typeLabel, typeComboBox, costLabel, radiusLabel, coordLabel, xField, yField, nameLabel, nameField);
        dialog.getDialogPane().setContent(content);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                String type = typeComboBox.getValue();
                String xStr = xField.getText();
                String yStr = yField.getText();
                String name = nameField.getText();
                if (type == null || xStr.isEmpty() || yStr.isEmpty() || name.isEmpty()) {
                    statusLabel.setText("Preencha todos os campos.");
                    return null;
                }
                int x, y, cost, area;
                String typeLower = type.toLowerCase();
                switch (type) {
                    case "Depot": cost = 50000; area = 9; break;
                    case "Station": cost = 100000; area = 16; break;
                    case "Terminal": cost = 200000; area = 25; break;
                    default: cost = 0; area = 0;
                }
                try {
                    x = Integer.parseInt(xStr);
                    y = Integer.parseInt(yStr);
                } catch (NumberFormatException e) {
                    statusLabel.setText("Coordenadas inválidas.");
                    return null;
                }
                // Verificar orçamento
                if (scenario.getBudget() < cost) {
                    statusLabel.setText("Orçamento insuficiente para construir esta estação.");
                    return null;
                }
                // Verificar se já existe estação nessas coordenadas
                boolean exists = scenario.getStationsPurchased().stream().anyMatch(s -> s.getX() == x && s.getY() == y);
                if (exists) {
                    statusLabel.setText("Já existe uma estação nessas coordenadas.");
                    return null;
                }
                // Criar estação
                Station station = new Station(name, typeLower, "-", scenario.getName(), area, x, y);
                scenario.addStationPurchased(station);
                scenario.setBudget(scenario.getBudget() - cost);
                Repositories.getInstance().getStationRepository().add(station);
                statusLabel.setText("Estação criada: " + name + " (" + type + ") em (" + x + ", " + y + ")");
                updateLabels(statusLabel.getText(), simulation.getCurrentYear());
                updateStationList();
                return null;
            }
            return null;
        });
        dialog.showAndWait();
    }
}
