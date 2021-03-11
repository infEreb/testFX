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
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;


public class Main extends Application {

    public static Pane root = new Pane();
    public static GridMap gridMap = new GridMap();
    int activeMove = Constants.NONE;
    int todoMove = Constants.NONE;
    boolean pIsStuck = true;
    boolean rgIsStuck = true;
    @Override
    public void start(Stage primaryStage) throws Exception{


        gridMap.loadBlocks();
        gridMap.loadPillows();


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

        // creating sprites and add them to the sprite list
        Sprite p_up0 = new Sprite(new ImageView("/res/Pacman/2.png"),
                new Point2D(0,0), new Point2D(0,0));
        Sprite p_up1 = new Sprite(new ImageView("/res/Pacman/1u.png"),
                new Point2D(0,0), new Point2D(0,0));
        Sprite p_up2 = new Sprite(new ImageView("/res/Pacman/0u.png"),
                new Point2D(0,0), new Point2D(0,0));
        ArrayList<Sprite> up = new ArrayList<Sprite>();
        up.add(p_up0);
        up.add(p_up1);
        up.add(p_up2);
        Sprite p_down0 = new Sprite(new ImageView("/res/Pacman/2.png"),
                new Point2D(0,0), new Point2D(0,0));
        Sprite p_down1 = new Sprite(new ImageView("/res/Pacman/1d.png"),
                new Point2D(0,0), new Point2D(0,0));
        Sprite p_down2 = new Sprite(new ImageView("/res/Pacman/0d.png"),
                new Point2D(0,0), new Point2D(0,0));
        ArrayList<Sprite> down = new ArrayList<Sprite>();
        down.add(p_down0);
        down.add(p_down1);
        down.add(p_down2);
        Sprite p_left0 = new Sprite(new ImageView("/res/Pacman/2.png"),
                new Point2D(0,0), new Point2D(0,0));
        Sprite p_left1 = new Sprite(new ImageView("/res/Pacman/1l.png"),
                new Point2D(0,0), new Point2D(0,0));
        Sprite p_left2 = new Sprite(new ImageView("/res/Pacman/0l.png"),
                new Point2D(0,0), new Point2D(0,0));
        ArrayList<Sprite> left = new ArrayList<Sprite>();
        left.add(p_left0);
        left.add(p_left1);
        left.add(p_left2);
        Sprite p_right0 = new Sprite(new ImageView("/res/Pacman/2.png"),
                new Point2D(0,0), new Point2D(0,0));
        Sprite p_right1 = new Sprite(new ImageView("/res/Pacman/1r.png"),
                new Point2D(0,0), new Point2D(0,0));
        Sprite p_right2 = new Sprite(new ImageView("/res/Pacman/0r.png"),
                new Point2D(0,0), new Point2D(0,0));
        ArrayList<Sprite> right = new ArrayList<Sprite>();
        right.add(p_right0);
        right.add(p_right1);
        right.add(p_right2);

        // puts list into hashmap for animation
        HashMap<Integer, ArrayList<Sprite>> pacmanSprites = new HashMap<>();
        pacmanSprites.put(Constants.UP, up);
        pacmanSprites.put(Constants.DOWN, down);
        pacmanSprites.put(Constants.LEFT, left);
        pacmanSprites.put(Constants.RIGHT, right);

        Body2D pacmanBody2d = new Body2D(p_up0,
                new RigidBody2D(9*28,
                        14*28, p_up0.getWidth(), p_up0.getHeight()));

        Pacman pacman = new Pacman(pacmanBody2d, new SpriteAnimation(pacmanSprites,0.15));
        pacman.setTranslateX(9*28);
        pacman.setTranslateY(14*28);
        pacman.getBody().setPosition(new Point2D(9*28, 14*28));
        pacman.setMapPositionX(pacman.getBody().getPosition().getX());
        pacman.setMapPositionY(pacman.getBody().getPosition().getY());

        Sprite g_rs = new Sprite(new ImageView("/res/Ghosts/Red/r-0.png"), new Point2D(0,0));
        Sprite g_r0u = new Sprite(new ImageView("/res/Ghosts/Red/r-0.png"), new Point2D(0,0));
        Sprite g_r1u = new Sprite(new ImageView("/res/Ghosts/Red/r-1.png"), new Point2D(0,0));
        Sprite g_r0d = new Sprite(new ImageView("/res/Ghosts/Red/r-0.png"), new Point2D(0,0));
        Sprite g_r1d = new Sprite(new ImageView("/res/Ghosts/Red/r-1.png"), new Point2D(0,0));
        Sprite g_r0l = new Sprite(new ImageView("/res/Ghosts/Red/r-0.png"), new Point2D(0,0));
        Sprite g_r1l = new Sprite(new ImageView("/res/Ghosts/Red/r-1.png"), new Point2D(0,0));
        Sprite g_r0r = new Sprite(new ImageView("/res/Ghosts/Red/r-0.png"), new Point2D(0,0));
        Sprite g_r1r = new Sprite(new ImageView("/res/Ghosts/Red/r-1.png"), new Point2D(0,0));
        ArrayList<Sprite> red_ghost_anim_up = new ArrayList<Sprite>();
        red_ghost_anim_up.add(g_r0u);
        red_ghost_anim_up.add(g_r1u);
        ArrayList<Sprite> red_ghost_anim_down = new ArrayList<Sprite>();
        red_ghost_anim_down.add(g_r0d);
        red_ghost_anim_down.add(g_r1d);
        ArrayList<Sprite> red_ghost_anim_left = new ArrayList<Sprite>();
        red_ghost_anim_left.add(g_r0l);
        red_ghost_anim_left.add(g_r1l);
        ArrayList<Sprite> red_ghost_anim_right = new ArrayList<Sprite>();
        red_ghost_anim_right.add(g_r0r);
        red_ghost_anim_right.add(g_r1r);
        ///============
        HashMap<Integer, ArrayList<Sprite>> redGhostSprites = new HashMap<Integer, ArrayList<Sprite>>();
        redGhostSprites.put(Constants.UP, red_ghost_anim_up);
        redGhostSprites.put(Constants.DOWN, red_ghost_anim_down);
        redGhostSprites.put(Constants.LEFT, red_ghost_anim_left);
        redGhostSprites.put(Constants.RIGHT, red_ghost_anim_right);
        Body2D redGhostBody = new Body2D(g_rs,
                new RigidBody2D(9*28,11*28, g_rs.getWidth(), g_rs.getHeight()));
        Ghost redGhost = new Ghost(redGhostBody, new SpriteAnimation(redGhostSprites, 0.15));
        redGhost.setTranslateX(9*28);
        redGhost.setTranslateY(11*28);
        redGhost.getBody().setPosition(new Point2D(9*28, 11*28));
        redGhost.setMapPositionX(redGhost.getBody().getPosition().getX());
        redGhost.setMapPositionY(redGhost.getBody().getPosition().getY());

        root.getChildren().addAll(pacman, redGhost);
        final Random[] rnd = {new Random()};
        //pacman.getAnimation().play();

        long[] tempNanoTime = {System.nanoTime()};
        final int[] rg_move = {1};
        final int[] rg_todo = {1};
        new AnimationTimer()
        {
            @Override
            public void handle(long presentNanoTime) {
                long time;

                if((pacman.getBody().getPosition().getX() % 28 == 0) && (pacman.getBody().getPosition().getY() % 28 == 0)){
                    if(!pIsStuck) {
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
                    pIsStuck = true;


                    if(todoMove != Constants.NONE && pacman.isPossibleToMove(todoMove)) {
                        activeMove = todoMove;
                        todoMove = Constants.NONE;
                    }
                }else{
                    pIsStuck = false;

                }

                rnd[0] = new Random();



                if((redGhost.getBody().getPosition().getX() % 28 == 0) && (redGhost.getBody().getPosition().getY() % 28 == 0)){
                    if(!rgIsStuck) {
                        switch (rg_move[0]) {
                            case Constants.RIGHT:
                                redGhost.mapPositionX++;
                                break;
                            case Constants.LEFT:
                                redGhost.mapPositionX--;
                                break;
                            case Constants.UP:
                                redGhost.mapPositionY--;
                                break;
                            case Constants.DOWN:
                                redGhost.mapPositionY++;
                                break;
                        }

                    }
                    rgIsStuck = true;

                    if(presentNanoTime - tempNanoTime[0] >= 3000000000L) {
                        //System.out.println("3 secs");
                        rg_todo[0] = rnd[0].nextInt(4) + 1;
                        if(redGhost.isPossibleToMove(rg_todo[0]))
                            rg_move[0] = rg_todo[0];
                        System.out.println("curr: " + rg_todo[0]);
                        tempNanoTime[0] = presentNanoTime;
                    }

                    while(!redGhost.isPossibleToMove(rg_todo[0])) {
                        rg_todo[0] = rnd[0].nextInt(4) + 1;
                        System.out.println("Stack " + rg_todo[0]);
                        rg_move[0] = rg_todo[0];
                    }
                }else{
                    rgIsStuck = false;

                }

                pacman.activeMoving(activeMove);
                System.out.println(rg_move[0]);
                redGhost.activeMoving(rg_move[0]);

            }
        }.start();

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
