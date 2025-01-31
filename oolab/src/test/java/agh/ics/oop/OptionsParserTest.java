package agh.ics.oop;

import agh.ics.oop.model.MapDirection;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OptionsParserTest {
    @Test
    void parseForward() {
        String[] args = new String[]{"f"};
        MapDirection[] moveDirection = {MapDirection.NORTH};

        assertArrayEquals(moveDirection, OptionsParser.DirectionParser(args).toArray());
    }

    @Test
    void parseBackward() {
        String[] args = new String[]{"b"};
        MapDirection[] moveDirection = {MapDirection.SOUTH};

        assertArrayEquals(moveDirection, OptionsParser.DirectionParser(args).toArray());
    }

    @Test
    void parseLeft() {
        String[] args = new String[]{"l"};
        MapDirection[] moveDirection = {MapDirection.WEST};

        assertArrayEquals(moveDirection, OptionsParser.DirectionParser(args).toArray());
    }

    @Test
    void parseRight() {
        String[] args = new String[]{"r"};
        MapDirection[] moveDirection = {MapDirection.EAST};

        assertArrayEquals(moveDirection, OptionsParser.DirectionParser(args).toArray());
    }

    @Test
    void parseMultipleDirections() {
        String[] args = new String[]{"f", "b", "l", "r", "f", "r"};
        MapDirection[] moveDirection = {
                MapDirection.NORTH,
                MapDirection.SOUTH,
                MapDirection.WEST,
                MapDirection.EAST,
                MapDirection.NORTH,
                MapDirection.EAST
        };

        assertArrayEquals(moveDirection, OptionsParser.DirectionParser(args).toArray());
    }

    @Test
    void handleInvalidValue() {
        String[] args = new String[]{"w"};

        assertThrows(IllegalArgumentException.class, () -> OptionsParser.DirectionParser(args).toArray());
    }

    @Test
    void handleInvalidValues() {
        String[] args = new String[]{"w", "w", "str", "test"};

        assertThrows(IllegalArgumentException.class, () -> OptionsParser.DirectionParser(args).toArray());
    }

    @Test
    void handleAllValues(){
        String[] args = new String[]{"f", "b", "str", "w", "f", "r"};
        MapDirection[] moveDirection = {
                MapDirection.NORTH,
                MapDirection.SOUTH,
                MapDirection.NORTH,
                MapDirection.EAST
        };

        assertThrows(IllegalArgumentException.class, () -> OptionsParser.DirectionParser(args).toArray());
    }
}