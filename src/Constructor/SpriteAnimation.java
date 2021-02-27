package Constructor;

import Engine.Constants;
import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;

public class SpriteAnimation extends Transition {
    private final HashMap<Integer, ArrayList<Sprite>> spriteMap; // <Direction, Sprites>
    private Sprite currentSprite;
    private int diraction;
    private double timer = 0;
    private final int count;
    private final double duration; // TODO: millis ??

    private int lastIndex;

    public SpriteAnimation(
            HashMap<Integer, ArrayList<Sprite>> spriteMap,
            int count,
            double duration) {
        this.spriteMap  = spriteMap;
        this.count      = spriteMap.get(Constants.UP).size();
        this.duration   = duration;
        currentSprite = spriteMap.get(Constants.UP).get(0);

        setCycleDuration(Duration.seconds(duration));
        setInterpolator(Interpolator.LINEAR);
        setCycleCount(INDEFINITE);
    }

    public int getDiraction() {
        return diraction;
    }
    public void setDiraction(int diraction) {
        this.diraction = diraction;
    }

    public Sprite getCurrentSprite() {
        return currentSprite;
    }

    public void interpolate(double k) { // 0.0 - 1.0
        //System.out.println(k);
        if (k > timer) {
            timer += 1.0/count;
            currentSprite = spriteMap.get(getDiraction()).get((int)(timer*count) - 1);
            System.out.println((int)(timer*count) - 1);
            if(timer > 0.99) timer = 0;
            //System.out.println(timer*count);
        }
    }

}
