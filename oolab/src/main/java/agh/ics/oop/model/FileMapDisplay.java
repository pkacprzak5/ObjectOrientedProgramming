package agh.ics.oop.model;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FileMapDisplay implements MapChangeListener {

    @Override
    public void mapChanged(WorldMap worldMap,String string) {
        String mapId = worldMap.getId().toString();
        String fileName = mapId + ".log";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            String timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            writer.write("[" + timeStamp + "] Map changed:\n");

            for (int x = 0; x < worldMap.getWidth(); x++) {
                for (int y = 0; y < worldMap.getHeight(); y++) {
                    Vector2d pos = new Vector2d(x, y);
                    if (worldMap.isOccupied(pos)) {
                        writer.write("Position " + pos + ": " + worldMap.objectAt(pos).toString() + "\n");
                    }
                }
            }

            writer.write("-----\n");
        } catch (IOException e) {
            System.err.println("Error writing to file " + fileName + ": " + e.getMessage());
        }
    }
}
