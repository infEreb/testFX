package sample;

import Constructor.Body2D;
import Constructor.GridMap;
import Constructor.Sprite;
import Constructor.SpriteAnimation;
import Engine.Constants;
import Engine.RigidBody2D;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.*;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static Engine.Constants.SPEED;


public class Main extends Application {

    public static Pane root = new Pane();
    public static GridMap gridMap = new GridMap();
    int activeMove = Constants.NONE;
    int todoMove = Constants.NONE;
    boolean isStuck = true;
    @Override
    public void start(Stage primaryStage) throws Exception{


        //root.setBackground(new Ba);
//        root.setPrefSize(1280, 720);

        gridMap.loadBlocks();


        Scene scene = new Scene(root, 800, 800);
        primaryStage.setScene(scene);
        root.setStyle("-fx-background-color: #000000;");

        scene.setOnKeyPressed(
                new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent keyEvent) {
                        switch (keyEvent.getCode().toString()) {
                            case "W":
                                todoMove = Constants.UP;
                                break;
                            case "S":
                                todoMove = Constants.DOWN;
                                break;
                            case "D":
                                todoMove = Constants.RIGHT;
                                break;
                            case "A":
                                todoMove = Constants.LEFT;
                                break;
                        }

                    }
                }
        );

        Body2D top_line = new Body2D(new Sprite(), new RigidBody2D(0,0,1280,1));
        Body2D left_line = new Body2D(new Sprite(), new RigidBody2D(0,0,1,720));
        Body2D bottom_line = new Body2D(new Sprite(), new RigidBody2D(0,680,1280,1));
        Body2D right_line = new Body2D(new Sprite(), new RigidBody2D(540,0,1,720));

        // creating sprites and add them to the sprite list
        Sprite p_up0 = new Sprite(new ImageView("/sample/2.png"),
                new Point2D(0,0), new Point2D(0,0));
        Sprite p_up1 = new Sprite(new ImageView("/sample/1u.png"),
                new Point2D(0,0), new Point2D(0,0));
        Sprite p_up2 = new Sprite(new ImageView("/sample/0u.png"),
                new Point2D(0,0), new Point2D(0,0));
        ArrayList<Sprite> up = new ArrayList<Sprite>();
        up.add(p_up0);
        up.add(p_up1);
        up.add(p_up2);
        Sprite p_down0 = new Sprite(new ImageView("/sample/2.png"),
                new Point2D(0,0), new Point2D(0,0));
        Sprite p_down1 = new Sprite(new ImageView("/sample/1d.png"),
                new Point2D(0,0), new Point2D(0,0));
        Sprite p_down2 = new Sprite(new ImageView("/sample/0d.png"),
                new Point2D(0,0), new Point2D(0,0));
        ArrayList<Sprite> down = new ArrayList<Sprite>();
        down.add(p_down0);
        down.add(p_down1);
        down.add(p_down2);
        Sprite p_left0 = new Sprite(new ImageView("/sample/2.png"),
                new Point2D(0,0), new Point2D(0,0));
        Sprite p_left1 = new Sprite(new ImageView("/sample/1l.png"),
                new Point2D(0,0), new Point2D(0,0));
        Sprite p_left2 = new Sprite(new ImageView("/sample/0l.png"),
                new Point2D(0,0), new Point2D(0,0));
        ArrayList<Sprite> left = new ArrayList<Sprite>();
        left.add(p_left0);
        left.add(p_left1);
        left.add(p_left2);
        Sprite p_right0 = new Sprite(new ImageView("/sample/2.png"),
                new Point2D(0,0), new Point2D(0,0));
        Sprite p_right1 = new Sprite(new ImageView("/sample/1r.png"),
                new Point2D(0,0), new Point2D(0,0));
        Sprite p_right2 = new Sprite(new ImageView("/sample/0r.png"),
                new Point2D(0,0), new Point2D(0,0));
        ArrayList<Sprite> right = new ArrayList<Sprite>();
        right.add(p_right0);
        right.add(p_right1);
        right.add(p_right2);

        // puts list into hashmap for animation
        HashMap<Integer, ArrayList<Sprite>> pacmanSprites = new HashMap<Integer, ArrayList<Sprite>>();
        pacmanSprites.put(Constants.UP, up);
        pacmanSprites.put(Constants.DOWN, down);
        pacmanSprites.put(Constants.LEFT, left);
        pacmanSprites.put(Constants.RIGHT, right);

        Body2D png_white = new Body2D(p_up0,
                new RigidBody2D(9*28,
                        14*28, p_up0.getWidth(), p_up0.getHeight()));

        final long tempNanoTime = System.nanoTime();

        Pacman pacman = new Pacman(png_white, new SpriteAnimation(pacmanSprites,2,0.15));
        pacman.setTranslateX(9*28);
        pacman.setTranslateY(14*28);
        pacman.getBody().setPosition(new Point2D(9*28, 14*28));
        pacman.setMapPositionX(pacman.getBody().getPosition().getX());
        pacman.setMapPositionY(pacman.getBody().getPosition().getY());
        root.getChildren().addAll(pacman);
        //pacman.getAnimation().play();

        new AnimationTimer()
        {

            double time;
            @Override
            public void handle(long presentNanoTime) {

                if((pacman.getBody().getPosition().getX() % 28 == 0) && (pacman.getBody().getPosition().getY() % 28 == 0)){
                    if(!isStuck) {
                        switch (activeMove) {
                            case Constants.RIGHT:
                                pacman.mapPositionX++;
                                break;
                            case Constants.LEFT:
                                pacman.mapPositionX--;
                                break;
                            case Constants.UP:
                                pacman.mapPositionY--;
                                break;
                            case Constants.DOWN:
                                pacman.mapPositionY++;
                                break;
                        }

                    }
                    isStuck = true;


                    if(todoMove != Constants.NONE && pacman.isPossibleToMove(todoMove)) {
                        activeMove = todoMove;
                        todoMove = Constants.NONE;
                    }
                }else{
                    isStuck = false;

                }

                pacman.activeMoving(activeMove);

            }
        }.start();

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
