package agh.ics.oop.model;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public void mapChanged(String message) {
        for (MapChangeListener observer : observers) {
            observer.mapChanged(this, message);
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

        oldAnimals.entrySet().stream()
                .flatMap(entry -> entry.getValue().stream()
                        .peek(animal -> animal.move(this))
                        .peek(animal -> {
                            if (!animal.getPosition().equals(entry.getKey())) {
                                mapChanged("Animal was moved from " + entry.getKey() + " to " + animal.getPosition());
                            } else {
                                mapChanged("Animal changed direction from " + animal.getDirection().next(4) + " to " + animal.getDirection());
                            }
                        }))
                .forEach(this::place);
    }

    public void feedAnimals(){
        animals.entrySet().stream()
                .filter(entry -> grass.containsKey(entry.getKey()))
                .forEach(entry -> {
                    Animal current = entry.getValue().poll();
                    if (current != null) {
                        current.eat(grass.get(entry.getKey()));
                        grass.remove(entry.getKey());
                        this.place(current);
                    }
                });
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
        grassGenerator.dailyGenerate().entrySet().stream()
                .filter(entry -> !grass.containsKey(entry.getKey()))
                .forEach(entry -> {
                    grass.put(entry.getKey(), entry.getValue());
                    mapChanged("Grass was added to " + entry.getKey());
                });
    }

    public HashMap<Vector2d, Grass> getGrass() {
        return (HashMap<Vector2d, Grass>) grass;
    }

    public void initializeGrass(Map<Vector2d, Grass> grass){
        this.grass = grass;
    }

    public void cleanDeadAnimals(){
        animals.replaceAll((key, oldQueue) -> oldQueue.stream()
                .filter(animal -> !animal.isDead(this))
                .collect(Collectors.toCollection(() -> new PriorityQueue<>(animalComparator))));
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

    public Optional<WorldElement> objectAt(Vector2d position) {
        if (animals.containsKey(position) && !animals.get(position).isEmpty()) {
            return Optional.ofNullable(animals.get(position).peek());
        }
        if (grass.containsKey(position)) {
            return Optional.ofNullable(grass.get(position));
        }
        return Optional.empty();
    }


    @Override
    public boolean isOccupied(Vector2d position) {
        if(animals.containsKey(position)){
            return !animals.get(position).isEmpty() || grass.containsKey(position);
        }
        return grass.containsKey(position);
    }

    @Override
    public List<Animal> getOrderedAnimals() {
        return animals.keySet().stream()
                .sorted(Comparator.comparing(Vector2d::getX)
                        .thenComparing(Vector2d::getY))
                .flatMap(key -> animals.get(key).stream())
                .toList();
    }

    public List<WorldElement> getElements() {
        return Stream.concat(
                        animals.values().stream()
                                .flatMap(PriorityQueue::stream),
                        grass.values().stream()
                )
                .toList();
    }
}
