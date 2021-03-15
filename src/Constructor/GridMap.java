package Constructor;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import sample.Game;
import sample.Main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class GridMap extends Pane {
    static ArrayList<Block> listOfWalls = new ArrayList<>();
    public static ArrayList<Fruit> listOfPillows = new ArrayList<>();
    public static ArrayList<Fruit> listOfBigPillows = new ArrayList<>();

    int currentLevel = 1;
    Image imgFile;
    int [][] map;
    public GridMap(){
        this.map = LevelData.levels[currentLevel-1];
    }
    public void loadBlocks(Pane root){
        for (int ROW = 0; ROW < map.length; ROW++){
            for (int COLUMN = 0; COLUMN < map[ROW].length; COLUMN++){
                if (map[ROW][COLUMN] > 0) {
                    String intToStr = String.valueOf(map[ROW][COLUMN]);
                    String filepath = "/res/Map/" + intToStr + ".png";
                    imgFile = new Image(getClass().getResourceAsStream(filepath));
                    Wall b = new Wall(COLUMN, ROW, imgFile);
                    listOfWalls.add(b);
                    root.getChildren().add(b);
                }

            }
        }

    }

    public void loadPillows(Pane root){
        for (int ROW = 0; ROW < map.length; ROW++){
            for (int COLUMN = 0; COLUMN < map[ROW].length; COLUMN++){
                //small pillow
                if (map[ROW][COLUMN] == -1) {
                    String filepath = "/res/Fruit/pill.png";
                    imgFile = new Image(getClass().getResourceAsStream(filepath));
                    Fruit b = new Fruit(COLUMN, ROW, imgFile, 6);
                    listOfPillows.add(b);
                    root.getChildren().add(b);
                }
                //big pillow
                if (map[ROW][COLUMN] == -2) {
                    String filepath = "/res/Fruit/big-0.png";
                    imgFile = new Image(getClass().getResourceAsStream(filepath));
                    Fruit b = new Fruit(COLUMN, ROW, imgFile, 6);
                    listOfBigPillows.add(b);
                    root.getChildren().add(b);
                }
            }
        }
    }
    public ArrayList<Block> getListOfBlocks() { return listOfWalls;}
    public static Block getBlockByPoint(Point2D logicalPos) {
        for (Block block: listOfWalls) {
            if(block.getLogicalPosition().equals(logicalPos)) {
                System.out.println("getBlock - " + block.getLogicalPosition());
                return block;
            }
        }
        return null;
    }
    public static Block getPillowByPoint(Point2D logicalPos) {
        for (Block fruit: listOfPillows) {
            if(fruit.getBody().getLogicalPosition().equals(logicalPos)) {
                System.out.println("getBlock - " + fruit.getLogicalPosition());
                return fruit;
            }
        }
        return null;
    }
    public static Block getBigPillowByPoint(Point2D logicalPos) {
        for (Block fruit: listOfBigPillows) {
            if(fruit.getBody().getLogicalPosition().equals(logicalPos)) {
                System.out.println("getBlock - " + fruit.getLogicalPosition());
                return fruit;
            }
        }
        return null;
    }
}
