package agh.ics.oop;

import agh.ics.oop.model.MoveDirection;

public class World {
    public static void main(String[] args) {
        System.out.println("system wystartował");
//        run(args);
        run(OptionsParser.DirectionParser(args));
        System.out.println("system zakończył działanie");
    }
    public static void run(/*String[] args*/MoveDirection[] directions) {
        System.out.println("Start");
//        for(int i = 0; i < args.length-1; i++) {
//            System.out.print(args[i] + ", ");
//        }
//        System.out.println(args[args.length-1]);

//        for(String arg : args) {
//            String message = switch (arg){
//                case "l" -> "skręca w lewo";
//                case "p" -> "skręca w prawo";
//                case "f" -> "idzie do przodu";
//                case "b" -> "idzie do tyłu";
//                default -> null;
//            };
//            if(message != null) {
//                System.out.println("Zwierzak " + message);
//            }
//        }

        for (MoveDirection direction : directions) {
            String message = switch (direction){
                case LEFT -> "skręca w lewo";
                case RIGHT -> "skręca w prawo";
                case FORWARD -> "idzie do przodu";
                case BACKWARD -> "idzie do tyłu";
            };
            System.out.println("Zwierzak " + message);
        }


        System.out.println("Stop");
    }
}
