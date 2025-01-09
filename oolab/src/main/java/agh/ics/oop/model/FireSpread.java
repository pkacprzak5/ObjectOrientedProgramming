package agh.ics.oop.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class FireSpread {
    protected int width;
    protected int height;
    protected int dayDelay;


    public FireSpread(int width, int height, int dayDelay) {
        this.width = width;
        this.height = height;
        this.dayDelay = dayDelay;
    }

    public Map<Vector2d, Fire> generate(HashMap<Vector2d, Grass> grassMap) {
        Map<Vector2d, Fire> fireMap = new HashMap<>();
        if (grassMap.isEmpty()) {
            return fireMap;
        }
        Random random = new Random();
        Vector2d[] grassPositions = grassMap.keySet().toArray(new Vector2d[0]);
        Vector2d randomPosition = grassPositions[random.nextInt(grassPositions.length)];

        fireMap.put(randomPosition, new Fire(randomPosition,dayDelay));
        return fireMap;
    }

    public Map<Vector2d, Fire> Spread(HashMap<Vector2d, Grass> grassMap, Map<Vector2d, Fire> fireMap) {
        Map<Vector2d, Fire> newFires = new HashMap<>();

        for (Vector2d firePosition : fireMap.keySet()) {
            fireMap.get(firePosition).decrementCounter();
            Vector2d[] neighbors = {
                    new Vector2d(firePosition.getX(), firePosition.getY() + 1), // Góra
                    new Vector2d(firePosition.getX(), firePosition.getY() - 1), // Dół
                    new Vector2d(firePosition.getX() - 1, firePosition.getY()), // Lewo
                    new Vector2d(firePosition.getX() + 1, firePosition.getY())  // Prawo
            };

            for (Vector2d neighbor : neighbors) {
                if (grassMap.containsKey(neighbor) && !fireMap.containsKey(neighbor) && !newFires.containsKey(neighbor)) {
                    newFires.put(neighbor, new Fire(neighbor,dayDelay));
                }
            }
        }

        fireMap.putAll(newFires);

        return fireMap;
    }


}

