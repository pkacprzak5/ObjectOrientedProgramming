package agh.ics.oop.presenter;

import agh.ics.oop.Simulation;
import agh.ics.oop.model.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
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
    private Simulation simulation;
    private XYChart.Series<Number, Number> seriesAnimals;
    private XYChart.Series<Number, Number> seriesGrass;
    private int xValue = 0;
    private int yValueAnimals = 0;
    private int yValueGrass = 0;

    @FXML
    private GridPane mapGrid;

    @FXML
    private Label dayLabel;

    @FXML
    private Label populationLabel;

    @FXML
    private Label childrenLabel;

    @FXML
    private Label animalsCountLabel;

    @FXML
    private Label plantsCountLabel;

    @FXML
    private Label freeFieldsLabel;

    @FXML
    private Label popularGenotypeLabel;

    @FXML
    private Label avgLivingEnergyLabel;

    @FXML
    private Label avgDeadLifeSpanLabel;

    @FXML
    private Label avgLivingChildrenLabel;

    @FXML
    private LineChart<Number, Number> animalNumberChart;

    @FXML
    private LineChart<Number, Number> grassNumberChart;

    @FXML
    private NumberAxis xAxisAnimal;

    @FXML
    private NumberAxis yAxisAnimal;

    @FXML
    private NumberAxis xAxisGrass;

    @FXML
    private NumberAxis yAxisGrass;

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

    private void updateStatistics(){
        dayLabel.setText(String.valueOf(worldMap.getCurrentTime()));
        populationLabel.setText(String.valueOf(worldMap.getAnimalsNumber()));
        childrenLabel.setText(String.valueOf(worldMap.getAvgChildrenNumber()));
        animalsCountLabel.setText(String.valueOf(worldMap.getAnimalsNumber()));
        plantsCountLabel.setText(String.valueOf(worldMap.getGrassNumber()));
        freeFieldsLabel.setText(String.valueOf(worldMap.getFreeFieldsNumber()));
        popularGenotypeLabel.setText(worldMap.getMostPopularGenotype());
        avgLivingEnergyLabel.setText(String.valueOf(worldMap.getAvgEnergy()));
        avgDeadLifeSpanLabel.setText(String.valueOf(worldMap.getAvgTimeAlive()));
        avgLivingChildrenLabel.setText(String.valueOf(worldMap.getAvgChildrenNumber()));
    }

    public void mapChanged(WorldMap worldMap, String mess) {
        if(Objects.equals(mess, "")){
            setWorldMap((AbstractRectangularMap) worldMap);
            Platform.runLater(() -> {
                updateStatistics();
                updateChart();
                clearGrid();
                drawMap();
            });
        }
    }

    public void runNewSimulation(Simulation simulation) {
        this.simulation = simulation;
        simulation.setupBeforeStart(this);
        new Thread(simulation::run).start();
        initializeCharts();
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

    public void initializeCharts() {
        seriesAnimals = new XYChart.Series<>();
        seriesGrass = new XYChart.Series<>();

        animalNumberChart.getData().add(seriesAnimals);
        grassNumberChart.getData().add(seriesGrass);
        xAxisAnimal.setLowerBound(0);
        xAxisAnimal.setUpperBound(100);
        xAxisGrass.setLowerBound(0);
        xAxisGrass.setUpperBound(100);

        yAxisGrass.setAutoRanging(true);
        yAxisAnimal.setAutoRanging(true);

    }

    public void updateChart() {
        yValueAnimals = worldMap.getAnimalsNumber();
        yValueGrass = worldMap.getGrassNumber();

        seriesAnimals.getData().add(new XYChart.Data<>(xValue, yValueAnimals));
        for (XYChart.Data<Number, Number> data : seriesAnimals.getData()) {
            data.setNode(null);
        }
        seriesGrass.getData().add(new XYChart.Data<>(xValue, yValueGrass));
        for (XYChart.Data<Number, Number> data : seriesGrass.getData()) {
            data.setNode(null);
        }

        if (seriesAnimals.getData().size() > 100) {
            seriesAnimals.getData().remove(0);
        }

        if (seriesGrass.getData().size() > 100) {
            seriesGrass.getData().remove(0);
        }

        if (xValue > 50) {
            xAxisAnimal.setLowerBound(xValue - 50);
            xAxisAnimal.setUpperBound(xValue + 50);
            xAxisGrass.setLowerBound(xValue - 50);
            xAxisGrass.setUpperBound(xValue + 50);
        }

        xValue++;
    }

}