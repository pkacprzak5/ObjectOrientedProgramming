package agh.ics.oop.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Vector2dTest {

    @Test
    void getY() {
        Vector2d vector = new Vector2d(3, 4);
        assertEquals(4, vector.getY());
    }

    @Test
    void getX() {
        Vector2d vector = new Vector2d(3, 4);
        assertEquals(3, vector.getX());
    }

    @Test
    void testToString() {
        Vector2d vector = new Vector2d(3, 4);
        assertEquals("(3,4)", vector.toString());
    }

    @Test
    void precedes() {
        Vector2d vector1 = new Vector2d(2, 3);
        Vector2d vector2 = new Vector2d(3, 4);
        assertTrue(vector1.precedes(vector2));
        assertFalse(vector2.precedes(vector1));
    }

    @Test
    void follows() {
        Vector2d vector1 = new Vector2d(3, 4);
        Vector2d vector2 = new Vector2d(2, 3);
        assertTrue(vector1.follows(vector2));
        assertFalse(vector2.follows(vector1));
    }

    @Test
    void add() {
        Vector2d vector1 = new Vector2d(1, 2);
        Vector2d vector2 = new Vector2d(3, 4);
        assertEquals(new Vector2d(4, 6), vector1.add(vector2));
    }

    @Test
    void subtract() {
        Vector2d vector1 = new Vector2d(5, 7);
        Vector2d vector2 = new Vector2d(3, 4);
        assertEquals(new Vector2d(2, 3), vector1.subtract(vector2));
    }

    @Test
    void upperRight() {
        Vector2d vector1 = new Vector2d(1, 4);
        Vector2d vector2 = new Vector2d(3, 2);
        assertEquals(new Vector2d(3, 4), vector1.upperRight(vector2));
    }

    @Test
    void lowerLeft() {
        Vector2d vector1 = new Vector2d(1, 4);
        Vector2d vector2 = new Vector2d(3, 2);
        assertEquals(new Vector2d(1, 2), vector1.lowerLeft(vector2));
    }

    @Test
    void opposite() {
        Vector2d vector = new Vector2d(3, -4);
        assertEquals(new Vector2d(-3, 4), vector.opposite());
    }

    @Test
    void testEquals() {
        Vector2d vector1 = new Vector2d(3, 4);
        Vector2d vector2 = new Vector2d(3, 4);
        Vector2d vector3 = new Vector2d(4, 3);
        assertEquals(vector1, vector2);
        assertNotEquals(vector1, vector3);
    }

    @Test
    void testHashCode() {
        Vector2d vector1 = new Vector2d(3, 4);
        Vector2d vector2 = new Vector2d(3, 4);
        assertEquals(vector1.hashCode(), vector2.hashCode());
    }
}
