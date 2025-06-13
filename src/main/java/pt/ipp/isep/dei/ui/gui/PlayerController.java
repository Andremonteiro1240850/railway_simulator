package pt.ipp.isep.dei.ui.gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import pt.ipp.isep.dei.controller.CreateScenarioController;
import pt.ipp.isep.dei.domain.HistoricalEvent;
import pt.ipp.isep.dei.domain.Locomotive;
import pt.ipp.isep.dei.domain.Player;
import pt.ipp.isep.dei.domain.PortBehavior;
import pt.ipp.isep.dei.domain.Scenario;
import pt.ipp.isep.dei.domain.Simulation;
import pt.ipp.isep.dei.domain.Station;
import pt.ipp.isep.dei.domain.enums.ListLocomotives;
import pt.ipp.isep.dei.domain.enums.LocomotiveType;
import pt.ipp.isep.dei.domain.enums.StationUpgrade;
import pt.ipp.isep.dei.repository.Repositories;
import pt.ipp.isep.dei.repository.ScenarioRepository;

public class PlayerController implements Initializable {
    private ScenarioRepository scenarioRepo;
    @FXML private MenuItem buildStationMenuItem;
    @FXML private MenuItem startSimulationMenuItem;
    @FXML private MenuItem pauseSimulationMenuItem;
    @FXML private MenuItem buyLocomotiveMenuItem;
    @FXML private Label statusLabel;
    @FXML private Label yearLabel;
    @FXML private Label budgetLabel;
    @FXML private TableView<Station> stationList;
    @FXML private TableColumn<Station, String> stationNameColumn;
    @FXML private TableColumn<Station, String> stationTypeColumn;
    @FXML private TableColumn<Station, String> stationCoordinatesColumn;
    @FXML private TableView<Locomotive> locomotiveInventory;
    @FXML private TableColumn<Locomotive, String> locomotiveNameColumn;
    @FXML private TableColumn<Locomotive, String> locomotiveTypeColumn;
    @FXML private TableColumn<Locomotive, String> locomotivePowerColumn;
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
                updateLabels("Selected scenario: " + selectedScenario.getName(), selectedScenario.getStartYear());
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupStationTable();
        setupLocomotiveInventoryTable();
        chooseScenario();
    }

    private void setupStationTable() {
        // Configure the station name column
        stationNameColumn.setCellValueFactory(cellData -> {
            Station station = cellData.getValue();
            return new SimpleStringProperty(station != null ? station.getName() : "");
        });

        // Configure the station type column
        stationTypeColumn.setCellValueFactory(cellData -> {
            Station station = cellData.getValue();
            return new SimpleStringProperty(station != null ? station.getType() : "");
        });

        // Configure the coordinates column
        stationCoordinatesColumn.setCellValueFactory(cellData -> {
            Station station = cellData.getValue();
            if (station != null) {
                return new SimpleStringProperty(String.format("(%d, %d)", station.getX(), station.getY()));
            }
            return new SimpleStringProperty("");
        });

        // Make sure the table is visible
        stationList.setVisible(true);
    }

    private void setupLocomotiveInventoryTable() {
        if (locomotiveNameColumn != null)
            locomotiveNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getReference()));
        if (locomotiveTypeColumn != null)
            locomotiveTypeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getType().toString()));
        if (locomotivePowerColumn != null)
            locomotivePowerColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getPower())));
    }

    private void updateStationList() {
        List<Station> allStations = Repositories.getInstance().getStationRepository().getAll();
        stationList.getItems().clear();
        stationList.getItems().addAll(allStations);
    }

    private void updateLocomotiveInventory() {
        if (simulation != null && locomotiveInventory != null) {
            locomotiveInventory.getItems().setAll(simulation.getLocomotives());
        }
    }

    private void startSimulationWithScenario(Scenario selectedScenario) {
        if (player == null) {
            player = new Player("player@email.com");
        }
        simulation = new Simulation(selectedScenario, player);
        updateLabels("Selected scenario: " + selectedScenario.getName(), selectedScenario.getStartYear());
        updateStationList();
        updateLocomotiveInventory();
    }

    @FXML
    private void onStartSimulation() {
        if (simulation == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Simulation Error");
            alert.setHeaderText(null);
            alert.setContentText("Please select a scenario before starting the simulation.");
            alert.showAndWait();
            return;
        }
        if (simulationTimeline != null && simulationTimeline.getStatus() == Timeline.Status.RUNNING) {
            statusLabel.setText("Simulação already in progress");
            return;
        }
        simulationTimeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            simulation.advanceYear();
            updateLabels("Simulation in progress", simulation.getCurrentYear());
            if (simulation.getCurrentYear() >= simulation.getEndYear()) {
                simulationTimeline.stop();
                updateLabels("Simulation finished", simulation.getCurrentYear());
            }
        }));
        simulationTimeline.setCycleCount(Timeline.INDEFINITE);
        simulationTimeline.play();
        updateLabels("Simulation in progress", simulation.getCurrentYear());
    }

    @FXML
    private void onPauseSimulation() {
        if (simulationTimeline != null) {
            simulationTimeline.stop();
            updateLabels("Simulation paused", simulation != null ? simulation.getCurrentYear() : null);
        }
    }

    private void updateLabels(String status, Integer year) {
        if (statusLabel != null) statusLabel.setText(status);
        if (yearLabel != null) yearLabel.setText("Current year: " + (year != null ? year : "-"));
        if (budgetLabel != null && simulation != null && simulation.getScenario() != null) {
            budgetLabel.setText("Budget: " + String.format("%.2f€", simulation.getScenario().getBudget()));
        }
    }

    // Método chamado quando o jogador clica em "Build Station"
    @FXML
    private void handleBuildStation() {
        Scenario scenario = simulation != null ? simulation.getScenario() : null;
        if (scenario == null) {
            statusLabel.setText("Error: please select a scenario before building a station.");
            return;
        }

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Build Station");
        dialog.setHeaderText("Build a new station in the scenario: " + scenario.getName());

        VBox content = new VBox(10);
        content.setPadding(new javafx.geometry.Insets(10));

        // Tipo de estação
        Label typeLabel = new Label("Station type:");
        ComboBox<String> typeComboBox = new ComboBox<>();
        typeComboBox.getItems().addAll("Depot", "Station", "Terminal");
        Label costLabel = new Label("Cost: -");
        Label radiusLabel = new Label("Radius: -");
        typeComboBox.valueProperty().addListener((obs, oldVal, newVal) -> {
            switch (newVal) {
                case "Depot":
                    costLabel.setText("Cost: 50000");
                    radiusLabel.setText("Radius: 3x3");
                    break;
                case "Station":
                    costLabel.setText("Cost: 100000");
                    radiusLabel.setText("Radius: 4x4");
                    break;
                case "Terminal":
                    costLabel.setText("Cost: 200000");
                    radiusLabel.setText("Radius: 5x5");
                    break;
                default:
                    costLabel.setText("Cost: -");
                    radiusLabel.setText("Radius: -");
            }
        });

        // Coordenadas
        Label coordLabel = new Label("Coordinates (X, Y):");
        TextField xField = new TextField();
        xField.setPromptText("X");
        TextField yField = new TextField();
        yField.setPromptText("Y");

        // Nome
        Label nameLabel = new Label("Station name:");
        TextField nameField = new TextField();
        nameField.setPromptText("Name");

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
                    statusLabel.setText("Fill in all the fields.");
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
                    statusLabel.setText("Invalid coordinates.");
                    return null;
                }
                // Verificar orçamento
                if (scenario.getBudget() < cost) {
                    statusLabel.setText("Not enough budget.");
                    return null;
                }
                // Verificar se já existe estação nessas coordenadas
                boolean exists = scenario.getStationsPurchased().stream().anyMatch(s -> s.getX() == x && s.getY() == y);
                if (exists) {
                    statusLabel.setText("This position is already ocuppied.");
                    return null;
                }
                // Criar estação
                Station station = new Station(name, typeLower, "-", scenario.getName(), area, x, y);
                scenario.addStationPurchased(station);
                scenario.setBudget(scenario.getBudget() - cost);
                Repositories.getInstance().getStationRepository().add(station);
                simulation.addStation(station); // Add to player's inventory
                statusLabel.setText("Station Created: " + name + " (" + type + ") in (" + x + ", " + y + ")");
                updateLabels(statusLabel.getText(), simulation.getCurrentYear());
                updateStationList();
                return null;
            }
            return null;
        });
        dialog.showAndWait();
    }

    @FXML
    private void handleListStations() {
        // Get all stations from the repository
        List<Station> allStations = Repositories.getInstance().getStationRepository().getAll();

        // Build the string with station info
        StringBuilder sb = new StringBuilder();
        if (allStations.isEmpty()) {
            sb.append("No stations built.");
        } else {
            for (Station station : allStations) {
                sb.append("Name: ").append(station.getName())
                  .append(" | Type: ").append(station.getType())
                  .append(" | Coordinates: (").append(station.getX()).append(", ").append(station.getY()).append(")\n");
            }
        }

        // Show in an Alert
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("EBuilt Sytation");
        alert.setHeaderText("List of all built Stations:");
        alert.setContentText(sb.toString());
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE); // To show all content
        alert.showAndWait();

        // Update the table as usual
        stationList.getItems().clear();
        stationList.getItems().addAll(allStations);
        statusLabel.setText("Built Stations list updated - There is " + allStations.size() + " stations built.");
    }

    @FXML
    public void handleUpgradeStation() {
        // 1. Selecionar estação
        List<Station> allStations = Repositories.getInstance().getStationRepository().getAll();
        if (allStations.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Stations");
            alert.setHeaderText(null);
            alert.setContentText("You have no stations to upgrade.");
            alert.showAndWait();
            return;
        }
        // Custom dialog with ListView
        Dialog<Station> dialog = new Dialog<>();
        dialog.setTitle("Choose Station");
        dialog.setHeaderText("Select a station to upgrade:");
        ListView<Station> stationListView = new ListView<>();
        stationListView.getItems().addAll(allStations);
        stationListView.setCellFactory(lv -> new ListCell<Station>() {
            @Override
            protected void updateItem(Station station, boolean empty) {
                super.updateItem(station, empty);
                if (empty || station == null) {
                    setText(null);
                } else {
                    setText(station.getName() + " (" + station.getX() + ", " + station.getY() + ")");
                }
            }
        });
        VBox content = new VBox(10, stationListView);
        dialog.getDialogPane().setContent(content);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                return stationListView.getSelectionModel().getSelectedItem();
            }
            return null;
        });
        Optional<Station> selectedStationOpt = dialog.showAndWait();
        if (!selectedStationOpt.isPresent() || selectedStationOpt.get() == null) return;
        Station selectedStation = selectedStationOpt.get();

        // Buscar a estação da simulação para garantir upgrades corretos
        Station stationInSimulation = simulation.getStations().stream()
            .filter(s -> s.getReference().equals(selectedStation.getReference()))
            .findFirst()
            .orElse(selectedStation);

        // 2. Filtrar upgrades possíveis
        int currentYear = simulation != null ? simulation.getCurrentYear() : 0;
        List<StationUpgrade> installed = stationInSimulation.getUpgrades();
        List<StationUpgrade> possibleUpgrades = new ArrayList<>();
        for (StationUpgrade upgrade : StationUpgrade.values()) {
            // Só upgrades do ano atual ou anterior
            if (upgrade.getYear() > currentYear) continue;
            // Não mostrar upgrades já instalados
            if (installed.contains(upgrade)) continue;
            // Exclusividade: não pode instalar se já tem um exclusivo
            boolean exclusive = false;
            for (StationUpgrade inst : installed) {
                if (upgrade.getExclusiveWith() != null && upgrade.getExclusiveWith().equals(inst.name())) exclusive = true;
                if (inst.getExclusiveWith() != null && inst.getExclusiveWith().equals(upgrade.name())) exclusive = true;
            }
            if (exclusive) continue;
            possibleUpgrades.add(upgrade);
        }

        if (possibleUpgrades.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No Upgrades Available");
            alert.setHeaderText(null);
            alert.setContentText("No upgrades available for this station at the current year.");
            alert.showAndWait();
            return;
        }

        // 3. Mostrar upgrades possíveis para escolha
        Dialog<StationUpgrade> upgradeDialog = new Dialog<>();
        upgradeDialog.setTitle("Choose Upgrade");
        upgradeDialog.setHeaderText("Available upgrades for: " + selectedStation.getName());
        ListView<StationUpgrade> upgradeListView = new ListView<>();
        upgradeListView.getItems().addAll(possibleUpgrades);
        upgradeListView.setCellFactory(lv -> new ListCell<StationUpgrade>() {
            @Override
            protected void updateItem(StationUpgrade upg, boolean empty) {
                super.updateItem(upg, empty);
                if (empty || upg == null) {
                    setText(null);
                } else {
                    setText(upg.getDisplayName() + " (Ano: " + upg.getYear() + ", Custo: $" + upg.getCost() + ")");
                }
            }
        });
        VBox upgContent = new VBox(10, upgradeListView);
        upgradeDialog.getDialogPane().setContent(upgContent);
        upgradeDialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        upgradeDialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                return upgradeListView.getSelectionModel().getSelectedItem();
            }
            return null;
        });
        Optional<StationUpgrade> chosenUpgradeOpt = upgradeDialog.showAndWait();
        if (!chosenUpgradeOpt.isPresent() || chosenUpgradeOpt.get() == null) return;
        StationUpgrade chosenUpgrade = chosenUpgradeOpt.get();

        // 4. Verificar orçamento antes de aplicar upgrade
        Scenario scenario = simulation != null ? simulation.getScenario() : null;
        if (scenario == null) {
            Alert error = new Alert(Alert.AlertType.ERROR, "No scenario loaded.");
            error.showAndWait();
            return;
        }
        if (scenario.getBudget() < chosenUpgrade.getCost()) {
            Alert error = new Alert(Alert.AlertType.ERROR, "Not enough budget for this upgrade.");
            error.showAndWait();
            return;
        }

        // 5. Aplicar upgrade e descontar do orçamento
        if (stationInSimulation != null) {
            stationInSimulation.addUpgrade(chosenUpgrade);
        } else {
            selectedStation.addUpgrade(chosenUpgrade);
        }
        scenario.setBudget(scenario.getBudget() - chosenUpgrade.getCost());
        Alert done = new Alert(Alert.AlertType.INFORMATION);
        done.setTitle("Upgrade Applied");
        done.setHeaderText(null);
        done.setContentText("Upgrade '" + chosenUpgrade.getDisplayName() + "' applied to station '" + selectedStation.getName() + "'.\nCost: $" + chosenUpgrade.getCost());
        done.showAndWait();
    }

    @FXML
    public void handleBuyLocomotive() {
        if (simulation == null || simulation.getScenario() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Scenario Selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select a scenario first.");
            alert.showAndWait();
            return;
        }

        Scenario scenario = simulation.getScenario();
        List<LocomotiveType> allowedTypes = scenario.getAvailableLocomotiveTypes();
        int currentYear = simulation.getCurrentYear();

        System.out.println("Current Year: " + currentYear);
        System.out.println("Allowed Types: " + allowedTypes);

        // Substituir ListView por TableView para mostrar todas as locomotivas disponíveis
        TableView<Locomotive> tableView = new TableView<>();
        TableColumn<Locomotive, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getReference()));
        TableColumn<Locomotive, String> typeCol = new TableColumn<>("Type");
        typeCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getType().toString()));
        TableColumn<Locomotive, String> powerCol = new TableColumn<>("Power");
        powerCol.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getPower())));
        tableView.getColumns().addAll(nameCol, typeCol, powerCol);

        // Adicione as locomotivas filtradas
        for (ListLocomotives locoEnum : ListLocomotives.values()) {
            System.out.println("Checking locomotive: " + locoEnum.name() + 
                             " | Available Year: " + locoEnum.getAvailableYear() + 
                             " | Retired Year: " + locoEnum.getRetiredYear() + 
                             " | Type: " + locoEnum.getLocomotiveType());
            
            if (allowedTypes.contains(locoEnum.getLocomotiveType())) {
                if (locoEnum.getAvailableYear() <= currentYear && locoEnum.getRetiredYear() >= currentYear) {
                    System.out.println("Adding locomotive to list: " + locoEnum.name());
                    tableView.getItems().add(locoEnum.toLocomotive());
                }
            }
        }

        if (tableView.getItems().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No Locomotives Available");
            alert.setHeaderText(null);
            alert.setContentText("No locomotives are available for purchase at the current year (" + currentYear + ").");
            alert.showAndWait();
            return;
        }

        // Dialog
        Dialog<Locomotive> dialog = new Dialog<>();
        dialog.setTitle("Buy Locomotive");
        dialog.setHeaderText("Select a locomotive to purchase:");
        dialog.getDialogPane().setContent(tableView);
        ButtonType buyButtonType = new ButtonType("Buy", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(buyButtonType, ButtonType.CANCEL);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == buyButtonType) {
                return tableView.getSelectionModel().getSelectedItem();
            }
            return null;
        });

        dialog.showAndWait().ifPresent(selectedLocomotive -> {
            if (selectedLocomotive != null) {
                // Check if player has enough budget
                if (scenario.getBudget() < selectedLocomotive.getPurchasePrice()) {
                    Alert error = new Alert(Alert.AlertType.ERROR);
                    error.setTitle("Insufficient Funds");
                    error.setHeaderText(null);
                    error.setContentText("You don't have enough budget to purchase this locomotive.");
                    error.showAndWait();
                    return;
                }

                // Update budget and add locomotive
                scenario.setBudget(scenario.getBudget() - selectedLocomotive.getPurchasePrice());
                scenario.addLocomotivePurchased(selectedLocomotive);
                simulation.addLocomotive(selectedLocomotive);

                // Update UI
                updateLabels("Locomotive purchased: " + selectedLocomotive.getReference(), simulation.getCurrentYear());
                updateLocomotiveInventory();
                
                Alert success = new Alert(Alert.AlertType.INFORMATION);
                success.setTitle("Purchase Successful");
                success.setHeaderText(null);
                success.setContentText("Locomotive purchased successfully!");
                success.showAndWait();
            }
        });
    }
}