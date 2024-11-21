package agh.ics.oop.model;

import agh.ics.oop.model.util.MapVisualizer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import static java.lang.Math.min;
import static java.lang.Math.sqrt;

public class GrassField extends AbstractWorldMap{
    private final int numberOfGrass;
    public Map<Vector2d, Grass> grasses = new HashMap<>();

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
    public boolean isOccupied(Vector2d position) {
        return super.isOccupied(position) || grasses.containsKey(position);
    }

    @Override
    public WorldElement objectAt(Vector2d position) {
        return super.objectAt(position) != null ? super.objectAt(position) : grasses.get(position);
    }

    @Override
    public Vector2d getLowerLeft() {
        Vector2d lowerLeft = new Vector2d(Integer.MAX_VALUE, Integer.MAX_VALUE);
        for (WorldElement element : getElements()) {
            lowerLeft = element.getPosition().lowerLeft(lowerLeft);
        }
        return lowerLeft;
    }

    @Override
    public Vector2d getUpperRight() {
        Vector2d upperRight = new Vector2d(Integer.MIN_VALUE, Integer.MIN_VALUE);
        for(WorldElement element : getElements()){
            upperRight = element.getPosition().upperRight(upperRight);
        }
        return upperRight;
    }

    @Override
    public List<WorldElement> getElements() {
        List<WorldElement> elements = super.getElements();
        elements.addAll(grasses.values());
        return elements;
    }
}
