package agh.ics.oop.model;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FileMapDisplay implements MapChangeListener {
    @Override
    public void mapChanged(WorldMap worldMap) {
        // Identyfikator mapy (możesz go zmienić, jeśli id mapy pochodzi z innego źródła)
        String mapId = worldMap.getId().toString();
        String fileName = mapId + ".log";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            // Zapisz czas zdarzenia
            String timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            writer.write("[" + timeStamp + "] Map changed:\n");

            // Zapisz pozycje obiektów na mapie
            for (int x = 0; x < worldMap.getWidth(); x++) {
                for (int y = 0; y < worldMap.getHeight(); y++) {
                    Vector2d pos = new Vector2d(x, y);
                    if (worldMap.isOccupied(pos)) {
                        writer.write("Position " + pos + ": " + worldMap.objectAt(pos).toString() + "\n");
                    }
                }
            }

            writer.write("-----\n"); // Separator dla czytelności
        } catch (IOException e) {
            System.err.println("Error writing to file " + fileName + ": " + e.getMessage());
        }
    }
}
