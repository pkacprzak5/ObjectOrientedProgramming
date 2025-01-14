package agh.ics.oop.presenter;

import agh.ics.oop.Simulation;
import agh.ics.oop.SimulationApp;
import agh.ics.oop.model.*;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Spinner;
import javafx.stage.Stage;
import java.io.IOException;


public class StartWindowPresenter{

    private AbstractRectangularMap worldMap;
    private int mapWidth;
    private int mapHeight;

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


    private void createWorldMap(){
        mapWidth = mwidth.getValue();
        mapHeight = mheight.getValue();
        Multiplication multiplication;
        if(mutationCheckBox.isSelected()){
            multiplication = new Multiplication(energyToBreed.getValue(), minMutations.getValue(), maxMutations.getValue(), energyToMultiply.getValue());
        }else{
            multiplication = new MultiplicationSlightCorrection(energyToBreed.getValue(), minMutations.getValue(), maxMutations.getValue(), energyToMultiply.getValue());
        }
        GrassGenerator grassGenerator = new GrassGenerator(mapWidth, mapHeight, initialPlants.getValue(), plantsPerDay.getValue(), energyPerPlant.getValue());
        if (fireCheckBox.isSelected()){
            this.worldMap = new RectangularMapFire(mapWidth, mapHeight, energyToMove.getValue(), multiplication, grassGenerator,2,30,new FireSpread(mapWidth,mapHeight,2));
        } else{
            this.worldMap = new RectangularMap(mapWidth, mapHeight, energyToMove.getValue(), multiplication, grassGenerator);
        }
        FileMapDisplay fileMapDisplay = new FileMapDisplay();
        this.worldMap.addObserver(fileMapDisplay);
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

}