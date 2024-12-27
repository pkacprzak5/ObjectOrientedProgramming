package agh.ics.oop.model;

import java.util.Objects;

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
        return new Vector2d(Math.min(x, vector.getX()), Math.min(y, vector.getY()));
    }

    public Vector2d modulo(int width) {
        return new Vector2d(x % width, y);
    }

    public Vector2d opposite() {
        return new Vector2d(-x, -y);
    }

    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (!(other instanceof Vector2d))
            return false;
        Vector2d that = (Vector2d) other;
        return x == that.getX() && y == that.getY();
    }

    public int hashCode() {
        return Objects.hash(x, y);
    }
}
