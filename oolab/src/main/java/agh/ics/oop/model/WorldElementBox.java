package agh.ics.oop.model;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class WorldElementBox {
    private static final Map<String, Image> imageCache = new HashMap<>();
    private VBox container;
    private int maxHealth = 50;
    private double height;

    public WorldElementBox(Optional<WorldElement> element,int size) {
        container = new VBox();
        height = 0.1*size;
        if (element.isPresent()) {
            WorldElement worldElement = element.get();
            String positionInfo = worldElement + worldElement.getPosition().toString();
            Image image = new Image(String.valueOf(getClass().getClassLoader().getResource(worldElement.getResourceName())));
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(size);
            imageView.setFitHeight(size);
            container.getChildren().addAll(imageView);
            //Label positionLabel = new Label(positionInfo);
            if (worldElement instanceof Animal) {
                Animal animal = (Animal) worldElement;
                Region healthBar = new Region();
                double energy = animal.getInfo().getEnergy();
                healthBar.setPrefHeight(height);
                double energyPercentage = energy / maxHealth;
                String color = energyPercentage > 0.75 ? "green"
                        : energyPercentage > 0.5 ? "yellow"
                        : energyPercentage > 0.25 ? "orange"
                        : "red";
                healthBar.setStyle("-fx-background-color: " + color + ";");

                container.getChildren().add(healthBar);
            }
        } else {
            Label emptyLabel = new Label(" ");
            container.getChildren().add(emptyLabel);
        }

        container.setAlignment(Pos.CENTER);
    }


    public VBox getContainer() {
        return container;
    }
}
