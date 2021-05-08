package sample;

import Constructor.*;
import Constructor.Character;
import Engine.*;
import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.HashMap;

public class Ghost extends Character implements Animation, Movable2D {

    private boolean playerIsVisible;
    private boolean isDead;
    private int directionToMove;
    private SpriteAnimation escapeAnimation = null;

    public Ghost(Body2D body, SpriteAnimation animation) {
        super(body, animation);
        this.moveAnimation.setDirection(Constants.UP);
        createGhostEscapeAnimation();
        isDead = false;
    }

    public SpriteAnimation getEscapeAnimation() {
        return escapeAnimation;
    }

    public int calculateDirEscape(Pacman pac) { // calculates direction to ghost's escape from pacman
        Point2D normVec = MoveActions.normalizeVec(this.getBody().getLogicalPosFromPixelPos().subtract(pac.getBody().getLogicalPosFromPixelPos()));
        //System.out.println(normVec.toString());
        return MoveActions.vectorToDirection(normVec);
    }

    public boolean playerIsVisible() {
        return playerIsVisible;
    }
//    public void setPlayerIsVisible(boolean playerIsVisible) {
//        this.playerIsVisible = playerIsVisible;
//    }

    public boolean isDead() {
        return isDead;
    }
    public void setIsDead(boolean isDead) {
        this.isDead = isDead;
    }

    public int getDirectionToMove() {
        return directionToMove;
    }
    public void setDirectionToMove(int direction) {
        directionToMove = direction;
    }

    public void ghostEscapeAnimation(int activeMove){
        if(activeMove == Constants.NONE) activeMove = Constants.UP;
        this.escapeAnimation.setDirection(activeMove);
        this.escapeAnimation.play();
        this.renderEscape();
        System.out.println(this.escapeAnimation.getCurrentSprite().getTexture().getImage().getUrl());

    }
    public void renderEscape(){
        getChildren().clear();
        getChildren().addAll(escapeAnimation.getCurrentSprite().getTexture());

    }

    public void createGhostEscapeAnimation() {
        Sprite g_s = new Sprite(new ImageView("/res/Ghosts/Death/f-0.png"),
                new Point2D(0, 0));
        Sprite g_0u = new Sprite(new ImageView("/res/Ghosts/Death/f-0.png"),
                new Point2D(0, 0));
        Sprite g_1u = new Sprite(new ImageView("/res/Ghosts/Death/f-1.png"),
                new Point2D(0, 0));
        Sprite g_0d = new Sprite(new ImageView("/res/Ghosts/Death/f-0.png"),
                new Point2D(0, 0));
        Sprite g_1d = new Sprite(new ImageView("/res/Ghosts/Death/f-1.png"),
                new Point2D(0, 0));
        Sprite g_0l = new Sprite(new ImageView("/res/Ghosts/Death/f-0.png"),
                new Point2D(0, 0));
        Sprite g_1l = new Sprite(new ImageView("/res/Ghosts/Death/f-1.png"),
                new Point2D(0, 0));
        Sprite g_0r = new Sprite(new ImageView("/res/Ghosts/Death/f-0.png"),
                new Point2D(0, 0));
        Sprite g_1r = new Sprite(new ImageView("/res/Ghosts/Death/f-1.png"),
                new Point2D(0, 0));

        //============ SPRITE LIST ANIMATION ============
        ArrayList<Sprite> ghost_anim_up = new ArrayList<>();
        ghost_anim_up.add(g_0u);
        ghost_anim_up.add(g_1u);
        ArrayList<Sprite> ghost_anim_down = new ArrayList<>();
        ghost_anim_down.add(g_0d);
        ghost_anim_down.add(g_1d);
        ArrayList<Sprite> ghost_anim_left = new ArrayList<>();
        ghost_anim_left.add(g_0l);
        ghost_anim_left.add(g_1l);
        ArrayList<Sprite> ghost_anim_right = new ArrayList<>();
        ghost_anim_right.add(g_0r);
        ghost_anim_right.add(g_1r);

        ///============ SPRITE DIRECTION HASHMAP ANIMATION ============
        HashMap<Integer, ArrayList<Sprite>> ghostSprites = new HashMap<>();
        ghostSprites.put(Constants.UP, ghost_anim_up);
        ghostSprites.put(Constants.DOWN, ghost_anim_down);
        ghostSprites.put(Constants.LEFT, ghost_anim_left);
        ghostSprites.put(Constants.RIGHT, ghost_anim_right);

        escapeAnimation = new SpriteAnimation(ghostSprites, 0.15);
    }

    @Override
    public boolean logicalIsPossibleToMove(int move){
        if(mapPositionX >= 0 && mapPositionX < LevelData.mapXMax-1 && mapPositionY >= 0 && mapPositionY < LevelData.mapYMax-1 ) {
            switch(move){
                case Constants.RIGHT:
                    return !(mapLevelData[mapPositionY][mapPositionX + 1] > 0);
                case Constants.LEFT:
                    return !(mapLevelData[mapPositionY][mapPositionX - 1] > 0);
                case Constants.UP:
                    return (!(mapLevelData[mapPositionY - 1][mapPositionX] > 0) || mapLevelData[mapPositionY - 1][mapPositionX] == 26);
                case Constants.DOWN:
                    return !(mapLevelData[mapPositionY + 1][mapPositionX] > 0 && mapLevelData[mapPositionY + 1][mapPositionX] != 26);
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
