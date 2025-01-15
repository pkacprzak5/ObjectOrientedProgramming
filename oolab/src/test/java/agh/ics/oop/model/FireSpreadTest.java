package agh.ics.oop.model;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class FireSpreadTest {

    private int width = 10;
    private int height = 10;
    private int dayDelay = 2;

    private FireSpread fireSpread = new FireSpread(width, height, dayDelay);
    
    @Test
    void generateEmpty() {
        Map<Vector2d, Grass> grass = new HashMap<>();
        Map<Vector2d, Fire> fire = new HashMap<>();

        assertEquals(fireSpread.generate(grass), fire);
    }

    @Test
    void generate() {
        Map<Vector2d, Grass> grass = new HashMap<>();
        Map<Vector2d, Fire> fire = new HashMap<>();

        grass.put(new Vector2d(1,1), new Grass(new Vector2d(1, 1), 5));
        grass.put(new Vector2d(2,1), new Grass(new Vector2d(2, 1), 5));
        grass.put(new Vector2d(1,2), new Grass(new Vector2d(1, 2), 5));
        grass.put(new Vector2d(2,2), new Grass(new Vector2d(2, 2), 5));
        grass.put(new Vector2d(3,1), new Grass(new Vector2d(3, 1), 5));

        Map<Vector2d, Fire> generatedFire = fireSpread.generate(grass);
        for (Map.Entry<Vector2d, Fire> entry : generatedFire.entrySet()){
            assertTrue(grass.containsKey(entry.getKey()));
        }

        assertNotEquals(fireSpread.generate(grass), fire);
    }

    @Test
    void spread() {
        Map<Vector2d, Grass> grass = new HashMap<>();
        Map<Vector2d, Fire> fire = new HashMap<>();

        grass.put(new Vector2d(1,1), new Grass(new Vector2d(1, 1), 5));
        grass.put(new Vector2d(2,1), new Grass(new Vector2d(2, 1), 5));
        grass.put(new Vector2d(1,2), new Grass(new Vector2d(1, 2), 5));
        grass.put(new Vector2d(2,2), new Grass(new Vector2d(2, 2), 5));
        grass.put(new Vector2d(3,1), new Grass(new Vector2d(3, 1), 5));

        fire.put(new Vector2d(1, 1), new Fire(new Vector2d(1, 1), dayDelay));

        Map<Vector2d, Fire> spreadFire = fireSpread.Spread(grass, fire);

        assertTrue(spreadFire.containsKey(new Vector2d(2, 1)));
        assertTrue(spreadFire.containsKey(new Vector2d(1, 2)));
        assertEquals(3, spreadFire.size());
    }
}