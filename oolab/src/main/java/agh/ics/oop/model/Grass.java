package agh.ics.oop.model;

public class Grass implements WorldElement{
    private final Vector2d position;
    private final int energyToGive;

    public Grass(Vector2d position, int energyToGive) {
        this.position = position;
        this.energyToGive = energyToGive;
    }

    public int getEnergyToGive() {
        return energyToGive;
    }

    @Override
    public Vector2d getPosition() {
        return position;
    }

    @Override
    public String toString(){
        return "R";
    }

    @Override
    public String getResourceName() {
        return "Grass.png";
    }
}
