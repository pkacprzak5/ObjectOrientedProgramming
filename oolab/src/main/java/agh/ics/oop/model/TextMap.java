package agh.ics.oop.model;

import javax.print.DocFlavor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TextMap implements WorldNumberPositionMap<String, Integer> {
    private List<String> napisy = new ArrayList<>();

    @Override
    public boolean place(String animal) {
        napisy.add(animal);
        return true;
    }

    @Override
    public void move(String animal, MoveDirection direction) {
        switch (direction) {
            case BACKWARD, LEFT ->{
                if(napisy.indexOf(animal) == 0) return;
                Collections.swap(napisy, napisy.indexOf(animal), napisy.indexOf(animal) - 1);
            }
            case FORWARD, RIGHT ->{
                if(napisy.indexOf(animal) == napisy.size()-1) return;
                Collections.swap(napisy, napisy.indexOf(animal), napisy.indexOf(animal) + 1);
            }
        }
    }

    @Override
    public boolean isOccupied(Integer position) {
        return napisy.size() > position && position >= 0;
    }

    @Override
    public String objectAt(Integer position) {
        if (isOccupied(position)) {
            return napisy.get(position);
        }
        return null;
    }

    @Override
    public boolean canMoveTo(Integer position) {
        return isOccupied(position) && position >= 0;
    }

    @Override
    public String toString() {
        return "TextMap{" +
                "napisy=" + napisy +
                '}';
    }
}
