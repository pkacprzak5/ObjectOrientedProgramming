package agh.ics.oop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RectangularMapFireTest {

    private RectangularMapFire map;
    private final Vector2d testPosition= new Vector2d(5, 5);
    private final AnimalInformation testInfo = new AnimalInformation(0,30,4);
    private final AnimalInformation testInfo2 = new AnimalInformation(0,20,4);
    private final AnimalInformation testInfo3 = new AnimalInformation(3,10,4);
    private final Animal testAnimal = new Animal(testPosition, testInfo);
    private final Animal testAnimal2 = new Animal(testPosition, testInfo2);
    private final Animal testAnimal3 = new Animal(testPosition, testInfo3);
    private final int fireDayDelay = 2;
    private final int fireInterval = 3;

    @BeforeEach
    void setUp() {
        GrassGenerator grassGenerator = new GrassGenerator(10,10,0,1,10); // Mock grass generator
        Multiplication multiplication = new Multiplication(30,2,2, 10); // Mock multiplication logic
        map = new RectangularMapFire(10, 10, 5, multiplication, grassGenerator, fireDayDelay, fireInterval, new FireSpread(10, 10, fireDayDelay)); // Test implementation
        map.growGrass();
    }

    @Test
    void fireSpread() {
        map.increaseCurrentTime();
        map.fireSpread();
        assertTrue(map.fireMap.isEmpty());

        map.increaseCurrentTime();
        System.out.println(map.getCurrentTime());
        map.fireSpread();
        assertFalse(map.fireMap.isEmpty());
    }

    @Test
    void fireClear() {
        map.increaseCurrentTime();
        map.increaseCurrentTime();
        map.fireSpread();
        map.increaseCurrentTime();
        map.fireSpread();
        map.fireSpread();
        map.fireSpread();
        map.fireClear();

        assertTrue(map.fireMap.isEmpty());
    }

}