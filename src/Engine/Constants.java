package Engine;

import Constructor.Sound;

public class Constants {
    public static final int NONE = 0;
    public static final int UP = 1;
    public static final int RIGHT = 2;
    public static final int DOWN = 3;
    public static final int LEFT = 4;
    public static final int UP_LEFT = 5;
    public static final int UP_RIGHT = 6;
    public static final int DOWN_LEFT = 7;
    public static final int DOWN_RIGHT = 8;
    public static final double ANIM_DELTA = 0.0005;

    public static final int PACMAN = 0;
    public static final int RED = 1;
    public static final int YELLOW = 2;
    public static final int BLUE = 3;
    public static final int PINK = 4;

    public static final String RED_STR = "Red";
    public static final String YELLOW_STR = "Yellow";
    public static final String BLUE_STR = "Blue";
    public static final String PINK_STR = "Pink";

    public static final double SPEED = 0.5;
    public static final double RED_GHOST_SPEED = 0.5;
    public static final double ESCAPE_SPEED = 0.5;
    public static final long GHOST_ESCAPE_TIME = 9000000000L;
    public static final long GHOST_BLINKING_TIME = 5000000000L;

    /*public static final Sound soundDead = new Sound("/src/res/audio/miss.mp3");*/

    public static final int SCORE_FOR_PILLOW = 10;
    public static final int SCORE_FOR_POWER_PELLET = 50;
    public static final int SCORE_FOR_CHERRY = 100;
    public static final int SCORE_FOR_GHOST = 200;

    public static String stringDirection(int direct) {
        switch (direct) {
            case 0:
                return "None";
            case 1:
                return "Up";
            case 2:
                return "Right";
            case 3:
                return "Down";
            case 4:
                return "Left";
            case 5:
                return "Up_Left";
            case 6:
                return "Up_Right";
            case 7:
                return "Down_Left";
            case 8:
                return "Down_Right";
            default:
                return null;
        }
    }

}
