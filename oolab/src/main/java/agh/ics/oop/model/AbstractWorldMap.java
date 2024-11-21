package agh.ics.oop.model;

import agh.ics.oop.model.util.MapVisualizer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractWorldMap implements WorldMap {
    protected MapVisualizer mapVisualizer;
    protected Map<Vector2d, Animal> animals = new HashMap<>();

    List<MapChangeListener> observers = new ArrayList<>();

    public void addObserver(MapChangeListener observer) {
        observers.add(observer);
    }

    public void removeObserver(MapChangeListener observer) {
        observers.remove(observer);
    }

    public void mapChanged(String message) {
        for (MapChangeListener observer : observers) {
            observer.mapChanged(this, message);
        }
    }

    public AbstractWorldMap() {
        this.mapVisualizer = new MapVisualizer(this);
    }

    public abstract Boundary getCurrentBounds();

    @Override
    public void place(Animal animal) throws IncorrectPositionException {
        if(canMoveTo(animal.getPosition())){
            animals.put(animal.getPosition(), animal);
            mapChanged("Animal placed on position " + animal.getPosition());
        }else {
            throw new IncorrectPositionException(animal.getPosition());
        }
    }

    @Override
    public void move(Animal animal, MoveDirection direction) {
        if(!animals.containsKey(animal.getPosition())) return;

        Vector2d oldPosition = animal.getPosition();
        MapDirection oldDirection = animal.getDirection();
        animal.move(this, direction);
        Vector2d newPosition = animal.getPosition();
        MapDirection newDirection = animal.getDirection();
        if(newPosition != oldPosition){
            mapChanged("Animal moved from " + oldPosition + " to position " + newPosition);
            animals.put(newPosition, animal);
            animals.remove(oldPosition);
        }
        if(newDirection != oldDirection) {
            mapChanged("Animal changed direction from " + oldDirection + " to " + newDirection);
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
        return getCurrentBounds().lowerLeft() != null ? mapVisualizer.draw(getCurrentBounds().lowerLeft(), getCurrentBounds().upperRight()) : "Mapa jest pusta!";
    }

    public List<WorldElement> getElements(){
        return new ArrayList<>(animals.values());
    }
}
