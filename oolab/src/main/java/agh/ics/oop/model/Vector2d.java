package agh.ics.oop.model;

public class Vector2d {
    private final int x;
    private final int y;

    public Vector2d(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public String toString() {
        return "(" + x + "," + y + ")";
    }

    public boolean precedes(Vector2d vector) {
        return x <= vector.getX() && y <= vector.getY();
    }

    public boolean follows(Vector2d vector) {
        return x >= vector.getX() && y >= vector.getY();
    }

    public Vector2d add(Vector2d vector) {
        return new Vector2d(x + vector.getX(), y + vector.getY());
    }

    public Vector2d subtract(Vector2d vector) {
        return new Vector2d(x - vector.getX(), y - vector.getY());
    }

    public Vector2d upperRight(Vector2d vector) {
        return new Vector2d(Math.max(x, vector.getX()), Math.max(y, vector.getY()));
    }

    public Vector2d lowerLeft(Vector2d vector) {
        return new Vector2d(Math.min(x, vector.getX()), Math.max(y, vector.getY()));
    }

    public Vector2d opposite(Vector2d vector) {
        return new Vector2d(-vector.getX(), -vector.getY());
    }

    public boolean equals(Vector2d vector) {
        return x == vector.getX() && y == vector.getY();
    }


}
