package agh.ics.oop;

import agh.ics.oop.model.*;

import java.util.List;

public class World {
    public static void main(String[] args) {
        List<MoveDirection> directions = OptionsParser.DirectionParser(args);
//        List<Vector2d> positions = List.of(new Vector2d(2,2), new Vector2d(3,4));
//        RectangularMap worldMap = new RectangularMap(5, 5);
//        Simulation simulation = new Simulation(positions, directions, worldMap);
//        simulation.run();

        List<String> napisy = List.of(new String[]{"Ala", "ma", "sowoniedźwiedzia"});
        TextMap map = new TextMap();
        for(String element : napisy) {
            map.place(element);
        }
        Simulation<String, Integer> simulation = new Simulation<>(napisy, directions, map);
        simulation.run();

//        Animal animal = new Animal();
//        System.out.println(animal);


//        Vector2d position1 = new Vector2d(1,2);
//        System.out.println(position1);
//        Vector2d position2 = new Vector2d(-2,1);
//        System.out.println(position2);
//        System.out.println(position1.add(position2));
//
//        MapDirection mapDirection = MapDirection.EAST;
//        System.out.println(mapDirection);
//        System.out.println(mapDirection.next());
//        System.out.println(mapDirection.previous());
//        System.out.println(mapDirection.toUnitVector());

//        System.out.println("system wystartował");
//        run(args);
//        run(OptionsParser.DirectionParser(args));
//        System.out.println("system zakończył działanie");
    }
    public static void run(/*String[] args*/List<MoveDirection> directions) {
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
