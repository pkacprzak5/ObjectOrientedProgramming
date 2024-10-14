package agh.ics.oop;

public class World {
    public static void main(String[] args) {
        System.out.println("system wystartował");
        run(args);
        System.out.println("system zakończył działanie");
    }
    public static void run(String[] args){
        System.out.println("START");
        for(int i = 0; i < args.length-1; i++) {
            System.out.print(args[i] + ", ");
        }
        System.out.println(args[args.length-1]);

        for(String arg : args) {
            String message = switch (arg){
                case "l" -> "skręca w lewo";
                case "p" -> "skręca w prawo";
                case "f" -> "idzie do przodu";
                case "b" -> "idzie do tyłu";
                default -> null;
            };
            if(message != null) {
                System.out.println("Zwierzak " + message);
            }
        }
        System.out.println("STOP");
    }
}
