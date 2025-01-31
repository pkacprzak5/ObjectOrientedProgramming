package agh.ics.oop.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MultiplicationSlightCorrection extends Multiplication{
    public MultiplicationSlightCorrection(int childEnergy, int minimumGenomeMutation, int maximumGenomeMutation, int minParentEnergy) {
        super(childEnergy, minimumGenomeMutation, maximumGenomeMutation, minParentEnergy);
    }

    @Override
    public void mutate(Animal a){
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
            int nextGene = genotype.get(i);
            if (rand.nextDouble() < 0.5){
                nextGene += 1;
            }else{
                nextGene -= 1;
            }
            genotype.set(i, (nextGene+8)%8);
        }
        a.getInfo().setGenotype(genotype);
    }
}
