package Constructor;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import sample.Main;

import java.util.ArrayList;


public class GridMap extends Pane {
    ArrayList<Block> listOfBlocks = new ArrayList<>();
    int currentLevel = 1;
    Image imgFile;
    public GridMap(){

    }
    public void loadBlocks(){
        int [][] blocks = LevelData.levels[currentLevel-1];
        for (int ROW = 0; ROW < blocks.length; ROW++){
            for (int COLUMN = 0; COLUMN < blocks[ROW].length; COLUMN++){
                if (blocks[ROW][COLUMN] > 0) {
                    String intToStr = String.valueOf(blocks[ROW][COLUMN]);
                    String filepath = "/res/Map/" + intToStr + ".png";
                    imgFile = new Image(getClass().getResourceAsStream(filepath));
                    Block b = new Block(COLUMN, ROW, imgFile);
                    listOfBlocks.add(b);
                    Main.root.getChildren().add(b);
                }

            }
        }

    }
}
