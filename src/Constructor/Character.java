package Constructor;

import Engine.Constants;
import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;

import static Engine.Constants.SPEED;

public class Character extends Pane {
    protected final Body2D body;
    protected final SpriteAnimation animation;
    protected int [][] mapLevelData;
    public int mapPositionX;
    public int mapPositionY;

    public Character(Body2D body, SpriteAnimation animation) {
        this.body = body;
        this.animation = animation;
        mapLevelData = LevelData.levels[0];
        getChildren().add(0, body.getSprite().getTexture());
    }

    public Body2D getBody() {
        return body;
    }
    public SpriteAnimation getAnimation() {
        return animation;
    }
    public void move(Point2D velocity, double speed, int direction) {
        this.getAnimation().setDiraction(direction);
        this.getAnimation().play();
        this.render();

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
        if(mapPositionX >= 0 && mapPositionX <= LevelData.mapXMax-1 && mapPositionY >= 0 && mapPositionY < LevelData.mapYMax-1 ) {
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

        if(!isPossibleToMove(activeMove)) return;

        switch(activeMove){
            case Constants.RIGHT:
                if((body.getPosition().getX() >= (LevelData.mapXMax-1) * 28)){
                    return;
                }
                this.move(new Point2D(1, 0), SPEED, Constants.RIGHT);
                break;
            case Constants.LEFT:
                if((body.getPosition().getX() <= 0)){
                    return;
                }
                this.move(new Point2D(-1, 0), SPEED, Constants.LEFT);
                break;
            case Constants.UP:
                if((body.getPosition().getY() <= 0)){
                    return;
                }
                this.move(new Point2D(0, -1), SPEED, Constants.UP);
                break;
            case Constants.DOWN:
                if((body.getPosition().getY() >= (LevelData.mapYMax-1) * 28)){
                    return;
                }
                this.move(new Point2D(0, 1), SPEED, Constants.DOWN);
                break;
        }
        if(mapPositionX == 1){
            this.setTranslateX((LevelData.mapXMax-3)*28);
            this.getBody().setPosition(new Point2D((LevelData.mapXMax-3)*28, this.getBody().getPosition().getY()));
            mapPositionX = LevelData.mapXMax-3;
        }
        else if(mapPositionX == LevelData.mapXMax-2){
            this.setTranslateX(2*28);
            this.getBody().setPosition(new Point2D(2*28, this.getBody().getPosition().getY()));
            mapPositionX = 2;
        }

    }
    public void render() {
        body.getSprite().setTexture(animation.getCurrentSprite().getTexture());
        getChildren().clear();
        getChildren().addAll(body.getSprite().getTexture());
    }
}
