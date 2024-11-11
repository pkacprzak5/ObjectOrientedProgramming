package agh.ics.oop;

import agh.ics.oop.model.Animal;
import agh.ics.oop.model.MoveDirection;
import agh.ics.oop.model.Vector2d;
import agh.ics.oop.model.WorldMap;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Simulation<T, P> {
    private List<T> animals; //ze względu częsty dostęp do elementów lsity
    private List<MoveDirection> moveDirections; //tutaj jedynie przechowujemy informacje
    private WorldMap<T, P> worldMap;

    public List<T> getAnimals() {
        return animals;
    }

    public List<MoveDirection> getMoveDirections() {
        return moveDirections;
    }

    public Simulation(List<T> animals, List<MoveDirection> moves, WorldMap<T, P> worldMap) {
        this.animals = animals;
        this.moveDirections = moves;
        this.worldMap = worldMap;
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
