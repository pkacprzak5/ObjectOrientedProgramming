package agh.ics.oop.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class GrassGenerator {
    protected int width;
    protected int height;
    private final float equatorSize = 0.2F;
    private final float equatorAmount = 0.2f;
    protected int dailyAmount;
    protected int startAmount;
    protected int energyGiven;

    public GrassGenerator(int width, int height, int startAmount, int dailyAmount, int energyGiven) {
        this.width = width;
        this.height = height;
        this.startAmount = startAmount;
        this.dailyAmount = dailyAmount;
        this.energyGiven = energyGiven;
    }

    public Map<Vector2d, Grass> StartGenerate(){
        return generate(startAmount);
    }

    public Map<Vector2d, Grass> DaylyGenerate(){
        return generate(dailyAmount);
    }

    private Map<Vector2d, Grass> generate(int amount) {
        Map<Vector2d, Grass> generatedGrass = new HashMap<>();
        Random random = new Random();

        int equatorHeightStart = (int) (height / 2 - (height * equatorSize) / 2);
        int equatorHeightEnd = (int) (height / 2 + (height * equatorSize) / 2);

        int otherGrassAmount = (int) (amount * equatorAmount);
        int equatorGrassAmount = amount - otherGrassAmount;

        // in equator
        for (int i = 0; i < equatorGrassAmount; i++) {
            Vector2d position;
            do {
                int x = random.nextInt(width);
                int y = random.nextInt(equatorHeightEnd - equatorHeightStart) + equatorHeightStart;
                position = new Vector2d(x, y);
            } while (generatedGrass.containsKey(position));
            generatedGrass.put(position, new Grass(position, energyGiven));
        }

        // out equator
        for (int i = 0; i < otherGrassAmount; i++) {
            Vector2d position;
            do {
                int x = random.nextInt(width);
                int y;
                do {
                    y = random.nextInt(height);
                } while (y >= equatorHeightStart && y < equatorHeightEnd);
                position = new Vector2d(x, y);
            } while (generatedGrass.containsKey(position));
            generatedGrass.put(position, new Grass(position, energyGiven));
        }

        return generatedGrass;
    }

}

