package sample;

import Constructor.*;
import Constructor.Character;
import Engine.Constants;

import java.util.ArrayList;


public class Pacman extends Character implements Movable2D, Animation {

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

    void isDead(ArrayList<Ghost> listGhost){
        for(Ghost ghost: listGhost){
            if(this.getBody().intersects(ghost.getBody())){
                
            }
        }
    }
    void die(){

    }

}
