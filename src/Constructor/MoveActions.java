package Constructor;

import Engine.Constants;
import javafx.geometry.Point2D;

import java.util.Random;

public class MoveActions {
    public static void randomMove(Character character, double speed) {
        int direction;
        Random rand = new Random();
        direction = rand.nextInt(3) + 1;

        Point2D velocity = new Point2D(0,0);

        switch (direction) {
            case Constants.UP:
                velocity = new Point2D(0, -1);
                break;
            case Constants.DOWN:
                velocity = new Point2D(0, 1);
                break;
            case Constants.LEFT:
                velocity = new Point2D(-1, 0);
                break;
            case Constants.RIGHT:
                velocity = new Point2D(1, 0);
                break;
        }

        character.getBody().setVelocity(velocity);
        character.getBody().update(speed);

        character.setTranslateX(character.getTranslateX()+velocity.getX()*speed);
        character.setTranslateY(character.getTranslateY()+velocity.getY()*speed);
    }
}

