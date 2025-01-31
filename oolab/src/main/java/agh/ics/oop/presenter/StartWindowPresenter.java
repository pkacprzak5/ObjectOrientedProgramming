package agh.ics.oop.presenter;

import agh.ics.oop.Simulation;
import agh.ics.oop.SimulationApp;
import agh.ics.oop.config.Configuration;
import agh.ics.oop.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.util.Optional;


public class StartWindowPresenter{

    private AbstractRectangularMap worldMap;
    private int mapWidth;
    private int mapHeight;

    private static final String CONFIG_FILE = "config.json";

    @FXML
    private Spinner<Integer> mwidth;

    @FXML
    private Spinner<Integer> mheight;

    @FXML
    private Spinner<Integer> initialPlants;

    @FXML
    private Spinner<Integer> energyPerPlant;

    @FXML
    private Spinner<Integer> plantsPerDay;

    @FXML
    private Spinner<Integer> initialAnimals;

    @FXML
    private Spinner<Integer> energyToMultiply;

    @FXML
    private Spinner<Integer> initialEnergy;

    @FXML
    private Spinner<Integer> energyToBreed;

    @FXML
    private Spinner<Integer> minMutations;

    @FXML
    private Spinner<Integer> maxMutations;

    @FXML
    private Spinner<Integer> genomeLength;

    @FXML
    private Spinner<Integer> energyToMove;

    @FXML
    private Spinner<Integer> refreshTime;

    @FXML
    private CheckBox fireCheckBox;

    @FXML
    private CheckBox mutationCheckBox;

    @FXML
    private Spinner<Integer> daydelay;

    @FXML
    private Spinner<Integer> dayamount;

    @FXML
    private HBox fireinfo;


    private void createWorldMap(){
        try {
            mapWidth = mwidth.getValue();
            mapHeight = mheight.getValue();
            Multiplication multiplication;
            if (mutationCheckBox.isSelected()) {
                multiplication = new Multiplication(energyToBreed.getValue(), minMutations.getValue(), maxMutations.getValue(), energyToMultiply.getValue());
            } else {
                multiplication = new MultiplicationSlightCorrection(energyToBreed.getValue(), minMutations.getValue(), maxMutations.getValue(), energyToMultiply.getValue());
            }
            GrassGenerator grassGenerator = new GrassGenerator(mapWidth, mapHeight, initialPlants.getValue(), plantsPerDay.getValue(), energyPerPlant.getValue());
            if (fireCheckBox.isSelected()) {
                Integer delay = daydelay.getValue();
                Integer amount = dayamount.getValue();

                this.worldMap = new RectangularMapFire(mapWidth, mapHeight, energyToMove.getValue(), multiplication, grassGenerator, delay, amount, new FireSpread(mapWidth, mapHeight, 2));
            } else {
                this.worldMap = new RectangularMap(mapWidth, mapHeight, energyToMove.getValue(), multiplication, grassGenerator);
            }
            FileMapDisplay fileMapDisplay = new FileMapDisplay();
            this.worldMap.addObserver(fileMapDisplay);
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Field can not be empty");
            alert.showAndWait();
            e.printStackTrace();
        }
    }

    @FXML
    public void fireon() {
        if (!fireinfo.visibleProperty().getValue()){
            fireinfo.visibleProperty().setValue(true);
        }else fireinfo.visibleProperty().setValue(false);

    }

    @FXML
    public void onSimulationStartClicked(){
        SimulationApp simulationApp = new SimulationApp();
        try {
            createWorldMap();
            GrassGenerator grassGenerator = new GrassGenerator(mapWidth, mapHeight, initialPlants.getValue(), plantsPerDay.getValue(), energyPerPlant.getValue());
            Simulation sim = new Simulation(initialAnimals.getValue(), initialEnergy.getValue(), genomeLength.getValue(), refreshTime.getValue(), grassGenerator, this.worldMap);
            simulationApp.createNewSimulation(new Stage(), sim);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void saveConfiguration() {
        TextInputDialog dialog = new TextInputDialog("configuration.json");
        dialog.setTitle("Save Configuration");
        dialog.setHeaderText("Enter the name of the configuration file to save:");
        dialog.setContentText("File name:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(fileName -> {
            try {
                Configuration config = new Configuration(
                        mwidth.getValue(),
                        mheight.getValue(),
                        initialPlants.getValue(),
                        energyPerPlant.getValue(),
                        plantsPerDay.getValue(),
                        initialAnimals.getValue(),
                        energyToMultiply.getValue(),
                        initialEnergy.getValue(),
                        energyToBreed.getValue(),
                        minMutations.getValue(),
                        maxMutations.getValue(),
                        genomeLength.getValue(),
                        energyToMove.getValue(),
                        refreshTime.getValue(),
                        fireCheckBox.isSelected(),
                        mutationCheckBox.isSelected(),
                        daydelay.getValue(),
                        dayamount.getValue()
                );
                ObjectMapper mapper = new ObjectMapper();
                mapper.writeValue(new File(fileName), config);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText("Configuration saved successfully!");
                alert.setContentText("File: " + fileName);
                alert.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
                showError("Error saving configuration", e.getMessage());
            }
        });
    }

    @FXML
    public void loadConfiguration() {
        TextInputDialog dialog = new TextInputDialog("configuration.json");
        dialog.setTitle("Load Configuration");
        dialog.setHeaderText("Enter the name of the configuration file to load:");
        dialog.setContentText("File name:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(fileName -> {
            try {
                ObjectMapper mapper = new ObjectMapper();
                Configuration config = mapper.readValue(new File(fileName), Configuration.class);

                mwidth.getValueFactory().setValue(config.getMapWidth());
                mheight.getValueFactory().setValue(config.getMapHeight());
                initialPlants.getValueFactory().setValue(config.getInitialPlants());
                energyPerPlant.getValueFactory().setValue(config.getEnergyPerPlant());
                plantsPerDay.getValueFactory().setValue(config.getPlantsPerDay());
                initialAnimals.getValueFactory().setValue(config.getInitialAnimals());
                energyToMultiply.getValueFactory().setValue(config.getEnergyToMultiply());
                initialEnergy.getValueFactory().setValue(config.getInitialEnergy());
                energyToBreed.getValueFactory().setValue(config.getEnergyToBreed());
                minMutations.getValueFactory().setValue(config.getMinMutations());
                maxMutations.getValueFactory().setValue(config.getMaxMutations());
                genomeLength.getValueFactory().setValue(config.getGenomeLength());
                energyToMove.getValueFactory().setValue(config.getEnergyToMove());
                refreshTime.getValueFactory().setValue(config.getRefreshTime());
                fireCheckBox.setSelected(config.isFireEnabled());
                mutationCheckBox.setSelected(config.isMutationEnabled());
                daydelay.getValueFactory().setValue(config.getDayDelay());
                dayamount.getValueFactory().setValue(config.getDayAmount());

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText("Configuration loaded successfully!");
                alert.setContentText("File: " + fileName);
                alert.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
                showError("Error loading configuration", e.getMessage());
            }
        });
    }

    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}