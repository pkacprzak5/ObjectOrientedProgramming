package agh.ics.oop.model;

import java.util.Vector;

public interface WorldElement {
    public Vector2d getPosition();

    @Override
    public String toString();
}
