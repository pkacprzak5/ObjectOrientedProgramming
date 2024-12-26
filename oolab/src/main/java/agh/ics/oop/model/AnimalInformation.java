package agh.ics.oop.model;

import java.util.*;

public class AnimalInformation {
    private final int generationNumber;
    private List<Integer> genotype;
    private int energy;
    private int grassEaten;
    private int timeAlive;
    private int timeOfDeath;
    private List<Animal> children = new ArrayList<>();
    private final UUID ID = UUID.randomUUID();

    public AnimalInformation(int generationNumber, int energy, int startGenotypeLength) {
        grassEaten = 0;
        timeAlive = 0;
        this.energy = energy;
        this.generationNumber = generationNumber;
        this.generateGenotype(startGenotypeLength);
    }

    private void generateGenotype(int startGenotypeLength){
        if (startGenotypeLength < 0){
            throw new IllegalArgumentException("Length of genotype cannot be negative");
        }
        Random rand = new Random();
        this.genotype = new ArrayList<>();
        for (int i = 0; i < startGenotypeLength; i++){
            this.genotype.add(rand.nextInt(8));
        }
    }

    public int getChildrenNumber(){
        return this.children.size();
    }

    public int getDescendantsNumber(){
        Set<UUID> descendants = new HashSet<>();
        Queue<Animal> queue = new LinkedList<>();
        for (Animal animal : this.children){
            queue.add(animal);
        }
        while (!queue.isEmpty()){
            Animal animal = queue.poll();
            for(Animal child : animal.getInfo().getChildren()){
                queue.add(child);
            }
            descendants.add(animal.getInfo().getID());
        }
        return descendants.size();
    }

    public UUID getID() {
        return ID;
    }

    public List<Animal> getChildren() {
        return children;
    }

    public int getGenerationNumber() {
        return generationNumber;
    }

    public List<Integer> getGenotype() {
        return genotype;
    }

    public int getEnergy() {
        return energy;
    }

    public int getGrassEaten() {
        return grassEaten;
    }

    public int getTimeAlive() {
        return timeAlive;
    }

    public int getTimeOfDeath() {
        return timeOfDeath;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public void increaseGrassEaten() {
        this.grassEaten += 1;
    }

    public void increaseTimeAlive() {
        this.timeAlive += 1;
    }

    public void setTimeOfDeath(int timeOfDeath) {
        this.timeOfDeath = timeOfDeath;
    }
    
    public void addChild(Animal animal){
        this.children.add(animal);
    }
}
