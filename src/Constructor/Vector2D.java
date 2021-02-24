package Constructor;

public class Vector2D {
    private Point2D coordinates;

    public Vector2D(Point2D coordinates) {
        this.coordinates = coordinates;
    }
    public Vector2D(Point2D tail, Point2D head) {
        coordinates = new Point2D(head.getX() - tail.getX(), head.getY() - tail.getY());
    }

    public Point2D getCoordinates() {
        return coordinates;
    }
    public void setCoordinates(Point2D coordinates) {
        this.coordinates = coordinates;
    }


}
