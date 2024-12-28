package agh.ics.oop.model;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class GrassGeneratorTest {

    @Test
    void startGenerate() {
        int width = 100;
        int height = 100;
        int startAmount = 50;
        int dailyAmount = 10;
        int energyGiven = 5;
        GrassGenerator grassGenerator = new GrassGenerator(width, height, startAmount, dailyAmount, energyGiven);

        // Generate initial grass
        Map<Vector2d, Grass> grassMap = grassGenerator.StartGenerate();

        // Assert correct number of grass
        assertEquals(startAmount, grassMap.size(), "Initial amount of grass does not match");

        // Check for duplicate positions
        assertEquals(startAmount, grassMap.keySet().size(), "Duplicate positions found in initial grass generation");
    }

    @Test
    void daylyGenerate() {
        int width = 100;
        int height = 100;
        int startAmount = 50;
        int dailyAmount = 10;
        int energyGiven = 5;
        GrassGenerator grassGenerator = new GrassGenerator(width, height, startAmount, dailyAmount, energyGiven);

        // Generate initial grass
        Map<Vector2d, Grass> initialGrass = grassGenerator.StartGenerate();
        int initialCount = initialGrass.size();

        // Generate daily grass
        Map<Vector2d, Grass> dailyGrass = grassGenerator.DaylyGenerate();

        // Assert correct number of daily grass added
        assertEquals(dailyAmount, dailyGrass.size(), "Daily amount of grass does not match");

        // Ensure no duplicates between initial and daily grass
        for (Vector2d position : dailyGrass.keySet()) {
            assertFalse(initialGrass.containsKey(position), "Duplicate position found between initial and daily grass");
        }
    }

    @Test
    void grassDistribution() {
        int width = 100;
        int height = 100;
        int startAmount = 100;
        int dailyAmount = 20;
        int energyGiven = 5;
        GrassGenerator grassGenerator = new GrassGenerator(width, height, startAmount, dailyAmount, energyGiven);

        // Generate grass
        Map<Vector2d, Grass> grassMap = grassGenerator.StartGenerate();

        // Calculate equator bounds
        int equatorHeightStart = (int) (height / 2 - (height * 0.2) / 2);
        int equatorHeightEnd = (int) (height / 2 + (height * 0.2) / 2);

        long equatorGrassCount = grassMap.keySet().stream()
                .filter(pos -> pos.getY() >= equatorHeightStart && pos.getY() < equatorHeightEnd)
                .count();

        long otherGrassCount = grassMap.size() - equatorGrassCount;

        // Assert distribution
        assertTrue(equatorGrassCount >= startAmount * 0.8, "Less than 80% grass in equator");
        assertTrue(otherGrassCount <= startAmount * 0.2, "More than 20% grass outside equator");
    }
}
