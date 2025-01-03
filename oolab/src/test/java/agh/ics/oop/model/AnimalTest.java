package agh.ics.oop.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AnimalTest {

    // Mock TestMap class extending AbstractRectangularMap
    private static class TestMap extends AbstractRectangularMap {
        public TestMap(int width, int height) {
            super(width, height, 1,
                    new Multiplication(5, 0, 1),
                    new GrassGenerator(0, 0, 0, 0, 0));
        }
    }

    // Test the initial position of the Animal
    @Test
    void getPosition() {
        AnimalInformation info = new AnimalInformation(1, 10, 5);
        Animal animal = new Animal(new Vector2d(3, 4), info);
        assertEquals(new Vector2d(3, 4), animal.getPosition());
    }

    // Test the initial direction of the Animal
    @Test
    void getDirection() {
        AnimalInformation info = new AnimalInformation(1, 10, 5);
        Animal animal = new Animal(new Vector2d(3, 4), info);
        assertEquals(MapDirection.NORTH, animal.getDirection());
    }

    // Test toString method which returns a string representing the direction
    @Test
    void testToString() {
        AnimalInformation info = new AnimalInformation(1, 10, 5);
        Animal animal = new Animal(new Vector2d(0, 0), info);
        assertEquals("N", animal.toString());
    }

    // Test the isAt method, which checks if the animal is at a given position
    @Test
    void isAt() {
        AnimalInformation info = new AnimalInformation(1, 10, 5);
        Animal animal = new Animal(new Vector2d(5, 5), info);

        // Test position that matches
        assertTrue(animal.isAt(new Vector2d(5, 5)));

        // Test position that does not match
        assertFalse(animal.isAt(new Vector2d(0, 0)));
    }

    // Test the move method, which moves the animal according to its genotype and direction
    @Test
    void move() {
        // Create a mock map for testing
        TestMap map = new TestMap(10, 10); // Assuming a map with width and height of 10

        // Define a genotype for the animal that moves in different directions
        AnimalInformation info = new AnimalInformation(1, 10, 5);
        info.setGenotype(new ArrayList<>(List.of(4, 2, 2, 1))); // Example genotype

        // Create the animal with a position and genotype
        Animal animal = new Animal(new Vector2d(5, 5), info);

        // Initially, the animal is at position (0, 0) and facing NORTH
        assertEquals(new Vector2d(5, 5), animal.getPosition());
        assertEquals(MapDirection.NORTH, animal.getDirection());

        // Move the animal once, it should move according to its genotype (gene 4 means south)
        animal.move(map);
        assertEquals(new Vector2d(5, 4), animal.getPosition());
        assertEquals(MapDirection.SOUTH, animal.getDirection());

        // Move again (gene 2 means east)
        animal.move(map);
        assertEquals(new Vector2d(4, 4), animal.getPosition());
        assertEquals(MapDirection.WEST, animal.getDirection());

        // Move again (gene 2 means east)
        animal.move(map);
        assertEquals(new Vector2d(4, 5), animal.getPosition());
        assertEquals(MapDirection.NORTH, animal.getDirection());

        // Move again (gene 1 means north)
        animal.move(map);
        assertEquals(new Vector2d(5, 6), animal.getPosition());
        assertEquals(MapDirection.NORTHEAST, animal.getDirection());
    }

    @Test
    void moveNextToBoundry() {
        // Create a mock map for testing
        TestMap map = new TestMap(10, 10); // Assuming a map with width and height of 10

        // Define a genotype for the animal that moves in different directions
        AnimalInformation info = new AnimalInformation(1, 10, 5);
        info.setGenotype(new ArrayList<>(List.of(4, 6, 7, 7))); // Example genotype
        // Create the animal with a position and genotype
        Animal animal = new Animal(new Vector2d(0, 0), info);
        // Initially, the animal is at position (0, 0) and facing NORTH
        assertEquals(new Vector2d(0, 0), animal.getPosition());
        assertEquals(MapDirection.NORTH, animal.getDirection());
        // Move the animal once, it should move according to its genotype (gene 4 means south)
        animal.move(map);
        assertEquals(new Vector2d(0, 0), animal.getPosition());
        assertEquals(MapDirection.NORTH, animal.getDirection());

        // Move again (gene 2 means east)
        animal.move(map);
        assertEquals(new Vector2d(9, 0), animal.getPosition());
        assertEquals(MapDirection.WEST, animal.getDirection());

        // Move again (gene 2 means east)
        animal.move(map);
        assertEquals(new Vector2d(9, 0), animal.getPosition());
        assertEquals(MapDirection.NORTHEAST, animal.getDirection());

        // Move again (gene 1 means north)
        animal.move(map);
        assertEquals(new Vector2d(9, 1), animal.getPosition());
        assertEquals(MapDirection.NORTH, animal.getDirection());
    }
}
