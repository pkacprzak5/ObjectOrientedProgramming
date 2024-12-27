package agh.ics.oop.model;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public abstract class AbstractRectangularMap implements WorldMap{
    protected int width;
    protected int height;
    protected Map<Vector2d, PriorityQueue<Animal>> animals = new HashMap<>();
    protected Map<Vector2d, Grass> grass = new HashMap<>();
    protected AnimalComparator animalComparator = new AnimalComparator();

    public int getWidth() {
        return width;
    }

    @Override
    public void place(Animal animal){
        if(!animals.containsKey(animal.getPosition())){
            animals.put(animal.getPosition(), new PriorityQueue<>(animalComparator));
        }
        animals.get(animal.getPosition()).add(animal);

    }

    public int getHeight() {
        return height;
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return animals.containsKey(position) || grass.containsKey(position);
    }
}
