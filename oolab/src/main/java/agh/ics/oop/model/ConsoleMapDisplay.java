package agh.ics.oop.model;

public class ConsoleMapDisplay implements MapChangeListener{
    private int changes = 0;

    @Override
    public void mapChanged(WorldMap worldMap, String message) {
        System.out.println(message);
        System.out.println(worldMap);
        changes++;
        System.out.println("Number of changes: " + changes);
    }
}
