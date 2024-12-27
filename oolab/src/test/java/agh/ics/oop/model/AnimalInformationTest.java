package agh.ics.oop.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

class AnimalInformationTest {

    @Test
    void testGetChildrenNumber() {
        AnimalInformation animalInfo = new AnimalInformation(1, 10, 5);
        assertEquals(0, animalInfo.getChildrenNumber());
        Animal child = new Animal(new Vector2d(0, 0), new AnimalInformation(2, 8, 5));
        animalInfo.addChild(child);
        assertEquals(1, animalInfo.getChildrenNumber());
    }

    @Test
    void testGetDescendantsNumber() {
        AnimalInformation parentInfo = new AnimalInformation(1, 10, 5);
        Animal child = new Animal(new Vector2d(1, 1), new AnimalInformation(2, 8, 5));
        Animal grandchild = new Animal(new Vector2d(2, 2), new AnimalInformation(3, 6, 5));
        Animal grandgrandchild = new Animal(new Vector2d(2, 2), new AnimalInformation(4, 6, 5));
        grandchild.getInfo().addChild(grandgrandchild);
        child.getInfo().addChild(grandchild);
        child.getInfo().addChild(grandgrandchild);
        parentInfo.addChild(child);

        assertEquals(3, parentInfo.getDescendantsNumber());
    }


    @Test
    void testGetID() {
        AnimalInformation animalInfo = new AnimalInformation(1, 10, 5);
        assertNotNull(animalInfo.getID());
    }

    @Test
    void testGetChildren() {
        AnimalInformation animalInfo = new AnimalInformation(1, 10, 5);
        assertTrue(animalInfo.getChildren().isEmpty());
        Animal child = new Animal(new Vector2d(0, 0), new AnimalInformation(2, 8, 5));
        animalInfo.addChild(child);
        assertEquals(1, animalInfo.getChildren().size());
        assertEquals(child, animalInfo.getChildren().getFirst());
    }

    @Test
    void testGetGenerationNumber() {
        AnimalInformation animalInfo = new AnimalInformation(1, 10, 5);
        assertEquals(1, animalInfo.getGenerationNumber());
    }

    @Test
    void testGetGenotype() {
        AnimalInformation animalInfo = new AnimalInformation(1, 10, 5);
        List<Integer> genotype = animalInfo.getGenotype();
        assertEquals(5, genotype.size());
        for (int gene : genotype) {
            assertTrue(gene >= 0 && gene < 8, "Genotype values must be between 0 and 7.");
        }
    }

    @Test
    void testSetGenotype() {
        AnimalInformation animalInfo = new AnimalInformation(1, 10, 5);
        ArrayList<Integer> newGenotype = new ArrayList<>(List.of(1, 2, 3, 4, 5));
        animalInfo.setGenotype(newGenotype);
        assertEquals(newGenotype, animalInfo.getGenotype());
    }

    @Test
    void testGetEnergy() {
        AnimalInformation animalInfo = new AnimalInformation(1, 10, 5);
        assertEquals(10, animalInfo.getEnergy());
    }

    @Test
    void testSetEnergy() {
        AnimalInformation animalInfo = new AnimalInformation(1, 10, 5);
        animalInfo.setEnergy(15);
        assertEquals(15, animalInfo.getEnergy());
    }

    @Test
    void testAddEnergy() {
        AnimalInformation animalInfo = new AnimalInformation(1, 10, 5);
        animalInfo.addEnergy(5);
        assertEquals(15, animalInfo.getEnergy());
    }

    @Test
    void testGetGrassEaten() {
        AnimalInformation animalInfo = new AnimalInformation(1, 10, 5);
        assertEquals(0, animalInfo.getGrassEaten());
    }

    @Test
    void testIncreaseGrassEaten() {
        AnimalInformation animalInfo = new AnimalInformation(1, 10, 5);
        animalInfo.increaseGrassEaten();
        assertEquals(1, animalInfo.getGrassEaten());
    }

    @Test
    void testGetTimeAlive() {
        AnimalInformation animalInfo = new AnimalInformation(1, 10, 5);
        assertEquals(0, animalInfo.getTimeAlive());
    }

    @Test
    void testIncreaseTimeAlive() {
        AnimalInformation animalInfo = new AnimalInformation(1, 10, 5);
        animalInfo.increaseTimeAlive();
        assertEquals(1, animalInfo.getTimeAlive());
    }


    @Test
    void testSetTimeOfDeath() {
        AnimalInformation animalInfo = new AnimalInformation(1, 10, 5);
        animalInfo.setTimeOfDeath(25);
        assertEquals(25, animalInfo.getTimeOfDeath());
    }


    @Test
    void testGenerateGenotypeInvalidLength() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new AnimalInformation(1, 10, -1)
        );
        assertEquals("Length of genotype cannot be negative", exception.getMessage());
    }
}
