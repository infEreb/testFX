package Constructor;

import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.util.Duration;

import java.util.ArrayList;

public class SpriteAnimation extends Transition {
    private final ArrayList<Sprite> spriteList;
    private Sprite currentSprite;
    private final int count;
    private final double duration; // millis

    private int lastIndex;

    public SpriteAnimation(
            ArrayList<Sprite> spriteList,
            int count,
            double duration) {
        this.spriteList = spriteList;
        this.count      = count;
        this.duration = duration;

        setCycleDuration(Duration.seconds(duration));
        setInterpolator(Interpolator.LINEAR);
        setCycleCount(INDEFINITE);
    }

    public void interpolate(double k) {
        System.out.println(k);
    }

}
