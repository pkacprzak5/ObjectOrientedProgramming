package agh.ics.oop.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MapDirectionTest {

    @Test
    void testToString() {
        assertEquals("NORTH", MapDirection.NORTH.toString());
        assertEquals("EAST", MapDirection.EAST.toString());
        assertEquals("SOUTH", MapDirection.SOUTH.toString());
        assertEquals("WEST", MapDirection.WEST.toString());
        assertEquals("NORTHEAST", MapDirection.NORTHEAST.toString());
        assertEquals("SOUTHEAST", MapDirection.SOUTHEAST.toString());
        assertEquals("NORTHWEST", MapDirection.NORTHWEST.toString());
        assertEquals("SOUTHWEST", MapDirection.SOUTHWEST.toString());
    }

    @Test
    void next() {
        assertEquals(MapDirection.NORTHEAST, MapDirection.NORTH.next());
        assertEquals(MapDirection.EAST, MapDirection.NORTHEAST.next());
        assertEquals(MapDirection.SOUTHEAST, MapDirection.EAST.next());
        assertEquals(MapDirection.SOUTH, MapDirection.SOUTHEAST.next());
        assertEquals(MapDirection.SOUTHWEST, MapDirection.SOUTH.next());
        assertEquals(MapDirection.WEST, MapDirection.SOUTHWEST.next());
        assertEquals(MapDirection.NORTHWEST, MapDirection.WEST.next());
        assertEquals(MapDirection.NORTH, MapDirection.NORTHWEST.next());
    }

    @Test
    void testNext() {
        assertEquals(MapDirection.EAST, MapDirection.NORTH.next(2)); // NORTH -> NORTHEAST -> EAST
        assertEquals(MapDirection.SOUTHWEST, MapDirection.NORTH.next(5)); // NORTH -> SOUTHEAST -> ...
        assertEquals(MapDirection.NORTH, MapDirection.NORTH.next(8)); // Full cycle
        assertEquals(MapDirection.WEST, MapDirection.EAST.next(4)); // Clockwise rotations
    }

    @Test
    void toUnitVector() {
        assertEquals(new Vector2d(0, 1), MapDirection.NORTH.toUnitVector());
        assertEquals(new Vector2d(1, 0), MapDirection.EAST.toUnitVector());
        assertEquals(new Vector2d(0, -1), MapDirection.SOUTH.toUnitVector());
        assertEquals(new Vector2d(-1, 0), MapDirection.WEST.toUnitVector());
        assertEquals(new Vector2d(1, 1), MapDirection.NORTHEAST.toUnitVector());
        assertEquals(new Vector2d(1, -1), MapDirection.SOUTHEAST.toUnitVector());
        assertEquals(new Vector2d(-1, 1), MapDirection.NORTHWEST.toUnitVector());
        assertEquals(new Vector2d(-1, -1), MapDirection.SOUTHWEST.toUnitVector());
    }
}
