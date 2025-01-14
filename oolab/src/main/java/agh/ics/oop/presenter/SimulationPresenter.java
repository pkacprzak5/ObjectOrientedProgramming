package agh.ics.oop.presenter;

import agh.ics.oop.Simulation;
import agh.ics.oop.SimulationApp;
import agh.ics.oop.model.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

import static java.lang.Math.max;
import static java.lang.Math.min;


public class SimulationPresenter implements MapChangeListener{

    private AbstractRectangularMap worldMap;
    private final int width = 500;
    private final int height = 500;
    private final int xMin = 0;
    private final int yMin = 0;
    private int xMax;
    private int yMax;
    private int mapWidth;
    private int mapHeight;
    private Thread simulationThread;
    private Simulation simulation;


    @FXML
    private GridPane mapGrid;

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
    private Spinner<Integer> daydelay;
    
    @FXML
    private Spinner<Integer> dayamount;

    @FXML
    private HBox fireinfo;


    public void setWorldMap(AbstractRectangularMap worldMap) {
        this.worldMap = worldMap;
    }

    public void updateBounds(){
        mapWidth = worldMap.getWidth();
        xMax = worldMap.getWidth()-1;
        mapHeight = worldMap.getHeight();
        yMax = worldMap.getHeight()-1;
    }

    public void columnsFunction(){
        for (int i = 0; i < mapWidth; i++){
            Label label = new Label(Integer.toString(xMin+i));
            GridPane.setHalignment(label, HPos.CENTER);
            mapGrid.getColumnConstraints().add(new ColumnConstraints(width/(mapWidth+1)));
            mapGrid.add(label, i+1, 0);
        }
    }

    public void rowsFunction(){
        for (int i = 0; i < mapHeight; i++){
            Label label = new Label(Integer.toString(yMax-i));
            GridPane.setHalignment(label, HPos.CENTER);
            mapGrid.getRowConstraints().add(new RowConstraints(height/(mapHeight+1)));
            mapGrid.add(label, 0, i+1);
        }
    }

    public void addElements() {
        for (int i = xMin; i <= xMax; i++) {
            for (int j = yMax; j >= yMin; j--) {
                Vector2d pos = new Vector2d(i,j);
                Optional<WorldElement> element = worldMap.objectAt(pos);

                //String labelText = element.map(Object::toString).orElse(" ");
                WorldElementBox box = new WorldElementBox(element,min(width,height)/max(mapWidth+1,mapHeight+1));
                mapGrid.add(box.getContainer(), i - xMin + 1, yMax - j + 1); //or labelText insted box.getContainer()
                mapGrid.setHalignment(mapGrid.getChildren().get(mapGrid.getChildren().size()-1), HPos.CENTER);
            }
        }
    }

    public void drawMap(){
        updateBounds();
        Labels();
        columnsFunction();
        rowsFunction();
        addElements();
        mapGrid.setGridLinesVisible(true);
    }

    private void clearGrid() {
        mapGrid.getChildren().retainAll(mapGrid.getChildren().getFirst()); // hack to retain visible grid lines
        mapGrid.getColumnConstraints().clear();
        mapGrid.getRowConstraints().clear();
    }

    public void Labels(){
        mapGrid.getColumnConstraints().add(new ColumnConstraints(min(width,height)/max(mapWidth+1,mapHeight+1)));
        mapGrid.getRowConstraints().add(new RowConstraints(min(width,height)/max(mapWidth+1,mapHeight+1)));
        Label label = new Label("y/x");
        mapGrid.add(label, 0, 0);
        GridPane.setHalignment(label, HPos.CENTER);
    }

    public void mapChanged(WorldMap worldMap, String mess) {
        if(Objects.equals(mess, "")){
            setWorldMap((AbstractRectangularMap) worldMap);
            Platform.runLater(() -> {
                clearGrid();
                drawMap();
            });
        }
    }

    private void createWorldMap(){
        mapWidth = mwidth.getValue();
        mapHeight = mheight.getValue();
        
        Multiplication multiplication = new Multiplication(energyToBreed.getValue(), minMutations.getValue(), maxMutations.getValue(), energyToMultiply.getValue());
        GrassGenerator grassGenerator = new GrassGenerator(mapWidth, mapHeight, initialPlants.getValue(), plantsPerDay.getValue(), energyPerPlant.getValue());
        if (fireCheckBox.isSelected()){
            Integer delay = daydelay.getValue();
            Integer amount = dayamount.getValue();
            
            this.worldMap = new RectangularMapFire(mapWidth, mapHeight, energyToMove.getValue(), multiplication, grassGenerator,delay,amount,new FireSpread(mapWidth,mapHeight,2));
        } else{
            this.worldMap = new RectangularMap(mapWidth, mapHeight, energyToMove.getValue(), multiplication, grassGenerator);
        }
        FileMapDisplay fileMapDisplay = new FileMapDisplay();
        this.worldMap.addObserver(fileMapDisplay);
    }

    public void runNewSimulation(Simulation simulation) {
        new Thread(() -> simulation.run(this)).start();
    }

    @FXML
    public void onSimulationStartClicked(){
        SimulationApp simulationApp = new SimulationApp();
        try {
            createWorldMap();
            GrassGenerator grassGenerator = new GrassGenerator(mapWidth, mapHeight, initialPlants.getValue(), plantsPerDay.getValue(), energyPerPlant.getValue());
            simulation = new Simulation(initialAnimals.getValue(), initialEnergy.getValue(), genomeLength.getValue(), refreshTime.getValue(), grassGenerator, this.worldMap);
            simulationApp.createNewSimulation(new Stage(), simulation);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void startClicked() {
    }

    @FXML
    public void stopClicked() {
    }

    public void fireon(ActionEvent actionEvent) {
        if (!fireinfo.visibleProperty().getValue()){
            fireinfo.visibleProperty().setValue(true);
        }else fireinfo.visibleProperty().setValue(false);

    }
}