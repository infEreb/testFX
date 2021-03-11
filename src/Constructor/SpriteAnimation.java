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
    private double timer;
    private final int count;
    private final double duration; // TODO: millis ??

    private int lastIndex;

    public SpriteAnimation(
            HashMap<Integer, ArrayList<Sprite>> spriteMap,
            double duration) {
        this.spriteMap  = spriteMap;
        this.count      = spriteMap.get(Constants.UP).size();
        this.duration   = duration;
        currentSprite = spriteMap.get(Constants.UP).get(0);
        timer = 0;

        setCycleDuration(Duration.seconds(this.duration));
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
        int index = (int)caclcSpriteIndex(k);
        System.out.println("Ingex: " + index);
        System.out.println("Diraction: " + diraction);
        currentSprite = spriteMap.get(this.getDiraction()).get(index);
    }

    public double caclcSpriteIndex(double k) {
        double e1 = 1.0/count; // +epsilon
        for(double e0 = 0; e0 < 1.0; e0+=1.0/count) { // -epsilon
            if(k >= e0 && k <= e1) {
                System.out.println("("+e0+", "+e1+") :: index= "+ e0*count);
                return e0 * count;
            }
            e1 += 1.0/count;

        }
        return -1;
    }

}
