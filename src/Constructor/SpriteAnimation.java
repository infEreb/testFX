package Constructor;

import Engine.Constants;
import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.HashMap;

public class SpriteAnimation extends Transition {
    private final HashMap<Integer, ArrayList<Sprite>> spriteMap; // <Direction, Sprites>
    private final ArrayList<Sprite> sprites;
    private Sprite currentSprite;
    private int currentSpriteIndex;
    private int direction;
    private double timer;
    private final int count;
    private final double duration;
    public double k;

    public SpriteAnimation(
            HashMap<Integer, ArrayList<Sprite>> spriteMap,
            double duration) {
        this.spriteMap  = spriteMap;
        this.count      = spriteMap.get(Constants.UP).size();
        this.duration   = duration;
        this.currentSpriteIndex = 0;
        /*try {
            currentSprite = spriteMap.get(Constants.UP).get(currentSpriteIndex).clone();
        }catch (Exception ex){
            currentSprite = null;
        }*/
        currentSprite = spriteMap.get(Constants.UP).get(currentSpriteIndex);
        timer = 0;
        sprites = null;
        setCycleDuration(Duration.seconds(this.duration));
        setInterpolator(Interpolator.LINEAR);
        setCycleCount(INDEFINITE);
    }
    public SpriteAnimation(
            ArrayList<Sprite> sprites,
            double duration) {
        this.sprites  = sprites;
        this.count      = sprites.size();
        this.duration   = duration;
        this.currentSpriteIndex = 0;
       /* try {
            currentSprite = sprites.get(currentSpriteIndex).clone();
        }catch (Exception ex){
            currentSprite = null;
        }*/
        currentSprite = sprites.get(currentSpriteIndex);
        timer = 0;
        spriteMap = null;
        setCycleDuration(Duration.seconds(this.duration));
        setInterpolator(Interpolator.LINEAR);
        setCycleCount(INDEFINITE);
    }

    public int getDirection() {
        return direction;
    }
    public void setDirection(int direction) {
        this.direction = direction;
    }

    public Sprite getCurrentSprite() {
        return currentSprite;
    }

    public ArrayList<Sprite> getSprites() {
        return sprites;
    }

    public void interpolate(double k) { // 0.0 - 1.0
        this.k = k;
        int index = (int)calcSpriteIndex(k);
        if(sprites == null) {
            currentSprite = spriteMap.get(this.getDirection()).get(index);
        }else{
           /* try {
                currentSprite = sprites.get(index).clone();
            }catch (Exception ex){
                currentSprite = null;
            }*/
            currentSprite = sprites.get(index);
        }
        this.currentSpriteIndex = index;
    }
    public int getCurrentSpriteIndex(){
        return this.currentSpriteIndex;
    }
    public int getCount(){
        return count;
    }
    /*public void printSpriteMap(){
        for(ArrayList<Sprite>sprites: sprites)
        System.out.println(spriteMap);
    }*/
    public double calcSpriteIndex(double k) {
        double e1 = 1.0/count; // +epsilon
        for(double e0 = 0; e0 < 1.0; e0+=1.0/count) { // -epsilon
            if(k >= e0 && k <= e1) {
                return e0 * count;
            }
            e1 += 1.0/count;

        }
        return -1;
    }

}
