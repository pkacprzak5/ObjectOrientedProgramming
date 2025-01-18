package agh.ics.oop.config;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Configuration {

    @JsonProperty
    private int mapWidth;
    @JsonProperty
    private int mapHeight;
    @JsonProperty
    private int initialPlants;
    @JsonProperty
    private int energyPerPlant;
    @JsonProperty
    private int plantsPerDay;
    @JsonProperty
    private int initialAnimals;
    @JsonProperty
    private int energyToMultiply;
    @JsonProperty
    private int initialEnergy;
    @JsonProperty
    private int energyToBreed;
    @JsonProperty
    private int minMutations;
    @JsonProperty
    private int maxMutations;
    @JsonProperty
    private int genomeLength;
    @JsonProperty
    private int energyToMove;
    @JsonProperty
    private int refreshTime;
    @JsonProperty
    private boolean fireEnabled;
    @JsonProperty
    private boolean mutationEnabled;
    @JsonProperty
    private int dayDelay;
    @JsonProperty
    private int dayAmount;

    public Configuration() {
    }

    public Configuration(int mapWidth, int mapHeight, int initialPlants, int energyPerPlant, int plantsPerDay, int initialAnimals, int energyToMultiply, int initialEnergy, int energyToBreed, int minMutations, int maxMutations, int genomeLength, int energyToMove, int refreshTime, boolean fireEnabled, boolean mutationEnabled, int dayDelay, int dayAmount) {
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        this.initialPlants = initialPlants;
        this.energyPerPlant = energyPerPlant;
        this.plantsPerDay = plantsPerDay;
        this.initialAnimals = initialAnimals;
        this.energyToMultiply = energyToMultiply;
        this.initialEnergy = initialEnergy;
        this.energyToBreed = energyToBreed;
        this.minMutations = minMutations;
        this.maxMutations = maxMutations;
        this.genomeLength = genomeLength;
        this.energyToMove = energyToMove;
        this.refreshTime = refreshTime;
        this.fireEnabled = fireEnabled;
        this.mutationEnabled = mutationEnabled;
        this.dayDelay = dayDelay;
        this.dayAmount = dayAmount;
    }

    public int getMapWidth() {
        return mapWidth;
    }

    public void setMapWidth(int mapWidth) {
        this.mapWidth = mapWidth;
    }

    public int getMapHeight() {
        return mapHeight;
    }

    public void setMapHeight(int mapHeight) {
        this.mapHeight = mapHeight;
    }

    public int getInitialPlants() {
        return initialPlants;
    }

    public void setInitialPlants(int initialPlants) {
        this.initialPlants = initialPlants;
    }

    public int getEnergyPerPlant() {
        return energyPerPlant;
    }

    public void setEnergyPerPlant(int energyPerPlant) {
        this.energyPerPlant = energyPerPlant;
    }

    public int getPlantsPerDay() {
        return plantsPerDay;
    }

    public void setPlantsPerDay(int plantsPerDay) {
        this.plantsPerDay = plantsPerDay;
    }

    public int getInitialAnimals() {
        return initialAnimals;
    }

    public void setInitialAnimals(int initialAnimals) {
        this.initialAnimals = initialAnimals;
    }

    public int getEnergyToMultiply() {
        return energyToMultiply;
    }

    public void setEnergyToMultiply(int energyToMultiply) {
        this.energyToMultiply = energyToMultiply;
    }

    public int getInitialEnergy() {
        return initialEnergy;
    }

    public void setInitialEnergy(int initialEnergy) {
        this.initialEnergy = initialEnergy;
    }

    public int getEnergyToBreed() {
        return energyToBreed;
    }

    public void setEnergyToBreed(int energyToBreed) {
        this.energyToBreed = energyToBreed;
    }

    public int getMinMutations() {
        return minMutations;
    }

    public void setMinMutations(int minMutations) {
        this.minMutations = minMutations;
    }

    public int getMaxMutations() {
        return maxMutations;
    }

    public void setMaxMutations(int maxMutations) {
        this.maxMutations = maxMutations;
    }

    public int getGenomeLength() {
        return genomeLength;
    }

    public void setGenomeLength(int genomeLength) {
        this.genomeLength = genomeLength;
    }

    public int getEnergyToMove() {
        return energyToMove;
    }

    public void setEnergyToMove(int energyToMove) {
        this.energyToMove = energyToMove;
    }

    public int getRefreshTime() {
        return refreshTime;
    }

    public void setRefreshTime(int refreshTime) {
        this.refreshTime = refreshTime;
    }

    public boolean isFireEnabled() {
        return fireEnabled;
    }

    public void setFireEnabled(boolean fireEnabled) {
        this.fireEnabled = fireEnabled;
    }

    public boolean isMutationEnabled() {
        return mutationEnabled;
    }

    public void setMutationEnabled(boolean mutationEnabled) {
        this.mutationEnabled = mutationEnabled;
    }

    public int getDayDelay() {
        return dayDelay;
    }

    public void setDayDelay(int dayDelay) {
        this.dayDelay = dayDelay;
    }

    public int getDayAmount() {
        return dayAmount;
    }

    public void setDayAmount(int dayAmount) {
        this.dayAmount = dayAmount;
    }
}
