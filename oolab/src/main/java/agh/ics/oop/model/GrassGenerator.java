package agh.ics.oop.model;

import java.sql.SQLOutput;
import java.util.*;

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
        Map<Vector2d, Grass> startGrass = new HashMap<>();
        return generate(startAmount, startGrass);
    }

    public Map<Vector2d, Grass> dailyGenerate(Map<Vector2d, Grass> currGrass){
        return generate(dailyAmount, currGrass);
    }

    private Map<Vector2d, Grass> generate(int amount, Map<Vector2d, Grass> grassMap) {
        Random random = new Random();
        int equatorStart = (int) (((double) height / 5) * 2);
        int equatorEnd = (int) (((double) height / 5) * 3);
        List<Vector2d> equatorCoordinates = new ArrayList<>();
        List<Vector2d> nonEquatorCoordinates = new ArrayList<>();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Vector2d position = new Vector2d(x, y);
                if (!grassMap.containsKey(position)) {
                    if (y >= equatorStart && y <= equatorEnd) {
                        equatorCoordinates.add(position);
                    } else {
                        nonEquatorCoordinates.add(position);
                    }
                }
            }
        }

        int counter = 0;
        while (counter < amount && (!equatorCoordinates.isEmpty() || !nonEquatorCoordinates.isEmpty())) {
            counter++;
            if(random.nextDouble() < 0.8 && !equatorCoordinates.isEmpty()) {
                int idx = random.nextInt(equatorCoordinates.size());
                Vector2d position = equatorCoordinates.get(idx);
                equatorCoordinates.remove(idx);
                grassMap.put(position, new Grass(position, energyToGive));
            }else if(!nonEquatorCoordinates.isEmpty()) {
                int idx = random.nextInt(nonEquatorCoordinates.size());
                Vector2d position = nonEquatorCoordinates.get(idx);
                nonEquatorCoordinates.remove(idx);
                grassMap.put(position, new Grass(position, energyToGive));
            }
        }
        return grassMap;
    }

}

