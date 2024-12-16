package agh.ics.oop.model;

public class Animal implements WorldElement {
    private MapDirection direction;       // Aktualny kierunek zwierzęcia
    private Vector2d position;            // Aktualna pozycja zwierzęcia
    private final AnimalInformation info; // Informacje o zwierzęciu (zawierające geny)
    private int currentGeneIndex = 0;     // Indeks aktualnie używanego genu

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

    public void move() {
        int[] genotype = info.genotype();
        int gene = genotype[currentGeneIndex];
        direction = direction.next(gene);
        position = position.add(direction.toUnitVector());
        currentGeneIndex = (currentGeneIndex + 1) % genotype.length;
    }
}
