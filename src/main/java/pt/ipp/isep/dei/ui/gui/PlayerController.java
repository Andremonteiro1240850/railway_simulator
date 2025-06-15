package pt.ipp.isep.dei.ui.gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

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
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import pt.ipp.isep.dei.controller.CreateScenarioController;
import pt.ipp.isep.dei.domain.*;
import pt.ipp.isep.dei.domain.enums.ListLocomotives;
import pt.ipp.isep.dei.domain.enums.LocomotiveType;
import pt.ipp.isep.dei.domain.enums.StationUpgrade;
import pt.ipp.isep.dei.repository.Repositories;
import pt.ipp.isep.dei.repository.ScenarioRepository;
import javafx.scene.control.ChoiceDialog;
import pt.ipp.isep.dei.controller.SaveLoadController;
import javafx.scene.control.TextInputDialog;

public class PlayerController implements Initializable {
    private ScenarioRepository scenarioRepo;
    @FXML private MenuItem buildStationMenuItem;
    @FXML private MenuItem startSimulationMenuItem;
    @FXML private MenuItem pauseSimulationMenuItem;
    @FXML private MenuItem buyLocomotiveMenuItem;
    @FXML private MenuItem financialResultsMenuItem;
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
    @FXML private TableView<Industry> industriesList;
    @FXML private TableColumn<Industry, String> industryNameColumn;
    @FXML private TableColumn<Industry, String> industryTypeColumn;
    @FXML private TableColumn<Industry, String> industryCoordinatesColumn;
    @FXML private TableView<City> citiesList;
    @FXML private TableColumn<City, String> cityNameColumn;
    @FXML private TableColumn<City, String> cityBlocksColumn;
    @FXML private TableColumn<City, String> cityCoordinatesColumn;
    @FXML private Pane mapView;
    @FXML private Label mapTitleLabel;
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
    private SaveLoadController saveLoadController = new SaveLoadController();

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
                updateLabels("Selected scenario: " + selectedScenario.getName(), selectedScenario.getStartYear(), selectedScenario.getStartMonth());
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupStationTable();
        setupLocomotiveInventoryTable();
        setupIndustriesTable();
        setupCitiesTable();
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

    private void setupIndustriesTable() {
        industryNameColumn.setCellValueFactory(cellData -> {
            Industry industry = cellData.getValue();
            return new SimpleStringProperty(industry != null ? industry.getType().toString() : "");
        });

        industryTypeColumn.setCellValueFactory(cellData -> {
            Industry industry = cellData.getValue();
            return new SimpleStringProperty(industry != null ? industry.getType().toString() : "");
        });

        industryCoordinatesColumn.setCellValueFactory(cellData -> {
            Industry industry = cellData.getValue();
            if (industry != null) {
                return new SimpleStringProperty(String.format("(%d, %d)", industry.getPositionX(), industry.getPositionY()));
            }
            return new SimpleStringProperty("");
        });
    }

    private void setupCitiesTable() {
        cityNameColumn.setCellValueFactory(cellData -> {
            City city = cellData.getValue();
            return new SimpleStringProperty(city != null ? city.getName() : "");
        });

        cityBlocksColumn.setCellValueFactory(cellData -> {
            City city = cellData.getValue();
            return new SimpleStringProperty(city != null ? String.valueOf(city.getHouseBlocks().size()) : "");
        });

        cityCoordinatesColumn.setCellValueFactory(cellData -> {
            City city = cellData.getValue();
            if (city != null) {
                return new SimpleStringProperty(String.format("(%d, %d)", city.getX(), city.getY()));
            }
            return new SimpleStringProperty("");
        });
    }

    private void updateStationList() {
        if (simulation != null && stationList != null) {
            stationList.getItems().setAll(simulation.getScenario().getStationsPurchased());
        }
    }

    private void updateLocomotiveInventory() {
        if (simulation != null && locomotiveInventory != null) {
            locomotiveInventory.getItems().setAll(simulation.getLocomotives());
        }
    }

    private void updateIndustriesList() {
        if (simulation != null && industriesList != null) {
            List<Industry> industries = simulation.getScenario().getMap().getIndustries();
            industriesList.getItems().setAll(industries);
        }
    }

    private void updateCitiesList() {
        if (simulation != null && citiesList != null) {
            List<City> cities = simulation.getScenario().getMap().getCities();
            citiesList.getItems().setAll(cities);
        }
    }

    private void startSimulationWithScenario(Scenario selectedScenario) {
        if (player == null) {
            player = new Player("player@email.com");
        }
        simulation = new Simulation(selectedScenario, player);
        updateLabels("Selected scenario: " + selectedScenario.getName(), selectedScenario.getStartYear(), selectedScenario.getStartMonth());
        updateStationList();
        updateLocomotiveInventory();
        updateIndustriesList();
        updateCitiesList();
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
            simulation.advanceMonth();
            updateLabels("Simulation in progress", simulation.getCurrentYear(), simulation.getCurrentMonth());
            if (simulation.getCurrentYear() >= simulation.getEndYear()) {
                simulationTimeline.stop();
                updateLabels("Simulation finished", simulation.getCurrentYear(), simulation.getCurrentMonth());
            }
        }));
        simulationTimeline.setCycleCount(Timeline.INDEFINITE);
        simulationTimeline.play();
        updateLabels("Simulation in progress", simulation.getCurrentYear(), simulation.getCurrentMonth());
    }

    @FXML
    private void onPauseSimulation() {
        if (simulationTimeline != null) {
            simulationTimeline.stop();
            updateLabels("Simulation paused", simulation != null ? simulation.getCurrentYear() : null, simulation != null ? simulation.getCurrentMonth() : null);
        }
    }

    private void updateLabels(String status, Integer year, Integer month) {
        if (statusLabel != null) statusLabel.setText(status);
        if (yearLabel != null) {
            String monthName = getMonthName(month);
            yearLabel.setText(String.format("Current date: %s %d", monthName, year));
        }
        if (budgetLabel != null && simulation != null && simulation.getScenario() != null) {
            budgetLabel.setText("Budget: " + String.format("%.2f€", simulation.getScenario().getBudget()));
        }
        // Atualizar o título do mapa
        if (mapTitleLabel != null && simulation != null && simulation.getScenario() != null && simulation.getScenario().getMap() != null) {
            mapTitleLabel.setText(simulation.getScenario().getMap().getName());
        }
    }

    private String getMonthName(Integer month) {
        if (month == null) return "-";
        return switch (month) {
            case 1 -> "January";
            case 2 -> "February";
            case 3 -> "March";
            case 4 -> "April";
            case 5 -> "May";
            case 6 -> "June";
            case 7 -> "July";
            case 8 -> "August";
            case 9 -> "September";
            case 10 -> "October";
            case 11 -> "November";
            case 12 -> "December";
            default -> "-";
        };
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
                updateLabels(statusLabel.getText(), simulation.getCurrentYear(), simulation.getCurrentMonth());
                updateStationList();
                drawMapAndAnimateRoute(null);
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
        int currentMonth = simulation != null ? simulation.getCurrentMonth() : 0;
        List<StationUpgrade> installed = stationInSimulation.getUpgrades();
        List<StationUpgrade> possibleUpgrades = new ArrayList<>();
        for (StationUpgrade upgrade : StationUpgrade.values()) {
            // Só upgrades do ano atual ou anterior
            if (upgrade.getYear() > currentYear) continue;
            if (upgrade.getYear() == currentYear && upgrade.getMonth() > currentMonth) continue;
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
        int currentMonth = simulation.getCurrentMonth();

        System.out.println("Current Year: " + currentYear);
        System.out.println("Current Month: " + currentMonth);
        System.out.println("Allowed Types: " + allowedTypes);

        // Mostrar tipos permitidos
        String allowedTypesStr = allowedTypes.stream().map(Enum::name).reduce((a, b) -> a + ", " + b).orElse("-");
        Label allowedTypesLabel = new Label("Available types: " + allowedTypesStr);
        allowedTypesLabel.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-padding: 0 0 8 0;");

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
            if (allowedTypes.contains(locoEnum.getLocomotiveType())) {
                if (locoEnum.getAvailableYear() <= currentYear && locoEnum.getRetiredYear() >= currentYear) {
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
        VBox dialogContent = new VBox(allowedTypesLabel, tableView);
        dialog.setHeaderText("Select a locomotive to purchase:");
        dialog.getDialogPane().setContent(dialogContent);
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
                updateLabels("Locomotive purchased: " + selectedLocomotive.getReference(), simulation.getCurrentYear(), simulation.getCurrentMonth());
                updateLocomotiveInventory();
                
                Alert success = new Alert(Alert.AlertType.INFORMATION);
                success.setTitle("Purchase Successful");
                success.setHeaderText(null);
                success.setContentText("Locomotive purchased successfully!");
                success.showAndWait();
            }
        });
    }

    @FXML
    public void handleAssignTrainToRoute() {
        if (simulation == null || simulation.getScenario() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Scenario Selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select a scenario first.");
            alert.showAndWait();
            return;
        }
        Scenario scenario = simulation.getScenario();
        List<Locomotive> availableLocos = scenario.getLocomotivePurchased();
        if (availableLocos.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No Locomotives");
            alert.setHeaderText(null);
            alert.setContentText("You have no locomotives to assign.");
            alert.showAndWait();
            return;
        }

        // 1. Select Locomotive
        Dialog<Locomotive> locoDialog = new Dialog<>();
        locoDialog.setTitle("Select Locomotive");
        locoDialog.setHeaderText("Choose a locomotive to assign to a route:");
        ListView<Locomotive> locoListView = new ListView<>();
        locoListView.getItems().addAll(availableLocos);
        locoListView.setCellFactory(lv -> new ListCell<Locomotive>() {
            @Override
            protected void updateItem(Locomotive loco, boolean empty) {
                super.updateItem(loco, empty);
                if (empty || loco == null) {
                    setText(null);
                } else {
                    setText(String.format("%s (Power: %d, Type: %s, Cost: %.2f)", 
                        loco.getReference(), 
                        loco.getPower(),
                        loco.getType(),
                        loco.getCost()));
                }
            }
        });
        VBox locoContent = new VBox(10, locoListView);
        locoDialog.getDialogPane().setContent(locoContent);
        locoDialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        locoDialog.setResultConverter(dialogButton -> dialogButton == ButtonType.OK ? locoListView.getSelectionModel().getSelectedItem() : null);
        Optional<Locomotive> selectedLocoOpt = locoDialog.showAndWait();
        if (!selectedLocoOpt.isPresent() || selectedLocoOpt.get() == null) return;
        Locomotive selectedLoco = selectedLocoOpt.get();

        // 3. Escolha da rota: início, intermediárias, fim
        List<Station> availableStations = scenario.getStationsPurchased();
        if (availableStations.size() < 2) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "You need at least two stations to create a route.");
            alert.showAndWait();
            return;
        }
        List<Station> routeStations = new ArrayList<>();
        // Escolher estação inicial (obrigatoriamente depot)
        List<Station> startCandidates = availableStations.stream()
            .filter(s -> s.getType().equalsIgnoreCase("depot"))
            .collect(Collectors.toList());
        Station startStation = showStationSelectionDialog("Select Start Station", "Choose the starting station (must be a depot):", startCandidates, null);
        if (startStation == null) return;
        routeStations.add(startStation);
        // Escolher estação final (station ou terminal, nunca depot)
        List<Station> endCandidates = availableStations.stream()
            .filter(s -> (s.getType().equalsIgnoreCase("terminal") || s.getType().equalsIgnoreCase("station")) && !s.equals(startStation))
            .collect(Collectors.toList());
        Station endStation = showStationSelectionDialog("Select End Station", "Choose the ending station (station or terminal):", endCandidates, "terminal");
        if (endStation == null) return;
        // Escolher intermediárias (qualquer tipo, não repetidas)
        List<Station> middleCandidates = availableStations.stream()
            .filter(s -> !s.equals(startStation) && !s.equals(endStation))
            .collect(Collectors.toList());
        List<Station> middleStations = showMultiStationSelectionDialog("Select Intermediate Stations", "Choose intermediate stations (optional, order matters):", middleCandidates);
        if (middleStations != null) {
            routeStations.addAll(middleStations);
        }
        routeStations.add(endStation);
        // Validação: não repetir estações
        if (routeStations.stream().distinct().count() != routeStations.size()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Stations cannot be repeated in the route.");
            alert.showAndWait();
            return;
        }
        // Resumo visual da rota
        StringBuilder routeSummary = new StringBuilder("Route order:\n");
        for (int i = 0; i < routeStations.size(); i++) {
            routeSummary.append((i+1)).append(". ").append(routeStations.get(i).getName()).append(" (").append(routeStations.get(i).getType()).append(")\n");
        }
        Alert summaryAlert = new Alert(Alert.AlertType.CONFIRMATION, routeSummary.toString(), ButtonType.OK, ButtonType.CANCEL);
        summaryAlert.setTitle("Confirm Route Order");
        summaryAlert.setHeaderText("Review the order of stations:");
        Optional<ButtonType> summaryResult = summaryAlert.showAndWait();
        if (!summaryResult.isPresent() || summaryResult.get() != ButtonType.OK) return;

        // 2. Select Carriages (depois de escolher startStation)
        List<Carriage> carriages = new ArrayList<>();
        boolean addMoreCarriages = true;
        int totalCarriageCapacity = 0;
        double totalCost = 0.0;
        while (addMoreCarriages) {
            Dialog<Carriage> carriageDialog = new Dialog<>();
            carriageDialog.setTitle("Add Carriage");
            carriageDialog.setHeaderText(String.format("Select a carriage to add to the train (Current Power: %d, Used: %d, Total Cost: %.2f)", 
                selectedLoco.getPower(), totalCarriageCapacity, totalCost));
            
            ListView<Carriage> carriageListView = new ListView<>();
            // Filtrar cargas permitidas pelo PortBehavior do cenário
            Set<String> allowedCargos = new HashSet<>();
            if (portBehavior != null) {
                allowedCargos.addAll(portBehavior.getImportCargo());
                allowedCargos.addAll(portBehavior.getExportCargo());
                allowedCargos.addAll(portBehavior.getTransformCargo());
            }
            // Filtrar ainda mais pelo que existe próximo da estação de origem
            Set<String> finalAllowedCargos = new HashSet<>();
            for (String cargo : allowedCargos) {
                if (cargo.equalsIgnoreCase("passengers")) {
                    if (!startStation.getNearbyCities(scenario.getMap()).isEmpty()) {
                        finalAllowedCargos.add(cargo);
                    }
                } else {
                    boolean hasIndustry = startStation.getNearbyIndustries(scenario.getMap()).stream()
                        .anyMatch(ind -> ind.getType().name().equalsIgnoreCase(cargo));
                    if (hasIndustry) {
                        finalAllowedCargos.add(cargo);
                    }
                }
            }
            for (pt.ipp.isep.dei.domain.enums.CarriageList c : pt.ipp.isep.dei.domain.enums.CarriageList.values()) {
                Carriage carriage = c.toCarriage();
                if (finalAllowedCargos.contains(carriage.getCargoType().toLowerCase())) {
                    if (totalCarriageCapacity + carriage.getCapacity() <= selectedLoco.getPower() &&
                        totalCost + carriage.getCost() <= scenario.getBudget()) {
                        carriageListView.getItems().add(carriage);
                    }
                }
            }
            if (carriageListView.getItems().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Limit Reached");
                alert.setHeaderText(null);
                if (totalCarriageCapacity >= selectedLoco.getPower()) {
                    alert.setContentText("Cannot add more carriages: locomotive power limit reached.");
                } else {
                    alert.setContentText("Cannot add more carriages: budget limit reached.");
                }
                alert.showAndWait();
                break;
            }
            carriageListView.setCellFactory(lv -> new ListCell<Carriage>() {
                @Override
                protected void updateItem(Carriage c, boolean empty) {
                    super.updateItem(c, empty);
                    if (empty || c == null) {
                        setText(null);
                    } else {
                        setText(String.format("%s (Capacity: %.1f, Type: %s, Cost: %.2f)", 
                            c.getName(), 
                            c.getCapacity(),
                            c.getCargoType(),
                            c.getCost()));
                    }
                }
            });
            VBox carriageContent = new VBox(10, carriageListView);
            carriageDialog.getDialogPane().setContent(carriageContent);
            carriageDialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
            carriageDialog.setResultConverter(dialogButton -> dialogButton == ButtonType.OK ? carriageListView.getSelectionModel().getSelectedItem() : null);
            Optional<Carriage> selectedCarriageOpt = carriageDialog.showAndWait();
            if (selectedCarriageOpt.isPresent() && selectedCarriageOpt.get() != null) {
                Carriage selectedCarriage = selectedCarriageOpt.get();
                carriages.add(selectedCarriage);
                totalCarriageCapacity += selectedCarriage.getCapacity();
                totalCost += selectedCarriage.getCost();
                Alert more = new Alert(Alert.AlertType.CONFIRMATION, 
                    String.format("Add another carriage? (Current Power: %d, Used: %d, Total Cost: %.2f, Budget: %.2f)", 
                        selectedLoco.getPower(), totalCarriageCapacity, totalCost, scenario.getBudget()), 
                    ButtonType.YES, ButtonType.NO);
                more.setHeaderText(null);
                more.setTitle("Add More Carriages");
                Optional<ButtonType> moreResult = more.showAndWait();
                addMoreCarriages = moreResult.isPresent() && moreResult.get() == ButtonType.YES;
            } else {
                addMoreCarriages = false;
            }
        }
        if (carriages.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "You must add at least one carriage.");
            alert.showAndWait();
            return;
        }
        // 4. Para cada ponto, escolher modo de carga
        List<pt.ipp.isep.dei.domain.PointsOfRoute> routePoints = new ArrayList<>();
        // Obter cargas permitidas pelo cenário
        Set<String> allowedCargos = new HashSet<>();
        if (portBehavior != null) {
            allowedCargos.addAll(portBehavior.getImportCargo());
            allowedCargos.addAll(portBehavior.getExportCargo());
            allowedCargos.addAll(portBehavior.getTransformCargo());
        }
        for (Station station : routeStations) {
            handleCargoModeSelection(station);
        }
        // 5. Confirmar e atribuir rota
        pt.ipp.isep.dei.controller.AssignRouteController assignController = new pt.ipp.isep.dei.controller.AssignRouteController();
        assignController.assignRouteToTrain(selectedLoco, carriages, routePoints, scenario);
        drawMapAndAnimateRoute(routePoints);
        Alert done = new Alert(Alert.AlertType.INFORMATION);
        done.setTitle("Route Assigned");
        done.setHeaderText(null);
        done.setContentText("Route assigned to train successfully!");
        done.showAndWait();
    }

    private Station showStationSelectionDialog(String title, String header, List<Station> candidates, String preferredType) {
        Dialog<Station> dialog = new Dialog<>();
        dialog.setTitle(title);
        dialog.setHeaderText(header);
        ListView<Station> listView = new ListView<>();
        if (preferredType != null) {
            // Preferir mostrar primeiro as estações do tipo preferido
            List<Station> preferred = candidates.stream().filter(s -> s.getType().equalsIgnoreCase(preferredType)).collect(Collectors.toList());
            List<Station> others = candidates.stream().filter(s -> !s.getType().equalsIgnoreCase(preferredType)).collect(Collectors.toList());
            listView.getItems().addAll(preferred);
            listView.getItems().addAll(others);
        } else {
            listView.getItems().addAll(candidates);
        }
        listView.setCellFactory(lv -> new ListCell<Station>() {
            @Override
            protected void updateItem(Station s, boolean empty) {
                super.updateItem(s, empty);
                if (empty || s == null) {
                    setText(null);
                } else {
                    setText(String.format("%s (%s) at (%d, %d)", s.getName(), s.getType(), s.getX(), s.getY()));
                }
            }
        });
        VBox content = new VBox(10, listView);
        dialog.getDialogPane().setContent(content);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        dialog.setResultConverter(dialogButton -> dialogButton == ButtonType.OK ? listView.getSelectionModel().getSelectedItem() : null);
        Optional<Station> result = dialog.showAndWait();
        return result.isPresent() ? result.get() : null;
    }

    private List<Station> showMultiStationSelectionDialog(String title, String header, List<Station> candidates) {
        Dialog<List<Station>> dialog = new Dialog<>();
        dialog.setTitle(title);
        dialog.setHeaderText(header);
        ListView<Station> listView = new ListView<>();
        listView.getItems().addAll(candidates);
        listView.getSelectionModel().setSelectionMode(javafx.scene.control.SelectionMode.MULTIPLE);
        listView.setCellFactory(lv -> new ListCell<Station>() {
            @Override
            protected void updateItem(Station s, boolean empty) {
                super.updateItem(s, empty);
                if (empty || s == null) {
                    setText(null);
                } else {
                    setText(String.format("%s (%s) at (%d, %d)", s.getName(), s.getType(), s.getX(), s.getY()));
                }
            }
        });
        VBox content = new VBox(10, listView);
        dialog.getDialogPane().setContent(content);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                return new ArrayList<>(listView.getSelectionModel().getSelectedItems());
            }
            return null;
        });
        Optional<List<Station>> result = dialog.showAndWait();
        return result.isPresent() ? result.get() : null;
    }

    private double calculateDistance(Station s1, Station s2) {
        return Math.sqrt(Math.pow(s2.getX() - s1.getX(), 2) + Math.pow(s2.getY() - s1.getY(), 2));
    }

    private double scaleX(int logicalX, int mapWidth, double paneWidth) {
        return logicalX * (paneWidth / mapWidth);
    }
    private double scaleY(int logicalY, int mapHeight, double paneHeight) {
        return logicalY * (paneHeight / mapHeight);
    }

    private void drawMapAndAnimateRoute(List<pt.ipp.isep.dei.domain.PointsOfRoute> routePoints) {
        mapView.getChildren().clear();
        double paneWidth = mapView.getPrefWidth();
        double paneHeight = mapView.getPrefHeight();
        int mapWidth = 1000, mapHeight = 1000;
        if (simulation != null && simulation.getScenario() != null && simulation.getScenario().getMap() != null) {
            mapWidth = simulation.getScenario().getMap().getWidth();
            mapHeight = simulation.getScenario().getMap().getHeight();
        }
        // 1. Desenhar cidades (centro e house blocks)
        if (simulation != null && simulation.getScenario() != null) {
            List<City> cities = simulation.getScenario().getMap().getCities();
            for (City city : cities) {
                // House blocks (quadrados cinza claro)
                for (int[] block : city.getHouseBlocks()) {
                    double bx = scaleX(block[0], mapWidth, paneWidth);
                    double by = scaleY(block[1], mapHeight, paneHeight);
                    javafx.scene.shape.Rectangle blockRect = new javafx.scene.shape.Rectangle(bx - 6, by - 6, 12, 12);
                    blockRect.setFill(javafx.scene.paint.Color.LIGHTGRAY);
                    mapView.getChildren().add(blockRect);
                }
                // Centro da cidade (círculo preto pequeno)
                double cx = scaleX(city.getX(), mapWidth, paneWidth);
                double cy = scaleY(city.getY(), mapHeight, paneHeight);
                javafx.scene.shape.Circle cityCenter = new javafx.scene.shape.Circle(cx, cy, 5);
                cityCenter.setFill(javafx.scene.paint.Color.BLACK);
                mapView.getChildren().add(cityCenter);
                javafx.scene.text.Text text = new javafx.scene.text.Text(cx + 10, cy, city.getName());
                text.setFill(javafx.scene.paint.Color.BLACK);
                mapView.getChildren().add(text);
            }
        }
        // 2. Desenhar indústrias (triângulo verde)
        if (simulation != null && simulation.getScenario() != null) {
            List<Industry> industries = simulation.getScenario().getMap().getIndustries();
            for (Industry ind : industries) {
                double ix = scaleX(ind.getPositionX(), mapWidth, paneWidth);
                double iy = scaleY(ind.getPositionY(), mapHeight, paneHeight);
                javafx.scene.shape.Polygon triangle = new javafx.scene.shape.Polygon(
                    ix, iy - 8,
                    ix - 8, iy + 8,
                    ix + 8, iy + 8
                );
                triangle.setFill(javafx.scene.paint.Color.FORESTGREEN);
                mapView.getChildren().add(triangle);
                javafx.scene.text.Text text = new javafx.scene.text.Text(ix + 14, iy, ind.getType().toString());
                text.setFill(javafx.scene.paint.Color.FORESTGREEN);
                mapView.getChildren().add(text);
            }
        }
        // 3. Desenhar estações (círculo colorido por tipo)
        if (simulation != null && simulation.getScenario() != null) {
            List<Station> stations = simulation.getScenario().getStationsPurchased();
            for (Station s : stations) {
                javafx.scene.paint.Color color;
                switch (s.getType().toLowerCase()) {
                    case "depot": color = javafx.scene.paint.Color.DODGERBLUE; break;
                    case "station": color = javafx.scene.paint.Color.RED; break;
                    case "terminal": color = javafx.scene.paint.Color.PURPLE; break;
                    default: color = javafx.scene.paint.Color.GRAY;
                }
                double sx = scaleX(s.getX(), mapWidth, paneWidth);
                double sy = scaleY(s.getY(), mapHeight, paneHeight);
                javafx.scene.shape.Circle circle = new javafx.scene.shape.Circle(sx, sy, 8);
                circle.setFill(color);
                mapView.getChildren().add(circle);
                javafx.scene.text.Text text = new javafx.scene.text.Text(sx + 14, sy, s.getName());
                text.setFill(color);
                mapView.getChildren().add(text);
            }
        }
        // 4. Desenhar rota (linhas)
        if (routePoints != null && routePoints.size() > 1) {
            for (int i = 0; i < routePoints.size() - 1; i++) {
                Station s1 = routePoints.get(i).getStations();
                Station s2 = routePoints.get(i + 1).getStations();
                double x1 = scaleX(s1.getX(), mapWidth, paneWidth);
                double y1 = scaleY(s1.getY(), mapHeight, paneHeight);
                double x2 = scaleX(s2.getX(), mapWidth, paneWidth);
                double y2 = scaleY(s2.getY(), mapHeight, paneHeight);
                javafx.scene.shape.Line line = new javafx.scene.shape.Line(x1, y1, x2, y2);
                line.setStroke(javafx.scene.paint.Color.BLACK);
                line.setStrokeWidth(4);
                mapView.getChildren().add(line);
            }
            // 5. Animar comboio
            animateTrainOnRoute(routePoints, mapWidth, mapHeight, paneWidth, paneHeight);
        }
    }

    private void animateTrainOnRoute(List<pt.ipp.isep.dei.domain.PointsOfRoute> routePoints, int mapWidth, int mapHeight, double paneWidth, double paneHeight) {
        if (routePoints == null || routePoints.size() < 2) return;
        // Lista de pontos (x, y) da rota
        List<javafx.geometry.Point2D> points = new ArrayList<>();
        for (pt.ipp.isep.dei.domain.PointsOfRoute p : routePoints) {
            double px = scaleX(p.getStations().getX(), mapWidth, paneWidth);
            double py = scaleY(p.getStations().getY(), mapHeight, paneHeight);
            javafx.geometry.Point2D pt = new javafx.geometry.Point2D(px, py);
            points.add(pt);
        }
        javafx.scene.shape.Circle train = new javafx.scene.shape.Circle(6, javafx.scene.paint.Color.DARKORANGE);
        mapView.getChildren().add(train);
        List<javafx.scene.shape.Line> routeLines = new ArrayList<>();
        for (int i = 0; i < points.size() - 1; i++) {
            javafx.scene.shape.Line line = new javafx.scene.shape.Line(points.get(i).getX(), points.get(i).getY(), points.get(i+1).getX(), points.get(i+1).getY());
            line.setStroke(javafx.scene.paint.Color.BLACK);
            line.setStrokeWidth(4);
            mapView.getChildren().add(line);
            routeLines.add(line);
        }
        final int[] segment = {0};
        final double[] progress = {0.0};
        final double speed = 2.0; // pixels por frame
        train.setCenterX(points.get(0).getX());
        train.setCenterY(points.get(0).getY());
        Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(20), e -> {
            if (segment[0] >= points.size() - 1) {
                timeline.stop();
                mapView.getChildren().removeAll(routeLines);
                return;
            }
            javafx.geometry.Point2D from = points.get(segment[0]);
            javafx.geometry.Point2D to = points.get(segment[0] + 1);
            double dx = to.getX() - from.getX();
            double dy = to.getY() - from.getY();
            double dist = Math.sqrt(dx * dx + dy * dy);
            if (dist == 0) {
                segment[0]++;
                progress[0] = 0.0;
                return;
            }
            progress[0] += speed / dist;
            if (progress[0] >= 1.0) {
                progress[0] = 0.0;
                segment[0]++;
                train.setCenterX(to.getX());
                train.setCenterY(to.getY());
            } else {
                double nextX = from.getX() + progress[0] * dx;
                double nextY = from.getY() + progress[0] * dy;
                train.setCenterX(nextX);
                train.setCenterY(nextY);
            }
        }));
        timeline.play();
    }

    private void handleCargoSelection(Station station) {
        if (station == null) {
            showAlert("Error", "No station selected", Alert.AlertType.ERROR);
            return;
        }

        List<String> availableCargos = station.getAvailableCargos();
        if (availableCargos.isEmpty()) {
            showAlert("Error", "No cargo available at this station", Alert.AlertType.ERROR);
            return;
        }

        ChoiceDialog<String> dialog = new ChoiceDialog<>(availableCargos.get(0), availableCargos);
        dialog.setTitle("Select Cargo");
        dialog.setHeaderText("Select cargo type for " + station.getName());
        dialog.setContentText("Available cargos:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(cargo -> {
            // Atualizar a interface para mostrar a carga selecionada
            updateCargoSelection(station, cargo);
        });
    }

    private void updateCargoSelection(Station station, String cargo) {
        // Atualizar a interface para mostrar a carga selecionada
        String message = String.format("Selected cargo '%s' at station %s", cargo, station.getName());
        updateStatus(message);
        
        // Aqui você pode adicionar lógica adicional para processar a seleção da carga
        // Por exemplo, atualizar o estado do jogo ou a interface do usuário
    }

    private void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void updateStatus(String message) {
        statusLabel.setText(message);
        updateLabels(message, simulation.getCurrentYear(), simulation.getCurrentMonth());
    }

    private void handleCargoModeSelection(Station station) {
        if (station == null) {
            showAlert("Error", "No station selected", Alert.AlertType.ERROR);
            return;
        }
        List<String> cargoModes = List.of("FULL", "HALF", "AVAILABLE");
        ChoiceDialog<String> dialog = new ChoiceDialog<>(cargoModes.get(0), cargoModes);
        dialog.setTitle("Select Cargo Mode");
        dialog.setHeaderText("Choose cargo mode for station: " + station.getName());
        dialog.setContentText("Cargo modes:");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(mode -> {
            // Atualizar a interface para mostrar o modo selecionado
            updateCargoModeSelection(station, mode);
        });
    }

    private void updateCargoModeSelection(Station station, String mode) {
        String message = String.format("Selected cargo mode '%s' at station %s", mode, station.getName());
        updateStatus(message);
        // Lógica adicional se necessário
    }

    @FXML
    private void handleSaveGame() {
        if (simulation == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Save Game Error");
            alert.setHeaderText(null);
            alert.setContentText("No simulation in progress to save.");
            alert.showAndWait();
            return;
        }
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Save Game");
        dialog.setHeaderText("Enter a name for your saved game:");
        dialog.setContentText("Filename:");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(filename -> {
            if (filename.trim().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Invalid Filename");
                alert.setHeaderText(null);
                alert.setContentText("Filename cannot be empty.");
                alert.showAndWait();
                return;
            }
            boolean success = saveLoadController.saveGame(simulation, filename.trim());
            Alert alert = new Alert(success ? Alert.AlertType.INFORMATION : Alert.AlertType.ERROR);
            alert.setTitle(success ? "Game Saved" : "Save Failed");
            alert.setHeaderText(null);
            alert.setContentText(success ? "Game saved successfully!" : "Failed to save game.");
            alert.showAndWait();
        });
    }

    @FXML
    private void handleLoadGame() {
        String[] savedGames = saveLoadController.getSavedGames();
        if (savedGames.length == 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No Saved Games");
            alert.setHeaderText(null);
            alert.setContentText("No saved games found.");
            alert.showAndWait();
            return;
        }

        ChoiceDialog<String> dialog = new ChoiceDialog<>(savedGames[0], savedGames);
        dialog.setTitle("Load Game");
        dialog.setHeaderText("Select a saved game to load:");
        dialog.setContentText("Saved games:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(filename -> {
            Simulation loadedSimulation = saveLoadController.loadGame(filename);
            if (loadedSimulation != null) {
                simulation = loadedSimulation;
                updateLabels("Game loaded: " + filename, simulation.getCurrentYear(), simulation.getCurrentMonth());
                stationList.getItems().clear();
                updateStationList();
                updateLocomotiveInventory();
                updateIndustriesList();
                updateCitiesList();
                drawMapAndAnimateRoute(null);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Game Loaded");
                alert.setHeaderText(null);
                alert.setContentText("Game loaded successfully!");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Load Failed");
                alert.setHeaderText(null);
                alert.setContentText("Failed to load game.");
                alert.showAndWait();
            }
        });
    }

    @FXML
    private void showFinancialResults() {
        if (simulation == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Simulation");
            alert.setHeaderText(null);
            alert.setContentText("Please start a simulation first.");
            alert.showAndWait();
            return;
        }

        FinancialResultsView view = new FinancialResultsView(simulation);
        view.show();
    }
}