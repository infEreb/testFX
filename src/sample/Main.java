package sample;

import Constructor.*;
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
import java.util.Locale;
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


        Scene scene = new Scene(root, 700, 672);
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

        Pacman pacman = createPacman();
        MoveActions pacMove = new MoveActions();

        Ghost redGhost = createGhost(Constants.Red);
        MoveActions redMove = new MoveActions();
        Ghost pinkGhost = createGhost(Constants.Pink);
        MoveActions pinkMove = new MoveActions();

        root.getChildren().addAll(pacman, redGhost, pinkGhost);
        redMove.setTime(System.nanoTime());
        pinkMove.setTime(System.nanoTime());
        new AnimationTimer()
        {

            @Override
            public void handle(long presentNanoTime) {
                pacman.activeMoving(pacMove.move(pacman, todoMove));
                redGhost.activeMoving(redMove.randomMove(redGhost, presentNanoTime));
                pinkGhost.activeMoving(pinkMove.randomMove(pinkGhost, presentNanoTime));

            }
        }.start();

        primaryStage.show();
    }

    private Ghost createGhost(String color) {

        Sprite g_s = new Sprite(new ImageView("/res/Ghosts/"+color+"/"+color.toLowerCase(Locale.ROOT).charAt(0)+"-0.png"),
                new Point2D(0, 0));
        Sprite g_0u = new Sprite(new ImageView("/res/Ghosts/"+color+"/"+color.toLowerCase(Locale.ROOT).charAt(0)+"-0.png"),
                new Point2D(0, 0));
        Sprite g_1u = new Sprite(new ImageView("/res/Ghosts/"+color+"/"+color.toLowerCase(Locale.ROOT).charAt(0)+"-1.png"),
                new Point2D(0, 0));
        Sprite g_0d = new Sprite(new ImageView("/res/Ghosts/"+color+"/"+color.toLowerCase(Locale.ROOT).charAt(0)+"-0.png"),
                new Point2D(0, 0));
        Sprite g_1d = new Sprite(new ImageView("/res/Ghosts/"+color+"/"+color.toLowerCase(Locale.ROOT).charAt(0)+"-1.png"),
                new Point2D(0, 0));
        Sprite g_0l = new Sprite(new ImageView("/res/Ghosts/"+color+"/"+color.toLowerCase(Locale.ROOT).charAt(0)+"-0.png"),
                new Point2D(0, 0));
        Sprite g_1l = new Sprite(new ImageView("/res/Ghosts/"+color+"/"+color.toLowerCase(Locale.ROOT).charAt(0)+"-1.png"),
                new Point2D(0, 0));
        Sprite g_0r = new Sprite(new ImageView("/res/Ghosts/"+color+"/"+color.toLowerCase(Locale.ROOT).charAt(0)+"-0.png"),
                new Point2D(0, 0));
        Sprite g_1r = new Sprite(new ImageView("/res/Ghosts/"+color+"/"+color.toLowerCase(Locale.ROOT).charAt(0)+"-1.png"),
                new Point2D(0, 0));

        //============ SPRITE LIST ANIMATION ============
        ArrayList<Sprite> ghost_anim_up = new ArrayList<Sprite>();
        ghost_anim_up.add(g_0u);
        ghost_anim_up.add(g_1u);
        ArrayList<Sprite> ghost_anim_down = new ArrayList<Sprite>();
        ghost_anim_down.add(g_0d);
        ghost_anim_down.add(g_1d);
        ArrayList<Sprite> ghost_anim_left = new ArrayList<Sprite>();
        ghost_anim_left.add(g_0l);
        ghost_anim_left.add(g_1l);
        ArrayList<Sprite> ghost_anim_right = new ArrayList<Sprite>();
        ghost_anim_right.add(g_0r);
        ghost_anim_right.add(g_1r);

        ///============ SPRITE DIRECTION HASHMAP ANIMATION ============
        HashMap<Integer, ArrayList<Sprite>> ghostSprites = new HashMap<Integer, ArrayList<Sprite>>();
        ghostSprites.put(Constants.UP, ghost_anim_up);
        ghostSprites.put(Constants.DOWN, ghost_anim_down);
        ghostSprites.put(Constants.LEFT, ghost_anim_left);
        ghostSprites.put(Constants.RIGHT, ghost_anim_right);
        Body2D ghostBody = new Body2D(g_s,
                new RigidBody2D(12 * 28, 11 * 28, g_s.getWidth(), g_s.getHeight()));
        Ghost ghost = new Ghost(ghostBody, new SpriteAnimation(ghostSprites, 0.15));
        ghost.setTranslateX(12*28);
        ghost.setTranslateY(11*28);
        ghost.getBody().setPosition(new Point2D(12*28, 11*28));
        ghost.setMapPositionX(ghost.getBody().getPosition().getX());
        ghost.setMapPositionY(ghost.getBody().getPosition().getY());

        return ghost;
    }
    private Pacman createPacman() {
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
                new RigidBody2D(12*28,
                        14*28, p_up0.getWidth(), p_up0.getHeight()));

        Pacman pacman = new Pacman(pacmanBody2d, new SpriteAnimation(pacmanSprites,0.15));
        pacman.setTranslateX(12*28);
        pacman.setTranslateY(14*28);
        pacman.getBody().setPosition(new Point2D(12*28, 14*28));
        pacman.setMapPositionX(pacman.getBody().getPosition().getX());
        pacman.setMapPositionY(pacman.getBody().getPosition().getY());

        return pacman;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
