package Constructor;

import Engine.Constants;
import Engine.RigidBody2D;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import sample.Game;
import sample.InfoBar;
import sample.Pacman;

import static Engine.Constants.SPEED;

public class Fruit extends Block {

    private int activeMove;
    private boolean isStuck;
    private boolean isEaten;

    public Fruit(float x, float y, Image imageBlock, int shift){
        super(x, y, imageBlock);
        body.getSprite().getTexture().setTranslateX(BLOCK_SIZE*x+shift);
        body.getSprite().getTexture().setTranslateY(BLOCK_SIZE*y+shift);
        activeMove = Constants.NONE;
        isStuck = false;
        isEaten = false;

        /*getChildren().remove(body.getSprite().getTexture());
        getChildren().add(body.getSprite().getTexture());*/
    }
    public boolean getIsStuck(){
        return isStuck;
    }

    public boolean getIsEaten(){
        return isEaten;
    }
    public void setIsEaten(boolean value){
        isEaten = value;
    }
    public void chooseMoveFruit(int todoMove){
        if((this.getBody().getPosition().getX() % 28 == 0) && (this.getBody().getPosition().getY() % 28 == 0)){
            if(!isStuck) {
                switch (activeMove) {
                    case Constants.RIGHT:
                        this.mapX++;
                        break;
                    case Constants.LEFT:
                        this.mapX--;
                        break;
                    case Constants.UP:
                        this.mapY--;
                        break;
                    case Constants.DOWN:
                        this.mapY++;
                        break;
                }

            }
            isStuck = true;

            //System.out.println("INTO ----------------------------------" + Constants.stringDirection(todoMove));
            if(todoMove != Constants.NONE) {
                activeMove = todoMove;
                //System.out.println("TISKA------------------------------" + Constants.stringDirection(todoMove));
                todoMove = Constants.NONE;
            }
        }else{
            isStuck = false;

        }
        setActiveMove(activeMove);
    }

    void setActiveMove(int activeMove){
        switch(activeMove){
            case Constants.RIGHT:
                if((body.getPosition().getX() >= (LevelData.mapXMax-1) * 28)){
                    return;
                }
                this.move(new Point2D(1, 0), SPEED);
                break;
            case Constants.LEFT:
                if((body.getPosition().getX() <= 0)){
                    return;
                }
                this.move(new Point2D(-1, 0), SPEED);
                break;
            case Constants.UP:
                if((body.getPosition().getY() <= 0)){
                    return;
                }
                this.move(new Point2D(0, -1), SPEED);
                break;
            case Constants.DOWN:
                if((body.getPosition().getY() >= (LevelData.mapYMax-1) * 28)){
                    return;
                }
                this.move(new Point2D(0, 1), SPEED);
                break;
        }
    }

    public void move(Point2D velocity, double speed) {

        body.setVelocity(velocity);
        body.update(speed);

        this.setTranslateX(this.getTranslateX()+velocity.getX()*speed);
        this.setTranslateY(this.getTranslateY()+velocity.getY()*speed);
    }
}

