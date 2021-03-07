package sample;

import Constructor.*;
import Engine.Constants;
import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static Engine.Constants.SPEED;

public class Pacman extends Pane implements Movable2D, Animation {

    private final Body2D body;
    private final SpriteAnimation animation;
    public int mapPositionX;
    public int mapPositionY;
    int [][] mapLevelData;
    public Pacman(Body2D body, SpriteAnimation animation) {
        this.body = body;
        mapLevelData = LevelData.levels[0];
        this.animation = animation;
        getChildren().add(0, body.getSprite().getTexture());

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
                        /*if((logicalPosition.x+1 < parentBoard.m_x) && (parentBoard.map[logicalPosition.x+1][logicalPosition.y]>0)){
                            return;
                        }*/
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
                        /*if((logicalPosition.x-1 >= 0) && (parentBoard.map[logicalPosition.x-1][logicalPosition.y]>0)){
                            return;
                        }*/
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
                        /*if((logicalPosition.y-1 >= 0) && (parentBoard.map[logicalPosition.x][logicalPosition.y-1]>0)){
                            return;
                        }*/
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
                        /*if((logicalPosition.y+1 < parentBoard.m_y) && (parentBoard.map[logicalPosition.x][logicalPosition.y+1]>0)){
                            return;
                        }*/
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


    }

    /*public void moveable(String direction){

        Body2D body2D = new Body2D(this.getBody().getSprite(), this.getBody().getRigidBody());
        double bodyX = this.getBody().getPosition().getX();
        double bodyY = this.getBody().getPosition().getY();
        switch (direction){
            case "W":
                bodyY -= 2;
                break;
            case "S":
                bodyY += 2;
                break;
            case "A":
                bodyX -= 2;
                break;
            case "D":
                bodyX += 2;
                break;
        }
        body2D.setPosition(new Point2D(bodyX, bodyY));
        int bodyIntX = (int)(bodyX / 28);
        int bodyIntY = (int)(bodyY / 28);
        System.out.println("X: " + bodyIntX);
        System.out.println("Y: " + bodyIntY);
        for(Block block: Main.gridMap.getListOfBlocks()){
                if (LevelData.levels[0][bodyIntY][bodyIntX - 1] > 0 || body2D.intersects(block.getBody())) // LEFT
                {
                    restrictedMove.put("A", false);
                }
                if (LevelData.levels[0][bodyIntY][bodyIntX + 1] > 0 || body2D.intersects(block.getBody())) { // RIGHT
                    restrictedMove.put("D", false);
                }
                if (LevelData.levels[0][bodyIntY + 1][bodyIntX] > 0 || body2D.intersects(block.getBody())) { // DOWN
                    restrictedMove.put("S", false);
                }
                if (LevelData.levels[0][bodyIntY - 1][bodyIntX] > 0 || body2D.intersects(block.getBody())) { // UP
                    restrictedMove.put("W", false);
                }
            }
        }*/
 //       for(Block block: Main.gridMap.getListOfBlocks()){
            /*if(body2D.intersects(Main.gridMap.getListOfBlocks().get(body2D.getPosition()).getBody())){
                switch (direction){
                    case "W":
                        restrictedMove.put("W", false);
//                        this.move(new Point2D(0, 1), SPEED);
                        System.out.println("Вперед");
                        break;
                    case "S":
                        restrictedMove.put("S", false);
//                        this.move(new Point2D(0, -1), SPEED);
                        System.out.println("Вниз");
                        break;
                    case "A":
                        restrictedMove.put("A", false);
//                        this.move(new Point2D(1, 0), SPEED);
                        System.out.println("Влево");
                        break;
                    case "D":
                        restrictedMove.put("D", false);
//                        this.move(new Point2D(-1, 0), SPEED);
                        System.out.println("Вправо");
                        break;
                }
            }*/
  //      }
//        return true;
        //System.out.println("Body2d: " + body2D.getPosition());

    public void render() {
        body.getSprite().setTexture(animation.getCurrentSprite().getTexture());
        getChildren().clear();
        getChildren().addAll(body.getSprite().getTexture());
    }
}
