package agh.ics.oop.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Multiplication {
    private int childEnergy;

    public Multiplication(int childEnergy) {
        this.childEnergy = childEnergy;
    }

    public int getChildEnergy() {
        return childEnergy;
    }

    public Animal multiply(Animal a, Animal b) {
        if(this.childEnergy > a.getInfo().getEnergy() || this.childEnergy > b.getInfo().getEnergy()) {
//            throw new IllegalArgumentException("One of animals has not enough energy");
            return null;
        }
        int proportion = (a.getInfo().getEnergy() / (b.getInfo().getEnergy() + a.getInfo().getEnergy()))
                * a.getInfo().getGenotype().size();
        Random rand = new Random();
        List<Integer> newGenotype = new ArrayList<>();
        if(rand.nextBoolean()) {
            newGenotype.addAll(a.getInfo().getGenotype().subList(0, proportion));
            newGenotype.addAll(b.getInfo().getGenotype()
                            .subList(proportion, b.getInfo().getGenotype().size()));
        }else{
            newGenotype.addAll(b.getInfo().getGenotype().subList(0, proportion));
            newGenotype.addAll(a.getInfo().getGenotype()
                            .subList(proportion, a.getInfo().getGenotype().size()));
        }
        AnimalInformation information = new AnimalInformation(a.getInfo().getGenerationNumber() + 1,
                childEnergy, a.getInfo().getGenotype().size());
        information.setGenotype(newGenotype);
        int decreaseInEnergy = childEnergy / 2;

        a.getInfo().setEnergy(a.getInfo().getEnergy() - decreaseInEnergy);
        b.getInfo().setEnergy(b.getInfo().getEnergy() - (childEnergy - decreaseInEnergy));

        Animal child = new Animal(a.getPosition(), information);
        a.getInfo().addChild(child);
        b.getInfo().addChild(child);

        return child;
    }
}
