package agh.ics.oop.model;

import java.util.List;
import java.util.UUID;


public interface WorldMap {

    /**
     * Place a animal on the map.
     *
     * @param animal The animal to place on the map.
     */
    void place(Animal animal);

    /**
     * Return true if given position on the map is occupied. Should not be
     * confused with canMove since there might be empty positions where the animal
     * cannot move.
     *
     * @param position Position to check.
     * @return True if the position is occupied.
     */
    boolean isOccupied(Vector2d position);


    /**
     * Return an animal at a given position.
     *
     * @param position The position of the animal.
     * @return animal or null if the position is not occupied.
     */

    WorldElement objectAt(Vector2d position);
//
//    List<WorldElement> getElements();

    int getHeight();

    int getWidth();

}