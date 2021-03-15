package Constructor;

import Engine.RigidBody2D;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Fruit extends Block {

    public Fruit(float x, float y, Image imageBlock, int shift){
        super(x, y, imageBlock);
        body.getSprite().getTexture().setTranslateX(BLOCK_SIZE*x+shift);
        body.getSprite().getTexture().setTranslateY(BLOCK_SIZE*y+shift);

        getChildren().remove(body.getSprite().getTexture());
        getChildren().add(body.getSprite().getTexture());
    }
}
