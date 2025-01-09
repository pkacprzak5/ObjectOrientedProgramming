package agh.ics.oop.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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

    public void fireSpread(int day) {
        if (day % fireDayDelay == 0) {
            fireMap.putAll(fireSpread.generate(this.getGrass()));
        }
        fireSpread.Spread(this.getGrass(), fireMap);
    }

    public void fireClear() {
        Iterator<Map.Entry<Vector2d, Fire>> iterator = fireMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Vector2d, Fire> entry = iterator.next();
            Fire fire = entry.getValue();
            if (fire.getCounter() <= 0) {
                this.getGrass().remove(entry.getKey());
                iterator.remove();
            }
        }
    }
}
