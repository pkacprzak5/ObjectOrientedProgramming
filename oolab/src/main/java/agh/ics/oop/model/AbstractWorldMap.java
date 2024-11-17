package agh.ics.oop.model;

import agh.ics.oop.model.util.MapVisualizer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractWorldMap implements WorldMap {
    protected MapVisualizer mapVisualizer;
    protected Map<Vector2d, Animal> animals = new HashMap<>();

    public abstract Vector2d getLowerLeft();
    public abstract Vector2d getUpperRight();

    public AbstractWorldMap() {
        this.mapVisualizer = new MapVisualizer(this);
    }

    @Override
    public boolean place(Animal animal) {
        if(canMoveTo(animal.getPosition())){
            animals.put(animal.getPosition(), animal);
            return true;
        }
        return false;
    }

    @Override
    public void move(Animal animal, MoveDirection direction) {
        if(!animals.containsKey(animal.getPosition())) return;

        Vector2d oldPosition = animal.getPosition();
        animal.move(this, direction);
        Vector2d newPosition = animal.getPosition();
        if(newPosition != oldPosition){
            animals.put(newPosition, animal);
            animals.remove(oldPosition);
        }
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return animals.containsKey(position);
    }

    @Override
    public WorldElement objectAt(Vector2d position) {
        return animals.get(position);
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return !animals.containsKey(position);
    }

    @Override
    public String toString() {
        return getLowerLeft() != null ? mapVisualizer.draw(getLowerLeft(), getUpperRight()) : "Mapa jest pusta!";
    }

    public List<WorldElement> getElements(){
        return new ArrayList<>(animals.values());
    }
}
