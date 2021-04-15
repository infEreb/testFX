package Engine;

import Constructor.Sound;

public class Constants {
    public static final int NONE = 0;
    public static final int UP = 1;
    public static final int RIGHT = 2;
    public static final int DOWN = 3;
    public static final int LEFT = 4;
    public static final double ANIM_DELTA = 0.0005;

    public static final Integer PACMAN = 0;
    public static final Integer RED = 1;
    public static final Integer YELLOW = 2;
    public static final Integer BLUE = 3;
    public static final Integer PINK = 4;

    public static final String RED_STR = "Red";
    public static final String YELLOW_STR = "Yellow";
    public static final String BLUE_STR = "Blue";
    public static final String PINK_STR = "Pink";

    public static final double SPEED = 0.5;
    public static final Sound soundDead = new Sound("/src/res/audio/miss.mp3");

    public static final int SCORE_FOR_PILLOW = 20;

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
            default:
                return null;
        }
    }

}
