package agh.ics.oop.model;

import java.util.Comparator;

public class AnimalComparator implements Comparator<Animal> {
    @Override
    public int compare(Animal o1, Animal o2) {
        int comparison = Integer.compare(o1.getInfo().getEnergy(), o2.getInfo().getEnergy());
        if (comparison != 0) {
            return comparison;
        }

        comparison = Integer.compare(o1.getInfo().getTimeAlive(), o2.getInfo().getTimeAlive());
        if (comparison != 0) {
            return comparison;
        }

        return Integer.compare(o1.getInfo().getChildrenNumber(), o2.getInfo().getChildrenNumber());
    }
}
