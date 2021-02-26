package sample;

import Constructor.Animation;
import Constructor.Body2D;
import Constructor.Control2D;
import Constructor.SpriteAnimation;
import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;

public class Pacman extends Pane implements Control2D {
    private final Body2D body;
    private final SpriteAnimation animation;
    public Pacman(Body2D body, SpriteAnimation animation) {
        this.body = body;
        this.animation = animation;
        getChildren().addAll(body.getSprite().getTexture());
    }

    public Body2D getBody() {
        return body;
    }
    public SpriteAnimation getAnimation() {
        return animation;
    }

    @Override
    public void move(Point2D velocity, int speed) {
        body.setVelocity(velocity);
        body.update(speed);

        this.setTranslateX(this.getTranslateX()+velocity.getX()*speed);
        this.setTranslateY(this.getTranslateY()+velocity.getY()*speed);
    }
}
