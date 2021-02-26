package Constructor;

import javafx.animation.AnimationTimer;
import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.util.ArrayList;

public abstract class SpriteAnimation extends AnimationTimer {
    private final ArrayList<Sprite> spriteList;
    private Sprite currentSprite;
    private final int delay;
    private final int count;

    private int lastIndex;

    public SpriteAnimation(
            ArrayList<Sprite> spriteList,
            int count,
            int delay) {
        this.spriteList = spriteList;
        this.count      = count;
        this.delay      = delay;
    }
}
