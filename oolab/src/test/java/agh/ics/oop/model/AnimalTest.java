package agh.ics.oop.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AnimalTest {

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
        AnimalInformation info = new AnimalInformation(1, 10, 5);
        info.setGenotype(new ArrayList<>(List.of(4, 2, 2, 1)));
        Animal animal = new Animal(new Vector2d(0, 0), info);

        // Initially, the animal is at position (0, 0) and facing NORTH
        assertEquals(new Vector2d(0, 0), animal.getPosition());
        assertEquals(MapDirection.NORTH, animal.getDirection());

        // Move the animal using its genotype (first gene decides the direction)
        animal.move();

        // After the first move, the animal should have changed position and direction
        assertEquals(new Vector2d(0, -1), animal.getPosition());
        assertEquals(MapDirection.SOUTH, animal.getDirection());

        animal.move();
        animal.move();
        // Check that the position has updated again after another move
        assertEquals(MapDirection.NORTH, animal.getDirection());
        assertEquals(new Vector2d(-1, 0), animal.getPosition()); // Ensure position is not the same as initial

        animal.move();
        assertEquals(MapDirection.NORTHEAST, animal.getDirection());
        assertEquals(new Vector2d(0, 1), animal.getPosition());
    }
}
