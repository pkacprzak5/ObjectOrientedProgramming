package agh.ics.oop.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class GrassGenerator {
    protected int width;
    protected int height;
    protected int dailyAmount;
    protected int startAmount;
    protected int energyToGive;

    public GrassGenerator(int width, int height, int startAmount, int dailyAmount, int energyToGive) {
        this.width = width;
        this.height = height;
        this.startAmount = startAmount;
        this.dailyAmount = dailyAmount;
        this.energyToGive = energyToGive;
    }

    public Map<Vector2d, Grass> startGenerate(){
        return generate(startAmount);
    }

    public Map<Vector2d, Grass> dailyGenerate(){
        return generate(dailyAmount);
    }

    private Map<Vector2d, Grass> generate(int amount) {
        Map<Vector2d, Grass> generatedGrass = new HashMap<>();
        Random random = new Random();
        int equatorStart = (int) (((double) height / 5) * 2);
        int equatorEnd = (int) (((double) height / 5) * 3);

        int counter = 0;
        while (counter < amount) {
            int x = random.nextInt(width);
            int y;
            if(random.nextDouble() < 0.8){
                y = random.nextInt(equatorStart, equatorEnd+1);
            }else{
                if (random.nextDouble() < 0.5) {
                    y = random.nextInt(0, equatorStart + 1);
                }else{
                    y = random.nextInt(equatorEnd+1, height);
                }
            }
            Vector2d pos = new Vector2d(x, y);
            if (!generatedGrass.containsKey(pos)) {
                counter++;
                generatedGrass.put(pos, new Grass(pos, energyToGive));
            }
        }

        return generatedGrass;
    }

}

