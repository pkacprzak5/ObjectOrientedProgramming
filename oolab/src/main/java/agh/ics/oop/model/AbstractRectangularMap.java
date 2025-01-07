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
    protected List<MapChangeListener> observers = new ArrayList<>();

    public void addObserver(MapChangeListener observer) {
        observers.add(observer);
    }

    public void removeObserver(MapChangeListener observer) {
        observers.remove(observer);
    }

    public void mapChanged() {
        for (MapChangeListener observer : observers) {
            observer.mapChanged(this);
        }
    }

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

    public Map<Vector2d, PriorityQueue<Animal>> getAnimals() {
        return animals;
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
                oldAnimals.add(parentA);
                oldAnimals.add(parentB);
                if (newAnimal == null) {
                    break;
                }
                newAnimals.add(newAnimal);
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

    public HashMap<Vector2d, Grass> getGrass() {
        return (HashMap<Vector2d, Grass>) grass;
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

    public WorldElement objectAt(Vector2d position){
        if(animals.containsKey(position)){
            return animals.get(position).peek();
        }
        if(grass.containsKey(position)){
            return grass.get(position);
        }
        return null;
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        if(animals.containsKey(position)){
            return animals.get(position).size() > 0 || grass.containsKey(position);
        }
        return grass.containsKey(position);
    }
}
