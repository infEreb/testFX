package Constructor;

import javafx.scene.layout.Pane;

public class Character extends Pane {
    protected final Body2D body;
    protected final SpriteAnimation animation;
    public int mapPositionX;
    public int mapPositionY;

    public Character(Body2D body, SpriteAnimation animation) {
        this.body = body;
        this.animation = animation;
        getChildren().add(0, body.getSprite().getTexture());
    }

    public Body2D getBody() {
        return body;
    }
    public SpriteAnimation getAnimation() {
        return animation;
    }

    public void setMapPositionX(double pixelPositionX){
        this.mapPositionX = (int) pixelPositionX / 28;
    }
    public void setMapPositionY(double pixelPositionY){
        this.mapPositionY = (int) pixelPositionY / 28;
    }
}
