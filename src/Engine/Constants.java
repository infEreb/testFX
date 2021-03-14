package Engine;

public class Constants {
    public static final int NONE = 0;
    public static final int UP = 1;
    public static final int DOWN = 2;
    public static final int LEFT = 3;
    public static final int RIGHT = 4;
    public static final double ANIM_DELTA = 0.0005;

    public static final String Red = "Red";
    public static final String Yellow = "Yellow";
    public static final String Blue = "Blue";
    public static final String Pink = "Pink";

    public static final double SPEED = 1;

    public static final int SCORE_FOR_PILLOW = 20;

    public static String stringDirection(int direct) {
        switch (direct) {
            case 0:
                return "NONE";
            case 1:
                return "UP";
            case 2:
                return "DOWN";
            case 3:
                return "LEFT";
            case 4:
                return "RIGHT";
            default:
                return null;
        }
    }

}
