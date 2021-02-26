package Constructor;

import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.util.ArrayList;

public class SpriteAnimation extends Transition implements Animation {
    private final ArrayList<Sprite> spriteList;
    private final int count;

    private int lastIndex;

    public SpriteAnimation(
            ArrayList<Sprite> spriteList,
            Duration duration,
            int count) {
        this.spriteList = spriteList;
        this.count      = count;
        setCycleDuration(duration);
        setInterpolator(Interpolator.LINEAR);
    }

    protected void interpolate(double k) {

    }

    @Override
    public void render() {

    }
}
