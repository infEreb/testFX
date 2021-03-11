package sample;

import Constructor.*;
import Constructor.Character;
import Engine.Constants;
import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;

import static Engine.Constants.SPEED;

public class Ghost extends Character implements Animation, Movable2D {

    private boolean playerIsVisible;
    private boolean dead;

    public Ghost(Body2D body, SpriteAnimation animation) {
        super(body, animation);
        this.animation.setDiraction(Constants.UP);
        dead = false;
    }

    /*public void render() {
        //if(!dead) {
            body.getSprite().setTexture(animation.getCurrentSprite().getTexture());
            getChildren().clear();
            getChildren().addAll(body.getSprite().getTexture());
            //System.out.println(animation.getCurrentSprite().getTexture().getImage().getUrl());
        //}
        //else {

        //}


    }*/

    @Override
    public boolean isPossibleToMove(int move){
        if(mapPositionX >= 0 && mapPositionX < LevelData.mapXMax-1 && mapPositionY >= 0 && mapPositionY < LevelData.mapYMax-1 ) {
            switch(move){
                case Constants.RIGHT:
                    return !(mapLevelData[mapPositionY][mapPositionX + 1] > 0);
                case Constants.LEFT:
                    return !(mapLevelData[mapPositionY][mapPositionX - 1] > 0);
                case Constants.UP:
                    return (!(mapLevelData[mapPositionY - 1][mapPositionX] > 0) || mapLevelData[mapPositionY - 1][mapPositionX] == 26);
                case Constants.DOWN:
                    return !(mapLevelData[mapPositionY + 1][mapPositionX] > 0);
            }
        }
        return false;
    }

    public void checkVisiblePlayer(int[] playerPosition) {
        boolean upBlock = false, downBlock = false, leftBlock = false, rightBlock = false;
        int[] up = {mapPositionX, mapPositionY - 1};
        int[] down = {mapPositionX, mapPositionY + 1};
        int[] left = {mapPositionX - 1, mapPositionY};
        int[] right = {mapPositionX + 1, mapPositionY};
        while (!(upBlock && downBlock && leftBlock && rightBlock)) {
            if (LevelData.levels[0][up[1]][up[0]] > 0) upBlock = true;
            if (LevelData.levels[0][down[1]][down[0]] > 0) downBlock = true;
            if (LevelData.levels[0][left[1]][left[0]] > 0) leftBlock = true;
            if (LevelData.levels[0][right[1]][right[0]] > 0) rightBlock = true;

            if(!upBlock) {
                if(up[0] == playerPosition[0] && up[1] == playerPosition[1])
                    playerIsVisible = true;
                up = new int[] {up[0], up[1]--};
            }
            if(!downBlock) {
                if(down[0] == playerPosition[0] && down[1] == playerPosition[1])
                    playerIsVisible = true;
                down = new int[] {down[0], down[1]++};
            }
            if(!leftBlock) {
                if(left[0] == playerPosition[0] && left[1] == playerPosition[1])
                    playerIsVisible = true;
                left = new int[] {left[0]--, left[1]};
            }
            if(!rightBlock) {
                if(right[0] == playerPosition[0] && right[1] == playerPosition[1])
                    playerIsVisible = true;
                right = new int[] {right[0]++, right[1]};
            }
        }

        playerIsVisible = false;
    }

}
