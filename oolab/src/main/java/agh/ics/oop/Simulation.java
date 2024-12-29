package agh.ics.oop;

public class Simulation {
    private final int width;
    private final int height;
    private final int grassNumber;
    private final int energyFromGrass;
    private final int animalsNumber;
    private final int animalStartEnergy;
    private final int energyToMultiply;
    private final int minimumGenomeMutation;
    private final int maximumGenomeMutation;
    private final int genomeLength;

    public Simulation(int width, int height, int grassNumber, int energyFromGrass, int animalsNumber, int animalStartEnergy, int energyToMultiply, int minimumGenomeMutation, int maximumGenomeMutation, int genomeLength) {
        this.width = width;
        this.height = height;
        this.grassNumber = grassNumber;
        this.energyFromGrass = energyFromGrass;
        this.animalsNumber = animalsNumber;
        this.animalStartEnergy = animalStartEnergy;
        this.energyToMultiply = energyToMultiply;
        this.minimumGenomeMutation = minimumGenomeMutation;
        this.maximumGenomeMutation = maximumGenomeMutation;
        this.genomeLength = genomeLength;
    }
}
