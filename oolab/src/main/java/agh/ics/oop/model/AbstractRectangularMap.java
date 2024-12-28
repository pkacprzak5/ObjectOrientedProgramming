package agh.ics.oop.model;

import java.util.*;

public abstract class AbstractRectangularMap implements WorldMap{
    protected int width;
    protected int height;
    protected Map<Vector2d, PriorityQueue<Animal>> animals = new HashMap<>();
    protected Map<Vector2d, Grass> grass = new HashMap<>();
    protected AnimalComparator animalComparator = new AnimalComparator();
    protected Multiplication multiplication;

    public AbstractRectangularMap(int width, int height, int energyForMultiplication) {
        this.width = width;
        this.height = height;
        this.multiplication = new Multiplication(energyForMultiplication);
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
                current.eat(grass.get(entry.getKey()).getEnergyToGive());
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
