package Constructor;

import Engine.Constants;
import Engine.Graph;
import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.Random;

public class MoveActions {
    private ArrayList<Point2D> chasingTrack;
    private Random random = new Random();
    private long time;
    private boolean isStuck = true;
    private boolean firstIter = true;
    private boolean isNotStuck = false;
    private int todoMove = Constants.UP;
    private int activeMove = Constants.UP;

    public long getTime() {
        return time;
    }
    public void setTime(long time) {
        this.time = time;
    }

    public int getActiveMove() {
        return this.activeMove;
    }
    public void startedMovementCondition(){
        this.activeMove = Constants.NONE;
        this.isStuck = false;
    }
    //pacman moving
    public int move(Character character, int todoMove) {
        if((character.getBody().getPosition().getX() % 28 == 0) && (character.getBody().getPosition().getY() % 28 == 0)){
            if(!isStuck) {
                switch (activeMove) {
                    case Constants.RIGHT:
                        character.mapPositionX++;
                        break;
                    case Constants.LEFT:
                        character.mapPositionX--;
                        break;
                    case Constants.UP:
                        character.mapPositionY--;
                        break;
                    case Constants.DOWN:
                        character.mapPositionY++;
                        break;
                }

            }
            isStuck = true;

            if(todoMove != Constants.NONE && character.logicalIsPossibleToMove(todoMove)) {
                activeMove = todoMove;
                //todoMove = Constants.NONE;
            }
        }else{
            isStuck = false;
        }
        return activeMove;
    }
    //random moving
    public int randomMove(Character character, long presentNanoTime) {
        if((character.getBody().getPosition().getX() % 28 == 0) && (character.getBody().getPosition().getY() % 28 == 0)){
            if(!isStuck) {
                switch (activeMove) {
                    case Constants.RIGHT:
                        character.mapPositionX++;
                        break;
                    case Constants.LEFT:
                        character.mapPositionX--;
                        break;
                    case Constants.UP:
                        character.mapPositionY--;
                        break;
                    case Constants.DOWN:
                        character.mapPositionY++;
                        break;
                }

            }
            isStuck = true;

            if(presentNanoTime - time >= 3000000000L) {
                todoMove = random.nextInt(4) + 1;
                if(character.logicalIsPossibleToMove(todoMove))
                    activeMove = todoMove;
                time = presentNanoTime;
            }

            while(!character.logicalIsPossibleToMove(todoMove)) {
                todoMove = random.nextInt(4) + 1;
                activeMove = todoMove;
            }
        }else{
            isStuck = false;

        }
        return activeMove;
    }
    //moving by "smart algorithm"
    public int aiMove(Character character, int todoMove) {
        //logical positions

        if((character.getBody().getPosition().getX() % 28 == 0) && (character.getBody().getPosition().getY() % 28 == 0)){
            if(!isStuck && !firstIter) {
                switch (activeMove) {
                    case Constants.RIGHT:
                        character.mapPositionX++;
                        break;
                    case Constants.LEFT:
                        character.mapPositionX--;
                        break;
                    case Constants.UP:
                        character.mapPositionY--;
                        break;
                    case Constants.DOWN:
                        character.mapPositionY++;
                        break;
                }

            }
            firstIter = false;
            isStuck = true;

            //System.out.println("INTO ----------------------------------" + Constants.stringDirection(todoMove));
            if(todoMove != Constants.NONE && character.logicalIsPossibleToMove(todoMove)) {
                activeMove = todoMove;
                //System.out.println("TISKA------------------------------" + Constants.stringDirection(todoMove));
                todoMove = Constants.NONE;
            }
        }else{
            isStuck = false;

        }
        return activeMove;
    }

    public static Integer vectorToDirection(Point2D vec) {
        int x = (int)vec.getX(), y = (int)vec.getY();
        if(x == 1 && y == 0)
            return Constants.RIGHT;
        else if(x == -1 && y == 0)
            return Constants.LEFT;
        else if(x == 0 && y == -1)
            return Constants.UP;
        else if(x == 0 && y == 1)
            return Constants.DOWN;
        else if(x == 1 && y == 1)
            return Constants.DOWN_RIGHT;
        else if(x == -1 && y == 1)
            return Constants.DOWN_LEFT;
        else if(x == 1 && y == -1)
            return Constants.UP_RIGHT;
        else if(x == -1 && y == -1)
            return Constants.UP_LEFT;
        else
            return null;
    }
    public static Point2D normalizeVec(Point2D vec) {
        int x,y;
        if(vec.getX() < 0)
            x = -1;
        else if(vec.getX() > 0)
            x = 1;
        else
            x = 0;

        if(vec.getY() < 0)
            y = -1;
        else if(vec.getY() > 0)
            y = 1;
        else
            y = 0;
        System.out.println("Into vec: " + vec.toString());
        System.out.println("Into norm: (" + x + "; " + y + ")");
        return new Point2D(x, y);
    }
    public static Point2D directionToVec(int direction) {
        switch (direction) {
            case Constants.UP:
                return new Point2D(0, -1);
            case Constants.DOWN:
                return new Point2D(0, 1);
            case Constants.RIGHT:
                return new Point2D(1, 0);
            case Constants.LEFT:
                return new Point2D(-1, 0);
            default:
                return null;
        }
    }

}

