package agh.ics.oop;

public class World {
    public static void main(String[] args) {
        System.out.println("system wystartował");
        run(args);
        System.out.println("system zakończył działanie");
    }
    public static void run(String[] args){
        System.out.println("zwierzak wystartował");
        for(int i = 0; i < args.length-1; i++) {
            System.out.print(args[i] + ", ");
        }
        System.out.println(args[args.length-1]);
    }
}
