package sample;

import Constructor.Animation;
import Constructor.Body2D;
import Constructor.Movable2D;
import Constructor.SpriteAnimation;
import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;

public class Ghost extends Pane implements Animation, Movable2D {

    private Body2D body;
    private SpriteAnimation animation;
    private boolean dead;

    public Ghost(Body2D body, SpriteAnimation animation) {
        this.body = body;
        this.animation = animation;
        dead = false;

        getChildren().addAll(body.getSprite().getTexture());
    }

    public void render() {
        if(!dead) {
            body.getSprite().setTexture(animation.getCurrentSprite().getTexture());
            getChildren().clear();
            getChildren().addAll(body.getSprite().getTexture());
        }
        else {

        }


    }
    public void move(Point2D velocity, double speed) {

    }

    public boolean checkVisiblePlayer(Point2D playerPosition) {
        return true;
    }

}
