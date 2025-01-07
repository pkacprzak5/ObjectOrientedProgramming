package agh.ics.oop.model;

import java.util.Comparator;
import java.util.Random;

public class AnimalComparator implements java.util.Comparator<Animal> {
    private final Random random = new Random();

    @Override
    public int compare(Animal o1, Animal o2) {
        int comparison = Integer.compare(o2.getInfo().getEnergy(), o1.getInfo().getEnergy());
        if (comparison != 0) {
            return comparison;
        }

        comparison = Integer.compare(o2.getInfo().getTimeAlive(), o1.getInfo().getTimeAlive());
        if (comparison != 0) {
            return comparison;
        }

        comparison = Integer.compare(o2.getInfo().getChildrenNumber(), o1.getInfo().getChildrenNumber());
        if (comparison != 0) {
            return comparison;
        }

        return random.nextBoolean() ? -1 : 1;
    }
}