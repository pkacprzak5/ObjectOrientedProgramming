package agh.ics.oop.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AnimalGenerator {
    private final int animalsNumber;

    public AnimalGenerator(int animalsNumber) {
        this.animalsNumber = animalsNumber;
    }

    public List<Animal> generateAnimals(AbstractRectangularMap map, int startEnergy, int genomeSize) {
        int mapWidth = map.getWidth();
        int mapHeight = map.getHeight();
        List<Animal> animals = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < animalsNumber; i++) {
            int x = rand.nextInt(mapWidth);
            int y = rand.nextInt(mapHeight);
            AnimalInformation animalInformation = new AnimalInformation(animalsNumber, startEnergy, genomeSize);
            animals.add(new Animal(new Vector2d(x, y), animalInformation));
        }
        return animals;
    }
}
