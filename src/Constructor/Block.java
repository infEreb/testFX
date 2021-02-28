package Constructor;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Block extends Pane {

    final int BLOCK_SIZE = 28;

    public Block(float x, float y, Image imageBlock){
        ImageView texture = new ImageView(imageBlock);
        texture.setFitHeight(BLOCK_SIZE);
        texture.setFitWidth(BLOCK_SIZE);
        texture.setTranslateX(BLOCK_SIZE*x);
        texture.setTranslateY(BLOCK_SIZE*y);

        getChildren().add(texture);
    }
}
