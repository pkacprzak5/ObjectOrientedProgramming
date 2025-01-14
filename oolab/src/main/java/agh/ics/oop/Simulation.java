package agh.ics.oop;

import agh.ics.oop.model.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

public class Simulation {
    private final int animalsNumber;
    private final int animalStartEnergy;
    private final int genomeLength;
    private final int refreshTime;
    private final GrassGenerator grassGenerator;
    private final AbstractRectangularMap worldMap;
    private volatile boolean running = true;



    public Simulation(int animalsNumber, int animalStartEnergy, int genomeLength, int refreshTime, GrassGenerator grassGenerator, AbstractRectangularMap worldMap) {
        this.animalsNumber = animalsNumber;
        this.animalStartEnergy = animalStartEnergy;
        this.genomeLength = genomeLength;
        this.refreshTime = refreshTime;
        this.grassGenerator = grassGenerator;
        this.worldMap = worldMap;
        setupWorldMap();
    }

    private void setupWorldMap() {
        worldMap.initializeGrass(grassGenerator.startGenerate());
        AnimalGenerator generator = new AnimalGenerator(animalsNumber);
        List<Animal> animals = generator.generateAnimals(worldMap, animalStartEnergy, genomeLength);
        for (Animal animal : animals) {
            worldMap.place(animal);
        }
    }

    public void stop() {
        running = false; // Ustawienie flagi na false zatrzymuje symulację
    }


    public void run(MapChangeListener mapChangeListener) {
        worldMap.addObserver(mapChangeListener);
        worldMap.addObserver((worldMap, message) -> {
            if(!Objects.equals(message, "")){
                String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                System.out.println(timestamp + " " + message);
            }
        });

        running = true;

        while(running){
            worldMap.increaseCurrentTime();
            worldMap.cleanDeadAnimals();
            worldMap.moveAnimals();
            worldMap.feedAnimals();
            worldMap.multiplyAnimals();
            worldMap.growGrass();
            if (worldMap instanceof RectangularMapFire) {
                ((RectangularMapFire) worldMap).fireSpread();
                ((RectangularMapFire) worldMap).fireClear();
                ((RectangularMapFire) worldMap).killByFire();
            }
            worldMap.mapChanged("");
            try{
                Thread.sleep(refreshTime);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}
