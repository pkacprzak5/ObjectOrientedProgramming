package agh.ics.oop;

import agh.ics.oop.model.Animal;
import agh.ics.oop.model.MoveDirection;
import agh.ics.oop.model.Vector2d;

import java.util.ArrayList;
import java.util.List;

public class Simulation {
    private List<Animal> animals = new ArrayList<>();
    private List<MoveDirection> moveDirections = new ArrayList<>();

    public Simulation(List<Vector2d> positions, List<MoveDirection> moves) {
        for(Vector2d position : positions) {
            animals.add(new Animal(position));
        }
        moveDirections = moves;
    }

    public void run(){
        int idx = 0;
        while(idx < moveDirections.size()) {
            for(int i = 0; i < animals.size(); i++) {
                animals.get(i).move(moveDirections.get(idx));
                System.out.println("Zwierzę " + i + ": " + animals.get(i));
                idx++;
                if(idx == moveDirections.size()) {
                    break;
                }
            }
        }
    }
}
