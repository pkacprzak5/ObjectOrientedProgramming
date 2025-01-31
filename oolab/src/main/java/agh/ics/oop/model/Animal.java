package agh.ics.oop.model;

public class Animal implements WorldElement {
    private MapDirection direction;       // Aktualny kierunek zwierzęcia
    private Vector2d position;            // Aktualna pozycja zwierzęcia
    private final AnimalInformation info; // Informacje o zwierzęciu (zawierające geny)
    private int currentGeneIndex = 0;   // Indeks aktualnie używanego genu

    @Override
    public Vector2d getPosition() {
        return position;
    }

    public MapDirection getDirection() {
        return direction;
    }

    public Animal(Vector2d position, AnimalInformation info) {
        this.direction = MapDirection.NORTH; // Początkowy kierunek
        this.position = position;            // Początkowa pozycja
        this.info = info;                    // Informacje o zwierzęciu (geny)
    }

    @Override
    public String toString() {
        return switch (direction) {
            case EAST -> "E";
            case NORTH -> "N";
            case SOUTH -> "S";
            case WEST -> "W";
            case NORTHWEST -> "NW";
            case NORTHEAST -> "NE";
            case SOUTHEAST -> "SE";
            case SOUTHWEST -> "SW";
        };
    }

    public boolean isAt(Vector2d position) {
        return this.position.equals(position);
    }

    public String getCurentGen(){
        return this.info.getGenotype().get((currentGeneIndex + 1) % info.getGenotype().size()).toString();
    }

    public void move(AbstractRectangularMap map) {
        info.increaseTimeAlive();

        int gene = info.getGenotype().get(currentGeneIndex);
        direction = direction.next(gene);
        position = position.add(direction.toUnitVector()).modulo(map.getWidth());
        if(position.getY() >= map.getHeight() || position.getY() < 0) {
            direction = direction.next(4);
            position = position.add(direction.toUnitVector()).modulo(map.getWidth());
        }
        currentGeneIndex = (currentGeneIndex + 1) % info.getGenotype().size();

        info.decreaseEnergy(map.getEnergyToMove());
    }

    public boolean isDead(AbstractRectangularMap map) {
        if (info.getEnergy() <= 0){
            info.setTimeOfDeath(map.getCurrentTime());
            return true;
        }
        return false;
    }

    public void eat(Grass grass) {
        info.addEnergy(grass.getEnergyToGive());
        info.increaseGrassEaten();
    }

    public AnimalInformation getInfo() {
        return info;
    }


    @Override
    public String getResourceName() {
        return switch (direction) {
            case EAST -> "Pig_E.png";
            case NORTH -> "Pig_N.png";
            case SOUTH -> "Pig_S.png";
            case WEST -> "Pig_W.png";
            case NORTHWEST -> "Pig_NW.png";
            case NORTHEAST -> "Pig_NE.png";
            case SOUTHEAST -> "Pig_SE.png";
            case SOUTHWEST -> "Pig_SW.png";
        };
    }
}
