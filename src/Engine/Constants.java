package Engine;

public class Constants {
    public static final int NONE = 0;
    public static final int UP = 1;
    public static final int RIGHT = 2;
    public static final int DOWN = 3;
    public static final int LEFT = 4;
    public static final double ANIM_DELTA = 0.0005;

    public static final String Red = "Red";
    public static final String Yellow = "Yellow";
    public static final String Blue = "Blue";
    public static final String Pink = "Pink";

    public static final double SPEED = 0.5;

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
