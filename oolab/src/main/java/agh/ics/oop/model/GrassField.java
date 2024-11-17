package agh.ics.oop.model;

import agh.ics.oop.model.util.MapVisualizer;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import static java.lang.Math.min;
import static java.lang.Math.sqrt;

public class GrassField implements WorldMap{
    private int numberOfGrass;
    public Map<Vector2d, Grass> grasses = new HashMap<>();
    private Map<Vector2d, Animal> animals = new HashMap<>();

    public GrassField(int numberOfGrass) {
        this.numberOfGrass = numberOfGrass;
        int min = 0;
        int max = (int) sqrt(numberOfGrass*10);
        int i = 0;
        while (i < this.numberOfGrass) {
            int randomX = ThreadLocalRandom.current().nextInt(min, max + 1);
            int randomY = ThreadLocalRandom.current().nextInt(min, max + 1);
            Vector2d grassPosition = new Vector2d(randomX, randomY);
            if(!grasses.containsKey(grassPosition)) {
                grasses.put(grassPosition, new Grass(grassPosition));
                i++;
            }
        }
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
        return animals.containsKey(position) || grasses.containsKey(position);
    }

    @Override
    public WorldElement objectAt(Vector2d position) {
        return animals.get(position) != null ? animals.get(position) : grasses.get(position);
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return !animals.containsKey(position);
    }

    public Vector2d getLowerLeft() {
        Vector2d lowerLeft = new Vector2d(Integer.MAX_VALUE, Integer.MAX_VALUE);
        for(Vector2d vector : animals.keySet()){
            lowerLeft = lowerLeft.lowerLeft(vector);
        }
        for(Vector2d vector : grasses.keySet()){
            lowerLeft = lowerLeft.lowerLeft(vector);
        }
        return lowerLeft;
    }

    public Vector2d getUpperRight() {
        Vector2d upperRight = new Vector2d(Integer.MIN_VALUE, Integer.MIN_VALUE);
        for(Vector2d vector : grasses.keySet()){
            upperRight = upperRight.upperRight(vector);
        }
        for(Vector2d vector : grasses.keySet()){
            upperRight = upperRight.upperRight(vector);
        }
        return upperRight;
    }

    @Override
    public String toString() {
        return new MapVisualizer(this).draw(getLowerLeft(), getUpperRight());
    }
}
