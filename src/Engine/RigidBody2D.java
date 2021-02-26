package Engine;

import javafx.geometry.Rectangle2D;

public class RigidBody2D extends Rectangle2D {

    private double width;
    private double height;
    private double mass;
    private double gravityScale;

    public RigidBody2D(double x, double y, double width, double height){
        super(x,y,width,height);
        this.width = width;
        this.height = height;
        setMass(1.0);
        setGravityScale(0);
    }

    public double getMass() {
        return mass;
    }
    public void setMass(double mass) {
        this.mass = mass;
    }

    public double getGravityScale() {
        return gravityScale;
    }
    public void setGravityScale(double gravityScale) {
        this.gravityScale = gravityScale;
    }
}
