package agh.ics.oop;

import agh.ics.oop.model.MoveDirection;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OptionsParserTest {
    @Test
    void parseForward() {
        String[] args = new String[]{"f"};
        MoveDirection[] moveDirection = {MoveDirection.FORWARD};

        assertArrayEquals(moveDirection, OptionsParser.DirectionParser(args).toArray());
    }

    @Test
    void parseBackward() {
        String[] args = new String[]{"b"};
        MoveDirection[] moveDirection = {MoveDirection.BACKWARD};

        assertArrayEquals(moveDirection, OptionsParser.DirectionParser(args).toArray());
    }

    @Test
    void parseLeft() {
        String[] args = new String[]{"l"};
        MoveDirection[] moveDirection = {MoveDirection.LEFT};

        assertArrayEquals(moveDirection, OptionsParser.DirectionParser(args).toArray());
    }

    @Test
    void parseRight() {
        String[] args = new String[]{"r"};
        MoveDirection[] moveDirection = {MoveDirection.RIGHT};

        assertArrayEquals(moveDirection, OptionsParser.DirectionParser(args).toArray());
    }

    @Test
    void parseMultipleDirections() {
        String[] args = new String[]{"f", "b", "l", "r", "f", "r"};
        MoveDirection[] moveDirection = {
                MoveDirection.FORWARD,
                MoveDirection.BACKWARD,
                MoveDirection.LEFT,
                MoveDirection.RIGHT,
                MoveDirection.FORWARD,
                MoveDirection.RIGHT
        };

        assertArrayEquals(moveDirection, OptionsParser.DirectionParser(args).toArray());
    }

    @Test
    void handleInvalidValue() {
        String[] args = new String[]{"w"};
        MoveDirection[] moveDirection = {};

        assertArrayEquals(moveDirection, OptionsParser.DirectionParser(args).toArray());
    }

    @Test
    void handleInvalidValues() {
        String[] args = new String[]{"w", "w", "str", "test"};
        MoveDirection[] moveDirection = {};

        assertArrayEquals(moveDirection, OptionsParser.DirectionParser(args).toArray());
    }

    @Test
    void handleAllValues(){
        String[] args = new String[]{"f", "b", "str", "w", "f", "r"};
        MoveDirection[] moveDirection = {
                MoveDirection.FORWARD,
                MoveDirection.BACKWARD,
                MoveDirection.FORWARD,
                MoveDirection.RIGHT
        };

        assertArrayEquals(moveDirection, OptionsParser.DirectionParser(args).toArray());
    }
}