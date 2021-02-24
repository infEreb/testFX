package Constructor;

import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.*;

public class Sprite extends Node {
    private Image image;
    Point2D position;
    Vector2D velocity;

    private int width;
    private int height;

    public Sprite(){}
    public Sprite(Image image, Point2D position, Vector2D velocity, int w, int h) {
        this.image = image;
        this.position = position;
        this.velocity = velocity;
        width = w;
        height = h;
    }
    public Sprite(Image image, double posX, double posY, double velX, double velY, int w, int h) {
        this.image = image;
        position = new Point2D(posX, posY);
        velocity = new Vector2D(new Point2D(velX, velY));
        width = w;
        height = h;
    }

    // Getters / setters
    // CHANGE w/h INT TO DOUBLE?? !!!
    //
    public Image getImage() {
        return image;
    }
    public void setImage(Image image) {
        this.image = image;
    }

    public Point2D getPosition() {
        return position;
    }
    public void setPosition(Point2D position) {
        this.position = position;
    }

    public Vector2D getVelocity() {
        return velocity;
    }
    public void setVelocity(Vector2D velocity) {
        this.velocity = velocity;
    }

    public int getHeight() {
        return height;
    }
    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }
    public void setWidth(int width) {
        this.width = width;
    }

    public void update(double time) {
        position.add(velocity.getCoordinates().getX()*time, velocity.getCoordinates().getY()*time);
    }
    public void render(GraphicsContext gc) {
        gc.drawImage(image, position.getX(), position.getY());
    }
    public Rectangle2D getBoundary() {
        return new Rectangle2D(position.getX(), position.getY(), width, height);
    }
    public boolean intersects(Sprite sprite) {
        return sprite.getBoundary().intersects(this.getBoundary());
    }
}
