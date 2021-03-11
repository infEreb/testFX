package sample;

import Constructor.*;
import Constructor.Character;


public class Pacman extends Character implements Movable2D, Animation {

    public Pacman(Body2D body, SpriteAnimation animation) {
        super(body, animation);
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
            }
        }
        GridMap.listOfPillows.remove(removedPillow);
        Main.root.getChildren().remove(removedPillow);
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
        Main.root.getChildren().remove(removedPillow);
        mapLevelData[mapPositionY][mapPositionX] = 0;
    }

}
