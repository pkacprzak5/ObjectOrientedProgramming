package agh.ics.oop.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static java.lang.Math.max;

public class Multiplication {
    private final int childEnergy;
    private final int minParentEnergy;
    private final int minimumGenomeMutation;
    private final int maximumGenomeMutation;

    public Multiplication(int childEnergy, int minimumGenomeMutation, int maximumGenomeMutation, int minParentEnergy) {
        this.childEnergy = childEnergy;
        this.minParentEnergy = max(minParentEnergy, childEnergy/2 + 1);
        this.minimumGenomeMutation = minimumGenomeMutation;
        this.maximumGenomeMutation = maximumGenomeMutation;
    }

    public int getChildEnergy() {
        return childEnergy;
    }

    public Animal multiply(Animal a, Animal b) {
        if(this.minParentEnergy > a.getInfo().getEnergy() || this.minParentEnergy > b.getInfo().getEnergy()) {
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

        mutate(child);

        return child;
    }

    public void mutate(Animal a) {
        Random rand = new Random();
        int x = rand.nextInt(maximumGenomeMutation - minimumGenomeMutation + 1) + minimumGenomeMutation;
        List<Integer> range = new ArrayList<>();
        for (int i = 0; i < a.getInfo().getGenotype().size(); i++) {
            range.add(i);
        }
        Collections.shuffle(range);
        range = range.subList(0, x);
        List<Integer> genotype = a.getInfo().getGenotype();
        for(Integer i : range) {
            genotype.set(i, rand.nextInt(8));
        }
        a.getInfo().setGenotype(genotype);
    }

}
