package Constructor;

import javafx.geometry.Point2D;

public interface Movable2D {
    void move(Point2D velocity, double speed, int direction);
}
