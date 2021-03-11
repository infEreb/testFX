package Constructor;

public class LevelData {
    static int [][] LEVEL1 = {
         //X  0   1   2    3   4   5   6   7   8   9  10   11   12  13  14  15  16  17  18  19  20  21 22  23  24//Y
            { 0,  0,  0,  10, 24, 24, 24, 24, 24, 24, 24,  24,  14, 24, 24, 24, 24, 24, 24, 24, 24, 13, 0,  0, 0}, //0
            { 0,  0,  0,  20, -1, -1, -1, -1, -1, -1, -1,  -1,  20, -1, -1, -1, -1, -1, -1, -1, -1, 20, 0,  0, 0}, //1
            { 0,  0,  0,  20, -2,  1,  4, -1,  1,  5,  4,  -1,  20, -1,  1,  5,  4, -1,  1,  4, -2, 20, 0,  0, 0}, //2
            { 0,  0,  0,  20, -1,  2,  3, -1,  2,  7,  3,  -1,  21, -1,  2,  7,  3, -1,  2,  3, -1, 20, 0,  0, 0}, //3
            { 0,  0,  0,  20, -1, -1, -1, -1, -1, -1, -1,  -1,  -1, -1, -1, -1, -1, -1, -1, -1, -1, 20, 0,  0, 0}, //4
            { 0,  0,  0,  20, -1, 22, 25, -1, 19, -1, 22,  24,  14, 24, 25, -1, 19, -1, 22, 25, -1, 20, 0,  0, 0}, //5
            { 0,  0,  0,  20, -1, -1, -1, -1, 20, -1, -1,  -1,  20, -1, -1, -1, 20, -1, -1, -1, -1, 20, 0,  0, 0}, //6
            { 0,  0,  0,  11, 24, 24, 13, -1, 15, 24, 25,  -1,  21, -1, 22, 24, 17, -1, 10, 24, 24, 12, 0,  0, 0}, //7
            { 0,  0,  0,   0,  0,  0, 20, -1, 20, -1, -1,  -1,  -1, -1, -1, -1, 20, -1, 20,  0,  0,  0,  0,  0, 0},  //8
            { 0,  0,  0,   0,  0,  0, 20, -1, 21, -1,  10, 24,  26, 24, 13, -1, 21, -1, 20,  0,  0,  0,  0,  0, 0},  //9
            {27, 27, 27,  22, 24, 24, 12, -1, -1, -1,  20,  0,   0,  0, 20, -1, -1, -1, 11, 24, 24, 25, 27, 27, 27}, //10
            { 0,  0,  0,   0,  0,  0,  0, -1, -1, -1,  20,  0,   0,  0, 20, -1, -1, -1,  0,  0,  0,  0,  0,  0, 0},  //11
            {27, 27, 27,  22, 24, 24, 13, -1, -1, -1,  20,  0,   0,  0, 20, -1, -1, -1, 10, 24, 24, 25, 27, 27, 27}, //12
            { 0,  0,  0,   0,  0,  0, 20, -1, 19, -1,  11, 24,  24, 24, 12, -1, 19, -1, 20,  0,  0,  0,  0,  0, 0},  //13
            { 0,  0,  0,   0,  0,  0, 20, -1, 20, -1, -1,  -1,  -1, -1, -1, -1, 20, -1, 20,  0,  0,  0,  0,  0, 0},  //14
            { 0,  0,  0,  10, 24, 24, 12, -1, 21, -1,  22, 24,  14, 24, 25, -1, 21, -1, 11, 24, 24, 13, 0,  0, 0}, //15
            { 0,  0,  0,  20, -1, -1, -1, -1, -1, -1, -1,  -1,  20, -1, -1, -1, -1, -1, -1, -1, -1, 20, 0,  0, 0}, //16
            { 0,  0,  0,  20, -2, 22, 13, -1, 22, 24, 25,  -1,  21, -1, 22, 24, 25, -1, 10, 25, -2, 20, 0,  0, 0}, //17
            { 0,  0,  0,  20, -1, -1, 20, -1, -1, -1, -1,  -1,  -1, -1, -1, -1, -1, -1, 20, -1, -1, 20, 0,  0, 0}, //18
            { 0,  0,  0,  15, 25, -1, 21, -1, 19, -1, 22,  24,  14, 24, 25, -1, 19, -1, 21, -1, 22, 17, 0,  0, 0}, //19
            { 0,  0,  0,  20, -1, -1, -1, -1, 20, -1, -1,  -1,  20, -1, -1, -1, 20, -1, -1, -1, -1, 20, 0,  0, 0}, //20
            { 0,  0,  0,  20, -1, 22, 24, 24, 16, 24, 25,  -1,  21, -1, 22, 24, 16, 24, 24, 25, -1, 20, 0,  0, 0}, //21
            { 0,  0,  0,  20, -1, -1, -1, -1, -1, -1, -1,  -1,  -1, -1, -1, -1, -1, -1, -1, -1, -1, 20, 0,  0, 0}, //22
            { 0,  0,  0,  11, 24, 24, 24, 24, 24, 24, 24,  24,  24, 24, 24, 24, 24, 24, 24, 24, 24, 12, 0,  0, 0}, //23
    };

    public static final int mapXMax = LEVEL1[LEVEL1.length-1].length;

    public static final int mapYMax = LEVEL1.length;

    public static int[][][] levels = new int[][][]{
        LEVEL1
    };
}
