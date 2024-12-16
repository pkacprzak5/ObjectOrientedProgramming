package agh.ics.oop.model;

public enum MapDirection {
    NORTH, EAST, SOUTH, WEST, NORTHEAST, SOUTHEAST, NORTHWEST, SOUTHWEST;

    public String toString() {
        return switch (this) {
            case NORTH -> "NORTH";
            case SOUTH -> "SOUTH";
            case EAST -> "EAST";
            case WEST -> "WEST";
            case NORTHEAST -> "NORTHEAST";
            case SOUTHEAST -> "SOUTHEAST";
            case NORTHWEST -> "NORTHWEST";
            case SOUTHWEST -> "SOUTHWEST";
        };
    }

    public MapDirection next(){
        return switch (this){
            case NORTH -> NORTHEAST;
            case NORTHEAST -> EAST;
            case EAST -> SOUTHEAST;
            case SOUTHEAST -> SOUTH;
            case SOUTH -> SOUTHWEST;
            case SOUTHWEST -> WEST;
            case WEST -> NORTHWEST;
            case NORTHWEST -> NORTH;
        };
    }

    public MapDirection next(int steps) {
        MapDirection result = this;
        for (int i = 0; i < steps; i++) {
            result = result.next();
        }
        return result;
    }

    public Vector2d toUnitVector(){
        return switch (this){
            case NORTH -> new Vector2d(0, 1);
            case EAST -> new Vector2d(1, 0);
            case SOUTH -> new Vector2d(0, -1);
            case WEST -> new Vector2d(-1, 0);
            case NORTHEAST -> new Vector2d(1, 1);
            case SOUTHEAST -> new Vector2d(1, -1);
            case NORTHWEST -> new Vector2d(-1, 1);
            case SOUTHWEST -> new Vector2d(-1, -1);
        };
    }

}
