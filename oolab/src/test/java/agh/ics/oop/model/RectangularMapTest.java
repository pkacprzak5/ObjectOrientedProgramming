package agh.ics.oop.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RectangularMapTest {
    private final int width = 10;
    private final int height = 10;
    Animal[] animals = {
            new Animal(new Vector2d(2, 2)),
            new Animal(new Vector2d(3, 4)),
            new Animal(new Vector2d(9, 4)),
            new Animal(new Vector2d(5, 5)),
            new Animal(new Vector2d(8, 5)),
            new Animal(new Vector2d(4, 4))};
    Animal[] animals2 = {
            new Animal(new Vector2d(2, 2)),
            new Animal(new Vector2d(2, 2))};

    @Test
    public void placement(){
        RectangularMap map = new RectangularMap(width, height);


        for (Animal animal : animals) {
            assertTrue(map.place(animal));
        }
        assertEquals(map.objectAt(new Vector2d(2, 2)), animals[0]);
        assertEquals(map.objectAt(new Vector2d(3, 4)), animals[1]);
        assertEquals(map.objectAt(new Vector2d(9, 4)), animals[2]);
        assertEquals(map.objectAt(new Vector2d(5, 5)), animals[3]);
        assertEquals(map.objectAt(new Vector2d(8, 5)), animals[4]);
        assertEquals(map.objectAt(new Vector2d(4, 4)), animals[5]);
    }

    @Test
    public void wrongPlacement(){
        RectangularMap map = new RectangularMap(width, height);
        map.place(new Animal(new Vector2d(2, 2)));

        for (Animal animal : animals2) {
            assertFalse(map.place(animal));
        }
    }

    @Test
    public void isObjectAt(){
        RectangularMap map = new RectangularMap(width, height);

        for (Animal animal : animals) {
            map.place(animal);
        }
        assertEquals(map.objectAt(new Vector2d(2, 2)), animals[0]);
        assertEquals(map.objectAt(new Vector2d(3, 4)), animals[1]);
        assertEquals(map.objectAt(new Vector2d(9, 4)), animals[2]);
        assertEquals(map.objectAt(new Vector2d(5, 5)), animals[3]);
        assertEquals(map.objectAt(new Vector2d(8, 5)), animals[4]);
        assertEquals(map.objectAt(new Vector2d(4, 4)), animals[5]);

        assertNull(map.objectAt(new Vector2d(0, 0)));
        assertNull(map.objectAt(new Vector2d(2, 0)));
        assertNull(map.objectAt(new Vector2d(0, 2)));
    }

    @Test
    public void occupation(){
        RectangularMap map = new RectangularMap(width, height);
        for (Animal animal : animals) {
            map.place(animal);
        }

        assertTrue(map.isOccupied(new Vector2d(2, 2)));
        assertTrue(map.isOccupied(new Vector2d(3, 4)));
        assertTrue(map.isOccupied(new Vector2d(9, 4)));
        assertTrue(map.isOccupied(new Vector2d(5, 5)));
        assertTrue(map.isOccupied(new Vector2d(8, 5)));
        assertTrue(map.isOccupied(new Vector2d(4, 4)));

        assertFalse(map.isOccupied(new Vector2d(0, 0)));
        assertFalse(map.isOccupied(new Vector2d(2, 0)));
        assertFalse(map.isOccupied(new Vector2d(0, 2)));
    }

    @Test
    public void validation(){
        RectangularMap map = new RectangularMap(width, height);
        for(Animal animal : animals){
            map.place(animal);
        }

        assertFalse(map.canMoveTo(new Vector2d(2, 2)));
        assertFalse(map.canMoveTo(new Vector2d(3, 4)));
        assertFalse(map.canMoveTo(new Vector2d(9, 4)));
        assertFalse(map.canMoveTo(new Vector2d(5, 5)));
        assertFalse(map.canMoveTo(new Vector2d(8, 5)));
        assertFalse(map.canMoveTo(new Vector2d(4, 4)));
        assertFalse(map.canMoveTo(new Vector2d(4, 12)));
        assertFalse(map.canMoveTo(new Vector2d(12, 2)));
        assertFalse(map.canMoveTo(new Vector2d(12, 12)));
        assertFalse(map.canMoveTo(new Vector2d(2, -2)));
        assertFalse(map.canMoveTo(new Vector2d(-2, 3)));
        assertFalse(map.canMoveTo(new Vector2d(-2, -3)));

        assertTrue(map.canMoveTo(new Vector2d(0, 0)));
        assertTrue(map.canMoveTo(new Vector2d(2, 0)));
        assertTrue(map.canMoveTo(new Vector2d(0, 2)));
    }

    @Test
    public void movement(){
        RectangularMap map = new RectangularMap(width, height);

        for (Animal animal : animals) {
            map.place(animal);
        }

        map.move(animals[0], MoveDirection.FORWARD);
        assertEquals(map.objectAt(new Vector2d(2, 3)), animals[0]);
        map.move(animals[1], MoveDirection.FORWARD);
        assertEquals(map.objectAt(new Vector2d(3, 5)), animals[1]);
        map.move(animals[2], MoveDirection.FORWARD);
        assertEquals(map.objectAt(new Vector2d(9, 5)), animals[2]);
        map.move(animals[3], MoveDirection.FORWARD);
        assertEquals(map.objectAt(new Vector2d(5, 6)), animals[3]);
    }
}