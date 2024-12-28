package agh.ics.oop.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MultiplicationTest {

    // Test to check if the child energy is correctly returned
    @Test
    void getChildEnergy() {
        Multiplication multiplication = new Multiplication(10);
        assertEquals(10, multiplication.getChildEnergy());
    }

    // Test the multiply method
    @Test
    void multiply() {

        AnimalInformation info1 = new AnimalInformation(1, 30, 5); // 30 energy, genotype size 5
        AnimalInformation info2 = new AnimalInformation(1, 40, 5); // 40 energy, genotype size 5
        info1.setGenotype(new ArrayList<>(List.of(0, 1, 2, 3, 4)));
        info1.setGenotype(new ArrayList<>(List.of(4, 3, 2, 1, 0)));
        Animal parent1 = new Animal(new Vector2d(1, 1), info1);
        Animal parent2 = new Animal(new Vector2d(1, 1), info2);

        // Create a Multiplication instance with child energy
        Multiplication multiplication = new Multiplication(10);

        // Perform multiplication
        Animal child = multiplication.multiply(parent1, parent2);

        // Ensure the child is not null
        assertNotNull(child);

        // Check that the child's energy is set correctly (childEnergy is 10)
        assertEquals(10, child.getInfo().getEnergy());

        // Check that the energy of the parents has been reduced correctly
        assertEquals(25, parent1.getInfo().getEnergy());  // Parent 1 energy should decrease by 10 (childEnergy / 2)
        assertEquals(35, parent2.getInfo().getEnergy());  // Parent 2 energy should decrease by (childEnergy - decreaseInEnergy)

        // Ensure the parents now have children
        assertTrue(parent1.getInfo().getChildren().contains(child));
        assertTrue(parent2.getInfo().getChildren().contains(child));

        // Check that the child has a valid genotype
        assertEquals(5, child.getInfo().getGenotype().size());  // The child's genotype size should be the same as parents

    }

    @Test
    void multiplyNotEnoughEnergy() {
        AnimalInformation info1 = new AnimalInformation(1, 5, 5);  // Insufficient energy (5 < 10)
        AnimalInformation info2 = new AnimalInformation(1, 20, 5);

        Animal parent1 = new Animal(new Vector2d(1, 1), info1);
        Animal parent2 = new Animal(new Vector2d(2, 2), info2);

        // Create a Multiplication instance with child energy
        Multiplication multiplication = new Multiplication(20);

        // Test that an exception is thrown due to insufficient energy in one of the parents
        assertNull(multiplication.multiply(parent1, parent2));
    }
}
