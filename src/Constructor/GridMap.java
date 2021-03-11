package Constructor;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import sample.Main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class GridMap extends Pane {
    ArrayList<Block> listOfBlocks = new ArrayList<>();
    public static ArrayList<Fruit> listOfPillows = new ArrayList<>();
    public static ArrayList<Fruit> listOfBigPillows = new ArrayList<>();

    int currentLevel = 1;
    Image imgFile;
    int [][] map;
    public GridMap(){
        this.map = LevelData.levels[currentLevel-1];
    }
    public void loadBlocks(){
        for (int ROW = 0; ROW < map.length; ROW++){
            for (int COLUMN = 0; COLUMN < map[ROW].length; COLUMN++){
                if (map[ROW][COLUMN] > 0) {
                    String intToStr = String.valueOf(map[ROW][COLUMN]);
                    String filepath = "/res/Map/" + intToStr + ".png";
                    imgFile = new Image(getClass().getResourceAsStream(filepath));
                    Block b = new Block(COLUMN, ROW, imgFile);
                    listOfBlocks.add(b);
                    Main.root.getChildren().add(b);
                }

            }
        }

    }

    public void loadPillows(){
        for (int ROW = 0; ROW < map.length; ROW++){
            for (int COLUMN = 0; COLUMN < map[ROW].length; COLUMN++){
                //small pillow
                if (map[ROW][COLUMN] == -1) {
                    String filepath = "/res/Fruit/pill.png";
                    imgFile = new Image(getClass().getResourceAsStream(filepath));
                    Fruit b = new Fruit(COLUMN, ROW, imgFile, 6);
                    listOfPillows.add(b);
                    Main.root.getChildren().add(b);
                }
                //big pillow
                if (map[ROW][COLUMN] == -2) {
                    String filepath = "/res/Fruit/big-0.png";
                    imgFile = new Image(getClass().getResourceAsStream(filepath));
                    Fruit b = new Fruit(COLUMN, ROW, imgFile, 6);
                    listOfBigPillows.add(b);
                    Main.root.getChildren().add(b);
                }
            }
        }
    }
    public ArrayList<Block> getListOfBlocks() { return listOfBlocks;}
}
