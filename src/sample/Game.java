package sample;

import Constructor.*;
import Engine.*;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Game {

    Map<Integer, Ghost> ghosts;
    Pacman pacman;

    Map<Integer, MoveActions> pacmanAndGhostMovements;

    // ghost and pacman animations for more comfortable usage
    // for each of them creates arraylist with 4 same elements crs this fcking OOP takes values by link and deletes them after
    private static HashMap<String, SpriteAnimation> ghostsAnimations;
    private static ArrayList<SpriteAnimation> ghostsEscapeAnimation;
    private static ArrayList<SpriteAnimation> ghostsBlinkingAnimation;
    private static ArrayList<SpriteAnimation> ghostsDeathAnimation;
    //private static SpriteAnimation pacmanAnimation, pacmanDeathAnimation;

    public static Pane root;
    public static GridMap gridMap;
    int activeMove;
    public static int todoMove;

    public Game(){
        gridMap = new GridMap();
        root = new Pane();
        activeMove = Constants.NONE;
        todoMove = Constants.NONE;
        ghosts = new HashMap<>();
        pacmanAndGhostMovements = new HashMap<>();
        ghostsAnimations = new HashMap<>();
        ghostsEscapeAnimation = new ArrayList<>();
        ghostsBlinkingAnimation = new ArrayList<>();
        ghostsDeathAnimation = new ArrayList<>();

        // creates animations
        for(int i = 0; i < 4; i++) {
            ghostsEscapeAnimation.add(createGhostsEscapeAnimation());
            ghostsBlinkingAnimation.add(createGhostsBlinkingAnimation());
            ghostsDeathAnimation.add(createGhostsDeathAnimation());
        }



    }

    public static SpriteAnimation getGhostsAnimation(String ghosts_color) {
        return ghostsAnimations.get(ghosts_color);
    }
    public static SpriteAnimation getGhostsEscapeAnimation(int index) {
        return ghostsEscapeAnimation.get(index);
    }
    public static SpriteAnimation getGhostsBlinkingAnimation(int index) {
        return ghostsBlinkingAnimation.get(index);
    }
    public static SpriteAnimation getGhostsDeathAnimation(int index) {
        return ghostsDeathAnimation.get(index);
    }
//    public static SpriteAnimation getPacmanDeathAnimation() {
//        return pacmanDeathAnimation;
//    }

    public void startGame(Stage primaryStage){

        gridMap.loadBlocks(root);
        gridMap.loadPillows(root);

        InfoBar infoBar = new InfoBar(primaryStage);
        infoBar.doSettingForInfoBars();

        Scene scene = infoBar.getScene();
        primaryStage.setScene(scene);
        scene.setOnKeyPressed(
                keyEvent -> {
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

        );

        createCharactersAndMoveActions();

        root.getChildren().add(pacman);
        root.getChildren().addAll(ghosts.values());

        //setting nanoTime for each moveActions
        for(int i = 0; i < pacmanAndGhostMovements.size(); i++){
            pacmanAndGhostMovements.get(i).setTime(System.nanoTime());
        }

        GameLoop gameHandle = new GameLoop(pacman, ghosts, pacmanAndGhostMovements, infoBar);
        gameHandle.start();

        primaryStage.setResizable(false);
        primaryStage.show();
    }

    void createCharactersAndMoveActions(){
        pacman = createPacman();
        MoveActions pacMove = new MoveActions();
        pacmanAndGhostMovements.put(Constants.PACMAN, pacMove);

        Ghost redGhost = createGhost(Constants.RED_STR);
        MoveActions redMove = new MoveActions();
        ghosts.put(Constants.RED, redGhost);
        pacmanAndGhostMovements.put(Constants.RED, redMove);

        Ghost pinkGhost = createGhost(Constants.PINK_STR);
        MoveActions pinkMove = new MoveActions();
        ghosts.put(Constants.PINK, pinkGhost);
        pacmanAndGhostMovements.put(Constants.PINK, pinkMove);

        Ghost yellowGhost = createGhost(Constants.YELLOW_STR);
        MoveActions yellowMove = new MoveActions();
        ghosts.put(Constants.YELLOW, yellowGhost);
        pacmanAndGhostMovements.put(Constants.YELLOW, yellowMove);

        Ghost blueGhost = createGhost(Constants.BLUE_STR);
        MoveActions blueMove = new MoveActions();
        ghosts.put(Constants.BLUE, blueGhost);
        pacmanAndGhostMovements.put(Constants.BLUE, blueMove);
    }
    private Ghost createGhost(String color) {

        Sprite g_s = new Sprite(new ImageView("/res/Ghosts/"+color+"/"+color.toLowerCase(Locale.ROOT).charAt(0)+"-0.png"),
                new Point2D(0, 0));
        Sprite g_0u = new Sprite(new ImageView("/res/Ghosts/"+color+"/"+color.toLowerCase(Locale.ROOT).charAt(0)+"-0u.png"),
                new Point2D(0, 0));
        Sprite g_1u = new Sprite(new ImageView("/res/Ghosts/"+color+"/"+color.toLowerCase(Locale.ROOT).charAt(0)+"-1u.png"),
                new Point2D(0, 0));
        Sprite g_0d = new Sprite(new ImageView("/res/Ghosts/"+color+"/"+color.toLowerCase(Locale.ROOT).charAt(0)+"-0d.png"),
                new Point2D(0, 0));
        Sprite g_1d = new Sprite(new ImageView("/res/Ghosts/"+color+"/"+color.toLowerCase(Locale.ROOT).charAt(0)+"-1d.png"),
                new Point2D(0, 0));
        Sprite g_0l = new Sprite(new ImageView("/res/Ghosts/"+color+"/"+color.toLowerCase(Locale.ROOT).charAt(0)+"-0l.png"),
                new Point2D(0, 0));
        Sprite g_1l = new Sprite(new ImageView("/res/Ghosts/"+color+"/"+color.toLowerCase(Locale.ROOT).charAt(0)+"-1l.png"),
                new Point2D(0, 0));
        Sprite g_0r = new Sprite(new ImageView("/res/Ghosts/"+color+"/"+color.toLowerCase(Locale.ROOT).charAt(0)+"-0r.png"),
                new Point2D(0, 0));
        Sprite g_1r = new Sprite(new ImageView("/res/Ghosts/"+color+"/"+color.toLowerCase(Locale.ROOT).charAt(0)+"-1r.png"),
                new Point2D(0, 0));

        //============ SPRITE LIST ANIMATION ============
        ArrayList<Sprite> ghost_anim_up = new ArrayList<>();
        ghost_anim_up.add(g_0u);
        ghost_anim_up.add(g_1u);
        ArrayList<Sprite> ghost_anim_down = new ArrayList<>();
        ghost_anim_down.add(g_0d);
        ghost_anim_down.add(g_1d);
        ArrayList<Sprite> ghost_anim_left = new ArrayList<>();
        ghost_anim_left.add(g_0l);
        ghost_anim_left.add(g_1l);
        ArrayList<Sprite> ghost_anim_right = new ArrayList<>();
        ghost_anim_right.add(g_0r);
        ghost_anim_right.add(g_1r);

        ///============ SPRITE DIRECTION HASHMAP ANIMATION ============
        HashMap<Integer, ArrayList<Sprite>> ghostSprites = new HashMap<>();
        ghostSprites.put(Constants.UP, ghost_anim_up);
        ghostSprites.put(Constants.DOWN, ghost_anim_down);
        ghostSprites.put(Constants.LEFT, ghost_anim_left);
        ghostSprites.put(Constants.RIGHT, ghost_anim_right);
        Body2D ghostBody = new Body2D(g_s,
                new RigidBody2D(12 * 28, 11 * 28, g_s.getWidth(), g_s.getHeight()));
        // saving anim
        SpriteAnimation ghostsAnimation = new SpriteAnimation(ghostSprites, 0.15);
        ghostsAnimations.put(color, ghostsAnimation);
        // creating ghost
        Ghost ghost = new Ghost(ghostBody, ghostsAnimation);

        ghost.setStartedPosition(12*28, 11*28);

        return ghost;
    }
    private SpriteAnimation createGhostsEscapeAnimation() {

        Sprite g_s = new Sprite(new ImageView("/res/Ghosts/Death/f-0.png"),
                new Point2D(0, 0));
        Sprite g_0u = new Sprite(new ImageView("/res/Ghosts/Death/f-0.png"),
                new Point2D(0, 0));
        Sprite g_1u = new Sprite(new ImageView("/res/Ghosts/Death/f-1.png"),
                new Point2D(0, 0));
        Sprite g_0d = new Sprite(new ImageView("/res/Ghosts/Death/f-0.png"),
                new Point2D(0, 0));
        Sprite g_1d = new Sprite(new ImageView("/res/Ghosts/Death/f-1.png"),
                new Point2D(0, 0));
        Sprite g_0l = new Sprite(new ImageView("/res/Ghosts/Death/f-0.png"),
                new Point2D(0, 0));
        Sprite g_1l = new Sprite(new ImageView("/res/Ghosts/Death/f-1.png"),
                new Point2D(0, 0));
        Sprite g_0r = new Sprite(new ImageView("/res/Ghosts/Death/f-0.png"),
                new Point2D(0, 0));
        Sprite g_1r = new Sprite(new ImageView("/res/Ghosts/Death/f-1.png"),
                new Point2D(0, 0));

        //============ SPRITE LIST ANIMATION ============
        ArrayList<Sprite> ghost_anim_up = new ArrayList<>();
        ghost_anim_up.add(g_0u);
        ghost_anim_up.add(g_1u);
        ArrayList<Sprite> ghost_anim_down = new ArrayList<>();
        ghost_anim_down.add(g_0d);
        ghost_anim_down.add(g_1d);
        ArrayList<Sprite> ghost_anim_left = new ArrayList<>();
        ghost_anim_left.add(g_0l);
        ghost_anim_left.add(g_1l);
        ArrayList<Sprite> ghost_anim_right = new ArrayList<>();
        ghost_anim_right.add(g_0r);
        ghost_anim_right.add(g_1r);

        ///============ SPRITE DIRECTION HASHMAP ANIMATION ============
        HashMap<Integer, ArrayList<Sprite>> ghostsEscapeSprites = new HashMap<>();
        ghostsEscapeSprites.put(Constants.UP, ghost_anim_up);
        ghostsEscapeSprites.put(Constants.DOWN, ghost_anim_down);
        ghostsEscapeSprites.put(Constants.LEFT, ghost_anim_left);
        ghostsEscapeSprites.put(Constants.RIGHT, ghost_anim_right);

        return new SpriteAnimation(ghostsEscapeSprites, 0.15);
    }
    private SpriteAnimation createGhostsBlinkingAnimation() {

        Sprite g_s = new Sprite(new ImageView("/res/Ghosts/Death/f-0.png"),
                new Point2D(0, 0));
        // up
        Sprite g_0u = new Sprite(new ImageView("/res/Ghosts/Death/f-0.png"),
                new Point2D(0, 0));
        Sprite g_1u = new Sprite(new ImageView("/res/Ghosts/Death/f-1.png"),
                new Point2D(0, 0));
        Sprite g_2u = new Sprite(new ImageView("/res/Ghosts/Death/f-2.png"),
                new Point2D(0, 0));
        Sprite g_3u = new Sprite(new ImageView("/res/Ghosts/Death/f-3.png"),
                new Point2D(0, 0));
        // down
        Sprite g_0d = new Sprite(new ImageView("/res/Ghosts/Death/f-0.png"),
                new Point2D(0, 0));
        Sprite g_1d = new Sprite(new ImageView("/res/Ghosts/Death/f-1.png"),
                new Point2D(0, 0));
        Sprite g_2d = new Sprite(new ImageView("/res/Ghosts/Death/f-2.png"),
                new Point2D(0, 0));
        Sprite g_3d = new Sprite(new ImageView("/res/Ghosts/Death/f-3.png"),
                new Point2D(0, 0));
        // left
        Sprite g_0l = new Sprite(new ImageView("/res/Ghosts/Death/f-0.png"),
                new Point2D(0, 0));
        Sprite g_1l = new Sprite(new ImageView("/res/Ghosts/Death/f-1.png"),
                new Point2D(0, 0));
        Sprite g_2l = new Sprite(new ImageView("/res/Ghosts/Death/f-2.png"),
                new Point2D(0, 0));
        Sprite g_3l = new Sprite(new ImageView("/res/Ghosts/Death/f-3.png"),
                new Point2D(0, 0));
        // right
        Sprite g_0r = new Sprite(new ImageView("/res/Ghosts/Death/f-0.png"),
                new Point2D(0, 0));
        Sprite g_1r = new Sprite(new ImageView("/res/Ghosts/Death/f-1.png"),
                new Point2D(0, 0));
        Sprite g_2r = new Sprite(new ImageView("/res/Ghosts/Death/f-2.png"),
                new Point2D(0, 0));
        Sprite g_3r = new Sprite(new ImageView("/res/Ghosts/Death/f-3.png"),
                new Point2D(0, 0));

        //============ SPRITE LIST ANIMATION ============
        ArrayList<Sprite> ghost_anim_up = new ArrayList<>();
        ghost_anim_up.add(g_0u);
        ghost_anim_up.add(g_1u);
        ghost_anim_up.add(g_2u);
        ghost_anim_up.add(g_3u);
        ArrayList<Sprite> ghost_anim_down = new ArrayList<>();
        ghost_anim_down.add(g_0d);
        ghost_anim_down.add(g_1d);
        ghost_anim_down.add(g_2d);
        ghost_anim_down.add(g_3d);
        ArrayList<Sprite> ghost_anim_left = new ArrayList<>();
        ghost_anim_left.add(g_0l);
        ghost_anim_left.add(g_1l);
        ghost_anim_left.add(g_2l);
        ghost_anim_left.add(g_3l);
        ArrayList<Sprite> ghost_anim_right = new ArrayList<>();
        ghost_anim_right.add(g_0r);
        ghost_anim_right.add(g_1r);
        ghost_anim_right.add(g_2r);
        ghost_anim_right.add(g_3r);

        ///============ SPRITE DIRECTION HASHMAP ANIMATION ============
        HashMap<Integer, ArrayList<Sprite>> ghostBlinkingSprites = new HashMap<>();
        ghostBlinkingSprites.put(Constants.UP, ghost_anim_up);
        ghostBlinkingSprites.put(Constants.DOWN, ghost_anim_down);
        ghostBlinkingSprites.put(Constants.LEFT, ghost_anim_left);
        ghostBlinkingSprites.put(Constants.RIGHT, ghost_anim_right);

        return new SpriteAnimation(ghostBlinkingSprites, 0.15);
    }
    private SpriteAnimation createGhostsDeathAnimation() {

        Sprite g_s = new Sprite(new ImageView("/res/Ghosts/Eyes/d.png"),
                new Point2D(0, 0));
        Sprite g_0u = new Sprite(new ImageView("/res/Ghosts/Eyes/u.png"),
                new Point2D(0, 0));
        Sprite g_1u = new Sprite(new ImageView("/res/Ghosts/Eyes/u.png"),
                new Point2D(0, 0));
        Sprite g_0d = new Sprite(new ImageView("/res/Ghosts/Eyes/d.png"),
                new Point2D(0, 0));
        Sprite g_1d = new Sprite(new ImageView("/res/Ghosts/Eyes/d.png"),
                new Point2D(0, 0));
        Sprite g_0l = new Sprite(new ImageView("/res/Ghosts/Eyes/l.png"),
                new Point2D(0, 0));
        Sprite g_1l = new Sprite(new ImageView("/res/Ghosts/Eyes/l.png"),
                new Point2D(0, 0));
        Sprite g_0r = new Sprite(new ImageView("/res/Ghosts/Eyes/r.png"),
                new Point2D(0, 0));
        Sprite g_1r = new Sprite(new ImageView("/res/Ghosts/Eyes/r.png"),
                new Point2D(0, 0));

        //============ SPRITE LIST ANIMATION ============
        ArrayList<Sprite> ghost_anim_up = new ArrayList<>();
        ghost_anim_up.add(g_0u);
        ghost_anim_up.add(g_1u);
        ArrayList<Sprite> ghost_anim_down = new ArrayList<>();
        ghost_anim_down.add(g_0d);
        ghost_anim_down.add(g_1d);
        ArrayList<Sprite> ghost_anim_left = new ArrayList<>();
        ghost_anim_left.add(g_0l);
        ghost_anim_left.add(g_1l);
        ArrayList<Sprite> ghost_anim_right = new ArrayList<>();
        ghost_anim_right.add(g_0r);
        ghost_anim_right.add(g_1r);

        ///============ SPRITE DIRECTION HASHMAP ANIMATION ============
        HashMap<Integer, ArrayList<Sprite>> ghostsEscapeSprites = new HashMap<>();
        ghostsEscapeSprites.put(Constants.UP, ghost_anim_up);
        ghostsEscapeSprites.put(Constants.DOWN, ghost_anim_down);
        ghostsEscapeSprites.put(Constants.LEFT, ghost_anim_left);
        ghostsEscapeSprites.put(Constants.RIGHT, ghost_anim_right);

        return new SpriteAnimation(ghostsEscapeSprites, 0.15);
    }
    private Pacman createPacman() {
        Sprite p_up0 = new Sprite(new ImageView("/res/Pacman/2.png"),
                new Point2D(0,0), new Point2D(0,0));
        Sprite p_up1 = new Sprite(new ImageView("/res/Pacman/1u.png"),
                new Point2D(0,0), new Point2D(0,0));
        Sprite p_up2 = new Sprite(new ImageView("/res/Pacman/0u.png"),
                new Point2D(0,0), new Point2D(0,0));
        ArrayList<Sprite> up = new ArrayList<>();
        up.add(p_up0);
        up.add(p_up1);
        up.add(p_up2);
        Sprite p_down0 = new Sprite(new ImageView("/res/Pacman/2.png"),
                new Point2D(0,0), new Point2D(0,0));
        Sprite p_down1 = new Sprite(new ImageView("/res/Pacman/1d.png"),
                new Point2D(0,0), new Point2D(0,0));
        Sprite p_down2 = new Sprite(new ImageView("/res/Pacman/0d.png"),
                new Point2D(0,0), new Point2D(0,0));
        ArrayList<Sprite> down = new ArrayList<>();
        down.add(p_down0);
        down.add(p_down1);
        down.add(p_down2);
        Sprite p_left0 = new Sprite(new ImageView("/res/Pacman/2.png"),
                new Point2D(0,0), new Point2D(0,0));
        Sprite p_left1 = new Sprite(new ImageView("/res/Pacman/1l.png"),
                new Point2D(0,0), new Point2D(0,0));
        Sprite p_left2 = new Sprite(new ImageView("/res/Pacman/0l.png"),
                new Point2D(0,0), new Point2D(0,0));
        ArrayList<Sprite> left = new ArrayList<>();
        left.add(p_left0);
        left.add(p_left1);
        left.add(p_left2);
        Sprite p_right0 = new Sprite(new ImageView("/res/Pacman/2.png"),
                new Point2D(0,0), new Point2D(0,0));
        Sprite p_right1 = new Sprite(new ImageView("/res/Pacman/1r.png"),
                new Point2D(0,0), new Point2D(0,0));
        Sprite p_right2 = new Sprite(new ImageView("/res/Pacman/0r.png"),
                new Point2D(0,0), new Point2D(0,0));
        ArrayList<Sprite> right = new ArrayList<>();
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
        pacman.createPacmanDeathAnimation();

        pacman.setStartedPosition(12*28, 14*28);
        return pacman;
    }

}
