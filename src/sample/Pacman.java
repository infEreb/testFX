package sample;

import Constructor.*;
import Constructor.Character;
import Engine.Body2D;
import Engine.Constants;
import Engine.Sprite;
import Engine.SpriteAnimation;
import javafx.animation.PauseTransition;
import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;


public class Pacman extends Character implements Movable2D, Animation {
    private boolean animationDeathStarted = false;
    private int eatenPillowPos;
    private boolean isDead = false;
    private boolean bigPillowHasEaten = false;
    private SpriteAnimation deathAnimation;
    private Integer currentScore;
    private int countAllPillows;
    private boolean canKillGhosts = false;
    private int countEatenGhosts = 0;

    public Pacman(Body2D body, SpriteAnimation animation) {
        super(body, animation);
        currentScore = 0;
        countAllPillows = GridMap.listOfPillows.size() + GridMap.listOfBigPillows.size();
        //System.out.println(body.mapX + " " + body.mapY);
    }
    public boolean isPacmanWin(){
        if(countAllPillows == 0){
            return true;
        }else{
            return false;
        }
    }
    public boolean getIsDead(){
        return isDead;
    }
    public void setIsDead(boolean value){
        isDead = value;
    }
    public void setBigPillowHasEaten(boolean state) { bigPillowHasEaten = state; }
    public boolean getBigPillowHasEaten() { return bigPillowHasEaten; }
    public int getEatenPillowPos() { return eatenPillowPos; }
    public SpriteAnimation getDeathAnimation() {
        return deathAnimation;
    }
    public void setDeathAnimation(SpriteAnimation deathAnimation) {
        this.deathAnimation = deathAnimation;
    }
    public boolean getCanKillGhosts(){ return canKillGhosts; }
    public void setCanKillGhosts(boolean value){ canKillGhosts = value; }
    public void setCountEatenGhosts(int value){ countEatenGhosts = value; }

    @Override
    public void activeMoving(int activeMove, double speed){
        super.activeMoving(activeMove, Constants.SPEED);
        if(mapLevelData[mapPositionY][mapPositionX] == -1){
            this.eatSmallPillow();
            countAllPillows--;
        }
        if(mapLevelData[mapPositionY][mapPositionX] == -2){
            this.eatBigPillow();
            countAllPillows--;
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
        int eatenPillow = 0;
        Fruit removedPillow = null;
        for (Fruit pillow: GridMap.listOfBigPillows) {
            if(this.getBody().intersects(pillow.getBody())){
                eatenPillow = calcEscapePathByEatenPillow(pillow);
                System.out.println("----eatBigPillow - " + Constants.stringDirection(eatenPillow));
                removedPillow = pillow;
                currentScore += Constants.SCORE_FOR_POWER_PELLET;
                InfoBar.getCurrentScoreLabel().setText(currentScore.toString());
            }
        }
        GridMap.listOfBigPillows.remove(removedPillow);
        Game.root.getChildren().remove(removedPillow);
        mapLevelData[mapPositionY][mapPositionX] = 0;
        setBigPillowHasEaten(true);
        eatenPillowPos = eatenPillow;
    }

    private int calcEscapePathByEatenPillow(Fruit pillow) { // diagonally opposite position
        Point2D pos = pillow.getBody().getLogicalPosFromPixelPos();
        System.out.println(pos);
        if(pos.getX() == 20) {
            if (pos.getY() == 17)
                return Constants.DOWN_RIGHT;
            else
                return Constants.UP_RIGHT;
        }
        else {
            if (pos.getY() == 2)
                return Constants.UP_LEFT;
            else
                return Constants.DOWN_LEFT;
        }

    }


    public void checkIsEatenFruit(Fruit fruit, int activeMove){
        if(this.body.intersects(fruit.getBody())){
            fruit.setIsEaten(true);
            currentScore += Constants.SCORE_FOR_CHERRY;
            InfoBar.getCurrentScoreLabel().setText(currentScore.toString());
            Game.root.getChildren().remove(fruit);
            addLabelAfterPacman(activeMove, new ImageView("/res/100.png"));
            Sound.playSound("/src/res/audio/eating-fruit.mp3");
            System.out.println("Pacman ate a fruit");
        }
    }

    public void countKilledGhostAndAddLabel(int activeMove){
        ImageView label;
        switch(countEatenGhosts){
            case 0:
                label = new ImageView("/res/200.png");
                currentScore += 200;
                countEatenGhosts++;
                break;
            case 1:
                label = new ImageView("/res/400.png");
                currentScore += 400;
                countEatenGhosts++;
                break;
            case 2:
                label = new ImageView("/res/800.png");
                currentScore += 800;
                countEatenGhosts++;
                break;
            case 3:
                label = new ImageView("/res/1600.png");
                currentScore += 1600;
                countEatenGhosts++;
                break;
            default:
                label = null;
        }
        InfoBar.getCurrentScoreLabel().setText(currentScore.toString());
        if(label != null){
            addLabelAfterPacman(activeMove, label);
        }
    }

    void addLabelAfterPacman(int activeMove, ImageView label){
        double x = this.getBody().getPosition().getX();
        double y = this.getBody().getPosition().getY();
        int shift = -3;
        switch (activeMove){
            case Constants.UP:
                x -= 6;
                y += shift;
                break;
            case Constants.RIGHT:
                x -= shift;
                y += 6;
                break;
            case Constants.DOWN:
                x -= 6;
                y -= shift;
                break;
            case Constants.LEFT:
                x += shift;
                y += 6;
                break;
            default: return;
        }
        label.setTranslateX(x);
        label.setTranslateY(y);
        //pacmanEatFruitMusic();
        //Sound.playSound("/src/res/audio/eating-fruit.mp3");
        Game.root.getChildren().add(label);
        PauseTransition pause = new PauseTransition(Duration.seconds(1));
        pause.setOnFinished(e -> {
            Game.root.getChildren().remove(label);
        });
        pause.play();
    }
    /*void pacmanEatFruitMusic(){
        Runnable r = ()->{
            Sound.playSound("/src/res/audio/eating-fruit.mp3");

        };

        Thread readyThread = new Thread(r, "FruitThread");
        readyThread.start();
    }*/



    public boolean isKilled(Collection<Ghost> ghosts){
        for(Ghost ghost: ghosts){
            if(ghost.getBody().intersects(this.getBody())){
                return true;
            }
        }
        return false;
    }
    public boolean isKilledByOne(Ghost ghost){
        if(ghost.getBody().intersects(this.getBody())){
            return true;
        }
        return false;
    }

    public void pacmanDeadAnimation(int activeMove){
        pacmanDeathMusicStart();
        if(activeMove == Constants.NONE) activeMove = Constants.UP;
        this.deathAnimation.setDirection(activeMove);
        this.deathAnimation.play();
        this.renderDeath();
        //System.out.println(this.deathAnimation.k);
        if(this.deathAnimation.k == 1.0) {
            this.isDead = true;
        }

    }
    void pacmanDeathMusicStart(){
        Runnable r = ()->{
            Sound.playSound("/src/res/audio/miss.mp3");

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
