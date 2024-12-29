package agh.ics.oop.model;

import java.util.*;

public abstract class AbstractRectangularMap implements WorldMap{
    protected final int width;
    protected final int height;
    protected final int energyToMove;
    protected int currentTime;
    protected Map<Vector2d, PriorityQueue<Animal>> animals = new HashMap<>();
    protected Map<Vector2d, Grass> grass = new HashMap<>();
    protected AnimalComparator animalComparator = new AnimalComparator();
    protected Multiplication multiplication;
    protected GrassGenerator grassGenerator;

    public AbstractRectangularMap(int width, int height, int energyToMove, Multiplication multiplication, GrassGenerator grassGenerator) {
        this.width = width;
        this.height = height;
        this.energyToMove = energyToMove;
        this.multiplication = multiplication;
        this.grassGenerator = grassGenerator;
        grass = grassGenerator.startGenerate();
        currentTime = 0;
    }

    @Override
    public void place(Animal animal){
        if(!animals.containsKey(animal.getPosition())){
            animals.put(animal.getPosition(), new PriorityQueue<>(animalComparator));
        }
        animals.get(animal.getPosition()).add(animal);
    }

    public void moveAnimals(){
        Map<Vector2d, PriorityQueue<Animal>> oldAnimals = animals;
        animals = new HashMap<>();
        for(Map.Entry<Vector2d, PriorityQueue<Animal>> entry : oldAnimals.entrySet()){
            for(Animal animal : entry.getValue()){
                animal.move(this);
                this.place(animal);
            }
        }
    }

    public void feedAnimals(){
        for(Map.Entry<Vector2d, PriorityQueue<Animal>> entry : animals.entrySet()){
            if(grass.containsKey(entry.getKey())){
                Animal current = entry.getValue().poll();
                assert current != null;
                current.eat(grass.get(entry.getKey()));
                grass.remove(entry.getKey());
                this.place(current);
            }
        }
    }

    public void multiplyAnimals(){
        for(Map.Entry<Vector2d, PriorityQueue<Animal>> entry : animals.entrySet()){
            List<Animal> newAnimals = new ArrayList<>();
            PriorityQueue<Animal> oldAnimals = entry.getValue();
            while(oldAnimals.size() >= 2){
                Animal parentA = oldAnimals.poll();
                Animal parentB = oldAnimals.poll();
                Animal newAnimal = multiplication.multiply(parentA, parentB);
                if (newAnimal == null) {
                    break;
                }
                newAnimals.add(newAnimal);
                oldAnimals.add(parentA);
                oldAnimals.add(parentB);
            }

            for(Animal animal : newAnimals){
                this.place(animal);
            }
        }
    }

    public void growGrass(){
        Map<Vector2d, Grass> newGrass = grassGenerator.dailyGenerate();
        for(Map.Entry<Vector2d, Grass> entry : newGrass.entrySet()){
            if (!grass.containsKey(entry.getKey())) {
                grass.put(entry.getKey(), entry.getValue());
            }
        }
    }

    public void initializeGrass(Map<Vector2d, Grass> grass){
        this.grass = grass;
    }

    public void cleanDeadAnimals(){
        for(Map.Entry<Vector2d, PriorityQueue<Animal>> entry : animals.entrySet()){
            PriorityQueue<Animal> newAnimals = new PriorityQueue<>(animalComparator);
            while (!entry.getValue().isEmpty()) {
                Animal animal = entry.getValue().poll();
                if(!animal.isDead(this)){
                    newAnimals.add(animal);
                }
            }
            entry.setValue(newAnimals);
        }
    }

    public int getCurrentTime() {
        return currentTime;
    }

    public void increaseCurrentTime() {
        this.currentTime += 1;
    }

    public int getEnergyToMove() {
        return energyToMove;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return animals.containsKey(position) || grass.containsKey(position);
    }
}
