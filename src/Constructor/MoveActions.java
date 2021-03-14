package Constructor;

import Engine.Constants;
import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.Random;

public class MoveActions {
    private ArrayList<Point2D> chasingTrack;
    private Random random = new Random();
    private long time;
    private boolean isStuck = true;
    private int todoMove = Constants.UP;
    private int activeMove = Constants.UP;

    public long getTime() {
        return time;
    }
    public void setTime(long time) {
        this.time = time;
    }

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


            if(todoMove != Constants.NONE && character.isPossibleToMove(todoMove)) {
                activeMove = todoMove;
                todoMove = Constants.NONE;
            }
        }else{
            isStuck = false;
        }
        return activeMove;
    }
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
                if(character.isPossibleToMove(todoMove))
                    activeMove = todoMove;
                time = presentNanoTime;
            }

            while(!character.isPossibleToMove(todoMove)) {
                todoMove = random.nextInt(4) + 1;
                activeMove = todoMove;
            }
        }else{
            isStuck = false;

        }
        return activeMove;
    }
}

