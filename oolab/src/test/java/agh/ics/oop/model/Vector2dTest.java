package agh.ics.oop.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class Vector2dTest {

    @Test
    void areVectorsEqual() {
        Vector2d v1 = new Vector2d(1, 1);

//        True cases
        assertTrue(v1.equals(new Vector2d(1, 1)));

//        False cases
        assertFalse(v1.equals(new Vector2d(0, 0)));
        assertFalse(v1.equals(new Vector2d(0, 1)));
        assertFalse(v1.equals(new Vector2d(0, 2)));
        assertFalse(v1.equals(new Vector2d(1, 0)));
        assertFalse(v1.equals(new Vector2d(1, 2)));
        assertFalse(v1.equals(new Vector2d(2, 0)));
        assertFalse(v1.equals(new Vector2d(2, 1)));
        assertFalse(v1.equals(new Vector2d(2, 2)));
        assertFalse(v1.equals(null));
    }

    @Test
    void changingVectorToString() {
        Vector2d v1 = new Vector2d(1, 1);

        assertEquals("(1,1)", v1.toString());
    }

    @Test
    void doesVectorPreced(){
        Vector2d v1 = new Vector2d(1, 1);

//        True cases
        assertTrue(v1.precedes(new Vector2d(1, 2)));
        assertTrue(v1.precedes(new Vector2d(2, 2)));
        assertTrue(v1.precedes(new Vector2d(1, 1)));
        assertTrue(v1.precedes(new Vector2d(2, 1)));

//        False cases
        assertFalse(v1.precedes(new Vector2d(0, 0)));
        assertFalse(v1.precedes(new Vector2d(0, 1)));
        assertFalse(v1.precedes(new Vector2d(0, 2)));
        assertFalse(v1.precedes(new Vector2d(1, 0)));
        assertFalse(v1.precedes(new Vector2d(2, 0)));
    }

    @Test
    void doesVectorFollow(){
        Vector2d v1 = new Vector2d(1, 1);

//        True cases
        assertTrue(v1.follows(new Vector2d(1, 1)));
        assertTrue(v1.follows(new Vector2d(0, 1)));
        assertTrue(v1.follows(new Vector2d(1, 0)));
        assertTrue(v1.follows(new Vector2d(0, 0)));

//        False cases
        assertFalse(v1.follows(new Vector2d(0, 2)));
        assertFalse(v1.follows(new Vector2d(1, 2)));
        assertFalse(v1.follows(new Vector2d(2, 2)));
        assertFalse(v1.follows(new Vector2d(2, 1)));
        assertFalse(v1.follows(new Vector2d(2, 0)));
    }

    @Test
    void getUpperRight(){
        Vector2d v1 = new Vector2d(1, 1);

        assertEquals(new Vector2d(1,1),v1.upperRight(new Vector2d(0, 0)));
        assertEquals(new Vector2d(1,1),v1.upperRight(new Vector2d(0, 1)));
        assertEquals(new Vector2d(1,1),v1.upperRight(new Vector2d(1, 0)));
        assertEquals(new Vector2d(1,1),v1.upperRight(new Vector2d(1, 1)));

        assertEquals(new Vector2d(1,2),v1.upperRight(new Vector2d(1, 2)));
        assertEquals(new Vector2d(1,2),v1.upperRight(new Vector2d(0, 2)));

        assertEquals(new Vector2d(2,1),v1.upperRight(new Vector2d(2, 1)));
        assertEquals(new Vector2d(2,1),v1.upperRight(new Vector2d(2, 0)));

        assertEquals(new Vector2d(2,2),v1.upperRight(new Vector2d(2, 2)));
    }

    @Test
    void getLowerLeft(){
        Vector2d v1 = new Vector2d(1, 1);

        assertEquals(new Vector2d(1,1),v1.lowerLeft(new Vector2d(1, 1)));
        assertEquals(new Vector2d(1,1),v1.lowerLeft(new Vector2d(1, 2)));
        assertEquals(new Vector2d(1,1),v1.lowerLeft(new Vector2d(2, 1)));
        assertEquals(new Vector2d(1,1),v1.lowerLeft(new Vector2d(2, 2)));

        assertEquals(new Vector2d(0,1),v1.lowerLeft(new Vector2d(0, 1)));
        assertEquals(new Vector2d(0,1),v1.lowerLeft(new Vector2d(0, 2)));

        assertEquals(new Vector2d(1,0),v1.lowerLeft(new Vector2d(1, 0)));
        assertEquals(new Vector2d(1,0),v1.lowerLeft(new Vector2d(2, 0)));

        assertEquals(new Vector2d(0,0),v1.lowerLeft(new Vector2d(0, 0)));
    }

    @Test
    void addingVector(){
        Vector2d v1 = new Vector2d(1, 1);
        Vector2d v2 = new Vector2d(1, 2);

        assertEquals(new Vector2d(2,3), v1.add(v2));
    }

    @Test
    void subtractingVector(){
        Vector2d v1 = new Vector2d(1, 1);
        Vector2d v2 = new Vector2d(1, 2);

        assertEquals(new Vector2d(0,-1), v1.subtract(v2));
    }

    @Test
    void oppositeVector(){
        Vector2d v1 = new Vector2d(2, 3);

        assertEquals(new Vector2d(-2,-3), v1.opposite());
    }
}
