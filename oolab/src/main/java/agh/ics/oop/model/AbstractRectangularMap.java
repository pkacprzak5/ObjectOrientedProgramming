package agh.ics.oop.model;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractRectangularMap implements WorldMap{
    protected int width;
    protected int height;
    protected Map<Vector2d, Animal> animals = new HashMap<>();
    protected Map<Vector2d, Grass> grass = new HashMap<>();

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return animals.containsKey(position) || grass.containsKey(position);
    }
}
