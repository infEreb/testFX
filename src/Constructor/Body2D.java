package Constructor;

import Engine.RigidBody2D;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Body2D {
    private Sprite sprite;
    private RigidBody2D rigidBody;

    Point2D position;
    Point2D velocity;

    public Body2D(Sprite sprite, RigidBody2D rigidBody) {

        this.sprite = sprite;
        this.rigidBody = rigidBody;
        setPosition(sprite.getPosition());
        setVelocity(sprite.getVelocity());
    }

    public Sprite getSprite() {
        return sprite;
    }
    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public RigidBody2D getRigidBody() {
        return rigidBody;
    }
    public void setRigidBody(RigidBody2D rigidBody) {
        this.rigidBody = rigidBody;
    }

    public Point2D getPosition() {
        return position;
    }
    public void setPosition(Point2D position) {
        this.position = position;
    }

    public Point2D getVelocity() {
        return velocity;
    }
    public void setVelocity(Point2D velocity) {
        this.velocity = velocity;
    }

    public void update(double speed) {
        position = position.add(velocity.getX()*speed, velocity.getY()*speed);
        sprite.setPosition(position);
        rigidBody = new RigidBody2D(position.getX(), position.getY(), rigidBody.getWidth(), rigidBody.getHeight());
    }
    public void render(GraphicsContext gc, Image texture) {
        sprite.setTexture(new ImageView(texture));
        gc.drawImage(sprite.getTexture().getImage(), position.getX(), position.getY());
    }

    public boolean intersects(Body2D anotherBody) {
        return anotherBody.getRigidBody().intersects(this.getRigidBody());
    }
}
