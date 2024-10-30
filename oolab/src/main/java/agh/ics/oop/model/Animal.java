package agh.ics.oop.model;

public class Animal {
    private MapDirection direction;
    private Vector2d position;

    public Vector2d getPosition() {
        return position;
    }

    public MapDirection getDirection() {
        return direction;
    }

    public Animal() {
        direction = MapDirection.NORTH;
        position = new Vector2d(2, 2);
    }

    public Animal(Vector2d position) {
        direction = MapDirection.NORTH;
        this.position = position;
    }

    @Override
    public String toString() {
        return "Pozycja: " + position.toString() + ", kierunek: " + direction.toString();
    }

    public boolean isAt(Vector2d position) {
        return this.position.equals(position);
    }

    public void move(MoveDirection direction) {
        switch (direction) {
            case RIGHT -> this.direction = this.direction.next();
            case LEFT -> this.direction = this.direction.previous();
            case FORWARD -> this.position = this.position.add(this.direction.toUnitVector());
            case BACKWARD -> this.position = this.position.subtract(this.direction.toUnitVector());
        }
        this.position = this.position.upperRight(new Vector2d(0, 0));
        this.position = this.position.lowerLeft(new Vector2d(4, 4));
    }

}
