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
    private Map<String, Boolean> restrictedMove = new HashMap<>(4);;
    private final String [] listButton = new String[]{"W", "S", "A", "D"};
    public Pacman(Body2D body, SpriteAnimation animation) {
        this.body = body;
        this.animation = animation;
        getChildren().add(0, body.getSprite().getTexture());
        updateRestrictedMove();
    }
    void updateRestrictedMove(){
        for (String key: listButton){
            restrictedMove.put(key, true);
        }
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

    public Map<String, Boolean> getRestrictedMove() {
        return restrictedMove;
    }


    public void moveable(String direction){

        Body2D body2D = new Body2D(this.getBody().getSprite(), this.getBody().getRigidBody());
        double bodyX = this.getBody().getPosition().getX();
        double bodyY = this.getBody().getPosition().getY();
        switch (direction){
            case "W":
                bodyY -= 1 * Constants.SPEED;
                break;
            case "S":
                bodyY+= 1 * Constants.SPEED;
                break;
            case "A":
                bodyX-= 1 * Constants.SPEED;
                break;
            case "D":
                bodyX+= 1 * Constants.SPEED;
                break;
        }
        body2D.setPosition(new Point2D(bodyX, bodyY));
/*        int bodyIntX = (int)(bodyX / 28);
        int bodyIntY = (int)(bodyY / 28);

        if(LevelData.levels[0][bodyIntX][bodyIntY] > 0) // LEFT
        {
            if ()
            restrictedMove.add(Constants.LEFT, false);
        }else if(LevelData.levels[0][pacmanCoordinateY][pacmanCoordinateX + 1] > 0){ // RIGHT
            restrictedMove.add(Constants.RIGHT, false);
        }else if(LevelData.levels[0][pacmanCoordinateY + 1][pacmanCoordinateX] > 0){ // DOWN
            restrictedMove.add(Constants.DOWN, false);
        }else if(LevelData.levels[0][pacmanCoordinateY - 1][pacmanCoordinateX] > 0){ // UP
            restrictedMove.add(Constants.UP, false);
        }*/
        for(Block block: Main.gridMap.getListOfBlocks()){
            if(body2D.intersects(block.getBody())){
                switch (direction){
                    case "W":
                        restrictedMove.put("W", false);
                        this.move(new Point2D(0, 1), SPEED);
                        System.out.println("Вперед");
                        break;
                    case "S":
                        restrictedMove.put("S", false);
                        this.move(new Point2D(0, -1), SPEED);
                        System.out.println("Вниз");
                        break;
                    case "A":
                        restrictedMove.put("A", false);
                        this.move(new Point2D(1, 0), SPEED);
                        System.out.println("Влево");
                        break;
                    case "D":
                        restrictedMove.put("D", false);
                        this.move(new Point2D(-1, 0), SPEED);
                        System.out.println("Вправо");
                        break;
                }
            }
        }
//        return true;
        System.out.println(body2D.getPosition());
    }
    public void render() {
        body.getSprite().setTexture(animation.getCurrentSprite().getTexture());
        getChildren().clear();
        getChildren().addAll(body.getSprite().getTexture());
    }
}
