package sample;

import Constructor.*;
import Constructor.Character;
import Engine.Constants;
import javafx.animation.RotateTransition;
import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;


public class Pacman extends Character implements Movable2D, Animation {
    private boolean animationDeathStarted = false;
    public boolean isDead = false;
    private SpriteAnimation deathAnimation;
    private Integer currentScore;


    public Pacman(Body2D body, SpriteAnimation animation) {
        super(body, animation);
        currentScore = 0;
        //System.out.println(body.mapX + " " + body.mapY);
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
        getChildren().clear();
        getChildren().addAll(deathAnimation.getCurrentSprite().getTexture());

    }
    private void eatSmallPillow(){
        Fruit removedPillow = null;
        for (Fruit pillow: GridMap.listOfPillows) {
            if(this.getBody().intersects(pillow.getBody())){
                removedPillow = pillow;
                currentScore += Constants.SCORE_FOR_PILLOW;
                InfoBar.getCurrentScoreLabel().setText(currentScore.toString());
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
                currentScore += Constants.SCORE_FOR_POWER_PELLET;
                InfoBar.getCurrentScoreLabel().setText(currentScore.toString());
            }
        }
        GridMap.listOfBigPillows.remove(removedPillow);
        Game.root.getChildren().remove(removedPillow);
        mapLevelData[mapPositionY][mapPositionX] = 0;
    }

    public boolean isKilled(Collection<Ghost> ghosts){
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
        pacmanDeathMusicStart();
        if(activeMove == Constants.NONE) activeMove = Constants.UP;
        this.deathAnimation.setDirection(activeMove);
        this.deathAnimation.play();
        this.renderDeath();
        System.out.println(this.deathAnimation.k);
        if(this.deathAnimation.k == 1.0) {
            this.isDead = true;
        }

    }
    void pacmanDeathMusicStart(){
        Runnable r = ()->{
//            setProperAngelAnimation(activeMove);
                Sound soundDead = new Sound("/src/res/audio/miss.mp3");
                soundDead.playSound();

        };
        if(!animationDeathStarted) {
            animationDeathStarted = true;
            Thread readyThread = new Thread(r, "DeathThread");
            readyThread.start();
        }

    }

    public void createPacmanDeathAnimation() {
        ArrayList<Sprite> spritesDeath = new ArrayList<>();
        HashMap<Integer, ArrayList<Sprite>> deathSprites = new HashMap<>();
        for(int n = 1; n <= 4; n++) {
            for (int i = 0; i < 11; i++) {
                //System.out.println("/res/Pacman/Death/" + Constants.stringDirection(n) + "/d-" + i + ".png");
                spritesDeath.add(new Sprite(new ImageView("/res/Pacman/Death/" + Constants.stringDirection(n) + "/d-" + i + ".png"), new Point2D(0, 0)));
            }
            deathSprites.put(n, spritesDeath);
            spritesDeath = new ArrayList<>();
        }
        SpriteAnimation deathAnimation = new SpriteAnimation(deathSprites, 1.5);
        this.setDeathAnimation(deathAnimation);
        this.getDeathAnimation().setCycleCount(1);
    }



}
