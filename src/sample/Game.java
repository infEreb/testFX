package sample;

import Constructor.*;
import Engine.Constants;
import Engine.Graph;
import Engine.RigidBody2D;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class Game {
    private Integer currentLevel;
    public static Integer currentScore;
    private Integer maxScore;
    private int maxLives;
    public static int currentLives;
    private Integer countFruit;


    public static Pane root;
    public static GridMap gridMap;
    int activeMove;
    int todoMove;
    boolean pIsStuck;
    boolean rgIsStuck;

    //Static labels
    public static Label currentLevelLabel;
    public static Label currentScoreLabel;
    public static Label maxScoreLabel;
    public static Label countFruitLabel;

    public Game(){
        gridMap = new GridMap();
        root = new Pane();
        activeMove = Constants.NONE;
        todoMove = Constants.NONE;
        pIsStuck = true;
        rgIsStuck = true;
    }
    private void loadInfoLabels(){
        currentLevel = 1;
        currentScore = 0;
        maxScore = GridMap.listOfPillows.size() * Constants.SCORE_FOR_PILLOW;
        maxLives = 4;
        countFruit = 1;
        currentLives = maxLives;
    }
    public void startGame(Stage primaryStage){

        gridMap.loadBlocks(root);
        gridMap.loadPillows(root);

        loadInfoLabels();
        Graph<Point2D> graphMap = LevelData.createGraphMap(1);
        //System.out.println(graphMap.toString());

        BorderPane mainPane = this.createInfoBars();
        Scene scene = new Scene(mainPane, 21*28, 29*28);

        String stylesheet = getClass().getResource("/CSS/GameLabelCSS.css").toExternalForm();
        scene.getStylesheets().add(stylesheet);
        scene.setFill(Color.BLACK);

        primaryStage.setScene(scene);
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
        Ghost yellowGhost = createGhost(Constants.Yellow);
        MoveActions yellowMove = new MoveActions();
        Ghost blueGhost = createGhost(Constants.Blue);
        MoveActions blueMove = new MoveActions();

        root.getChildren().addAll(pacman, redGhost, pinkGhost, blueGhost, yellowGhost);
        redMove.setTime(System.nanoTime());
        pinkMove.setTime(System.nanoTime());
        yellowMove.setTime(System.nanoTime());
        blueMove.setTime(System.nanoTime());

        new AnimationTimer()
        {

            @Override
            public void handle(long presentNanoTime) {
                pacman.activeMoving(pacMove.move(pacman, todoMove));

                Point2D redPos = redGhost.getBody().getLogicalPosFromPixelPos();
                Point2D pacPos = pacman.getBody().getLogicalPosFromPixelPos();

                System.out.println(redPos);
                System.out.println(pacPos);

                ArrayList<Point2D> points = null;
                points = graphMap.nodeListToValueList(
                        graphMap.DijkstraSearching(
                                graphMap.getNode(redPos),
                                graphMap.getNode(pacPos)
                        )
                );
                Point2D vecPoint = points.get(0).subtract(redPos);
                int direction = MoveActions.vectorToDirection(vecPoint) != null ? MoveActions.vectorToDirection(vecPoint) : Constants.NONE;

                System.out.println("Direct: " + Constants.stringDirection(direction));


                redGhost.activeMoving(redMove.aiMove(redGhost, direction));


                pinkGhost.activeMoving(pinkMove.randomMove(pinkGhost, presentNanoTime));
                yellowGhost.activeMoving(yellowMove.randomMove(yellowGhost, presentNanoTime));
                blueGhost.activeMoving(blueMove.randomMove(blueGhost, presentNanoTime));

            }
        }.start();
        primaryStage.setResizable(false);
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
        pacman.setTranslateX(12*28);
        pacman.setTranslateY(14*28);
        pacman.getBody().setPosition(new Point2D(12*28, 14*28));
        pacman.setMapPositionX(pacman.getBody().getPosition().getX());
        pacman.setMapPositionY(pacman.getBody().getPosition().getY());

        return pacman;
    }
    public BorderPane createInfoBars(){

        String stylesheet = getClass().getResource("/CSS/GameLabelCSS.css").toExternalForm();

        //Score and level-----
        currentLevelLabel = new Label(this.currentLevel + " LEVEL");

        currentScoreLabel = new Label(currentScore.toString());

        VBox currentScoreAndLevel = new VBox(currentLevelLabel, currentScoreLabel);
        currentScoreAndLevel.setAlignment(Pos.BASELINE_RIGHT);
        ///-------------

        //High score--------
        Label maxScoreText = new Label("HIGH SCORE");

        maxScoreLabel = new Label(maxScore.toString());

        VBox maxScoreTextAndDigit = new VBox(maxScoreText, maxScoreLabel);
        maxScoreTextAndDigit.setAlignment(Pos.BASELINE_CENTER);

        //-----------------

        //Menu button-----
        Button menuButton = new Button("MENU");
        //----------------

        //Upper main bar---------------
        HBox scoreBar = new HBox(currentScoreAndLevel, maxScoreTextAndDigit, menuButton);
        scoreBar.setMaxWidth(19*28);

        HBox.setMargin(currentScoreAndLevel, new Insets(20, 0, 20, 0));
        HBox.setMargin(maxScoreTextAndDigit, new Insets(20, 0, 20, 20));
        HBox.setMargin(menuButton, new Insets(20, 0, 20, 20));

        scoreBar.setAlignment(Pos.TOP_CENTER);
        //------------------------------

        //Part bottom bar
        //Pacman lives ------------

        HBox pacmanLives = new HBox();
        pacmanLives.setAlignment(Pos.BASELINE_LEFT);

        for(int i = 0; i < maxLives; i++){
            pacmanLives.getChildren().add(new ImageView("/res/life.png"));
        }

        //--------------------------
        //Fruit count----------
        countFruitLabel = new Label(countFruit.toString());

        HBox fruitsCount = new HBox(new ImageView("/res/Fruit/cherry.png"), countFruitLabel);
        fruitsCount.setAlignment(Pos.BASELINE_RIGHT);
        //---------------------

        //Bottom bar---------

        HBox bottomBox = new HBox();
        Region r = new Region();
        HBox.setHgrow(r, Priority.ALWAYS);
        bottomBox.getChildren().addAll(pacmanLives, r, fruitsCount);
        bottomBox.setMaxWidth(17*28);
        bottomBox.setAlignment(Pos.BOTTOM_CENTER);
        //--------------------
        BorderPane mainPane = new BorderPane();
        mainPane.setTop(scoreBar);
        mainPane.setCenter(root);
        mainPane.setBottom(bottomBox);
        BorderPane.setAlignment(scoreBar, Pos.CENTER);
        BorderPane.setAlignment(bottomBox, Pos.CENTER);
        BorderPane.setMargin(scoreBar, new Insets(0, 0, 0, 50));
        BorderPane.setMargin(bottomBox, new Insets(0, 10, 5, 60));
        mainPane.setLayoutX(-28*2);
        mainPane.setBackground(new Background(new BackgroundFill(Color.BLACK,
                CornerRadii.EMPTY,
                Insets.EMPTY)));
        return mainPane;
    }

}
