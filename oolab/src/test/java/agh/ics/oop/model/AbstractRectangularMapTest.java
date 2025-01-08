package agh.ics.oop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class AbstractRectangularMapTest {

    private AbstractRectangularMap map;
    private final Vector2d testPosition= new Vector2d(5, 5);
    private final AnimalInformation testInfo = new AnimalInformation(0,30,4);
    private final AnimalInformation testInfo2 = new AnimalInformation(0,20,4);
    private final AnimalInformation testInfo3 = new AnimalInformation(3,10,4);
    private final Animal testAnimal = new Animal(testPosition, testInfo);
    private final Animal testAnimal2 = new Animal(testPosition, testInfo2);
    private final Animal testAnimal3 = new Animal(testPosition, testInfo3);
    private final Animal[] listAnimal = new Animal[] {testAnimal, testAnimal2, testAnimal3};

    @BeforeEach
    void setUp() {
        GrassGenerator grassGenerator = new GrassGenerator(10,10,0,10,10); // Mock grass generator
        Multiplication multiplication = new Multiplication(30,2,2, 10); // Mock multiplication logic
        map = new RectangularMap(10, 10, 5, multiplication, grassGenerator); // Test implementation
    }

    @Test
    void place() {
        map.place(testAnimal);
        assertEquals(testAnimal, map.getAnimals().get(testPosition).peek());
        map.place(testAnimal2);
        map.place(testAnimal3);

        int index = 0; // Indeks dla porównywania z listAnimal
        for (Animal animal : map.getAnimals().get(testPosition)) {
            assertEquals(listAnimal[index], animal);
            index++;
        }
    }

    @Test
    void moveAnimals()
    {
        map.place(testAnimal);
        map.place(testAnimal2);
        map.place(testAnimal3);

        assertTrue(map.getAnimals().containsKey(testPosition));
        assertEquals(3, map.getAnimals().get(testPosition).size());


        map.moveAnimals();
        for (Animal animal : listAnimal) {
            assertNotEquals(testPosition, animal.getPosition(), "Zwierzę nie zmieniło pozycji po ruchu.");
        }
        for (Animal animal : listAnimal) {
            Vector2d newPosition = animal.getPosition();
            assertTrue(newPosition.follows(new Vector2d(4, 4)), "Zwierzę wyszło poza dolne granice mapy.");
            assertTrue(newPosition.precedes(new Vector2d(6, 6)), "Zwierzę wyszło poza górne granice mapy.");
        }
        assertEquals(25, testAnimal.getInfo().getEnergy());
        assertEquals(15, testAnimal2.getInfo().getEnergy());
        assertEquals(5, testAnimal3.getInfo().getEnergy());
        assertNull(map.getAnimals().get(testPosition), "Stara pozycja powinna być pusta.");
    }

    @Test
    void feedAnimals() {
        map.place(testAnimal);
        map.place(testAnimal2);
        map.place(testAnimal3);
        map.growGrass();
        map.growGrass();
        map.growGrass();
        map.growGrass();
        map.growGrass();
        map.growGrass();
        map.growGrass();
        map.feedAnimals();
        assertEquals(40, testAnimal.getInfo().getEnergy());
        assertEquals(20, testAnimal2.getInfo().getEnergy());
        assertEquals(10, testAnimal3.getInfo().getEnergy());
    }

    @Test
    void multiplyAnimals() {
        map.place(testAnimal);
        map.place(testAnimal2);
        map.place(testAnimal3);

        map.multiplyAnimals();
        int count = map.getAnimals().get(testPosition).size();
        assertEquals(4, count, "Powinno być dokładnie 4 zwierzęta po rozmnażaniu."); // 3 oryginalne + 1 nowe

        assertEquals(15, testAnimal.getInfo().getEnergy(), "Energia pierwszego rodzica powinna zostać zmniejszona.");
        assertEquals(5, testAnimal2.getInfo().getEnergy(), "Energia drugiego rodzica powinna zostać zmniejszona.");
        assertEquals(10, testAnimal3.getInfo().getEnergy(), "Energia trzeciego zwierzęcia nie powinna się zmienić.");
    }

    @Test
    void growGrass() {
        // Pobranie początkowej liczby trawy na mapie
        int initialGrassCount = map.getGrass().size();

        // Wywołanie metody growGrass
        map.growGrass();

        // Pobranie nowej liczby trawy na mapie
        int newGrassCount = map.getGrass().size();

        // Sprawdzenie, czy liczba trawy wzrosła o oczekiwaną wartość
        assertTrue(initialGrassCount + 10 >= newGrassCount && newGrassCount >= initialGrassCount,
                "Liczba trawy po wzroście powinna być w zakresie pomiędzy początkową liczbą a maksymalnym limitem wzrostu.");


        // Sprawdzenie, czy trawa została dodana w unikalnych pozycjach
        Map<Vector2d, Grass> grassMap = map.getGrass();
        for (Map.Entry<Vector2d, Grass> entry : grassMap.entrySet()) {
            assertNotNull(entry.getValue(), "Każda pozycja w mapie trawy powinna zawierać obiekt trawy.");
        }
    }


    @Test
    void initializeGrass() {
        // Przygotowanie mapy trawy do inicjalizacji
        Map<Vector2d, Grass> initialGrassMap = new HashMap<>();
        initialGrassMap.put(new Vector2d(1, 1), new Grass(new Vector2d(1, 1), 10));
        initialGrassMap.put(new Vector2d(2, 3), new Grass(new Vector2d(2, 3), 10));
        initialGrassMap.put(new Vector2d(4, 5), new Grass(new Vector2d(4, 5), 10));

        map.initializeGrass(initialGrassMap);

        Map<Vector2d, Grass> mapGrass = map.getGrass();

        assertEquals(initialGrassMap.size(), mapGrass.size(), "Liczba trawy na mapie powinna być zgodna z wprowadzaną.");

        for (Map.Entry<Vector2d, Grass> entry : initialGrassMap.entrySet()) {
            assertTrue(mapGrass.containsKey(entry.getKey()),
                    "Mapa trawy powinna zawierać pozycję: " + entry.getKey());
            assertEquals(entry.getValue(), mapGrass.get(entry.getKey()),
                    "Element trawy na pozycji " + entry.getKey() + " powinien być poprawnie zainicjalizowany.");
        }
    }

    @Test
    void cleanDeadAnimals() {

        map.place(testAnimal3);

        map.moveAnimals();

        assertEquals(5, testAnimal3.getInfo().getEnergy(), "Energía testAnimal3 powinna wynosić 5 przed usunięciem.");
        assertFalse(map.getAnimals().get(testAnimal3.getPosition()).isEmpty(),
                "testAnimal3 powinno zostać na mapy.");

        map.moveAnimals();
        map.cleanDeadAnimals();

        assertEquals(0, testAnimal3.getInfo().getEnergy(), "Energía testAnimal3 powinna wynosić 0.");
        assertTrue(map.getAnimals().get(testAnimal3.getPosition()).isEmpty(),
                "testAnimal3 powinno zostać usunięte z mapy.");
    }


    @Test
    void objectAt() {
        map.place(testAnimal3);
        map.place(testAnimal2);
        map.place(testAnimal);
        assertEquals(testAnimal, map.objectAt(testAnimal.getPosition()).orElse(null));
        assertNull(map.objectAt(new Vector2d(1,1)).orElse(null));
        map.moveAnimals();
        assertEquals(testAnimal, map.objectAt(testAnimal.getPosition()).orElse(null));
    }

    @Test
    void isOccupied() {
        map.place(testAnimal3);
        assertTrue(map.isOccupied(testAnimal3.getPosition()));
        map.moveAnimals();
        assertFalse(map.isOccupied(new Vector2d(5, 5)));
        assertTrue(map.isOccupied(testAnimal3.getPosition()));
    }

    @Test
    void getOrderedAnimals() {
        Animal testAnimal1 = new Animal(new Vector2d(1, 2), testInfo);
        Animal testAnimal2 = new Animal(new Vector2d(1, 3), testInfo);
        Animal testAnimal3 = new Animal(new Vector2d(2, 2), testInfo);
        Animal testAnimal4 = new Animal(new Vector2d(3, 2), testInfo);

        map.place(testAnimal1);
        map.place(testAnimal2);
        map.place(testAnimal3);
        map.place(testAnimal4);

        List<Animal> animals = List.of(testAnimal1, testAnimal2, testAnimal3, testAnimal4);

        assertEquals(animals, map.getOrderedAnimals());
    }

    @Test
    void getElements(){
        map.place(testAnimal3);
        map.place(testAnimal2);
        map.place(testAnimal);
        Map<Vector2d, Grass> initialGrassMap = new HashMap<>();
        Grass grass1 = new Grass(new Vector2d(1, 1), 10);
        Grass grass2 = new Grass(new Vector2d(2, 3), 10);
        Grass grass3 = new Grass(new Vector2d(4, 5), 10);


        initialGrassMap.put(new Vector2d(1, 1), grass1);
        initialGrassMap.put(new Vector2d(2, 3), grass2);
        initialGrassMap.put(new Vector2d(4, 5), grass3);

        map.initializeGrass(initialGrassMap);

        List<WorldElement> elements = map.getElements();
        assertEquals(6, elements.size());
        assertTrue(elements.contains(testAnimal3));
        assertTrue(elements.contains(testAnimal2));
        assertTrue(elements.contains(testAnimal));
        assertTrue(elements.contains(grass1));
        assertTrue(elements.contains(grass2));
        assertTrue(elements.contains(grass3));
    }
}