package Constructor;

import Engine.Graph;
import javafx.geometry.Point2D;

public class LevelData {
    static int [][] LEVEL1 = {
         //X   0    1    2    3   4   5   6   7   8   9  10  11  12  13  14  15  16  17  18  19  20  21  22   23  24//Y
            { 27,  27,  27,  10, 24, 24, 24, 24, 24, 24, 24, 24, 14, 24, 24, 24, 24, 24, 24, 24, 24, 13, 27, 27, 27}, //0
            { 27,  27,  27,  20, -1, -1, -1, -1, -1, -1, -1, -1, 20, -1, -1, -1, -1, -1, -1, -1, -1, 20, 27, 27, 27}, //1
            { 27,  27,  27,  20, -2,  1,  4, -1,  1,  5,  4, -1, 20, -1,  1,  5,  4, -1,  1,  4, -2, 20, 27, 27, 27}, //2
            { 27,  27,  27,  20, -1,  2,  3, -1,  2,  7,  3, -1, 21, -1,  2,  7,  3, -1,  2,  3, -1, 20, 27, 27, 27}, //3
            { 27,  27,  27,  20, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 20, 27, 27, 27}, //4
            { 27,  27,  27,  20, -1, 22, 25, -1, 19, -1, 22, 24, 14, 24, 25, -1, 19, -1, 22, 25, -1, 20, 27, 27, 27}, //5
            { 27,  27,  27,  20, -1, -1, -1, -1, 20, -1, -1, -1, 20, -1, -1, -1, 20, -1, -1, -1, -1, 20, 27, 27, 27}, //6
            { 27,  27,  27,  11, 24, 24, 13, -1, 15, 24, 25, -1, 21, -1, 22, 24, 17, -1, 10, 24, 24, 12, 27, 27, 27}, //7
            { 27,  27,  27,  27, 27, 27, 20, -1, 20, -1, -1, -1, -1, -1, -1, -1, 20, -1, 20, 27, 27, 27, 27, 27, 27}, //8
            { 27,  27,  27,  27, 27, 27, 20, -1, 21, -1, 10, 24, 26, 24, 13, -1, 21, -1, 20, 27, 27, 27, 27, 27, 27}, //9
            { 27,  27,  27,  22, 24, 24, 12, -1, -1, -1, 20,  0,  0,  0, 20, -1, -1, -1, 11, 24, 24, 25, 27, 27, 27}, //10
            {  0,   0,   0,   0,  0,  0,  0, -1, -1, -1, 20,  0,  0,  0, 20, -1, -1, -1,  0,  0,  0,  0,  0,  0,  0}, //11
            { 27,  27,  27,  22, 24, 24, 13, -1, -1, -1, 20,  0,  0,  0, 20, -1, -1, -1, 10, 24, 24, 25, 27, 27, 27}, //12
            { 27,  27,  27,  27, 27, 27, 20, -1, 19, -1, 11, 24, 24, 24, 12, -1, 19, -1, 20, 27, 27, 27, 27, 27, 27}, //13
            { 27,  27,  27,  27, 27, 27, 20, -1, 20, -1, -1, -1, -1, -1, -1, -1, 20, -1, 20, 27, 27, 27, 27, 27, 27}, //14
            { 27,  27,  27,  10, 24, 24, 12, -1, 21, -1, 22, 24, 14, 24, 25, -1, 21, -1, 11, 24, 24, 13, 27, 27, 27}, //15
            { 27,  27,  27,  20, -1, -1, -1, -1, -1, -1, -1, -1, 20, -1, -1, -1, -1, -1, -1, -1, -1, 20, 27, 27, 27}, //16
            { 27,  27,  27,  20, -2, 22, 13, -1, 22, 24, 25, -1, 21, -1, 22, 24, 25, -1, 10, 25, -2, 20, 27, 27, 27}, //17
            { 27,  27,  27,  20, -1, -1, 20, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 20, -1, -1, 20, 27, 27, 27}, //18
            { 27,  27,  27,  15, 25, -1, 21, -1, 19, -1, 22, 24, 14, 24, 25, -1, 19, -1, 21, -1, 22, 17, 27, 27, 27}, //19
            { 27,  27,  27,  20, -1, -1, -1, -1, 20, -1, -1, -1, 20, -1, -1, -1, 20, -1, -1, -1, -1, 20, 27, 27, 27}, //20
            { 27,  27,  27,  20, -1, 22, 24, 24, 16, 24, 25, -1, 21, -1, 22, 24, 16, 24, 24, 25, -1, 20, 27, 27, 27}, //21
            { 27,  27,  27,  20, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 20, 27, 27, 27}, //22
            { 27,  27,  27,  11, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 12, 27, 27, 27}, //23
    };

    public static final int mapXMax = LEVEL1[LEVEL1.length-1].length;

    public static final int mapYMax = LEVEL1.length;

    public static int[][][] levels = new int[][][]{
        LEVEL1
    };

    public static Graph createGraphMap(int levelNum) {
        if(levelNum <= 0 || levelNum > levels.length) // if number of level out of area return null
            return null;

        Graph graph = new Graph();
        int[][] map = levels[levelNum-1]; // map of current lvl from levels array by levelNum
        for(int y = 0; y < mapYMax; y++) { // row running (Y)
            for(int x = 0; x < mapXMax; x++) { // column running (X)
                if(map[y][x] <= 0 || map[y][x] == 26) { // if block on this point != wall or == door
                    graph.addNode(new Point2D(x, y)); // we can move by this block and add it like node
                    if (x != mapXMax - 1) { // if x is not point of last block by X
                        if(map[y][x+1] <= 0 || map[y][x+1] == 26) // if next(right) by X block != wall or == door
                            graph.addEdge(new Point2D(x, y), new Point2D(x + 1, y)); // add connect between this and next(right)
                        if (y != mapYMax - 1) // if y is not point of last block by Y
                            if(map[y+1][x] <= 0 || map[y+1][x] == 26) // if next(bottom) by Y block != wall or == door
                                graph.addEdge(new Point2D(x, y), new Point2D(x, y + 1)); // add connect between this and next(bottom)
                    } else { // if x is point of last block by X
                        if (y != mapYMax - 1) // if y is point of last block by Y
                            if(map[y+1][x] <= 0 || map[y+1][x] == 26) // if next(bottom) by Y block != wall or == door
                                graph.addEdge(new Point2D(x, y), new Point2D(x, y + 1)); // add this
                    }
                }
            }
        }
        return graph;
    }

    public static int[] pixelPosToLogicPos(Point2D pixPos) {
        return new int[]{(int) pixPos.getX() / 28, (int) pixPos.getY() / 28};
    }
}
