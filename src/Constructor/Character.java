package Constructor;

import Engine.*;
import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;

import static Engine.Constants.SPEED;

public class Character extends Pane {
    protected final Body2D body;
    protected SpriteAnimation moveAnimation;
    protected int [][] mapLevelData;
    public int mapPositionX;
    public int mapPositionY;
    private final Sprite initialSprite;

    public Character(Body2D body, SpriteAnimation animation) {
        Sprite initialSprite1;
        this.body = body;
        try {
            initialSprite1 = body.getSprite().clone();
        }catch (CloneNotSupportedException ex){
            initialSprite1 = null;
        }
        this.initialSprite = initialSprite1;
        this.moveAnimation = animation;
        mapLevelData = LevelData.levels[0];
        getChildren().add(0, body.getSprite().getTexture());
    }

    public Body2D getBody() {
        return body;
    }
    public SpriteAnimation getAnimation() {
        return moveAnimation;
    }
    public void setAnimation(SpriteAnimation animation) {
        moveAnimation = animation;
    }
    public void move(Point2D velocity, double speed, int direction) {
        this.getAnimation().setDirection(direction);
        this.getAnimation().play();
        this.render();

        body.setVelocity(velocity);
        body.update(speed);

        this.setTranslateX(this.getTranslateX()+velocity.getX()*speed);
        this.setTranslateY(this.getTranslateY()+velocity.getY()*speed);
    }

    public void setMapPosition(Point2D pixelPosition) {
        mapPositionX = (int)pixelPosition.getX() / 28;
        mapPositionY = (int)pixelPosition.getY() / 28;
    }
    public void setMapPositionX(double pixelPositionX){
        this.mapPositionX = (int) pixelPositionX / 28;
    }
    public void setMapPositionY(double pixelPositionY){
        this.mapPositionY = (int) pixelPositionY / 28;
    }
    public void setStartedPosition(int x, int y){
        this.setTranslateX(x);
        this.setTranslateY(y);
        body.setPosition(new Point2D(x, y));
        this.setMapPositionX(x);
        this.setMapPositionY(y);
    }
    public void setStartedPositionAfterPacmanDeath(int x, int y){
        this.setStartedPosition(x, y);
        body.setRigidBody(new RigidBody2D(x, y,
                body.getRigidBody().getWidth(), body.getRigidBody().getHeight()));
        body.setSprite(initialSprite);
//        body.getSprite().setPosition(new Point2D(0, 0));
        body.setVelocity(new Point2D(0, 0));
        getChildren().clear();
        getChildren().add(body.getSprite().getTexture());
    }
    public boolean logicalIsPossibleToMove(int move){
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
    public void activeMoving(int activeMove, double speed){

        if(!logicalIsPossibleToMove(activeMove)) return;

        switch(activeMove){
            case Constants.RIGHT:
                if((body.getPosition().getX() >= (LevelData.mapXMax-1) * 28)){
                    return;
                }
                this.move(new Point2D(1, 0), speed, Constants.RIGHT);
                break;
            case Constants.LEFT:
                if((body.getPosition().getX() <= 0)){
                    return;
                }
                this.move(new Point2D(-1, 0), speed, Constants.LEFT);
                break;
            case Constants.UP:
                if((body.getPosition().getY() <= 0)){
                    return;
                }
                this.move(new Point2D(0, -1), speed, Constants.UP);
                break;
            case Constants.DOWN:
                if((body.getPosition().getY() >= (LevelData.mapYMax-1) * 28)){
                    return;
                }
                this.move(new Point2D(0, 1), speed, Constants.DOWN);
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
//        body.getSprite().setTexture(moveAnimation.getCurrentSprite().getTexture());
        getChildren().clear();
        getChildren().addAll(moveAnimation.getCurrentSprite().getTexture());
    }
}
