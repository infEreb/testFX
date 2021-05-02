package Engine;

import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.image.*;

public class Sprite extends Parent implements Cloneable{
    private ImageView texture;
    Point2D position;
    Point2D velocity;

    private double width;
    private double height;

    public Sprite(){
        width = 0;
        height = 0;
        position = null;
        velocity = null;
    }
    public Sprite(ImageView texture, Point2D position, Point2D velocity) {
        this.texture = texture;
        this.position = position;
        this.velocity = velocity;
        width = texture.getImage().getWidth();
        height = texture.getImage().getHeight();
    }
    public Sprite(ImageView texture, Point2D position) {
        this.texture = texture;
        this.position = position;
        this.velocity = new Point2D(0,0);
        width = texture.getImage().getWidth();
        height = texture.getImage().getHeight();
    }
    public Sprite(ImageView texture, double posX, double posY, double velX, double velY) {
        this.texture = texture;
        position = new Point2D(posX, posY);
        velocity = new Point2D(velX, velY);
        width = texture.getImage().getWidth();
        height = texture.getImage().getHeight();

    }

    public Sprite clone() throws CloneNotSupportedException{

        return (Sprite) super.clone();
    }

    public ImageView getTexture() {
        return texture;
    }
    public void setTexture(ImageView texture) {
        this.texture = texture;
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

    public double getHeight() {
        return height;
    }
    public void setHeight(int height) {
        this.height = height;
    }

    public double getWidth() {
        return width;
    }
    public void setWidth(int width) {
        this.width = width;
    }

}
