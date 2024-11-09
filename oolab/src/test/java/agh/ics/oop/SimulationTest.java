package agh.ics.oop;

import agh.ics.oop.model.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SimulationTest {
    private final List<Vector2d> positions =
            List.of(new Vector2d(2, 2),
                    new Vector2d(2, 1),
                    new Vector2d(1, 2)
                    );
    private final RectangularMap worldMap = new RectangularMap(5, 5);

    @Test
    void leftOrient() {
        String[] args = new String[]{"l", "l", "l"};
        List<MoveDirection> moves = OptionsParser.DirectionParser(args);
        Simulation simulation = new Simulation(positions, moves,worldMap);
        simulation.run();

        assertEquals(MapDirection.WEST, simulation.getAnimals().get(0).getDirection());
        assertEquals(MapDirection.WEST, simulation.getAnimals().get(1).getDirection());
        assertEquals(MapDirection.WEST, simulation.getAnimals().get(2).getDirection());
    }

    @Test
    void rightOrient() {
        String[] args = new String[]{"r", "r", "r"};
        List<MoveDirection> moves = OptionsParser.DirectionParser(args);
        Simulation simulation = new Simulation(positions, moves,worldMap);
        simulation.run();

        assertEquals(MapDirection.EAST, simulation.getAnimals().get(0).getDirection());
        assertEquals(MapDirection.EAST, simulation.getAnimals().get(1).getDirection());
        assertEquals(MapDirection.EAST, simulation.getAnimals().get(2).getDirection());
    }

    @Test
    void backOrient() {
        String[] args = new String[]{"r", "r", "r", "r", "r", "r"};
        List<MoveDirection> moves = OptionsParser.DirectionParser(args);
        Simulation simulation = new Simulation(positions, moves, worldMap);
        simulation.run();

        assertEquals(MapDirection.SOUTH, simulation.getAnimals().get(0).getDirection());
        assertEquals(MapDirection.SOUTH, simulation.getAnimals().get(1).getDirection());
        assertEquals(MapDirection.SOUTH, simulation.getAnimals().get(2).getDirection());
    }

    @Test
    void orient(){
        String[] args = new String[]{"r", "r", "r", "l", "l", "l", "b", "b", "b", "r", "r", "r"};
        List<MoveDirection> moves = OptionsParser.DirectionParser(args);
        Simulation simulation = new Simulation(positions, moves, worldMap);
        simulation.run();

        assertEquals(MapDirection.EAST, simulation.getAnimals().get(0).getDirection());
        assertEquals(MapDirection.EAST, simulation.getAnimals().get(1).getDirection());
        assertEquals(MapDirection.EAST, simulation.getAnimals().get(2).getDirection());
    }

    @Test
    void moveUp(){
        String[] args = new String[]{"f", "f", "f"};
        List<MoveDirection> moves = OptionsParser.DirectionParser(args);
        Simulation simulation = new Simulation(positions, moves, worldMap);
        simulation.run();

        assertEquals(new Vector2d(2, 3), simulation.getAnimals().get(0).getPosition());
        assertEquals(new Vector2d(2, 2), simulation.getAnimals().get(1).getPosition());
        assertEquals(new Vector2d(1, 3), simulation.getAnimals().get(2).getPosition());
    }

    @Test
    void moveDown(){
        String[] args = new String[]{"b", "b", "b"};
        List<MoveDirection> moves = OptionsParser.DirectionParser(args);
        Simulation simulation = new Simulation(positions, moves, worldMap);
        simulation.run();

        assertEquals(new Vector2d(2, 1), simulation.getAnimals().get(0).getPosition());
        assertEquals(new Vector2d(2, 0), simulation.getAnimals().get(1).getPosition());
        assertEquals(new Vector2d(1, 1), simulation.getAnimals().get(2).getPosition());
    }

    @Test
    void moveLeft(){
        String[] args = new String[]{"l", "l", "l", "f", "f", "f"};
        List<MoveDirection> moves = OptionsParser.DirectionParser(args);
        Simulation simulation = new Simulation(positions, moves, worldMap);
        simulation.run();

        assertEquals(new Vector2d(1, 2), simulation.getAnimals().get(0).getPosition());
        assertEquals(new Vector2d(1, 1), simulation.getAnimals().get(1).getPosition());
        assertEquals(new Vector2d(0, 2), simulation.getAnimals().get(2).getPosition());
    }

    @Test
    void moveRight(){
        String[] args = new String[]{"r", "r", "r", "f", "f", "f"};
        List<MoveDirection> moves = OptionsParser.DirectionParser(args);
        Simulation simulation = new Simulation(positions, moves, worldMap);
        simulation.run();

        assertEquals(new Vector2d(3, 2), simulation.getAnimals().get(0).getPosition());
        assertEquals(new Vector2d(3, 1), simulation.getAnimals().get(1).getPosition());
        assertEquals(new Vector2d(2, 2), simulation.getAnimals().get(2).getPosition());
    }

    @Test
    void move(){
        String[] args = new String[]{"f", "f", "f", "r", "r", "r",
                "f", "f", "f", "l", "l", "l", "f", "f", "f"};
        List<MoveDirection> moves = OptionsParser.DirectionParser(args);
        Simulation simulation = new Simulation(positions, moves, worldMap);
        simulation.run();

        assertEquals(new Vector2d(3, 4), simulation.getAnimals().get(0).getPosition());
        assertEquals(new Vector2d(3, 3), simulation.getAnimals().get(1).getPosition());
        assertEquals(new Vector2d(2, 4), simulation.getAnimals().get(2).getPosition());
    }

    @Test
    void forwardAndBack(){
        String[] args = new String[]{"f", "f", "f", "b", "b", "b"};
        List<MoveDirection> moves = OptionsParser.DirectionParser(args);
        Simulation simulation = new Simulation(positions, moves, worldMap);
        simulation.run();

        assertEquals(new Vector2d(2, 3), simulation.getAnimals().get(0).getPosition());
        assertEquals(new Vector2d(2, 1), simulation.getAnimals().get(1).getPosition());
        assertEquals(new Vector2d(1, 2), simulation.getAnimals().get(2).getPosition());
    }

    @Test
    void upperBound(){
        String[] args = new String[]{
                "f", "f", "f", "f", "f", "f",
                "f", "f", "f", "f", "f", "f",
                "f", "f", "f", "f", "f", "f",
                "f", "f", "f", "f", "f", "f",
                "f", "f", "f", "f", "f", "f",
                "f", "f", "f", "f", "f", "f",
        };
        List<MoveDirection> moves = OptionsParser.DirectionParser(args);
        Simulation simulation = new Simulation(positions, moves, worldMap);
        simulation.run();

        assertEquals(new Vector2d(2, 4), simulation.getAnimals().get(0).getPosition());
        assertEquals(new Vector2d(2, 3), simulation.getAnimals().get(1).getPosition());
        assertEquals(new Vector2d(1, 4), simulation.getAnimals().get(2).getPosition());
    }

    @Test
    void rightBound(){
        String[] args = new String[]{
                "r", "r", "r",
                "f", "f", "f", "f", "f", "f",
                "f", "f", "f", "f", "f", "f",
                "f", "f", "f", "f", "f", "f",
                "f", "f", "f", "f", "f", "f",
                "f", "f", "f", "f", "f", "f",
                "f", "f", "f", "f", "f", "f",
        };
        List<MoveDirection> moves = OptionsParser.DirectionParser(args);
        Simulation simulation = new Simulation(positions, moves, worldMap);
        simulation.run();

        assertEquals(new Vector2d(4, 2), simulation.getAnimals().get(0).getPosition());
        assertEquals(new Vector2d(4, 1), simulation.getAnimals().get(1).getPosition());
        assertEquals(new Vector2d(3, 2), simulation.getAnimals().get(2).getPosition());
    }

    @Test
    void leftBound(){
        String[] args = new String[]{
                "l", "l", "l",
                "f", "f", "f", "f", "f", "f",
                "f", "f", "f", "f", "f", "f",
                "f", "f", "f", "f", "f", "f",
                "f", "f", "f", "f", "f", "f",
                "f", "f", "f", "f", "f", "f",
                "f", "f", "f", "f", "f", "f",
        };
        List<MoveDirection> moves = OptionsParser.DirectionParser(args);
        Simulation simulation = new Simulation(positions, moves, worldMap);
        simulation.run();

        assertEquals(new Vector2d(1, 2), simulation.getAnimals().get(0).getPosition());
        assertEquals(new Vector2d(0, 1), simulation.getAnimals().get(1).getPosition());
        assertEquals(new Vector2d(0, 2), simulation.getAnimals().get(2).getPosition());
    }

    @Test
    void lowerBound(){
        String[] args = new String[]{
                "b", "b", "b", "b", "b", "b",
                "b", "b", "b", "b", "b", "b",
                "b", "b", "b", "b", "b", "b",
                "b", "b", "b", "b", "b", "b",
                "b", "b", "b", "b", "b", "b",
                "b", "b", "b", "b", "b", "b",
        };
        List<MoveDirection> moves = OptionsParser.DirectionParser(args);
        Simulation simulation = new Simulation(positions, moves, worldMap);
        simulation.run();

        assertEquals(new Vector2d(2, 1), simulation.getAnimals().get(0).getPosition());
        assertEquals(new Vector2d(2, 0), simulation.getAnimals().get(1).getPosition());
        assertEquals(new Vector2d(1, 0), simulation.getAnimals().get(2).getPosition());
    }

    @Test
    void directions(){
        String[] args = new String[]{"r", "l", "f", "f", "f", "f"};
        List<MoveDirection> moves = OptionsParser.DirectionParser(args);
        Simulation simulation = new Simulation(positions, moves, worldMap);

        assertIterableEquals(moves, simulation.getMoveDirections());
    }
}