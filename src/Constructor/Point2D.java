package Constructor;

public class Point2D {
    private double x;
    private double y;

    public Point2D(double X, double Y) {
        x = X;
        y = Y;
    }

    public void setCoordinates(double posX, double posY) {
        x = posX;
        y = posY;
    }
    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }

    public void add(Point2D point) {
        this.x += point.getX();
        this.y += point.getY();
    }
    public void add(double x, double y) {
        this.x += x;
        this.y += y;
    }
    public void multiply(int num) {
        x *= num;
        y *= num;
    }
}
