package agh.ics.oop;

import agh.ics.oop.model.Animal;
import agh.ics.oop.model.MoveDirection;
import agh.ics.oop.model.Vector2d;
import agh.ics.oop.model.WorldMap;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Simulation {
    private ArrayList<Animal> animals = new ArrayList<>(); //ze względu częsty dostęp do elementów lsity
    private List<MoveDirection> moveDirections = new ArrayList<>(); //tutaj jedynie przechowujemy informacje
    private WorldMap worldMap;

    public ArrayList<Animal> getAnimals() {
        return animals;
    }

    public List<MoveDirection> getMoveDirections() {
        return moveDirections;
    }

    public Simulation(List<Vector2d> positions, List<MoveDirection> moves, WorldMap worldMap) {
        this.worldMap = worldMap;
        for(Vector2d position : positions) {
            if(worldMap.place(new Animal(position))){
                animals.add(new Animal(position));
            }
        }
        moveDirections = moves;
    }

    public void run(){
        int idx = 0;
        while(idx < moveDirections.size()) {
            for(int i = 0; i < animals.size(); i++) {
                worldMap.move(animals.get(i), moveDirections.get(idx));
                System.out.println(worldMap);
                idx++;
                if(idx == moveDirections.size()) {
                    break;
                }
            }
        }
    }
}
