package agh.ics.oop;

import agh.ics.oop.presenter.SimulationPresenter;
import agh.ics.oop.presenter.StartWindowPresenter;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class SimulationApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("startwindow.fxml"));
        BorderPane viewRoot = loader.load();
        StartWindowPresenter presenter = loader.getController();

        configureStage(primaryStage, viewRoot);

        primaryStage.setOnCloseRequest(event -> {
            Platform.exit();
            System.exit(0);
        });
        primaryStage.show();
    }

    public void createNewSimulation(Stage stage, Simulation simulation) throws IOException {
        FXMLLoader newLoader = new FXMLLoader();
        newLoader.setLocation(getClass().getClassLoader().getResource("simulation.fxml"));
        BorderPane viewRoot = newLoader.load();
        SimulationPresenter presenter = newLoader.getController();

        presenter.runNewSimulation(simulation);

        stage.setOnCloseRequest(event -> simulation.stop());

        configureStage(stage, viewRoot);
        stage.show();
    }

    private void configureStage(Stage primaryStage, BorderPane viewRoot) {
        var scene = new Scene(viewRoot);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Simulation app");
        primaryStage.minWidthProperty().bind(viewRoot.minWidthProperty());
        primaryStage.minHeightProperty().bind(viewRoot.minHeightProperty());
    }


}
