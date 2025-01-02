package agh.ics.oop.presenter;

import agh.ics.oop.SimulationApp;
import agh.ics.oop.model.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

import java.io.IOException;


public class SimulationPresenter{
    private WorldMap worldMap;
    private final int width = 50;
    private final int height = 50;
    private final int xMin = 0;
    private final int yMin = 0;
    private int xMax;
    private int yMax;
    private  int mapWidth;
    private  int mapHeight;

    @FXML
    private TextField moveListTextField;

    @FXML
    private GridPane mapGrid;

    @FXML
    private Label moveDescriptionLabel;

    public void setWorldMap(WorldMap worldMap) {
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
            mapGrid.getColumnConstraints().add(new ColumnConstraints(width));
            mapGrid.add(label, i+1, 0);
        }
    }

    public void rowsFunction(){
        for (int i = 0; i < mapHeight; i++){
            Label label = new Label(Integer.toString(yMax-i));
            GridPane.setHalignment(label, HPos.CENTER);
            mapGrid.getRowConstraints().add(new RowConstraints(height));
            mapGrid.add(label, 0, i+1);
        }
    }

    public void addElements() {
        for (int i = xMin; i <= xMax; i++) {
            for (int j = yMax; j >= yMin; j--) {
                Vector2d pos = new Vector2d(i,j);
                if(worldMap.isOccupied(pos)){
                    mapGrid.add(new Label(worldMap.objectAt(pos).toString()),i-xMin+1,yMax-j+1);
                }
                else {
                    mapGrid.add(new Label(" "),i-xMin+1,yMax-j+1);
                }
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
        mapGrid.getChildren().retainAll(mapGrid.getChildren().get(0)); // hack to retain visible grid lines
        mapGrid.getColumnConstraints().clear();
        mapGrid.getRowConstraints().clear();
    }

    public void Labels(){
        mapGrid.getColumnConstraints().add(new ColumnConstraints(width));
        mapGrid.getRowConstraints().add(new RowConstraints(height));
        Label label = new Label("y/x");
        mapGrid.add(label, 0, 0);
        GridPane.setHalignment(label, HPos.CENTER);
    }

    public void mapChanged(WorldMap worldMap, String message) {
        setWorldMap(worldMap);
        Platform.runLater(() -> {
            clearGrid();
            drawMap();
            moveDescriptionLabel.setText(message);
        });
    }

    @FXML
    private void newWindow() {
        SimulationApp simulationApp = new SimulationApp();
        try {
            simulationApp.createNewSimulation(new Stage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    //tu trzeba dodać wszystkie dane do wprowadzania
    public void onSimulationStartClicked(){
//        String moveList = moveListTextField.getText();

//        this.worldMap = worldMap;
//        Simulation simulation = new Simulation(positions, directions, worldMap);
//        new Thread(() -> {
//            simulation.run();
//        }).start();
    }
}