package agh.ics.oop.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

public class RectangularMapFire extends AbstractRectangularMap {

    protected final int fireDayDelay;
    protected final int fireInterval;
    protected Map<Vector2d, Fire> fireMap = new HashMap<>();
    protected FireSpread fireSpread;

    public RectangularMapFire(int width, int height, int energyToMove, Multiplication multiplication, GrassGenerator grassGenerator, int fireDayDelay, int fireInterval, FireSpread fireSpread) {
        super(width, height, energyToMove, multiplication, grassGenerator);
        this.fireDayDelay = fireDayDelay;
        this.fireInterval = fireInterval;
        this.fireSpread = fireSpread;
    }

    public void fireSpread() {
        if (this.currentTime % fireDayDelay == 0) {
            fireMap.putAll(fireSpread.generate(this.grass));
        }
        fireSpread.Spread(this.grass, fireMap);
    }

    public void fireClear() {
        Iterator<Map.Entry<Vector2d, Fire>> iterator = fireMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Vector2d, Fire> entry = iterator.next();
            Fire fire = entry.getValue();
            if (fire.getCounter() <= 0) {
                this.grass.remove(entry.getKey());
                iterator.remove();
            }
        }
    }

    @Override
    public Optional<WorldElement> objectAt(Vector2d position) {
        if (fireMap.containsKey(position)) {
            return Optional.of(fireMap.get(position));
        }
        if (animals.containsKey(position) && !animals.get(position).isEmpty()) {
            return Optional.ofNullable(animals.get(position).peek());
        }
        if (grass.containsKey(position)) {
            return Optional.ofNullable(grass.get(position));
        }
        return Optional.empty();
    }

    public void killByFire() {
        for (Vector2d firePosition : fireMap.keySet()) {
            if (animals.containsKey(firePosition)) {
                animals.remove(firePosition);
            }
        }
    }

}
