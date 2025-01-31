package agh.ics.oop;

import agh.ics.oop.model.MapDirection;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class OptionsParser {
    public static List<MapDirection> DirectionParser(String[] args) {
        return Stream.of(args)
                .map(arg -> switch (arg) {
                    case "f" -> MapDirection.NORTH;
                    case "b" -> MapDirection.SOUTH;
                    case "r" -> MapDirection.EAST;
                    case "l" -> MapDirection.WEST;
                    default -> throw new IllegalArgumentException(arg + " is not legal move specification");
                })
                .toList();
    }
}