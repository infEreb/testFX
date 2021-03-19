package sample;

import Constructor.*;
import Constructor.Character;
import Engine.Constants;
import javafx.animation.RotateTransition;
import javafx.geometry.Point2D;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

import java.util.ArrayList;


public class Pacman extends Character implements Movable2D, Animation {
    private boolean animationDeathStarted = false;
    public boolean isDead = false;
    private SpriteAnimation deathAnimation;


    public Pacman(Body2D body, SpriteAnimation animation) {
        super(body, animation);
        System.out.println(body.mapX + " " + body.mapY);
    }

    public SpriteAnimation getDeathAnimation() {
        return deathAnimation;
    }
    public void setDeathAnimation(SpriteAnimation deathAnimation) {
        this.deathAnimation = deathAnimation;
    }

    @Override
    public void activeMoving(int activeMove){
        super.activeMoving(activeMove);
        if(mapLevelData[mapPositionY][mapPositionX] == -1){
            this.eatSmallPillow();
        }
        if(mapLevelData[mapPositionY][mapPositionX] == -2){
            this.eatBigPillow();
        }
    }

    public void setAnimationDeathStarted(boolean animationDeathStarted) {
        this.animationDeathStarted = animationDeathStarted;
    }

    public void renderDeath(){
//        body.getSprite().setTexture(deathAnimation.getCurrentSprite().getTexture());

        getChildren().clear();
        getChildren().addAll(deathAnimation.getCurrentSprite().getTexture());

    }
    private void eatSmallPillow(){
        Fruit removedPillow = null;
        for (Fruit pillow: GridMap.listOfPillows) {
            if(this.getBody().intersects(pillow.getBody())){
                removedPillow = pillow;
                Game.currentScore += Constants.SCORE_FOR_PILLOW;
                Game.currentScoreLabel.setText(Game.currentScore.toString());
            }
        }
        GridMap.listOfPillows.remove(removedPillow);
        Game.root.getChildren().remove(removedPillow);
        mapLevelData[mapPositionY][mapPositionX] = 0;
    }
    private void eatBigPillow(){
        Fruit removedPillow = null;
        for (Fruit pillow: GridMap.listOfBigPillows) {
            if(this.getBody().intersects(pillow.getBody())){
                removedPillow = pillow;
            }
        }
        GridMap.listOfBigPillows.remove(removedPillow);
        Game.root.getChildren().remove(removedPillow);
        mapLevelData[mapPositionY][mapPositionX] = 0;
    }

    public boolean isKilled(ArrayList<Ghost> ghosts){
        for(Ghost ghost: ghosts){
            if(ghost.getBody().intersects(this.getBody())){
                return true;
            }
        }
        return false;
    }
    private void setProperAngelAnimation(int activeMove){
        deathAnimation.getSprites().forEach(sprite -> {
            sprite.getTexture().setRotate(90*(activeMove-1));
        });
    }
    public void pacmanDeadAnimation(int activeMove){
//        if(!animationDeathStarted)
//        {
//            setProperAngelAnimation(activeMove);
//            animationDeathStarted = true;
//        }
        if(activeMove == Constants.NONE) activeMove = Constants.UP;
        this.deathAnimation.setDirection(activeMove);
        this.deathAnimation.play();
        this.renderDeath();
        System.out.println(this.deathAnimation.k);
        if(this.deathAnimation.k == 1.0) {
            this.isDead = true;
        }

    }



}
