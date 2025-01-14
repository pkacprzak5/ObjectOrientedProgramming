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
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

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
    private Simulation simulation;

    @FXML
    private GridPane mapGrid;

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

    public void runNewSimulation(Simulation simulation) {
        this.simulation = simulation;
        simulation.setupBeforeStart(this);
        new Thread(simulation::run).start();
    }

    @FXML
    public void startClicked() {
        if(!simulation.isRunning()){
            simulation.resume();
            new Thread(simulation::run).start();
        }
    }

    @FXML
    public void stopClicked() {
        simulation.stop();
    }

}