package Constructor;

import Engine.RigidBody2D;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Block extends Pane {

    final int BLOCK_SIZE = 28;
    Body2D body;
    Point2D pointBlock;
    public Block(float x, float y, Image imageBlock){
        pointBlock = new Point2D(x*BLOCK_SIZE, y*BLOCK_SIZE);
        body = new Body2D(new Sprite(new ImageView(imageBlock), pointBlock),
                new RigidBody2D(pointBlock.getX(), pointBlock.getY(), imageBlock.getWidth(), imageBlock.getHeight()));

        body.getSprite().getTexture().setTranslateX(BLOCK_SIZE*x);
        body.getSprite().getTexture().setTranslateY(BLOCK_SIZE*y);

        getChildren().add(body.getSprite().getTexture());
    }
    int getBLOCK_SIZE(){
        return BLOCK_SIZE;
    }
    public Body2D getBody() {
        return body;
    }
}