package agh.ics.oop.model;

public class Fire implements WorldElement{

    private final Vector2d position;
    private int counter;

    public Fire(Vector2d position, int counter) {
        this.position = position;
        this.counter = counter;
    }

    @Override
    public Vector2d getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return "X";
    }

    public String getResourceName() {
        return "Fire.png";
    }

    public int getCounter() {
        return counter==0?1:counter;
    }

    public void decrementCounter() {
        counter--;
    }
}
