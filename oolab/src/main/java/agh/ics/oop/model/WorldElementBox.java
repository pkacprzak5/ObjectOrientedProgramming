package agh.ics.oop.model;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class WorldElementBox {
    private static final Map<String, Image> imageCache = new HashMap<>();
    private VBox container;

    public WorldElementBox(Optional<WorldElement> element) {
        container = new VBox();

        if (element.isPresent()) {
            WorldElement worldElement = element.get();
            String positionInfo = worldElement + worldElement.getPosition().toString();
            Image image = new Image(String.valueOf(getClass().getClassLoader().getResource(worldElement.getResourceName())));
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(30);
            imageView.setFitHeight(30);

            //Label positionLabel = new Label(positionInfo);

            container.getChildren().addAll(imageView);//, positionLabel);
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
