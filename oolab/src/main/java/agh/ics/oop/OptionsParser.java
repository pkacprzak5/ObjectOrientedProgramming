package agh.ics.oop;

import agh.ics.oop.model.MoveDirection;

import java.util.Arrays;

public class OptionsParser {
    public static MoveDirection[] DirectionParser(String[] args){
        MoveDirection[] directions = new MoveDirection[args.length];
        int idx = 0;
        for(String arg : args) {
            switch (arg) {
                case "f" -> directions[idx++] = MoveDirection.FORWARD;
                case "b" -> directions[idx++] = MoveDirection.BACKWARD;
                case "r" -> directions[idx++] = MoveDirection.RIGHT;
                case "l" -> directions[idx++] = MoveDirection.LEFT;
                default -> {}
            }
        }
        return Arrays.copyOfRange(directions, 0, idx);
    }
}
