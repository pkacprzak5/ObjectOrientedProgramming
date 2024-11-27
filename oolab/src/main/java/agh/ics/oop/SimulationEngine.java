package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SimulationEngine {
    private final List<Simulation> simulations;
    private final List<Thread> threads = new ArrayList<>();
    private final ExecutorService executor = Executors.newFixedThreadPool(4);

    public SimulationEngine(List<Simulation> simulations) {
        this.simulations = simulations;
    }

    public void runSync(){
        for(Simulation simulation : simulations){
            try {
                simulation.run();
            }catch (IllegalArgumentException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
    }

    public void runAsync(){
        for(Simulation simulation : simulations){
            Thread thread = new Thread(simulation);
            threads.add(thread);
            thread.start();
        }
        awaitSimulationsEnd(false);
    }

    public void runAsyncInThreadPool(){
        for(Simulation simulation : simulations){
            executor.submit(simulation);
        }
        executor.shutdown();
        awaitSimulationsEnd(true);
    }

    public void awaitSimulationsEnd(boolean ifexecutor){
        if (!ifexecutor) {
            for (Thread thread : threads) {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    System.err.println("Simulation interrupted");
                    e.printStackTrace();
                }
            }
        }
        else {
            try {
                if (executor.awaitTermination(10, TimeUnit.SECONDS)) {
                    System.out.println("All simulations finished");
                } else {
                    System.err.println("Stopping unfinished simulations (>10s)");
                    executor.shutdownNow();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
