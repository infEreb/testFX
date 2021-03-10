package Constructor;

import Engine.RigidBody2D;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Fruit extends Pane {

    final int BLOCK_SIZE = 28;
    Body2D body;
    Point2D pointBlock;
    int mapX, mapY;
    public Fruit(float x, float y, Image imageBlock, int shift){
        pointBlock = new Point2D(x*BLOCK_SIZE+shift, y*BLOCK_SIZE+shift);
        body = new Body2D(new Sprite(new ImageView(imageBlock), pointBlock),
                new RigidBody2D(pointBlock.getX(), pointBlock.getY(), imageBlock.getWidth(), imageBlock.getHeight()));
        this.mapX = (int) x;
        this.mapY = (int) y;
        body.getSprite().getTexture().setTranslateX(BLOCK_SIZE*x+shift);
        body.getSprite().getTexture().setTranslateY(BLOCK_SIZE*y+shift);

        getChildren().add(body.getSprite().getTexture());
    }
    public int getBLOCK_SIZE(){
        return BLOCK_SIZE;
    }
    public int getMapX(){
        return mapX;
    }
    public int getMapY(){
        return mapY;
    }
    public Body2D getBody() {
        return body;
    }

}
