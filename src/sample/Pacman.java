package sample;

import Constructor.*;
import Constructor.Character;
import Engine.Constants;
import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static Engine.Constants.SPEED;

public class Pacman extends Character implements Movable2D, Animation {

    int [][] mapLevelData;
    public Pacman(Body2D body, SpriteAnimation animation) {
        super(body, animation);
        mapLevelData = LevelData.levels[0];
    }

    public Body2D getBody() {
        return body;
    }
    public SpriteAnimation getAnimation() {
        return animation;
    }

    public void move(Point2D velocity, double speed) {
        body.setVelocity(velocity);
        body.update(speed);

        this.setTranslateX(this.getTranslateX()+velocity.getX()*speed);
        //System.out.println("X : " + this.getTranslateX());
        this.setTranslateY(this.getTranslateY()+velocity.getY()*speed);
        //System.out.println("Y : " + this.getTranslateY());
    }

    public void setMapPositionX(double pixelPositionX){
        this.mapPositionX = (int) pixelPositionX / 28;
    }
    public void setMapPositionY(double pixelPositionY){
        this.mapPositionY = (int) pixelPositionY / 28;
    }

    public boolean isPossibleToMove(int move){
        if(mapPositionX >= 0 && mapPositionX < LevelData.mapXMax-1 && mapPositionY >= 0 && mapPositionY < LevelData.mapYMax-1 ) {
            switch(move){
                case Constants.RIGHT:
                    return !(mapLevelData[mapPositionY][mapPositionX + 1] > 0);
                case Constants.LEFT:
                    return !(mapLevelData[mapPositionY][mapPositionX - 1] > 0);
                case Constants.UP:
                    return !(mapLevelData[mapPositionY - 1][mapPositionX] > 0);
                case Constants.DOWN:
                    return !(mapLevelData[mapPositionY + 1][mapPositionX] > 0);
            }
        }
        return false;
    }
    public void activeMoving(int activeMove){

        switch(activeMove){
            case Constants.RIGHT:
                if((body.getPosition().getX() >= (LevelData.mapXMax-1) * 28)){
                    return;
                }
                if(mapPositionX >= 0 && mapPositionX < LevelData.mapXMax-1 && mapPositionY >= 0 && mapPositionY < LevelData.mapYMax-1) {
                    if (mapLevelData[mapPositionY][mapPositionX + 1] > 0) {
                        return;
                    }
                }
                this.getAnimation().setDiraction(Constants.RIGHT);
                this.getAnimation().play();
                this.render();
                this.move(new Point2D(1, 0), SPEED);
                break;
            case Constants.LEFT:
                if((body.getPosition().getX() <= 0)){
                    return;
                }
                if(mapPositionX >= 0 && mapPositionX < LevelData.mapXMax-1 && mapPositionY >= 0 && mapPositionY < LevelData.mapYMax-1) {
                    if (mapLevelData[mapPositionY][mapPositionX - 1] > 0) {
                        return;
                    }
                }
                this.getAnimation().setDiraction(Constants.LEFT);
                this.getAnimation().play();
                this.render();
                this.move(new Point2D(-1, 0), SPEED);
                break;
            case Constants.UP:
                if((body.getPosition().getY() <= 0)){
                    return;
                }
                if(mapPositionX >= 0 && mapPositionX < LevelData.mapXMax-1 && mapPositionY >= 0 && mapPositionY < LevelData.mapYMax-1) {
                    if(mapLevelData[mapPositionY - 1][mapPositionX] > 0){
                        return;
                    }
                }

                this.getAnimation().setDiraction(Constants.UP);
                this.getAnimation().play();
                this.render();
                this.move(new Point2D(0, -1), SPEED);
                break;
            case Constants.DOWN:
                if((body.getPosition().getY() >= (LevelData.mapYMax-1) * 28)){
                    return;
                }
                if(mapPositionX >= 0 && mapPositionX < LevelData.mapXMax-1 && mapPositionY >= 0 && mapPositionY < LevelData.mapYMax-1) {
                    if(mapLevelData[mapPositionY + 1][mapPositionX] > 0){
                        return;
                    }
                }
                this.getAnimation().setDiraction(Constants.DOWN);
                this.getAnimation().play();
                this.render();
                this.move(new Point2D(0, 1), SPEED);
                break;
        }
        if(mapLevelData[mapPositionY][mapPositionX] == -1){
            this.eat();
        }


    }
    private void eat(){
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
    public void render() {
        body.getSprite().setTexture(animation.getCurrentSprite().getTexture());
        getChildren().clear();
        getChildren().addAll(body.getSprite().getTexture());
    }
}
