package agh.ics.oop.model;

import java.util.Random;

public record AnimalInformation(
        int generationNumber,
        int[] genotype,
        int energy,
        int grassCount,
        int childrenCount,
        int allDescendantsCount,
        int timeAlive,
        int timeOfDeath
) {
    public AnimalInformation(int generationNumber, int n, int energy, int grassCount,
                             int childrenCount, int allDescendantsCount, int timeAlive, int timeOfDeath) {
        this(generationNumber, generateRandomGenotype(n), energy, grassCount,
                childrenCount, allDescendantsCount, timeAlive, timeOfDeath);
    }
    private static int[] generateRandomGenotype(int n) {
        Random random = new Random();
        int[] genotype = new int[n];
        for (int i = 0; i < n; i++) {
            genotype[i] = random.nextInt(8);
        }
        return genotype;
    }

    public AnimalInformation {
        if (genotype == null || !isValidGenotype(genotype)) {
            throw new IllegalArgumentException("Genotype must be an array of integers between 0 and 7.");
        }
    }

    private static boolean isValidGenotype(int[] genotype) {
        for (int gene : genotype) {
            if (gene < 0 || gene > 7) {
                return false;
            }
        }
        return true;
    }

    public static AnimalInformation createDefault() {
        return new AnimalInformation(0, 8, 0, 0, 0, 0, 0, -1);
    }
}
