package Engine;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Body2D {
    private Sprite sprite;
    private RigidBody2D rigidBody;

    Point2D position;
    Point2D velocity;
    public int mapX, mapY;

    public Body2D(Sprite sprite, RigidBody2D rigidBody) {

        this.sprite = sprite;
        this.rigidBody = rigidBody;
        setPosition(sprite.getPosition());
        setVelocity(sprite.getVelocity());
        //mapX = (int)position.getX()/28;
        //mapY = (int)position.getY()/28;

    }

    public Sprite getSprite() {
        return sprite;
    }
    public void setSprite(Sprite sprite) {
        try {
            this.sprite = sprite.clone();
        }catch (Exception ex){
        }
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
    public Point2D getLogicalPosition() {
        return new Point2D(mapX, mapY);
    }
    public Point2D getLogicalPosFromPixelPos(){
        return new Point2D((int)position.getX()/28, (int)position.getY()/28);
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
        gc.drawImage(texture, position.getX(), position.getY());
    }

    public boolean intersects(Body2D anotherBody) {
        return anotherBody.getRigidBody().intersects(this.getRigidBody());
    }
}
